package com.github.krisbanas.toolbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    public static final String RESOURCES_ROOT = "src/main/resources/";

    public static List<String> readAsStringList(String filename) {
        try {
            return Files.readAllLines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<Integer>> readAsListOfIntegerLists(String filename) {
        return readAsListOfIntegerLists(filename, " ");
    }

    public static List<List<Integer>> readAsListOfIntegerLists(String filename, String separator) {
        try {
            return Files.lines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath())
                    .map(line -> Arrays.stream(line.split(separator)).map(Integer::valueOf).toList())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> readAsIntegerList(String filename) {
        try {
            return Files.readAllLines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath())
                    .stream().mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAsString(String filename) {
        try {
            return Files.readString(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> loadStringSeparatedIntegers(String filename) {
        return Arrays.stream(FileReader.readAsString(filename).split(","))
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());
    }

    public static String[][] readAsStringGrid(String filename){
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[][] resultGrid = new String[lines.size()][lines.get(0).length()];
        for (int j = 0; j < lines.size(); j++) {
            String s = lines.get(j);
            var els = s.split("");
            String[] row = new String[els.length];
            for (int i = 0; i < els.length; i++) {
                String el = els[i];
                row[i] = el;
            }
            resultGrid[j] = row;
        }
        return resultGrid;
    }
}
