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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {


    private Timer gameTimer;
    private Game game;
    Game getGame(){return game;}

    private GameBoard_View GameView;


    private boolean showPauseMenu;
    boolean getpausemenu(){return showPauseMenu;}

    private DebugConsole debugConsole;

    public GameBoard(JFrame owner){
        super();
        showPauseMenu = false;
        GameView = new GameBoard_View(this);
        game = new Game(new Rectangle(0,0,GameView.getwidth(),GameView.getheight()),30,4,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner,game,this);

        //initialize the first level
        game.wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            game.move();
            game.findImpacts();
            GameView.setmessage(String.format("Bricks: %d Balls %d",game.wall.getBrickCount(),game.getBallCount()));
            if(game.isBallLost()){
                if(game.ballEnd()){
                    game.wall.wallReset();
                    game.resetBallCount();
                    GameView.setmessage("Game over");
                }
                game.LevelReset();
                gameTimer.stop();
            }
            else if(game.wall.isDone()){
                if(game.wall.hasLevel()){
                    GameView.setmessage("Go to Next Level");
                    gameTimer.stop();
                    game.LevelReset();
                    game.wall.wallReset();
                    game.wall.nextLevel();
                }
                else{
                    GameView.setmessage("ALL WALLS DESTROYED");
                    gameTimer.stop();
                }
            }
            GameView.updatescreen(this);
        });

    }


    GameBoard_View GetGameView(){return GameView;}

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                game.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                game.player.movRight();
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
                game.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        game.player.stop();
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
            game.LevelReset();
            game.wall.wallReset();
            showPauseMenu = false;
            GameView.updatescreen(this);
        }
        else if(GameView.getexitButtonRect().contains(p)){
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
