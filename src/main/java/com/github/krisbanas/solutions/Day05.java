package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.krisbanas.Main.ROW_PATTERN;

public class Day05 {

    public static void main(String[] args) {
        new Day05();
    }

    private final List<String[]> incorrectPagesList = new ArrayList<>();

    public Day05() {
        long startTime = System.nanoTime();
        long part1Result = part1();
        long part1Time = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long part2Result = part2();
        long part2Time = System.nanoTime() - startTime;

        System.out.printf(ROW_PATTERN, 5, part1Result, part1Time / 1_000_000.0, part2Result, part2Time / 1_000_000.0);
    }

    public long part1() {
        String[] input = FileReader.readAsString("5.txt").split("\r\n\r\n");
        String[] firstPart = input[0].split("\r\n");

        List<Rule> rules = new ArrayList<>();
        for (String entry : firstPart) {
            String[] split = entry.split("\\|");
            rules.add(new Rule(split[0], split[1]));
        }

        Map<String, List<String>> pagesByPreceding = rules.stream().collect(Collectors.toMap(
                Rule::pre,
                x -> List.of(x.post()),
                (a, b) -> Stream.of(a, b).flatMap(Collection::stream).toList()
        ));
        Map<String, List<String>> pagesBySucceeding = rules.stream().collect(Collectors.toMap(
                Rule::post,
                x -> List.of(x.pre()),
                (a, b) -> Stream.of(a, b).flatMap(Collection::stream).toList()
        ));

        long result = 0;
        String[] pageEntries = input[1].split("\r\n");
        outer:
        for (String entry : pageEntries) {
            String[] pages = Arrays.stream(entry.split(",")).toArray(String[]::new);
            List<String> pagesBefore = new ArrayList<>();
            List<String> pagesAfter = new ArrayList<>(Arrays.stream(pages).toList());

            for (String page : pages) {
                List<String> pagesThatShouldGoAfter = pagesByPreceding.get(page);
                List<String> pagesThatShouldGoBefore = pagesBySucceeding.get(page);

                long violations = pagesThatShouldGoAfter.stream().filter(pagesBefore::contains).count();
                violations += pagesThatShouldGoBefore.stream().filter(pagesAfter::contains).count();
                pagesBefore.add(page);
                pagesAfter.remove(page);

                if (violations != 0) {
                    incorrectPagesList.add(pages);
                    continue outer;
                }
            }

            result += Integer.parseInt(pages[pages.length / 2]);
        }

        return result;
    }

    public long part2() {
        long result = 0;
        String input = FileReader.readAsString("5.txt");
        String[] firstPart = input.split("\r\n\r\n")[0].split("\r\n");
        List<Rule> rules = new ArrayList<>();
        for (String entry : firstPart) {
            String[] split = entry.split("\\|");
            rules.add(new Rule(split[0], split[1]));
        }

        for (String[] pages : incorrectPagesList) {
            String[] ordered = findOrder(pages, rules);
            result += Integer.parseInt(ordered[ordered.length / 2]);
            adjacencyList = new HashMap<>();
            visited = new ArrayList<>();
            toVisit = new LinkedList<>();
        }

        return result;
    }

    private Map<String, List<String>> adjacencyList = new HashMap<>();
    private Map<String, Integer> inDegree = new HashMap<>();
    private List<String> visited = new ArrayList<>();
    private Queue<String> toVisit = new LinkedList<>();

    // Kahn's
    private String[] findOrder(String[] pages, List<Rule> rules) {
        inDegree = new HashMap<>();
        for (String page : pages) {
            inDegree.put(page, 0);
        }

        for (String page : pages) {
            adjacencyList.put(page, new ArrayList<>());
        }

        for (var rule : rules) {
            if (!adjacencyList.containsKey(rule.pre())) continue;
            if (!adjacencyList.containsKey(rule.post())) continue;
            adjacencyList.get(rule.pre()).add(rule.post());
            inDegree.put(rule.post(), inDegree.get(rule.post()) + 1);
        }

        initializeQueue();
        while (!toVisit.isEmpty()) {
            String nextVertex = toVisit.poll();
            visited.add(nextVertex);

            adjacencyList.get(nextVertex).stream()
                    .peek(i -> inDegree.put(i, inDegree.get(i) - 1))
                    .filter(i -> inDegree.get(i) == 0)
                    .forEach(toVisit::offer);
        }
        return visited.toArray(String[]::new);
    }

    private void initializeQueue() {
        for (String page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) toVisit.offer(page);
        }
    }

    private record Rule(String pre, String post) {}
}
