package com.github.krisbanas.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Extractor {
    public static String ints(String input) {
        return input.replaceAll("[^\\d,.]", "");
    }

    public static <T> List<T> getColumn(T[][] input, int columnIndex) {
        return Arrays.stream(input).map(ts -> ts[columnIndex]).collect(Collectors.toList());
    }
}
