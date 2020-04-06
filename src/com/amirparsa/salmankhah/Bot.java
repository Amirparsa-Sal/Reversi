package com.amirparsa.salmankhah;

import java.util.ArrayList;

/**
 * Represents a bot.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
class Bot extends Player {

    /**
     * Constructor with 2 parameters.
     *
     * @param name Name of the bot
     * @param sign Sign of the bot
     */
    public Bot(String name, char sign) {
        super(name, sign);
    }

    /**
     * Selects a point to move.
     *
     * @param botMoves An ArrayList of available points.
     * @return The selected point
     */
    public Point think(ArrayList<Point> botMoves) {
        Network network = new Network(new Node(this, getBoard(), 0));
        network.activateLayer(0);
        network.activateLayer(1);
        network.activateLayer(2);
        network.activateLayer(3);
        if (botMoves.size() <= 7)
            network.activateLayer(4);
        ArrayList<Integer> profits = network.getProfits();
        int max = -9999999, index = 0;
        for (int i = 0; i < profits.size(); i++) {
            if (profits.get(i) > max) {
                max = profits.get(i);
                index = i;
            }
        }
        System.out.println();
        System.out.print("\n" + getName() + " has chosen: ");
        botMoves.get(index).print();
        return botMoves.get(index);
    }

    /**
     * Calculates the profit for the bot in a particular situation.
     *
     * @param simulatedBoard The simulated board.
     * @return profit of the bot
     */
    public int calculateProfit(Board simulatedBoard) {
        //Value of each point of the map
        int[][] balance = {
                {99, -8, 8, 6, 6, 8, -8, 99},
                {-8, -30, -4, -3, -3, -4, -30, -8},
                {10, 0, 0, 0, 0, 0, 0, 10},
                {6, 0, 4, 3, 3, 4, 0, 6},
                {6, 0, 4, 3, 3, 4, 0, 6},
                {10, 0, 0, 0, 0, 0, 0, 10},
                {-8, -30, -4, -3, -3, -4, -30, -8},
                {99, -8, 8, 6, 6, 8, -8, 99}
        };
        //changing values if the bot has corners
        if (simulatedBoard.getDisk(1, 0).getSign() != '\0' && simulatedBoard.getDisk(3, 0).getSign() != '\0' && simulatedBoard.getDisk(1, 0).getSign() == '\0') {
            balance[0][1] = -15;
            balance[0][2] = 15;
            balance[0][3] = -15;
        }
        if (simulatedBoard.getDisk(1, 7).getSign() != '\0' && simulatedBoard.getDisk(3, 7).getSign() != '\0' && simulatedBoard.getDisk(1, 7).getSign() == '\0') {
            balance[7][1] = -15;
            balance[7][2] = 15;
            balance[7][3] = -15;
        }
        if (simulatedBoard.getDisk(4, 0).getSign() != '\0' && simulatedBoard.getDisk(6, 0).getSign() != '\0' && simulatedBoard.getDisk(5, 0).getSign() == '\0') {
            balance[0][4] = -15;
            balance[0][5] = 15;
            balance[0][6] = -15;
        }
        if (simulatedBoard.getDisk(4, 7).getSign() != '\0' && simulatedBoard.getDisk(6, 7).getSign() != '\0' && simulatedBoard.getDisk(5, 7).getSign() == '\0') {
            balance[7][4] = -15;
            balance[7][5] = 15;
            balance[7][6] = -15;
        }
        if (simulatedBoard.getDisk(0, 1).getSign() != '\0' && simulatedBoard.getDisk(0, 3).getSign() != '\0' && simulatedBoard.getDisk(0, 2).getSign() == '\0') {
            balance[1][0] = -15;
            balance[2][0] = 15;
            balance[3][0] = -15;
        }
        if (simulatedBoard.getDisk(7, 1).getSign() != '\0' && simulatedBoard.getDisk(7, 3).getSign() != '\0' && simulatedBoard.getDisk(7, 2).getSign() == '\0') {
            balance[1][7] = -15;
            balance[2][7] = 15;
            balance[3][7] = -15;
        }
        if (simulatedBoard.getDisk(0, 4).getSign() != '\0' && simulatedBoard.getDisk(0, 6).getSign() != '\0' && simulatedBoard.getDisk(0, 5).getSign() == '\0') {
            balance[4][0] = -15;
            balance[5][0] = 15;
            balance[6][0] = -15;
        }
        if (simulatedBoard.getDisk(7, 4).getSign() != '\0' && simulatedBoard.getDisk(7, 6).getSign() != '\0' && simulatedBoard.getDisk(7, 5).getSign() == '\0') {
            balance[4][7] = -15;
            balance[5][7] = 15;
            balance[6][7] = -15;
        }
        if (simulatedBoard.getDisk(0, 0).getSign() != '\0') {
            balance[0][1] = 5;
            balance[1][1] = 5;
            balance[1][0] = 0;
        }
        if (simulatedBoard.getDisk(7, 0).getSign() != '\0') {
            balance[0][6] = 5;
            balance[1][7] = 5;
            balance[1][6] = 0;
        }
        if (simulatedBoard.getDisk(0, 7).getSign() != '\0') {
            balance[6][0] = 5;
            balance[7][1] = 5;
            balance[6][1] = 0;
        }
        if (simulatedBoard.getDisk(7, 7).getSign() != '\0') {
            balance[6][7] = 5;
            balance[7][6] = 5;
            balance[6][6] = 0;
        }
        //calculate profit from values
        int botProfit = 0, playerProfit = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char sign = simulatedBoard.getDisk(j, i).getSign();
                if (sign != this.getSign() && sign != '\0')
                    playerProfit += balance[i][j];
                else if (sign == this.getSign())
                    botProfit += balance[i][j];
            }
        }
        //calculate profit from number of disks
        botProfit += this.getDisks().size();
        playerProfit = this.getOpponent().getDisks().size();
        return botProfit - playerProfit;
    }
}
