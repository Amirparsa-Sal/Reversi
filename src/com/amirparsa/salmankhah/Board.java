package com.amirparsa.salmankhah;

/**
 * Represents the board of the game
 */
public class Board {
    //Disks
    private Disk[][] table;
    //Player1
    private Player player1;
    //Player2
    private Player player2;

    public Board(Player player1, Player player2) {
        table = new Disk[8][8];
        this.player1 = player1;
        this.player2 = player2;
        player1.setBoard(this);
        player2.setBoard(this);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                table[i][j] = new Disk('\0', j, i);
        this.getDisk(3, 3).setSign(player2.getSign());
        this.getDisk(4, 3).setSign(player1.getSign());
        this.getDisk(3, 4).setSign(player1.getSign());
        this.getDisk(4, 4).setSign(player2.getSign());
    }

    public Disk getDisk(int x, int y) {
        return table[y][x];
    }

    public void setDiskSign(char sign, int x, int y) {
        this.getDisk(x, y).setSign(sign);
    }

    public void print() {
        System.out.print("  ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 8; j++) {
                char sign = this.getDisk(j, i).getSign();
                if (sign == '\0')
                    System.out.print(" . ");
                else
                    System.out.print(" " + sign + " ");
            }
            System.out.println();
        }
    }


    //public void updateMap(){}
}
