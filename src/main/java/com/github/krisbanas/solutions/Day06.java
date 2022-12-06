package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;
import static java.lang.System.out;

public class Day06 {

    public Day06() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        var input = FileHelper.loadString("Day6Input.txt");

        for (int i=3; i<input.length(); i++) {
            Set<Character> characterSet = new HashSet<>();
            for (int j=i; j > i-4; j--) characterSet.add(input.charAt(j));
            if (characterSet.size() == 4) return i + 1;
        }

        return null;
    }

    private Object part2() {
        var input = FileHelper.loadString("Day6Input.txt");

        for (int i=13; i<input.length(); i++) {
            Set<Character> characterSet = new HashSet<>();
            for (int j=i; j > i-14; j--) characterSet.add(input.charAt(j));
            if (characterSet.size() == 14) return i + 1;
        }

        return null;    }


}

