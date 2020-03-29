package com.amirparsa.salmankhah;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
    private Board board;
    private Player[] players;
    private int turn;

    public Game(String player1Type, String player2Type) {
        players = new Player[2];
        if (player1Type.equals("Bot"))
            setBot(1);
        else
            setRealPlayer(1);

        if (player2Type.equals("Bot"))
            setBot(2);
        else
            setRealPlayer(2);
        setBoard();
        turn = 0;
    }

    public void setRealPlayer(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player" + (char) ('0' + playerNumber) + "'s name:");
        String playerName = sc.next();
        char playerSign = (char) ('0' + playerNumber);
        System.out.println("Your sign is: " + playerSign);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        players[playerNumber - 1] = new RealPlayer(playerName, playerSign);
    }

    public void setBot(int playerNumber) {
        char botSign = (char) ('0' + playerNumber);
        String name;
        if (playerNumber == 1)
            name = "Amir(Bot)";
        else
            name = "Parsa(Bot)";
        players[playerNumber - 1] = new Bot(name, botSign);

    }

    public Player getPlayer(int playerNumber) {
        return players[playerNumber - 1];
    }

    public void setBoard() {
        board = new Board(players[0], players[1]);
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public boolean inProgress() {
        return !(players[0].availableMoves().size() == 0 && players[1].availableMoves().size() == 0);
    }

    public void playTurn(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        Player player = players[playerNumber - 1];
        String name = player.getName();
        System.out.println("\nIt's " + name + "'s " + "turn\n");
        ArrayList<Point> points = player.availableMoves();
        turn++;
        if (points.size() == 0) {
            System.out.println("Pass");
            return;
        }
        System.out.print(name + "'s ");
        players[playerNumber - 1].showAvailableMoves();
        Point point = player.think(points);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.placeDisk(point);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        board.updateMap(point);
        board.print();
        showScores();
    }

    private int countPlayerDisks(int playerNumber) {
        int cnt = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(i, j).getSign() == players[playerNumber - 1].getSign())
                    cnt++;
        return cnt;
    }

    public void showScores() {
        int score1 = countPlayerDisks(1);
        int score2 = countPlayerDisks(2);
        System.out.print("Scores:    ");
        String bgColor1 = "\u001b[41m", bgColor2 = "\u001b[44m", textColor = "\u001B[30m", reset = "\u001B[0m";
        System.out.println(bgColor1 + textColor + players[0].getName() + ": " + score1 + reset + "    "
                + bgColor2 + textColor + players[1].getName() + ": " + score2 + reset);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Select mode:");
        System.out.println("1) Single Player");
        System.out.println("2) Multiplayer ");
        System.out.println("3) Bot vs Bot");
        int input = sc.nextInt();
        Game game;
        if (input == 1)
            game = new Game("Player", "Bot");
        else if (input == 2)
            game = new Game("Player", "Player");
        else
            game = new Game("Bot", "Bot");

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        game.getBoard().print();
        game.showScores();
        while (game.inProgress()) {
            if (game.getTurn() % 2 == 0)
                game.playTurn(1);
            else
                game.playTurn(2);
        }
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
