package com.amirparsa.salmankhah;

import java.util.*;

/**
 * Represents a game with 2 players or bot and a board.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Game {
    //Board of the game
    private Board board;
    //Players of the game
    private Player[] players;
    //Turn of the playing
    private int turn;
    /**
     * Constructor with 2 parameters.
     *
     * @param player1Type Type of the player1 as an String. example: "Bot" or "Player"
     * @param player2Type Type of the player2 as an String. example: "Bot" or "Player"
     */
    public Game(String player1Type, String player2Type) {
        players = new Player[2];
        //init players
        if (player1Type.equals("Bot"))
            setBot(1);
        else
            setRealPlayer(1);

        if (player2Type.equals("Bot"))
            setBot(2);
        else
            setRealPlayer(2);
        setBoard();
        turn=0;
    }

    /**
     * Sets a real player.
     *
     * @param playerNumber player number in the game
     */
    public void setRealPlayer(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player" + (char) ('0' + playerNumber) + "'s name:");
        String playerName = sc.next();
        char playerSign = (char) ('0' + playerNumber);
        System.out.println("Your sign is: " + playerSign);
        //delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        players[playerNumber - 1] = new RealPlayer(playerName, playerSign);
    }

    /**
     * Sets a bot.
     *
     * @param playerNumber bot number in the game.
     */
    public void setBot(int playerNumber) {
        char botSign = (char) ('0' + playerNumber);
        String name;
        if (playerNumber == 1)
            name = "Amir(Bot)";
        else
            name = "Parsa(Bot)";
        players[playerNumber - 1] = new Bot(name, botSign);

    }

    /**
     * Board setter. Automatically links the board to players.
     */
    public void setBoard() {
        board = new Board(players[0], players[1]);
    }

    /**
     * Player getter
     *
     * @param playerNumber player number in the game
     * @return The player
     */
    public Player getPlayer(int playerNumber) {
        return players[playerNumber - 1];
    }

    /**
     * Board getter
     *
     * @return Board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Turn getter
     * @return Turn of the playing
     */
    public int getTurn(){
        return turn;
    }
    /**
     * Checks if the game is still in progress or not.
     *
     * @return true if yes and false if not.
     */
    public boolean inProgress() {
        return !(players[0].availableMoves().size() == 0 && players[1].availableMoves().size() == 0);
    }

    /**
     * handles a turn for a player.
     *
     * @param playerNumber Number of the player in game
     */
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
        //delay
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

    /**
     * Counts player disks.
     *
     * @param playerNumber Number of the player in the game
     * @return Number of player disks
     */
    public int countPlayerDisks(int playerNumber) {
        int cnt = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getDisk(i, j).getSign() == players[playerNumber - 1].getSign())
                    cnt++;
        return cnt;
    }

    /**
     * Prints the scores
     */
    public void showScores() {
        int score1 = countPlayerDisks(1);
        int score2 = countPlayerDisks(2);
        System.out.print("Scores:    ");
        String bgColor1 = "\u001b[41m", bgColor2 = "\u001b[44m", textColor = "\u001B[30m", reset = "\u001B[0m";
        System.out.println(bgColor1 + textColor + players[0].getName() + ": " + score1 + reset + "    "
                + bgColor2 + textColor + players[1].getName() + ": " + score2 + reset);
    }
}
