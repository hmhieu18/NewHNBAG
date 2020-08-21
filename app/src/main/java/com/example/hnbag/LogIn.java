package com.example.hnbag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public static void checkLogIn() throws IOException, JSONException {
        new retrieveData().execute();
    }

    static class retrieveData extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/users/5f3fd8cc0f0d31746334124c";
                URL obj = null;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");
                con.setRequestProperty("X-Appery-Session-Token", "62f5bddb-195b-4883-986b-76b90532de19");
//                con.setRequestProperty("Content-Type", "application/json");

//                con.setDoOutput(true);
//                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//                JSONObject json = new JSONObject();
//                json.put("username", Profile.username);
//                json.put("password", Profile.password);
//                wr.writeBytes(json.toString());
//                wr.flush();
//                wr.close();

                int responseCode = con.getResponseCode();
                Log.d("Response Code : ", Integer.valueOf(responseCode).toString());

                BufferedReader iny = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = iny.readLine()) != null) {
                    response.append(output);
                }
                iny.close();
                //printing result from response
                Log.d("response", response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}