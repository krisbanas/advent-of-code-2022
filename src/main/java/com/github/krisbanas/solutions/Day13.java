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
        for (String entry : input) {
            var left = entry.split("\n")[0].trim();
            var right = entry.split("\n")[1].trim();

            List<Object> leftThing = toObjectList(left);
            List<Object> rightThing = toObjectList(right);

            boolean isCorrect = compare(leftThing, rightThing);
        }


        return null;
    }

    private List<Object> toObjectList(String input) {
        List<Object> result = new ArrayList<>();
        if (input.equals("")) return result;
        if (!input.contains("[") && !input.contains("]")) return Arrays.stream(input.split(",")).map(Integer::valueOf).map(x -> (Object) x).toList();
        int level = 0;
        List<Integer> milestones = new ArrayList<>();
        milestones.add(0);
        milestones.add(input.length() - 1);
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '[') level++;
            else if (c == ']') level--;
            else if (c == ',' && level == 0) milestones.add(i);
        }
        milestones.sort(Integer::compareTo);
        for (int i = 0; i < milestones.size() - 1; i++) {
            int start = milestones.get(i) + 1;
//            if (input.charAt(start) == '[')
            int end = milestones.get(i + 1) - 1;
            String next = input.substring(start, end);
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

//    private static List<LevelInteger> toLevelList(String left) {
//        List<LevelInteger> leftList = new ArrayList<>();
//        int pointer = 0;
//        int level = 0;
//        String[] leftArr = left.split(" +");
//        while (pointer < leftArr.length) {
//            String next = leftArr[pointer];
//            switch (next) {
//                case "[" -> level++;
//                case "]" -> level--;
//                case ",", "" -> {
//                    pointer++;
//                    continue;
//                }
//                default -> leftList.add(new LevelInteger(level, Integer.parseInt(next))); // digit
//            }
//            pointer++;
//        }
//        return leftList;
//    }

    record LevelInteger(int level, int value) {
    }

}
