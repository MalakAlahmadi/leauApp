package com.collegeproject.watertank.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.collegeproject.watertank.HardwareConfirmActivity;
import com.collegeproject.watertank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HardwareFragment extends Fragment {


    public HardwareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hardware, container, false);

        Button btn = root.findViewById(R.id.order_now_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity() , HardwareConfirmActivity.class));
            }
        });

        return root;
    }

}
