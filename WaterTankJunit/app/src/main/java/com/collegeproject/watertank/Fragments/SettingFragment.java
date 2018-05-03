package com.collegeproject.watertank.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.collegeproject.watertank.Models.ProviderModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.Parser.CustomParser;
import com.collegeproject.watertank.R;
import com.farbod.labelledspinner.LabelledSpinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    TextView alarmLevelTV , supplierTV , maintainerTV;
    String alarmStr , supplierStr , maintainerStr;


    ArrayList<ProviderModel> suppliers , maintainers;

    LabelledSpinner alarmLevel , supplier , maintainer;

    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_setting, container, false);


        alarmLevelTV = root.findViewById(R.id.alarm_lvl_tv);
        supplierTV = root.findViewById(R.id.supplier_tv);
        maintainerTV = root.findViewById(R.id.maintainer_tv);

        alarmLevel = root.findViewById(R.id.setting_profile_alarm);
        maintainer = root.findViewById(R.id.setting_profile_maintainer);
        supplier = root.findViewById(R.id.setting_profile_supplier);
        btn = root.findViewById(R.id.set_details);


        alarmLevelTV.setText("Alarm Level :"+getALFromPrefs());
        supplierTV.setText("Supplier :"+getSupplierFromPrefs());
        maintainerTV.setText("Maintainer :"+getMaintainerFromPrefs());

        final ArrayList<String> alarm_lvl = new ArrayList<>();
        alarm_lvl.add("10%");
        alarm_lvl.add("20%");
        alarm_lvl.add("30%");
        alarm_lvl.add("40%");
        alarm_lvl.add("50%");
        alarm_lvl.add("60%");
        alarm_lvl.add("70%");


        alarmLevel.setItemsArray(alarm_lvl);

        RequestPackage q = new RequestPackage();
        q.setUri(Contract.SUPPLIER_LIST_URL);
        q.setMethod("GET");
        GET_MAINTAINER_TASK t = new GET_MAINTAINER_TASK();
        t.execute(q);

        RequestPackage p = new RequestPackage();
        p.setUri(Contract.MAINTAINER_LIST_URL);
        p.setMethod("GET");
        GET_SUPPLIER_TASK task = new GET_SUPPLIER_TASK();
        alarmLevelTV.setText("Alarm Level :"+getALFromPrefs());
        supplierTV.setText("Supplier :"+getSupplierFromPrefs());
        maintainerTV.setText("Maintainer :"+getMaintainerFromPrefs());
        task.execute(p);



        alarmLevel.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                alarmStr = alarm_lvl.get(position);
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        supplier.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                supplierStr = suppliers.get(position).name;
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        maintainer.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                maintainerStr = maintainers.get(position).name;
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData())
                {
                    save();
                }
            }
        });

        return root;
    }

    class GET_SUPPLIER_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                maintainers = CustomParser.ProviderParse(s);
                maintainer.setItemsArray(maintainers);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showToast(String msg)
    {
        Toast.makeText(getActivity() , msg , Toast.LENGTH_SHORT).show();
    }

    class GET_MAINTAINER_TASK extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                suppliers = CustomParser.ProviderParse(s);
                supplier.setItemsArray(suppliers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save()
    {

        RequestPackage p = new RequestPackage();
        p.setUri(Contract.SET_DETAILS);
        p.setMethod("POST");
        p.setParams("alarmLevel" , alarmStr);
        p.setParams("supplierUserName" , supplierStr);
        p.setParams("maintainerUserName" , maintainerStr);
        p.setParams("email" , getIdFromPrefs() );

        SET_TASK task = new SET_TASK();
        task.execute(p);
    }


    class SET_TASK extends AsyncTask<RequestPackage, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Updating Information");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getPostData(requestPackages[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            try {

                writeALarmToPrefs(alarmStr);
                writeSupplierToPrefs(supplierStr);
                writeMaintainerToPrefs(maintainerStr);

                alarmLevelTV.setText("Alarm Level :"+getALFromPrefs());
                supplierTV.setText("Supplier :"+getSupplierFromPrefs());
                maintainerTV.setText("Maintainer :"+getMaintainerFromPrefs());


                showToast("Information Updated.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkData()
    {
        if (alarmStr.isEmpty() || supplierStr.isEmpty() || maintainerStr.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public void writeALarmToPrefs(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("alarm", email);
        editor.apply();
    }

    public void writeSupplierToPrefs(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("supplier", email);
        editor.apply();
    }

    public void writeMaintainerToPrefs(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("maintainer", email);
        editor.apply();
    }
    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("email", "");
        return _id;
    }

    public String getALFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("alarm", "");
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
}
