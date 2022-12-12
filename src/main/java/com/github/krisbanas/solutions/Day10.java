package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

public class Day10 {

    public Day10() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var commands = FileReader.readAsStringList("Day10Input.txt");
        int cycle = 1;
        int value = 1;
        int result = 0;

        for (String entry : commands) {
            if (shouldStrengthenSignal(cycle)) result += cycle * value;
            if (entry.contains("addx")) {
                cycle++;
                if (shouldStrengthenSignal(cycle)) result += cycle * value;
                value += Integer.parseInt(entry.split(" ")[1]);
            }
            cycle++;
        }
        return result;
    }


    private Object part2() {
        var entries = FileReader.readAsStringList("Day10Input.txt");
        int cycle = 1;
        int value = 1;

        for (String entry : entries) {
            paintPixel(cycle, value);
            if (entry.contains("addx")) {
                cycle++;
                paintPixel(cycle, value);
                value += Integer.parseInt(entry.split(" ")[1]);
            }
            cycle++;
        }
        return "";
    }

    private static boolean shouldStrengthenSignal(int cycle) {
        return (cycle + 20) % 40 == 0;
    }

    private void paintPixel(int cycle, int value) {
        boolean isBlackPixel = (cycle - 1) % 40 >= value - 1 && (cycle - 1) % 40 <= value + 1;
        if ((cycle - 1) % 40 == 0) System.out.println();
        System.out.print(isBlackPixel ? "██" : "  ");
    }
}
