
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
    private final int brickCount = 30;
    private final int lineCount = 4;
    private final float brickDimensionRatio = 6/2;
    private final Point StartingPos = new Point(300,430);


    private Timer gameTimer;
    private final GameModel gameModel;
    private final test.GameView GameView;
    private boolean showPauseMenu;
    private final DebugConsole debugConsole;
    int[] highscorelist;
    boolean GameEnd = false;

    /**
     * GameController is the constructor method for the GameController class. It creates the GameView and GameModel, checks if a highscore savefile exists and creates one if it doesn't, initializes the first level, controls the gameTimer, and keeps track of if the game ends, saving the player's score if it's a new highscore, displaying the highscore, and restarting the game
     * @param owner the window of the game
     */
    public GameController(JFrame owner){
        super();
        showPauseMenu = false;
        GameView = new GameView(this);
        gameModel = new GameModel(new Rectangle(0,0,GameView.getwidth(),GameView.getheight()),brickCount,lineCount,brickDimensionRatio,StartingPos);

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

    /**
     * GameView gets us the GameView which is the visual component of our game
     * @return GameView
     */
    test.GameView GetGameView(){return GameView;}

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

    /**
     * onLostFocus stops the game if the player clicks off of it
     */
    public void onLostFocus(){
        gameTimer.stop();
        GameView.setmessage("Focus Lost");
        GameView.updatescreen(this);
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

    /**
     * gethighscorelist opens a savefile which stores all the highscores and copies them into an array that it then returns
     * @return the array of highscores
     * @throws FileNotFoundException in case the savefile does not exist
     */
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


}
