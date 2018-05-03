package com.collegeproject.watertank;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.collegeproject.watertank.Network.Contract;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      Contract.WATER_LEVEL= LoginActivity.getWaterFromPrefs(MainActivity.this);
///
        Context ctx = getApplicationContext();
/** this gives us the time for the first trigger.  */
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        long interval = 1000 * 60 * 1; // 5 minutes in milliseconds
        Intent serviceIntent = new Intent(ctx, MyService.class);
// make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        PendingIntent servicePendingIntent =
                PendingIntent.getService(ctx,
                        MyService.SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
// there are other options like setInexactRepeating, check the docs
        am.setRepeating(
                AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                cal.getTimeInMillis(),
                interval,
                servicePendingIntent
        );
        ///

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment())
                .commit();


        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

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
        return super.onOptionsItemSelected(item);
    }

}
