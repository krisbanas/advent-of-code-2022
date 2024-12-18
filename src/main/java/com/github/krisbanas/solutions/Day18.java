package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.*;

public class Day18 {

    public Day18() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        int SIZE = 71;
        List<String> input = FileReader.readAsStringList("18.txt");
        List<Point> moves = new ArrayList<>();
        for (int j = 0; j < 1024; j++) { // TODO
            String in = input.get(j);
            String[] i = in.split(",");
            int rowMov = Integer.parseInt(i[1]);
            int colMov = Integer.parseInt(i[0]);
            moves.add(new Point(rowMov, colMov));
        }

        return simulate(SIZE, moves);
    }

    public Object part2() {
        int size = 71;
        List<String> input = FileReader.readAsStringList("18.txt");
        List<Point> moves = new ArrayList<>();
        for (String in : input) {
            String[] i = in.split(",");
            int rowMov = Integer.parseInt(i[1]);
            int colMov = Integer.parseInt(i[0]);
            moves.add(new Point(rowMov, colMov));
        }

        int result = 2500; // A wild guess
        while (true) {
            System.out.println("Step: " + result);
            List<Point> currMoves = moves.subList(0, result);
            boolean exited = simulate(size, currMoves) != Integer.MAX_VALUE;
            if (!exited) break;
            result++; // No binary search needed if your Dijkstra is quick enough :)
        }

        System.out.println("Broke after: " + result);
        return "Breaking point: " + moves.get(result - 1).col() + "," + moves.get(result - 1).row();
    }

    private Integer simulate(int size, List<Point> moves) {
        String[][] grid = new String[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (moves.contains(new Point(row, col))) {
                    grid[row][col] = "#";
                } else
                    grid[row][col] = ".";
            }
        }

        Map<Point, Integer> distances = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distances.put(new Point(i, j), Integer.MAX_VALUE);
            }
        }

        distances.put(new Point(0, 0), 0);

        Set<Point> visited = new HashSet<>();
        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        pq.offer(new Point(0, 0));

        while (!pq.isEmpty()) {
            Point curr = pq.poll();
            if (visited.contains(curr)) continue;
            visited.add(curr);
            int cost = distances.get(curr);

            // left
            Point toLeft = new Point(curr.row(), curr.col() - 1);
            if (!visited.contains(toLeft) && curr.col() - 1 >= 0 && !grid[curr.row()][curr.col() - 1].equals("#")) {
                int minDistance = cost + 1 < distances.get(toLeft) ? cost + 1 : distances.get(toLeft);
                distances.put(toLeft, minDistance);
                pq.offer(toLeft);
            }

            // Right
            Point toRight = new Point(curr.row(), curr.col() + 1);
            if (!visited.contains(toRight) && curr.col() + 1 < size && !grid[curr.row()][curr.col() + 1].equals("#")) {
                int minDistance = cost + 1 < distances.get(toRight) ? cost + 1 : distances.get(toRight);
                distances.put(toRight, minDistance);
                pq.offer(toRight);
            }

            // Up
            Point toUp = new Point(curr.row() - 1, curr.col());
            if (!visited.contains(toUp) && curr.row() - 1 >= 0 && !grid[curr.row() - 1][curr.col()].equals("#")) {
                int minDistance = cost + 1 < distances.get(toUp) ? cost + 1 : distances.get(toUp);
                distances.put(toUp, minDistance);
                pq.offer(toUp);
            }

            // Down
            Point toDown = new Point(curr.row() + 1, curr.col());
            if (!visited.contains(toDown) && curr.row() + 1 < size && !grid[curr.row() + 1][curr.col()].equals("#")) {
                int minDistance = cost + 1 < distances.get(toDown) ? cost + 1 : distances.get(toDown);
                distances.put(toDown, minDistance);
                pq.offer(toDown);
            }
        }

        print(grid, distances);

        return distances.get(new Point(size - 1, size - 1));
    }

    private void print(String[][] grid, Map<Point, Integer> distances) {
        for (int i = 0; i < grid.length; i++) {
            String[] strings = grid[i];
            for (int j = 0; j < strings.length; j++) {
                String string = strings[j];
                if (distances.get(new Point(i, j)) != Integer.MAX_VALUE) System.out.print("O");
                else System.out.print(string);
            }
            System.out.println();
        }
        System.out.println();
    }
}
