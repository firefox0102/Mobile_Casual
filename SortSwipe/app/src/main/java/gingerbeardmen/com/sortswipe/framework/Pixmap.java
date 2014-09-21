package gingerbeardmen.com.sortswipe.framework;

import gingerbeardmen.com.sortswipe.framework.Graphics.PixmapFormat;

/**
 * Created by Peter on 9/16/2014.
 */
public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
