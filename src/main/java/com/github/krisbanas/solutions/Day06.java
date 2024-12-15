package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.List;
import java.util.Objects;

public class Day06 {

    public Day06() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        List<String> input = FileReader.readAsStringList("Day6Input.txt");
        String[][] in = input.stream().map(x -> x.split("")).toArray(String[][]::new);
        int[][] visited = new int[in.length][in[0].length];

        int[] position = null;
        outer:
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (!in[i][j].equals("#") && !in[i][j].equals(".")) {
                    position = new int[]{i, j};
                    break outer;
                }
            }
        }

        boolean out = false;

        int dirPointer = 2;
        while (!out) {
            int[] direction = dirs[dirPointer];
            visited[position[0]][position[1]] = 1;
            position = new int[]{position[0] + direction[0], position[1] + direction[1]};
            if (
                    (position[0] == 0 && direction[0] == -1)
                            || (position[0] == in.length - 1 && direction[0] == 1)
                            || (position[1] == 0 && direction[1] == -1)
                            || (position[1] == in[0].length - 1 && direction[1] == 1)
            ) { // hit the block
                out = true;
                continue;
            }

            if (!in[position[0] + direction[0]][position[1] + direction[1]].equals("#")) {
                visited[position[0]][position[1]] = 1;
            } else {
                dirPointer--;
                if (dirPointer == -1) dirPointer = 3;
                direction = dirs[dirPointer];
            }
        }

        visited[position[0]][position[1]] = 1;
        long result = 0;
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                if (visited[i][j] == 1) result++;
            }
        }
        return result;
    }

    int[][] dirs = new int[][]{
            new int[]{1, 0},
            new int[]{0, 1},
            new int[]{-1, 0},
            new int[]{0, -1}
    };

    record Log(int val, int dirPointer) {
    }

    ;

    public boolean isThereParadox(String[][] in, Log[][] visited, int[] position) {
        boolean out = false;
        int stepa = 0;
        int dirPointer = 2;
        while (!out) {
            stepa++;
            if (stepa > 16900) return false;
            int[] direction = dirs[dirPointer];
            visited[position[0]][position[1]] = new Log(1, dirPointer);
            position = new int[]{position[0] + direction[0], position[1] + direction[1]};
            if (
                    (position[0] == 0 && direction[0] == -1)
                            || (position[0] == in.length - 1 && direction[0] == 1)
                            || (position[1] == 0 && direction[1] == -1)
                            || (position[1] == in[0].length - 1 && direction[1] == 1)
            ) { // hit the block
                out = true;
                continue;
            }

//            if (new Log(1, dirPointer).equals(visited[position[0]][position[1]])) {
//                return true;
//            }
//            else
                if (!in[position[0] + direction[0]][position[1] + direction[1]].equals("#")) {
                visited[position[0]][position[1]] = new Log(1, dirPointer);
            } else {
                dirPointer--;
                if (dirPointer == -1) dirPointer = 3;
                direction = dirs[dirPointer];
            }
            if (Objects.equals(visited[position[0] + direction[0]][position[1] + direction[1]], new Log(1, dirPointer))) return true;

        }


        return false;
    }

    public Object part2() {
        List<String> input = FileReader.readAsStringList("Day6Input.txt");
        String[][] in = input.stream().map(x -> x.split("")).toArray(String[][]::new);
        Log[][] visited = new Log[in.length][in[0].length];

        int[] position = null;
        outer:
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (!in[i][j].equals("#") && !in[i][j].equals(".")) {
                    position = new int[]{i, j};
                    break outer;
                }
            }
        }

        long counter = 0;

        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                System.out.print("Checking: " + i + " " + j);
                if (Objects.equals(in[i][j], ".")) in[i][j] = "#";
                boolean is = isThereParadox(in, visited, position);
                if (is) {
                    counter++;
                    System.out.println("  HIT!");
                } else {
                    System.out.println();
                }
                input = FileReader.readAsStringList("Day6Input.txt");
                in = input.stream().map(x -> x.split("")).toArray(String[][]::new);
                visited = new Log[in.length][in[0].length];
            }
        }

        // put shizzle
        return counter;
    }
}
