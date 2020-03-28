package com.amirparsa.salmankhah;

/**
 * Represents the board of the game
 */
public class Board {
    //Disks
    Disk[][] table;
    //Player1
    Player player1;
    //Player2
    Player player2;

    public Board(Player player1, Player player2) {
        table = new Disk[8][8];
        this.player1 = player1;
        this.player2 = player2;
        player1.board = this;
        player2.board = this;
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

    public void print() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                char sign = this.getDisk(j,i).getSign();
                if(sign=='\0')
                    System.out.print(" . ");
                else
                    System.out.print(" " + sign + " ");
            }
            System.out.println();
        }
    }
}
