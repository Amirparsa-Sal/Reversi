package com.amirparsa.salmankhah;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class Player {
    private String name;
    private char sign;
    private Board board;

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

    public void placeDisk(Point position) {
        board.getDisk(position.getX(), position.getY()).setSign(sign);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Point> availableMoves() {
        ArrayList<Disk> playerDisks = getDisks();
        ArrayList<Disk> opponentDisks = getOpponentDisks();
        ArrayList<Point> availableDisks = new ArrayList<>();

        for (Disk playerDisk : playerDisks) {
            for (Disk opponentDisk : opponentDisks) {
                Point playerPosition = new Point();
                playerPosition.copy(playerDisk.getPosition());
                Point opponentPosition = new Point();
                opponentPosition.copy(opponentDisk.getPosition());
                while (playerPosition.isNeighbor(opponentPosition )) {
                    Point reflectPoint = playerPosition.reflection(opponentPosition);
                    if (!reflectPoint.isValid())
                        break;
                    Disk reflectDisk = board.getDisk(reflectPoint.getX(), reflectPoint.getY());
                    if (reflectDisk.getSign() == sign)
                        break;
                    else if (reflectDisk.getSign() == '\0') {
                        availableDisks.add(reflectPoint);
                        break;
                    }
                    playerPosition.copy(opponentPosition);
                    opponentPosition.copy(reflectPoint);
                }
            }
        }
        return availableDisks;
    }

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

    public void showAvailableMoves() {
        int index = 1;
        ArrayList<Point> points = availableMoves();
        removeDuplicate(points);
        System.out.println("available moves:");
        for (Point point : points) {
            System.out.print(index + ") ");
            point.print();
            index++;
        }
    }

    private void removeDuplicate(ArrayList<Point> points) {
        Iterator<Point> it = points.iterator();
        while (it.hasNext()) {
            Point pnt = it.next();
            if (count(points, pnt) > 1)
                it.remove();
        }
    }

    public int count(ArrayList<Point> points, Point pnt) {
        int cnt = 0;
        for (Point point : points)
            if (point.equals(pnt))
                cnt++;
        return cnt;
    }
}
