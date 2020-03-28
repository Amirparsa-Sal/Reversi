package com.amirparsa.salmankhah;

import java.util.ArrayList;
import java.util.Scanner;

class RealPlayer extends Player{

    public RealPlayer(String name, char sign) {
        super(name, sign);
    }

    public Point think (ArrayList<Point> points){
        Point point;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your move:");
            String move = sc.nextLine();
            point = new Point(move.charAt(2) - 'A', move.charAt(0) - '1');
            if (count(points, point)>0)
                break;
            System.out.println("Invalid move. Try again :)");
        }
        return point;
    }
}