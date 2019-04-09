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

public class BackgroundReport extends AsyncTask<String[],Void,String[]> {

    InputStream inputStream = null;
    String line ;
    String result;
    Context context;
    static String[] restatus;
    public static String[] refloor;
    public static String[] readmin;
    public static String[] reExp;
    public static String[] reCollect;
    public static String[] reDesc;
    public static String[] reDate;

    BackgroundReport(ReportFragment ctx) {
        context = ctx.getContext();
    }


    @Override
    protected String[] doInBackground(String[]... params) {
        try {
            //String login_URL = "http://172.16.10.56/getReport.php";
            String login_URL = "http://" + MainActivity.ip +"getReport.php";
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

            restatus = new String[jsonArray.length()];
            refloor = new String[jsonArray.length()];
            reCollect = new String[jsonArray.length()];
            reExp = new String[jsonArray.length()];
            readmin = new String[jsonArray.length()];
            reDesc = new String[jsonArray.length()];
            reDate = new String[jsonArray.length()];

            for (int i = 0; i<jsonArray.length(); i++){

                object = jsonArray.getJSONObject(i);
                restatus[i] = object.getString("bill_status");
                refloor[i] = object.getString("floor_no");
                reCollect[i] = object.getString("collection");
                reExp[i] = object.getString("expenses");
                readmin[i] = object.getString("owner");
                reDesc[i] = object.getString("description");
                reDate[i] = object.getString("date");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return restatus;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
    }

}

