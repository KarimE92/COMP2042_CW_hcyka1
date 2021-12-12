package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    int Score;
    public int GetScore(){return Score;}
    public void SetScore(){Score =0;}

    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private final GeneralPath crack;

        private final int crackDepth;
        private final int steps;

        /**
         * Crack is the constructor method for the crack class, which represents a crack that forms in bricks that take more than 1 hit to break
         * @param crackDepth the depth of the crack
         * @param steps the number of lines that make up a crack
         */
        public Crack(int crackDepth, int steps){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }


        /**
         * draw returns us our crack in order to draw it on our brick
         * @return crack to draw on the brick
         */
        public GeneralPath draw(){

            return crack;
        }

        /**
         * reset resets our crack so it no longer shows on our brick
         */
        public void reset(){
            crack.reset();
        }

        /**
         * makeCrack makes a crack on our brick
         * @param point the point where we need to make the crack
         * @param direction the direction the crack has to go
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;

            }
        }

        /**
         * makeCrack makes a crack on our brick
         * @param start the starting point of our crack
         * @param end the ending point of our crack
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i, steps))
                    y += jumps(jump);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i, int divisions){
            int low = (Crack.CRACK_SECTIONS / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound){

            if(rnd.nextDouble() > Crack.JUMP_PROBABILITY)
                return randomInBounds(bound);
            return  0;

        }

        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch (direction) {
                case HORIZONTAL -> {
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos, to.y);
                }
                case VERTICAL -> {
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x, pos);
                }
            }
            return out;
        }

    }

    private static Random rnd;

    Shape brickFace;

    private final Color border;
    private final Color inner;

    private final int fullStrength;
    private int strength;

    private boolean broken;

    /**
     * Brick is the constructor method for our brick class, which serves to make a brick that we need for the game
     * @param pos the position of the brick
     * @param size the size of the brick
     * @param border the color of the border of the brick
     * @param inner the inner color of the brick
     * @param strength the strength of the brick. ie, how many hits it will take for the brick to break
     */
    public Brick(Point pos, Dimension size, Color border, Color inner, int strength){
        rnd = new Random();
        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * makeBrickFace is parent method that is overrided by its child classes and is used to make a brickFace, the visual aspect of the brick
     * @param pos the position of the brick
     * @param size the size of the brick
     * @return the brick's face to be drawn on screen
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * setImpact is called when a brick collision has occured
     * @param point is the point of the impact
     * @param dir is the direction of the impact
     * @return if the brick is broken or not
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * getBrick is a parent method that is inherited by its child classes and is just used to get the brick
     * @return the brick
     */
    public abstract Shape getBrick();


    /**
     * getBorderColor is used to fetch the color of the border of the brick so it can be drawn on screen
     * @return the color of the border of the brick
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * getInnerColor is used to fetch the color of the brick so it can be drawn on screen
     * @return the color of the brick
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * findImpact checks to see if any bricks have collided with a ball
     * @param b the ball to check for collisions with
     * @return an integer representing the direction of the impact with the ball, or if broken, returns 10 instead
     */
    public final int findImpact(Ball b){
        if(broken) {
            return 10;
        }
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * isBroken tells us if the brick is broken or not
     * @return the state of the brick: true if broken, false if not broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * repair fixes broken bricks and returns them to their full strength
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * Impact is called whenever a collision occurs, and reduces the strength of a brick, breaking when the strength reaches 0
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    /**
     * ResetScore resets the assigned score to each different type of brick
     */
    public void ResetScore(){
        MakeScore();
    }

    /**
     * MakeScore assigns the coresponding score to that type of brick
     */
    protected abstract void MakeScore();
}





