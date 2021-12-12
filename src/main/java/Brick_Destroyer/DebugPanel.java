
package Brick_Destroyer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */


public class DebugPanel extends JPanel {

    private static final int slidermin = -4;
    private static final int slidermax = 4;
    private static final Color DEF_BKG = Color.WHITE;


    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    /**
     * DebugPanel is the constructor method for the DebugPanel class. It initializes itself, makes the skiplevel and resetballs buttons and makes the ballxspeed and ballyspeed sliders
     * @param gameModel the game's main logic, passed in so we can get the ball's features faster
     */
    public DebugPanel(GameModel gameModel){

        initialize();

        JButton skipLevel = makeButton("Skip Level", e -> gameModel.getLevels().nextLevel());
        JButton resetBalls = makeButton("Reset Balls", e -> gameModel.resetBallCount());

        ballXSpeed = makeSlider(e -> gameModel.getBall().setXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(e -> gameModel.getBall().setYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * initialize sets up the DebugPanel's background and layout
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * makeButton makes a clickable button
     * @param title the name of the button
     * @param e the method to be called when the button is pressed
     * @return the created button
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * makeSlider creates a slider
     * @param e the method to be called when the slider is used
     * @return the created slider
     */
    private JSlider makeSlider(ChangeListener e){
        JSlider out = new JSlider(slidermin, slidermax);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * setValues sets the values of the ball's X and Y speed on the sliders
     * @param x the value of XSpeed to be set
     * @param y the value of YSpeed to be set
     */
    public void setValues(float x,float y){
        ballXSpeed.setValue((int) x);
        ballYSpeed.setValue((int) y);
    }

}
