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
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AndroidClient extends AppCompatActivity {
    TextView textView;
    EditText editText;  //
    //
    DBHelper mHelper;
    String databaseName;

    android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.db_name);  //
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
            case R.id.open:
                try {
                    databaseName = editText.getText().toString();
                    mHelper = new DBHelper(this);
                    println("데이터 베이스를 열었습니다. : " + databaseName);
                } catch (Exception e) { e.printStackTrace(); }
                break;
            case R.id.add:
                try {
                    database = mHelper.getWritableDatabase();
                    if (database != null) {
                        database.execSQL("INSERT INTO Information (name, age, mobile) VALUES" +
                                "('김가은' , 23, '010-1234-5678')");
                        println("데이터를 추가했습니다.");
                    } else
                        println("데이터베이스를 먼저 열어야 합니다.");
                } catch(Exception e) { e.printStackTrace(); }
                break;
            case R.id.find:
                try {
                    database = mHelper.getReadableDatabase();
                    if(database != null) {
                        Cursor cursor = database.rawQuery("SELECT name, age, mobile FROM Information", null);

                        int count = cursor.getCount();
                        println("결과 레코드의 갯수 : " + count);

                        int i = 0;
                        while(cursor.moveToNext()) {
                            String name = cursor.getString(0);
                            int age = cursor.getInt(1);
                            String mobile = cursor.getString(2);

                            println("레코드 #" + i + " : " + name + ", " + age + ", " + mobile);
                            i++;
                        }
                    } else
                        println("데이터베이스를 먼저 열어야 합니다.");
                } catch(Exception e) { e.printStackTrace(); }
                break;
        }
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, databaseName, null, 1);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Information ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, age INTEGER, mobile TEXT);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Information");
            onCreate(db);
        }
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

