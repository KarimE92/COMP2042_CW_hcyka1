
package Brick_Destroyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";


    private final JFrame owner;
    private final DebugPanel debugPanel;
    private final GameController gameControls;
    private final GameModel gameModel;

    /**
     * DebugConsole is the constructor method of the DebugConsole Class. It saves the gameModel, owner and gameBoard, initializes itself, creates and adds the debugpanel, and then resizes itself to fit already existing window preferences
     * @param owner the game's window
     * @param gameModel the game's central logic
     * @param gameControls the game's main controls
     */
    public DebugConsole(JFrame owner, GameModel gameModel, GameController gameControls){

        this.gameModel = gameModel;
        this.owner = owner;
        this.gameControls = gameControls;
        initialize();

        debugPanel = new DebugPanel(gameModel);
        this.add(debugPanel,BorderLayout.CENTER);


        this.pack();
    }

    /**
     * initialize makes it so that the Debug Console is the main focus rather than the Game itself
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * setLocation sets the location of the DebugConsole up
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    /**
     * windowOpened is an Override to the parent class, and does nothing since we want nothing to happen when the window is opened
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * windowClosing repaints the gameControls, refreshing the game when the window is closed
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameControls.repaint();
    }

    /**
     * windowClosed is an override to the parent class, and does nothing since we want nothing to happen when the window is closed
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }
    /**
     * windowIconified is an override to the parent class, and does nothing since we want nothing to happen when the window is iconified
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }
    /**
     * windowDeiconified is an override to the parent class, and does nothing since we want nothing to happen when the window is deiconified
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * windowActivated gets the ball from the game's model and passes the ball's speed into debugpanel when the window is activated
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = gameModel.getBall();
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }
    /**
     * windowDeactivated is an override to the parent class, and does nothing since we want nothing to happen when the window is deactivated
     * @param windowEvent the event occurring to the window
     */
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
