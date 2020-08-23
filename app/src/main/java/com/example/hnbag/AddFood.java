package com.example.hnbag;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddFood extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static  ListView _listView;
    public static  GridViewArrayAdapter _adapterAdd;
    public static  ArrayList<Food> _favoritefoodAdd;
    private Button btcomeback;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);
        initComponents();
        btcomeback=(Button)findViewById(R.id.btreturn);
        ListView listView=(ListView)findViewById(R.id.FoodListAdd);
        listView.setOnItemClickListener(this);
    }

    public void returnFavoriteFood(View view) {
        Intent myIntent = new Intent(AddFood.this, FavoriteFood.class);
        AddFood.this.startActivity(myIntent);
    }


    private void initComponents() {
        _listView = findViewById(R.id.FoodListAdd);
        _adapterAdd = new GridViewArrayAdapter(this, R.layout.list_food_add, _favoritefoodAdd);
        _listView.setAdapter(_adapterAdd);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Food FoodAdd=_favoritefoodAdd.get(i);
        String DesAdd=FoodAdd.getDescription();
        int LogoAdd=FoodAdd.getLogoID();

        Food s=new Food(DesAdd,LogoAdd);
        (MainActivity.mProfile.favoriteFoods).add(s);
        _favoritefoodAdd.remove(i);
        (_adapterAdd).notifyDataSetChanged();
        new updateToDatabase().execute();
    }
    class updateToDatabase extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Gson gson = new GsonBuilder().create();
                String jsonFavoriteFood = gson.toJson(MainActivity.mProfile.favoriteFoods);
                String myURL = "https://api.appery.io/rest/1/db/users/" +
                        MainActivity.mProfile.get_id();
                URL obj = null;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("PUT");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");
                con.setRequestProperty("X-Appery-Session-Token", MainActivity.mProfile.getSessionToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                JSONObject json = new JSONObject();
                json.put("json_favorite_foods", JsonHandling.base64Encode(jsonFavoriteFood));
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
            } catch (IOException |
                    JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}