package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.Arrays;
import java.util.stream.LongStream;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public class Day01 {

    public Day01() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        return getInput().max().getAsLong();
    }

    private Object part2() {
        return getInput().limit(3).sum();
    }

    private static LongStream getInput() {
        return Arrays.stream(FileReader.readAsString("Day1Input.txt")
                        .split(lineSeparator() + lineSeparator()))
                .map(x -> x.split(lineSeparator()))
                .mapToLong(x -> Arrays.stream(x).mapToLong(Long::valueOf).sum());
    }
}
