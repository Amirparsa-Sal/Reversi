package com.amirparsa.salmankhah;
import java.util.ArrayList;
class Player {
    String name;
    char sign;
    Board board;

    public Player(String name, char sign){
        this.name = name;
        this.sign = sign;
        this.board = null;
    }

    public char getSign(){
        return sign;
    }

    public String getName(){
        return name;
    }

    public void setSign(char sign){
        this.sign = sign;
    }

    public void setName(String name){
        this.name = name;
    }

    /*
    public ArrayList<Point> availableMoves(){
    }
    public void placeDisk(Point position);
    public ArrayList<Disk> getDisks();
    public ArrayList<Disk> getOpponentDisks();
     */
}
