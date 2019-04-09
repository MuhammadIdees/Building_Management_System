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

public class BackGroundWorkerList extends AsyncTask<String[],Void,String[]> {

    InputStream inputStream = null;
    String line ;
    String result;
    Context context;
    static String[] name;
    static String[] floor;
    static String[] contact;

    BackGroundWorkerList(MembersFragment ctx) {
        context = ctx.getContext();
    }


    @Override
    protected String[] doInBackground(String[]... params) {
        try {
            //String login_URL = "http://172.16.10.56/getData.php";
            String login_URL = "http://" + MainActivity.ip +"getData.php";

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

            for (int i = 0; i<jsonArray.length(); i++){

                object = jsonArray.getJSONObject(i);
                name[i] = object.getString("owner");
                floor[i] = object.getString("floor_no");
                floor[i] += ", Flat# " + object.getString("flat_no");
                contact[i] = object.getString("contact");
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