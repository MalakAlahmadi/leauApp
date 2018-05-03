package com.collegeproject.watertank;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.collegeproject.watertank.Fragments.ContactUsFragment;
import com.collegeproject.watertank.Fragments.HardwareFragment;
import com.collegeproject.watertank.Fragments.MainFragment;
import com.collegeproject.watertank.Fragments.SettingFragment;
import com.collegeproject.watertank.GPS.Utility;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment())
                .commit();


        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
            if(Utility.newuser) {
                tabId = R.id.tab_profile;
                Utility.newuser=false;
            }
                switch (tabId) {

                    case R.id.tab_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment())
                                .commit();
                        break;

                    case R.id.tab_hardware:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HardwareFragment())
                                .commit();
                        break;

                    case R.id.tab_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment())
                                .commit();
                        break;

                    case R.id.tab_contact_us:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ContactUsFragment())
                                .commit();
                        break;

                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_notification) {
            startActivity(new Intent(this, NotificationActivity.class));
        }
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(this , LoginActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

}
