package com.amirparsa.salmankhah;

import java.util.ArrayList;

class Player {
    String name;
    char sign;
    Board board;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.board = null;
    }

    public char getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Point> availableMoves() {
        ArrayList<Disk> playerDisks = getDisks();
        ArrayList<Disk> opponentDisks = getOpponentDisks();
        ArrayList<Point> availableDisks = new ArrayList<>();
        for (Disk playerDisk : playerDisks) {
            for (Disk opponentDisk : opponentDisks) {
                Point playerPosition = playerDisk.getPosition();
                Point opponentPosition = opponentDisk.getPosition();
                if (playerPosition.isNeighbor(opponentPosition)) {
                    Point reflectPoint= playerPosition.reflection(opponentPosition);
                    Disk reflectDisk = board.getDisk(reflectPoint.getX(),reflectPoint.getY());
                    if (reflectPoint.isValid() && reflectDisk.getSign()=='\0')
                        availableDisks.add(reflectPoint);

                }
            }
        }
        return availableDisks;
    }
    //public void placeDisk(Point position){}

    private ArrayList<Disk> getDisks() {
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(j, i).getSign() == this.getSign())
                    disks.add(board.getDisk(j, i));
        return disks;
    }

    private ArrayList<Disk> getOpponentDisks() {
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(j, i).getSign() != this.getSign() && board.getDisk(j, i).getSign() != '\0')
                    disks.add(board.getDisk(j, i));
        return disks;
    }

    public void showAvailableMoves(){
        int index=1;
        ArrayList<Point> points = availableMoves();
        System.out.println("Available Moves:");
        for(Point point : points) {
            System.out.print(index + ") ");
            point.print();
            index++;
        }
    }
}
