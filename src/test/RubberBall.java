package test;
import java.awt.*;
import java.awt.geom.Point2D;
/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class RubberBall extends Ball {

    private final Color border;
    private final Color inner;

    /**
     * RubberBall is the constructor method for the RubberBall class. It creates a ball and sets its color
     * @param center the coordinates for the center of the ball
     * @param radius the radius of the ball
     */
    public RubberBall(Point2D center, int radius){
        super(center, radius);

        Color inner = new Color(255, 219, 88); //Defining the colour of the ball
        this.border = inner.darker().darker();
        this.inner  = inner;
    }

    /**
     * getBorderColor gives us the color of the border of the ball to be drawn for the player
     * @return the color of the border of the ball
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * getInnerColor gives su the color of the ball to be drawn for the player
     * @return the color of the ball
     */
    public Color getInnerColor(){
        return inner;
    }

}
