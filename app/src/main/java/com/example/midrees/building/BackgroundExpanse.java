package com.example.midrees.building;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by M IDREES on 2/18/2018.
 */

public class BackgroundExpanse extends AsyncTask<String,Void,String> {


    Context context;
    AlertDialog alertDialog;
    ProgressDialog progress;
    BackgroundExpanse(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];

        //String update_URL = "http://172.16.10.56/insertExpenseTest.php";
        String update_URL = "http://" + MainActivity.ip +"insertExpenseTest.php";

        if(type.equals("update")){
            try {
                String flat_no = params[1];
                String expenses = params[2];
                String description = params[3];
                String date = params[4];

                URL url = new URL(update_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("flat_no", "UTF-8")+"="+URLEncoder.encode(flat_no, "UTF-8")+"&"
                        + URLEncoder.encode("flat_no", "UTF-8")+"="+URLEncoder.encode(flat_no, "UTF-8")+"&"
                        + URLEncoder.encode("expenses", "UTF-8")+"="+URLEncoder.encode(expenses, "UTF-8")+"&"
                        + URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode(description, "UTF-8")+"&"
                        + URLEncoder.encode("date", "UTF-8")+"="+URLEncoder.encode(date, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String result = "";
                String line = "";


                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                System.out.println(result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
        progress = ProgressDialog.show(context, "","Updating....");


    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Insert success")){
            progWait(500);
            alertDialog.setTitle("Status");
            alertDialog.setMessage(result);
            alertDialog.show();
            Intent intent = new Intent(context,FlatActivity.class);
            context.startActivity(intent);
        }
        else  {
            progWait(500);
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Invalid Input");
            alertDialog.show();
        }
    }

    void progWait(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.dismiss();
    }
}
