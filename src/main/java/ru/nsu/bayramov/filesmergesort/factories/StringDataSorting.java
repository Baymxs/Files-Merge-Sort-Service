package ru.nsu.bayramov.filesmergesort.factories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringDataSorting extends DataSorting {
    @Override
    public List<String> sortData(String s1, String s2, int sortMode, BufferedReader firstFileReader,
                                 BufferedReader secondFileReader, FileWriter outputFile) throws IOException {
        List<String> out = new ArrayList<>();

        if ((sortMode == 0 && s1.compareTo(s2) < 0) || (sortMode == 1 && s1.compareTo(s2) > 0)) {
            outputFile.write(s1);
            outputFile.write('\n');
            s1 = firstFileReader.readLine();
        } else {
            outputFile.write(s2);
            outputFile.write('\n');
            s2 = secondFileReader.readLine();
        }

        out.add(s1);
        out.add(s2);

        return out;
    }
}
