package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day15P2 {

    private Point robot;

    public Day15P2() {
        System.out.println(part1());
    }

    int ROWS;
    int COLS;

    public Object part1() {
        String[] input = FileReader.readAsString("15p2.txt").split("\r\n\r\n");
        String[] inputMap = input[0].split("\r\n");
        String[][] map = new String[inputMap.length][inputMap[0].length() * 2];

        robot = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            String[] row = inputMap[i].split("");
            for (int j = 0; j < row.length; j += 1) {
                if (Objects.equals(row[j], "O")) {
                    map[i][2 * j] = "[";
                    map[i][2 * j + 1] = "]";
                } else {
                    map[i][2 * j] = row[j];
                    if (!row[j].equals("@")) {
                        map[i][2 * j + 1] = row[j];

                    } else {
                        map[i][2 * j + 1] = ".";
                        robot = new Point(i, 2 * j + 1);
                    }
                }
            }
        }
        ROWS = map.length;
        COLS = map[0].length;

        String[] movement = input[1].replaceAll("\r\n", "").split("");

        //
        print(map, "");

        for (String move : movement) {
            switch (move) {
                case ">" -> { // right
                    // cut row
                    String[] vec = new String[COLS];
                    for (int i = 0; i < COLS; i++) {
                        vec[i] = map[robot.row()][i];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < COLS; i++) {
                        map[robot.row()][i] = movedVec[i];
                        if (Objects.equals(map[robot.row()][i], "@")) {
                            robot = new Point(robot.row(), i);
                        }
                    }
                }
                case "<" -> { // left
                    String[] vec = new String[COLS];
                    for (int i = 0; i < COLS; i++) {
                        vec[i] = map[robot.row()][COLS - i - 1];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < COLS; i++) {
                        map[robot.row()][COLS - i - 1] = movedVec[i];
                        if (Objects.equals(map[robot.row()][COLS - i - 1], "@")) {
                            robot = new Point(robot.row(), COLS - i - 1);
                        }
                    }
                }
                case "v" -> { // down
                    int robotRow = robot.row();
                    String[][] toMove = new String[ROWS - robotRow][COLS];
                    for (int i = robotRow; i < ROWS; i++) {
                        for (int j = 0; j < COLS; j++) {
                            toMove[i - robotRow][j] = map[i][j];
                        }
                    }

                    String[][] movedVec = moveUpDown(toMove);

                    for (int i = robotRow; i < ROWS; i++) {
                        for (int j = 0; j < COLS; j++) {
                            map[i][j] = movedVec[i - robotRow][j];
                            if (Objects.equals(map[i][j], "@")) {
                                robot = new Point(i, j);
                            }
                        }
                    }
                }
                case "^" -> { // up
                    int robotRow = robot.row();
                    String[][] toMove = new String[robotRow + 1][COLS];
                    for (int i = 0; i <= robotRow; i++) {
                        for (int j = 0; j < COLS; j++) {
                            toMove[robotRow - i][j] = map[i][j];
                        }
                    }

                    String[][] movedVec = moveUpDown(toMove);

                    for (int i = 0; i <= robotRow; i++) {
                        for (int j = 0; j < COLS; j++) {
                            map[i][j] = movedVec[robotRow - i][j];
                            if (Objects.equals(map[i][j], "@")) {
                                robot = new Point(i, j);
                            }
                        }
                    }
                }
                default -> {
                    throw new IllegalArgumentException("wtf " + move);
                }
            }
//            print(map, move);
        }

        long result = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (map[i][j].equals("[")) result += (100L * i + j);
            }
        }

        return result;
    }

    private String[] move(String[] vec) {
        String[] movedVec = new String[vec.length];

        int pointer = 0;
        while (!vec[pointer].equals("@")) {
            movedVec[pointer] = vec[pointer];
            pointer++;
        }

        // pointer on robot.

        int firstBlank = pointer;
        while (firstBlank < vec.length && !vec[firstBlank].equals(".")) {
            if (vec[firstBlank].equals("#")) return vec; // found a wall!
            firstBlank++;
        }

        if (firstBlank == vec.length || vec[firstBlank].equals("#")) { // no blank space found
            return vec;
        }

        int sentinel = firstBlank + 1;
        while (firstBlank > pointer) {
            movedVec[firstBlank] = vec[firstBlank - 1];
            firstBlank--;
        }
        movedVec[firstBlank] = ".";

        for (int i = sentinel; i < vec.length; i++) {
            movedVec[i] = vec[i];
        }

        return movedVec;
    }

    private String[][] moveUpDown(String[][] map) {
        int[][] span = new int[map.length][map[0].length];

        for (int i = 0; i < map[0].length; i++) {
            if (map[0][i].equals("@")) {
                span[0][i] = 1;
            }
        }
        boolean canMove = true;
        boolean hasSpans = false;

        for (int i = 0; i < map.length; i++) {
            if (!canMove) return map;
            hasSpans = false;
            for (int j = 0; j < map[i].length; j++) {
                if (span[i][j] == 0) continue;
                // check
                if (map[i + 1][j].equals("#")) { // wall
                    return map;
                }
                if (map[i + 1][j].equals("[")) {
                    span[i + 1][j] = 1;
                    if (map[i + 1][j + 1].equals("#")) { // wall
                        return map;
                    }
                    span[i + 1][j + 1] = 1;
                    hasSpans = true;
                }
                if (map[i + 1][j].equals("]")) {
                    span[i + 1][j] = 1;
                    if (map[i + 1][j - 1].equals("#")) { // wall
                        return map;
                    }
                    span[i + 1][j - 1] = 1;
                    hasSpans = true;
                }
            }
            if (!hasSpans) {
                break;
            }
        }

        for (int i = map.length - 1; i > 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                if (span[i - 1][j] == 0) continue;
                map[i][j] = map[i - 1][j];
                map[i - 1][j] = ".";
            }
        }

        // move acc to span

        return map;
    }


    private void print(String[][] grid, String s) {
        System.out.println("Move " + s);
        System.out.println("Robot: " + robot.row() + " " + robot.col());
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
