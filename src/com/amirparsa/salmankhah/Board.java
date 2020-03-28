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
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                table[i][j] = new Disk('\0', j, i);
        if(player1!=null && player2!=null) {
            player1.setBoard(this);
            player2.setBoard(this);
            this.getDisk(3, 3).setSign(player2.getSign());
            this.getDisk(4, 3).setSign(player1.getSign());
            this.getDisk(3, 4).setSign(player1.getSign());
            this.getDisk(4, 4).setSign(player2.getSign());
        }
    }

    public Player[] getPlayers(){
        Player[] players = new Player[2];
        players[0]=player1;
        players[1]=player2;
        return players;
    }

    public void setPlayers(Player[] players){
        player1=players[0];
        player2=players[1];
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

    public void updateMap(Point lastMove) {
        char sign = getDisk(lastMove.getX(), lastMove.getY()).getSign();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Point tmp = new Point(lastMove.getX() + i, lastMove.getY() + j);
                boolean flag = false;
                while (!flag && tmp.isValid()) {
                    char tmpSign = getDisk(tmp.getX(), tmp.getY()).getSign();
                    if (tmpSign == sign) {
                        flag = true;
                        break;
                    } else if (tmpSign == '\0')
                        break;
                    tmp.setX(tmp.getX() + i);
                    tmp.setY(tmp.getY() + j);
                }
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

    public void copy(Board otherBoard) {
        Player[] players = new Player[2];
        players[0]=new RealPlayer(null,'\0');
        players[0].copy(otherBoard.getPlayers()[0]);
        if(otherBoard.getPlayers()[1] instanceof RealPlayer)
            players[1] = new RealPlayer(null, '\0');
        else
            players[1] = new Bot(null, '\0');
        players[1].copy(otherBoard.getPlayers()[1]);
        this.setPlayers(players);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Disk disk = otherBoard.getDisk(j,i);
                this.getDisk(j,i).copy(disk);
            }
        }
    }
}
