package test;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Multiball extends Powerup{
    private Shape multiFace;
    private int radius = 16;
    private Color border;
    private Color inner;

    public Multiball(){
        super();
        multiFace = makePowerup(getPosition());
        Color inner = new Color(255, 0, 0); //Defining the colour of the ball
        Color border = inner.darker().darker(); //Defining the border of the ball
        this.border = border;
        this.inner  = inner;
    }

    public Shape makePowerup(Point2D center){
        double x = center.getX() - (radius / 2);
        double y = center.getY() - (radius / 2);

        return new Ellipse2D.Double(x,y,radius,radius);
    }

    public Shape getmultiFace(){
        return multiFace;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }
}
