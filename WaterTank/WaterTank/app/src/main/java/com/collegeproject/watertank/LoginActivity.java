package com.collegeproject.watertank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onClickLogin(View view) {
        int btnId = view.getId();
        if (btnId == R.id.login_start_btn) {
         startActivity(new Intent(this , MainActivity.class));
        } else if (btnId == R.id.login_signup_link) {
        startActivity(new Intent(this , RegisterActivity.class));
        }
    }

}
