package com.example.administrator.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMenuClick(View v) {
        switch (v.getId()) {
            case R.id.main_location:
                Intent intent_location = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent_location);
                break;
            case R.id.main_camera:
                Intent intent_camera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent_camera);
                break;
            case R.id.main_sound:
                Intent intent_sound = new Intent(MainActivity.this, SoundActivity.class);
                startActivity(intent_sound);
                break;
            case R.id.main_option:
                Intent intent_option = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent_option);
                break;
        }
    }
}
