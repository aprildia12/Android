package com.example.administrator.androidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AndroidClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMenuClick(View v) {
        switch (v.getId()) {
            case R.id.main_location:
                Intent intent_location = new Intent(AndroidClient.this, LocationActivity.class);
                startActivity(intent_location);
                break;
            case R.id.main_sound:
                Intent intent_sound = new Intent(AndroidClient.this, SoundActivity.class);
                startActivity(intent_sound);
                break;
            case R.id.main_option:
                Intent intent_option = new Intent(AndroidClient.this, OptionActivity.class);
                startActivity(intent_option);
                break;
        }
    }
}

