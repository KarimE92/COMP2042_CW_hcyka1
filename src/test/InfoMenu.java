package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JTextArea;

public class InfoMenu extends JComponent implements MouseListener, MouseMotionListener{


    private static final String BACK_TEXT = "Back";
    private static final String INSTRUCTIONS = "How To Play:";
    private static final String INSTRUCTIONS1 = "Use A and D to move around. Hold shift to sprint";
    private static final String INSTRUCTIONS2 = "Destroy all bricks to move on to the next level";
    private static final String INSTRUCTIONS3 = "Grab powerups along the way to help you";
    private Font TitleFont;
    private Font instructionsFont;


    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color TEXT_COLOR = new Color(255, 0, 0);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    Toolkit t=Toolkit.getDefaultToolkit();
    private Image Title_Image = t.getImage("src/test/Title_Image.jpg");

    private Rectangle menuFace;
    private Rectangle backButton;



    private Font buttonFont;

    private GameFrame owner;

    private boolean backClicked;


    public InfoMenu(GameFrame owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        backButton = new Rectangle(btnDim);

        buttonFont = new Font("Monospaced",Font.PLAIN,backButton.height-2);
        TitleFont = new Font("Noto Mono",Font.PLAIN,40);
        instructionsFont = new Font("Monospaced", Font.PLAIN, 20);


    }

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    public void drawMenu(Graphics2D g2d){
        g2d.drawImage(Title_Image, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);

        drawText(g2d);
        drawButton(g2d);

    }



    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = instructionsFont.getStringBounds(INSTRUCTIONS,frc);


        int sX,sY;

        sX = (int)(menuFace.getWidth())/ 4;
        sY = (int)(menuFace.getHeight() / 4);
        g2d.setFont(TitleFont);
        g2d.drawString(INSTRUCTIONS,sX,sY);

        int tY;

        tY = (int)(sY + 50);
        g2d.setFont(instructionsFont);
        g2d.drawString(INSTRUCTIONS1,5,tY);

        int uY;
        uY = tY + 25;
        g2d.drawString(INSTRUCTIONS2, 5, uY);

        int vY;
        vY = uY + 25;
        g2d.drawString(INSTRUCTIONS3, 5, vY);






    }

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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            owner.enableHomeMenu();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            backClicked = true;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(backClicked ){
            backClicked = false;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
