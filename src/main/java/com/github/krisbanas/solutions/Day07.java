package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;

public class Day07 {

    public Day07() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        List<String> input = FileReader.readAsStringList("Day7Input.txt");

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
                List<Long> summed = new ArrayList<>();
                summed.add(curr.get(0) + curr.get(1));
                for (int i = 2; i < curr.size(); i++) summed.add(curr.get(i));
                q.offer(summed);

                List<Long> muld = new ArrayList<>();
                muld.add(curr.get(0) * curr.get(1));
                for (int i = 2; i < curr.size(); i++) muld.add(curr.get(i));
                q.offer(muld);
            }
            for (long i : results) {
                if (i == sum) {
                    result += sum;
                    break;
                }
            }
        }

        return result;
    }

    public Object part2() {
        List<String> input = FileReader.readAsStringList("Day7Input.txt");

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
                List<Long> summed = new ArrayList<>();
                summed.add(curr.get(0) + curr.get(1));
                for (int i = 2; i < curr.size(); i++) summed.add(curr.get(i));
                q.offer(summed);

                List<Long> muld = new ArrayList<>();
                muld.add(curr.get(0) * curr.get(1));
                for (int i = 2; i < curr.size(); i++) muld.add(curr.get(i));
                q.offer(muld);

                List<Long> cat = new ArrayList<>();
                String s = String.valueOf(curr.get(0)) + String.valueOf(curr.get(1));
                if (!(s.length() > String.valueOf(sum).length())) {
                    cat.add(Long.parseLong(s));
                    for (int i = 2; i < curr.size(); i++) cat.add(curr.get(i));
                    q.offer(cat);
                }

            }
            for (long i : results) {
                if (i == sum) {
                    result += sum;
                    break;
                }
            }
        }

        return result;
    }
}
