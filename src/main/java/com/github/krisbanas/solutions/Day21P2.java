package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Day21P2 {

    public static void main(String[] args) {
        new Day21P2();
    }

    public Day21P2() {
        System.out.println(part1());
        System.out.println(part2());
    }

    record Edge(String start, String end, String step) {}

    public Object part1() {
        String in = FileReader.readAsString("21.txt");
        String[] codes = in.split("\r\n");

        Map<String, List<Edge>> numpadEdges = buildNumpadGraph();
        Map<String, Map<String, Set<String>>> numpadPaths = findNumpadPaths(numpadEdges);

        List<Set<String>> pathsOnNumpad = findPathsOnNumpad(codes, numpadPaths);

        System.out.println("Path on numpad: " + pathsOnNumpad.get(0));

        Map<String, List<Edge>> dirEdges = buildDirGraph();
        Map<String, Map<String, Set<String>>> dirPaths = findDirPaths(dirEdges);

        List<Set<String>> results = new ArrayList<>(pathsOnNumpad);
        int iterations = 2;

        for (int iter = 0; iter < iterations; iter++) {
            List<Set<String>> afterIteration = new ArrayList<>();

            for (Set<String> pathsForSingleEntry : results) {
                List<Set<String>> one = findPathsOnDir(pathsForSingleEntry.stream().toList(), dirPaths);
                List<String> consolidated = one.stream().flatMap(Collection::stream).distinct().toList();
                Set<String> shortest = findBest(consolidated);
                afterIteration.add(shortest);
            }

            results = afterIteration;
        }

        List<Integer> lengths = new ArrayList<>();
        for (Set<String> pathsForSingleEntry : results) {
            lengths.add(pathsForSingleEntry.stream().findAny().get().length());
        }

        List<Integer> multiples = new ArrayList<>();
        for (String code : codes) {
            var xd = code.substring(0, 3);
            multiples.add(Integer.parseInt(xd));
        }

        long restul = 0;
        for (int i = 0; i < codes.length; i++) {
            restul += (long) multiples.get(i) * lengths.get(i);
        }

        return restul;
    }

    private Set<String> findShortest(List<String> consolidated) {
        int shortest = Integer.MAX_VALUE;
        for (String consolidatedLine : consolidated) {
            shortest = Math.min(shortest, consolidatedLine.length());
        }
        Set<String> result = new HashSet<>();
        for (String s : consolidated) {
            if (s.length() == shortest) result.add(s);
        }
        return result;
    }

    private Set<String> findBest(List<String> consolidated) {
        // shortest
        int shortest = Integer.MAX_VALUE;
        for (String consolidatedLine : consolidated) {
            shortest = Math.min(shortest, consolidatedLine.length());
        }
        Set<String> shortestLengths = new HashSet<>();
        for (String s : consolidated) {
            if (s.length() == shortest) shortestLengths.add(s);
        }

        // repetition
        Set<String> bestRepeated = new HashSet<>();
        int repeatedScore = 0;
        for (String s : shortestLengths) {
            repeatedScore = Math.max(repeatedScore, getRepeatScore(s));
        }
        for (String s : shortestLengths) {
            if (getRepeatScore(s) == repeatedScore) bestRepeated.add(s);
        }



        // hori
        return bestRepeated;
    }

    private static int getRepeatScore(String str) {
        if (str == null || str.length() <= 1) {
            return 0;
        }

        int totalCount = 0;
        int currentCount = 1;

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                currentCount++;
            } else {
                if (currentCount > 1) {
                    totalCount += currentCount;
                }
                currentCount = 1;
            }
        }

        // Don't forget to add the last group
        if (currentCount > 1) {
            totalCount += currentCount;
        }

        return totalCount;
    }

    private static List<Set<String>> findPathsOnDir(List<String> codes, Map<String, Map<String, Set<String>>> dirPaths) {
        List<Set<String>> results = new ArrayList<>();
        for (String code : codes) {
            String start = "A";
            Set<String> pathsOnNumpadBuilder = Set.of("");
            for (String end : code.split("")) {
                Set<String> possiblePaths = dirPaths.get(start).get(end);
                Set<String> pathsSoFar = pathsOnNumpadBuilder;
                Set<String> newPaths = new HashSet<>();
                for (String path : pathsSoFar) {
                    for (String possiblePath : possiblePaths) {
                        newPaths.add(path + possiblePath + "A");
                    }
                }
                pathsOnNumpadBuilder = newPaths;
                start = end;
            }
            results.add(pathsOnNumpadBuilder);
        }
        return results;
    }

    private static List<Set<String>> findPathsOnNumpad(String[] codes, Map<String, Map<String, Set<String>>> numpadPaths) {
        List<Set<String>> results = new ArrayList<>();
        for (String code : codes) {
            String start = "A";
            Set<String> pathsOnNumpadBuilder = Set.of("");
            for (String end : code.split("")) {
                Set<String> possiblePaths = numpadPaths.get(start).get(end);
                Set<String> pathsSoFar = pathsOnNumpadBuilder;
                Set<String> newPaths = new HashSet<>();
                for (String path : pathsSoFar) {
                    for (String possiblePath : possiblePaths) {
                        newPaths.add(path + possiblePath + "A");
                    }
                }
                pathsOnNumpadBuilder = newPaths;
                start = end;
            }
            results.add(pathsOnNumpadBuilder);
        }
        return results;
    }

    private Map<String, Map<String, Set<String>>> findNumpadPaths(Map<String, List<Edge>> edges) {
        Map<String, Map<String, Set<String>>> numpadPaths = new HashMap<>();
        String[] keys = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A"};

        for (String key : keys) {
            Map<String, Set<String>> pathsForKey = new HashMap<>();
            pathsForKey.put(key, new HashSet<>(Set.of("")));
            Queue<String> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(key);
            while (!queue.isEmpty()) { // layers
                String cur = queue.poll(); // what am I?
                if (visited.contains(cur)) continue;
                visited.add(cur);
                Set<String> pathsSoFar = pathsForKey.get(cur); // how did I get here from "key"?
                for (String pathSoFar : pathsSoFar) { // for each path I came here by
                    List<Edge> edgesForKey = edges.get(cur);
                    for (Edge edge : edgesForKey) { // take all my neighbours, for each neighbour
                        if (!pathsForKey.containsKey(edge.end)) pathsForKey.put(edge.end, new HashSet<>());
                        pathsForKey.get(edge.end).add(pathSoFar + edge.step);
                        if (!visited.contains(edge.end)) queue.offer(edge.end);
                    }
                }
            }
            // trim!
            for (String k : pathsForKey.keySet()) {
                Set<String> candidates = pathsForKey.get(k);
                // Find min length
                int minLength = candidates.stream()
                        .mapToInt(String::length)
                        .min()
                        .getAsInt();

                candidates.removeIf(s -> s.length() > minLength);
            }
            numpadPaths.put(key, pathsForKey);

        }
        return numpadPaths;
    }

    private Map<String, Map<String, Set<String>>> findDirPaths(Map<String, List<Edge>> edges) {
        Map<String, Map<String, Set<String>>> dirPaths = new HashMap<>();
        String[] keys = new String[]{"<", ">", "v", "^", "A"};

        for (String key : keys) {
            Map<String, Set<String>> pathsForKey = new HashMap<>();
            pathsForKey.put(key, new HashSet<>(Set.of("")));
            Queue<String> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(key);
            while (!queue.isEmpty()) { // layers
                String cur = queue.poll(); // what am I?
                if (visited.contains(cur)) continue;
                visited.add(cur);
                Set<String> pathsSoFar = pathsForKey.get(cur); // how did I get here from "key"?
                for (String pathSoFar : pathsSoFar) { // for each path I came here by
                    List<Edge> edgesForKey = edges.get(cur);
                    for (Edge edge : edgesForKey) { // take all my neighbours, for each neighbour
                        if (!pathsForKey.containsKey(edge.end)) pathsForKey.put(edge.end, new HashSet<>());
                        pathsForKey.get(edge.end).add(pathSoFar + edge.step);
                        if (!visited.contains(edge.end)) queue.offer(edge.end);
                    }
                }
            }
            // trim!
            for (String k : pathsForKey.keySet()) {
                Set<String> candidates = pathsForKey.get(k);
                // Find min length
                int minLength = candidates.stream()
                        .mapToInt(String::length)
                        .min()
                        .getAsInt();

                candidates.removeIf(s -> s.length() > minLength);
            }
            dirPaths.put(key, pathsForKey);

        }
        return dirPaths;
    }

    private Map<String, List<Edge>> buildNumpadGraph() {
        Map<Point, String> pointToNum = new HashMap<>();
        pointToNum.put(new Point(2, 0), "1");
        pointToNum.put(new Point(2, 1), "2");
        pointToNum.put(new Point(2, 2), "3");
        pointToNum.put(new Point(1, 0), "4");
        pointToNum.put(new Point(1, 1), "5");
        pointToNum.put(new Point(1, 2), "6");
        pointToNum.put(new Point(0, 0), "7");
        pointToNum.put(new Point(0, 1), "8");
        pointToNum.put(new Point(0, 2), "9");
        pointToNum.put(new Point(3, 1), "0");
        pointToNum.put(new Point(3, 2), "A");

        Map<String, Point> numToPoint = new HashMap<>();
        numToPoint.put("1", new Point(2, 0));
        numToPoint.put("2", new Point(2, 1));
        numToPoint.put("3", new Point(2, 2));
        numToPoint.put("4", new Point(1, 0));
        numToPoint.put("5", new Point(1, 1));
        numToPoint.put("6", new Point(1, 2));
        numToPoint.put("7", new Point(0, 0));
        numToPoint.put("8", new Point(0, 1));
        numToPoint.put("9", new Point(0, 2));
        numToPoint.put("0", new Point(3, 1));
        numToPoint.put("A", new Point(3, 2));


        Map<String, List<Edge>> edges = new HashMap<>();
        String[] keys = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A"};
        for (String key : keys) {
            List<Edge> edgesForKey = new ArrayList<>();
            Point keyPoint = numToPoint.get(key);

            Point up = new Point(keyPoint.row() - 1, keyPoint.col());
            if (pointToNum.containsKey(up)) {
                edgesForKey.add(new Edge(key, pointToNum.get(up), "^"));
            }

            Point down = new Point(keyPoint.row() + 1, keyPoint.col());
            if (pointToNum.containsKey(down)) {
                edgesForKey.add(new Edge(key, pointToNum.get(down), "v"));
            }

            Point left = new Point(keyPoint.row(), keyPoint.col() - 1);
            if (pointToNum.containsKey(left)) {
                edgesForKey.add(new Edge(key, pointToNum.get(left), "<"));
            }

            Point right = new Point(keyPoint.row(), keyPoint.col() + 1);
            if (pointToNum.containsKey(right)) {
                edgesForKey.add(new Edge(key, pointToNum.get(right), ">"));
            }
            edges.put(key, edgesForKey);
        }
        return edges;
    }

    private Map<String, List<Edge>> buildDirGraph() {
        Map<Point, String> pointToNum = new HashMap<>();
        pointToNum.put(new Point(1, 0), "<");
        pointToNum.put(new Point(1, 1), "v");
        pointToNum.put(new Point(1, 2), ">");
        pointToNum.put(new Point(0, 1), "^");
        pointToNum.put(new Point(0, 2), "A");

        Map<String, Point> numToPoint = new HashMap<>();
        numToPoint.put("<", new Point(1, 0));
        numToPoint.put("v", new Point(1, 1));
        numToPoint.put(">", new Point(1, 2));
        numToPoint.put("^", new Point(0, 1));
        numToPoint.put("A", new Point(0, 2));

        Map<String, List<Edge>> edges = new HashMap<>();
        String[] keys = new String[]{"<", ">", "v", "^", "A"};
        for (String key : keys) {
            List<Edge> edgesForKey = new ArrayList<>();
            Point keyPoint = numToPoint.get(key);

            Point up = new Point(keyPoint.row() - 1, keyPoint.col());
            if (pointToNum.containsKey(up)) {
                edgesForKey.add(new Edge(key, pointToNum.get(up), "^"));
            }

            Point down = new Point(keyPoint.row() + 1, keyPoint.col());
            if (pointToNum.containsKey(down)) {
                edgesForKey.add(new Edge(key, pointToNum.get(down), "v"));
            }

            Point left = new Point(keyPoint.row(), keyPoint.col() - 1);
            if (pointToNum.containsKey(left)) {
                edgesForKey.add(new Edge(key, pointToNum.get(left), "<"));
            }

            Point right = new Point(keyPoint.row(), keyPoint.col() + 1);
            if (pointToNum.containsKey(right)) {
                edgesForKey.add(new Edge(key, pointToNum.get(right), ">"));
            }
            edges.put(key, edgesForKey);
        }
        return edges;
    }

    public Object part2() {
        return null;
    }
}
