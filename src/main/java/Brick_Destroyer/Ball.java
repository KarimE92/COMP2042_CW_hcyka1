package Brick_Destroyer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
abstract public class Ball {


    protected Point2D center;
    Point2D getCenter(){return center;}
    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    protected Shape ballFace;


    protected float speedX;
    protected float speedY;

    /**
     *Ball constructor method for the ball class. It sets where the center of the ball is, makes the ball's face, which is the visual aspect of the ball to be displayed to the user, sets up the ball's cardinal directions, and sets the ball's initial speed
     * @param center the center of the ball
     * @param radius the radius of the ball
     */
    protected Ball(Point2D center, int radius){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();


        up.setLocation(center.getX(),center.getY()-(radius / 2));
        down.setLocation(center.getX(),center.getY()+(radius / 2));

        left.setLocation(center.getX()-(radius /2),center.getY());
        right.setLocation(center.getX()+(radius /2),center.getY());

        ballFace = makeBall(center,radius,radius);

        speedX = 0;
        speedY = 0;
    }

    /**
     * makeBall is used to create the ballFace, which is the visual aspect of the ball
     * @param center represents the ball's center
     * @param radiusA represents the ball's horizontal radius
     * @param radiusB represents the ball's vertical radius
     * since we're making a ball radiusA and radiusB are almost always equal
     * @return returns the visual aspect of our ball
     */
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 2);
        double y = center.getY() - (radiusB / 2);

        return new Ellipse2D.Double(x,y,radiusA,radiusB);
    }

    /**
     * setSpeed changes the speed of our ball
     * @param x is the horizontal velocity of our ball
     * @param y is the vertical velocity of our ball
     */
    public void setSpeed(float x,float y){
        speedX = x;
        speedY = y;
    }

    /**
     * setXSpeed changes only the horizontal velocity of our ball
     * @param s is the new horizontal velocity of our ball
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * setYSpeed changes only the vertical velocity of our ball
     * @param s is the new vertical velocity of our ball
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * reverseX reverses the horizontal velocity of our ball, and is normally used when the ball collides with the left and right borders or with the side of a brick
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * reverseY reverses the vertical velocity of our ball, and is normally used when the ball collides with a brick
     */
    public void reverseY(){
        speedY *= -1;
    }


    /**
     * getPosition gets us the coordinates of our ball
     * @return the coordinates of the ball
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * moveTo changes the ball's location to a specified point
     * @param p the point we want to change the ball's location to
     */
    public void moveTo(Point p){
        getCenter().setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() -(w / 2)),(getCenter().getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * getBallFace gets us the ball's face which is the visual aspect of our ball
     * @return ballFace, the visual aspect of our ball
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * move is the method we call to move our ball every frame by changing the location of the center and creating a new ballFace for that center
     */
    public void move(){
         //this will make the ball get faster and faster every frame in the direction it is going
        RectangularShape tmp = (RectangularShape) ballFace;

        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }

    /**
     * accelerate allows our ball to accelerate every few frames, since the value of acceleration is added/subtracted from speedX and speedY depending on which way the ball is headed
     */
    public void accelerate(){
        float acceleration = (float) 0.0025;
        if (speedX >= 0 && speedX < 10){ //if the ball is moving to the right it should accelerate to the right up to a max speed
            speedX+= acceleration;
        }else if(speedX < 0 && speedX > -10){ //if the ball is moving to the left it should accelerate to the left up to a max speed
            speedX-= acceleration;
        }
        if (speedY > 0 && speedY < 8){ //if the ball is moving down it should accelerate downwards up to a max speed
            speedY+= acceleration;
        }else if(speedY <= 0 && speedY > -8){ //if the ball is moving up it should accelerate upwards up to a max speed
            speedY-= acceleration;
        }
    }

    /**
     * setPoints sets the new points for the ballFace when moving the ball
     * @param width the width of the ball
     * @param height the height of the ball
     */
    public void setPoints(double width,double height){
        this.up.setLocation(center.getX(),center.getY()-(height / 2));
        this.down.setLocation(center.getX(),center.getY()+(height / 2));

        this.left.setLocation(center.getX()-(width / 2),center.getY());
        this.right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * getSpeedX gets us the horizontal velocity of our ball
     * @return the horizontal velocity of our ball
     */
    public float getSpeedX(){
        return speedX;
    }

    /**
     * getSpeedY gets us the vertical velocity of our ball
     * @return the vertical velocity of our ball
     */
    public float getSpeedY(){
        return speedY;
    }


}
