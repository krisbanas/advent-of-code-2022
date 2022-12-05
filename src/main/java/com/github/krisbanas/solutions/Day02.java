package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import static java.lang.System.out;

public class Day02 {

    public Day02() {
        out.println(part1());
        out.println(part2());
    }

    public int part1() {
        return FileHelper.loadStringList("Day2Input.txt")
                .stream().map(s -> s.split(" "))
                .map(i -> new Pair(i[0], i[1]))
                .mapToInt(this::getPointsForPair)
                .sum();
    }

    private Object part2() {
        return FileHelper.loadStringList("Day2Input.txt")
                .stream().map(s -> s.split(" "))
                .map(i -> new Pair(i[0], i[1]))
                .mapToInt(this::getPointsForPair2)
                .sum();
    }

    private int getPointsForPair(Pair i) {
        return switch (i.left()) {
            case "A" -> //rock
                    switch (i.right()) {
                        case "X" -> 3 + 1; // rock
                        case "Y" -> 6 + 2; //paper
                        case "Z" -> 0 + 3; // scissors
                        default -> 0;
                    };
            case "B" -> //paper
                    switch (i.right()) {
                        case "X" -> 1; // rock
                        case "Y" -> 3 + 2; //paper
                        case "Z" -> 6 + 3; //scissors
                        default -> 0;
                    };
            case "C" -> //scissors
                    switch (i.right()) {
                        case "X" -> 6 + 1; // rock
                        case "Y" -> 0 + 2; // paper
                        case "Z" -> 3 + 3; //scissors
                        default -> 0;
                    };
            default -> 0;
        };
    }

    private int getPointsForPair2(Pair i) {
        return switch (i.left()) {
            case "A" -> //rock
                    switch (i.right()) {
                        case "X" -> 3 + 0; // lose, choose sc
                        case "Y" -> 1 + 3; // draw
                        case "Z" -> 2 + 6; // win, ppr
                        default -> 0;
                    };
            case "B" -> //paper
                    switch (i.right()) {
                        case "X" -> 1 + 0; // lose
                        case "Y" -> 2 + 3; //draw, ppr
                        case "Z" -> 3 + 6; //scissors
                        default -> 0;
                    };
            case "C" -> //scissors
                    switch (i.right()) {
                        case "X" -> 2 + 0; // lose
                        case "Y" -> 3 + 3; // draw
                        case "Z" -> 1 + 6; //win
                        default -> 0;
                    };
            default -> 0;
        };
    }

    record Pair(String left, String right) { }
}
