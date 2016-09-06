package com.example.administrator.androidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AndroidClient extends AppCompatActivity {
    android.os.Handler handler = new android.os.Handler();  //

    private static final String serverIP = "192.168.123.101";
    private static final int serverPort = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onStartClick(View v) {
        ConnectThread thread = new ConnectThread(); //
        thread.start();
        Intent intent = new Intent(AndroidClient.this, LoginActivity.class);
        startActivity(intent);
    }
    class ConnectThread extends Thread {
        public void run() {
            try {
                Socket socket = new Socket(serverIP, serverPort);
                System.out.println("********************** 연결 완료 (" + serverIP + ", " + serverPort + ")");

                String output = "START";
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                System.out.println("********************** 받은 데이터: " + input);

                instream.close();
                outstream.close();
                instream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}