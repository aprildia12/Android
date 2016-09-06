package com.example.administrator.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class RegisterActivity extends Activity {
    EditText editName;
    EditText editEmail;
    EditText editPassword;

    DBMemberHelper mHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = (EditText)findViewById(R.id.register_name);
        editEmail = (EditText)findViewById(R.id.register_email);
        editPassword = (EditText)findViewById(R.id.register_password);

        mHelper = new DBMemberHelper(this);
    }

    public void onRegisterClick(View v) {
        SQLiteDatabase db;
        switch (v.getId()) {
            case R.id.register_btn:
                String inputName = editName.getText().toString();
                String inputID = editEmail.getText().toString();
                String inputPW = editPassword.getText().toString();

                // DB에 등록
                db = mHelper.getWritableDatabase();
                db.execSQL("INSERT INTO member (name, email, password) VALUES" + "('" + inputName + "', '" + inputID + "', '" + inputPW + "')");
                break;
            case R.id.register_back:
                break;
        }
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    class DBMemberHelper extends SQLiteOpenHelper {
        public DBMemberHelper(Context context) { super(context, "Register.db", null, 1); }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE member ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, email TEXT, password TEXT);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS member");
            onCreate(db);
        }
    }
}
