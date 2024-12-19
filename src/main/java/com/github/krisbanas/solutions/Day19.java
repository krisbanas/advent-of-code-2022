package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19 {

    public Day19() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String[] input = FileReader.readAsString("19.txt").split("\r\n\r\n");
        String[] colours = input[0].split(", ");
        String[] entries = input[1].split("\r\n");

        Map<String, Boolean> can = new HashMap<>();
        can.put("", true);
        long result = 0;

        for (String entry : entries) {
            outer:
            for (int j = entry.length() - 1; j >= 0; j--) {
                String suffix = entry.substring(j);
                for (String colour : colours) {
                    if (!suffix.startsWith(colour)) continue;
                    String newEntry = suffix.replaceFirst(colour, "");
                    if (can.getOrDefault(newEntry, false)) {
                        can.put(suffix, true);
                        continue outer;
                    }
                }
                can.put(suffix, false);
            }
            if (can.get(entry)) result++;
        }

        return result;
    }

    public Object part2() {
        String[] input = FileReader.readAsString("19.txt").split("\r\n\r\n");
        List<String> colours = new ArrayList<>(Arrays.stream(input[0].split(", ")).toList());
        String[] entries = input[1].split("\r\n");

        Set<String> cannot = new HashSet<>();
        Map<String, Long> count = new HashMap<>();
        count.put("", 1L);
        long result = 0;

        for (String entry : entries) {
            for (int j = entry.length() - 1; j >= 0; j--) {
                String toTest = entry.substring(j);
                if (count.containsKey(toTest)) continue;
                if (cannot.contains(toTest)) continue;

                for (String colour : colours) {
                    if (!toTest.startsWith(colour)) continue;
                    String newEntry = toTest.replaceFirst(colour, "");
                    if (!count.containsKey(newEntry)) continue;
                    if (!count.containsKey(toTest)) {
                        count.put(toTest, 0L);
                    }
                    if (count.containsKey(toTest)) {
                        count.put(toTest, count.get(toTest) + count.get(newEntry));
                    }
                }
                cannot.add(toTest);
            }
            if (count.containsKey(entry)) result += count.get(entry);
        }

        return result;
    }
}
