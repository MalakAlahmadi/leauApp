package com.collegeproject.watertank.Models;

/**
 * Created by mac on 24/02/2018.
 */

public class AuthModel {
    public String userName, password, name, email, phone, ssn, cityId, areaId, street, house, latitude, longitude;
    public String supplier , maintainer , alarmLvl;
    public AuthModel() {
    }

    @Override
    public String toString() {
        return email;
    }
}
