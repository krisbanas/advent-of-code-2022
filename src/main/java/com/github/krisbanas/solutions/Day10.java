package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;

import java.util.*;

public class Day10 {

    public Day10() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String string = FileReader.readAsString("Day10Input.txt");
        NumGrid grid = new NumGrid(string, "\r\n", "", ".");

        Queue<List<Point>> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.grid.length; i++) {
            for (int j = 0; j < grid.grid[0].length; j++) {
                if (grid.grid[i][j] == 0) queue.offer(List.of(new Point(i, j)));
            }
        }

        int[][] dirs = new int[][]{
                new int[]{0, 1},
                new int[]{-1, 0},
                new int[]{0, -1},
                new int[]{1, 0},
        };

        List<List<Point>> results = new ArrayList<>();

        outer:
        while (!queue.isEmpty()) {
            List<Point> hike = queue.poll();
            List<Point> newHike = new ArrayList<>();
            for (Point point : hike) {
                long currentHeight = grid.getValue(point);
                if (currentHeight == 9) {
                    results.add(hike);
                    continue outer;
                }
                for (int[] dir : dirs) {
                    var candidate = new Point(point.row() + dir[0], point.col() + dir[1]);
                    if (!grid.isInGrid(candidate)) continue;
                    if (grid.getValue(candidate) == -1) continue;
                    long candHeight = grid.getValue(candidate);
                    if (candHeight - 1 == currentHeight) newHike.add(candidate);
                }

            }
            if (!newHike.isEmpty()) {
                queue.offer(newHike.stream().distinct().toList());
            }
        }

        int res = results.stream().mapToInt(List::size).sum();
        return res;
    }


    public Object part2() {
        String string = FileReader.readAsString("Day10Input.txt");
        NumGrid grid = new NumGrid(string, "\r\n", "", ".");

        Queue<List<Point>> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.grid.length; i++) {
            for (int j = 0; j < grid.grid[0].length; j++) {
                if (grid.grid[i][j] == 0) queue.offer(List.of(new Point(i, j)));
            }
        }

        int[][] dirs = new int[][]{
                new int[]{0, 1},
                new int[]{-1, 0},
                new int[]{0, -1},
                new int[]{1, 0},
        };

        List<List<Point>> results = new ArrayList<>();

        outer:
        while (!queue.isEmpty()) {
            List<Point> hike = queue.poll();
            List<Point> newHike = new ArrayList<>();
            for (Point point : hike) {
                long currentHeight = grid.getValue(point);
                if (currentHeight == 9) {
                    results.add(hike);
                    continue outer;
                }
                for (int[] dir : dirs) {
                    var candidate = new Point(point.row() + dir[0], point.col() + dir[1]);
                    if (!grid.isInGrid(candidate)) continue;
                    if (grid.getValue(candidate) == -1) continue;
                    long candHeight = grid.getValue(candidate);
                    if (candHeight - 1 == currentHeight) newHike.add(candidate);
                }

            }
            if (!newHike.isEmpty()) {
                queue.offer(newHike);
            }
        }

        int res = results.stream().mapToInt(List::size).sum();
        return res;
    }
}
