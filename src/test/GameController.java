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
import java.io.*;
import java.util.Scanner;

public class GameController extends JComponent implements KeyListener,MouseListener,MouseMotionListener {


    private Timer gameTimer;
    private final GameModel gameModel;
    GameModel getGame(){return gameModel;}

    private final test.GameView GameView;


    private boolean showPauseMenu;
    boolean getpausemenu(){return showPauseMenu;}

    private final DebugConsole debugConsole;

    int[] highscorelist;
    int[] gethighscorelist() throws FileNotFoundException {
        int[] data = new int[gameModel.getScoreLength()];
        try {
            File SaveFile = new File("SaveFile.txt");
            Scanner myReader = new Scanner(SaveFile);
            for (int i = 0; i < gameModel.getScoreLength(); i++) { //adding all the highscores to a list
                data[i] = myReader.nextInt();
            }
            myReader.close();
        }catch (IOException f) {
            System.out.println("An error occurred.");
            f.printStackTrace();
        }
        return data;
    }
    boolean GameEnd = false;
    public GameController(JFrame owner){
        super();
        showPauseMenu = false;
        GameView = new GameView(this);
        gameModel = new GameModel(new Rectangle(0,0,GameView.getwidth(),GameView.getheight()),30,4,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner, gameModel,this);
        //creating a savefile if it doesn't already exist
        try {
            File SaveFile = new File("SaveFile.txt");
            if (SaveFile.createNewFile()) {
                System.out.println("File created: " + SaveFile.getName());
                FileWriter myWriter = new FileWriter(SaveFile.getName());
                BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);
                for(int i=0; i<gameModel.getScoreLength(); i++) {
                    myBufferedWriter.write("0 ");
                    myBufferedWriter.newLine();
                }
                myBufferedWriter.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //initialize the first level
        gameModel.getLevels().nextLevel();

        gameTimer = new Timer(10,e ->{
            gameModel.move();
            gameModel.findImpacts();
            GameView.setmessage(String.format("Bricks: %d Balls %d Score: %d", gameModel.getLevels().getBrickCount(), gameModel.getBallCount(), gameModel.GetScore()));
            if(gameModel.isBallLost()){
                if(gameModel.ballEnd()){
                    //save the high score if it's above any currently existing high scores, and add their high score if there is none, and reset the score variable to zero
                    gameModel.getLevels().wallReset();
                    gameModel.resetBallCount();
                    GameView.setmessage("Game Over");
                    gameModel.ClearMiniBalls();

                    gameModel.getLevels().resetLevel();
                    gameModel.refreshWall();

                    try {
                        highscorelist = gethighscorelist();
                            int score = gameModel.GetScore();
                            int temp;
                            for (int j =0; j<gameModel.getScoreLength(); j++) { //checking if our current score is higher than the highscores in the file
                                if (score >= highscorelist[j]) {
                                    temp = highscorelist[j];
                                    highscorelist[j] = score;
                                    score = temp;
                                }
                            }
                                FileWriter myWriter = new FileWriter("SaveFile.txt");
                                BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);
                                for(int k=0; k<gameModel.getScoreLength(); k++) {
                                    myBufferedWriter.write(String.valueOf(highscorelist[k]));
                                    myBufferedWriter.newLine();
                                }
                                myBufferedWriter.close();


                    } catch (IOException f) {
                        System.out.println("An error occurred.");
                        f.printStackTrace();
                    }
                    gameModel.toggleHighscoremenu();
                    gameModel.ResetScore();
                }
                gameModel.ResetPosition();
                gameTimer.stop();
            }
            else if(gameModel.getLevels().isDone()){
                if(gameModel.getLevels().hasLevel()){
                    GameView.setmessage("Go to Next Level");
                    gameTimer.stop();
                    gameModel.ResetPosition();
                    gameModel.getLevels().wallReset();
                    gameModel.getLevels().nextLevel();
                    gameModel.ClearMiniBalls();
                }
                else{
                    GameView.setmessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                    try {
                        highscorelist = gethighscorelist();
                        int score = gameModel.GetScore();
                        int temp;
                        for (int j =0; j<gameModel.getScoreLength(); j++) { //checking if our current score is higher than the highscores in the file
                            if (score >= highscorelist[j]) {
                                temp = highscorelist[j];
                                highscorelist[j] = score;
                                score = temp;
                            }
                        }
                        FileWriter myWriter = new FileWriter("SaveFile.txt");
                        BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);
                        for(int k=0; k<gameModel.getScoreLength(); k++) {
                            myBufferedWriter.write(String.valueOf(highscorelist[k]));
                            myBufferedWriter.newLine();
                        }
                        myBufferedWriter.close();


                    } catch (IOException f) {
                        System.out.println("An error occurred.");
                        f.printStackTrace();
                    }
                    gameModel.toggleHighscoremenu();
                    GameEnd = true;
                }
            }
            GameView.updatescreen(this);
        });

    }


    test.GameView GetGameView(){return GameView;}

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

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
                GameView.updatescreen(this);
                gameTimer.stop();
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
                    GameView.updatescreen(this);
                    GameEnd = false;
                    gameModel.ResetScore();
                    GameView.setmessage(String.format("Bricks: %d Balls %d Score: %d", gameModel.getLevels().getBrickCount(), gameModel.getBallCount(), gameModel.GetScore()));
                }
                if(!showPauseMenu)
                    if(gameModel.gethighscoremenu()){
                        gameModel.toggleHighscoremenu();
                        GameView.updatescreen(this);
                    }else if(gameTimer.isRunning()) {
                        gameTimer.stop();

                    }else{
                        gameTimer.start();
                    }
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                gameModel.getPlayer().stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameModel.getPlayer().stop();
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
            gameModel.getLevels().resetLevel();
            gameModel.ResetPosition();
            gameModel.refreshWall();
            gameModel.getLevels().wallReset();
            showPauseMenu = false;
            gameModel.ClearMiniBalls();
            gameModel.resetBallCount();
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
