package gingerbeardmen.com.sortswipe.SortSwipe;

import gingerbeardmen.com.sortswipe.framework.Game;
import gingerbeardmen.com.sortswipe.framework.Graphics;
import gingerbeardmen.com.sortswipe.framework.Screen;
import gingerbeardmen.com.sortswipe.framework.Graphics.PixmapFormat;

/**
 * Created by Peter on 9/21/2014.
 */
public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.credits1 = g.newPixmap("credits1.png", PixmapFormat.ARGB4444);
        Assets.credits2 = g.newPixmap("credits2.png", PixmapFormat.ARGB4444);
        Assets.credits3 = g.newPixmap("credits3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.levelSelect = g.newPixmap("levelSelect.png", PixmapFormat.ARGB4444);
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);

        //Loading level 1 assets
        Assets.lev1card1 = g.newPixmap("level1/level1card1.png", PixmapFormat.ARGB4444);
        Assets.lev1card2 = g.newPixmap("level1/level1card2.png", PixmapFormat.ARGB4444);
        Assets.lev1card3 = g.newPixmap("level1/level1card3.png", PixmapFormat.ARGB4444);
        Assets.lev1card4 = g.newPixmap("level1/level1card4.png", PixmapFormat.ARGB4444);
        Assets.lev1card5 = g.newPixmap("level1/level1card5.png", PixmapFormat.ARGB4444);
        Assets.level1background = g.newPixmap("level1/level1background.png", PixmapFormat.RGB565);
        Assets.level1ready = g.newPixmap("level1/level1ready.png", PixmapFormat.ARGB4444);

        //Loading level 2 assets
        Assets.lev2card1 = g.newPixmap("level2/level2card1.png", PixmapFormat.ARGB4444);
        Assets.lev2card2 = g.newPixmap("level2/level2card2.png", PixmapFormat.ARGB4444);
        Assets.lev2card3 = g.newPixmap("level2/level2card3.png", PixmapFormat.ARGB4444);
        Assets.lev2card4 = g.newPixmap("level2/level2card4.png", PixmapFormat.ARGB4444);
        Assets.lev2card5 = g.newPixmap("level2/level2card5.png", PixmapFormat.ARGB4444);
        Assets.level2background = g.newPixmap("level2/level2background.png", PixmapFormat.RGB565);
        Assets.level2ready = g.newPixmap("level2/level2ready.png", PixmapFormat.ARGB4444);

        //Loading level 3 assets
        Assets.lev3card1 = g.newPixmap("level3/level3card1.png", PixmapFormat.ARGB4444);
        Assets.lev3card2 = g.newPixmap("level3/level3card2.png", PixmapFormat.ARGB4444);
        Assets.lev3card3 = g.newPixmap("level3/level3card3.png", PixmapFormat.ARGB4444);
        Assets.lev3card4 = g.newPixmap("level3/level3card4.png", PixmapFormat.ARGB4444);
        Assets.lev3card5 = g.newPixmap("level3/level3card5.png", PixmapFormat.ARGB4444);
        Assets.level3background = g.newPixmap("level3/level3background.png", PixmapFormat.RGB565);
        Assets.level3ready = g.newPixmap("level3/level3ready.png", PixmapFormat.ARGB4444);

        //Sound Assets
        Assets.click = game.getAudio().newSound("click.wav");
        Assets.success = game.getAudio().newSound("success.wav");
        Assets.fail = game.getAudio().newSound("fail.wav");
        Assets.explosion = game.getAudio().newSound("explode.wav");
        //Game Music
        //YEAH by Nicholas Shooter, may be found at https://soundcloud.com/nicholas-shooter/yeah
        Assets.music = game.getAudio().newMusic("music.wav");

        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

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
}
