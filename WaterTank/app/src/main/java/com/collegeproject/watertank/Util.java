package com.collegeproject.watertank;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ahmedsalamamohamed on 4/15/18.
 */

public class Util {
    private static ProgressDialog pd;

    public static void showprogress(Context context, String message) {
        pd = ProgressDialog.show(context, "", message);
    }

    public static void dismissprogress() {
        if (pd != null) {
            pd.dismiss();
        }
    }
}
