package com.github.krisbanas;

import com.github.krisbanas.solutions.*;

public class Main {
    public static final String ROW_PATTERN = "│ Day%3d │ %20d │ %7.2fms │ %20d │ %9.2fms │%n";

    public static void main(String[] args) {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                      A D V E N T   O F   C O D E   2 0 2 4                     │");
        System.out.println("│────────────────────────────────────────────────────────────────────────────────│");
        System.out.println("│                         Solutions by: Krzysztof BANASIK                        │");
        System.out.println("│────────┬──────────────────────┬───────────┬──────────────────────┬─────────────│");
        System.out.println("│  Day   │    Part 1 Result     │ Time [ms] │     Part 2 Result    │  Time [ms]  │");
        System.out.println("├────────┼──────────────────────┼───────────┼──────────────────────┼─────────────┤");
        new Day01();
        new Day02();
        new Day03();
        System.out.println("Day 4 here");
        new Day05();
        System.out.println("Day 6 here");
        new Day07();
        System.out.println("└────────┴──────────────────────┴───────────┴──────────────────────┴──────────────┘");
    }
}
