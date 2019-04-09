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
 * Created by MuhammadWarisBaloch on 2/15/2018.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {



    Context context;
    AlertDialog alertDialog;
    ProgressDialog progress;
    BackgroundWorker(Context ctx){
        context = ctx;
    }
    static boolean isUser = false;

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        //String login_URL = "http://172.16.10.56/user_login.php";
        //String login_URL = "http://192.168.1.4/building/user_login.php";
        String login_URL = "http://" + MainActivity.ip +"user_login.php";

        //System.out.println("waris");
        if(type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_URL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                System.out.println("Chacha");
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"
                        +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                System.out.println("Chacha2");

                String result = "";
                String line = "";


                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                System.out.println("Chacha3 " + result);

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
        progress = ProgressDialog.show(context, "","Logging in.....");
        //setter(true);
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

        if(result.equals("User")){
            progWait(1000);
            progress.dismiss();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("User logged in");
            alertDialog.show();
            Intent intent = new Intent(context,FlatActivity.class);
            context.startActivity(intent);

        }

        else if(result.equals("No user")){
            progWait(500);
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Flat not found");
            alertDialog.show();
        }

        else if(result.equals("Admin")){
            progWait(500);
            setter(true);
            alertDialog.setMessage(result);
            alertDialog.show();
        }

        if(result.equals("Login success")){
            progWait(1000);
            progress.dismiss();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Admin logged in");
            alertDialog.show();
            Intent intent = new Intent(context,FlatActivity.class);
            context.startActivity(intent);
        }

        else if(result.equals("Login Unsuccessful")){
            progWait(500);
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Admin not found");
            alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    void progWait(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.dismiss();
    }

    static void setter (boolean status){
        isUser = true;
    }

    static boolean getUser (){
        return isUser;
    }
}