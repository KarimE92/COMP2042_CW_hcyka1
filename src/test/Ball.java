package test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {


    private Point2D center;
    Point2D getCenter(){return center;}
    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Shape ballFace;


    private float speedX;
    private float speedY;
    private float acceleration = (float) 0.0025;

    public final int MAXBALLSPEED = 10;

    public Ball(Point2D center, int radius){
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

    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 2);
        double y = center.getY() - (radiusB / 2);

        return new Ellipse2D.Double(x,y,radiusA,radiusB);
    }


    public void setSpeed(float x,float y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }



    public Point2D getPosition(){
        return center;
    }

    public void moveTo(Point p){
        getCenter().setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() -(w / 2)),(getCenter().getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    public Shape getBallFace(){
        return ballFace;
    }


    public void move(){
         //this will make the ball get faster and faster every frame in the direction it is going
        RectangularShape tmp = (RectangularShape) ballFace;
        if (speedX > 0 && speedX < 10){ //if the ball is moving to the right it should accelerate to the right up to a max speed
            speedX+=acceleration;
        }else if(speedX < 0 && speedX > -10){ //if the ball is moving to the left it should accelerate to the left up to a max speed
            speedX-=acceleration;
        }
        if (speedY > 0 && speedY < 10){ //if the ball is moving down it should accelerate downwards up to a max speed
            speedY+=acceleration;
        }else if(speedY < 0 && speedY > -10){ //if the ball is moving up it should accelerate upwards up to a max speed
            speedY-=acceleration;
        }
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }


    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public float getSpeedX(){
        return speedX;
    }

    public float getSpeedY(){
        return speedY;
    }


}
