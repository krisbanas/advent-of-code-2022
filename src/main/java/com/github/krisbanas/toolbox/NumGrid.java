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

    public NumGrid(String stringToParse, String lineDelimiter, String elementDelimiter, boolean fromChars) {
        grid = Arrays.stream(stringToParse.split(lineDelimiter))
                .map(line -> Arrays.stream(line.split(elementDelimiter)).map(String::trim).filter(e -> !e.isEmpty()).mapToLong(x -> x.charAt(0) - 'a').toArray())
                .toArray(long[][]::new);
    }

    public NumGrid(String stringToParse, String lineDelimiter, String elementDelimiter) {
        grid = Arrays.stream(stringToParse.split(lineDelimiter))
                .map(line -> Arrays.stream(line.split(elementDelimiter))
                        .map(String::trim)
                        .filter(e -> !e.isEmpty())
                        .mapToLong(Long::parseLong)
                        .toArray())
                .toArray(long[][]::new);
    }

    public NumGrid(String stringToParse, String lineDelimiter, String elementDelimiter, String ignoredCharacter) {
        grid = Arrays.stream(stringToParse.split(lineDelimiter))
                .map(line -> Arrays.stream(line.split(elementDelimiter))
                        .map(String::trim)
                        .filter(e -> !e.isEmpty())
                        .mapToLong(x -> "ignoredCharacter".equals(x) ? -1 : Long.parseLong(x))
                        .toArray())
                .toArray(long[][]::new);
    }

    public NumGrid(String stringToParse) {
        this(stringToParse, System.lineSeparator(), "");
    }

    private NumGrid(int row, int col) {
        grid = new long[row][col];
    }

    public static NumGrid ofSize(int row, int col) {
        return new NumGrid(row, col);
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

    public void setValue(Point p, long n) {
        if (isInGrid(p)) grid[p.row()][p.col()] = n;
    }

    public long getValue(Point p, int ifNot) {
        return isInGrid(p) ? grid[p.row()][p.col()] : ifNot;
    }

    public Stream<Point> stream() {
        return IntStream.range(0, grid.length).boxed().flatMap(row -> IntStream.range(0, grid[row].length).mapToObj(col -> new Point(row, col)));
    }

    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public int count(int target) {
        int result = 0;
        for (long[] longs : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                result += longs[j] == target ? 1 : 0;
            }
        }
        return result;
    }
}