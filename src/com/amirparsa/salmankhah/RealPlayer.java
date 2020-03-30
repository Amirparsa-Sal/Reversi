package com.amirparsa.salmankhah;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a real player.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
class RealPlayer extends Player {

    /**
     * Constructor with 2 parameters.
     *
     * @param name Name of the player
     * @param sign Sign of the player
     */
    public RealPlayer(String name, char sign) {
        super(name, sign);
    }

    /**
     * Selects a point to move.
     *
     * @param points An ArrayList of available points.
     * @return The selected point
     */
    public Point think(ArrayList<Point> points) {
        Point point;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter your move (Example: 3 F):");
            String move = sc.nextLine();
            point = new Point(move.charAt(2) - 'A', move.charAt(0) - '1');
            if (count(points, point) > 0)
                break;
            System.out.println("Invalid move. Try again :)");
        }
        return point;
    }
}