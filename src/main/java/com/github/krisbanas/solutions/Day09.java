package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;

import static java.lang.System.out;


public class Day09 {
    private final static int[][] DIRS = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public Day09() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        var moves = FileHelper.loadStringList("Day9Input.txt")
                .stream().map(x -> x.split(" "))
                .map(x -> new Move(x[0], Integer.parseInt(x[1])))
                .toList();

        int[][] matrix = new int[10000][10000];
        String[][] visited = new String[10000][10000];
        visited[5000][5000] = "#";

        printTable(visited);

        Point head = new Point(5000, 5000);
        Point prevHead;
        Point tail = new Point(5000, 5000);

        for (Move m : moves) {
            int[] dir = switch (m.dir) {
                case "L" -> DIRS[1];
                case "R" -> DIRS[0];
                case "U" -> DIRS[2];
                case "D" -> DIRS[3];
                default -> null;
            };
            for (int i = 0; i < m.length; i++) {
                // move head
                prevHead = new Point(head.row, head.col);
                head = new Point(head.row() + dir[0], head.col() + dir[1]);

                if (shouldMove(head, tail)) {
                    // move tail
                    tail = new Point(prevHead.row, prevHead.col);
                    visited[tail.row][tail.col] = "#";
                    printTable(visited);
                }
            }
        }

        return Arrays.stream(visited).flatMap(Arrays::stream).filter("#"::equals).count();
    }

    private boolean shouldMove(Point head, Point tail) {
        return Math.pow(head.row - tail.row, 2) + Math.pow(head.col - tail.col, 2) > 2;
    }

    private boolean part2() {
        var xd = FileHelper.loadStringList("Day9Input.txt");


        return false;
    }

    private void printTable(String[][] matrix) {
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                out.print("#".equals(matrix[i][j]) ? "#" : ".");
//            }
//            out.println();
//        }
//        out.println();
    }

    record Move(String dir, int length) {
    }

    public record Point(int row, int col) {
    }
}
