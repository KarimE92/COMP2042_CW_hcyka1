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


    private final Point startPoint; //the starting point of the level
    private final Rectangle area;
    private RubberBall ball;
    RubberBall getBall(){return ball;}
    private final Player player;
    Player getPlayer(){return player;}
    private final Levels levels;
    Levels getLevels(){return levels;}

    private final ArrayList<MiniBall> MiniBalls = new ArrayList<>();
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
        int ballRadius = 15;
        ball = new RubberBall(ballPos, ballRadius);
    }

    public void move(){

        for (MiniBall miniBall : MiniBalls) {
            miniBall.move();
        }

        ball.accelerate();
        ball.move();

        player.move();

    }


    public void findImpacts() {
        for(int i = 0; i< levels.getmultiballpoweruplevelcount(); i++){
            for(int j = 0; j< levels.getextralifepoweruplevelcount(); j++) {
                int miniBallRadius = 10;
                if (levels.getMultiballpowerup(i).impact(ball)) { //checking if ball collides with powerup
                    for (int k = 0; k < 3; k++) {
                        Point MiniBallCenter = new Point((int) ((ball.getPosition()).getX() + k), (int) ball.getPosition().getY() + k);
                        MiniBalls.add(new MiniBall(MiniBallCenter, miniBallRadius));
                    }
                } else if (levels.getExtraLifepowerup(i).impact(ball)) {
                    ballCount++;
                }

                for (int k = 0; k < MiniBalls.size(); k++) { //checking if miniball collides with powerup
                    if (levels.getMultiballpowerup(i).impact(MiniBalls.get(k))) {
                        for (int l = 0; l < 3; l++) {
                            Point MiniBallCenter = new Point((int) ((MiniBalls.get(j).getPosition()).getX() + l), (int) MiniBalls.get(j).getPosition().getY() + l);
                            MiniBalls.add(new MiniBall(MiniBallCenter, miniBallRadius));
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
            for (MiniBall miniBall : MiniBalls) {
                if (player.impact(miniBall)) {
                    miniBall.reverseY();
                }

                if (impactWall(miniBall)) {
                    levels.BrickCollision();
                    for (Brick b : levels.bricks) {
                        if (b.findImpact(miniBall) != 0) {
                            IncrementScore(b.GetScore());
                            b.SetScore();
                        }
                    }
                }
                if (impactBorder(miniBall)) {
                    miniBall.reverseX();
                }
                if (miniBall.getPosition().getY() < area.getY()) {
                    miniBall.reverseY();
                }

            }
        }
    }


    boolean impactWall(Ball ball){
        for(Brick b : levels.bricks){
            //Vertical Impact
            //Horizontal Impact
            switch (b.findImpact(ball)) {
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.down, Brick.Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.up, Brick.Crack.DOWN);
                }
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.right, Brick.Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.left, Brick.Crack.LEFT);
                }
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
        Random rnd = new Random();
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