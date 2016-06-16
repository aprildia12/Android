package com.example.administrator.androidclient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidClient extends AppCompatActivity {
    DBHelper mHelper;
    TextView textView;
    android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        mHelper = new DBHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClicked(View v) {
        ConnectThread thread = new ConnectThread();
        thread.start();

        SQLiteDatabase database;

        switch (v.getId()) {
            case R.id.insert:
                try {
                    database = mHelper.getWritableDatabase();

                    String currentDate = nowDate();
                    String currentLocation = "'123.456.789.012'";
                    database = mHelper.getWritableDatabase();
                    database.execSQL("INSERT INTO MyLocation (date, location) VALUES" +
                                "('" + currentDate + "', " + currentLocation + ")");
                } catch(Exception e) { e.printStackTrace(); }
                break;
            case R.id.show:
                try {
                    database = mHelper.getReadableDatabase();
                    Cursor cursor = database.rawQuery("SELECT date, location FROM MyLocation", null);

                    int count = cursor.getCount();
                    println("레코드 개수 = " + count);

                    for (int i = 0; i < count; i++) {
                        cursor.moveToNext();

                        String date = cursor.getString(0);
                        String location = cursor.getString(1);

                        println("#" + i + " : " + date + ", " + location);
                    }
                } catch(Exception e) { e.printStackTrace(); }
                break;
            case R.id.delete:
                try {
                    database = mHelper.getWritableDatabase();
                    database.execSQL("DELETE FROM MyLocation");
                } catch(Exception e) { e.printStackTrace(); }
                break;
        }
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "MyLocation", null, 1);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE MyLocation ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT, location TEXT);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS MyLocation");
            onCreate(db);
        }
    }

    public String nowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);
        return dTime;
    }

    class ConnectThread extends Thread {
        public void run() {

            String host = "192.168.123.101";
            int port = 5001;

            try {
                Socket socket = new Socket(host, port);
                System.out.println("서버로 연결되었습니다." + host + ", " + port);

                String output = "Hello";
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();
                System.out.println("서버로 보낸 데이터: " + output);

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                Object input = instream.readObject();
                System.out.println("서버로부터 받은 데이터: " + input);

                instream.close();
                outstream.close();
                instream.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    private void println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
        //textView.append(data + "\n");   //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}

