package gingerbeardmen.com.sortswipe.SortSwipe;

import gingerbeardmen.com.sortswipe.framework.Screen;
import gingerbeardmen.com.sortswipe.framework.impl.AndroidGame;

/**
 * Created by Peter on 9/21/2014.
 */
public class SortSwipeGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}