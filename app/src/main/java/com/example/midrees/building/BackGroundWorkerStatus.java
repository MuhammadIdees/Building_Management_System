package com.example.midrees.building;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

;

/**
 * Created by MuhammadWarisBaloch on 2/17/2018.
 */

public class BackGroundWorkerStatus extends AsyncTask<String[],Void,String[]> {

    InputStream inputStream = null;
    String line ;
    String result;
    Context context;
    static String[] name;
    public static String[] floor;
    public static String[] floorNo;
    public static String[] admin;
    public static String[] contact;

    BackGroundWorkerStatus(FlatFragment ctx) {
        context = ctx.getContext();
    }


    @Override
    protected String[] doInBackground(String[]... params) {
        try {
            //String login_URL = "http://172.16.10.56/getStatus.php";
            String login_URL = "http://" + MainActivity.ip +"getStatus.php";

            URL url = new URL(login_URL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            result = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject object;

            name = new String[jsonArray.length()];
            floor = new String[jsonArray.length()];
            contact = new String[jsonArray.length()];
            floorNo = new String[jsonArray.length()];
            admin = new String[jsonArray.length()];

            for (int i = 0; i<jsonArray.length(); i++){

                object = jsonArray.getJSONObject(i);
                name[i] = object.getString("status");
                floor[i] = object.getString("flat_no");
                contact[i] = object.getString("dues");
                floorNo[i] = object.getString("floor_no");
                admin[i] = object.getString("admin");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
    }

}

