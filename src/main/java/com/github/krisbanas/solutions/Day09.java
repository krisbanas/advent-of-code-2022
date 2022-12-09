package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.out;
// gunwo

public class Day09 {
    private final static int[][] DIRS = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public Day09() {
//        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        var moves = FileHelper.loadStringList("Day9Input.txt")
                .stream().map(x -> x.split(" "))
                .map(x -> new Move(x[0], Integer.parseInt(x[1])))
                .toList();

        int[][] matrix = new int[5][6];
        String[][] visited = new String[5][6];
        visited[4][0] = "#";

        printTable(visited);

        Point head = new Point(4, 0);
        Point prevHead = new Point(4, 0);
        Point tail = new Point(4, 0);

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

        return Arrays.stream(visited).flatMap(Arrays::stream).filter(x -> "#".equals(x)).count();
    }

    private boolean shouldMove(Point head, Point tail) {
        return Math.pow(head.row - tail.row, 2) + Math.pow(head.col - tail.col, 2) > 2;
    }

    private Object part2() {
        var moves = FileHelper.loadStringList("Day9Input.txt")
                .stream().map(x -> x.split(" "))
                .map(x -> new Move(x[0], Integer.parseInt(x[1])))
                .toList();

        Point head = new Point(0, 0);
        List<Point> rope = new ArrayList<>(IntStream.iterate(0, i -> i + 1).limit(10).mapToObj(i -> new Point(0, 0)).toList());
        rope.set(0, head);
        Set<Point> visited = new HashSet<>();

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
                head = new Point(head.row() + dir[0], head.col() + dir[1]);
                rope.set(0, head);
                // move the rest
                for (int j = 0; j < rope.size() - 1; j++) {
                    Point pulling = rope.get(j);
                    Point follower = rope.get(j + 1);
                    Point newPoint = moveFollower(pulling, follower);
                    rope.set(j + 1, newPoint);
                }

                visited.add(rope.get(9));
            }
        }

        return null;
    }

    private Point moveFollower(Point pulling, Point follower) {
        if (pulling.equals(follower)) return follower;
        if (!shouldMove(pulling, follower)) return follower;
        if (pulling.row > follower.row && pulling.col == follower.col) {
            return new Point(follower.row + 1, follower.col);
        } else if (pulling.row < follower.row && pulling.col == follower.col) {
            return new Point(follower.row - 1, follower.col);
        } else if (pulling.row == follower.row && pulling.col > follower.col) {
            return new Point(follower.row, follower.col + 1);
        } else if (pulling.row == follower.row && pulling.col < follower.col) {
            return new Point(follower.row, follower.col - 1);
        } else if (pulling.row > follower.row && pulling.col > follower.col) {
            return new Point(follower.row + 1, follower.col + 1);
        } else if (pulling.row < follower.row && pulling.col < follower.col) {
            return new Point(follower.row - 1, follower.col - 1);
        } else if (pulling.row < follower.row && pulling.col > follower.col) {
            return new Point(follower.row - 1, follower.col + 1);
        } else if (pulling.row > follower.row && pulling.col < follower.col) {
            return new Point(follower.row + 1, follower.col - 1);
        } else throw new IllegalStateException();
    }

    private void printTable(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out.print("#".equals(matrix[i][j]) ? "#" : ".");
            }
            out.println();
        }
        out.println();
    }

    record Move(String dir, int length) {
    }

    public record Point(int row, int col) {
    }
}
