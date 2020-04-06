package com.amirparsa.salmankhah;

import java.util.Scanner;

/**
 * Runs the game.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Run {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Menu
        System.out.println("Select mode (exmaple: 1):");
        System.out.println("1) Single Player");
        System.out.println("2) Multiplayer ");
        System.out.println("3) Bot vs Bot");
        Game game = null;
        int input = 0;
        while (input < 1 || input > 3) {
            System.out.println("Enter the mode: ");
            input = sc.nextInt();
            if (input == 1)
                game = new Game("Player", "Bot");
            else if (input == 2)
                game = new Game("Player", "Player");
            else if (input == 3)
                game = new Game("Bot", "Bot");
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        game.getBoard().print();
        game.showScores();
        //Start game
        while (game.inProgress()) {
            if (game.getTurn() % 2 == 0)
                game.playTurn(1);
            else
                game.playTurn(2);
        }
        //Final result
        int score1 = game.countPlayerDisks(1);
        int score2 = game.countPlayerDisks(2);
        String name1 = game.getPlayer(1).getName();
        String name2 = game.getPlayer(2).getName();
        System.out.println("\nFinal Result:    " + name1 + ": " + score1 + "    " + name2 + ": " + score2);
        if (score1 > score2)
            System.out.println(name1 + " WON!");
        else if (score1 < score2)
            System.out.println(name2 + " WON!");
        else
            System.out.println("DRAW!");
    }
}
