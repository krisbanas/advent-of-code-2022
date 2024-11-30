package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.*;

public class Day13 {

    public Day13() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part2() {
        String input = FileReader.readAsString("Day13Input.txt");
        String[] singleInput = input.split("\r\n\r\n");
        List<Game> games = new ArrayList<>();

        for (String single : singleInput) {
            String[] lines = single.split("\r\n");
            String[] line0 = lines[0].replaceAll("Button A: X+", "").replaceAll(" Y+", "").split(",");
            Point movA = new Point(Integer.parseInt(line0[0]), Integer.parseInt(line0[1]));
            String[] line1 = lines[1].replaceAll("Button B: X+", "").replaceAll(" Y+", "").split(",");
            Point movB = new Point(Integer.parseInt(line1[0]), Integer.parseInt(line1[1]));
            String[] target = lines[2].replaceAll("Prize: X=", "").replaceAll(" Y=", "").split(",");
            Point tg = new Point(Integer.parseInt(target[0]) + 10000000000000L, Integer.parseInt(target[1]) + 10000000000000L);
            Game game = new Game(tg, movA, movB);
            games.add(game);
        }

        long result = 0;
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            System.out.println("Sim game number: " + i + " from: " + games.size());
            long res = simulate2(game);
            if (res != -1) {
                result += res;
            }
        }
        return result;
    }

    private long simulate2(Game game) {
        // sry for polish but dzielna is divided by dzielnik, the rest is 2 equations with 2 unknowns.
        // układ dwóch równań liniowe
        Point a = game.a;
        Point b = game.b;

        long dzielna = game.target.row() * a.col() - game.target.col() * a.row();
        long dzielnik = a.col() * b.row() - a.row() * b.col();

        if (dzielna % dzielnik != 0) return -1;
        long numberOfB = dzielna / dzielnik;

        long dzielnaA = game.target.row - numberOfB * b.row();
        long dzielnikA = a.row;

        if (dzielnaA % dzielnikA != 0) return -1;
        long numberOfA = dzielnaA / dzielnikA;

        return numberOfA * 3 + numberOfB;
    }

    public Object part1() {
        String input = FileReader.readAsString("Day13Input.txt");
        String[] singleInput = input.split("\r\n\r\n");
        List<Game> games = new ArrayList<>();
        for (String single : singleInput) {
            String[] lines = single.split("\r\n");
            String[] line0 = lines[0].replaceAll("Button A: X+", "").replaceAll(" Y+", "").split(",");
            Point movA = new Point(Integer.parseInt(line0[0]), Integer.parseInt(line0[1]));
            String[] line1 = lines[1].replaceAll("Button B: X+", "").replaceAll(" Y+", "").split(",");
            Point movB = new Point(Integer.parseInt(line1[0]), Integer.parseInt(line1[1]));
            String[] target = lines[2].replaceAll("Prize: X=", "").replaceAll(" Y=", "").split(",");
            Point tg = new Point(Integer.parseInt(target[0]), Integer.parseInt(target[1]));
            Game game = new Game(tg, movA, movB);
            games.add(game);
        }


        long result = 0;
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            System.out.println("Sim game number: " + i + " from: " + games.size());
            long res = simulate(game);
            if (res != -1) {
                result += res;
            }
        }
        return result;
    }

    private long simulate(Game game) {
        Queue<State> queue = new ArrayDeque<>();
        Map<Point, Integer> costMap = new HashMap<>();
        queue.add(new State(new Point(0, 0), 0, new int[]{0, 0}));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            Integer currPriceMemo = costMap.getOrDefault(current.pos, Integer.MAX_VALUE);
            if (currPriceMemo <= current.price) continue;
            costMap.put(current.pos, current.price);

            Point posA = new Point(current.pos.row() + game.a.row(), current.pos.col() + game.a.col());
            Point posB = new Point(current.pos.row() + game.b.row(), current.pos.col() + game.b.col());

            State pressedA = new State(posA, current.price + 3, new int[]{current.times[0] + 1, current.times[1]});
            State pressedB = new State(posB, current.price + 1, new int[]{current.times[0], current.times[1] + 1});

            if (pressedA.times[0] < 100
                    && pressedA.times[1] < 100
                    && pressedA.pos.row() <= game.target.row()
                    && pressedA.pos.col() <= game.target.col()
                    && costMap.getOrDefault(pressedA.pos, Integer.MAX_VALUE) > pressedA.price
            ) queue.offer(pressedA);

            if (pressedB.times[0] < 100
                    && pressedB.times[1] < 100
                    && pressedB.pos.row() <= game.target.row()
                    && pressedB.pos.col() <= game.target.col()
                    && costMap.getOrDefault(pressedB.pos, Integer.MAX_VALUE) > pressedA.price
            ) queue.offer(pressedB);
        }

        return costMap.getOrDefault(game.target, -1);
    }

    record Game(Point target, Point a, Point b) {
    }

    record State(Point pos, int price, int[] times) {
    }

    record Point(long row, long col) {
    }
}
