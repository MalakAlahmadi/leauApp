package com.collegeproject.watertank.Models;

import com.collegeproject.watertank.Network.Contract;

/**
 * Created by ahmedsalamamohamed on 4/14/18.
 */

public class NotificationModel {
    int id;
    String user_id;
    String water_level;
    String PH;

    public NotificationModel() {
    }

    public NotificationModel(int id, String user_id, String water_level, String PH) {
        this.id = id;
        this.user_id = user_id;
        this.water_level = water_level;
        this.PH = PH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWater_level() {
        return water_level;
    }

    public void setWater_level(String water_level) {
        this.water_level = water_level;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    @Override
    public String toString() {
        int waterlevel = Integer.parseInt(water_level);
        float ph = Float.parseFloat(PH);
        if (ph < 5 || ph > 11) {
            return "PH level is "+ph+" Please check with the supplier";
        } else if (waterlevel < Contract.WATER_LEVEL) {
            return " Water level is Low please refill your tank";
        }
        return "";

    }
}
