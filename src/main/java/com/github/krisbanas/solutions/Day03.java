package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day03 {

    public Day03() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var input = FileReader.readAsString("Day3Input.txt");
        var regex = "mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regex);
        List<String> rs = pattern.matcher(input).results().map(MatchResult::group).toList();
        long result = 0L;
        for (String r : rs) {
            String next = r.replaceAll("\\(", "");
            next = next.replaceAll("\\)", "");
            next = next.replaceAll("mul", "");
            long first = Long.parseLong(next.split(",")[0]);
            long second = Long.parseLong(next.split(",")[1]);
            result += first * second;
        }
        return result;
    }

    public Object part2() {
        var input = FileReader.readAsString("Day3Input.txt");
        var regex = "(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))";
        Pattern pattern = Pattern.compile(regex);
        List<String> rs = pattern.matcher(input).results().map(MatchResult::group).toList();
        long result = 0L;
        boolean ok = true;
        for (String r : rs) {
            String next = r.replaceAll("\\(", "");
            next = next.replaceAll("\\)", "");
            next = next.replaceAll("mul", "");
            if (next.contains("don't")) {
                ok = false;
            } else if (next.contains("do")) {
                ok = true;
            } else {
                if (!ok) continue;
                long first = Long.parseLong(next.split(",")[0]);
                long second = Long.parseLong(next.split(",")[1]);
                result += first * second;
            }
        }
        return result;
    }
}

