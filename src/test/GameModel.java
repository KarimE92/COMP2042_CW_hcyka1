package test;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class GameModel {
    private final int ballRadius = 15;
    private static final int ScoreLength = 5;
    private final int StartingLives = 3;


    private RubberBall ball;
    private final Player player;
    private final Levels levels;
    private final Point startPoint; //the starting point of the level
    private final Rectangle area;
    private final ArrayList<MiniBall> MiniBalls = new ArrayList<>();
    private int ballCount;
    private boolean ballLost;
    private int Score;
    private boolean highscoremenu;

    /**
     * GameModel is the constructor method for the GameModel class. It creates the ball,player,levels and the area we play the game in
     * @param drawArea the area we play the game in
     * @param brickCount the number of bricks for the level
     * @param lineCount the number of lines of bricks for the level
     * @param brickDimensionRatio the size of the bricks
     * @param ballPos the coordinates of the ball's starting position
     */
    protected GameModel(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        makeBall(ballPos);
        ballCount = 3;
        ballLost = false;

        player = new Player((Point) ballPos.clone(),150,15, drawArea);

        levels = new Levels(drawArea,brickCount,lineCount,brickDimensionRatio);

        area = drawArea;


        ResetPosition();

    }

    /**
     * makeBall creates a new ball
     * @param ballPos the position of the ball to be made
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos, ballRadius);
    }

    /**
     * move moves the miniballs if there are any, accelerates then moves the ball, and moves the player
     */
    public void move(){

        for (MiniBall miniBall : MiniBalls) {
            miniBall.move();
        }

        ball.accelerate();
        ball.move();

        player.move();

    }

    /**
     * findImpacts checks for collisions between powerups and ball, powerups and miniballs, bricks and ball, bricks and miniballs, player and ball, player and miniball, the borders of the game and ball, the borders and miniball
     */
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

    /**
     * impactWall loops through all the bricks to detect a collision, and if one is detected determines how the ball should move after a collision
     * @param ball the ball being checked for collisions
     * @return true if a collision occurred
     */
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

    /**
     * impact border checks if there was a collision between the ball and the border of the screen
     * @param ball the ball being checked for collisions
     * @return true if a collision occurred
     */
    private boolean impactBorder(Ball ball){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }


    /**
     * ResetPosition returns the player and the ball to their starting positions, and randomly determines the ball's speed
     */
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


    /**
     * getBallCount gives us the number of balls (lives) we have left
     * @return the number of lives we have left
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * resetBallCount resets our number of lives back to the default number
     */
    public void resetBallCount(){
        ballCount = StartingLives;
    }

    /**
     * ballEnd tells us if we are out of lives
     * @return true if ballCount is 0 (we have no more lives)
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * isBallLost tells us if our ball is offscreen
     * @return true if ball is offscreen
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * gets us our ball so we can use it in collision detection
     * @return ball
     */
    RubberBall getBall(){return ball;}

    /**
     * gets us our player so we can use it in collision detection
     * @return player
     */
    Player getPlayer(){return player;}

    /**
     * gets us our levels so we can use the bricks in collision detection
     * @return levels
     */
    Levels getLevels(){return levels;}

    /**
     * gets us our array of miniballs so we can use them in collision detection
     * @return
     */
    public ArrayList<MiniBall> getMiniBalls(){return MiniBalls;}

    /**
     * ClearMiniBalls removes all the MiniBalls currently in the MiniBalls array for the next level
     */
    public void ClearMiniBalls(){MiniBalls.removeAll(MiniBalls);}

    /**
     * getScoreLength gets us how many scores are displayed in the highscore file
     * @return how many scores can be displayed in the highscore file
     */
    int getScoreLength(){return ScoreLength;}

    /**
     * IncrementScore ups our score by a certain value
     * @param value the value the score is increased by
     */
    public void IncrementScore(int value){
        Score += Math.abs(value);
    }

    /**
     * ResetScore resets our score to 0
     */
    public void ResetScore(){
        Score = 0;
    }

    /**
     * GetScore gets us our current score
     * @return current score
     */
    public int GetScore(){return Score;}

    /**
     * gethighscoremenu gets us highscoremenu which tells us if we should display highscoremenu
     * @return highscoremenu
     */
    boolean gethighscoremenu(){return highscoremenu;}

    /**
     * toggleHighscoremenu makes highscoremenu false if it's true and vice versa
     */
    void toggleHighscoremenu(){highscoremenu = !(highscoremenu);}

    /**
     * refreshWall loops through every single brick in the level and resets its score
     */
    void refreshWall(){
        for(Brick b : levels.bricks){
            b.ResetScore();
        }

    }



}