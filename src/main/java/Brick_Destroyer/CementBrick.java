package Brick_Destroyer;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class CementBrick extends Brick {


    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private static final int Cement_Points = 2000;
    private final Crack crack;
    private Shape brickFace;

    /**
     *CementBrick is the constructor method for CementBricks, and it creates a brick, sets its score, creates a crack, and then creates a brickFace so the brick can be displayed
     * @param point the coordinates where the brick will be created
     * @param size the size of the brick
     */
    public CementBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        ResetScore();
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * makeBrickFace creates the face of the brick to be displayed to the player
     * @param pos the position of the brick
     * @param size the size of the brick
     * @return a rectangle representing the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * setImpact is called when a brick collision occurs, and it checks if a brick is broken, if not it impacts it, and if it's still not broken it makes a crack on the brick
     * @param point is the point of the impact
     * @param dir is the direction of the impact
     * @return whether or not there was an impact
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * getBrick gets us the brickFace to draw onto the screen
     * @return the brickFace, the visual component of our brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * updateBrick updates a brick if it's cracked by drawing a crack and updating the brickFace to include that crack
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * repair fixes the brick if its broken and removes any cracks it has on it if any
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }

    /**
     * MakeScore sets the score of the CementBrick that will be given to the player when they break it
     */
    @Override
    public void MakeScore(){Score = Cement_Points;}
}
