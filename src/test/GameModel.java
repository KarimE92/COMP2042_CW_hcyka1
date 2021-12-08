package test;
/* GameModel should be the main game's internal logic
It should be able to setup the initial level by calling a class, move the ball and the player,
and setup the next level when the current level ends
*/

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;



public class GameModel {

    private int BallRadius = 15;
    private int MiniBallRadius = 10;


    private Point startPoint; //the starting point of the level
    private Random rnd;
    private Rectangle area;
    private RubberBall ball;
    RubberBall getBall(){return ball;}
    private Player player;
    Player getPlayer(){return player;}
    private Levels levels;
    Levels getWall(){return levels;}

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
        for(Brick b : levels.bricks){
            b.ResetScore();
        }

    }
    protected GameModel(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        makeBall(ballPos);
        ballCount = 3;
        ballLost = false;

        player = new Player((Point) ballPos.clone(),150,15, drawArea);

        levels = new Levels(drawArea,brickCount,lineCount,brickDimensionRatio);

        area = drawArea;




        ResetPosition();
        //create levels of bricks
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
        for(int i = 0; i< levels.getmultiballpoweruplevelcount(); i++){
            for(int j = 0; j< levels.getextralifepoweruplevelcount(); j++) {
                if (levels.getMultiballpowerup(i).impact(ball)) { //checking if ball collides with powerup
                    for (int k = 0; k < 3; k++) {
                        Point MiniBallCenter = new Point((int) ((ball.getPosition()).getX() + k), (int) ball.getPosition().getY() + k);
                        MiniBalls.add(new MiniBall(MiniBallCenter, MiniBallRadius));
                    }
                } else if (levels.getExtraLifepowerup(i).impact(ball)) {
                    ballCount++;
                }

                for (int k = 0; k < MiniBalls.size(); k++) { //checking if miniball collides with powerup
                    if (levels.getMultiballpowerup(i).impact(MiniBalls.get(k))) {
                        for (int l = 0; l < 3; l++) {
                            Point MiniBallCenter = new Point((int) ((MiniBalls.get(j).getPosition()).getX() + l), (int) MiniBalls.get(j).getPosition().getY() + l);
                            MiniBalls.add(new MiniBall(MiniBallCenter, MiniBallRadius));
                        }
                    } else if (levels.getExtraLifepowerup(i).impact(MiniBalls.get(k))) {
                        ballCount++;
                    }
                }
            }
        }



        //ball collision logic
        if (player.impact(ball)) {
            ball.reverseY();
        }
        if (impactWall(ball)) {
            levels.BrickCollision();
            for (Brick b : levels.bricks) {
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
                    levels.BrickCollision();
                    for (Brick b : levels.bricks) {
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
        for(Brick b : levels.bricks){
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