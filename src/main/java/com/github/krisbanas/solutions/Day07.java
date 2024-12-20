package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;

import static com.github.krisbanas.Main.ROW_PATTERN;

public class Day07 {

    public static void main(String[] args) {
        new Day07();
    }

    public Day07() {
        long startTime = System.nanoTime();
        long part1Result = part1();
        long part1Time = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long part2Result = part2();
        long part2Time = System.nanoTime() - startTime;

        System.out.printf(ROW_PATTERN, 7, part1Result, part1Time / 1_000_000.0, part2Result, part2Time / 1_000_000.0);
    }

    public long part1() {
        List<String> input = FileReader.readAsStringList("7.txt");

        long result = 0;
        for (String entry : input) {
            long sum = Long.parseLong(entry.split(": ")[0]);
            List<Long> parts = Arrays.stream(entry.split(": ")[1].split(" ")).map(Long::parseLong).toList();
            Queue<List<Long>> q = new ArrayDeque<>();
            q.offer(parts);
            List<Long> results = new ArrayList<>();
            while (!q.isEmpty()) {
                List<Long> curr = q.poll();
                if (curr.size() == 1) {
                    results.add(curr.getFirst());
                    continue;
                }
                List<Long> summed = applySum(curr);
                q.offer(summed);

                List<Long> muld = applyMultiplication(curr);
                q.offer(muld);
            }
            for (long candidateResult : results) {
                if (candidateResult == sum) {
                    result += sum;
                    break;
                }
            }
        }

        return result;
    }

    private static List<Long> applyMultiplication(List<Long> curr) {
        List<Long> muld = new ArrayList<>();
        muld.add(curr.get(0) * curr.get(1));
        for (int i = 2; i < curr.size(); i++) muld.add(curr.get(i));
        return muld;
    }

    private static List<Long> applySum(List<Long> curr) {
        List<Long> summed = new ArrayList<>();
        summed.add(curr.get(0) + curr.get(1));
        for (int i = 2; i < curr.size(); i++) summed.add(curr.get(i));
        return summed;
    }

    public long part2() {
        List<String> input = FileReader.readAsStringList("7.txt");

        long result = 0;
        for (String entry : input) {
            long sum = Long.parseLong(entry.split(": ")[0]);
            List<Long> parts = Arrays.stream(entry.split(": ")[1].split(" ")).map(Long::parseLong).toList();
            Queue<List<Long>> q = new ArrayDeque<>();
            q.offer(parts);
            List<Long> results = new ArrayList<>();
            while (!q.isEmpty()) {
                List<Long> curr = q.poll();
                if (curr.size() == 1) {
                    results.add(curr.getFirst());
                    continue;
                }
                List<Long> summed = applySum(curr);
                q.offer(summed);

                List<Long> muld = applyMultiplication(curr);
                q.offer(muld);

                List<Long> cat = new ArrayList<>();
                String s = curr.get(0) + String.valueOf(curr.get(1));
                cat.add(Long.parseLong(s));
                for (int i = 2; i < curr.size(); i++) cat.add(curr.get(i));
                if (!(s.length() > String.valueOf(sum).length())) {
                    q.offer(cat);
                }
            }
            for (long candidateResult : results) {
                if (candidateResult == sum) {
                    result += sum;
                    break;
                }
            }
        }

        return result;
    }
}
