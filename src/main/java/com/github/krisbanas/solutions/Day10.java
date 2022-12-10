package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    public Day10() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var input = FileHelper.loadStringList("Day10Input.txt");
        int cycleEnd = 1;
        int fast = 2;
        int cumulated = 1;
        int toAdd = 0;
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            String entry = input.get(i);
            if (entry.contains("noop")) {
                fast += 1;
                if (shouldPrint(fast)) {
                    System.out.println("During cycle :  " + (fast) + " value: " + cumulated);
                    result += fast * cumulated;
                }
            } else {
                fast += 2;
                cumulated += Integer.parseInt(entry.split(" ")[1]);
                if (shouldPrint(fast - 1)) {
                    System.out.println("During cycle :  " + (fast - 1) + " value: " + cumulated);
                    result += (fast - 1) * cumulated;

                }
                if (shouldPrint(fast)) {
                    System.out.println("During cycle :  " + fast + " value: " + cumulated);
                    result += fast * cumulated;
                }
            }
            if (fast == 140) System.out.println("here");
            cycleEnd++;
//            System.out.println("cycle " + cycleEnd + " value: " + cumulated);

        }
        return result;
    }

    private static boolean shouldPrint(int fast) {
        return fast == 0 || (fast - 20) % 40 == 0;
    }

    String [][] matrix = new String[6][40];

    private Object part2() {
        var input = FileHelper.loadStringList("Day10Input.txt");
        int fast = 2;
        int cumulated = 1;
        int result = 0;
        List<Integer> spriteWhere = new ArrayList<>();
        spriteWhere.add(Integer.MIN_VALUE);
        spriteWhere.add(1);

        for (int i = 0; i < 40 * 6; i++) {
            String entry = i < input.size() ? input.get(i) : "noop";
            int caretPosition = i % 40;

            if (entry.contains("noop")) {
                fast += 1;
                spriteWhere.add(cumulated);
                var spriteNow = spriteWhere.get(i+1);
                if (i % 40 >= spriteNow - 1 && i % 40 <= spriteNow + 1) {
                    paintPixel(i / 40, i % 40);
                }

            } else {
                fast += 2;
                spriteWhere.add(cumulated);
                var spriteNow = spriteWhere.get(i+1);
                if (i < input.size()) {
                    int toAdd = Integer.parseInt(entry.split(" ")[1]);
                    cumulated += toAdd;
                }

                spriteWhere.add(cumulated);
                if (i % 40 >= spriteNow - 1 && i % 40 <= spriteNow + 1) {
                    paintPixel(i / 40, i % 40);
                }
            }
        }
        paintAll();
        return result;
    }

    private void paintPixel(int i, int j) {
        matrix[i][j] = ".";
        System.out.println("Painting!");
    }

    private void paintAll() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 40; j++) {
                var toPrint = matrix[i][j] == null ? "#" : ".";
                System.out.print(toPrint);
            }
            System.out.println();
        }
    }

    record Instruction(String command, int when) {
    }
}
