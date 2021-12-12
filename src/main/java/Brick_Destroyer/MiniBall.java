package Brick_Destroyer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class MiniBall extends Ball{
    private final Color border;
    private final Color inner;

    /**
     * MiniBall is the constructor method for the MiniBall class. It creates a ball, assigns it its color, and sets the ball's speed to a random number
     * @param center the coordinates where the ball should be made
     * @param radius the radius of the ball
     */
    public MiniBall(Point2D center, int radius) {
        super(center, radius);

        Color inner = new Color(0, 53, 255); //Defining the colour of the ball
        this.border = inner.darker().darker();
        this.inner  = inner;

        Random rnd = new Random();
        do{
            setXSpeed(rnd.nextInt(5) - 2);
        }while(getSpeedX() == 0);
        do{
            setYSpeed(-rnd.nextInt(3));
        }while(getSpeedY() == 0);

    }

    /**
     * getBorderColor gives us the color of the border of the ball to be displayed to the player
     * @return the color of the border of the ball
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * getInnerColor gives us the color of the ball to be dispalyed to the player
     * @return the color of the ball
     */
    public Color getInnerColor(){
        return inner;
    }
}
