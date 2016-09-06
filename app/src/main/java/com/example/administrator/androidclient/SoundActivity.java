package com.example.administrator.androidclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class SoundActivity extends Activity {
    MediaPlayer mp;

    private static final String serverIP = "192.168.123.101";
    private static final int serverPort = 9000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        mp = MediaPlayer.create(this, R.raw.sound1);
        mp.setLooping(false);
    }

    public void onSoundClick(View v) {
        switch (v.getId()) {
            case R.id.sound_play:
                ConnectThread thread1 = new ConnectThread(1);
                thread1.start();
                break;
            case R.id.sound_record:
                ConnectThread thread2 = new ConnectThread(2);
                thread2.start();
                break;
        }
    }

    class ConnectThread extends Thread {
        int num = 0;
        public ConnectThread(int n) {
            num = n;
        }
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void run() {
            try {
                Socket socket = new Socket(serverIP, serverPort);

                String output = "SOUND_";
                if (num == 1) {
                    output = "SOUND_PLAY";
                }
                else if (num == 2) {
                    output = "SOUND_RECORD";
                }
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                System.out.println("********************** 받은 데이터: " + input);
                String MSG = (String) input;
                if (Objects.equals(MSG, "SOUND_PLAY")) {
                    if (mp.isPlaying())
                        mp.pause();
                    else
                        mp.start();
                }
                else if (Objects.equals(MSG, "SOUND_RECORD")) {
                    ////
                }

                instream.close();
                outstream.close();
                instream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
