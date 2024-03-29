package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;
import java.util.Set;

import android.graphics.Color;
import android.util.Log;

import gingerbeardmen.com.sortswipe.framework.Audio;
import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input;
import gingerbeardmen.com.sortswipe.framework.Input.TouchEvent;
import gingerbeardmen.com.sortswipe.framework.Music;
import gingerbeardmen.com.sortswipe.framework.Pixmap;
import gingerbeardmen.com.sortswipe.framework.Screen;

/**
 * Created by Peter on 9/21/2014.
 */
public class GameScreen extends Screen {
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver,
        Win
    }

    GameState state = GameState.Ready;
    World world;
    float gameTime = 0;
//    int oldScore = 0;
    int currentLevel;
    float leveltime = 10;
    int stage = 1;
//    String score = "0";
    boolean inFlingEvent = false;
    boolean inShakeEvent = false;

    //Assets for the level
    Pixmap card1Asset;
    Pixmap card2Asset;
    Pixmap card3Asset;
    Pixmap card4Asset;
    Pixmap card5Asset;
    Pixmap readyAsset;
    Pixmap stageAsset;
    Pixmap levelbackground;

    public GameScreen(Game game, int level) {
        super(game);
        world = new World();
        currentLevel = level;
        initializeLevelAssets();
        Assets.music.stop(); //Halts the main menu music
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver || state == GameState.Win)
            updateGameOver(touchEvents);
    }

    private void initializeLevelAssets() {
        stageAsset = Assets.stage1;

        if(currentLevel == 1) {
            levelbackground = Assets.level1background;
            readyAsset = Assets.level1ready;
            card1Asset = Assets.lev1card1;
            card2Asset = Assets.lev1card2;
            card3Asset = Assets.lev1card3;
            card4Asset = Assets.lev1card4;
            card5Asset = Assets.lev1card5;
        } else if(currentLevel == 2) {
            levelbackground = Assets.level2background;
            readyAsset = Assets.level2ready;
            card1Asset = Assets.lev2card1;
            card2Asset = Assets.lev2card2;
            card3Asset = Assets.lev2card3;
            card4Asset = Assets.lev2card4;
            card5Asset = Assets.lev2card5;
        } else if(currentLevel == 3) {
            levelbackground = Assets.level3background;
            readyAsset = Assets.level3ready;
            card1Asset = Assets.lev3card1;
            card2Asset = Assets.lev3card2;
            card3Asset = Assets.lev3card3;
            card4Asset = Assets.lev3card4;
            card5Asset = Assets.lev3card5;
        }
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();

        leveltime -= deltaTime;
        if(leveltime <= 0) {
            state = GameState.GameOver;
        } else {

            //If the phone is being shaken!!!
            if ((game.getmAccel() > 12) && (world.cardList.size() > 0) && (!inShakeEvent && !inFlingEvent)) {
                inShakeEvent = true;
                checkShakeEvent(world.cardList.get(0));
            } else {
                inShakeEvent = false;
            }

            for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);

                //Pause the game
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    Log.v("Blah", "Touch event! TOUCH UP!!!!");

                    if (event.x < 64 && event.y < 64) {
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Paused;
                        return;
                    }
                }

                if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                }

                if (world.cardList.size() > 0) {
                    Card topCard = world.cardList.get(0);

                    //Dragging Events
                    if (event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                        Log.v("Blah", "Touch event! Dragged!" + event.x + ", " + event.y);
                        if (!topCard.hasBeenFlung && !inShakeEvent) {
                            if ((event.x > topCard.x && event.y > topCard.y) && (event.x < (topCard.x + 100) && event.y < (topCard.y + 100))) {
                                checkFlingEvent(event.x, event.y, topCard);
                                inFlingEvent = false;
                            }
                        }
                    }
                } else {
                    nextStage(world);
                }
            }

            world.update(deltaTime);
            if (world.gameOver) {
                if (Settings.soundEnabled)
                    Assets.explosion.play(1);
                state = GameState.GameOver;
            }
//        if(oldScore != world.score) {
//            oldScore = world.score;
//            score = "" + oldScore;
//        }
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Running;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    private void checkFlingEvent(int x, int y, Card topCard) {
        if((topCard.x != x) || (topCard.y != y)) {
            topCard.x = x - 50;
            topCard.y = y - 50;
            int middleX = x, middleY = y;
            //Checking if the card has been dragged into the boundaries of a sorting direction
            if(middleY > 340) {
                inFlingEvent = true;
                flingDown(topCard);
            } else if(middleY < 140) {
                inFlingEvent = true;
                flingUp(topCard);
            } else if(middleX > 245) {
                inFlingEvent = true;
                flingRight(topCard);
            } else if (middleX < 75) {
                inFlingEvent = true;
                flingLeft(topCard);
            }
        }
    }

    private boolean flingLeft(Card topCard) {
        Log.v("FLING LEFT", "Flinging LEFT!!!");
        topCard.hasBeenFlung = true;
        if(checkSuccess(topCard, Card.TYPE_1)) {
            moveCardOffScreen(topCard, Card.TYPE_1);
        }
        return true;
    }

    private boolean flingRight(Card topCard) {
        Log.v("FLING RIGHT", "Flinging RIGHT!!!");
        topCard.hasBeenFlung = true;
        if(checkSuccess(topCard, Card.TYPE_2)) {
            moveCardOffScreen(topCard, Card.TYPE_2);
        }
        return true;
    }

    private boolean flingUp(Card topCard) {
        Log.v("FLING UP", "Flinging UP!!!");
        topCard.hasBeenFlung = true;
        if(checkSuccess(topCard, Card.TYPE_3)) {
            moveCardOffScreen(topCard, Card.TYPE_3);
        }
        return true;
    }

    private boolean flingDown(Card topCard) {
        Log.v("FLING DOWN", "Flinging DOWN!!!");
        topCard.hasBeenFlung = true;
        if(checkSuccess(topCard, Card.TYPE_4)) {
            moveCardOffScreen(topCard, Card.TYPE_4);
        }
        return true;
    }

    private boolean checkSuccess(Card topCard, int type) {
        boolean success = false;
        if(topCard.type == type) {
            if(topCard.type == Card.TYPE_5) {
                    Assets.explosion.play(1);
                world.score += 5;
            } else {
                Assets.success.play(1);
                world.score += 1;
            }
            success = true;
        } else {
            Assets.fail.play(1);
            topCard.hasBeenFlung = false;
            topCard.resetPosition();
        }
        return success;
    }

    private void moveCardOffScreen(Card topCard, int direction) {
        switch (direction) {
            case Card.TYPE_1:
                Log.v("moveOffScreen", "Moving left");
                //Move left
                while(topCard.x >= 0) {
                    topCard.x--;
                }
                world.cardList.remove(0);
                break;
            case Card.TYPE_2:
                Log.v("moveOffScreen", "Moving right");
                //Move right
                while(topCard.x <= 320) {
                    topCard.x++;
                }
                world.cardList.remove(0);
                break;
            case Card.TYPE_3:
                Log.v("moveOffScreen", "Moving up");
                //Move up
                while(topCard.y >= 0) {
                    topCard.y--;
                }
                world.cardList.remove(0);
                break;
            case Card.TYPE_4:
                Log.v("moveOffScreen", "Moving down");
                //Move down
                while(topCard.y <= 480) {
                    topCard.y++;
                }
                world.cardList.remove(0);
                break;
            case Card.TYPE_5:
                //Explode!!!
                world.cardList.remove(0);
                break;
        }
    }

    private boolean checkShakeEvent(Card topCard) {
        Log.v("SHAKE EVENT **", "SHAKE EVENT HAS BEEN TRIPPED");
        if(checkSuccess(topCard, Card.TYPE_5)) {
            //TODO:: Do shit for shaking the phone
            moveCardOffScreen(topCard, Card.TYPE_5);
        }
        return true;
    }

    private void nextStage(World world) {
        //Play sound
        Assets.nextlevel.play(1);
        switch (stage) {
            case 1:
                //Change stage image to Stage 2
                stage = 2;
                stageAsset = Assets.stage2;
                //Increment game time
                leveltime += 10;
                break;
            case 2:
                //Change stage image to Stage 3
                stage = 3;
                stageAsset = Assets.stage3;
                //Increment game time
                leveltime += 10;
                break;
            case 3:
                //Change stage image to Stage 4
                stage = 4;
                stageAsset = Assets.stage4;
                //Increment game time
                leveltime += 10;
                break;
            case 4:
                //YOU WIN!
                state = GameState.Win;
                break;
        }
        world.placeCards(stage);
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(levelbackground, 0, 0);
        drawWorld(world);
        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();
        if(state == GameState.Win)
            drawWinUI();
        if(state != GameState.GameOver || state != GameState.Win || state != GameState.Paused) {
            //Timer should go at the top of the screen so that the user can actually see it
            drawText(g, "" + (int) leveltime, g.getWidth() - 155 / 2, 440);
            //Score goes at the bottom of the screen to utilize the unused screen space down there
//            drawText(g, score, g.getWidth() - 50 - score.length() * 20 / 2, 450);
        } else {
            //Score goes at the bottom of the screen to utilize the unused screen space down there
            //drawText(g, "Score: " + score, 220, 100);
        }
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(readyAsset, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        //Pause button
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawPixmap(stageAsset, 90, 10);
        if(world.cardList.size() > 0) {
            Card topCard = world.cardList.get(0);

            Pixmap cardPixmap = null;
            if (topCard.type == Card.TYPE_1)
                cardPixmap = card1Asset;
            if (topCard.type == Card.TYPE_2)
                cardPixmap =card2Asset;
            if (topCard.type == Card.TYPE_3)
                cardPixmap = card3Asset;
            if (topCard.type == Card.TYPE_4)
                cardPixmap = card4Asset;
            if (topCard.type == Card.TYPE_5) {
                cardPixmap = card5Asset;
                //TODO:: Add particle effect to card
            }
//        int x = topCard.x * 32;
//        int y = topCard.y * 32;
            g.drawPixmap(cardPixmap, topCard.x, topCard.y);
        }

        //g.drawLine(0, 416, 480, 416, Color.BLACK);
        //Arrow buttons
        //g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        //g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameOver, 0, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
    }

    private void drawWinUI() {
        Graphics g = game.getGraphics();
        //TODO: Add winner image here
        g.drawPixmap(Assets.gameOver, 0, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        Assets.music.stop();
        if(state == GameState.Running)
            state = GameState.Paused;

        if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
            Assets.music.stop();
    }

    @Override
    public void dispose() {
        Assets.music.stop();
    }
}
