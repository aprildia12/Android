package com.example.administrator.androidclient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class CameraActivity extends Activity {
    Bitmap profileBitmap = null;
    ImageView image_view;

    private static final String serverIP = "192.168.123.101";
    private static final int serverPort = 9000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        image_view = (ImageView)findViewById(R.id.view_image);
    }

    public void onCameraClick(View v) {
        switch (v.getId()) {
            case R.id.camera_image:
                ConnectThread thread1 = new ConnectThread(1);
                thread1.start();
                break;
            case R.id.camera_video:
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

                String output = "CAMERA_";
                if (num == 1) {
                    output = "CAMERA_IMAGE";
                }
                else if (num == 2) {
                    output = "CAMERA_VIDEO";
                }
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                System.out.println("********************** 받은 데이터: " + input);
                String MSG = (String) input;
                if (Objects.equals(MSG, "CAMERA_IMAGE")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                }
                else if (Objects.equals(MSG, "CAMERA_VIDEO")) {
                    Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(videoIntent, 2);
                }

                instream.close();
                outstream.close();
                instream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != 0) {
            if(requestCode==1 && !data.equals(null)) {
                try {
                    profileBitmap = (Bitmap)data.getExtras().get("data");
                    image_view.setImageBitmap(profileBitmap);
                    image_view.setScaleType(ImageView.ScaleType.FIT_XY);
                } catch(Exception e) {
                    return;
                }
            }
            else if(requestCode==2 && !data.equals(null)) {
                try {
                    //profileBitmap = (Bitmap)data.getExtras().get("data");
                    //image_view.setImageBitmap(profileBitmap);
                    //image_view.setScaleType(ImageView.ScaleType.FIT_XY);
                } catch(Exception e) {
                    return;
                }
            }
        }
    }

}
