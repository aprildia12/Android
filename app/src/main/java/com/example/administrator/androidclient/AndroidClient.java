package com.example.administrator.androidclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AndroidClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            String hostname = "192.168.123.101";    // 안드로이드 어플리케이션에서 사용하는 IP
            int port = 9999;

            Socket sock = new Socket(hostname, port);   // 소켓 연결을 위해 Socket 객체를 생성

            // 데이터를 쓰기 위한 스트림 객체 생성. 그리고 쓰기
            ObjectOutputStream outstream = new ObjectOutputStream( sock.getOutputStream() );
            outstream.writeObject("Hello Android! ");
            outstream.flush();

            // 데이터를 읽기 위한 스트림 객체 생성. 그리고 쓰기.
            ObjectInputStream instream = new ObjectInputStream( sock.getInputStream() );
            String obj = (String) instream.readObject();

            //activity_main.xml에 있는 text 뷰에 표시
            TextView tText = new TextView(this);
            tText.setText(obj);
            setContentView(tText);

            sock.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
