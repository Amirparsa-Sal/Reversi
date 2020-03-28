package com.amirparsa.salmankhah;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.*;

public class Game {
    private Board board;
    private Player[] players;
    private int turn;
    private int end;

    public Game() {
        players = new Player[2];
        setPlayer(1);
        setPlayer(2);
        setBoard();
        turn = 0;
        end = 0;
    }

    public void setPlayer(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player" + (char) ('0' + playerNumber) + "'s name:");
        String playerName = sc.next();
        System.out.println("Enter player" + (char) ('0' + playerNumber) + "'s sign:");
        char playerSign = sc.next().charAt(0);
        players[playerNumber - 1] = new Player(playerName, playerSign);
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
        boolean flag=false;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(i, j).getSign() == '\0')
                    flag = true;
        return flag;
    }

    public void playTurn(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        Player player = players[playerNumber - 1];
        String name = player.getName();
        System.out.println("It's " + name + "'s " + "turn");
        ArrayList<Point> points = player.availableMoves();
        turn++;
        if (points.size() == 0) {
            System.out.println("Pass");
            return;
        }
        System.out.print(name + "'s ");
        player.showAvailableMoves();
        Point point;
        while (true) {
            System.out.println("Enter your move:");
            String move = sc.nextLine();
            point = new Point(move.charAt(2) - 'A', move.charAt(0) - '1');
            if (player.count(points, point)>0)
                break;
            System.out.println("Invalid move. Try again :)");
        }
        player.placeDisk(point);
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

    public void showScores(){
        int score1 = countPlayerDisks(1);
        int score2 = countPlayerDisks(2);
        System.out.print("Scores:    ");
        System.out.println(players[0].getName()+": " + score1 + "    " + players[1].getName() + ": " + score2);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.getBoard().print();
        game.showScores();
        while (game.inProgress()) {
            if (game.getTurn() % 2 == 0)
                game.playTurn(1);
            else
                game.playTurn(2);
        }
    }
}
