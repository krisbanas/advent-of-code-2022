package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    public Day14() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        int rows = 103;
        int cols = 101;
        int TIMES = 1000;
        List<Robot> robots = new ArrayList<>();
        List<String> input = FileReader.readAsStringList("14.txt");
        for (String line : input) {
            line = line.replaceAll("p=", "").replaceAll(" v=", ",");
            String[] split = line.split(",");
            Robot robot = new Robot(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[0])), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
            robots.add(robot);
        }

        int[][] grid = new int[rows][cols];
        for (Robot robot : robots) {
            grid[robot.pos.row()][robot.pos.col()] += 1;
        }
        print(grid, 0);

        for (int i = 0; i < TIMES; i++) {
            System.out.println("Simulating time: " + (i + 1));
            int[][] newGrid = new int[rows][cols];
            List<Robot> newRobots = new ArrayList<>();
            for (Robot robot : robots) {
                Point pos = robot.pos;
                int newRow = (pos.row() + robot.rowVel());
                if (newRow > rows - 1) newRow %= (rows);
                if (newRow < 0) newRow = (rows + newRow);

                int newCol = (pos.col() + robot.colVel());
                if (newCol > cols - 1) newCol %= (cols);
                if (newCol < 0) newCol = (cols + newCol);

                Robot newRobot = new Robot(new Point(newRow, newCol), robot.colVel(), robot.rowVel());
                newRobots.add(newRobot);
                newGrid[newRobot.pos.row()][newRobot.pos.col()] += 1;
            }
            grid = newGrid;
            robots = newRobots;
            print(grid, i + 1);
        }

        // count
        List<Point> quadStart = List.of(
                new Point(0, 0),
                new Point(rows / 2 + 1, 0),
                new Point(0, cols / 2 + 1),
                new Point(rows / 2 + 1, cols / 2 + 1)
        );

        List<Integer> results = new ArrayList<>();
        for (Point point : quadStart) {
            int rowStart = point.row();
            int colStart = point.col();
            int quadResult = 0;
            for (int row = 0; row < rows / 2; row++) {
                for (int col = 0; col < cols / 2; col++) {
                    quadResult += grid[row + rowStart][col + colStart];
                }
            }
            results.add(quadResult);
        }

        long res = 1;
        for (int result : results) {
            res *= result;
        }
        return res;
    }

    public Object part2() {
        // test
        int[][] myGrid = new int[][]{
                new int[]{0, 1, 1, 1, 0},
                new int[]{0, 1, 1, 1, 0},
                new int[]{0, 1, 1, 1, 0},
                new int[]{1, 1, 1, 1, 2},
        };
        print(myGrid, 0);
        System.out.println(checkChristmasTree(myGrid));


        myGrid = new int[][]{
                new int[]{1, 2, 1, 1, 2},
                new int[]{0, 1, 5, 1, 0},
                new int[]{0, 1, 8, 1, 0},
                new int[]{0, 1, 5, 1, 0},
                new int[]{1, 2, 1, 1, 2},
        };
        print(myGrid, 0);
        System.out.println(checkChristmasTree(myGrid));


        int rows = 103;
        int cols = 101;
        int TIMES = 1000000;
        List<Robot> robots = new ArrayList<>();
        List<String> input = FileReader.readAsStringList("14.txt");
        for (String line : input) {
            line = line.replaceAll("p=", "").replaceAll(" v=", ",");
            String[] split = line.split(",");
            Robot robot = new Robot(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[0])), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
            robots.add(robot);
        }

        int[][] grid = new int[rows][cols];
        for (Robot robot : robots) {
            grid[robot.pos.row()][robot.pos.col()] += 1;
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i % 100000 == 0) System.out.println("On itaration: " + i);
            int[][] newGrid = new int[rows][cols];
            List<Robot> newRobots = new ArrayList<>();
            for (Robot robot : robots) {
                Point pos = robot.pos;
                int newRow = (pos.row() + robot.rowVel());
                if (newRow > rows - 1) newRow %= (rows);
                if (newRow < 0) newRow = (rows + newRow);

                int newCol = (pos.col() + robot.colVel());
                if (newCol > cols - 1) newCol %= (cols);
                if (newCol < 0) newCol = (cols + newCol);

                Robot newRobot = new Robot(new Point(newRow, newCol), robot.colVel(), robot.rowVel());
                newRobots.add(newRobot);
                newGrid[newRobot.pos.row()][newRobot.pos.col()] += 1;

            }
            grid = newGrid;
            robots = newRobots;
            if (checkChristmasTree(grid)) {
                print(grid, i + 1);
            }
        }

        return null;

    }

    private boolean checkChristmasTree(int[][] grid) {
        int count = 0;
        for (int i = 1; i < grid.length - 1; i++) { // hope it's in the midle
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (grid[i][j] > 0 && grid[i - 1][j] > 0 && grid[i + 1][j] > 0 && grid[i][j - 1] > 0 && grid[i][j + 1] > 0)
                    count++;
            }
        }
        return count > 15;
    }

    /**
     * Najpierw col potem row
     */
    record Robot(Point pos, int colVel, int rowVel) {
    }

    private void print(int[][] grid, int i) {
        System.out.println("Robots after iteration: " + i);
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int val = grid[row][col];
                System.out.print(val == 0 ? "." : val + "");
            }
            System.out.println();
        }
        System.out.println();
    }
}
