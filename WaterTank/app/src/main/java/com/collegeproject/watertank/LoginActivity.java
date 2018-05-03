package com.collegeproject.watertank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.collegeproject.watertank.Models.AuthModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;

import org.json.JSONException;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {


    String emailStr, passStr;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!getIdFromPrefs().equals(""))
        {
           startActivity(new Intent(this , MainActivity.class));
           finishAffinity();
        }

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

    }

    public boolean checkData() {

        emailStr = email.getText().toString();
        passStr = password.getText().toString();


        if (emailStr.isEmpty() && passStr.isEmpty()) {
            showToast("Fields are empty");
            return false;
        } else if (emailStr.isEmpty() || passStr.isEmpty()) {
            if (emailStr.isEmpty()) {
                showToast("Email is Empty.");
                return false;
            }
            if (passStr.isEmpty()) {
                showToast("Password is Empty.");
                return false;
            }
        } else {
            return true;
        }

        return false;
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void onClickLogin(View view) {
        int btnId = view.getId();
        if (btnId == R.id.login_start_btn) {


            if (checkData()) {


                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri(Contract.LOGIN_URL);
                p.setParams("email" , emailStr);
                p.setParams("password" , passStr);

                LOGIN_USER_TASK task = new LOGIN_USER_TASK();
                task.execute(p);

            }

        } else if (btnId == R.id.login_signup_link) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }


    class LOGIN_USER_TASK extends AsyncTask<RequestPackage, String, String> {

        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Authenticating User");
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
                Log.d("hsm","hoon");

                ArrayList<AuthModel> models = CustomParser.AuthParse(s);
                writeUserToPrefs(models.get(0));
                writeIdToPrefs(emailStr);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            pd.dismiss();
        }

    }

    public void writeUserToPrefs(AuthModel model) {
        Log.d("hsm", "hsm0");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", model.userName);
        editor.putString("phone", model.phone);
        editor.putString("email", model.email);
        editor.putString("address", model.areaId);
        editor.putString("supplier", model.supplier);
        editor.putString("maintainer", model.maintainer);
        editor.putString("alarm", model.alarmLvl);
        Contract.WATER_LEVEL=Integer.parseInt(model.alarmLvl.replace("%",""));
        editor.apply();
    }


    public void writeIdToPrefs(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String _id = settings.getString("email", "");
        return _id;
    }
    public static int getWaterFromPrefs(Context activity) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
        String _water = settings.getString("alarm", "");
        return  Integer.parseInt(_water.replace("%",""));
    }

}
