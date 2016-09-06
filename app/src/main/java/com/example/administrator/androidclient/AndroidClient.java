package com.example.administrator.androidclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class AndroidClient extends AppCompatActivity {
    android.os.Handler handler = new android.os.Handler();  //

    // Wifi실제 : 121.88.50.16
    // Wifi : 192.168.123.101
    // 포켓실제 : 223.52.66.14
    // 포켓 : 192.168.1.117
    private static final String serverIP = "192.168.123.101";
    private static final int serverPort = 9000;
    TextView textview;

    Socket socket = null;
    BufferedReader reader = null;
    PrintWriter writer = null;

    public String getClientIP() {
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return inet.getHostAddress();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        textview = (TextView)findViewById(R.id.message);
    }

    public void onStartClick(View v) {
        ConnectThread thread = new ConnectThread(); //
        thread.start();
        //Intent intent = new Intent(AndroidClient.this, LoginActivity.class);
        //startActivity(intent);
    }
    class ConnectThread extends Thread {
        public void run() {
            try {
                socket = new Socket(serverIP, serverPort);
                System.out.println("********************** 연결 완료 (" + serverIP + ", " + serverPort + ")");

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());

                ReaderThread rthread = new ReaderThread();
                rthread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onMSGClick(View v) {
        String input = "";
        input = textview.getText().toString();
        if (input != "") {
            writer.println(input);
            writer.flush();
            System.out.println("************************메시지 : " + input);
        }
    }

    class ReaderThread extends Thread {
        public void run() {
            String temp;
            while(true) {
                try {
                    temp = reader.readLine();
                    if (!temp.isEmpty()) {
                        System.out.println("명령 : " + temp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}