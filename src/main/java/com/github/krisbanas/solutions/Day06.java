package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.stream.IntStream;

import static java.lang.System.out;

public class Day06 {
    public Day06() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        return findSequenceWithDistinct(4);
    }

    private Object part2() {
        return findSequenceWithDistinct(14);
    }

    private static int findSequenceWithDistinct(int size) {
        final var input = FileReader.readAsString("Day6Input.txt");

        final var result = IntStream.range(0, input.length() - size)
                .mapToObj(i -> input.substring(i, i + size))
                .filter(s -> s.chars().distinct().count() == size)
                .findFirst()
                .orElseThrow();

        return input.indexOf(result) + size;
    }
}
