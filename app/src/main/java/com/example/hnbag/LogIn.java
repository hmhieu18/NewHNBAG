package com.example.hnbag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LogIn extends AppCompatActivity {
    private EditText username;
    private TextView password;
    private Button login;
    private boolean isvalid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initComponent();
    }

    private void initComponent() {
        MainActivity.mProfile = new mProfile();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInClicked(v);
            }
        });
    }

    public void checkLogIn() throws IOException, JSONException {
        new retrieveData().execute();
    }

    public void logInClicked(View view) {
        MainActivity.mProfile.setUsername(String.valueOf(username.getText()));
        MainActivity.mProfile.setPassword(String.valueOf(password.getText()));
        try {
            checkLogIn();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
        }
    }

    class retrieveData extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/login";
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
                json.put("username", MainActivity.mProfile.username);
                json.put("password", MainActivity.mProfile.password);
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
                MainActivity.mProfile = new Gson().fromJson(response.toString(), mProfile.class);
                Log.d("token", MainActivity.mProfile.getSessionToken());
                if (MainActivity.mProfile.get_id() != null)
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
                fetchData();
                Intent myIntent = new Intent(LogIn.this, MainActivity.class);
                LogIn.this.startActivity(myIntent);
                finish();
            } else
                Toast.makeText(LogIn.this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchData() {
        new FavoriteFood.updateFavoriteFoodsFromDatabase().execute();
        new GroupDisplay.updateGroupFromDatabase().execute();
        new FavoritePlaces.updateFavoritePlacesFromDatabase().execute();
    }
}