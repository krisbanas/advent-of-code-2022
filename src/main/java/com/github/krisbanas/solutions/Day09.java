package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;

public class Day09 {

    public Day09() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String input = FileReader.readAsString("Day9Input.txt");
        List<Integer> disk = new ArrayList<>();
        int id = 0;

        for (int pointer = 0; pointer < input.length(); pointer += 2) {
            int blockLen = Character.digit(input.charAt(pointer), 10);
            for (int i = 0; i < blockLen; i++) disk.add(id);
            int emptyLen = 0;
            if (pointer + 1 < input.length()) {
                emptyLen = Character.digit(input.charAt(pointer + 1), 10);
            }
            for (int i = 0; i < emptyLen; i++) disk.add(-1);
            id++;
        }

        int left = 0;
        while (disk.get(left) != -1) left++;
        int right = disk.size() - 1;
        while (disk.get(right) == -1) right--;

        while (left < right) {
            disk.set(left, disk.get(right));
            disk.set(right, -1);
            while (disk.get(left) != -1) left++;
            while (disk.get(right) == -1) right--;
        }

        long result = 0;
        for (int i = 0; i < disk.size() && disk.get(i) != -1; i++) {
            result += (long) i * disk.get(i);
        }
        return result;
    }

    Map<Integer, Integer> blockLengths = new HashMap<>();
    Map<Integer, Integer> emptyLengths = new HashMap<>();


    public Object part2() {
        String input = FileReader.readAsString("Day9Input.txt");
        List<Integer> disk = new ArrayList<>();
        int id = 0;

        for (int pointer = 0; pointer < input.length(); pointer += 2) {
            int blockLen = Character.digit(input.charAt(pointer), 10);
            blockLengths.put(disk.size(), blockLen);
            for (int i = 0; i < blockLen; i++) disk.add(id);
            int emptyLen = 0;
            if (pointer + 1 < input.length()) {
                emptyLen = Character.digit(input.charAt(pointer + 1), 10);
                emptyLengths.put(disk.size(), emptyLen);
            }
            for (int i = 0; i < emptyLen; i++) disk.add(-1);
            id++;
        }

        List<Integer> sorted = blockLengths.keySet().stream().sorted(Comparator.comparingInt(x -> -x)).toList();
        List<Integer> emptySorted = emptyLengths.keySet().stream().sorted(Comparator.comparingInt(x -> x)).toList();
        outer:
        for (int blockLocation : sorted) { // try once
            int blockLen = blockLengths.get(blockLocation);
            for (int emptyLocation : emptySorted) {
                int emptylen = emptyLengths.getOrDefault(emptyLocation, 0);
                if (emptylen >= blockLen) {
                    for(int i=0; i < blockLen; i++) {
                        disk.set(emptyLocation + i, disk.get(blockLocation + i));
                        disk.set(blockLocation + i, -1);
                    }
                    emptyLengths.remove(emptyLocation);
                    if (emptylen - blockLen > 0) {
                        emptyLengths.put(emptyLocation + blockLen, emptylen - blockLen);
                        emptySorted = emptyLengths.keySet().stream().sorted(Comparator.comparingInt(x -> x)).toList();
                    }
                    continue outer;
                }
            }
        }
        System.out.println(Arrays.toString(disk.toArray()));
        long result = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == -1) continue;
            result += (long) i * disk.get(i);
        }
        return result;
    }
}
