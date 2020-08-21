package com.example.hnbag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp extends AppCompatActivity {
    private EditText username;
    private TextView password;
    private boolean isvalid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initComponent();
    }

    private void initComponent() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void checkLogIn() throws IOException, JSONException {
        new createAccount().execute();
    }

    public void logInClicked(View view) {
        MainActivity.profile.setUsername(String.valueOf(username.getText()));
        MainActivity.profile.setPassword(String.valueOf(password.getText()));
        try {
            checkLogIn();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
        }
    }

    class createAccount extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/users";
                URL obj = null;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");
//                con.setRequestProperty("X-Appery-Session-Token", "62f5bddb-195b-4883-986b-76b90532de19");
                con.setRequestProperty("Content-Type", "application/json");

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                JSONObject json = new JSONObject();
                json.put("username", MainActivity.profile.username);
                json.put("password", MainActivity.profile.password);
                wr.writeBytes(json.toString());
                wr.flush();
                wr.close();

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
                MainActivity.profile = new Gson().fromJson(response.toString(), Profile.class);
                if (MainActivity.profile.get_id() != null)
                    isvalid = true;
                else isvalid = false;
            } catch (IOException |
                    JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //get favorite
            if (isvalid) {
                Intent myIntent = new Intent(SignUp.this, MainActivity.class);
                SignUp.this.startActivity(myIntent);
                finish();
            } else
                Toast.makeText(SignUp.this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
        }
    }
}