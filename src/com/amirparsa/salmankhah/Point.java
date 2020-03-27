package com.amirparsa.salmankhah;

/**
 * Represents a point with x and y
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Point {
    //X of point
    int x;
    //Y of point
    int y;

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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isValid(){
        if(x>=0 && x<8 && y>=0 && y<8)
            return true;
        return false;
    }
}
