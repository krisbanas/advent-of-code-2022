package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.Direction;
import com.github.krisbanas.toolbox.NumGrid;
import com.github.krisbanas.toolbox.Point;
import com.github.krisbanas.toolbox.FileReader;

import static java.lang.System.out;

public class Day08 {

    private final NumGrid numericGrid;

    public Day08() {
        numericGrid = new NumGrid(FileReader.readAsString("Day8Input.txt"));
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        return numericGrid.stream().filter(this::canBeSeen).count();
    }

    private Object part2() {
        return numericGrid.stream().mapToLong(this::scoreTree).max().orElseThrow();
    }

    private boolean canBeSeen(Point tree) {
        Direction[] directions = Direction.fourDirections();
        long targetHeight = numericGrid.getValue(tree);
        for (Direction direction : directions) {
            Point pointerTree = tree;
            while (true) {
                pointerTree = direction.move(pointerTree);
                long pointerTreeHeight = numericGrid.getValue(pointerTree);
                if (pointerTreeHeight >= targetHeight) break;
                if (pointerTreeHeight == -1) return true;
            }
        }
        return false;
    }

    private long scoreTree(Point treePoint) {
        final var directions = Direction.fourDirections();
        long score = 1;
        final var treeHeight = numericGrid.getValue(treePoint);
        for (Direction direction : directions) {
            long directionScore = 0;
            long nextTreeHeight = 0;
            Point nextTree = treePoint;
            while (nextTreeHeight < treeHeight) {
                nextTree = direction.move(nextTree);
                nextTreeHeight = numericGrid.getValue(nextTree);
                if (nextTreeHeight == -1) break;
                directionScore++;
            }
            if (directionScore > 0) score *= directionScore;
        }
        return score;
    }
}
