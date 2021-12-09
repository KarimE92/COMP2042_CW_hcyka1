package test;

import java.awt.*;
import java.awt.Point;


/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class ClayBrick extends Brick {

    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;
    private static final int Clay_Points = 1000;


    /**
     * ClayBrick is the constructor method for the ClayBrick class, and it creates a brick and then sets its score
     * @param point the coordinates of where the brick should be made
     * @param size the size of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
        ResetScore();
    }

    /**
     * makeBrickFace creates the face of the brick, the visual component of the brick to be shown to the player
     * @param pos the position of the brick
     * @param size the size of the brick
     * @return
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrick gets us our brickFace to draw to the user
     * @return the face of the brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }

    /**
     * MakeScore sets the score of the ClayBrick that will be given to the player when they break it
     */
    @Override
    public void MakeScore(){Score = Clay_Points;}
}
