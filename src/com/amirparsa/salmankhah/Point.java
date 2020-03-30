package com.amirparsa.salmankhah;

/**
 * Represents a point with it's x and y.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Point {
    //X of point
    private int x;
    //Y of point
    private int y;

    /**
     * Constructor without any parameter. Makes the origin point.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Constructor with 2 parameters
     *
     * @param x X of point
     * @param y Y of point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X setter
     *
     * @param x X of point
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Y setter
     *
     * @param y Y of point
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * X getter
     *
     * @return X of point
     */
    public int getX() {
        return x;
    }

    /**
     * Y getter
     *
     * @return y Y of point
     */
    public int getY() {
        return y;
    }

    /**
     * Checks the equality between the point and other point.
     *
     * @param otherPoint Other point
     * @return true if they are equal and false if not.
     */
    public boolean equals(Point otherPoint) {
        return x == otherPoint.getX() && y == otherPoint.getY();
    }

    /**
     * Checks that the point is inside the othello map.
     *
     * @return true if yes and false if not.
     */
    public boolean isValid() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    /**
     * Checks that the other point is the points's neighbor.
     *
     * @param secondPoint Other point
     * @return true if yes and false if not.
     */
    public boolean isNeighbor(Point secondPoint) {
        return Math.abs(x - secondPoint.getX()) < 2 && Math.abs(y - secondPoint.getY()) < 2;
    }

    /**
     * Finds the reflection of the point with respect to the other point.
     *
     * @param otherPoint Other point
     * @return Reflection point
     */
    public Point reflection(Point otherPoint) {
        int x = otherPoint.getX() * 2 - this.x;
        int y = otherPoint.getY() * 2 - this.y;
        return new Point(x, y);
    }

    /**
     * Copy other point fields to this point.
     *
     * @param otherPoint Other point
     */
    public void copy(Point otherPoint) {
        x = otherPoint.getX();
        y = otherPoint.getY();
    }

    /**
     * Prints the point in Othello points style.
     */
    public void print() {
        System.out.println("(" + (y + 1) + ", " + (char) (x + 'A') + ")");
    }

}

