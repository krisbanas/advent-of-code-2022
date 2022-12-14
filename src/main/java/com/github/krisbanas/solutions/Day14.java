package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.Direction;
import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;

import java.util.*;
import java.util.stream.IntStream;

public class Day14 {

    private static final long ROCK = 1;
    private static final long SAND = 5;

    public Day14() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var input = FileReader.readAsStringList("Day14Input.txt");
        List<List<Point>> points = input.stream().map(x -> x.split(" -> "))
                .map(x -> Arrays.stream(x).map(p -> p.split(",")).map(p -> new Point(Integer.parseInt(p[0]) - 450, Integer.parseInt(p[1]))).toList())
                .toList();

        NumGrid numGrid = prepareGridForP1(points);
        List<Point> sandPoints = new ArrayList<>();
        Direction[] sandDirections = new Direction[]{Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHEAST};

        outer:
        while (true) {
            sandPoints.add(new Point(50, 0));
            List<Point> pointsToIterate = new ArrayList<>(sandPoints);
            for (Point point : pointsToIterate) {
                var moved = true;
                inner:
                while (moved) {
                    if (point.col() >= 179) break outer; // fell!
                    numGrid.setValue(point, SAND);

                    for (Direction dir : sandDirections) {
//                        if (dir == Direction.SOUTHEAST && numGrid.getValue(Direction.EAST.move(point)) != 0) continue;
//                        if (dir == Direction.SOUTHWEST && numGrid.getValue(Direction.WEST.move(point)) != 0) continue;
                        Point newSpot = dir.move(point);
                        if (numGrid.getValue(newSpot) == 0) {
                            numGrid.setValue(newSpot, SAND);
                            numGrid.setValue(point, 0);
                            sandPoints.add(newSpot);
                            sandPoints.remove(point);
                            point = newSpot;
                            continue inner;
                        }
                    }

                    moved = false;
                }
                // can't move!
                sandPoints.remove(point);
            }
        }
//        printGrid(numGrid);
        return numGrid.count(5) - 1;
    }

    public Object part2() {
        var input = FileReader.readAsStringList("Day14Input.txt");
        List<List<Point>> points = input.stream().map(x -> x.split(" -> "))
                .map(x -> Arrays.stream(x).map(p -> p.split(",")).map(p -> new Point(Integer.parseInt(p[0]) - 250, Integer.parseInt(p[1]))).toList())
                .toList();

        NumGrid numGrid = prepareGridForP2(points);
        Direction[] sandDirections = new Direction[]{Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHEAST};

        outer:
        while (true) {
            Point point = new Point(250, 0);

            var moved = true;
            boolean cantMove = true;
            for (Direction dir : sandDirections)
                if (numGrid.getValue(dir.move(point)) != SAND) cantMove = false;
            if (cantMove) break outer;
            inner:
            while (moved) {
                numGrid.setValue(point, SAND);


                for (Direction dir : sandDirections) {
                    Point newSpot = dir.move(point);
                    if (numGrid.getValue(newSpot) == 0) {
                        numGrid.setValue(newSpot, SAND);
                        numGrid.setValue(point, 0);
                        point = newSpot;
                        continue inner;
                    }
                }
                moved = false;
            }

            if (numGrid.getValue(point) > 1000) break outer;
        }
        printGrid(numGrid);
        return numGrid.count(5) + 1;
    }

    private static NumGrid prepareGridForP1(List<List<Point>> points) {
        var numGrid = NumGrid.ofSize(100, 180);
        for (var line : points) {
            for (int i = 0; i < line.size() - 1; i++) {
                var left = line.get(i);
                var right = line.get(i + 1);
                Direction drawingDir = left.findDirectionToTarget(right);
                while (!left.equals(drawingDir.move(right))) {
                    numGrid.setValue(left, ROCK); // 1 = rock
                    left = drawingDir.move(left);
                }
            }
        }
        return numGrid;
    }


    private static NumGrid prepareGridForP2(List<List<Point>> points) {
        var numGrid = NumGrid.ofSize(500, 180);
        for (var line : points) {
            for (int i = 0; i < line.size() - 1; i++) {
                var left = line.get(i);
                var right = line.get(i + 1);
                Direction drawingDir = left.findDirectionToTarget(right);
                while (!left.equals(drawingDir.move(right))) {
                    numGrid.setValue(left, ROCK); // 1 = rock
                    left = drawingDir.move(left);
                }
            }
        }

        int maxCol = points.stream().flatMap(Collection::stream).sorted(Comparator.comparingInt(x -> -x.col())).toList().get(0).col();

        int floorCol = maxCol + 2;

        IntStream.range(0, 500).mapToObj(x -> new Point(x, floorCol)).forEach(p -> numGrid.setValue(p, ROCK));

        return numGrid;
    }

    public void printGrid(NumGrid numGrid) {
        var grid = numGrid.grid;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                long val = grid[i][j];
                if (val == 0) System.out.print(".");
                if (val == 1) System.out.print("#");
                if (val == 5) System.out.print("o");
            }
            System.out.println();
        }
    }
}
