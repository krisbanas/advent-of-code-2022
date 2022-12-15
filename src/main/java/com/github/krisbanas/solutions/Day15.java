package com.github.krisbanas.solutions;


import com.github.krisbanas.toolbox.Direction;
import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 {

    public Day15() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        List<Pair> pairs = getPairs();
        List<Point> takenPoints = getOccupiedPoints(pairs);

        int left = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToInt(Point::col).min().getAsInt();
        int right = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToInt(Point::col).max().getAsInt();

        int count = 0;
        for (int i = left - 1000000; i <= right + 1000000; i++) {
            Point pointer = new Point(2000000, i);
            if (takenPoints.contains(pointer)) continue;
            if (pairs.stream().anyMatch(x -> isBlockedBy(pointer, x))) {
                count++;
            }
        }

        return count;
    }

    public Object part2() {
        List<Pair> pairs = getPairs();
        List<Point> takenPoints = getOccupiedPoints(pairs);

        var edges = pairs.stream().map(Pair::getEdge).flatMap(Collection::stream).collect(Collectors.toSet());

        for (Point p : edges) {
            if (p.row() < 0 || p.row() > 4000000 || p.col() < 0 || p.col() > 4000000) continue;
            if (takenPoints.contains(p)) continue;
            if (pairs.stream().anyMatch(x -> isBlockedBy(p, x))) continue;
            return 4000000L * p.col() + p.row();
        }
        return 0;
    }

    private static List<Pair> getPairs() {
        return Arrays.stream(FileReader.readAsString("Day15Input.txt").trim().split("\n"))
                .map(x -> x.split("[= ,:]"))
                .map(x -> new Pair(new Point(Integer.parseInt(x[6]), Integer.parseInt(x[3])), new Point(Integer.parseInt(x[16]), Integer.parseInt(x[13])),
                        getInfluence(new Point(Integer.parseInt(x[6]), Integer.parseInt(x[3])), new Point(Integer.parseInt(x[16]), Integer.parseInt(x[13])))))
                .toList();
    }

    record Pair(Point sensor, Point bacon, double influence) {
        List<Point> getEdge() {
            List<Point> result = new ArrayList<>();
            Point pointer = Direction.EAST.move(sensor, (int) (influence - 0.000000001) + 2);
            result.add(pointer);
            while (pointer.row() > sensor.row()) {
                pointer = Direction.NORTHWEST.move(pointer);
                result.add(pointer);
            }
            while (pointer.col() < sensor.col()) {
                pointer = Direction.SOUTHWEST.move(pointer);
                result.add(pointer);
            }
            while (pointer.row() < sensor.row()) {
                pointer = Direction.SOUTHEAST.move(pointer);
                result.add(pointer);
            }
            while (pointer.col() > sensor.col()) {
                pointer = Direction.NORTHEAST.move(pointer);
                result.add(pointer);
            }

            return result;
        }
    }

    private static List<Point> getOccupiedPoints(List<Pair> pairs) {
        return pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).toList();
    }

    private boolean isBlockedBy(Point test, Pair pair) {
        double testInf = getInfluence(test, pair.sensor);
        return testInf <= pair.influence;
    }

    public static double getInfluence(Point a, Point b) {
        return (Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col()));
    }
}
