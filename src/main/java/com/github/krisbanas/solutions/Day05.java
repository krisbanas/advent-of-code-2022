package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Day05 {

    public Day05() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        List<Stack<String>> stacks = readStacks();
        List<Command> commands = readCommands();

        for (Command c : commands) {
            for (int i = 0; i < c.count(); i++) {
                stacks.get(c.to()).push(stacks.get(c.from()).pop());
            }
        }

        return stacks.stream().map(Stack::pop).collect(Collectors.joining());
    }

    private Object part2() {
        List<Stack<String>> stacks = readStacks();
        List<Command> commands = readCommands();
        Stack<String> tmp = new Stack<>();

        for (Command c : commands) {
            int count = c.count();
            for (int i = 0; i < count; i++) tmp.push(stacks.get(c.from()).pop());
            for (int i = 0; i < count; i++) stacks.get(c.to()).push(tmp.pop());
        }

        return stacks.stream().map(Stack::pop).collect(Collectors.joining());
    }

    private static List<Stack<String>> readStacks() {
        var xd = FileHelper.loadString("Day5Input.txt").split(System.lineSeparator() + System.lineSeparator());
        var inputs = Arrays.stream(xd[0].split(System.lineSeparator()))
                .map(x -> x.replace("[", " "))
                .map(x -> x.replace("]", " "))
                .map(x -> x.replaceAll("^ ", ""))
                .map(x -> x.replaceAll(" $", ""))
                .toList();
        var max = Arrays.stream(inputs.get(inputs.size() - 1).replace("[a-zA-z]", "").split(" +")).filter(x -> !x.isEmpty()).mapToInt(Integer::parseInt).max().getAsInt();
        List<Stack<String>> stacks = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            stacks.add(new Stack<>());
        }
        for (int i = inputs.size() - 2; i >= 0; i--) {
            String line = inputs.get(i);
            int pointer = 0;
            while (pointer < line.length()) {
                String character = Character.toString(line.charAt(pointer));
                if (!character.isBlank()) stacks.get(pointer / 4).push(character);
                pointer += 4;
            }
        }
        return stacks;
    }

    private static List<Command> readCommands() {
        var input = FileHelper.loadString("Day5Input.txt").split(System.lineSeparator() + System.lineSeparator());
        return Arrays.stream(input[1].split(System.lineSeparator()))
                .map(x -> x.replaceAll("[a-zA-Z ]", " "))
                .map(x -> x.trim().replaceAll(" +", " ").split(" "))
                .map(x -> new Command(Integer.parseInt(x[0]), Integer.parseInt(x[1]) - 1, Integer.parseInt(x[2]) - 1))
                .toList();
    }
    record Command(int count, int from, int to) { }
}

