package com.collegeproject.watertank;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.collegeproject.watertank.Adapters.NotificationAdapter;
import com.collegeproject.watertank.Fragments.ContactUsFragment;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listView = findViewById(R.id.notification_lv);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(Contract.NOTIFICATION_URL);
        p.setParams("email", getIdFromPrefs());

        NOTIFICATION_TASK task = new NOTIFICATION_TASK();
        task.execute(p);

    }


    class NOTIFICATION_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                ArrayList<String> notification = CustomParser.NotificationParse(s);
                NotificationAdapter adapter = new NotificationAdapter(NotificationActivity.this , notification);
                listView.setAdapter(adapter);

            } catch (Exception e) {
                showToast("Something went wrong.");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        String _id = settings.getString("email", "");
        return _id;
    }

}
