package test;
/* Game_Model should be the main game's internal logic
It should be able to setup the initial level by calling a class, move the ball and the player,
and setup the next level when the current level ends
*/

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;



public class Game_Model {

    private Point startPoint; //the starting point of the level


    private Random rnd;
    private Rectangle area;
    Ball ball;
    Player player;
    Wall wall;
    private int ballCount;
    private boolean ballLost;
    private static final int ScoreLength = 5;
    int getScoreLength(){return ScoreLength;}
    private int Score;
    public void IncrementScore(int value){
        Score += Math.abs(value);
    }
    public void ResetScore(){
        Score = 0;
    }
    public int GetScore(){return Score;}

    boolean highscoremenu;
    boolean gethighscoremenu(){return highscoremenu;}
    void toggleHighscoremenu(){highscoremenu = !(highscoremenu);}


    protected Game_Model(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        makeBall(ballPos);
        ballCount = 3;
        ballLost = false;

        rnd = new Random();
        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        wall = new Wall(drawArea,brickCount,lineCount,brickDimensionRatio);

        area = drawArea;

        LevelReset();
        //create wall of bricks
        //create player
        //create ball
        //move player and ball

    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public void move(){
        player.move();
        ball.move();
    }


    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            wall.BrickCollision();
            for(Brick b : wall.bricks) {
                if (b.findImpact(ball) != 0) {
                    IncrementScore(b.GetScore());
                    b.SetScore();
                }
            }
        }

        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }
    boolean impactWall(){
        for(Brick b : wall.bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    if (b.isBroken()){

                    }
                    return b.setImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Brick.Crack.LEFT);
            }
        }
        return false;
    }
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }




    public void LevelReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        float speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;

    }


    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }


    public int getBallCount(){
        return ballCount;
    }
    public void resetBallCount(){
        ballCount = 3;
    }
    public boolean ballEnd(){
        return ballCount == 0;
    }
    public boolean isBallLost(){
        return ballLost;
    }


}