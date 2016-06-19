package com.example.administrator.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class LocationActivity extends Activity {
    DBHelper mHelper;
    TextView textView;
    int updateTime = 2000;
    android.os.Handler handler = new android.os.Handler();
    private boolean runThread = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textView = (TextView)findViewById(R.id.textView);
        mHelper = new DBHelper(this);
    }

    public void onDBClick(View v) {
        SQLiteDatabase database;

        database  = mHelper.getWritableDatabase();
        BackThread timeThread = new BackThread(database, updateTime);
        timeThread.setDaemon(true);

        switch (v.getId()) {
            case R.id.start:
                try {
                    runThread = true;
                    timeThread.start();
                } catch(Exception e) { e.printStackTrace(); }
                break;
            case R.id.stop:
                try {
                    runThread = false;
                    timeThread.join();

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

    class BackThread extends Thread {
        SQLiteDatabase mDatabase;
        int updateTime;
        BackThread(SQLiteDatabase database, int time) {
            mDatabase = database;
            updateTime = time;
        }
        public void run() {
            while(runThread) {
                String currentDate = nowDate();
                String currentLocation = "'123.456.789.012'";
                mDatabase = mHelper.getWritableDatabase();
                mDatabase.execSQL("INSERT INTO MyLocation (date, location) VALUES" +
                        "('" + currentDate + "', " + currentLocation + ")");

                try { Thread.sleep(updateTime); } catch(InterruptedException e) {;}
            }
        }
    }

    public String nowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);
        return dTime;
    }

    public void onMapClick(View v) {
        Intent intent = new Intent(LocationActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    private void println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}
