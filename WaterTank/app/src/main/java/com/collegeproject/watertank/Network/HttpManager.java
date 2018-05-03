package com.collegeproject.watertank.Network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Musti on 6/25/2016.
 */
public class HttpManager {

    public static String getPostData(RequestPackage p) {

        BufferedReader reader = null;
        String uri = p.getUri();

        if (p.getMethod().equals("GET")) {
            uri += "?" + p.getEncodedParams();


        }

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());




            if (p.getMethod().equals("POST")) {

                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(p.getEncodedParams());
                writer.flush();
            }

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.d("hsm", "string");
            Log.d("hsm", sb.toString());
            return sb.toString();

        } catch (Exception e) {

            Log.d("hsm", "err");
            e.printStackTrace();
            try {
                if (reader != null) {
                    reader.close();
                    return null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            return null;
        }

    }

    public static String getData(String uri) {

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (reader != null) {
                    reader.close();
                    return null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            return null;
        }

    }
}
