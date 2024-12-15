package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.*;

public class Day08 {

    public Day08() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String[][] input = FileReader.readAsStringGrid("Day8Input.txt");
        int rows = input.length;
        int cols = input[0].length;

        Map<String, List<Point>> antennas = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                String e = input[i][j];
                if (e.equals(".")) continue;
                List<Point> list = antennas.getOrDefault(e, new ArrayList<>());
                Point p = new Point(i, j);
                list.add(p);
                antennas.put(e, list);
            }
        }

        Set<Point> antinodes = new HashSet<>();

        antennas.values().forEach(points -> {
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point one = points.get(i);
                    Point two = points.get(j);

                    int rowVec = Math.abs(one.row() - two.row());
                    int colVec = Math.abs(one.col() - two.col());


                    Point three = new Point(
                            one.row() > two.row() ? one.row() + rowVec : one.row() - rowVec,
                            one.col() > two.col() ? one.col() + colVec : one.col() - colVec
                    );
                    Point four = new Point(
                            two.row() > one.row() ? two.row() + rowVec : two.row() - rowVec,
                            two.col() > one.col() ? two.col() + colVec : two.col() - colVec
                    );
                    if (three.row() >= 0 && three.row() < rows && three.col() >= 0 && three.col() < cols) {
                        antinodes.add(three);
                        input[three.row()][three.col()] = "#";
                    }
                    if (four.row() >= 0 && four.row() < rows && four.col() >= 0 && four.col() < cols) {
                        antinodes.add(four);
                        input[four.row()][four.col()] = "#";
                    }
                }
            }
        });

        for (String[] str : input) {
            for (String s : str) {
                System.out.print(s);
            }
            System.out.println();
        }

        return antinodes.size();
    }

    public Object part2() {
        String[][] input = FileReader.readAsStringGrid("Day8Input.txt");
        int rows = input.length;
        int cols = input[0].length;

        Map<String, List<Point>> antennas = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                String e = input[i][j];
                if (e.equals(".")) continue;
                if (e.equals("#")) continue;
                List<Point> list = antennas.getOrDefault(e, new ArrayList<>());
                Point p = new Point(i, j);
                list.add(p);
                antennas.put(e, list);
            }
        }

        Set<Point> antinodes = new HashSet<>();

        antennas.values().forEach(points -> {
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point one = points.get(i);
                    Point two = points.get(j);

                    int rowVec = Math.abs(one.row() - two.row());
                    int colVec = Math.abs(one.col() - two.col());

                    boolean inGrid = true;
                    int count = 1;
                    while (inGrid) {
                        Point three = new Point(
                                one.row() > two.row() ? one.row() + count * rowVec : one.row() - count * rowVec,
                                one.col() > two.col() ? one.col() + count * colVec : one.col() - count * colVec
                        );
                        if (three.row() >= 0 && three.row() < rows && three.col() >= 0 && three.col() < cols) {
                            antinodes.add(three);
                            input[three.row()][three.col()] = "#";
                            count++;
                        } else {
                            inGrid = false;
                        }
                    }

                    inGrid = true;
                    count = 1;
                    while (inGrid) {
                        Point four = new Point(
                                two.row() > one.row() ? two.row() + count * rowVec : two.row() - count * rowVec,
                                two.col() > one.col() ? two.col() + count * colVec : two.col() - count * colVec
                        );

                        if (four.row() >= 0 && four.row() < rows && four.col() >= 0 && four.col() < cols) {
                            antinodes.add(four);
                            input[four.row()][four.col()] = "#";
                            count++;
                        } else {
                            inGrid = false;
                        }
                    }
                }
            }

            for (Point four : points) {
                input[four.row()][four.col()] = "#";
                antinodes.add(four);
            }
        });

        for (String[] str : input) {
            for (String s : str) {
                System.out.print(s);
            }
            System.out.println();
        }

        return antinodes.size();
    }
}
