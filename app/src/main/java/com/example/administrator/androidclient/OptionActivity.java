package com.example.administrator.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class OptionActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    public void onOptionClick(View v) {
        switch (v.getId()) {
            case R.id.option_location:
                /////
                break;
            case R.id.option_camera:
                /////
                break;
            case R.id.option_sound:
                /////
                break;
            case R.id.oprion_lock:
                /////
                break;
        }
    }
}
