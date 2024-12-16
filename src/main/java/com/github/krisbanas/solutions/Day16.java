package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;
import com.github.krisbanas.toolbox.Point;

import java.util.*;

public class Day16 {

    public Day16() {
//        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        String[] input = FileReader.readAsString("16.txt").split("\r\n\r\n");
        String[] inputMap = input[0].split("\r\n");
        String[][] map = new String[inputMap.length][inputMap[0].length()];

        Point start = new Point(0, 0);
        Point end = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            String[] row = inputMap[i].split("");
            for (int j = 0; j < row.length; j++) {
                map[i][j] = row[j];
                if (Objects.equals(map[i][j], "E")) end = new Point(i, j);
                if (Objects.equals(map[i][j], "S")) start = new Point(i, j);
            }
        }

        print(map);

        Map<State, Long> states = new HashMap<>();
        State startState = new State(start, Dir.RIGHT);
        Queue<State> queue = new LinkedList<>();
        queue.offer(startState);
        states.put(startState, 0L);

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            Dir dir = curr.dir;
            long cost = states.get(curr);

            // 3 possibilities. 1) Go forward!
            Point movement = getMovement(dir);
            Point goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                State goForward = new State(goForwardPos, dir);
                if (states.containsKey(goForward)) {
                    long costSoFar = states.get(goForward);
                    if (costSoFar > cost + 1) {
                        states.put(goForward, cost + 1);
                        queue.offer(goForward);
                    }
                } else {
                    states.put(goForward, cost + 1);
                    queue.offer(goForward);
                }
            }

            // turn right!
            Dir turnedRight = turnRight(dir);
            State turnedRightState = new State(curr.point, turnedRight);
            // if there's a wall, ignore
            movement = getMovement(turnedRight);
            goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                // it's worth considering
                if (states.containsKey(turnedRightState)) {
                    long costSoFar = states.get(turnedRightState);
                    if (costSoFar > cost + 1000) {
                        states.put(turnedRightState, cost + 1000);
                        queue.offer(turnedRightState);
                    }
                } else {
                    states.put(turnedRightState, cost + 1000);
                    queue.offer(turnedRightState);
                }
            }

            // turn right!
            Dir turnedLeft = turnLeft(dir);
            State turnedLeftState = new State(curr.point, turnedLeft);
            // if there's a wall, ignore
            movement = getMovement(turnedLeft);
            goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                // it's worth considering
                if (states.containsKey(turnedLeftState)) {
                    long costSoFar = states.get(turnedLeftState);
                    if (costSoFar > cost + 1000) {
                        states.put(turnedLeftState, cost + 1000);
                        queue.offer(turnedLeftState);
                    }
                } else {
                    states.put(turnedLeftState, cost + 1000);
                    queue.offer(turnedLeftState);
                }
            }
            System.out.println("Iteration");
        }

        List<State> possibleEndStates = new ArrayList<>();
        possibleEndStates.add(new State(end, Dir.RIGHT));
        possibleEndStates.add(new State(end, Dir.LEFT));
        possibleEndStates.add(new State(end, Dir.UP));
        possibleEndStates.add(new State(end, Dir.DOWN));

        long result = Long.MAX_VALUE;
        for (State state : possibleEndStates) {
            long val = states.getOrDefault(state, Long.MAX_VALUE);
            result = Math.min(result, val);
        }

        return result;
    }

    private void print(String[][] grid) {
        for (String[] strings : grid) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
        System.out.println();
    }

    enum Dir {UP, RIGHT, LEFT, DOWN}

    private Dir turnRight(Dir dir) {
        return switch (dir) {
            case LEFT -> Dir.UP;
            case RIGHT -> Dir.DOWN;
            case UP -> Dir.RIGHT;
            case DOWN -> Dir.LEFT;
        };
    }

    private Dir turnLeft(Dir dir) {
        return switch (dir) {
            case LEFT -> Dir.DOWN;
            case RIGHT -> Dir.UP;
            case UP -> Dir.LEFT;
            case DOWN -> Dir.RIGHT;
        };
    }

    private Point getMovement(Dir dir) {
        return switch (dir) {
            case LEFT -> new Point(0, -1);
            case RIGHT -> new Point(0, 1);
            case UP -> new Point(-1, 0);
            case DOWN -> new Point(1, 0);
        };
    }

    record State(Point point, Dir dir) {
    }

    public Object part2() {
        String[] input = FileReader.readAsString("16.txt").split("\r\n\r\n");
        String[] inputMap = input[0].split("\r\n");
        String[][] map = new String[inputMap.length][inputMap[0].length()];

        Point start = new Point(0, 0);
        Point end = new Point(0, 0);
        for (int i = 0; i < map.length; i++) {
            String[] row = inputMap[i].split("");
            for (int j = 0; j < row.length; j++) {
                map[i][j] = row[j];
                if (Objects.equals(map[i][j], "E")) end = new Point(i, j);
                if (Objects.equals(map[i][j], "S")) start = new Point(i, j);
            }
        }

        print(map);

        Map<State, Result> states = new HashMap<>();
        State startState = new State(start, Dir.RIGHT);
        Queue<State> queue = new LinkedList<>();
        queue.offer(startState);
        states.put(startState, new Result(0L, Set.of(start)));

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            Dir dir = curr.dir;
            Result result = states.get(curr);
            long cost = result.cost;
            Set<Point> path = result.path;

            // 3 possibilities. 1) Go forward!
            Point movement = getMovement(dir);
            Point goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                State goForward = new State(goForwardPos, dir);
                if (states.containsKey(goForward)) {
                    long costSoFar = states.get(goForward).cost;
                    if (costSoFar == cost + 1) {
                        Set<Point> seenAtArrivalPlace = states.get(goForward).path;
                        Set<Point> newPath = new HashSet<>(path);
                        newPath.addAll(seenAtArrivalPlace);
                        newPath.add(goForwardPos); // do nothin?
                        states.put(goForward, new Result(cost + 1, newPath));
                        queue.offer(goForward);
                    } else if (costSoFar >= cost + 1) {
                        Set<Point> newPath = new HashSet<>(path);
                        newPath.add(goForwardPos);
                        states.put(goForward, new Result(cost + 1, newPath));
                        queue.offer(goForward);
                    }
                } else {
                    Set<Point> newPath = new HashSet<>(path);
                    newPath.add(goForwardPos);
                    states.put(goForward, new Result(cost + 1, newPath));
                    queue.offer(goForward);
                }
            }

            // turn right!
            Dir turnedRight = turnRight(dir);
            State turnedRightState = new State(curr.point, turnedRight);
            // if there's a wall, ignore
            movement = getMovement(turnedRight);
            goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                // it's worth considering
                if (states.containsKey(turnedRightState)) {
                    long costSoFar = states.get(turnedRightState).cost;
                    if (costSoFar == cost + 1000) {
                        Set<Point> seenAtArrivalPlace = states.get(turnedRightState).path;
                        Set<Point> newPath = new HashSet<>(path);
                        newPath.addAll(seenAtArrivalPlace);
                        newPath.add(goForwardPos); // do nothin?
                        states.put(turnedRightState, new Result(cost + 1000, newPath));
                        queue.offer(turnedRightState);
                    } else if (costSoFar >= cost + 1000) {
                        Set<Point> newPath = new HashSet<>(path);
                        states.put(turnedRightState, new Result(cost + 1000, newPath));
                        queue.offer(turnedRightState);
                    }
                } else {
                    Set<Point> newPath = new HashSet<>(path);
                    states.put(turnedRightState, new Result(cost + 1000, newPath));
                    queue.offer(turnedRightState);
                }
            }

            // turn right!
            Dir turnedLeft = turnLeft(dir);
            State turnedLeftState = new State(curr.point, turnedLeft);
            // if there's a wall, ignore
            movement = getMovement(turnedLeft);
            goForwardPos = new Point(curr.point.row() + movement.row(), curr.point.col() + movement.col());
            if (!map[goForwardPos.row()][goForwardPos.col()].equals("#")) {
                // it's worth considering
                if (states.containsKey(turnedLeftState)) {
                    long costSoFar = states.get(turnedLeftState).cost;
                    if (costSoFar == cost + 1000) {
                        Set<Point> seenAtArrivalPlace = states.get(turnedLeftState).path;
                        Set<Point> newPath = new HashSet<>(path);
                        newPath.addAll(seenAtArrivalPlace);
                        newPath.add(goForwardPos); // do nothin?
                        states.put(turnedLeftState, new Result(cost + 1000, newPath));
                        queue.offer(turnedLeftState);
                    } else if (costSoFar >= cost + 1000) {
                        Set<Point> newPath = new HashSet<>(path);
                        states.put(turnedLeftState, new Result(cost + 1000, newPath));
                        queue.offer(turnedLeftState);
                    }
                } else {
                    Set<Point> newPath = new HashSet<>(path);
                    states.put(turnedLeftState, new Result(cost + 1000, newPath));
                    queue.offer(turnedLeftState);
                }
            }
//            System.out.println("Iteration");
        }

        List<State> possibleEndStates = new ArrayList<>();
        possibleEndStates.add(new State(end, Dir.RIGHT));
        possibleEndStates.add(new State(end, Dir.LEFT));
        possibleEndStates.add(new State(end, Dir.UP));
        possibleEndStates.add(new State(end, Dir.DOWN));

        Result result = new Result(Long.MAX_VALUE, Set.of());
        for (State state : possibleEndStates) {
            Result candResult = states.get(state);
            if (candResult == null) {continue;}
            result = result.cost > candResult.cost ? candResult : result;
        }

        Set<Point> points = result.path;
        for (int i = 0; i < map.length; i++) {
            String[] strings = map[i];
            for (int j = 0; j < strings.length; j++) {
                String string = strings[j];
                if (points.contains(new Point(i, j))) {
                    System.out.print("O");
                } else {
                    System.out.print(string);
                }
            }
            System.out.println();
        }
        System.out.println();

        return result.path.size();
    }

    record Result(long cost, Set<Point> path) {
    }

}
