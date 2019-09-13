package ru.nsu.bayramov.filesmergesort.arguments;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgs {
    @Parameter(names = "-a", description = "Sort files in ascending order")
    private boolean ascendingSort;

    public boolean getAscendingSortOption() {
        return ascendingSort;
    }

    @Parameter(names = "-d", description = "Sort files in descending order")
    private boolean descendingSort;

    public boolean getDescendingSortOption() {
        return descendingSort;
    }

    @Parameter(names = "-s", description = "String data")
    private boolean stringData;

    public boolean getStringDataOption() {
        return stringData;
    }

    @Parameter(names = "-i", description = "Integer data")
    private boolean integerData;

    public boolean getIntegerDataOption() {
        return integerData;
    }

    //[0] - output file name, [1 - ...] - input file names
    @Parameter(description = "File names")
    private List<String> inputFileNames = new ArrayList<>();

    public List<String> getFileNames() {
        return inputFileNames.subList(0, inputFileNames.size());
    }
}
