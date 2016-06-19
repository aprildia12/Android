package com.example.administrator.androidclient;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class SoundActivity extends Activity {
    MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        mp = MediaPlayer.create(this, R.raw.sound1);
        mp.setLooping(false);
    }

    public void onSoundClick(View v) {
        switch (v.getId()) {
            case R.id.sound_play:
                if (mp.isPlaying())
                    mp.pause();
                else
                    mp.start();
                break;
            case R.id.sound_record:
                break;
        }
    }
}
