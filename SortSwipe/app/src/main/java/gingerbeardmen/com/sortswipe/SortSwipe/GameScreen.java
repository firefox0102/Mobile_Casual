package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;
import android.graphics.Color;
import android.util.Log;
import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input;
import gingerbeardmen.com.sortswipe.framework.Input.TouchEvent;
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
        GameOver
    }

    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";
    boolean inFlingEvent = false;
    boolean inShakeEvent = false;

    public GameScreen(Game game) {
        super(game);
        world = new World();
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
        if(state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();

        //If the phone is being shaken!!!
        if((game.getmAccel() > 12) && (world.cardList.size() > 0) && !inFlingEvent) {
            inShakeEvent = true;
            checkShakeEvent(world.cardList.get(0));
        } else {
            inShakeEvent = false;
        }

        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            //Pause the game
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                Log.v("Blah", "Touch event! TOUCH UP!!!!");
                inFlingEvent = false;

                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
            }

            if(world.cardList.size() > 0) {
                Card topCard = world.cardList.get(0);

                //Dragging Events
                if (event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                    Log.v("Blah", "Touch event! Dragged!" + event.x + ", " + event.y);
                    if(!topCard.hasBeenFlung && !inShakeEvent) {
                        if ((event.x > topCard.x && event.y > topCard.y) && (event.x < (topCard.x + 100) && event.y < (topCard.y + 100))) {
                            Log.v("Output", "Touch event! Touch Down!");
                            //TODO:: some sort of fling method for directions
                            checkFlingEvent(event.x, event.y, topCard);
                            //inFlingEvent = false;
                        }
                    }
                }
            } else {
                //TODO:: This is where the game either needs to end, or something needs to happen
                //world.placeCards();
                state = GameState.GameOver;
            }
        }

        world.update(deltaTime);
        if(world.gameOver) {
            if(Settings.soundEnabled)
                Assets.bitten.play(1);
            state = GameState.GameOver;
        }
        if(oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            if(Settings.soundEnabled)
                Assets.eat.play(1);
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

    private boolean checkFlingEvent(int x, int y, Card topCard) {
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
            return true;
        } else {
            return false;
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
                world.score += 5;
            } else {
                world.score += 1;
            }
            success = true;
        } else {
            Assets.bitten.play(1);
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

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();

        drawText(g, score, g.getWidth()- 50 - score.length()*20 / 2, 10);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();

        /*
        Stain stain = world.stain;

        Pixmap stainPixmap = null;
        if(stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;
        if(stain.type == Stain.TYPE_2)
            stainPixmap = Assets.stain2;
        if(stain.type == Stain.TYPE_3)
            stainPixmap = Assets.stain3;
        int x = stain.x * 32;
        int y = stain.y * 32;
        g.drawPixmap(stainPixmap, x, y);
        */
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        //Pause button
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);

        if(world.cardList.size() > 0) {
            Card topCard = world.cardList.get(0);

            Pixmap cardPixmap = null;
            if (topCard.type == Card.TYPE_1)
                cardPixmap = Assets.card1;
            if (topCard.type == Card.TYPE_2)
                cardPixmap = Assets.card2;
            if (topCard.type == Card.TYPE_3)
                cardPixmap = Assets.card3;
            if (topCard.type == Card.TYPE_4)
                cardPixmap = Assets.card4;
            if (topCard.type == Card.TYPE_5)
                cardPixmap = Assets.card5;
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

        g.drawPixmap(Assets.gameOver, 62, 100);

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
        if(state == GameState.Running)
            state = GameState.Paused;

        if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }
}
