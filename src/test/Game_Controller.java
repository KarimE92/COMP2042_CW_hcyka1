/*
 *  Brick Destroy - A simple Arcade video gameModel
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Game_Controller extends JComponent implements KeyListener,MouseListener,MouseMotionListener {


    private Timer gameTimer;
    private Game_Model gameModel;
    Game_Model getGame(){return gameModel;}

    private Game_View GameView;


    private boolean showPauseMenu;
    boolean getpausemenu(){return showPauseMenu;}

    private DebugConsole debugConsole;

    public Game_Controller(JFrame owner){
        super();
        showPauseMenu = false;
        GameView = new Game_View(this);
        gameModel = new Game_Model(new Rectangle(0,0,GameView.getwidth(),GameView.getheight()),30,4,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner, gameModel,this);

        //initialize the first level
        gameModel.wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            gameModel.move();
            gameModel.findImpacts();
            GameView.setmessage(String.format("Bricks: %d Balls %d Score: %d", gameModel.wall.getBrickCount(), gameModel.getBallCount(), gameModel.GetScore()));
            if(gameModel.isBallLost()){
                if(gameModel.ballEnd()){
                    //save the high score if it's above any currently existing high scores, and add their high score if there is none, and reset the score variable to zero
                    gameModel.wall.wallReset();
                    gameModel.resetBallCount();
                    GameView.setmessage("Game Over");
                    try {
                        File SaveFile = new File("SaveFile.txt");
                        System.out.println("File Opened");
                        Scanner myReader = new Scanner(SaveFile);
                        int[] data = {0,0,0};
                            System.out.println("Reading Line:");
                            for (int i = 0; i<3; i++) { //adding all the highscores to a list
                                data[i] = myReader.nextInt();
                            }
                            myReader.close();
                            int score = gameModel.GetScore();
                            int temp;
                            for (int j =0; j<3; j++) { //checking if our current score is higher than the highscores in the file
                                if (score >= data[j]) {
                                    temp = data[j];
                                    data[j] = score;
                                    score = temp;
                                }
                            }
                                FileWriter myWriter = new FileWriter(SaveFile.getName());
                                BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);
                                for(int k=0; k<3; k++) {
                                    myBufferedWriter.write(String.valueOf(data[k]));
                                    myBufferedWriter.newLine();
                                }
                                myBufferedWriter.close();
                                System.out.println("Successfully wrote to the file.");



                    } catch (IOException f) {
                        System.out.println("An error occurred.");
                        f.printStackTrace();
                    }
                    gameModel.ResetScore(); //just resetting the score. Ideally we would parse a text file and check if the current score exceeds any scores on the text file and replace them
                }
                gameModel.LevelReset();
                gameTimer.stop();
            }
            else if(gameModel.wall.isDone()){
                if(gameModel.wall.hasLevel()){
                    GameView.setmessage("Go to Next Level");
                    gameTimer.stop();
                    gameModel.LevelReset();
                    gameModel.wall.wallReset();
                    gameModel.wall.nextLevel();
                }
                else{
                    GameView.setmessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                }
            }
            GameView.updatescreen(this);
        });

    }


    Game_View GetGameView(){return GameView;}

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                if (keyEvent.isShiftDown()){
                    gameModel.player.sprintLeft();
                }else {
                    gameModel.player.moveLeft();
                }
                break;
            case KeyEvent.VK_D:
                if (keyEvent.isShiftDown()){
                    gameModel.player.sprintRight();
                }else {
                    gameModel.player.moveRight();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                GameView.updatescreen(this);
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning()) {
                        gameTimer.stop();

                    }else{
                        gameTimer.start();
                    }
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                gameModel.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameModel.player.stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(GameView.getcontinueButtonRect().contains(p)){
            showPauseMenu = false;
            GameView.updatescreen(this);
        }
        else if(GameView.getrestartButtonRect().contains(p)){
            GameView.setmessage("Restarting Game...");
            gameModel.ResetScore();
            gameModel.LevelReset();
            gameModel.wall.wallReset();
            showPauseMenu = false;
            GameView.updatescreen(this);
        }
        else if(GameView.getexitButtonRect().contains(p)){
            //Save the game (including the high score before exiting the game
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent){
        Point p = mouseEvent.getPoint();
        if(GameView.getexitButtonRect() != null && showPauseMenu) {
            if (GameView.getexitButtonRect().contains(p) || GameView.getcontinueButtonRect().contains(p) || GameView.getrestartButtonRect().contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void onLostFocus(){
        gameTimer.stop();
        GameView.setmessage("Focus Lost");
        GameView.updatescreen(this);
    }

}
