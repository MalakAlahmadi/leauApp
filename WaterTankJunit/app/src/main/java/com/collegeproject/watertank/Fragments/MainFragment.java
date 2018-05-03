package com.collegeproject.watertank.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.collegeproject.watertank.Models.HomeModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;
import com.collegeproject.watertank.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    TextView waterLvl;
    String serviceProvider;
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        waterLvl = root.findViewById(R.id.home_water_level);
         img = root.findViewById(R.id.main_meter);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(Contract.HOME_URL);
        p.setParams("email", getIdFromPrefs());

        HOME_TASK task = new HOME_TASK();
        task.execute(p);

        Button btn = root.findViewById(R.id.request_service_btn);
        Button btn2 = root.findViewById(R.id.request_maintaince_btn);
        Button btn3 = root.findViewById(R.id.test_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                   serviceProvider =  getSupplierFromPrefs();

                    RequestPackage p = new RequestPackage();
                    p.setMethod("POST");
                    p.setUri(Contract.SUPPLIER_LIST_URL);
                    p.setParams("email", getIdFromPrefs());
                    p.setParams("userName" , getSupplierFromPrefs());

                    REQUEST_TASK task = new REQUEST_TASK();
                    task.execute(p);


                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Please! Select Supplier", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    serviceProvider =  getMaintainerFromPrefs();

                    RequestPackage p = new RequestPackage();
                    p.setMethod("POST");
                    p.setUri(Contract.MAINTAINER_LIST_URL);
                    p.setParams("email", getIdFromPrefs());
                    p.setParams("userName" , getMaintainerFromPrefs());

                    REQUEST_TASK task = new REQUEST_TASK();
                    task.execute(p);

                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Please! Select Mainatiner", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    img.setImageResource(R.drawable.zero);
                    waterLvl.setText("0%");

                    RequestPackage p = new RequestPackage();
                    p.setMethod("POST");
                    p.setUri(Contract.HOME_URL);
                    p.setParams("email", getIdFromPrefs());

                    HOME_TASK task = new HOME_TASK();
                    task.execute(p);

                }
                catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    class HOME_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            Log.d("Hloga", content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                ArrayList<HomeModel> models = CustomParser.HomeParse(s);
                waterLvl.setText(models.get(0).water_level + "%");
             //   Toast.makeText(getActivity() , models.get(0).water_level+"" , Toast.LENGTH_SHORT).show();

                updateMeter(models.get(0).ph);

            } catch (Exception e) {

            }
        }
    }

    public void showAlertDailogue(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("Request has been sent to " + msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("email", "");
        return _id;
    }

    public String getSupplierFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("supplier", "");
        return _id;
    }

    public String getMaintainerFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("maintainer", "");
        return _id;
    }


    class REQUEST_TASK extends AsyncTask<RequestPackage, String, String> {

        ProgressDialog pd = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Requesting Service");
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

                   showAlertDailogue("Request has been sent to "+serviceProvider);

            }catch (Exception e)
            {
                Toast.makeText(getActivity() , "Something went wrong." , Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void updateMeter(int position)
    {

        switch (position)
        {

            case 0:
                img.setImageResource(R.drawable.zero);
                 break;

            case 1:
                img.setImageResource(R.drawable.one);
                break;

            case 2:
                img.setImageResource(R.drawable.two);
                break;

            case 3:
                img.setImageResource(R.drawable.three);
                break;

            case 4:
                img.setImageResource(R.drawable.four);
                break;

            case 5:
                img.setImageResource(R.drawable.five);
                break;

            case 6:
                img.setImageResource(R.drawable.six);
                break;

            case 7:
                img.setImageResource(R.drawable.seven);
                break;

            case 8:
                img.setImageResource(R.drawable.eight);
                break;

            case 9:
                img.setImageResource(R.drawable.nine);
                break;

            case 10:
                img.setImageResource(R.drawable.ten);
                break;

            case 11:
                img.setImageResource(R.drawable.eleven);
                break;

            case 12:
                img.setImageResource(R.drawable.tweleve);
                break;

            case 13:
                img.setImageResource(R.drawable.thirteen);
                break;

            case 14:
                img.setImageResource(R.drawable.fourteen);
                break;

        }


    }

}
