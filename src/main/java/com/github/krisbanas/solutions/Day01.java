package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String[] args) {
        new Day01();
    }

    public Day01() {
        long startTime = System.nanoTime();
        long part1Result = part1();
        long part1Time = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long part2Result = part2();
        long part2Time = System.nanoTime() - startTime;

        System.out.printf("Part 1: %d (%.3fms)%n", part1Result, part1Time / 1_000_000.0);
        System.out.printf("Part 2: %d (%.3fms)%n", part2Result, part2Time / 1_000_000.0);
    }

    public long part1() {
        String string = FileReader.readAsString("1.txt");
        String[] entries = string.split("\r\n");
        long[] first = new long[entries.length];
        long[] second = new long[entries.length];

        for (int i = 0; i < entries.length; i++) {
            String[] parts = entries[i].split(" +");
            first[i] = Long.parseLong(parts[0]);
            second[i] = Long.parseLong(parts[1]);
        }

        Arrays.sort(first);
        Arrays.sort(second);

        long total = 0;
        for (int i = 0; i < first.length; i++) {
            total += Math.abs(second[i] - first[i]);
        }
        return total;
    }

    public long part2() {
        String string = FileReader.readAsString("1.txt");
        List<Long> first = new ArrayList<>();
        List<Long> second = new ArrayList<>();

        for (String entry : string.split("\r\n")) {
            String[] entries = entry.split(" +");
            first.add(Long.parseLong(entries[0]));
            second.add(Long.parseLong(entries[1]));
        }

        Map<Long, Long> freq =
                second.stream().collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        long total = 0;
        for (long i : first) {
            total += freq.getOrDefault(i, 0L) * i;
        }
        return total;
    }
}
