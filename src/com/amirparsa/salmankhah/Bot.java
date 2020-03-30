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
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int bestMove = 0;
        Integer bestWorstInnerProfit=-1000;
        for (int i = 0; i < botMoves.size(); i++) {
            //Phase I - Simulating bot's move
            list.add(new ArrayList<Integer>()); //making a list for opponent mvoe after the bot's move
            //making a copy of the game
            Board simulatingBoard = new Board(null, null);
            simulatingBoard.copy(getBoard());
            Player[] players = getBoard().getPlayers();
            Player simulatedBot, simulatedPlayer;
            if (players[0].equals(this)) {
                simulatedPlayer = simulatingBoard.getPlayers()[1];
                simulatedBot = simulatingBoard.getPlayers()[0];
            } else {
                simulatedPlayer = simulatingBoard.getPlayers()[0];
                simulatedBot = simulatingBoard.getPlayers()[1];
            }
            simulatedBot.setBoard(simulatingBoard);
            simulatedBot.placeDisk(botMoves.get(i)); //bot's move
            simulatingBoard.updateMap(botMoves.get(i)); //update the map
            //Phase II - Simulating opponent's move
            simulatedPlayer.setBoard(simulatingBoard);
            ArrayList<Point> playerMoves = simulatedPlayer.availableMoves(); //getting available moves
            for (int j = 0; j < playerMoves.size(); j++) {
                //making copy of the game
                Board simulatingBoardcpy = new Board(null, null);
                simulatingBoardcpy.copy(simulatingBoard);
                simulatedPlayer.setBoard(simulatingBoardcpy);
                simulatedPlayer.placeDisk(playerMoves.get(j)); //opponent's move
                simulatingBoardcpy.updateMap(playerMoves.get(j));
                list.get(i).add(calculateProfit(simulatingBoardcpy)); //adding profit to the list
            }
            //finding the maximum of minimum profits. we suppose that the opponent is genius.
            Integer innerMin=1000;
            for( int j = 0; j < playerMoves.size(); j++)
                if(list.get(i).get(j)<innerMin)
                    innerMin = list.get(i).get(j);
            if(innerMin>bestWorstInnerProfit) {
                bestWorstInnerProfit = innerMin;
                bestMove = i;
            }
        }
        //Printing the move
        System.out.print("\n" + getName() + " has chosen: ");
        botMoves.get(bestMove).print();
        return botMoves.get(bestMove);
    }

    /**
     * Calculates the profit for the bot in a particular situation.
     * @param simulatingBoard The simulated board.
     * @return
     */
    private int calculateProfit(Board simulatedBoard) {
        //Value of each point of the map
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
        //changing values if the bot has corners
        if(simulatedBoard.getDisk(0, 0).getSign()==getSign()){
            balance[0][1]=3;
            balance[1][1]=3;
            balance[1][0]=0;
        }
        if(simulatedBoard.getDisk(7, 0).getSign()==getSign()){
            balance[0][6]=3;
            balance[1][7]=3;
            balance[1][6]=0;
        }
        if(simulatedBoard.getDisk(0, 7).getSign()==getSign()){
            balance[6][0]=3;
            balance[7][1]=3;
            balance[6][1]=0;
        }
        if(simulatedBoard.getDisk(7, 7).getSign()==getSign()){
            balance[6][7]=3;
            balance[7][6]=3;
            balance[6][6]=0;
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
        botProfit += this.getDisks().size()*(4);
        playerProfit += this.getOpponentDisks().size()*(4);

        return botProfit - playerProfit;
    }
}
