package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    public static void main(String[] args) {
        new Day04();
    }

    public Day04() {
        System.out.println(part1());
        System.out.println("should be: " + 2464);
        System.out.println(part2());
        System.out.println("should be: " + 1982);
    }

    public Object part1() {
        char[][] in = FileReader.readAsCharsGrid("4.txt");
        int count = 0;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {
                if (i < in.length - 4 && in[i][j] == 'X' && in[i + 1][j] == 'M' && in[i + 2][j] == 'A' && in[i + 3][j] == 'S')
                    count++;

                if (i >= 3 && in[i][j] == 'X' && in[i - 1][j] == 'M' && in[i - 2][j] == 'A' && in[i - 3][j] == 'S')
                    count++;

                if (j < in[0].length - 4 && in[i][j] == 'X' && in[i][j + 1] == 'M' && in[i][j + 2] == 'A' && in[i][j + 3] == 'S')
                    count++;

                if (j >= 3 && in[i][j] == 'X' && in[i][j - 1] == 'M' && in[i][j - 2] == 'A' && in[i][j - 3] == 'S')
                    count++;

                if (i >= 3 && j >= 3 && in[i][j] == 'X' && in[i - 1][j - 1] == 'M' && in[i - 2][j - 2] == 'A' && in[i - 3][j - 3] == 'S')
                    count++;

                if (i < in.length - 4 && j < in[0].length - 4 && in[i][j] == 'X' && in[i + 1][j + 1] == 'M' && in[i + 2][j + 2] == 'A' && in[i + 3][j + 3] == 'S')
                    count++;

                if (i < in.length - 4 && j >= 3 && in[i][j] == 'X' && in[i + 1][j - 1] == 'M' && in[i + 2][j - 2] == 'A' && in[i + 3][j - 3] == 'S')
                    count++;

                if (i >= 3 && j < in[0].length - 4 && in[i][j] == 'X' && in[i - 1][j + 1] == 'M' && in[i - 2][j + 2] == 'A' && in[i - 3][j + 3] == 'S')
                    count++;

            }
        }

        return count;
    }

    public Object part2() {
        char[][] in = FileReader.readAsCharsGrid("4.txt");
        int count = 0;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {

            }
        }
        return count;
    }
}

