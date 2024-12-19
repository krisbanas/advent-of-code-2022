package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public static void main(String[] args) {
        new Day02();
    }

    public Day02() {
        long startTime = System.nanoTime();
        long part1Result = part1();
        long part1Time = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long part2Result = part2();
        long part2Time = System.nanoTime() - startTime;

        System.out.printf("Part 1: %d (%.3fms)%s%n", part1Result, part1Time / 1_000_000.0, " should be: 220");
        System.out.printf("Part 2: %d (%.3fms)%s%n", part2Result, part2Time / 1_000_000.0, " should be: 296");
    }

    public long part1() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("2.txt");
        int count = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) count++;
        }
        return count;
    }

    public long part2() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("2.txt");
        int count = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) count++;
            else {
                boolean ok = false;
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> newList = new ArrayList<>();
                    newList.addAll(report.subList(0, i));
                    newList.addAll(report.subList(i + 1, report.size()));
                    if (isSafe(newList)) {
                        ok = true;
                    }
                }
                if (ok) count++;
            }
        }
        return count;
    }

    static boolean isSafe(List<Integer> report) {
        if (report.get(1) > report.get(0)) {
            for (int i = 0; i < report.size() - 1; i++) {
                Integer prev = report.get(i);
                Integer curr = report.get(i + 1);
                if (curr - prev > 3) return false;
                if (curr <= prev) return false;
            }
        } else {
            for (int i = 0; i < report.size() - 1; i++) {
                Integer prev = report.get(i);
                Integer curr = report.get(i + 1);
                if (curr - prev < 3) return false;
                if (curr >= prev) return false;
            }
        }
        return true;
    }
}

