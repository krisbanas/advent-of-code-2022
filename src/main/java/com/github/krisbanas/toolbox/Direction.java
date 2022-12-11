package com.github.krisbanas.toolbox;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Written by SimonBaars. I merely borrowed it because it's awesome!
 */
public enum Direction {
    NORTH(1, 'U'),
    EAST(4, 'R'),
    SOUTH(2, 'D'),
    WEST(3, 'L'),
    CENTER(8, 'C'),
    NORTHEAST(4, 'E'),
    SOUTHEAST(5, 'E'),
    SOUTHWEST(6, 'E'),
    NORTHWEST(7, 'E');

    public final int num;
    public final int code;

    Direction(int num, char code) {
        this.num = num;
        this.code = code;
    }

    public static Direction getByDirCode(char code) {
        return Arrays.stream(values()).filter(e -> e.code == code).findAny().get();
    }

    public static Direction getByDir(char code) {
        return Arrays.stream(values()).filter(e -> e.name().charAt(0) == code).findAny().get();
    }

    public static Direction getByMove(Point from, Point to) {
        if (to.row() > from.row()) return EAST;
        else if (to.row() < from.row()) return WEST;
        else if (to.col() > from.col()) return SOUTH;
        else if (to.col() < from.col()) return NORTH;
        else return CENTER;
    }

    public static Point turn(Point w, boolean b) {
        return b ? new Point(-w.col(), w.row()) : new Point(w.col(), -w.row());
    }

    public static Point turnDegrees(Point w, int distance, boolean b) {
        int num = distance % 360;
        while (num > 0) {
            w = turn(w, b);
            num -= 90;
        }
        return w;
    }

    public static Point turnDegrees(Point w, int distance) {
        return turnDegrees(w, abs(distance), distance > 0);
    }

    public static Direction[] fourDirections() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }

    public static Direction[] eightDirections() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
    }

    public Direction turn(boolean right) {
        int cur = ordinal() + (right ? 1 : -1);
        if (cur == fourDirections().length) cur = 0;
        else if (cur == -1) cur = fourDirections().length - 1;
        return fourDirections()[cur];
    }

    public Point move(Point currentLocation, int amount) {
        return switch (this) {
            case SOUTH -> new Point(currentLocation.row(), currentLocation.col() + amount);
            case NORTH -> new Point(currentLocation.row(), currentLocation.col() - amount);
            case EAST -> new Point(currentLocation.row() + amount, currentLocation.col());
            case WEST -> new Point(currentLocation.row() - amount, currentLocation.col());
            case SOUTHWEST -> new Point(currentLocation.row() - amount, currentLocation.col() + amount);
            case NORTHEAST -> new Point(currentLocation.row() + amount, currentLocation.col() - amount);
            case SOUTHEAST -> new Point(currentLocation.row() + amount, currentLocation.col() + amount);
            case NORTHWEST -> new Point(currentLocation.row() - amount, currentLocation.col() - amount);
            case CENTER -> new Point(currentLocation.row(), currentLocation.col());
        };
    }

    public Point moveFix(Point currentLocation, int amount) {
        return switch (this) {
            case SOUTH -> new Point(currentLocation.row(), currentLocation.col() + amount);
            case NORTH -> new Point(currentLocation.row(), currentLocation.col() - amount);
            case EAST -> new Point(currentLocation.row() - amount, currentLocation.col());
            case WEST -> new Point(currentLocation.row() + amount, currentLocation.col());
            case SOUTHWEST -> new Point(currentLocation.row() + amount, currentLocation.col() + amount);
            case NORTHEAST -> new Point(currentLocation.row() - amount, currentLocation.col() - amount);
            case SOUTHEAST -> new Point(currentLocation.row() - amount, currentLocation.col() + amount);
            case NORTHWEST -> new Point(currentLocation.row() + amount, currentLocation.col() - amount);
            case CENTER -> new Point(currentLocation.row(), currentLocation.col());
        };
    }

    public char getInGrid(char[][] grid, Point p, char none) {
        if (p.row() >= 0 && p.row() < grid.length && p.col() >= 0 && p.col() < grid[0].length) {
            return grid[p.row()][p.col()];
        }
        return none;
    }

    public long getInGrid(long[][] grid, Point p, int none) {
        p = this.move(p);
        if (p.row() >= 0 && p.row() < grid.length && p.col() >= 0 && p.col() < grid[0].length) {
            return grid[p.row()][p.col()];
        }
        return none;
    }

    public char getInGrid(char[][] grid, Point p) {
        return getInGrid(grid, p, '.');
    }

    public Point move(Point currentLocation) {
        return move(currentLocation, 1);
    }

    public Point moveFix(Point currentLocation) {
        return moveFix(currentLocation, 1);
    }

    public Direction reverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTHEAST -> SOUTHWEST;
            case SOUTHWEST -> NORTHEAST;
            case SOUTHEAST -> NORTHWEST;
            case NORTHWEST -> SOUTHEAST;
            case CENTER -> CENTER;
        };
    }

    public boolean leftOf(Direction robotDir) {
        int n = this.ordinal() - 1;
        if (n == -1) n = values().length - 1;
        return robotDir.ordinal() == n;
    }

    public Direction turnDegrees(int degrees, boolean right) {
        int num = degrees % 360;
        Direction dir = this;
        while (num > 0) {
            dir = turn(right);
            num -= 90;
        }
        return dir;
    }

    public Direction turnDegrees(int degrees) {
        return turnDegrees(abs(degrees), degrees > 0);
    }
}