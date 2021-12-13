package Brick_Destroyer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    @BeforeEach
    void CreateModel(){

    }
        private GameModel gameModel = new GameModel(new GameFrame());

    @Test
    void MiniBallTest(){
        gameModel.getLevels().nextLevel();
        gameModel.getBall().moveTo((Point)gameModel.getLevels().getMultiballpowerup(0).getPosition());
        gameModel.findImpacts();
        assertEquals(3, gameModel.getMiniBalls().size());
    }
    @Test
    void ExtraLifeTest() {
        gameModel.getLevels().nextLevel(); //need to move to a level where there is 1 powerup
        gameModel.getBall().moveTo((Point)gameModel.getLevels().getExtraLifepowerup(0).getPosition());
        gameModel.findImpacts();
        assertEquals(4, gameModel.getBallCount());
    }
    @Test
    void MiniBallCollectMultiBallPowerup(){
        gameModel.getLevels().nextLevel(); //need to get to a level where there are 2 powerups
        gameModel.getLevels().nextLevel();
        gameModel.getLevels().nextLevel();
        gameModel.getBall().moveTo((Point)gameModel.getLevels().getMultiballpowerup(0).getPosition());
        gameModel.findImpacts();
        gameModel.getMiniBalls().get(0).moveTo((Point)gameModel.getLevels().getMultiballpowerup(1).getPosition());
        gameModel.findImpacts();
        assertEquals(6, gameModel.getMiniBalls().size());
    }

    @Test
    void MiniBallCollectExtraLifePowerup(){
        gameModel.getLevels().nextLevel(); //need to get to a level where there is 1 powerup

        gameModel.getBall().moveTo((Point)gameModel.getLevels().getMultiballpowerup(0).getPosition());
        gameModel.findImpacts();
        gameModel.getMiniBalls().get(0).moveTo((Point)gameModel.getLevels().getExtraLifepowerup(0).getPosition());
        gameModel.findImpacts();
        assertEquals(4, gameModel.getBallCount());
    }
    @Test
    void BrickCollisionTest(){
        gameModel.getBall().moveTo((new Point(50,50)));
        gameModel.move();
        gameModel.findImpacts();
        assertEquals(29, gameModel.getLevels().getBrickCount());
    }

    @Test
    void BallAccelerationTest(){
        gameModel.getBall().setXSpeed(3);
        gameModel.getBall().accelerate();
        assertEquals(3.0025, gameModel.getBall().getSpeedX(),0.00001);
    }
}