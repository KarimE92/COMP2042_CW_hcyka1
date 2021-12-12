package Brick_Destroyer;
import java.awt.*;
import java.awt.geom.Point2D;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public abstract class Powerup {
    private Point2D center;

    /**
     * Powerup is the constructor method for the Powerup class. It calls the resetPowerup method
     */
    public Powerup(){
        resetPowerup();
    }

    /**
     * getPosition gives us the coordinates of the powerup to draw on screen and to handle collisions with
     * @return the position of the powerup
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * resetPowerup generates random coordinates for the powerup and then creates a face to be displayed to the user at those coordinates
     */
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

    /**
     * resetFace creates the face of the powerup to be displayed to the user
     * @param point is the coordinates where the face should be located
     */
    protected abstract void resetFace(Point2D point);

    /**
     * getmultiFace gets us the face of the powerup to be displayed to the user
     * @return the face of the powerup
     */
    protected abstract Shape getmultiFace();

    /**
     * getInnerColor gets us the color of the powerup to be displayed to the user
     * @return the color of the powerup
     */
    protected abstract Color getInnerColor();

    /**
     * getBorderColor gets us the color of the border of the powerup to be displayed to the user
     * @return the color of the border of the powerup
     */
    protected abstract Color getBorderColor();
}
