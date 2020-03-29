package com.amirparsa.salmankhah;

import com.sun.deploy.security.SelectableSecurityManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

class Bot extends Player {

    public Bot(String name, char sign) {
        super(name, sign);
    }

    public Point think(ArrayList<Point> botMoves) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        //Adding inner lists
        int bestMove = 0;
        Integer bestWorstInnerProfit=-1000;
        for (int i = 0; i < botMoves.size(); i++) {
            list.add(new ArrayList<Integer>());
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
            simulatedBot.placeDisk(botMoves.get(i));
            simulatingBoard.updateMap(botMoves.get(i));
            //Phase II
            simulatedPlayer.setBoard(simulatingBoard);
            ArrayList<Point> playerMoves = simulatedPlayer.availableMoves();
            for (int j = 0; j < playerMoves.size(); j++) {
                Board simulatingBoardcpy = new Board(null, null);
                simulatingBoardcpy.copy(simulatingBoard);
                simulatedPlayer.setBoard(simulatingBoardcpy);
                simulatedPlayer.placeDisk(playerMoves.get(j));
                simulatingBoardcpy.updateMap(playerMoves.get(j));
                list.get(i).add(calculateProfit(simulatingBoardcpy));
            }
            Integer innerMin=1000;
            for( int j = 0; j < playerMoves.size(); j++)
                if(list.get(i).get(j)<innerMin)
                    innerMin = list.get(i).get(j);
            if(innerMin>bestWorstInnerProfit) {
                bestWorstInnerProfit = innerMin;
                bestMove = i;
            }
            botMoves.get(i).print();
            System.out.println("BestWorst: " + bestWorstInnerProfit + "    " + "InnerMin: " + innerMin);
        }
        System.out.print("Bot has chosen: ");
        botMoves.get(bestMove).print();
        return botMoves.get(bestMove);
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
        if(simulatingBoard.getDisk(0, 0).getSign()==getSign()){
            balance[0][1]=0;
            balance[1][1]=0;
            balance[1][0]=0;
        }
        if(simulatingBoard.getDisk(7, 0).getSign()==getSign()){
            balance[0][6]=0;
            balance[1][7]=0;
            balance[1][6]=0;
        }
        if(simulatingBoard.getDisk(0, 7).getSign()==getSign()){
            balance[6][0]=0;
            balance[7][1]=0;
            balance[6][1]=0;
        }
        if(simulatingBoard.getDisk(7, 7).getSign()==getSign()){
            balance[6][7]=0;
            balance[7][6]=0;
            balance[6][6]=0;
        }
        int botProfit = 0, playerProfit = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char sign = simulatingBoard.getDisk(j, i).getSign();
                if (sign != this.getSign() && sign != '\0')
                    playerProfit += balance[i][j];
                else if (sign == this.getSign())
                    botProfit += balance[i][j];
            }
        }
        botProfit += this.getDisks().size()*(5);
        playerProfit += this.getOpponentDisks().size()*(5);
        return botProfit - playerProfit;
    }
}
