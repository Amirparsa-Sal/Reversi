package com.amirparsa.salmankhah;

import java.util.ArrayList;

/**
 * Represents a network of nodes which stores the game states.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Network {
    //List of nodes
    private ArrayList<ArrayList<Node>> nodes;
    //Number of layers
    private int layers;

    /**
     * Constructor with a parameter
     *
     * @param head Head node of the network
     */
    public Network(Node head) {
        nodes = new ArrayList<>();
        nodes.add(new ArrayList<Node>());
        nodes.get(0).add(head);
        head.setPrev(null);
        layers = 0;
    }

    /**
     * Head getter
     * @return Head node
     */
    public Node getHead() {
        return nodes.get(0).get(0);
    }

    /**
     * Simulates all of possible moves in a layer
     *
     * @param layerNumber Number of the layer
     */
    public void activateLayer(int layerNumber) {
        //making new list
        ArrayList<Node> listOfNodes = nodes.get(layerNumber);
        ArrayList<Node> newNodes = new ArrayList<>();
        nodes.add(newNodes);
        //iterating the layer
        for (Node node : listOfNodes) {
            ArrayList<Point> moves = node.getPlayer().availableMoves();
            //if we dont have any possible move
            if (moves.size() == 0) {
                Board simulatingBoard = new Board(null, null);
                simulatingBoard.copy(node.getBoard());
                Player opponent = null, player = null;
                if (simulatingBoard.getPlayers()[0].equals(node.getPlayer())) {
                    player = simulatingBoard.getPlayers()[0];
                    opponent = simulatingBoard.getPlayers()[1];
                } else {
                    player = simulatingBoard.getPlayers()[1];
                    opponent = simulatingBoard.getPlayers()[0];
                }
                player.setBoard(simulatingBoard);
                opponent.setBoard(simulatingBoard);
                Bot realBot = (Bot) getHead().getPlayer();
                Node zero;
                if (player.equals(realBot)) {
                    realBot.copy(player);
                    zero = new Node(opponent, simulatingBoard, realBot.calculateProfit(simulatingBoard));
                } else {
                    realBot.copy(opponent);
                    zero = new Node(opponent, simulatingBoard, realBot.calculateProfit(simulatingBoard));
                }
                newNodes.add(zero);
                zero.setPrev(node);
            }
            //adding all possible moves
            for (int i = 0; i < moves.size(); i++) {
                //copying the game state
                Board simulatingBoard = new Board(null, null);
                simulatingBoard.copy(node.getBoard());
                Player opponent = null, player = null;
                if (simulatingBoard.getPlayers()[0].equals(node.getPlayer())) {
                    player = simulatingBoard.getPlayers()[0];
                    opponent = simulatingBoard.getPlayers()[1];
                } else {
                    player = simulatingBoard.getPlayers()[1];
                    opponent = simulatingBoard.getPlayers()[0];
                }
                player.setBoard(simulatingBoard);
                opponent.setBoard(simulatingBoard);
                player.placeDisk(moves.get(i));
                simulatingBoard.updateMap(moves.get(i));
                //adding new node with new game state
                Bot realBot = (Bot) getHead().getPlayer();
                Node newState;
                if (player.equals(realBot)) {
                    realBot.copy(player);
                    newState = new Node(opponent, simulatingBoard, realBot.calculateProfit(simulatingBoard));
                } else {
                    realBot.copy(opponent);
                    newState = new Node(opponent, simulatingBoard, realBot.calculateProfit(simulatingBoard));
                }
                newState.setPrev(node);
                newNodes.add(newState);
            }
        }
        layers++;
    }

    /**
     * Finds that with which move we can reach to an specific node
     * @param index Number of the node that we want to check in last layer
     * @return Number of the move that helps us to reach the node
     */
    public int findMove(int index) {
        Node lastNode = nodes.get(layers).get(index);
        while (lastNode.getPrev() != getHead())
            lastNode = lastNode.getPrev();
        int iter = 0, saveIndex = 0;
        for (Node node : nodes.get(1)) {
            if (node.equals(lastNode)) {
                saveIndex = iter;
                break;
            }
            iter++;
        }
        return saveIndex;
    }

    /**
     * Calculates minimum profit of each move.
     * @return A list of profits
     */
    public ArrayList<Integer> getProfits() {
        ArrayList<Integer> list = new ArrayList<>();
        int currentIndex = 0, currentMin = 99999999;
        for (int i = 0; i < nodes.get(layers).size(); i++) {
            int index = findMove(i);

            if (index == currentIndex) {
                if (nodes.get(layers).get(i).getProfit() < currentMin) {
                    currentMin = nodes.get(layers).get(i).getProfit();
                }
            } else {
                //if we are in a new move
                list.add(currentMin);
                currentMin = nodes.get(layers).get(i).getProfit();
                currentIndex = index;
            }
        }
        list.add(currentMin);
        return list;
    }
}
