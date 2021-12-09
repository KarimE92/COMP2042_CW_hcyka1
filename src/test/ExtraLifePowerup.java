package test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class ExtraLifePowerup extends Powerup{
    private final int radius = 16;
    private final Color inner = new Color(255, 0, 0); //Defining the colour of the ball
    private final Color border = inner.darker().darker();


    private Shape lifeFace;
    private boolean ShowExtraLife = true;

    /**
     * ExtraLifePowerup is the constructor method of the ExtraLifePowerup class. It creates a powerup and then creates a face for it to be displayed to the user
     */
    public ExtraLifePowerup(){
        super();
        lifeFace = makePowerup(getPosition());

    }

    /**
     * makePowerup creates the powerup's face which is the visual aspect of the extralifepowerup that will be displayed to the user
     * @param center the coordinates of where the powerup will be made
     * @return the extralifepowerup's face to be displayed to the user
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
        return lifeFace;
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
        if (lifeFace.contains(b.getPosition()) && (ShowExtraLife)){
            setshowextralife();
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
        lifeFace = makePowerup(point);
    }

    /**
     * getshowextralife gets us the variable ShowExtraLife which tells us whether or not to draw the powerup or not
     * @return ShowExtraLife
     */
    public boolean getshowextralife(){return ShowExtraLife;}

    /**
     * setshowextralife toggles ShowExtraLife from false to true and vice versa
     */
    public void setshowextralife(){ShowExtraLife = !(ShowExtraLife);}
}
