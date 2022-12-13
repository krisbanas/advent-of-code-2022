package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {

    public Day13() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var input = FileReader.readAsString("Day13Input.txt").trim().split("\n\n");
        int counter = 0;
        for (int i = 0; i < input.length; i++) {
            String entry = input[i];
            var left = entry.split("\n")[0].trim();
            var right = entry.split("\n")[1].trim();

            List<Object> rightThing = toObjectList(right);
            List<Object> leftThing = toObjectList(left);

            boolean isCorrect = compare(leftThing, rightThing);
            if (isCorrect) {
                counter++;
                System.out.println("Correct index: " + (int) (i + 1));
            }

        }


        return counter;
    }

    private List<Object> toObjectList(String input) {
        List<Object> result = new ArrayList<>();
        input = input.replaceAll("^,", "").replaceAll(",$", "");
        if (input.equals("")) return result;
        if (!input.contains("[") && !input.contains("]")) return Arrays.stream(input.split(",")).map(Integer::valueOf).map(x -> (Object) x).toList();
        int level = 0;
        List<Integer> milestones = new ArrayList<>();

        if (input.startsWith("[")) milestones.add(0);
        if (input.endsWith("]")) milestones.add(input.length());
        if (input.startsWith("[") && input.endsWith("]")) {
            milestones.add(1);
            milestones.add(input.length()-1);
        }

        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '[') {
                level++;
            } else if (c == ']') {
                level--;

            } else if (c == ',') {
                if (level == 0) milestones.add(i);
            }
        }
        milestones = milestones.stream().sorted(Integer::compareTo).toList();
        for (int i = 0; i < milestones.size() - 1; i ++) {
            int start = milestones.get(i);
            int end = milestones.get(i + 1);
            String next = input.substring(start, end);
            if (next.equals("[") || next.equals("]")) continue;
            result.add(toObjectList(next));
        }
        return result;
    }

    private boolean compare(Object left, Object right) {
        if (left instanceof List leftList && right instanceof List rightList) {
            if (leftList.size() > rightList.size()) return false;
            for (int i = 0; i < leftList.size(); i++) {
                if (!compare(leftList.get(i), rightList.get(i))) return false;
            }
        } else if (left instanceof List leftList && right instanceof Integer i) {
            if (!compare(leftList, List.of(i))) return false;
        } else if (left instanceof Integer i && right instanceof List rightList) {
            if (!compare(List.of(i), right)) return false;
        } else if (left instanceof Integer l && right instanceof Integer r) {
            if (l > r) return false;
        } else {
            System.out.println("");
        }


        return true;
    }

    public Object part2() {
        return null;
    }

    record LevelInteger(int level, int value) {
    }

}
