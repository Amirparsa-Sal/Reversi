package com.amirparsa.salmankhah;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a player. its subclasses are RealPlayer and Bot.It contains player name, sign and active board.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
abstract class Player {
    //Name of the player
    private String name;
    //Sign of the player
    private char sign;
    //Active board
    private Board board;

    /**
     * Constructor with 2 parameters.
     *
     * @param name Name of the player.
     * @param sign Sign of the player.
     */
    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.board = null;
    }

    /**
     * Name getter
     *
     * @param name Name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sign getter
     *
     * @param sign Sign of the player
     */
    public void setSign(char sign) {
        this.sign = sign;
    }

    /**
     * Board setter
     *
     * @param board Active board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Name getter
     *
     * @return Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sign getter
     *
     * @return Sign of the player
     */
    public char getSign() {
        return sign;
    }

    /**
     * Board getter
     *
     * @return Active board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks the equality between the player and other player.
     *
     * @param otherPlayer Other player
     * @return true if they are equal and false if not.
     */
    public boolean equals(Player otherPlayer) {
        return name.equals(otherPlayer.getName()) && sign == otherPlayer.getSign();
    }

    /**
     * Copy other player fields to this player.
     *
     * @param otherPlayer Other player
     */
    public void copy(Player otherPlayer) {
        this.setName(otherPlayer.getName());
        this.setSign(otherPlayer.getSign());
    }

    /**
     * Places a disk from the player at a position.
     *
     * @param position position of the disk.
     */
    public void placeDisk(Point position) {
        board.getDisk(position.getX(), position.getY()).setSign(sign);
    }

    /**
     * Checks for the player available moves
     *
     * @return An ArrayList of the available points.
     */
    public ArrayList<Point> availableMoves() {
        //getting disks
        ArrayList<Disk> playerDisks = getDisks();
        ArrayList<Disk> opponentDisks = getOpponentDisks();
        ArrayList<Point> availableDisks = new ArrayList<>();

        for (Disk playerDisk : playerDisks) {
            for (Disk opponentDisk : opponentDisks) {
                //getting disk positions
                Point playerPosition = new Point();
                Point opponentPosition = new Point();
                playerPosition.copy(playerDisk.getPosition());
                opponentPosition.copy(opponentDisk.getPosition());
                //Finding the reflection point if it exists
                while (playerPosition.isNeighbor(opponentPosition)) {
                    Point reflectPoint = playerPosition.reflection(opponentPosition);
                    if (!reflectPoint.isValid()) //if we are out of the bound -> there is no reflect point
                        break;
                    Disk reflectDisk = board.getDisk(reflectPoint.getX(), reflectPoint.getY());
                    if (reflectDisk.getSign() == sign) //if the reflect point have the same sign -> there is no reflect point.
                        break;
                    else if (reflectDisk.getSign() == '\0') { //if the reflect point is empty -> add to list
                        availableDisks.add(reflectPoint);
                        break;
                    }
                    //if the reflect point has the opposite sign -> continue
                    playerPosition.copy(opponentPosition);
                    opponentPosition.copy(reflectPoint);
                }
            }
        }
        return availableDisks;
    }

    /**
     * Prints available moves.
     */
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

    /**
     * Get opponent player
     * @return Opponent player
     */
    public Player getOpponent() {
        if (board.getPlayers()[0].equals(this))
            return board.getPlayers()[1];
        return board.getPlayers()[1];
    }

    /**
     * Get player's disks.
     *
     * @return An ArrayList of disks.
     */
    protected ArrayList<Disk> getDisks() {
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(j, i).getSign() == this.getSign())
                    disks.add(board.getDisk(j, i));
        return disks;
    }

    /**
     * Get opponent's disks.
     *
     * @return An ArrayList of disks.
     */
    protected ArrayList<Disk> getOpponentDisks() {
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(j, i).getSign() != this.getSign() && board.getDisk(j, i).getSign() != '\0')
                    disks.add(board.getDisk(j, i));
        return disks;
    } //BOT

    /**
     * Remove duplicate elements from the list.
     *
     * @param points list of points
     */
    protected void removeDuplicate(ArrayList<Point> points) {
        Iterator<Point> it = points.iterator();
        while (it.hasNext()) {
            Point pnt = it.next();
            if (count(points, pnt) > 1)
                it.remove();
        }
    }

    /**
     * Counts number of a point in an ArrayList of points.
     *
     * @param points The ArrayList
     * @param pnt    The point
     * @return Number of points
     */
    protected int count(ArrayList<Point> points, Point pnt) {
        int cnt = 0;
        for (Point point : points)
            if (point.equals(pnt))
                cnt++;
        return cnt;
    }

    /**
     * Selects a point to move.
     *
     * @param points An ArrayList of available points.
     * @return The selected point
     */
    public abstract Point think(ArrayList<Point> points);

}
