package Brick_Destroyer;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class MultiballPowerup extends Powerup{
    private final int radius = 16;
    private final Color inner = new Color(0, 53, 255); //Defining the colour of the ball
    private final Color border = inner.darker().darker();


    private Shape multiFace;
    private boolean ShowMultiBall = true;

    /**
     * MultiballPowerup is the constructor method of the MultiballPowerup class. It creates a powerup, and then creates the face for the powerup to be displayed to the user
     */
    public MultiballPowerup(){
        super();
        multiFace = makePowerup(getPosition());
    }
    /**
     * makePowerup creates the powerup's face which is the visual aspect of the MultiballPowerup that will be displayed to the user
     * @param center the coordinates of where the powerup will be made
     * @return the multiballpowerup's face to be displayed to the user
     */
    public Shape makePowerup(Point2D center){
        double x = center.getX() - (radius / 2);
        double y = center.getY() - (radius / 2);

        return new Ellipse2D.Double(x,y, radius, radius);
    }

    /**
     * getmultiFace gets us the powerup's face to be displayed to the user
     * @return the powerup's face to be displayed to the user
     */
    @Override
    public Shape getmultiFace(){
        return multiFace;
    }

    /**
     * getBorderColor gets us the color of the border of the powerup to be displayed to the user
     * @return the color of the border of the powerup to be displayed to the user
     */
    @Override
    public Color getBorderColor(){
        return border;
    }

    /**
     * getInnerColor gets us the color of the powerup to be displayed to the user
     * @return the color of the powerup to be displayed to the user
     */
    @Override
    public Color getInnerColor(){
        return inner;
    }

    /**
     * impact chekcs if there was a collision with a ball and makes itself invisible if true
     * @param b is the ball we're testing collision with
     * @return true if there was a collision with the ball
     */
    public boolean impact(Ball b){
        if (multiFace.contains(b.getPosition()) && (getshowmulti())){
            setshowmulti();
            return true;
        }
            return false;
    }
    /**
     * resetFace creates a new face to be displayed to the user
     * @param point is the coordinates where the face should be located
     */
    @Override
    public void resetFace(Point2D point){
        multiFace = makePowerup(point);
    }

    /**
     * getshowemulti gets us the variable ShowMultiBall which tells us whether or not to draw the powerup or not
     * @return ShowMultiBall
     */
    public boolean getshowmulti(){return ShowMultiBall;}

    /**
     * setshowmulti toggles ShowMultiBall from false to true and vice versa
     */
    public void setshowmulti(){ShowMultiBall = !(ShowMultiBall);}

}

