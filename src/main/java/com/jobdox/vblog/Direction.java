package com.jobdox.vblog;

/**
 * Created by umesh on 12/20/14.
 */
public enum Direction {
    CURRENT (0),
    PREVIOUS(-1),
    NEXT(1);

    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    private double direction() { return direction; }

}
