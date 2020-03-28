package com.amirparsa.salmankhah;

/**
 * Represents a point with x and y
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Point {
    //X of point
    private int x;
    //Y of point
    private int y;

    public Point() {
        this(0, 0);

    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isValid() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public void print() {
        System.out.println("(" + (y+1) + ", " + (char)(x + 'A') + ")");
    }

    public boolean isNeighbor(Point secondPoint) {
        return Math.abs(x - secondPoint.getX()) < 2 && Math.abs(y - secondPoint.getY()) < 2;
    }

    public Point reflection(Point secondPoint) {
        int x = secondPoint.getX() * 2 - this.x;
        int y = secondPoint.getY() * 2 - this.y;
        return new Point(x, y);
    }
}
