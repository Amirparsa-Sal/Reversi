package com.amirparsa.salmankhah;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player[] players;
    private int turn;

    public Game(){
        players = new Player[2];
        setPlayer(1);
        setPlayer(2);
        setBoard();
        turn = 0;
    }

    public void setPlayer(int playerNumber){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player" +(char)('0'+playerNumber) +"'s name:");
        String playerName = sc.next();
        System.out.println("Enter player" +(char)('0'+playerNumber) +"'s sign:");
        char playerSign = sc.next().charAt(0);
        players[playerNumber-1] = new Player(playerName,playerSign);
    }

    public Player getPlayer(int playerNumber){
        return players[playerNumber-1];
    }
    public void setBoard(){
        board = new Board(players[0],players[1]);
    }

    public Board getBoard(){
        return board;
    }

    public int getTurn(){
        return turn;
    }

    public void playTurn(int playerNumber){
        Scanner sc = new Scanner(System.in);
        Player player = players[playerNumber-1];
        String name = player.getName();
        System.out.println("It's " + name + "'s " + "turn");
        System.out.print(name + "'s ");
        ArrayList<Point> points = player.availableMoves();
        turn++;
        if(points.size()==0){
            System.out.println("Pass");
            return;
        }
        player.showAvailableMoves();
        System.out.println("Enter your move:");
        //must check here
        String move = sc.nextLine();
        Point point = new Point(move.charAt(2) - 'A',move.charAt(0) - '1');
        player.placeDisk(point);
        board.updateMap(point);
        board.print();

    }
    public static void main(String[] args) {
        Game game = new Game();
        game.getBoard().print();
        while (true) {
            if (game.getTurn() % 2 == 0)
                game.playTurn(1);
            else
                game.playTurn(2);
        }
    }
}
