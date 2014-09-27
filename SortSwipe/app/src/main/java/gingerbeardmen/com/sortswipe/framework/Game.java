package gingerbeardmen.com.sortswipe.framework;

/**
 * Created by Peter on 9/16/2014.
 */
public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public float getmAccel();
}
