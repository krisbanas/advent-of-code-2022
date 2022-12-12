package com.github.krisbanas.toolbox;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    public static final String RESOURCES_ROOT = "src/main/resources/";

    @SneakyThrows
    public static List<String> readAsStringList(String filename) {
        return Files.readAllLines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath());
    }

    @SneakyThrows
    public static List<Integer> readAsIntegerList(String filename) {
        return Files.readAllLines(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath())
                .stream().mapToInt(Integer::valueOf)
                .boxed()
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static String readAsString(String filename) {
        return Files.readString(Paths.get(RESOURCES_ROOT + filename).toAbsolutePath());
    }

    @SneakyThrows
    public static List<Integer> loadStringSeparatedIntegers(String filename) {
        return Arrays.stream(FileReader.readAsString(filename).split(","))
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());
    }
}
