package com.collegeproject.watertank;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.collegeproject.watertank.Fragments.ContactUsFragment;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;

public class HardwareConfirmActivity extends AppCompatActivity {

    TextView email , phone , name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_confirm);

        email = findViewById(R.id.confirm_order_email);
        email.setText(getIdFromPrefs());

        phone = findViewById(R.id.confirm_order_phone);
        phone.setText(getPhoneFromPrefs());

        name = findViewById(R.id.confirm_order_name);
        name.setText(getNameFromPrefs());


    }

    public void OrderHardware(View view)
    {
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(Contract.ORDER_COMMENTS_URL);

        p.setParams("type", "order");
        p.setParams("email", getIdFromPrefs());
        p.setParams("message", "I need Ph Measurement Hardware.");

        COMMENT_TASK task = new COMMENT_TASK();
        task.execute(p);
    }

    class COMMENT_TASK extends AsyncTask<RequestPackage, String, String> {
        ProgressDialog pd = new ProgressDialog(HardwareConfirmActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Submitting Feedback");
            pd.show();
        }
        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                pd.dismiss();
                if (s.contains("200")) {
                   showAlertDailogue("Order Submitted Successfully.");
                }
            }catch (Exception e)
            {
                showToast("Something went wrong.");
            }
        }
    }

    public void showToast(String msg)
    {
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String _id = settings.getString("email", "");
        return _id;
    }

    public String getPhoneFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String _id = settings.getString("phone", "");
        return _id;
    }

    public String getNameFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String _id = settings.getString("name", "");
        return _id;
    }

    public void showAlertDailogue(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Hardware Order has been submitted.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
