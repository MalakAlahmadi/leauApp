package com.collegeproject.watertank.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.collegeproject.watertank.Models.NotificationModel;
import com.collegeproject.watertank.Network.Contract;
import com.collegeproject.watertank.R;

import java.util.ArrayList;

/**
 * Created by mac on 06/11/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<NotificationModel> notification;

    public NotificationAdapter(Context context , ArrayList<NotificationModel> notification) {
        this.notification = notification;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notification.size();
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
        TextView content = convertView.findViewById(R.id.notification_content);
        Float phF = Float.parseFloat(notification.get(position).getPH());
        int wLI = Integer.parseInt(notification.get(position).getWater_level());
        if (phF < 5 || phF > 11 || wLI < Contract.WATER_LEVEL) {
            content.setText(notification.get(position).toString());
        }
        return convertView;
    }






}
