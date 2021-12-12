package Brick_Destroyer;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.io.FileNotFoundException;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class GameView extends JComponent {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private static final Color MENU_COLOR = new Color(0, 255, 0); //pause menu colour
    private static final int TEXT_SIZE = 30;
    private static final Color BG_COLOR = Color.WHITE;

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private int strLen;

    private String message;
    private GameModel Model;
    private GameController Controller;
    private Font menuFont;
    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;


    /**
     * GameView is the constructor method of the GameView class. It sets up the game screen and the opening message for the game, as well as save the GameController
     * @param GameModel is the game's main logic
     * @param GameController the game's main controls
     */
    protected GameView(GameModel GameModel, GameController GameController) {
        Model = GameModel;
        Controller = GameController;
        strLen = 0;
        message = "Press Spacebar to Start!";
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(GameController);
        this.addMouseListener(GameController);
        this.addMouseMotionListener(GameController);
    }

    /**
     * updatescreen is called every frame. If updates the controller, increments the score, and repaints the entire screen
     * @param GameController the game's main controls
     */
    public void updatescreen(GameController GameController){
        this.Controller = GameController;
        this.Controller.getGame().IncrementScore((int)(this.Controller.getGame().getBall().getSpeedX()));
        repaint();
    }

    /**
     * paint draws all the visual aspects of the game. It draws the ball, then the powerups if there are any, then miniballs if there are any, then bricks, then the player. If the pause menu is on it draws the pause menu, and if the highscore menu is on it draws the highscore menu
     * @param g the graphics to paint the game with
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(Controller.getGame().getBall(),g2d);
        for(int i= 0; i<Controller.getGame().getLevels().getmultiballpoweruplevelcount(); i++){
            if(Controller.getGame().getLevels().getMultiballpowerup(i).getshowmulti()) {
                drawPowerup(Controller.getGame().getLevels().getMultiballpowerup(i), g2d);
            }
        }

        for(int i= 0; i<Controller.getGame().getLevels().getextralifepoweruplevelcount(); i++){
            if(Controller.getGame().getLevels().getExtraLifepowerup(i).getshowextralife()) {
                drawPowerup(Controller.getGame().getLevels().getExtraLifepowerup(i), g2d);
            }
        }


        if (!Controller.getGame().getMiniBalls().isEmpty()){
            for(int i=0; i<Controller.getGame().getMiniBalls().size(); i++){
                drawMiniBall(Controller.getGame().getMiniBalls().get(i), g2d);
            }
        }
        for(Brick b : Controller.getGame().getLevels().bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(Controller.getGame().getPlayer(),g2d);

        if(Controller.getpausemenu())
            drawPauseMenu(g2d);

        if(Controller.getGame().gethighscoremenu()) {
            try {
                drawHighScore(g2d);
            } catch (FileNotFoundException e) {
                System.out.println("Error!");
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * drawBrick draws all the bricks on screen. It fills the part of the screen the brick should be in with the brick's color
     * @param brick the brick to draw on screen
     * @param g2d the graphics to paint the game with
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    /**
     * drawBall draws the main ball on screen by filling the specific area the ball is in with the ball's color
     * @param ball the ball to be drawn on screen
     * @param g2d the graphics used to paint the game with
     */
    private void drawBall(RubberBall ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * drawMiniBall draws a miniball by filling the specific area the miniball is in with the miniball's color
     * @param ball the miniball to be drawn on screen
     * @param g2d the graphics used to paint the game with
     */
    private void drawMiniBall(MiniBall ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * drawPowerup draws a powerup by filling the specific area the powerup is in with the powerup's color
     * @param powerup the powerup to be drawn on screen
     * @param g2d the graphics used to paint the game with
     */
    private void drawPowerup(Powerup powerup,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = powerup.getmultiFace();

        g2d.setColor(powerup.getInnerColor());
        g2d.fill(s);

        g2d.setColor(powerup.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * drawPlayer draws the player by filling the specific area the player is in with the player's color
     * @param p the player
     * @param g2d the graphics used to paint the game with
     */
    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }


    /**
     * drawHighScore draws the high score menu by opening a file containing all the saved highscores and outputting that on screen
     * @param g2d the graphics used to draw the game in
     * @throws FileNotFoundException in case the savefile containing high scores is not found
     */
    private void drawHighScore(Graphics2D g2d) throws FileNotFoundException {
        obscureGameController(g2d);
        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        int[] highscores = Model.gethighscorelist();

        int x = (this.getWidth() - strLen) / 3;
        int y = this.getHeight() / 10;

        g2d.drawString("High Scores:", x, y);
        x = (this.getWidth() - strLen) / 4;
        y+=100;
        for(int i=0; i< Controller.getGame().getScoreLength(); i++){
            g2d.drawString(String.valueOf(highscores[i]), x, y);
            y+=50;
        }

        x= (this.getWidth() - strLen) / 4;
        y= 400;
        g2d.drawString("Press Space to Restart", x, y);

    }

    /**
     * drawPauseMenu draws the pause menu and all the buttons included in the pause menu
     * @param g2d the graphics used to paint the game in
     */
    private void drawPauseMenu(Graphics2D g2d){
        obscureGameController(g2d);
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * clear clears all the colors currently stored in g2d and resets them to default
     * @param g2d the graphics used to paint the game with
     */
    void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /**
     * obscureGameController is called to dim the screen whenever a new screen has to be displayed. ie the pause menu or the highscore menu
     * @param g2d the graphics used to paint the game with
     */
    void obscureGameController(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * setmessage changes the message to be displayed on screen
     * @param string the message to be displayed on screen
     */
    void setmessage(String string){message = string; }

    /**
     * getwidth gives us the width of the game's window
     * @return the game's width
     */
    int getwidth(){return DEF_WIDTH;}

    /**
     * getheight gives us the height of the game's window
     * @return the game's height
     */
    int getheight(){return DEF_HEIGHT;}

    /**
     * getcontinueButtonRect gives us the continue button's rectangle to draw on the screen
     * @return continue button's rectangle
     */
    Rectangle getcontinueButtonRect(){return continueButtonRect;}

    /**
     * getexitButtonRect gives us the exit button's rectangle to draw on the screen
     * @return exit button's rectangle
     */
    Rectangle getexitButtonRect(){return exitButtonRect;}

    /**
     * getrestartButtonRect gives us the restart button's rectangle to draw on the screen
     * @return restart button's rectangle
     */
    Rectangle getrestartButtonRect(){return restartButtonRect;}
}

















