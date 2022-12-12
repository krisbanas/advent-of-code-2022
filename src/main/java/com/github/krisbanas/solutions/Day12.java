package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.Direction;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;
import com.github.krisbanas.toolbox.FileReader;

import java.util.*;

public class Day12 {

    private static final Point MARKER = new Point(-9, -9);
    private final NumGrid numericGrid;

    public Day12() {
        numericGrid = new NumGrid(FileReader.readAsString("Day12Input.txt"), "\n", "", true);
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        Point start = numericGrid.stream().filter(x -> numericGrid.getValue(x) == -14).findAny().get();
        numericGrid.setValue(start, 0);
        Point end = numericGrid.stream().filter(x -> numericGrid.getValue(x) == -28).findAny().get();
        return findDistance(start, end);
    }

    private int findDistance(Point start, Point end) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        queue.offer(start);
        queue.offer(MARKER);
        int steps = 0;
        while (!queue.isEmpty()) {

            Point currentPoint = queue.poll();
            if (currentPoint.equals(end)) return steps;
            if (currentPoint.equals(MARKER)) {
                if (queue.size() == 0) return Integer.MAX_VALUE;
                steps++;
                queue.offer(MARKER);
                continue;
            }

            if (visited.contains(currentPoint)) continue;
            visited.add(currentPoint);
            long currentVal = numericGrid.getValue(currentPoint);
            Arrays.stream(Direction.fourDirections())
                    .map(dir -> dir.move(currentPoint))
                    .filter(x -> !visited.contains(x))
                    .filter(numericGrid::isInGrid)
                    .filter(newPoint -> numericGrid.getValue(newPoint) - currentVal <= 1 || (newPoint.equals(end) && currentVal >= 24))
                    .forEach(queue::offer);
        }
        return Integer.MAX_VALUE;
    }

    public Object part2() {
        Point end = numericGrid.stream().filter(x -> numericGrid.getValue(x) == -28).findAny().get();
        return numericGrid.stream().filter(x -> numericGrid.getValue(x) == 0).mapToInt(x -> findDistance(x, end)).min().getAsInt();
    }
}
