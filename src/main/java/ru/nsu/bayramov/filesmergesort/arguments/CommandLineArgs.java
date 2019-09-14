package ru.nsu.bayramov.filesmergesort.arguments;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgs {
    @Parameter(names = "-h", description = "Shows information about program options", help = true)
    private boolean help;

    public boolean getHelpOption() {
        return  help;
    }

    @Parameter(names = "-a", description = "Sort data in ascending order")
    private boolean ascendingSort;

    public boolean getAscendingSortOption() {
        return ascendingSort;
    }

    @Parameter(names = "-d", description = " Sort data in descending order")
    private boolean descendingSort;

    public boolean getDescendingSortOption() {
        return descendingSort;
    }

    @Parameter(names = "-s", description = "Sort data of type string")
    private boolean stringData;

    public boolean getStringDataOption() {
        return stringData;
    }

    @Parameter(names = "-i", description = "Sort data of type integer")
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
