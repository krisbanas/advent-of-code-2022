package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.Objects;

public class Day15 {

    private Point robot;

    public Day15() {
        System.out.println(part1());
        System.out.println(part2());
    }

    int SIZE;

    public Object part1() {
        String[] input = FileReader.readAsString("15.txt").split("\r\n\r\n");
        String[] inputMap = input[0].split("\r\n");
        String[][] map = new String[inputMap.length][inputMap[0].length()];

        robot = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            String[] row = inputMap[i].split("");
            for (int j = 0; j < row.length; j++) {
                map[i][j] = row[j];
                if (Objects.equals(map[i][j], "@")) robot = new Point(i, j);
            }
        }
        SIZE = map.length;

        String[] movement = input[1].replaceAll("\r\n", "").split("");

        //
        print(map, "");

        for (String move : movement) {
            switch (move) {
                case ">" -> { // right
                    // cut row
                    String[] vec = new String[SIZE];
                    for (int i = 0; i < SIZE; i++) {
                        vec[i] = map[robot.row()][i];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < SIZE; i++) {
                        map[robot.row()][i] = movedVec[i];
                        if (Objects.equals(map[robot.row()][i], "@")) {
                            robot = new Point(robot.row(), i);
                        }
                    }
                }
                case "<" -> { // left
                    String[] vec = new String[SIZE];
                    for (int i = 0; i < SIZE; i++) {
                        vec[i] = map[robot.row()][SIZE - i - 1];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < SIZE; i++) {
                        map[robot.row()][SIZE - i - 1] = movedVec[i];
                        if (Objects.equals(map[robot.row()][SIZE - i - 1], "@")) {
                            robot = new Point(robot.row(), SIZE - i - 1);
                        }
                    }
                }
                case "v" -> { // down
                    String[] vec = new String[SIZE];
                    for (int i = 0; i < SIZE; i++) {
                        vec[i] = map[i][robot.col()];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < SIZE; i++) {
                        map[i][robot.col()] = movedVec[i];
                        if (Objects.equals(map[i][robot.col()], "@")) {
                            robot = new Point(i, robot.col());
                        }
                    }
                }
                case "^" -> { // up
                    String[] vec = new String[SIZE];
                    for (int i = 0; i < SIZE; i++) {
                        vec[i] = map[SIZE - i - 1][robot.col()];
                    }

                    String[] movedVec = move(vec);

                    for (int i = 0; i < SIZE; i++) {
                        map[SIZE - i - 1][robot.col()] = movedVec[i];
                        if (Objects.equals(map[SIZE - i - 1][robot.col()], "@")) {
                            robot = new Point(SIZE - i - 1, robot.col());
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
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j].equals("O")) result += (100L * i + j);
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
        while (firstBlank < SIZE && !vec[firstBlank].equals(".")) {
            if (vec[firstBlank].equals("#")) return vec; // found a wall!
            firstBlank++;
        }

        if (firstBlank == SIZE || vec[firstBlank].equals("#")) { // no blank space found
            return vec;
        }

        int sentinel = firstBlank + 1;
        while (firstBlank > pointer) {
            movedVec[firstBlank] = vec[firstBlank - 1];
            firstBlank--;
        }
        movedVec[firstBlank] = ".";

        for (int i = sentinel; i < SIZE; i++) {
            movedVec[i] = vec[i];
        }

        return movedVec;
    }

    public Object part2() {
        return null;
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
