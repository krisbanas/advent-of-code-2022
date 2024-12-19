package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static com.github.krisbanas.Main.ROW_PATTERN;

public class Day03 {

    public static void main(String[] args) {
        new Day03();
    }

    public Day03() {
        long startTime = System.nanoTime();
        long part1Result = part1();
        long part1Time = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        long part2Result = part2();
        long part2Time = System.nanoTime() - startTime;

        System.out.printf(ROW_PATTERN, 3, part1Result, part1Time / 1_000_000.0, part2Result, part2Time / 1_000_000.0);
    }

    public long part1() {
        String input = FileReader.readAsString("3.txt");
        String regex = "mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regex);
        List<String> rs = pattern.matcher(input).results().map(MatchResult::group).toList();
        long result = 0;
        for (String r : rs) {
            String next = r.replaceAll("\\)|mul\\(", "");
            long first = Long.parseLong(next.split(",")[0]);
            long second = Long.parseLong(next.split(",")[1]);
            result += first * second;
        }
        return result;
    }

    public long part2() {
        var input = FileReader.readAsString("3.txt");
        var regex = "(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))";
        Pattern pattern = Pattern.compile(regex);
        List<String> line = pattern.matcher(input).results().map(MatchResult::group).toList();
        long result = 0L;
        boolean ok = true;
        for (String match : line) {
            String next = match.replaceAll("\\)|mul\\(", "");
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

