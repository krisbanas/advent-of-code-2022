package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

public class Day01 {

    public Day01() {
        part1();
        part2();
    }

    public void part1() {
        FileHelper.loadStringList("Day01Input.txt").stream()
                .forEach(System.out::println);
    }

    private void part2() {
    }
}
