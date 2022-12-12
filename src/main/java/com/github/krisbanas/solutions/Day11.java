package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class Day11 {

    public static final int DIVISION_MASTER = 9699690;

    public Day11() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        return simulateMonkeys(20, false);
    }

    private Object part2() {
        return simulateMonkeys(10_000, true);
    }

    private Object simulateMonkeys(int size, boolean modulo) {
        final var monkeys = extractToMonkeyList();

        for (int j = 0; j < size; j++) {
            for (Monkey monke : monkeys) {
                for (Long item : monke.items) {
                    monke.itemsInspected[0] += 1;
                    item = monke.function.apply(item);
                    item = modulo ? item % DIVISION_MASTER : item / 3;
                    if (item % monke.test == 0) {
                        monkeys.get(monke.ifTrue).items.add(item);
                    } else {
                        monkeys.get(monke.ifFalse).items.add(item);
                    }
                }
                monke.items.clear();
            }
        }
        return extractMostWorryingItem(monkeys);
    }

    private List<Monkey> extractToMonkeyList() {
        return Arrays.stream(FileReader.readAsString("Day11Input.txt").split("\n\n"))
                .map(x -> x.split("\n"))
                .map(this::ints)
                .map(this::toMonkey)
                .toList();
    }

    private static long extractMostWorryingItem(List<Monkey> monkeys) {
        List<Long> sorted = new ArrayList<>(monkeys.stream().mapToLong(m -> m.itemsInspected[0]).sorted().boxed().toList());
        Collections.reverse(sorted);
        return sorted.get(0) * sorted.get(1);
    }

    private Monkey toMonkey(String[] input) {
        int number = Integer.parseInt(input[0]);
        List<Long> items = new ArrayList<>(Arrays.stream(input[1].split(",")).map(Long::valueOf).toList());
        UnaryOperator<Long> operator;
        if (input[2].contains("old * old")) {
            operator = x -> x * x;
        } else if (input[2].contains("+")) {
            operator = x -> x + Integer.parseInt(ints(input[2]));
        } else {
            operator = x -> x * Integer.parseInt(ints(input[2]));
        }

        return new Monkey(number, items, operator, Integer.parseInt(input[3]), Integer.parseInt(input[4]), Integer.parseInt(input[5]), new int[1]);
    }

    private String[] ints(String[] input) {
        var output = Arrays.stream(input)
                .map(x -> x.replaceAll("[^\\d,.]", ""))
                .toArray(String[]::new);
        output[2] = input[2].replace("  Operation: new = ", "");
        return output;
    }

    private String ints(String input) {
        return input.replaceAll("[^\\d,.]", "");
    }

    record Monkey(int number, List<Long> items, UnaryOperator<Long> function, int test, int ifTrue, int ifFalse, int[] itemsInspected) {

    }
}
