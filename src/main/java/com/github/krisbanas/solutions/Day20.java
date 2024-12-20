package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Day20 {

    public static final int CHEAT_SIZE = 2;

    public static void main(String[] args) {
        new Day20();
    }

    private final List<Point> movements = List.of(new Point(0, -1), new Point(0, 1), new Point(1, 0), new Point(-1, 0));

    public Day20() {
        System.out.println(part1());
//        System.out.println(part2());
    }

    public Object part1() {
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

        print(map);

        // let's get the baseline
        int baseline = findPathLength(start, end, map);
        System.out.println("Baseline: " + baseline);

        Map<Integer, Integer> saved = new HashMap<>();

        // find hashes
        for (int i = 0; i < map.length - 1; i++) {
            for (int j = 0; j < map[i].length - 1; j++) {
                if (Objects.equals(map[i][j], "#")) {
//                    if (i > 1 && map[i - 1][j].equals("#")) {
                        String[][] newMap = new String[map.length][map[i].length];
                        copyArrays(map, newMap);
                        newMap[i][j] = ".";
//                        newMap[i - 1][j] = "1";
                        int save = baseline - findPathLength(start, end, newMap);
                        if (save > 99) {
//                            System.out.println("Saved: " + save);
//                            print(newMap);
                            saved.put(save, saved.getOrDefault(save, 0) + 1);
                        }
//                    }
//                    if (j > 1 && map[i][j - 1].equals("#")) {
//                        String[][] newMap = new String[map.length][map[i].length];
//                        copyArrays(map, newMap);
//                        newMap[i][j] = "1";
//                        newMap[i][j - 1] = "1";
//                        int save = baseline - findPathLength(start, end, newMap);
//                        if (save > 60) {
//                            System.out.println("Saved: " + save);
//                            print(newMap);
//                            saved.put(save, saved.getOrDefault(save, 0) + 1);
//                        }
//                    }
                }
            }
        }
        long result = 0;
//        var k = saved.keySet().stream().sorted().toList();
        for (int a : saved.keySet()) {
            result  += saved.get(a);
//            if (a >= 100) result  += r;
//            System.out.println("There are " + saved.get(a) + " cheats that save " + a + " picoseconds.");
        }


        return result;
    }

    private void copyArrays(String[][] map, String[][] newMap) {
        for (int i = 0; i < map.length; i++) {
            newMap[i] = Arrays.copyOf(map[i], map[i].length);
        }
    }

    private int findPathLength(Point start, Point end, String[][] map) {
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(start, 1, CHEAT_SIZE));
        Set<Point> visited = new HashSet<>(); // add timeout?

        int shortestDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State curr = queue.poll();
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
                queue.offer(new State(newPoint, currLen + 1, curr.cheatTimeout() - 1));

//                if (curr.cheatTimeout() > 0 && map[newPoint.row()][newPoint.col()].equals("1")) {
//                    queue.offer(new State(newPoint, currLen + 1, curr.cheatTimeout() - 1));
//                }
//                else {
//                    int cheatTimeout = curr.cheatTimeout() == CHEAT_SIZE
//                            ? CHEAT_SIZE
//                            : curr.cheatTimeout() - 1;
//                    queue.offer(new State(newPoint, currLen + 1, cheatTimeout));
//                }
                visited.add(newPoint);
            }
        }
        return shortestDistance;
    }

    private void print(String[][] grid) {
        for (String[] strings : grid) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
        System.out.println();
    }


    public Object part2() {
        return null;
    }
}


record State(Point point, int len, int cheatTimeout) {}
