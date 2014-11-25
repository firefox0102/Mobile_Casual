package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.List;
import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Input.TouchEvent;
import gingerbeardmen.com.sortswipe.framework.Screen;

/**
 * Created by Peter on 9/21/2014.
 */
public class HelpScreen extends Screen {
    public HelpScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    //TODO: After implementing all art, add SORT help screen
                    //game.setScreen(new HelpScreen2(game));
                    game.setScreen(new HelpScreen3(game));
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
        g.drawPixmap(Assets.help1, 0, 0);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
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
