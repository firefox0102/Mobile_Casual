package gingerbeardmen.com.sortswipe.framework.impl;

import gingerbeardmen.com.sortswipe.framework.Sound;
import android.media.SoundPool;

/**
 * Created by Peter on 9/18/2014.
 */
public class AndroidSound implements Sound{
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    public  void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}
