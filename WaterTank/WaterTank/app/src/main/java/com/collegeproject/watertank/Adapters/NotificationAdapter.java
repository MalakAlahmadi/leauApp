package com.collegeproject.watertank.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.collegeproject.watertank.R;

import java.util.ArrayList;

/**
 * Created by mac on 06/11/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;




    public NotificationAdapter(Context context) {

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int position  = i;
        View convertView;
        if (view == null) {
            convertView = inflater.inflate(R.layout.notification_layout_item, null);
        } else {
            convertView = view;
        }

        return convertView;
    }






}
