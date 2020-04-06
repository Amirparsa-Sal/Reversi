package com.amirparsa.salmankhah;

/**
 * Represents a Disk with its sign and position.
 *
 * @author Amirparsa Salmankhah
 * @version 1.0
 */
public class Disk {
    //Sign of the disk
    private char sign;
    //Position of the disk
    private Point position;

    /**
     * Constructor without any parameter. Makes a disk at origin with null sign.
     */
    public Disk() {
        this('\0', 0, 0);
    }

    /**
     * Constrcutor with 3 parameters.
     *
     * @param sign Sign of the disk
     * @param x    X of the disk
     * @param y    Y of the disk
     */
    public Disk(char sign, int x, int y) {
        this.sign = sign;
        position = new Point(x, y);
    }

    /**
     * Sign setter
     *
     * @param sign Sign of the disk
     */
    public void setSign(char sign) {
        this.sign = sign;
    }

    /**
     * Position setter
     *
     * @param x X of the disk
     * @param y Y of the disk
     */
    public void setPosition(int x, int y) {
        position.setX(x);
        position.setY(y);
    }

    /**
     * Sign getter
     *
     * @return Sign of the disk
     */
    public char getSign() {
        return sign;
    }

    /**
     * Position getter
     *
     * @return Position of the disk
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Copy other disk fiels to this disk.
     *
     * @param otherDisk Other disk
     */
    public void copy(Disk otherDisk) {
        this.setSign(otherDisk.getSign());
        this.getPosition().copy(otherDisk.getPosition());
    }
}
