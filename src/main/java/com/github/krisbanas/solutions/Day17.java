package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public Day17() {
        System.out.println("Part one: " + part1());
        System.out.println("Part two: " + part2());
    }

    public Object part1() {
        String[] input = FileReader.readAsString("17.txt").split("\r\n\r\n");
        long initialA = Long.parseLong(input[0].split("\r\n")[0].replaceAll("Register A: ", ""));

        String[] split = input[1].trim()
                .replaceAll("Program: ", "")
                .split(",");
        int[] program = Arrays.stream(split)
                .mapToInt(Integer::parseInt)
                .toArray();

        Integer[] result = execute(program, initialA);
        return Arrays.stream(result).map(String::valueOf).collect(Collectors.joining(","));
    }

    public Object part2() {
        String[] input = FileReader.readAsString("17.txt").split("\r\n\r\n");

        String[] split = input[1].trim()
                .replaceAll("Program: ", "")
                .split(",");
        int[] program = Arrays.stream(split)
                .mapToInt(Integer::parseInt)
                .toArray();


        int[] target = new int[program.length];
        for (int i = 0; i < program.length; i++) {
            target[i] = program[program.length - 1 - i];
        }

        return searchForA(0, 0, target, program);
    }


    public long searchForA(long a, int depth, int[] target, int[] prog) {
        if (depth == target.length) {
            return a;
        }
        for (int i = 0; i < 8; i++) {
            Integer[] output = execute(prog, a * 8L + i);
            if (output != null && output[0] == target[depth]) {
                long result = searchForA(a * 8 + i, depth + 1, target, prog);
                if (result != 0) {
                    return result;
                }
            }
        }
        return 0;
    }

    private Integer[] execute(int[] program, long initialA) {
        long a = initialA, b = 0, c = 0;
        int pc = 0;
        List<Integer> results = new ArrayList<>();
        while (pc < program.length) {
            int opcode = program[pc++];
            int operand = program[pc++];
            long combo = new long[]{0, 1, 2, 3, a, b, c}[operand];

            switch (opcode) {
                case 0 -> a = a / ((long) Math.pow(2, combo));
                case 1 -> b = b ^ operand;
                case 2 -> b = combo % 8;
                case 3 -> pc = a == 0 ? pc : operand;
                case 4 -> b = b ^ c;
                case 5 -> results.add((int) (combo % 8));
                case 6 -> b = a / ((long) Math.pow(2, combo));
                case 7 -> c = a / ((long) Math.pow(2, combo));
            }
        }
        return results.toArray(Integer[]::new);
    }
}
