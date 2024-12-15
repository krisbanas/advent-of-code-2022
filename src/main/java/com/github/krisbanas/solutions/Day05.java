package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day05 {

    private final List<Integer[]> incorrect = new ArrayList<>();

    public Day05() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        long result = 0;
        String input = FileReader.readAsString("Day5Input.txt");
        String[] first = input.split("\r\n\r\n")[0].split("\r\n");
        List<Integer[]> rules = new ArrayList<>();
        for (String entry : first) {
            Integer[] rule = new Integer[2];
            rule[0] = Integer.valueOf(entry.split("\\|")[0]);
            rule[1] = Integer.valueOf(entry.split("\\|")[1]);
            rules.add(rule);
        }

        String[] second = input.split("\r\n\r\n")[1].split("\r\n");

        Map<Integer, List<Integer[]>> rulesByBefore = rules.stream().collect(Collectors.groupingBy(i -> i[0]));
        Map<Integer, List<Integer[]>> rulesByAfter = rules.stream().collect(Collectors.groupingBy(i -> i[1]));

        outer:
        for (String entry : second) {
            Integer[] pages = Arrays.stream(entry.split(",")).map(Integer::valueOf).toArray(Integer[]::new);
            List<Integer> pagesBefore = new ArrayList<>();
            List<Integer> pagesAfter = new ArrayList<>(Arrays.stream(pages).toList());

            for (int page : pages) {
                List<Integer> pagesThatShouldGoAfter = rulesByBefore.getOrDefault(page, Collections.emptyList())
                        .stream().map(x -> x[1]).toList();
                List<Integer> pagesThatShouldGoBefore = rulesByAfter.getOrDefault(page, Collections.emptyList())
                        .stream().map(x -> x[0]).toList();

                long violations = pagesThatShouldGoAfter.stream().filter(pagesBefore::contains).count();
                violations += pagesThatShouldGoBefore.stream().filter(pagesAfter::contains).count();
                pagesBefore.add(page);
                pagesAfter.remove(Integer.valueOf(page));

                if (violations != 0) {
                    incorrect.add(pages);
                    continue outer;
                }
            }

            result += pages[pages.length / 2];
        }

        return result;
    }

    public Object part2() {
        long result = 0;
        String input = FileReader.readAsString("Day5Input.txt");
        String[] first = input.split("\r\n\r\n")[0].split("\r\n");
        List<Integer[]> rules = new ArrayList<>();
        for (String entry : first) {
            Integer[] rule = new Integer[2];
            rule[0] = Integer.valueOf(entry.split("\\|")[0]);
            rule[1] = Integer.valueOf(entry.split("\\|")[1]);
            rules.add(rule);
        }
        Integer[][] prerequisites = rules.toArray(Integer[][]::new);
        for (Integer[] incorrectPages : incorrect) {
            int[] ordered = findOrder(incorrectPages, prerequisites);
            result += ordered[ordered.length / 2];
           adjacencyList = new HashMap<>();
           visited = new ArrayList<>();
            toVisit = new LinkedList<>();
        }

        return result;
    }

    private  Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
    private Map<Integer, Integer> inDegree;
    private  List<Integer> visited = new ArrayList<>();
    private  Queue<Integer> toVisit = new LinkedList<>();

    public int[] findOrder(Integer[] pages, Integer[][] prerequisites) {
        inDegree = new HashMap<Integer, Integer>();
        for (int page : pages) {
            inDegree.put(page, 0);
        }

        for (int page : pages) {
            adjacencyList.put(page, new ArrayList<>());
        }

        for (var prerequisite : prerequisites) {
            if (!adjacencyList.containsKey(prerequisite[0])) continue;
            if (!adjacencyList.containsKey(prerequisite[1])) continue;
            adjacencyList.get(prerequisite[0]).add(prerequisite[1]);
            inDegree.put(prerequisite[1], inDegree.get(prerequisite[1]) + 1);
        }

        initializeQueue();
        while (!toVisit.isEmpty()) {
            Integer nextVertex = toVisit.poll();
            visited.add(nextVertex);

            adjacencyList.get(nextVertex).stream()
                    .peek(i -> inDegree.put(i, inDegree.get(i) - 1))
                    .filter(i -> inDegree.get(i) == 0)
                    .forEach(toVisit::offer);
        }
        int[] resultArray = visited.stream().mapToInt(x -> x).toArray();
        return resultArray;
    }

    private void initializeQueue() {
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) toVisit.offer(page);
        }

    }

}
