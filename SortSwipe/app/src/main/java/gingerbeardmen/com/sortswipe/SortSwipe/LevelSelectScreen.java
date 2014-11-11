package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;

import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input;
import gingerbeardmen.com.sortswipe.framework.Screen;

/**
 * Created by MHubers on 9/27/2014.
 */
public class LevelSelectScreen extends Screen {
    public LevelSelectScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP)
            {
                /**
                 * Goes to Level 1
                 */
                if(inBounds(event,0,30,320,100))
                {
                    game.setScreen(new GameScreen(game, 1));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes to Level 2
                 */
                if(inBounds(event,0,123,320,100))
                {
                    game.setScreen(new GameScreen(game, 2));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes to Level 3
                 */
                if(inBounds(event,0,223,320,100))
                {
                    game.setScreen(new GameScreen(game, 3));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes back to menu
                 */
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    if (event.x < 64 && event.y > 416) {
                        game.setScreen(new MainMenuScreen(game));
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                    }
                }

            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.levelSelect, 0, 0);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);

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

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
