package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.*;

import static java.lang.System.out;

public class Day08 {
    public Day08() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        var xd = FileHelper.loadStringList("Day8Input.txt");
        Set<Tree> set = new HashSet<>();
        int[][] trees = new int[xd.size()][];
        for (int i = 0; i < xd.size(); i++) {
            var split = Arrays.stream(xd.get(i).split("")).toList();
            trees[i] = new int[split.size()];
            for (int j = 0; j < split.size(); j++) {
                trees[i][j] = Integer.parseInt(split.get(j));
            }
        }
        for (int i = 0; i < trees.length; i++) {
        }

        var counter = 0;
        // left-right
        for (int i = 0; i < trees.length; i++) {

            int[] row = trees[i];

            int left = 0, right = row.length - 1;
            int currLeft = -1, currRight = -1;
            while (left <= right) {
                if (currLeft <= currRight) { // scan left
                    int tree = row[left];
                    if (tree > currLeft) {
                        set.add(new Tree(i, left));
                        currLeft = tree;
                    }
                    left++;
                } else {
                    int tree = row[right];
                    if (tree > currRight) {
                        set.add(new Tree(i, right));
                        currRight = tree;
                    }
                    right--;
                }
            }
        }

        // up-down
        for (int i = 0; i < trees[0].length; i++) {
            int[] col = getCol(trees, i);
            int left = 0, right = col.length - 1;
            int currLeft = -1, currRight = -1;
            while (left <= right) {
                if (currLeft <= currRight) { // scan left
                    int tree = col[left];
                    if (tree > currLeft) {
                        set.add(new Tree(left, i));
                        currLeft = tree;
                    }
                    left++;
                } else {
                    int tree = col[right];
                    if (tree > currRight) {
                        set.add(new Tree(right, i));
                        currRight = tree;
                    }
                    right--;
                }
            }
        }

        return set.size();
    }

    private int[] getCol(int[][] trees, int i) {
        int[] result = new int[trees[0].length];
        for (int j = 0; j < trees[0].length; j++) {
            result[j] = trees[j][i];
        }

        return result;
    }

    private Object part2() {
        List<List<Integer>> scores = new ArrayList<>();
        var xd = FileHelper.loadStringList("Day8Input.txt");
        int[][] trees = new int[xd.size()][];
        for (int i = 0; i < xd.size(); i++) {
            var split = Arrays.stream(xd.get(i).split("")).toList();
            trees[i] = new int[split.size()];
            for (int j = 0; j < split.size(); j++) {
                trees[i][j] = Integer.parseInt(split.get(j));
            }
        }

        var counter = 0;
        // left-right
        for (int i = 0; i < trees.length; i++) {
            for (int j = 0; j < trees[i].length; j++) {
                var score = scoreTree(trees, i, j);
                counter = Math.max(counter, score);
            }
        }

        return counter;
    }

    private int scoreTree(int[][] trees, int row, int col) {
        int scenicScore = 1;
        int score = 0;
        int tree = trees[row][col];

        int biggestSoFar = 0;
        // pointer
        int pointer = row - 1;
        while (pointer >= 0) { //up
            if (biggestSoFar > trees[pointer][col]) break;
            biggestSoFar = trees[pointer][col];
            pointer--;
            score++;
        }
        scenicScore *= score;
        score = 0;

        biggestSoFar = 0;
        pointer = row + 1;
        while (pointer <= trees[row].length - 1) { //down
            if (biggestSoFar > trees[pointer][col]) break;
            biggestSoFar = trees[pointer][col];
            pointer++;
            score++;
        }
        scenicScore *= score;
        score = 0;

        biggestSoFar = 0;
        pointer = col - 1;
        while (pointer >= 0) { //left
            if (biggestSoFar > trees[row][pointer]) break;
            biggestSoFar =trees[row][pointer];
            pointer--;
            score++;
        }
        scenicScore *= score;
        score = 0;

        biggestSoFar = 0;
        pointer = col + 1;
        while (pointer <= trees.length - 1) { //down
            if (biggestSoFar > trees[row][pointer]) break;
            biggestSoFar = trees[row][pointer];
            pointer++;
            score++;
        }
        scenicScore *= score;
        score = 0;

        return scenicScore;
    }

    public record Tree(int row, int col) {
    }


}
