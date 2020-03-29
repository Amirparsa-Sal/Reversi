package com.amirparsa.salmankhah;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.*;

public class Game {
    private Board board;
    private Player[] players;
    private int turn;
    public Game(String player1Type, String player2Type) {
        players = new Player[2];
        if(player1Type.equals("Bot"))
            setBot(1);
        else
           setRealPlayer(1);

        if(player2Type.equals("Bot"))
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
        System.out.println("Enter player" + (char) ('0' + playerNumber) + "'s sign:");
        char playerSign = sc.next().charAt(0);
        players[playerNumber - 1] = new RealPlayer(playerName, playerSign);
    }

    public void setBot(int playerNumber){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter bot's sign:");
        char botSign = sc.next().charAt(0);
        String name;
        if(playerNumber==1)
            name="Amir(Bot)";
        else
            name="Parsa(Bot)";
        players[playerNumber-1] = new Bot(name, botSign);

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
        return !(players[0].availableMoves().size()==0 && players[1].availableMoves().size()==0);
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
        players[playerNumber - 1].showAvailableMoves();
        Point point = player.think(points);
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
        Game game = new Game("Bot","Player");
        game.getBoard().print();
        game.showScores();
        while (game.inProgress()) {
            if (game.getTurn() % 2 == 0)
                game.playTurn(1);
            else
                game.playTurn(2);
        }
    }
    //WINNER
}
