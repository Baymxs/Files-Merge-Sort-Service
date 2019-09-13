package ru.nsu.bayramov.filesmergesort;

public class Main {
    public static void main(String[] args) {
        try {
            new FilesMergeSortService().sort(args);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
