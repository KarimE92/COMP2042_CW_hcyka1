package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";
    private final InfoMenu InfoMenu;
    public GameModel gameBoard;
    private final HomeMenu homeMenu;
    private boolean gaming;

    /**
     * GameFrame is the constructor method for the GameFrame class. It creates a window, makes it non-resizable, initializes gameBoard, homeMenu and InfoMenu and displays homeMenu
     */
    public GameFrame(){
        super();
        setResizable(false); //the game is not resizable
        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameModel(this);

        homeMenu = new HomeMenu(this,new Dimension(600,450));

        InfoMenu = new InfoMenu(this, new Dimension(600, 450));
        this.add(homeMenu,BorderLayout.CENTER);

        this.setUndecorated(false);


    }

    /**
     * initialize sets up the window's title, defines what happens when the window is closed, and makes the window visible
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * enableGameBoard removes the homeMenu and displays the gameBoard to start playing the game
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard.getGameView(),BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * enableInfoMenu removes the homeMenu and displays the infoMenu
     */
    public void enableInfoMenu(){
        this.dispose();
        this.remove(homeMenu);
        this.add(InfoMenu,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }

    /**
     * enableHomeMenu removes the InfoMenu and displays the homemenu
     */
    public void enableHomeMenu(){
        this.dispose();
        this.remove(InfoMenu);
        this.add(homeMenu,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }

    /**
     * autoLocate automatically locates the game's window
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    /**
     * windowGainedFocus makes the game run again when you focus back on the window
     * @param windowEvent variable representing the event that has occurred to the window
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        gaming = true;
    }

    /**
     * windowLostFocus makes the game stop running when you foucs away from the window by calling a method in gameBoard
     * @param windowEvent variable representing the event that has occurred to the window
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.getGameController().onLostFocus();

    }
}
