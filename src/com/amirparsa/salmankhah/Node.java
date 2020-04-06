package com.amirparsa.salmankhah;

import java.util.ArrayList;

/**
 * Represents a node which contains an specific game state.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0.0
 */
public class Node {
    //Previous node
    private Node prev;
    //Board of the node
    private Board board;
    //Player of the node
    private Player player;
    //Profit of the node
    private int profit;

    /**
     * Constructor with 3 parameters
     *
     * @param player Player of the node
     * @param board  Board of the node
     * @param profit Profit of the node
     */
    public Node(Player player, Board board, int profit) {
        this.board = board;
        this.profit = profit;
        this.player = player;
        prev = null;
    }

    /**
     * Previous node getter
     *
     * @return Previous node
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Previous node setter
     *
     * @param prev Previous node
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    /**
     * Player getter
     *
     * @return Player of the node
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Player setter
     *
     * @param player Player of the node
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Board getter
     *
     * @return Board of the node
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Board setter
     *
     * @param board Board of the node
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Profit getter
     *
     * @return Profit of the node
     */
    public int getProfit() {
        return profit;
    }

    /**
     * Profit setter
     *
     * @param profit Profit of the node
     */
    public void setProfit(int profit) {
        this.profit = profit;
    }
}
