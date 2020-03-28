package com.amirparsa.salmankhah;

import java.util.ArrayList;

class Bot extends Player {

    public Bot(String name, char sign) {
        super("Bot", sign);
    }

    public Point think(ArrayList<Point> points) {
        ArrayList<ArrayList<Integer>> simulatingList = new ArrayList<>();
        //Adding inner lists
        Board simulatingBoard = new Board(null,null);
        simulatingBoard.copy(getBoard());
//        for (int i = 0; i < points.size(); i++){
//            simulatingList.add(new ArrayList<Integer>());
//        }



        return null;
    }

    private int calculateProfit(Board simulatingBoard) {
        int[][] balance = {
                {99, -8, 8, 6, 6, 8, -8, 99},
                {-8, -24, -4, -3, -3, -4, -24, -8},
                {8, -4, 7, 4, 4, 7, -4, 8},
                {6, -3, 4, 0, 0, 4, -3, 6},
                {6, -3, 4, 0, 0, 4, -3, 6},
                {8, -4, 7, 4, 4, 7, -4, 8},
                {-8, -24, -4, -3, -3, -4, -24, -8},
                {99, -8, 8, 6, 6, 8, -8, 99}
        };
        int botProfit = 0, playerProfit = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char sign = simulatingBoard.getDisk(j, i).getSign();
                if (sign != this.getSign() && sign != '\0')
                    playerProfit += balance[i][j];
                else if (sign == this.getSign())
                    botProfit+=balance[i][j];
            }
        }
        return botProfit-playerProfit;
    }
}
