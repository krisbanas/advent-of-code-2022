package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {

    public Day11() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String[] s = FileReader.readAsString("Day11Input.txt").split(" ");
        List<Long> input = Arrays.stream(s).map(x -> Long.valueOf(x)).toList();


        List<Long> nextStep = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            System.out.println("Part 1, step: " + i);
            nextStep = new ArrayList<>();
            for (long stone : input) {
                if (stone == 0) nextStep.add(1L);
                else if (String.valueOf(stone).length() % 2 == 0) {
                    var len = String.valueOf(stone).length();
                    var soneString = String.valueOf(stone);
                    long first = Long.parseLong(soneString.substring(0, len / 2));
                    String s1 = soneString.substring(len / 2).replaceAll("^0+", "");
                    if (s1.isEmpty()) s1 = "0";
                    long snd = Long.parseLong(s1);
                    nextStep.add(first);
                    nextStep.add(snd);
                } else nextStep.add(stone * 2024);
            }
            input = nextStep;
        }
        System.out.println(input.size());
        System.out.println(nextStep.size());
        return null;
    }

    public Object part2() {
        String[] s = FileReader.readAsString("Day11Input.txt").split(" ");
        List<Long> input = Arrays.stream(s).map(x -> Long.valueOf(x)).toList();


        Map<Long, Long> nextOcc = new HashMap<>();
        Map<Long, Long> occurrences = new HashMap<>();
        occurrences = input.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        for (int i = 0; i < 75; i++) {
            System.out.println("Step: " + i);
            for (long stone : occurrences.keySet()) {
                if (stone == 0) nextOcc.put(1L, occurrences.get(stone));
                else if (String.valueOf(stone).length() % 2 == 0) {
                    var len = String.valueOf(stone).length();
                    var soneString = String.valueOf(stone);
                    long first = Long.parseLong(soneString.substring(0, len / 2));
                    String s1 = soneString.substring(len / 2).replaceAll("^0+", "");
                    if (s1.isEmpty()) s1 = "0";
                    long snd = Long.parseLong(s1);

                    var existingFirsts = nextOcc.getOrDefault(first, 0L);
                    nextOcc.put(first, existingFirsts + occurrences.get(stone));

                    var existingSnd = nextOcc.getOrDefault(snd, 0L);
                    nextOcc.put(snd, existingSnd + occurrences.get(stone));
                } else {
                    long value = stone * 2024;
                    var existing = nextOcc.getOrDefault(value, 0L);
                    nextOcc.put(value, existing + occurrences.get(stone));
                }
            }
            occurrences = nextOcc;
            nextOcc = new HashMap<>();
        }

        long res = 0;
        for (Long x : occurrences.values()) {
            res += x;
        }
        System.out.println(res);
        return null;
    }

}
