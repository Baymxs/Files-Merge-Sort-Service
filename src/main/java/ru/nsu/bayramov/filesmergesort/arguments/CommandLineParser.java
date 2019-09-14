package ru.nsu.bayramov.filesmergesort.arguments;

import com.beust.jcommander.JCommander;
import ru.nsu.bayramov.filesmergesort.exceptions.WrongArgException;

import java.util.Arrays;
import java.util.List;

public class CommandLineParser {
    private CommandLineArgs commandLineArgs;

    public CommandLineParser(String[] args) {
        commandLineArgs = new CommandLineArgs();

        JCommander.newBuilder().addObject(commandLineArgs).build().parse(Arrays.copyOfRange(args, 0, args.length));
    }

    public boolean getHelp() {
        return commandLineArgs.getHelpOption();
    }

    public void showHelp() {
        JCommander.newBuilder().addObject(commandLineArgs).build().usage();
    }

    public int getSortMode() throws WrongArgException {
        boolean descendingSortOption = commandLineArgs.getDescendingSortOption();
        boolean ascendingSortOption = commandLineArgs.getAscendingSortOption();

        if (descendingSortOption && !ascendingSortOption) {
            return 1;
        } else if (!descendingSortOption) {
            return 0;
        } else {
            throw new WrongArgException("Mutually exclusive options '-a' and -'d'");
        }
    }

    public int getDataType() throws WrongArgException {
        boolean integerDataOption = commandLineArgs.getIntegerDataOption();
        boolean stringDataOption = commandLineArgs.getStringDataOption();

        if (integerDataOption && !stringDataOption) {
            return 0;
        } else if (!integerDataOption && stringDataOption) {
            return 1;
        } else if (integerDataOption) {
            throw new WrongArgException("Mutually exclusive options '-s' and '-i'");
        } else {
            throw new WrongArgException("No data type option");
        }
    }

    public String getOutputFileName() throws WrongArgException {
        if (commandLineArgs.getFileNames().size() == 0) {
            throw new WrongArgException("No output file name");
        }

        return commandLineArgs.getFileNames().get(0);
    }

    public List<String> getInputFileNames() {
        return commandLineArgs.getFileNames().subList(1, commandLineArgs.getFileNames().size());
    }


}
