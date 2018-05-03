package com.collegeproject.watertank;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.collegeproject.watertank.Adapters.NotificationAdapter;
import com.collegeproject.watertank.Models.NotificationModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;

import java.util.ArrayList;

public class MyService extends Service {
    public static final int SERVICE_ID = 2414;
    ArrayList<NotificationModel> notification;
    static int id=0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(Contract.NOTIFICATION_URL);
        p.setParams("email", getIdFromPrefs());

        NOTIFICATION_TASK task = new NOTIFICATION_TASK();
        task.execute(p);


        return super.onStartCommand(intent, flags, startId);
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
                notification = CustomParser.NotificationParse(s,getApplicationContext());
                NotificationModel notificationModel = notification.get(notification.size() - 1);
                Float phF = Float.parseFloat(notificationModel.getPH());
                int wLI = Integer.parseInt(notificationModel.getWater_level());
                if (phF < 5 || phF > 11 || wLI < Contract.WATER_LEVEL) {


                    Intent intent1 = new Intent(MyService.this, NotificationActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(MyService.this, 0, intent1, 0);
                    Notification n = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Water Tank Alert")
                            .setContentText(notificationModel.toString())
                            .setContentIntent(pIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true).build();


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(id, n);
                    id++;
                }

            } catch (Exception e) {
                showToast("Something went wrong.");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyService.this);
        String _id = settings.getString("email", "");
        return _id;
    }
}
