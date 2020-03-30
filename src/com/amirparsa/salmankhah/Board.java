package com.amirparsa.salmankhah;

/**
 * Represents a board with its players and disks.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Board {
    //Disks
    private Disk[][] table;
    //Players
    private Player[] players;

    /**
     * Constructor with 2 parameters.
     *
     * @param player1 Player1
     * @param player2 Player2
     */
    public Board(Player player1, Player player2) {
        table = new Disk[8][8];
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                table[i][j] = new Disk('\0', j, i);
        if (player1 != null && player2 != null) {
            player1.setBoard(this);
            player2.setBoard(this);
            this.getDisk(3, 3).setSign(player2.getSign());
            this.getDisk(4, 3).setSign(player1.getSign());
            this.getDisk(3, 4).setSign(player1.getSign());
            this.getDisk(4, 4).setSign(player2.getSign());
        }
    }

    /**
     * Players setter
     *
     * @param players An array of players
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Players getter
     *
     * @return An array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the disk which is in (x,y) point. Note: Origin is on Top Left point.
     *
     * @param x X of the point
     * @param y Y of the point
     * @return The disk which is in (x,y) point
     */
    public Disk getDisk(int x, int y) {
        return table[y][x];
    }

    /**
     * Prints the board with its disks.
     */
    public void print() {
        //Print characters
        System.out.print("  ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (char) ('A' + i) + " ");
        System.out.println();
        //Print
        for (int i = 0; i < 8; i++) {
            //Print row number
            System.out.print(i + 1 + " ");
            //Print each cell
            for (int j = 0; j < 8; j++) {
                char sign = this.getDisk(j, i).getSign();
                //set colors
                String bgColor, textColor = "\u001B[30m";
                String reset = "\u001B[0m";
                if (players[0].getSign() == sign)
                    bgColor = "\u001b[41m";
                else
                    bgColor = "\u001b[44m";
                //print cell
                if (sign == '\0')
                    System.out.print(" . ");
                else
                    System.out.print(bgColor + textColor + " " + sign + " " + reset);
            }
            System.out.println();
        }
    }

    /**
     * Updates map after each move.
     *
     * @param lastMove Last move point
     */
    public void updateMap(Point lastMove) {
        char sign = getDisk(lastMove.getX(), lastMove.getY()).getSign();
        //checking all of directions
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Point tmp = new Point(lastMove.getX() + i, lastMove.getY() + j); //each direction neighbor
                boolean flag = false;
                //Checking if there is a point in this direction and find the last point.
                while (!flag && tmp.isValid()) {
                    char tmpSign = getDisk(tmp.getX(), tmp.getY()).getSign();
                    if (tmpSign == sign) {  //reaching to a same disk sign on this direction
                        flag = true;
                        break;
                    } else if (tmpSign == '\0') //reaching to an empty disk on this direction
                        break;
                    //reaching to an opposite disk sign on this direction
                    tmp.setX(tmp.getX() + i);
                    tmp.setY(tmp.getY() + j);
                }
                //filling up the sequence from first point to the last point
                if (flag) {
                    while (!tmp.equals(lastMove)) {
                        tmp.setX(tmp.getX() - i);
                        tmp.setY(tmp.getY() - j);
                        getDisk(tmp.getX(), tmp.getY()).setSign(sign);
                    }
                }
            }
        }
    }

    /**
     * Copy the other board fields to this board.
     * @param otherBoard Other board
     */
    public void copy(Board otherBoard) {
        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++) {
            if (otherBoard.getPlayers()[i] instanceof RealPlayer)
                players[i] = new RealPlayer(null, '\0');
            else
                players[i] = new Bot(null, '\0');
            players[i].copy(otherBoard.getPlayers()[i]);
        }
        this.setPlayers(players);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Disk disk = otherBoard.getDisk(j, i);
                this.getDisk(j, i).copy(disk);
            }
        }
    }
}
