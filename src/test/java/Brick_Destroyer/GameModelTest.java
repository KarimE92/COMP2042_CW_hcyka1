package Brick_Destroyer;


import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {


    @Test
    void MiniBallTest(){
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getLevels().nextLevel();
        GameModel.getBall().moveTo((Point)GameModel.getLevels().getMultiballpowerup(0).getPosition());
        GameModel.findImpacts();
        assertEquals(3, GameModel.getMiniBalls().size());
    }
    @Test
    void ExtraLifeTest() {
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getLevels().nextLevel(); //need to move to a level where there is 1 powerup
        GameModel.getBall().moveTo((Point)GameModel.getLevels().getExtraLifepowerup(0).getPosition());
        GameModel.findImpacts();
        assertEquals(4, GameModel.getBallCount());
    }
    @Test
    void MiniBallCollectMultiBallPowerup(){
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getLevels().nextLevel(); //need to get to a level where there are 2 powerups
        GameModel.getLevels().nextLevel();
        GameModel.getLevels().nextLevel();
        GameModel.getBall().moveTo((Point)GameModel.getLevels().getMultiballpowerup(0).getPosition());
        GameModel.findImpacts();
        GameModel.getMiniBalls().get(0).moveTo((Point)GameModel.getLevels().getMultiballpowerup(1).getPosition());
        GameModel.findImpacts();
        assertEquals(6, GameModel.getMiniBalls().size());
    }

    @Test
    void MiniBallCollectExtraLifePowerup(){
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getLevels().nextLevel(); //need to get to a level where there is 1 powerup

        GameModel.getBall().moveTo((Point)GameModel.getLevels().getMultiballpowerup(0).getPosition());
        GameModel.findImpacts();
        GameModel.getMiniBalls().get(0).moveTo((Point)GameModel.getLevels().getExtraLifepowerup(0).getPosition());
        GameModel.findImpacts();
        assertEquals(4, GameModel.getBallCount());
    }
    @Test
    void BrickCollisionTest(){
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getBall().moveTo((new Point(50,50)));
        GameModel.move();
        GameModel.findImpacts();
        assertEquals(29, GameModel.getLevels().getBrickCount());
    }

    @Test
    void BallAccelerationTest(){
        GameModel GameModel = new GameModel(new GameFrame());
        GameModel.getBall().setXSpeed(3);
        GameModel.getBall().accelerate();
        assertEquals(3.0025, GameModel.getBall().getSpeedX(),0.00001);
    }
}