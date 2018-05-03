package com.collegeproject.watertank.Models;

import android.location.Location;

/**
 * Created by mac on 23/02/2018.
 */

public class LocaleModel {
    public int Id;
    public String name;

    public LocaleModel() {
    }

    public LocaleModel(int Id, String name) {
        this.Id = Id;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

