
package test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Karim Elbishouty on 9/12/2021
 * @author Karim
 * @since 2021-12-09
 *
 */
public class Levels {

    private static final int LEVELS_COUNT = 10;
    private final int[] multiballpoweruplevelcount = new int[]{0, 1, 1, 2, 2, 3, 3, 4, 4, 5};
    private final int[] extralifepoweruplevelcount = new int[]{0, 1, 1, 2, 2, 3, 3, 4, 4, 5};
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private final ArrayList<ExtraLifePowerup> extralifepowerup = new ArrayList<>();
    private final ArrayList<MultiballPowerup> multiballpowerup  = new ArrayList<>();
    Brick[] bricks;
    private final Brick[][] levels;
    private int currentlevel;
    private int brickCount;

    /**
     * Levels is the constructor method for the Levels class. It calls the makeLevels method
     * @param drawArea the area the levels must encompass
     * @param brickCount the number of bricks for the level
     * @param lineCount the number of rows of bricks for the level
     * @param brickDimensionRatio the size of the bricks
     */
    protected Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
    }

    /**
     * makeLevels creates the levels for the game by creating an array which represents a wall of bricks that should be loaded and drawn
     * @param drawArea the area the levels must encompass
     * @param brickCount the number of bricks for the level
     * @param lineCount the number of rows of bricks for the level
     * @param brickDimensionRatio the size of the bricks
     * @return the 2D array of bricks so that levels can be generated from it
     */
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

    /**
     * makeSingleTypeLevel makes a level using only 1 type of brick
     * @param drawArea the area the levels must encompass
     * @param brickCnt the number of bricks for the level
     * @param lineCnt the number of rows of bricks for the level
     * @param brickSizeRatio the size of the bricks
     * @param type the type of brick the level should be made from
     * @return an array of bricks that represents the level
     */
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

    /**
     * makeChessboardLevel creates a level using 2 types of bricks, where the bricks alternate in type
     * @param drawArea the area the levels must encompass
     * @param brickCnt the number of bricks for the level
     * @param lineCnt the number of rows of bricks for the level
     * @param brickSizeRatio the size of the bricks
     * @param typeA the first type of brick the level should be made from
     * @param typeB the second type of brick the level should be made from
     * @return an array of bricks that represents the level
     */
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

    /**
     * makeStripesLevel creates a level using 3 types of bricks, where each row of bricks is its own type
     * @param drawArea the area the levels must encompass
     * @param brickCnt the number of bricks for the level
     * @param lineCnt the number of rows of bricks for the level
     * @param brickSizeRatio the size of the bricks
     * @param typeA the first type of brick the level should be made from
     * @param typeB the second type of brick the level should be made from
     * @param typeC the third type of brick the level should be made from
     * @return an array of bricks that represents the level
     */
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


    /**
     * nextLevelPowerup generates the powerups to be used for the next level
     */
    public void nextLevelPowerup(){
        for(int i=0; i<multiballpoweruplevelcount[currentlevel-1]; i++){
            multiballpowerup.add(new MultiballPowerup());
        }
        for(int i=0; i<extralifepoweruplevelcount[currentlevel-1]; i++){
            extralifepowerup.add(new ExtraLifePowerup());
        }
    }

    /**
     * getBrickCount gets us the number of unbroken bricks in the level to display to the player
     * @return the number of unbroken bricks in the current level
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * BrickCollision is ran whenever a brick breaks. It decrements brickCount to keep brickCount up to date
     */
    public void BrickCollision(){
        brickCount--;
    }

    /**
     * isDone tells us if the level has ended as a result of all the bricks being destroyed
     * @return true if brickCount is 0 (all the bricks have been destroyed)
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * nextLevel loads the next level by loading the next level's bricks into the brick variable, updating brickCount, and spawning in the powerups for the level
     */
    public void nextLevel(){
        bricks = levels[currentlevel++];
        brickCount = bricks.length;
        multiballpowerup.removeAll(multiballpowerup);
        extralifepowerup.removeAll(extralifepowerup);
        nextLevelPowerup();
    }

    /**
     * resetLevel resets the level by resetting the brick wall, going back to the first level, and spawning the first level's powerups
     */
    public void resetLevel(){
        wallReset();
        currentlevel = 0;
        bricks = levels[currentlevel];
        multiballpowerup.removeAll(multiballpowerup);
        extralifepowerup.removeAll(extralifepowerup);
        currentlevel += 1;
        nextLevelPowerup();

    }

    /**
     * hasLevel tells us if there is a next level
     * @return true if the currentlevel is less than the total number of levels (if we aren't on the final level)
     */
    public boolean hasLevel(){
        return currentlevel < levels.length;
    }

    /**
     * wallReset loops through all the bricks in the current level and repairs them one by one, and then resets brickCount
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
    }

    /**
     * makeBrick creates a brick of the coresponding type
     * @param point the coordinates where the brick should be created
     * @param size the size of the brick
     * @param type the type of brick to be created
     * @return the brick after it's created
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }

    /**
     * getextralifepoweruplevelcount gives us the number of extralifepowerups we need to spawn for the current level
     * @return the number of extralifepowerups to create for the current level
     */
    int getextralifepoweruplevelcount(){return extralifepoweruplevelcount[currentlevel-1];}

    /**
     * getExtraLifepowerup gets us the extralifepowerup we need from an array of extralifepowerups
     * @param i the position of the extralifepowerup we want in the array
     * @return the extralifepowerup for us to handle collisions with
     */
    ExtraLifePowerup getExtraLifepowerup(int i){return extralifepowerup.get(i);}

    /**
     * getmultiballpoweruplevelcount gives us the number of multiballpowerups we need to spawn for the current level
     * @return the number of multiballpowerups to create for the current level
     */
    int getmultiballpoweruplevelcount(){return multiballpoweruplevelcount[currentlevel-1];}

    /**
     * getMultiBallpowerup gets us the multiballpowerup we need from an array of multiballpowerups
     * @param i the position of the mulitballpowerup we want in the array
     * @return the multiballpowerup for us to handle collisions with
     */
    MultiballPowerup getMultiballpowerup(int i){return multiballpowerup.get(i);}
}
