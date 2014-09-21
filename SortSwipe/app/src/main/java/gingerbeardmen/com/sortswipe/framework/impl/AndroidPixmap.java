package gingerbeardmen.com.sortswipe.framework.impl;

import android.graphics.Bitmap;
import gingerbeardmen.com.sortswipe.framework.Graphics.PixmapFormat;
import gingerbeardmen.com.sortswipe.framework.Pixmap;

/**
 * Created by Peter on 9/21/2014.
 */
public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;

    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}