package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    public Day04() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        var input = FileReader.readAsString("Day4Input.txt");
        var lines = input.split("\r\n");
        List<String[]> grid = Arrays.stream(lines).map(x -> x.split("")).toList();

        int xmas = 0;
        int searched = 0;

        List<String> candidates = new ArrayList<>();

        //forward
        for (String[] row : grid) {
            String toSearch = String.join("", row);
            candidates.add(toSearch);
            candidates.add(new StringBuilder(toSearch).reverse().toString());
        }

        //top down
        for (int i = 0; i < grid.getFirst().length; i++) {
            int p = i;
            String toSearch = grid.stream().map(ts -> ts[p]).collect(Collectors.joining());

            candidates.add(toSearch);
            candidates.add(new StringBuilder(toSearch).reverse().toString());
        }


        String res = "";
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getFirst().length && j + i < grid.size(); j++) {
                res += grid.get(j + i)[j];
            }
            candidates.add(res);
            candidates.add(new StringBuilder(res).reverse().toString());
            res = "";
        }

        // candidates - top
        for (int i = 0; i < grid.size(); i++) {
            if (i == 0) continue;
            for (int j = 0; j < grid.getFirst().length && j + i < grid.getFirst().length; j++) {
                res += grid.get(j)[i + j];
            }
            candidates.add(res);
            candidates.add(new StringBuilder(res).reverse().toString());
            res = "";
        }

        // reversed everything
        grid = grid.reversed();
        for (int i = 0; i < grid.size(); i++) {
            Collections.reverse(Arrays.asList(grid.get(i)));
        }

        // once again
        res = "";
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getFirst().length && j + i < grid.size(); j++) {
                res += grid.get(j + i)[j];
            }
            candidates.add(res);
            candidates.add(new StringBuilder(res).reverse().toString());
            res = "";
        }

        // candidates - top
        for (int i = 0; i < grid.size(); i++) {
            if (i == 0) continue;
            for (int j = 0; j < grid.getFirst().length && j + i < grid.getFirst().length; j++) {
                res += grid.get(j)[i + j];
            }
            candidates.add(res);
            candidates.add(new StringBuilder(res).reverse().toString());
            res = "";
        }

        System.out.println();

        int pointer = 0;
        for (String toSearch : candidates) {
            String copy = toSearch;
            while (copy.contains("XMAS")) {
                xmas++;
                copy = copy.replace("XMAS", "");
            }
        }

        return xmas;
    }

    public Object part2() {

        return null;
    }
}

