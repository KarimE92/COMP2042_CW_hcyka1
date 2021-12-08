package test;
import java.awt.*;
import java.awt.geom.Point2D;

public class Powerup {

    private Point2D center;

    public Powerup(){
 //random x and y coords between a certain range
        resetPowerup();
        //from here you can have a method that makes the power up's face that would inherit from this class
    }

    public Point2D getPosition(){
        return center;
    }


    public void resetPowerup(){
        int minX = 50;
        int maxX = 550;
        int point_x = (int)(Math.random() * (maxX - minX)) + minX;
        int minY = 100;
        int maxY = 300;
        int point_y = (int)(Math.random() * (maxY - minY)) + minY;
        this.center = new Point(point_x, point_y);
        resetFace(getPosition());
    }

    public void resetFace(Point2D point){}

    public Shape getmultiFace(){return null;}

    public Color getInnerColor(){return null;}

    public Color getBorderColor(){return null;}
}
