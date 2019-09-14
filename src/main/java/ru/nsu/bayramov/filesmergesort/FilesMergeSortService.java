package ru.nsu.bayramov.filesmergesort;

import ru.nsu.bayramov.filesmergesort.arguments.CommandLineParser;
import ru.nsu.bayramov.filesmergesort.exceptions.WrongArgException;
import ru.nsu.bayramov.filesmergesort.factories.DataSorting;
import ru.nsu.bayramov.filesmergesort.factories.IntegerDataSorting;
import ru.nsu.bayramov.filesmergesort.factories.StringDataSorting;

import java.io.IOException;
import java.util.List;

public class FilesMergeSortService {
    private CommandLineParser commandLineParser;

    private int sortMode;
    private int dataType;
    private String outputFileName;
    private List<String> inputFileNames;

    private DataSorting dataSorting;

    private void setOptions() throws WrongArgException {
        sortMode = commandLineParser.getSortMode();
        dataType = commandLineParser.getDataType();
        outputFileName = commandLineParser.getOutputFileName();
        inputFileNames = commandLineParser.getInputFileNames();
    }

    public void sort(String[] args) throws InterruptedException, IOException {
        commandLineParser = new CommandLineParser(args);

        if (commandLineParser.getHelp()) {
            commandLineParser.showHelp();
            return;
        }

        setOptions();

        if (dataType == 0) {
            dataSorting = new IntegerDataSorting();
        } else if (dataType == 1) {
            dataSorting = new StringDataSorting();
        }

        dataSorting.sort(sortMode, outputFileName, inputFileNames);
    }
}
