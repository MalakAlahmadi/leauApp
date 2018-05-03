package com.collegeproject.watertank.Parser;

import android.util.Log;

import com.collegeproject.watertank.Models.AuthModel;
import com.collegeproject.watertank.Models.HomeModel;
import com.collegeproject.watertank.Models.LocaleModel;
import com.collegeproject.watertank.Models.ProviderModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by mac on 13/01/2018.
 */

public class CustomParser {


//    public static String authParser(String response) throws JSONException {
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("teacher");
//        String _id = arr.getJSONObject(0).getString("teacherObject");
//        return _id;
//    }
//
//

    public static ArrayList<LocaleModel> CitiesParse(String response) throws JSONException {
        ArrayList<LocaleModel> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {
            LocaleModel model = new LocaleModel();
            model.Id = array.getJSONObject(i).getInt("cityId");
            model.name = array.getJSONObject(i).getString("cityName");
            models.add(model);
        }
        return models;
    }

    public static ArrayList<LocaleModel> AreasParse(String response) throws JSONException {
        Log.d("hsm", response);
        ArrayList<LocaleModel> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {
            LocaleModel model = new LocaleModel();
            model.Id = array.getJSONObject(i).getInt("areaId");
            model.name = array.getJSONObject(i).getString("areaName");
            models.add(model);
        }
        return models;
    }

    public static ArrayList<ProviderModel> ProviderParse(String response) throws JSONException {
        ArrayList<ProviderModel> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {
            ProviderModel model = new ProviderModel();
            model.userName = array.getJSONObject(i).getString("username");
            model.name = array.getJSONObject(i).getString("name");
            models.add(model);
        }
        return models;
    }

    public static ArrayList<AuthModel> AuthParse(String response) throws JSONException {
        ArrayList<AuthModel> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {

            AuthModel model = new AuthModel();
            model.userName = array.getJSONObject(i).getString("username");
            model.password = array.getJSONObject(i).getString("password");
            model.name = array.getJSONObject(i).getString("name");
            model.email = array.getJSONObject(i).getString("email");
            model.phone = array.getJSONObject(i).getString("phone_number");
            model.ssn = array.getJSONObject(i).getString("SSN");
            Log.d("hsm", "zzzz");

            model.cityId = array.getJSONObject(i).getString("city_ID");
            model.areaId = array.getJSONObject(i).getString("area_ID");
            model.street = array.getJSONObject(i).getString("street");
            model.house = array.getJSONObject(i).getString("number");
            Log.d("hsm", "zzzz");
            model.supplier = array.getJSONObject(i).getString("supplier_ID");
            Log.d("hsm", "zzzz");
            model.maintainer = array.getJSONObject(i).getString("maintenance_ID");
            model.alarmLvl = array.getJSONObject(i).getString("alram_level");
            models.add(model);
            Log.d("hsm", "zzzz");
        }
        return models;
    }

    public static ArrayList<HomeModel> HomeParse(String response) throws JSONException {

        ArrayList<HomeModel> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {
            HomeModel model = new HomeModel();
            model.ph = array.getJSONObject(i).getInt("PH");
            model.water_level = array.getJSONObject(i).getInt("water_level");
            models.add(model);
        }
        return models;
    }

    public static ArrayList<String> NotificationParse(String response) throws JSONException {
        ArrayList<String> models = new ArrayList<>();
        JSONArray array = new JSONArray(response);
        for (int i = 0; i < array.length(); i++) {
            String model = array.getJSONObject(i).getString("content");
            models.add(model);
        }
        return models;
    }

//
//    public static ArrayList<InstituteModel> DepartmentParse(String response) throws JSONException {
//        ArrayList<InstituteModel> models = new ArrayList<>();
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("departments");
//        for (int i = 0; i < arr.length(); i++) {
//            InstituteModel model = new InstituteModel();
//            model._id = arr.getJSONObject(i).getString("departmentObject");
//            model.name = arr.getJSONObject(i).getString("departmentName");
//            models.add(model);
//        }
//        return models;
//    }
//
//
//    public static ArrayList<CourseModel> CoursesParse(String response) throws JSONException {
//        ArrayList<CourseModel> models = new ArrayList<>();
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("courses");
//        for (int i = 0; i < arr.length(); i++) {
//            CourseModel model = new CourseModel();
//            model.code = arr.getJSONObject(i).getString("courseObject");
//            model.name = arr.getJSONObject(i).getString("courseName");
//            models.add(model);
//        }
//        return models;
//    }
//
//    public static ArrayList<CourseModel> TeacherCoursesParse(String response) throws JSONException {
//        ArrayList<CourseModel> models = new ArrayList<>();
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("teacherCourses");
//        for (int i = 0; i < arr.length(); i++) {
//            CourseModel model = new CourseModel();
//            model.code = arr.getJSONObject(i).getString("courseObject");
//            model.nick = arr.getJSONObject(i).getString("courseCode");
//            model.name = arr.getJSONObject(i).getString("courseName");
//            models.add(model);
//        }
//        return models;
//    }
//
//
//    public static ArrayList<SlotModel> AllSlotsParse(String response) throws JSONException {
//        ArrayList<SlotModel> models = new ArrayList<>();
//
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("allSlots");
//        for (int i = 0; i < arr.length(); i++) {
//            SlotModel model = new SlotModel();
//            model._id = arr.getJSONObject(i).getString("slotObject");
//            model.fromTime = arr.getJSONObject(i).getString("fromTime");
//            model.name = arr.getJSONObject(i).getString("slotName");
//            model.toTime = arr.getJSONObject(i).getString("toTime");
//            model.weekDays = arr.getJSONObject(i).getString("weekDays");
//            String status = arr.getJSONObject(i).getString("status");
//            if (status.equals("0")) {
//                model.status = false;
//            } else {
//                model.status = true;
//            }
//            models.add(model);
//        }
//        return models;
//    }
//
//    public static String leaveApplicationParser(String response) throws JSONException {
//        JSONObject obj = new JSONObject(response);
//        JSONArray arr = obj.getJSONArray("leave");
//        String _id = arr.getJSONObject(0).getString("leaveObject");
//        return _id;
//    }

}
