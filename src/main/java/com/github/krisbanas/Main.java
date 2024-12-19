package com.github.krisbanas;

import com.github.krisbanas.solutions.*;

public class Main {
    public static final String ROW_PATTERN = "│ Day%2d │ %14d │ %6.2fms │ %14d │ %6.2fms │%n";

    public static void main(String[] args) {
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│             A D V E N T   O F   C O D E   2 0 2 4             │");
        System.out.println("│───────────────────────────────────────────────────────────────│");
        System.out.println("│                Solutions by: Krzysztof BANASIK                │");
        System.out.println("│───────┬────────────────┬──────────┬────────────────┬──────────│");
        System.out.println("│  Day  │    Part 1      │   Time   │    Part 2      │   Time   │");
        System.out.println("├───────┼────────────────┼──────────┼────────────────┼──────────┤");
        new Day01();
        new Day02();
        new Day03();
        System.out.println("└───────┴────────────────┴──────────┴────────────────┴──────────┘");
    }
}
