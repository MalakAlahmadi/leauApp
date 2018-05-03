package com.collegeproject.watertank;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mac on 12/10/2017.
 */

public class DatabaseUtil {

    //Database for the Project.
    private static FirebaseDatabase mDatabase;
    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
