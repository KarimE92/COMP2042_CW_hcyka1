
package test;

import java.awt.*;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class Player {
    private static final int DEF_MOVE_AMOUNT = 5;
    private static final int DEF_SPRINT_AMOUNT = 10;
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private final Rectangle playerFace;
    private final Point ballPoint;
    private int moveAmount;
    private final int min;
    private final int max;

    /**
     * Player is the constructor method for the Player class. It
     * @param ballPoint the coordinates where the player should be made
     * @param width the width of the player
     * @param height the height of the player
     * @param container the size of the window so that the player does not go off screen
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * makeRectangle creates the rectangle that will be displayed to the player
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return the rectangle to be displayed to the player
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * impact returns true if the player has collided with a ball
     * @param b the ball being tested for collisions
     * @return true if there was a collision between ball and player
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * move moves the player by changing the location of the ballPoint as well as the playerFace which is drawn for the user
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min-10 || x > max+10)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * moveLeft moves the player to the left by a defined amount
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * sprintLeft moves the player to the left by a defined amount that is greater than moveLeft's defined amount
     */
    public void sprintLeft(){moveAmount = -DEF_SPRINT_AMOUNT;}

    /**
     * moveRight moves the player to the right by a defined amount
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * sprintRight moves the player to the right by a defined amount that is greater than moveRight's defined amount
     */
    public void sprintRight(){moveAmount = DEF_SPRINT_AMOUNT;}

    /**
     * stop stops the player from moving
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * getPlayerFace gets us the playerface to be displayed to the player
     * @return the playerface to be displayed to the player
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * moveTo moves the player to a specified point on the screen
     * @param p the coordinates of the point the player should be moved to
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}
