package com.github.krisbanas.toolbox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumGrid {
    /**
     * in competitive programming, encapsulation is superfluous
     */
    public long[][] grid;

    public NumGrid(String stringToParse, String lineDelimiter, String elementDelimiter) {
        grid = Arrays.stream(stringToParse.split(lineDelimiter))
                .map(line -> Arrays.stream(line.split(elementDelimiter)).map(String::trim).filter(e -> !e.isEmpty()).mapToLong(Long::parseLong).toArray())
                .toArray(long[][]::new);
    }

    public NumGrid(String stringToParse) {
        this(stringToParse, System.lineSeparator(), "");
    }

    public Set<Point> edgePoints() {
        Set<Point> edgePoints = new HashSet<>();
        IntStream.range(0, grid.length).forEach(i -> {
            edgePoints.add(new Point(i, 0));
            edgePoints.add(new Point(i, grid[0].length - 1));
        });
        IntStream.range(0, grid[0].length).forEach(i -> {
            edgePoints.add(new Point(0, i));
            edgePoints.add(new Point(0, grid.length - 1));
        });
        return edgePoints;
    }

    public boolean isInGrid(Point p) {
        return p.row() >= 0 && p.row() < grid.length && p.col() >= 0 && p.col() < grid[0].length;
    }

    public long getValue(Point p) {
        return isInGrid(p) ? grid[p.row()][p.col()] : -1;
    }

    public long getValue(Point p, int ifNot) {
        return isInGrid(p) ? grid[p.row()][p.col()] : ifNot;
    }

    public Stream<Point> stream() {
        return IntStream.range(0, grid.length).boxed().flatMap(row -> IntStream.range(0, grid[row].length).mapToObj(col -> new Point(row, col)));
    }
}