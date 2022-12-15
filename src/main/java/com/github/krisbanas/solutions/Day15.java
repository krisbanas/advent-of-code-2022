package com.github.krisbanas.solutions;


import com.github.krisbanas.toolbox.FileReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.github.krisbanas.toolbox.Extractor.ints;

public class Day15 {

    public Day15() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var pairs = Arrays.stream(FileReader.readAsString("Day15Input.txt").trim().split("\n"))
                .map(x -> x.split("[= ,:]"))
                .map(x -> new Pair(new Point(Integer.parseInt(x[6]), Integer.parseInt(x[3])), new Point(Integer.parseInt(x[16]), Integer.parseInt(x[13])),
                        getInfluence(new Point(Integer.parseInt(x[6]), Integer.parseInt(x[3])), new Point(Integer.parseInt(x[16]), Integer.parseInt(x[13])))))
                .toList();

        long left = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToLong(Point::col).min().getAsLong();
        long right = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToLong(Point::col).max().getAsLong();
        long top = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToLong(Point::row).min().getAsLong();
        long bot = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToLong(Point::row).max().getAsLong();

        var takenPoints = pairs.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).toList();

        int count = 0;
        for (long i = left; i <= right; i++) {
            Point pointer = new Point(10, i);
            if (takenPoints.contains(pointer)) continue;
            if (pairs.stream().anyMatch(x -> isBlockedBy(pointer, x))) count++;
        }
        long OFFSET = 15;
        for (long i = top - OFFSET; i < bot + OFFSET; i++) {
            System.out.printf("%2d ", i);

            for (long j = left - OFFSET; j < right + OFFSET; j++) {
                Point p = new Point(i, j);
                if (takenPoints.contains(p)) System.out.print("B");
                else if (pairs.stream().anyMatch(x -> isBlockedBy(p, x))) System.out.print("#");
//                else if (isBlockedBy(p, new Pair(new Point(7, 8), new Point(10, 2), getInfluence(new Point(7, 8), new Point(10, 2))))) System.out.print("#");
                else System.out.print(".");
            }
            System.out.println();
        }

        return count;
    }

    public Object part2() {
        return null;
    }

    record Pair(Point sensor, Point bacon, double influence) {
    }

    private boolean isBlockedBy(Point test, Pair pair) {
        double testInf = getInfluence(test, pair.sensor);
        return testInf <= pair.influence;
    }

    public static double getInfluence(Point a, Point b) {
        return (Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col()));
    }

    record Point(long row, long col) {

    }
}

