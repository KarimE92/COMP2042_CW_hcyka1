package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class MiniBall extends Ball{
    private final Color border;
    private final Color inner;

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

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }
}
