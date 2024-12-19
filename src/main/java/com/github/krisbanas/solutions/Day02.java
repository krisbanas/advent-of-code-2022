package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.krisbanas.Main.ROW_PATTERN;

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

        System.out.printf(ROW_PATTERN, 2, part1Result, part1Time / 1_000_000.0, part2Result, part2Time / 1_000_000.0);
    }

    public long part1() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("2.txt");

        return reports.stream()
                .filter(this::isSafe)
                .count();
    }

    public long part2() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("2.txt");
        int count = 0;
        outer:
        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                count++;
                continue;
            }
            for (int i = 0; i < report.size(); i++) {
                List<Integer> newList = new ArrayList<>();
                newList.addAll(report.subList(0, i));
                newList.addAll(report.subList(i + 1, report.size()));
                if (isSafe(newList)) {
                    count++;
                    continue outer;
                }
            }
        }
        return count;
    }

    private boolean isSafe(List<Integer> report) {
        List<Integer> increasing = report.get(0) > report.get(1) ? report.reversed() : report;
        for (int i = 0; i < increasing.size() - 1; i++) {
            Integer curr = increasing.get(i);
            Integer next = increasing.get(i + 1);
            if (next - curr > 3) return false;
            if (next <= curr) return false;
        }
        return true;
    }
}
