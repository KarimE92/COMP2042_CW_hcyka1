package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.util.Random;

public class MiniBall extends Ball{
    private Color border;
    private Color inner;
    private Random rnd;
    public MiniBall(Point2D center, int radius) {
        super(center, radius);


        Color inner = new Color(255, 0, 0); //Defining the colour of the ball
        Color border = inner.darker().darker(); //Defining the border of the ball
        this.border = border;
        this.inner  = inner;


        rnd = new Random();
        do{
            setXSpeed(rnd.nextInt(5) - 2);
        }while(getSpeedX() == 0);
        do{
            setYSpeed(-rnd.nextInt(3));
        }while(getSpeedY() == 0);
        System.out.println("X Speed: " +getSpeedX());
        System.out.println("Y Speed: " +getSpeedY());
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }
}
