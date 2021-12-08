package test;
/* Game_Model should be the main game's internal logic
It should be able to setup the initial level by calling a class, move the ball and the player,
and setup the next level when the current level ends
*/

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;



public class Game_Model {

    private Point startPoint; //the starting point of the level

    private int BallRadius = 15;
    private int MiniBallRadius = 10;


    private Random rnd;
    private Rectangle area;
    private int bricknum;
    private int linenum;
    private double brickdimension;
    private RubberBall ball;
    RubberBall getBall(){return ball;}
    private Player player;
    Player getPlayer(){return player;}
    private Wall wall;
    Wall getWall(){return wall;}
    private Multiball multiball;
    Multiball getmultipowerup(){return multiball;}

    private ArrayList<MiniBall> MiniBalls = new ArrayList<>();
    public ArrayList<MiniBall> getMiniBalls(){return MiniBalls;}
    public void ClearMiniBalls(){MiniBalls.removeAll(MiniBalls);}
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

    void refreshWall(){
        for(Brick b : wall.bricks){
            b.ResetScore();
        }

    }
    protected Game_Model(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        makeBall(ballPos);
        ballCount = 3;
        ballLost = false;

        player = new Player((Point) ballPos.clone(),150,15, drawArea);

        wall = new Wall(drawArea,brickCount,lineCount,brickDimensionRatio);

        area = drawArea;
        bricknum = brickCount;
        linenum = lineCount;
        brickdimension = brickDimensionRatio;



        ResetPosition();
        //create wall of bricks
        //create player
        //create ball
        //move player and ball

    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos, BallRadius);
    }

    public void move(){

        for (int i = 0; i < MiniBalls.size(); i++) {
                (MiniBalls.get(i)).move();
            }

        ball.accelerate();
        ball.move();

        player.move();

    }


    public void findImpacts() {
        if (wall.getMultiball().impact(ball)) {
            for (int i = 0; i < 3; i++) {
                Point MiniBallCenter = new Point((int)((ball.getPosition()).getX() + i), (int)ball.getPosition().getY() + i);
                MiniBalls.add(new MiniBall(MiniBallCenter, MiniBallRadius));
            }
        }

        //ball collision logic
        if (player.impact(ball)) {
            ball.reverseY();
        }
        if (impactWall(ball)) {
            wall.BrickCollision();
            for (Brick b : wall.bricks) {
                if (b.findImpact(ball) != 0) {
                    IncrementScore(b.GetScore());
                    b.SetScore();
                }
            }
        }

        if (impactBorder(ball)) {
            ball.reverseX();
        }

                if (ball.getPosition().getY() < area.getY()) {
                    ball.reverseY();
                }
                if (ball.getPosition().getY() > area.getY() + area.getHeight()) {
                    ballCount--;
                    ballLost = true;
                    ClearMiniBalls();
                }
                //Miniball collision logic
        if (!MiniBalls.isEmpty()) {
            for (int i = 0; i < MiniBalls.size(); i++) {
                if (player.impact(MiniBalls.get(i))) {
                    MiniBalls.get(i).reverseY();
                }

                if (impactWall(MiniBalls.get(i))) {
                    wall.BrickCollision();
                    for (Brick b : wall.bricks) {
                        if (b.findImpact(MiniBalls.get(i)) != 0) {
                            IncrementScore(b.GetScore());
                            b.SetScore();
                        }
                    }
                }
                if (impactBorder(MiniBalls.get(i))) {
                    MiniBalls.get(i).reverseX();
                }
                if(MiniBalls.get(i).getPosition().getY() < area.getY()){
                    MiniBalls.get(i).reverseY();
                }

            }
        }
    }


    boolean impactWall(Ball ball){
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
    private boolean impactBorder(Ball ball){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }




    public void ResetPosition(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        rnd = new Random();
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