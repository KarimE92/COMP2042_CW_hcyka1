package test;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Multiball extends Powerup{
    private Shape multiFace;
    private int radius = 16;
    private Color border;
    private Color inner;
    private boolean ShowMultiBall = true;
    public boolean getshowmulti(){return ShowMultiBall;}
    public void setshowmulti(){ShowMultiBall = !(ShowMultiBall);}

    public Multiball(){
        super();
        multiFace = makePowerup(getPosition());
        Color inner = new Color(0, 53, 255); //Defining the colour of the ball
        Color border = inner.darker().darker(); //Defining the border of the ball
        this.border = border;
        this.inner  = inner;
    }

    public Shape makePowerup(Point2D center){
        double x = center.getX() - (radius / 2);
        double y = center.getY() - (radius / 2);

        return new Ellipse2D.Double(x,y,radius,radius);
    }
    @Override
    public Shape getmultiFace(){
        return multiFace;
    }
    @Override
    public Color getBorderColor(){
        return border;
    }
    @Override
    public Color getInnerColor(){
        return inner;
    }

    public boolean impact(Ball b){
        if (multiFace.contains(b.getPosition()) && (getshowmulti())){
            setshowmulti();
            return true;
        }
            return false;
    }

    @Override
    public void resetFace(Point2D point){
        multiFace = makePowerup(point);
    }


}

