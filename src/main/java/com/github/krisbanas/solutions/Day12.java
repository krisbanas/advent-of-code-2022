package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Day12 {

    private boolean[][] visited;
    private String[][] input;

    public Day12() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        input = FileReader.readAsStringGrid("Day12Input.txt");

        visited = new boolean[input.length][input[0].length];

        int currentArea = 0;
        int currentPeri = 0;

        List<Plot> plots = new ArrayList<>();


        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (visited[i][j]) continue;
                plots.add(visit(i, j));
            }
        }

        long result = 0;
        for (Plot p : plots) {
            result += p.area * p.peri;
        }
        return result;
    }

    public Object part2() {
        input = FileReader.readAsStringGrid("Day12Input.txt");
        int rows = input.length;
        int cols = input[0].length;
        visited = new boolean[input.length][input[0].length];

        List<PlotSide> plots = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (visited[i][j]) continue;
                plots.add(visit2(i, j));
            }
        }

        long result = 0;
        for (PlotSide p : plots) {
            long plotResult = 0;
            for (Dir dir : Dir.values()) {
                long oneResult = 0;
                // dla row i gory?
                if (dir == Dir.UP) {
                    List<Siatka> siatkiInLine = p.siatka.stream().filter(x -> x.dir == dir).toList();
                    List<Integer> linie = siatkiInLine.stream().map(x -> x.row).distinct().toList();
                    for (int l : linie) {
                        int[] arr = siatkiInLine.stream().filter(s -> s.row == l).mapToInt(s -> s.col).sorted().toArray();
                        long lineResult = 1;
                        for (int i = 0; i < arr.length - 1; i++) {
                            if (arr[i + 1] - arr[i] > 1) lineResult++;
                        }
                        oneResult += lineResult;
                    }
                }
                if (dir == Dir.DOWN) {
                    List<Siatka> siatkiInLine = p.siatka.stream().filter(x -> x.dir == dir).toList();
                    List<Integer> linie = siatkiInLine.stream().map(x -> x.row).distinct().toList();
                    for (int l : linie) {
                        int[] arr = siatkiInLine.stream().filter(s -> s.row == l).mapToInt(s -> s.col).sorted().toArray();
                        long lineResult = 1;
                        for (int i = 0; i < arr.length - 1; i++) {
                            if (arr[i + 1] - arr[i] > 1) lineResult++;
                        }
                        oneResult += lineResult;
                    }
                }
                if (dir == Dir.LEFT) {
                    List<Siatka> siatkiInLine = p.siatka.stream().filter(x -> x.dir == dir).toList();
                    List<Integer> linie = siatkiInLine.stream().map(x -> x.col).distinct().toList();
                    for (int l : linie) {
                        int[] arr = siatkiInLine.stream().filter(s -> s.col == l).mapToInt(s -> s.row).sorted().toArray();
                        long lineResult = 1;
                        for (int i = 0; i < arr.length - 1; i++) {
                            if (arr[i + 1] - arr[i] > 1) lineResult++;
                        }
                        oneResult += lineResult;
                    }
                }
                if (dir == Dir.RIGHT) {
                    List<Siatka> siatkiInLine = p.siatka.stream().filter(x -> x.dir == dir).toList();
                    List<Integer> linie = siatkiInLine.stream().map(x -> x.col).distinct().toList();
                    for (int l : linie) {
                        int[] arr = siatkiInLine.stream().filter(s -> s.col == l).mapToInt(s -> s.row).sorted().toArray();
                        long lineResult = 1;
                        for (int i = 0; i < arr.length - 1; i++) {
                            if (arr[i + 1] - arr[i] > 1) lineResult++;
                        }
                        oneResult += lineResult;
                    }
                }
                long plotResult1 = oneResult * p.area;
                plotResult += plotResult1;
            }
            result += plotResult;

        }


        return result;
    }

    private PlotSide visit2(int row, int col) {
        if (visited[row][col]) return null;
        visited[row][col] = true;
        int area = 1;
        List<Siatka> siatki = new ArrayList<>();
        if (row < 0 || row == input.length || col < 0 || col == input[0].length) return null;

        if (row == 0) siatki.add(new Siatka(0, col, Dir.UP));
        if (row == input.length - 1) siatki.add(new Siatka(row, col, Dir.DOWN));
        if (col == 0) siatki.add(new Siatka(row, col, Dir.LEFT));
        if (col == input[0].length - 1) siatki.add(new Siatka(row, col, Dir.RIGHT));

        if (row > 0 && !input[row - 1][col].equals(input[row][col])) siatki.add(new Siatka(row, col, Dir.UP));
        if (col > 0 && !input[row][col - 1].equals(input[row][col])) siatki.add(new Siatka(row, col, Dir.LEFT));
        if (row < input.length - 1 && !input[row + 1][col].equals(input[row][col])) siatki.add(new Siatka(row, col, Dir.DOWN));
        if (col < input.length - 1 && !input[row][col + 1].equals(input[row][col])) siatki.add(new Siatka(row, col, Dir.RIGHT));

        if (row > 0 && input[row][col].equals(input[row - 1][col])) {
            var plot = visit2(row - 1, col);
            if (plot != null) {
                area += plot.area;
                siatki.addAll(plot.siatka);
            }
        }
        if (col > 0 && input[row][col].equals(input[row][col - 1])) {
            var plot = visit2(row, col - 1);
            if (plot != null) {
                area += plot.area;
                siatki.addAll(plot.siatka);
            }
        }
        if (row < input.length - 1 && input[row][col].equals(input[row + 1][col])) {
            var plot = visit2(row + 1, col);
            if (plot != null) {
                area += plot.area;
                siatki.addAll(plot.siatka);
            }
        }
        if (col < input[0].length - 1 && input[row][col].equals(input[row][col + 1])) {
            var plot = visit2(row, col + 1);
            if (plot != null) {
                area += plot.area;
                siatki.addAll(plot.siatka);
            }
        }

        visited[row][col] = true;
        return new PlotSide(area, siatki);
    }

    private Plot visit(int i, int j) {
        if (visited[i][j]) return null;
        visited[i][j] = true;
        int area = 1;
        int peri = 0;
        if (i < 0 || i == input.length || j < 0 || j == input[0].length) return null;
        if (i == 0 || i == input.length - 1) peri++;
        if (j == 0 || j == input[0].length - 1) peri++;
        if (i > 0 && !input[i - 1][j].equals(input[i][j])) peri++;
        if (j > 0 && !input[i][j - 1].equals(input[i][j])) peri++;
        if (i < input.length - 1 && !input[i + 1][j].equals(input[i][j])) peri++;
        if (j < input.length - 1 && !input[i][j + 1].equals(input[i][j])) peri++;

        if (i > 0 && input[i][j].equals(input[i - 1][j])) {
            var plot = visit(i - 1, j);
            if (plot != null) {
                area += plot.area;
                peri += plot.peri;
            }
        }
        if (j > 0 && input[i][j].equals(input[i][j - 1])) {
            var plot = visit(i, j - 1);
            if (plot != null) {
                area += plot.area;
                peri += plot.peri;
            }
        }
        if (i < input.length - 1 && input[i][j].equals(input[i + 1][j])) {
            var plot = visit(i + 1, j);
            if (plot != null) {
                area += plot.area;
                peri += plot.peri;
            }
        }
        if (j < input[0].length - 1 && input[i][j].equals(input[i][j + 1])) {
            var plot = visit(i, j + 1);
            if (plot != null) {
                area += plot.area;
                peri += plot.peri;
            }
        }

        visited[i][j] = true;
        return new Plot(area, peri);
    }

    record Plot(int area, int peri) {
    }

    record PlotSide(int area, List<Siatka> siatka) {
    }

    record Siatka(int row, int col, Dir dir) {
    }

    enum Dir {UP, DOWN, LEFT, RIGHT}
}
