package ru.nsu.bayramov.filesmergesort.factories;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class DataSorting {
    private boolean isInterrupted;

    private void interrupt() {
        isInterrupted = true;
    }

    public abstract List<String> sortData(String s1, String s2, int sortMode,
                                          BufferedReader firstFileReader, BufferedReader secondFileReader,
                                          FileWriter outputFile) throws IOException;

    public void sort(int sortMode, String outputFileName, List<String> inputFileNames)
            throws InterruptedException, IOException {
        int numOfIterations = 0;
        boolean isFirstInputFiles = false;

        File destFile = new File(outputFileName);
        if (!destFile.isFile()) {
            throw new IOException("invalid output file name");
        }

        if (inputFileNames.size() == 0) {
            return;
        }

        while (inputFileNames.size() != 1) {
            ExecutorService threadPool = Executors.newFixedThreadPool(16);
            List<String> tmpInputFileNames = new ArrayList<>();

            for (int i = 0; i < inputFileNames.size() / 2; i++) {
                String tmpFileName = numOfIterations + "_" + i + ".txt";

                threadPool.submit(new FileSortService(this, tmpInputFileNames, tmpFileName,
                        sortMode, tmpFileName, inputFileNames.get(2 * i), inputFileNames.get(2 * i + 1)));
            }

            threadPool.shutdown();
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            if (isInterrupted) {
                return;
            }

            //if the number of input files is odd, add the latest file to the end of the list
            if (inputFileNames.size() % 2 != 0) {
                tmpInputFileNames.add(inputFileNames.get(inputFileNames.size() - 1));
            }

            //delete already sorted files without input files
            if (!isFirstInputFiles) {
                isFirstInputFiles = true;
            } else {
                for (int i = 0; i < inputFileNames.size(); i++) {
                    if (inputFileNames.size() % 2 != 0 && inputFileNames.size() != 2 && i == inputFileNames.size() - 1) {
                    } else {
                        File file = new File(inputFileNames.get(i));
                        if (!file.delete()) {
                            System.err.println("File " + inputFileNames.get(i) + " was not deleted");
                        }
                    }
                }
            }

            inputFileNames = tmpInputFileNames;
            numOfIterations++;
        }

        File sourceFile = new File(inputFileNames.get(0));

        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        if (!sourceFile.delete()) {
            System.err.println("File " + sourceFile.getName() + " was not deleted");
        }
    }

    public class FileSortService implements Runnable {
        DataSorting dataSorting;
        List<String> tmpInputFileNames;
        String tmpFileName;
        int sortMode;
        String outputFileName, firstFileName, secondFileName;

        public FileSortService(DataSorting dataSorting, List<String> tmpInputFileNames, String tmpFileName,
                               int sortMode, String outputFileName,
                               String firstFileName, String secondFileName) {
            this.dataSorting = dataSorting;
            this.tmpInputFileNames = tmpInputFileNames;
            this.tmpFileName = tmpFileName;
            this.sortMode = sortMode;
            this.outputFileName = outputFileName;
            this.firstFileName = firstFileName;
            this.secondFileName = secondFileName;
        }

        @Override
        public void run() {
            String s1, s2;

            try (BufferedReader firstFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(firstFileName)));
                 BufferedReader secondFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(secondFileName)));
                 FileWriter outputFile = new FileWriter(outputFileName)) {

                s1 = firstFileReader.readLine();
                s2 = secondFileReader.readLine();

                while (s1 != null || s2 != null) {
                    if (s1 == null) {
                        outputFile.write(s2);
                        outputFile.write('\n');
                        s2 = secondFileReader.readLine();
                        continue;
                    }

                    if (s2 == null) {
                        outputFile.write(s1);
                        outputFile.write('\n');
                        s1 = firstFileReader.readLine();
                        continue;
                    }

                   List<String> out = sortData(s1, s2, sortMode, firstFileReader, secondFileReader, outputFile);

                    s1 = out.get(0);
                    s2 = out.get(1);
                }

                tmpInputFileNames.add(tmpFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file name " + e.getMessage());
                dataSorting.interrupt();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                dataSorting.interrupt();
            }
        }
    }
}
