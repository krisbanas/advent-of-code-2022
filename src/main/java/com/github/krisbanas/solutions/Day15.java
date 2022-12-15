package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.Extractor;
import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;

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
        var input = Arrays.stream(FileReader.readAsString("Day15Input.txt").trim().split("\n"))
                .map(x -> x.split("[= ,:]"))
                .map(x -> new Pair(new Point(Integer.parseInt(x[3]), Integer.parseInt(x[6])), new Point(Integer.parseInt(x[13]), Integer.parseInt(x[16])),
                        getInfluence(new Point(Integer.parseInt(x[3]), Integer.parseInt(x[6])), new Point(Integer.parseInt(x[13]), Integer.parseInt(x[16])))))
                .toList();

//        List<Point> sensors = input.stream().map(x -> x.sensor).toList();
        int left = input.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToInt(x -> x.row()).min().getAsInt();
        int right = input.stream().map(x -> List.of(x.sensor, x.bacon)).flatMap(Collection::stream).mapToInt(x -> x.row()).max().getAsInt();
        for (int i = left; i <= right; i++) {

        }
        return null;
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
        return Math.sqrt(Math.pow(a.row() - b.row(), 2) + Math.pow(a.col() - b.col(), 2));
    }
}
