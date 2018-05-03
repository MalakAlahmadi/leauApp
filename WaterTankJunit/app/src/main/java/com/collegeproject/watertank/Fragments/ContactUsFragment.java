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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.Network.HttpManager;
import com.collegeproject.watertank.Network.RequestPackage;
import com.collegeproject.watertank.R;
import com.collegeproject.watertank.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    String emailStr, msgStr;

    EditText feedback;
   Button feedbackBtn;
    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);

        feedback = root.findViewById(R.id.feedback_ET);
        feedbackBtn = root.findViewById(R.id.feedbackBtn);

        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkData()) {
                    RequestPackage p = new RequestPackage();
                    p.setMethod("POST");
                    p.setUri(Contract.ORDER_COMMENTS_URL);

                    p.setParams("type", "comment");
                    p.setParams("email", getIdFromPrefs());
                    p.setParams("message", msgStr);

                    COMMENT_TASK task = new COMMENT_TASK();
                    task.execute(p);
                }
            }
        });




        return root;
    }

    public boolean checkData() {
        msgStr = feedback.getText().toString();
        if (msgStr.isEmpty()) {
            showToast("Please! Enter Feedback.");
            return false;
        } else {
            return true;
        }
    }


    class COMMENT_TASK extends AsyncTask<RequestPackage, String, String> {

        ProgressDialog pd = new ProgressDialog(getActivity());

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
                    showToast("Feedback Submitted Successfully");
                    feedback.setText("");
                }
            }catch (Exception e)
            {
                showToast("Something went wrong.");
            }
        }

    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public String getIdFromPrefs() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String _id = settings.getString("email", "");
        return _id;
    }

}
