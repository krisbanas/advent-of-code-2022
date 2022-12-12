package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.Direction;
import com.github.krisbanas.toolbox.Point;
import com.github.krisbanas.toolbox.FileReader;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class Day09 {
    private final List<Move> moves;

    public Day09() {
        moves = FileReader.readAsStringList("Day9Input.txt")
                .stream().map(x -> x.split(" "))
                .map(x -> new Move(x[0], Integer.parseInt(x[1])))
                .toList();
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        Set<Point> visited = moveRope(1);
        return visited.size();
    }

    private Object part2() {
        Set<Point> visited = moveRope(9);
        return visited.size();
    }

    private boolean shouldMove(Point head, Point tail) {
        return Math.pow(head.row() - tail.row(), 2) + Math.pow(head.col() - tail.col(), 2) > 2;
    }

    private Set<Point> moveRope(int ropeSize) {
        Point head = new Point(0, 0);
        List<Point> rope = new ArrayList<>(IntStream.iterate(0, i -> i + 1).limit(ropeSize + 1).mapToObj(i -> new Point(0, 0)).toList());
        rope.set(0, head);
        Set<Point> visited = new HashSet<>();

        for (Move m : moves) {
            Direction dir = getDirectionByLetter(m.dir);
            for (int i = 0; i < m.length; i++) {
                head = dir.move(head);
                rope.set(0, head);

                for (int j = 0; j < rope.size() - 1; j++) {
                    Point pulling = rope.get(j);
                    Point follower = rope.get(j + 1);
                    Point newPoint = moveFollower(pulling, follower);
                    rope.set(j + 1, newPoint);
                }

                visited.add(rope.get(ropeSize));
            }
        }
        return visited;
    }

    private Direction getDirectionByLetter(String letter) {
        return switch (letter) {
            case "L" -> Direction.WEST;
            case "R" -> Direction.EAST;
            case "U" -> Direction.NORTH;
            case "D" -> Direction.SOUTH;
            default -> Direction.CENTER;
        };
    }

    private Point moveFollower(Point target, Point follower) {
        if (target.equals(follower)) return follower;
        if (!shouldMove(target, follower)) return follower;
        Direction directionToTarget = follower.findDirectionToTarget(target);
        return directionToTarget.move(follower);
    }

    record Move(String dir, int length) { }
}
