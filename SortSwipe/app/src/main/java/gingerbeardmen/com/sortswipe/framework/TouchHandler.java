package gingerbeardmen.com.sortswipe.framework;

import gingerbeardmen.com.sortswipe.framework.Input.TouchEvent;
import java.util.List;
import android.view.View.OnTouchListener;

/**
 * Created by Peter on 9/18/2014.
 */
public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}