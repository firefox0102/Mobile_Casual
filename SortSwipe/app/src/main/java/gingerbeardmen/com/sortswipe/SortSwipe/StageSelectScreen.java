package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;

import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input;
import gingerbeardmen.com.sortswipe.framework.Screen;

/**
 * Created by MHubers on 9/27/2014.
 */
public class StageSelectScreen extends Screen {
    public StageSelectScreen(Game game) {
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
                 * Goes to stage 1-1
                 */
                if(inBounds(event,22,47,71,54))
                {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes to stage 1-2
                 */
                if(inBounds(event,110,47,78,54))
                {
                    game.setScreen(new GameScreen2(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes to stage 1-3
                 */
                if(inBounds(event,210,47,90,54))
                {
                    game.setScreen(new GameScreen3(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                /**
                 * Goes back to level select
                 */
                if(inBounds(event,98,410,124,53))
                {
                    game.setScreen(new LevelSelectScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.stageSelect, 0, 0);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
