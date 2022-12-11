package com.github.krisbanas.toolbox;

public record Point(int row, int col) {
    public Direction findDirectionToTarget(Point target) {
        if (target.row() > this.row && target.col() == this.col) return Direction.EAST;
        if (target.row() < this.row && target.col() == col) return Direction.WEST;
        if (target.row() == this.row && target.col() > col) return Direction.SOUTH;
        if (target.row() == this.row && target.col() < col) return Direction.NORTH;
        if (target.row() > this.row && target.col() > col) return Direction.SOUTHEAST;
        if (target.row() < this.row && target.col() < col) return Direction.NORTHWEST;
        if (target.row() < this.row && target.col() > col) return Direction.SOUTHWEST;
        if (target.row() > this.row && target.col() < col) return Direction.NORTHEAST;
        return Direction.CENTER;
    }
}
