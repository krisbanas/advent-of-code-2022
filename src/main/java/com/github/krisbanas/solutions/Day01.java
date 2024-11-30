package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day01 {

    public Day01() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String string = FileReader.readAsString("Day1Input.txt");
        List<Long> first = new ArrayList<>();
        List<Long> second = new ArrayList<>();

        for (String entry : string.split("\r\n")) {
            String[] entries = entry.split(" +");
            first.add(Long.parseLong(entries[0]));
            second.add(Long.parseLong(entries[1]));
        }

        first = first.stream().sorted().toList();
        second = second.stream().sorted().toList();

        long total = 0;
        for (int i = 0; i < first.size(); i++) {
            total += Math.abs(second.get(i) - first.get(i));
        }

        return total;
    }

    public Object part2() {
        String string = FileReader.readAsString("Day1Input.txt");
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
