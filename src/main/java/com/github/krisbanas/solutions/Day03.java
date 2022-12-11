package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileReader;

import java.util.*;

import static java.lang.System.out;

public class Day03 {

    public Day03() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        return FileReader.readAsStringList("Day3Input.txt").stream()
                .map(this::findDuplicate)
                .mapToInt(x -> Character.toLowerCase(x) - 'a' + (Character.isUpperCase(x) ? 26 : 0) + 1)
                .sum();

    }

    private Object part2() {
        List<String> elves = FileReader.readAsStringList("Day3Input.txt");
        List<List<String>> grouped = new ArrayList<>();
        List<String> current = null;
        for (int i = 0; i < elves.size(); i++) {
            if (i % 3 == 0) {
                current = new ArrayList<>();
            }
            current.add(elves.get(i));
            if (i % 3 == 0) {
                grouped.add(current);
            }
        }
        return grouped.stream().map(this::findCommonItem)
                .mapToInt(x -> Character.toLowerCase(x) - 'a' + (Character.isUpperCase(x) ? 26 : 0) + 1)
                .sum();
    }

    private Character findCommonItem(List<String> x) {
        Map<String, String> res = new HashMap<>();
        for (String s : x) {
            for (char c : s.toCharArray()) {
                var entry = res.getOrDefault(String.valueOf(c), "");
                if (entry.contains(s)) continue;
                res.put(String.valueOf(c), entry + s);
            }
        }

        return res.entrySet().stream()
                .min((a, b) -> -(a.getValue().length() - b.getValue().length()))
                .get().getKey().charAt(0);
    }

    private Character findDuplicate(String x) {
        String first = x.substring(0, x.length() / 2);
        String second = x.substring(x.length() / 2);

        Set<Character> hm = new HashSet<>();
        for (char c : first.toCharArray()) {
            hm.add(c);
        }
        for (char c : second.toCharArray()) {
            if (hm.contains(c)) return c;
        }
        throw new NullPointerException();
    }
}
