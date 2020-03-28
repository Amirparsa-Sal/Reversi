package com.amirparsa.salmankhah;

/**
 * Represents a Disk in game
 */
public class Disk {
    //Sign of the disk
    char sign;
    //Position of the disk
    Point position;

    public Disk(){
        this('\0',0,0);
    }

    public Disk(char sign, int x, int y){
        this.sign = sign;
        position = new Point(x,y);
    }

    public void setSign(char sign){
        this.sign = sign;
    }

    public void setPosition(int x,int y){
        position.setX(x);
        position.setY(y);
    }

    public char getSign(){
        return sign;
    }

    public Point getPosition(){
        return position;
    }
}
