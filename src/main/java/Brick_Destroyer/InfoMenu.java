package Brick_Destroyer;

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
public class InfoMenu extends JComponent implements MouseListener, MouseMotionListener{


    private static final String BACK_TEXT = "Back";



    private static final Color BG_COLOR = Color.BLACK.darker();

    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;

    private final Rectangle menuFace;
    private final Rectangle backButton;

    Toolkit t=Toolkit.getDefaultToolkit();
    private final Image Info_Image = t.getImage("src/main/resources/InfoScreen.png");

    private final Font buttonFont;

    private final GameFrame owner;

    private boolean backClicked;

    /**
     * InfoMenu is the constructor method for the InfoMenu class. It loads the InfoMenu, creates a menuFace to draw, and creates a backButton to go back to the HomeMenu
     * @param owner the game's window
     * @param area the area of the window
     */
    public InfoMenu(GameFrame owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 6, area.height / 12);
        backButton = new Rectangle(btnDim);

        buttonFont = new Font("Monospaced",Font.PLAIN,backButton.height-2);



    }
    /**
     * paint calls drawMenu to draw all the homemenu's visual aspects
     * @param g the graphics to paint the game with
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * drawMenu draws the menu by setting the background color and filling the menuface with the background color, then drawing the infomenu's text and button
     * @param g2d the graphics to paint the game with
     */
    public void drawMenu(Graphics2D g2d){
        g2d.drawImage(Info_Image, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);

        drawButton(g2d);

    }



    /**
     * drawButton draws the back button as well as the text for the back button
     * @param g2d the graphics to paint the game with
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT,frc);


        g2d.setFont(buttonFont);

        int x = 10;
        int y =(int) ((menuFace.height - backButton.height) * 0.95);

        backButton.setLocation(x,y);

        x = (int)(backButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(backButton.getHeight() - txtRect.getHeight()) / 2;

        x += backButton.x;
        y += backButton.y + (backButton.height * 0.9);


        if(backClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(backButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(BACK_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(backButton);
            g2d.drawString(BACK_TEXT,x,y);
        }
    }

    /**
     * mouseClicked is run when the mouse is clicked on screen, if the back button is clicked, the player should be returned to the HomeMenu
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            owner.enableHomeMenu();
        }
    }
    /**
     * mousePressed is run whenever the mouse is pressed. If pressed on the back button, it repaints the button to look different
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            backClicked = true;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }
    /**
     * mouseReleased is called whenever the mouse is released. If it's released from the back button while it's being pressed, the button will be repainted
     * @param mouseEvent variable representing what the computer mouse did
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(backClicked ){
            backClicked = false;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    /**
     * mosueEntered is an override since we don't want anything to happen when the mouse is entered
     * @param e variable representing what the mouse did
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * mosueExited is an override since we don't want anything to happen when the mouse is exited
     * @param e variable representing what the mouse did
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * mouseDragged is an override since we don't want anything to happen when the mouse is dragged
     * @param e variable representing what the mouse did
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * mosueMoved is an override since we don't want anything to happen when the mouse is moved
     * @param mouseEvent variable representing what the mouse did
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
