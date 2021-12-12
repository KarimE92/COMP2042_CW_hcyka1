
package Brick_Destroyer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class SteelBrick extends Brick {

    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;
    private static final int Steel_Points = 3000;

    private final Random rnd;
    private final Shape brickFace;

    /**
     * SteelBrick is the constructor method for the SteelBrick class, and it creates a brick, sets the brick's score, generates a random number, and then creates the face of the brick to be displayed to the player
     * @param point the coordinates where the brick will be made
     * @param size the size of the brick
     */
    public SteelBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        ResetScore();
        rnd = new Random();
        brickFace = super.brickFace;
    }

    /**
     * makeBrickFace creates the brickFace, the visual component of the brick to be displayed to the player
     * @param pos the position of the brick
     * @param size the size of the brick
     * @return the brickFace, the visual component of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrick gets us the brickFace to draw
     * @return the brickFace
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * setImpact is called when a brick collision occurs, and it checks if a brick is broken, if not it impacts it, and if it's still not broken it makes a crack on the brick
     * @param point is the point of the impact
     * @param dir is the direction of the impact
     * @return whether or not there was an impact
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    /**
     * impact is called when a collision occurs. It checks the randomly generated number against the probability and breaks the brick if the random number is smaller
     */
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

    /**
     * MakeScore sets the score of the SteelBrick that will be given to the player when they break it
     */
    @Override
    public void MakeScore(){Score = Steel_Points;}
}
