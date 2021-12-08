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
import java.util.ArrayList;


public class Levels {

    private static final int LEVELS_COUNT = 10;
    private int[] multiballpoweruplevelcount = new int[]{0, 1, 1, 2, 2, 3, 3, 4, 4, 5};
    private int[] extralifepoweruplevelcount = new int[]{0, 1, 1, 2, 2, 3, 3, 4, 4, 5};
    int getextralifepoweruplevelcount(){return extralifepoweruplevelcount[currentlevel-1];}
    ExtraLifePowerup getExtraLifepowerup(int i){return extralifepowerup.get(i);}
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private ArrayList<ExtraLifePowerup> extralifepowerup = new ArrayList<>();
    private ArrayList<MultiballPowerup> multiballpowerup  = new ArrayList<>();

    int getmultiballpoweruplevelcount(){return multiballpoweruplevelcount[currentlevel-1];}
    MultiballPowerup getMultiballpowerup(int i){return multiballpowerup.get(i);}
    Brick[] bricks;

    private Brick[][] levels;
    private int currentlevel;


    private int brickCount;



    protected Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize, type);
        }
        return tmp;

    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    private Brick[] makeStripesLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB, int typeC){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;


        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            if(line % 3 == 0){
                tmp[i] = makeBrick(p,brickSize,typeA);
            }else if(line % 3 == 1){
                tmp[i] = makeBrick(p,brickSize,typeB);
            }else{
                tmp[i] = makeBrick(p,brickSize,typeC);
            }
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            if(y%3 == 0) {
                tmp[i] = makeBrick(p, brickSize, typeA);
            }else if(y%3 == 1){
                tmp[i] = makeBrick(p, brickSize, typeB);
            }else{
                tmp[i] = makeBrick(p,brickSize, typeC);
            }
        }
        return tmp;
    }




    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] Level = new Brick[LEVELS_COUNT][];
        Level[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        Level[1] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        Level[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        Level[3] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT);
        Level[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,STEEL);
        Level[5] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL);
        Level[6] = makeStripesLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY, STEEL, CEMENT);
        Level[7] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        Level[8] = makeStripesLevel(drawArea,brickCount,lineCount,brickDimensionRatio, CEMENT, STEEL, STEEL);
        Level[9] = makeStripesLevel(drawArea,brickCount,lineCount,brickDimensionRatio, STEEL, CEMENT, CEMENT);
        return Level;
    }

    public void nextPowerup(){
        for(int i=0; i<multiballpoweruplevelcount[currentlevel-1]; i++){
            multiballpowerup.add(new MultiballPowerup());
        }
        for(int i=0; i<extralifepoweruplevelcount[currentlevel-1]; i++){
            extralifepowerup.add(new ExtraLifePowerup());
        }
    }

    public int getBrickCount(){
        return brickCount;
    }


    public int BrickCollision(){
        return brickCount--;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        bricks = levels[currentlevel++];
        brickCount = bricks.length;
        multiballpowerup.removeAll(multiballpowerup);
        extralifepowerup.removeAll(extralifepowerup);
        nextPowerup();
    }


    public void resetLevel(){
        wallReset();
        currentlevel = 0;
        bricks = levels[currentlevel];
        multiballpowerup.removeAll(multiballpowerup);
        extralifepowerup.removeAll(extralifepowerup);
        currentlevel += 1;
        nextPowerup();

    }

    public boolean hasLevel(){
        return currentlevel < levels.length;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
    }

    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}
