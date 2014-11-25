package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;

import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input.TouchEvent;
import gingerbeardmen.com.sortswipe.framework.Screen;

/**
 * Created by Peter on 9/21/2014.
 */
public class MainMenuScreen extends Screen {
    boolean isMusicPlaying;

    public MainMenuScreen(Game game) {
        super(game);
        isMusicPlaying = false;
    }

    @Override
    public void update(float deltaTime) {
        if(!isMusicPlaying && Settings.soundEnabled) {
            Assets.music.play();
            isMusicPlaying = true;
        }
        if(!Settings.soundEnabled) {
            Assets.music.stop();
        }

        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    Settings.save(game.getFileIO());
                    if(Settings.soundEnabled) {
                        Assets.click.play(1);
                        Assets.music.play();
                    }
                }
                if(inBounds(event, 64, 185, 192, 42) ) {
                    game.setScreen(new LevelSelectScreen(game));//level select
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 250, 192, 42) ) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 315, 192, 42) ) {
                    game.setScreen(new HelpScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 380, 192, 42) ) {
                    game.setScreen(new CreditsScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 0, 5);
        g.drawPixmap(Assets.mainMenu, 15, 185);
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
        Assets.music.stop();
    }

    @Override
    public void resume() {
        if (Settings.soundEnabled) {
            Assets.music.play();
        }
    }

    @Override
    public void dispose() {
        Assets.music.stop();
    }
}
