package com.example.administrator.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Gaeun on 2016-06-19.
 */
public class LoginActivity extends Activity {
    EditText editEmail;
    EditText editPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText)findViewById(R.id.login_email);
        editPassword = (EditText)findViewById(R.id.login_password);
    }

    public void onLoginClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //ID, PW 저장
                String id = editEmail.getText().toString();
                String pw = editPassword.getText().toString();

                //DB 오픈 후 확인
                /*try {
                    String memberID = "1";
                    String memberPW = "2";

                    SQLiteDatabase db = openOrCreateDatabase("Register.db", Context.MODE_PRIVATE, null);
                    Cursor cursor = db.rawQuery("SELECT * FROM member", null);

                    int count = cursor.getCount();
                    for (int i = 0; i < count; i++) {
                        cursor.moveToNext();

                        memberID = cursor.getString(1);
                        if (memberID.equals(id)) {
                            memberPW = cursor.getString(2);
                            if (memberPW.equals(id)) {
                                Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent_main);
                            }
                        }
                        if (id.equals(cursor.getString(1)) && pw.equals(cursor.getString(2))) {
                            Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent_main);
                        }
                        Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_main);
                    }
                    Toast.makeText(LoginActivity.this, "id = " + id + " pw = " + pw + " memberID = " + memberID + " memberPW = " + memberPW, Toast.LENGTH_LONG).show();
                } catch(Exception e) { } */
                Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_main);
                break;
            case R.id.login_register:
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                break;
        }
    }
}
