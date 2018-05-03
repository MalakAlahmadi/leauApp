package com.collegeproject.watertank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.collegeproject.watertank.GPS.GPSTracker;
import com.collegeproject.watertank.GPS.Utility;
import com.collegeproject.watertank.Models.AuthModel;
import com.collegeproject.watertank.Models.LocaleModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;
import com.farbod.labelledspinner.LabelledSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, name, email, phone, ssn, house_no, street_no;
    LabelledSpinner area, city;

    String unStr, passStr, nameStr, emailStr, phoneStr, ssnStr, house_noStr, street_noStr, areaStr = "", cityStr = "";

    ArrayList<LocaleModel> areasList, cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.signup_user_name);
        password = findViewById(R.id.signup_password);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        phone = findViewById(R.id.signup_phone);
        ssn = findViewById(R.id.signup_ssn);
        house_no = findViewById(R.id.signup_home);
        street_no = findViewById(R.id.signup_street);

        city = findViewById(R.id.register_location_city);
        area = findViewById(R.id.register_location_area);
        Log.d("hsm", "hsm0");
        RequestPackage a = new RequestPackage();
        a.setMethod("GET");
        a.setUri(Contract.GET_ALL_AREAS);

        final GET_AREAS_TASK areas = new GET_AREAS_TASK();
        areas.execute(a);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(Contract.GET_ALL_CITIES);

        GET_CITIES_TASK task = new GET_CITIES_TASK();
        task.execute(p);

        Log.d("hsm", "hsm1");
        city.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                cityStr = String.valueOf(cities.get(position).getId());
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });


        area.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                areaStr = String.valueOf(areasList.get(position).getId());
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

    }

    public boolean checkData() {

        unStr = username.getText().toString();
        passStr = password.getText().toString();
        nameStr = name.getText().toString();
        emailStr = email.getText().toString();
        phoneStr = phone.getText().toString();
        ssnStr = ssn.getText().toString();
        house_noStr = house_no.getText().toString();
        street_noStr = street_no.getText().toString();

        if (unStr.isEmpty() && passStr.isEmpty() && nameStr.isEmpty() && emailStr.isEmpty()
                && phoneStr.isEmpty() && ssnStr.isEmpty() && house_noStr.isEmpty() && street_noStr.isEmpty()
                && areaStr.isEmpty() && cityStr.isEmpty()) {
            showToast("Fields are Empty");
            return false;
        } else if (unStr.isEmpty() || passStr.isEmpty() || nameStr.isEmpty() || emailStr.isEmpty()
                || phoneStr.isEmpty() || ssnStr.isEmpty() || house_noStr.isEmpty() || street_noStr.isEmpty()
                || areaStr.isEmpty() || cityStr.isEmpty()) {
            showToast("Incomplete Form");
            return false;
        } else {

            return true;
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    class GET_CITIES_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                cities = CustomParser.CitiesParse(s);
                city.setItemsArray(cities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class GET_AREAS_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            Log.d("hsm", "hsm2");
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.d("hsm", s);
                areasList = CustomParser.AreasParse(s);
                area.setItemsArray(areasList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerUser(View view) {
        GPSTracker gpsTracker;


        if (Utility.checkLocationPermission(this)) {
            gpsTracker = new GPSTracker(this);
            if (gpsTracker.canGetLocation()) {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();


                if (checkData()) {



                    RequestPackage a = new RequestPackage();
                    a.setMethod("POST");
                    a.setUri(Contract.SIGN_UP_URL);

                    a.setParams("userName", unStr);
                    a.setParams("password", passStr);
                    a.setParams("name", nameStr);
                    a.setParams("email", emailStr);
                    a.setParams("phone", phoneStr);
                    a.setParams("ssn", ssnStr);
                    a.setParams("cityId", cityStr);
                    a.setParams("areaId", areaStr);
                    a.setParams("street", street_noStr);
                    a.setParams("house", house_noStr);
                    a.setParams("latitude", new DecimalFormat("##.##").format(latitude));
                    a.setParams("longitude", new DecimalFormat("##.##").format(longitude));

                    REGISTER_USER_TASK areas = new REGISTER_USER_TASK();
                    areas.execute(a);
                }


            } else {
                gpsTracker.showSettingsAlert();
            }
        }

    }


    class REGISTER_USER_TASK extends AsyncTask<RequestPackage, String, String> {

        ProgressDialog pd = new ProgressDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Creating User");
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
             if (!s.contains("404")) {
                 ArrayList<AuthModel> models = CustomParser.AuthParse(s);
                 pd.dismiss();

                 writeIdToPrefs(models.get(0).email);
                 writeUserToPrefs(models.get(0));
             }


                startActivity(new Intent(RegisterActivity.this , MainActivity.class));
                   finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void writeUserToPrefs(AuthModel model) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", model.userName);
        editor.putString("phone", model.phone);
        editor.putString("email", model.email);
        editor.putString("address", model.areaId);
        editor.putString("supplier", model.supplier);
        editor.putString("maintainer", model.maintainer);
        editor.putString("alarm", model.alarmLvl);
        editor.apply();
    }


    public void writeIdToPrefs(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

}







