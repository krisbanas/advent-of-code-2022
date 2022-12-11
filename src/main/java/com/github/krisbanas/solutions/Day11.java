package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileReader;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {

    public static final int DIVISION_MASTER = 9699690;
    public static final int DIVISION_MASTER_TESTER = 96577;

    public Day11() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        final var monkeys = Arrays.stream(FileReader.readAsString("Day11Input.txt").split("\n\n"))
                .map(x -> x.split("\n"))
                .map(this::ints)
                .map(x -> toMonkey(x))
                .toList();

        for (int j = 0; j < 20; j++) {
            for (Monkey monke : monkeys) {
                for (Integer item : monke.items) {
                    monke.itemsInspected[0] += 1;
                    item = monke.function.apply(item) / 3;
                    if (item % monke.test == 0) {
                        monkeys.get(monke.ifTrue).items.add(item);
                    } else {
                        monkeys.get(monke.ifFalse).items.add(item);
                    }
                }
                monke.items.clear();
            }
            System.out.println();
            System.out.println("Round " + j + 1);
            System.out.println("items in monke 1: " + Arrays.toString(monkeys.get(0).items.toArray()));
            System.out.println("items in monke 2: " + Arrays.toString(monkeys.get(1).items.toArray()));
        }
        List<Integer> sorted = new ArrayList<>(monkeys.stream().mapToInt(m -> m.itemsInspected[0]).sorted().boxed().toList());
        Collections.reverse(sorted);
        return sorted.get(0) * sorted.get(1);
    }


    private Object part2() {
        final var monkeys = Arrays.stream(FileReader.readAsString("Day11Input.txt").split("\n\n"))
                .map(x -> x.split("\n"))
                .map(this::ints)
                .map(this::toBigMonkey)
                .toList();

        List<String> itemsExpectedRound = new ArrayList<>();

        List<Integer> monke1Items = new ArrayList<>();
        monke1Items.add(0);
        List<Integer> monke1ItemsDerivative = new ArrayList<>();
        monke1ItemsDerivative.add(0);
        Set<String> monkeState = new HashSet<>();

        for (int j = 1; j < 10001; j++) {
            for (BigMonkey monke : monkeys) {
                for (BigInteger item : monke.items) {
                    monke.itemsInspected[0] += 1;
                    item = monke.function.apply(item).mod(BigInteger.valueOf(DIVISION_MASTER));
                    if (item.mod(monke.test).equals(BigInteger.ZERO)) {
                        monkeys.get(monke.ifTrue).items.add(item);
                    } else {
                        monkeys.get(monke.ifFalse).items.add(item);
                    }
                }
                monke.items.clear();

            }


            // debugging tools:
            var xd = monkeys.stream().map(x -> String.valueOf(x.itemsInspected[0])).collect(Collectors.joining(", "));
            itemsExpectedRound.add(xd);
            monke1Items.add(monkeys.get(0).itemsInspected[0]);
            monke1ItemsDerivative.add(monkeys.get(0).itemsInspected[0] - monke1Items.get(j));

            var dd = monkeys.stream().map(x -> String.valueOf(x.itemsInspected[0])).collect(Collectors.joining(""));
            if (monkeState.contains(dd)) throw new RuntimeException();
            monkeState.add(dd);

            if (j == 20 || j % 1000 == 0) {
            System.out.println("Round " + j);
            System.out.println(Arrays.toString(monkeys.stream().mapToInt(m -> m.itemsInspected[0]).toArray()));
//                System.out.println("items in monke 1: " + monkeys.get(0).itemsInspected[0]);
//                System.out.println("items in monke 2: " + monkeys.get(1).itemsInspected[0]);
            }
        }


        ///////


        List<Integer> sorted = new ArrayList<>(monkeys.stream().mapToInt(m -> m.itemsInspected[0]).sorted().boxed().toList());
        Collections.reverse(sorted);
        return sorted.get(0) * sorted.get(1);
    }

    private Monkey toMonkey(String[] input) {
        int number = Integer.parseInt(input[0]);
        List<Integer> items = new ArrayList<>(Arrays.stream(input[1].split(",")).map(x -> Integer.valueOf(x)).toList());
        Function<Integer, Integer> f;
        if (input[2].contains("old * old")) {
            f = x -> x * x;
        } else if (input[2].contains("+")) {
            f = x -> x + Integer.parseInt(ints(input[2]));
        } else {
            f = x -> x * Integer.parseInt(ints(input[2]));
        }

        return new Monkey(number, items, f, Integer.parseInt(input[3]), Integer.parseInt(input[4]), Integer.parseInt(input[5]), new int[1]);
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

    record Monkey(int number, List<Integer> items, Function<Integer, Integer> function, int test, int ifTrue,
                  int ifFalse, int[] itemsInspected) {
    }

    record BigMonkey(int number, List<BigInteger> items, Function<BigInteger, BigInteger> function, BigInteger test,
                     int ifTrue,
                     int ifFalse, int[] itemsInspected) {
    }

    private BigMonkey toBigMonkey(String[] input) {
        int number = Integer.parseInt(input[0]);
        List<BigInteger> items = new ArrayList<>(Arrays.stream(input[1].split(",")).map(x -> BigInteger.valueOf(Integer.parseInt(x))).toList());
        Function<BigInteger, BigInteger> f;
        if (input[2].contains("old * old")) {
            f = x -> x.multiply(x);
        } else if (input[2].contains("+")) {
            BigInteger factor = BigInteger.valueOf(Integer.parseInt(ints(input[2])));
            f = x -> x.add(factor);
        } else {
            BigInteger factor = BigInteger.valueOf(Integer.parseInt(ints(input[2])));
            f = x -> x.multiply(factor);
        }

        BigInteger test = BigInteger.valueOf(Integer.parseInt(input[3]));
        return new BigMonkey(number, items, f, test, Integer.parseInt(input[4]), Integer.parseInt(input[5]), new int[1]);
    }
}
