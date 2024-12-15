package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public Day02() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("Day2Input.txt");
        int count = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) count++;
        }
        return count;
    }

    public Object part2() {
        List<List<Integer>> reports = FileReader.readAsListOfIntegerLists("Day2Input.txt");
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

