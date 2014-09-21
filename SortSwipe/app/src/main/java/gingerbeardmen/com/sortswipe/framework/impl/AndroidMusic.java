package gingerbeardmen.com.sortswipe.framework.impl;

import gingerbeardmen.com.sortswipe.framework.Music;
import java.io.IOException;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Created by Peter on 9/18/2014.
 */
public class AndroidMusic implements Music, OnCompletionListener{
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;

    public AndroidMusic(AssetFileDescriptor assetFileDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                                        assetFileDescriptor.getStartOffset(),
                                        assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music!");
        }
    }

    public void dispose() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void pause() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void play() {
        if(mediaPlayer.isPlaying()) {
            return;
        }
        try{
            synchronized (this) {
                if(!isPrepared) {
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }

    //Setters ******************************
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }
    // End of Setters **********************

    //Accessors*****************************
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isStopped() {
        return !isPrepared;
    }
    //End of Accessors***********************
}
