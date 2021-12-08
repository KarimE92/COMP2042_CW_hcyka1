/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;


import java.awt.*;
import java.awt.geom.Point2D;

public class RubberBall extends Ball {

    private final Color border;
    private final Color inner;


    public RubberBall(Point2D center, int radius){

        super(center, radius);




        Color inner = new Color(255, 219, 88); //Defining the colour of the ball
        this.border = inner.darker().darker();
        this.inner  = inner;



    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }



}
