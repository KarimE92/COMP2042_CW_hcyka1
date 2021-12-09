
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class GameController extends JComponent implements KeyListener,MouseListener,MouseMotionListener {



    boolean GameEnd = false;
    private boolean showPauseMenu;
    private GameModel gameModel;
    private GameView gameView;



    /**
     * GameController is the constructor method for the GameController class. It creates the GameView and GameModel, checks if a highscore savefile exists and creates one if it doesn't, initializes the first level, controls the gameTimer, and keeps track of if the game ends, saving the player's score if it's a new highscore, displaying the highscore, and restarting the game
     *
     */
    public GameController(GameModel GameModel){
        gameModel = GameModel;
        showPauseMenu = false;

    }

    public void SetGameView(GameView GameView){gameView = GameView;}


    /**
     * keyTypes overrides the parent method since we don't want anything to happen if they player types something
     * @param keyEvent variable representing a keyboard input
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * keyPressed checks which key the user pressed, and either moves the player, pauses the game, unpauses the game, or opens the debug menu depending on the player's input
     * @param keyEvent variable representing a keyboard input
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                if (keyEvent.isShiftDown()){
                    gameModel.getPlayer().sprintLeft();
                }else {
                    gameModel.getPlayer().moveLeft();
                }
                break;
            case KeyEvent.VK_D:
                if (keyEvent.isShiftDown()){
                    gameModel.getPlayer().sprintRight();
                }else {
                    gameModel.getPlayer().moveRight();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                gameView.updatescreen(this);
                gameModel.getGameTimer().stop();
                break;
            case KeyEvent.VK_SPACE:
                if (GameEnd){
                    gameModel.getLevels().resetLevel();
                    gameModel.ResetPosition();
                    gameModel.refreshWall();
                    gameModel.getLevels().wallReset();
                    showPauseMenu = false;
                    gameModel.ClearMiniBalls();
                    gameModel.resetBallCount();
                    gameView.updatescreen(this);
                    GameEnd = false;
                    gameModel.ResetScore();
                    gameView.setmessage(String.format("Bricks: %d Balls %d Score: %d", gameModel.getLevels().getBrickCount(), gameModel.getBallCount(), gameModel.GetScore()));
                }
                if(!showPauseMenu)
                    if(gameModel.gethighscoremenu()){
                        gameModel.toggleHighscoremenu();
                        gameView.updatescreen(this);
                    }else if(gameModel.getGameTimer().isRunning()) {
                        gameModel.getGameTimer().stop();

                    }else{
                        gameModel.getGameTimer().start();
                    }
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    gameModel.getdebugConsole().setVisible(true);
            default:
                gameModel.getPlayer().stop();
        }
    }

    /**
     * keyReleased stops the player's movement when they are no longer pressing a key
     * @param keyEvent variable representing a keyboard input
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameModel.getPlayer().stop();
    }

    /**
     * mouseClicked does nothing if you're not on the pause menu, resumes the game if you click resume, restarts the game if you click restart, and exits the game if you click exit
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(gameView.getcontinueButtonRect().contains(p)){
            showPauseMenu = false;
            gameView.updatescreen(this);
        }
        else if(gameView.getrestartButtonRect().contains(p)){
            gameView.setmessage("Restarting Game...");
            gameModel.ResetScore();
            gameModel.getLevels().resetLevel();
            gameModel.ResetPosition();
            gameModel.refreshWall();
            gameModel.getLevels().wallReset();
            showPauseMenu = false;
            gameModel.ClearMiniBalls();
            gameModel.resetBallCount();
            gameView.updatescreen(this);
        }
        else if(gameView.getexitButtonRect().contains(p)){
            //Save the game (including the high score before exiting the game
            System.exit(0);
        }

    }

    /**
     * mousePressed is an override of the parent's method since we don't want anything to happen when we press the mouse
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    /**
     * mouseReleased is an override of the parent's method since we don't want anything to happen when we release the mouse
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * mouseEntered is an override of the parent's method since we don't want anything to happen when we enter the mouse
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    /**
     * mouseExited is an override of the parent's method since we don't want anything to happen when we exit the mouse
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    /**
     * mouseDragged is an override of the parent's method since we don't want anything to happen when we drag the mouse
     * @param mouseEvent variable representing a mouse input
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * mouseMoved
     * @param mouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent){
        Point p = mouseEvent.getPoint();
        if(gameView.getexitButtonRect() != null && showPauseMenu) {
            if (gameView.getexitButtonRect().contains(p) || gameView.getcontinueButtonRect().contains(p) || gameView.getrestartButtonRect().contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * onLostFocus stops the game if the player clicks off of it
     */
    public void onLostFocus(){
        gameModel.getGameTimer().stop();
        gameView.setmessage("Focus Lost");
        gameView.updatescreen(this);
    }

    /**
     * getGame gets us the gameModel, the game main's logic
     * @return gameModel
     */
    GameModel getGame(){return gameModel;}

    /**
     * getpausemenu gets us showPauseMenu, so we know whether or not to display the pause menu
     * @return showPauseMenu
     */
    boolean getpausemenu(){return showPauseMenu;}




}
