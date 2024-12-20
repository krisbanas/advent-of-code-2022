package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class Day20P2 {

    public static final int CHEAT_SIZE = 20;

    public static void main(String[] args) {
        new Day20P2();
    }

    private final List<Point> movements = List.of(new Point(0, -1), new Point(0, 1), new Point(1, 0), new Point(-1, 0));

    public Day20P2() {
        System.out.println(part2());
    }

    public Object part2() {
        String[] input = FileReader.readAsString("20.txt").split("\r\n\r\n");
        String[] inputMap = input[0].split("\r\n");
        String[][] map = new String[inputMap.length][inputMap[0].length()];

        Point start = new Point(0, 0);
        Point end = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            String[] row = inputMap[i].split("");
            for (int j = 0; j < row.length; j++) {
                map[i][j] = row[j];
                if (Objects.equals(map[i][j], "E")) end = new Point(i, j);
                if (Objects.equals(map[i][j], "S")) start = new Point(i, j);
            }
        }


        int baseline = findBaselinePathLength(start, end, map);
        System.out.println("Baseline: " + baseline);

        Map<Integer, Integer> results = findPathLength(start, end, map, baseline);

        long result = 0;
        var sortedResults = results.keySet().stream().sorted().toList();
        for (int savedPicoseconds : sortedResults) {
            if (savedPicoseconds < 50) continue;
            result += results.get(savedPicoseconds);
            System.out.println("There are " + results.get(savedPicoseconds) + " cheats that save " + savedPicoseconds + " picoseconds.");
        }

        return result;
    }

    private Map<Integer, Integer> findPathLength(Point start, Point end, String[][] map, int baseline) {
        Queue<State3> queue = new LinkedList<>();
        queue.offer(new State3(start, 1, CHEAT_SIZE, "0"));
        Map<String, Set<Point>> visited = new HashMap<>();
        visited.put("0", new HashSet<>());
        Map<Integer, Integer> saveCount = new HashMap<>();
        Set<String> finished = new HashSet<>();
        Map<String, Integer> cheatStepsDecidedForMapId = new HashMap<>();

        while (!queue.isEmpty()) {
            State3 curr = queue.poll();
            Point currPoint = curr.point();
            int currLen = curr.len();
            String mapId = curr.mapId();
            if (finished.contains(mapId)) continue;
            visited.get(mapId).add(currPoint);

            for (Point move : movements) {
                Point newPoint = new Point(currPoint.row() + move.row(), currPoint.col() + move.col());
                if (newPoint.row() < 0 || newPoint.col() < 0 || newPoint.row() >= map.length || newPoint.col() >= map[0].length)
                    continue;
                if (visited.getOrDefault(mapId, new HashSet<>()).contains(newPoint)) continue;
                if (newPoint.equals(end)) {
                    if (finished.contains(mapId)) continue;
                    int saved = baseline - curr.len();
                    int savedCountSoFar = saveCount.getOrDefault(saved, 0);
                    saveCount.put(saved, savedCountSoFar + 1);
                    finished.add(mapId);
                }

                if (curr.cheatTimeout() == CHEAT_SIZE && map[newPoint.row()][newPoint.col()].equals("#")) { //start cheat
                    String newMapId = UUID.randomUUID().toString();
                    queue.offer(new State3(newPoint, currLen + 1, curr.cheatTimeout() - 1, newMapId));

                    var oldVisited = visited.get(mapId);
                    visited.put(newMapId, new HashSet<>());
                    visited.get(newMapId).add(currPoint);
                    visited.get(newMapId).addAll(oldVisited);

                    cheatStepsDecidedForMapId.put(newMapId, CHEAT_SIZE - (curr.cheatTimeout() - 1));
                } else if (curr.cheatTimeout() > 0 && map[newPoint.row()][newPoint.col()].equals("#")) {
                    int alreadyStepped = cheatStepsDecidedForMapId.get(curr.mapId());
                    if (alreadyStepped == CHEAT_SIZE - (curr.cheatTimeout() - 1)) { // already stepped -> fork!
                        String newMapId = UUID.randomUUID().toString();
                        queue.offer(new State3(newPoint, currLen + 1, curr.cheatTimeout() - 1, newMapId));

                        var oldVisited = visited.get(mapId);
                        visited.put(newMapId, new HashSet<>());
                        visited.get(newMapId).add(currPoint);
                        visited.get(newMapId).addAll(oldVisited);

                        cheatStepsDecidedForMapId.put(newMapId, CHEAT_SIZE - (curr.cheatTimeout() - 1));
                    } else { // not stepped yet! :)
                        queue.offer(new State3(newPoint, currLen + 1, curr.cheatTimeout() - 1, curr.mapId()));
                        cheatStepsDecidedForMapId.put(curr.mapId(), CHEAT_SIZE - (curr.cheatTimeout() - 1));
                    }
                } else {
                    int cheatTimeout = curr.cheatTimeout() == CHEAT_SIZE
                            ? CHEAT_SIZE
                            : curr.cheatTimeout() - 1;
                    queue.offer(new State3(newPoint, currLen + 1, cheatTimeout, mapId));
                    visited.get(mapId).add(newPoint);
                }
            }
        }
        return saveCount;
    }

    private int findBaselinePathLength(Point start, Point end, String[][] map) {
        Queue<State2> queue = new LinkedList<>();
        queue.offer(new State2(start, 1, CHEAT_SIZE));
        Set<Point> visited = new HashSet<>(); // add timeout?

        int shortestDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State2 curr = queue.poll();
            Point currPoint = curr.point();
            int currLen = curr.len();

            for (Point move : movements) {
                Point newPoint = new Point(currPoint.row() + move.row(), currPoint.col() + move.col());
                if (newPoint.row() < 0 || newPoint.col() < 0 || newPoint.row() >= map.length || newPoint.col() >= map[0].length)
                    continue;
                if (map[newPoint.row()][newPoint.col()].equals("#")) continue;
                if (visited.contains(newPoint)) continue;
                if (newPoint.equals(end)) {
                    return currLen;
                }
                queue.offer(new State2(newPoint, currLen + 1, curr.cheatTimeout() - 1));
                visited.add(newPoint);
            }
        }
        return shortestDistance;
    }

}

record VisitedForMap(Point point, String mapId) {}

record State3(Point point, int len, int cheatTimeout, String mapId) {}

record State2(Point point, int len, int cheatTimeout) {}
