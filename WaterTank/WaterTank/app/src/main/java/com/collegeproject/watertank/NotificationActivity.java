package com.collegeproject.watertank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.collegeproject.watertank.Adapters.NotificationAdapter;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

       ListView listView = findViewById(R.id.notification_lv);
        NotificationAdapter adapter = new NotificationAdapter(this);
        listView.setAdapter(adapter);

    }
}
