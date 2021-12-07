package test;
import java.awt.*;
import java.awt.geom.Point2D;

public class Powerup {

    private Point2D center;
    private int minX = 50;
    private int maxX = 550;

    private int minY = 100;
    private int maxY = 300;

    public Powerup(){
        int point_x = (int)(Math.random() * (maxX - minX)) + minX;
        int point_y = (int)(Math.random() * (maxY - minY)) + minY;
        this.center = new Point(point_x, point_y); //random x and y coords between a certain range

        //from here you can have a method that makes the power up's face that would inherit from this class
    }

    public Point2D getPosition(){
        return center;
    }




}
