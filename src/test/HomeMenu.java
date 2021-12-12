
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 1.0";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";

    private static final Color BG_COLOR = Color.RED.darker();
    private static final Color TEXT_COLOR = new Color(255, 0, 0);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;


    Toolkit t=Toolkit.getDefaultToolkit();
    private final Image Title_Image = t.getImage("src/test/Title_Image.jpg");

    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle exitButton;
    private final Rectangle infoButton;



    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;

    private final GameFrame owner;

    private boolean startClicked;
    private boolean exitClicked;
    private boolean infoClicked;

    /**
     * HomeMenu is the constructor method for the HomeMenu class. It loads the home menu, and creates 3 buttons that the player can click on
     * @param owner the game's window
     * @param area the area of the window
     */
    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;



        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

    }

    /**
     * paint calls drawMenu to draw all the homemenu's visual aspects
     * @param g the graphics to paint the game with
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * drawMenu draws the HomeMenu by setting an image as the background, then calling drawText to draw the text and drawButton to draw the buttons
     * @param g2d the graphics to paint the game with
     */
    public void drawMenu(Graphics2D g2d){

        g2d.drawImage(Title_Image, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);

        drawText(g2d);
        drawButton(g2d);
    }

    /**
     * drawText draws all the text for the HomeMenu on screen
     * @param g2d the graphics to paint the game with
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * drawButton draws all the buttons for the HomeMenu on screen and also draws the text for those buttons
     * @param g2d the graphics to paint the game with
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D eTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT, frc);
        g2d.setFont(buttonFont);
        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.65);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);




        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.fill(startButton);
            g2d.setColor(Color.RED);
            g2d.draw(startButton);

            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        exitButton.setLocation(x,y);




        x = (int)(exitButton.getWidth() - eTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - eTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        if(exitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.fill(exitButton);
            g2d.setColor(Color.RED);
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }
//adding the info button
        x = startButton.x;
        y = startButton.y;

        y *= 1.4;

        infoButton.setLocation(x,y);


        x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - iTxtRect.getHeight() / 2);



        x += infoButton.x;
        y += infoButton.y + (exitButton.height * 0.5);

        if(infoClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.fill(infoButton);
            g2d.setColor(Color.RED);
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }
    }

    /**
     * mouseClicked is run when the mouse is clicked on screen, with something different happening depending on which button was clicked
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(infoButton.contains(p)){
            owner.enableInfoMenu();
        }
    }

    /**
     * mousePressed is run whenever the mouse is pressed. If pressed on a button, it repaints that button to look different
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    /**
     * mouseReleased is called whenever the mouse is released. If it's released from a button while it's being pressed, the button will be repainted
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(exitClicked){
            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if(infoClicked){
            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    /**
     * mosueEntered is an override since we don't want anything to happen when the mouse is entered
     * @param mouseEvent variable representing what the mouse did
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    /**
     * mosueExited is an override since we don't want anything to happen when the mouse is exited
     * @param mouseEvent variable representing what the mouse did
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * mouseDragged is an override since we don't want anything to happen when the mouse is dragged
     * @param mouseEvent variable representing what the mouse did
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
    /**
     * mosueMoved is an override since we don't want anything to happen when the mouse is moved
     * @param mouseEvent variable representing what the mouse did
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
