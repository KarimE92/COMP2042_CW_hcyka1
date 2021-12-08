package test;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.io.FileNotFoundException;

public class Game_View extends JComponent {

    private String message;
    void setmessage(String string){message = string; }

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 255, 0); //pause menu colour
    private int strLen;

    private Game_Controller Controller;

    private static final int DEF_WIDTH = 600;
    int getwidth(){return DEF_WIDTH;}
    private static final int DEF_HEIGHT = 450;
    int getheight(){return DEF_HEIGHT;}

    private static final Color BG_COLOR = Color.WHITE;

    private Font menuFont;

    private Rectangle continueButtonRect;
    Rectangle getcontinueButtonRect(){return continueButtonRect;}
    private Rectangle exitButtonRect;
    Rectangle getexitButtonRect(){return exitButtonRect;}
    private Rectangle restartButtonRect;
    Rectangle getrestartButtonRect(){return restartButtonRect;}


    protected Game_View(Game_Controller GameBoard) {
        Controller = GameBoard;
        strLen = 0;
        message = "Press Spacebar to Start!";
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(GameBoard);
        this.addMouseListener(GameBoard);
        this.addMouseMotionListener(GameBoard);

    }

    public void updatescreen(Game_Controller GameBoard){
        this.Controller = GameBoard;
        this.Controller.getGame().IncrementScore((int)(this.Controller.getGame().getBall().getSpeedX()));
        repaint();
    }
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(Controller.getGame().getBall(),g2d);
        for(int i= 0; i<Controller.getGame().getWall().getmultiballpoweruplevelcount(); i++){
            if(Controller.getGame().getWall().getMultiballpowerup(i).getshowmulti()) {
                drawPowerup(Controller.getGame().getWall().getMultiballpowerup(i), g2d);
            }
        }

        for(int i= 0; i<Controller.getGame().getWall().getextralifepoweruplevelcount(); i++){
            if(Controller.getGame().getWall().getExtraLifepowerup(i).getshowextralife()) {
                drawPowerup(Controller.getGame().getWall().getExtraLifepowerup(i), g2d);
            }
        }


        if (!Controller.getGame().getMiniBalls().isEmpty()){
            for(int i=0; i<Controller.getGame().getMiniBalls().size(); i++){
                drawMiniBall(Controller.getGame().getMiniBalls().get(i), g2d);
            }
        }
        for(Brick b : Controller.getGame().getWall().bricks)
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

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(RubberBall ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMiniBall(MiniBall ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPowerup(Powerup powerup,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = powerup.getmultiFace();

        g2d.setColor(powerup.getInnerColor());
        g2d.fill(s);

        g2d.setColor(powerup.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }


    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }



    private void drawHighScore(Graphics2D g2d) throws FileNotFoundException {
        obscureGameBoard(g2d);
        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        int[] highscores = Controller.gethighscorelist();

        int x = (this.getWidth() - strLen) / 3;
        int y = this.getHeight() / 10;

        g2d.drawString("High Scores:", x, y);
        x = (this.getWidth() - strLen) / 4;
        y+=100;
        for(int i=0; i< Controller.getGame().getScoreLength(); i++){
            g2d.drawString(String.valueOf(highscores[i]), x, y);
            y+=50;
        }

    }
    private void drawPauseMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
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

    void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

}

















