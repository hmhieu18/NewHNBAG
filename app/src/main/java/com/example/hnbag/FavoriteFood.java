package com.example.hnbag;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FavoriteFood extends AppCompatActivity {
    public GridView _gridView;
    public static GridViewArrayAdapter _adapter;
    private Button btaddfood;
    public static int check = 0;
    private int _position = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_food);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) _position = extras.getInt("position");
        }

        check = check + 1;
        if (check == 1) {
            loadData();
            loadDataAdd();
            if (_position == -1)
                new FavoriteFood.updateFavoriteFoodsFromDatabase().execute();
            SystemClock.sleep(1000);
            initComponents();
        } else if (check > 1) {
            initComponents();
        }
        btaddfood = (Button) findViewById(R.id.btaddfood);

    }

    public void addfoodClicked(View view) {
        Intent myIntent = new Intent(FavoriteFood.this, AddFood.class);
        FavoriteFood.this.startActivity(myIntent);
    }


    private void loadData() {
        MainActivity.mProfile.favoriteFoods = new ArrayList<>();
//        Food f1 = new Food("Cua rang me", R.drawable.doan1);
//        Food f2 = new Food("Món Việt", R.drawable.doan2);
//        Food f3 = new Food("Món Thái", R.drawable.doan3);
//        Food f4 = new Food("Món Nhật", R.drawable.doan4);
//        MainActivity.mProfile.favoriteFoods.add(f1);
//        MainActivity.mProfile.favoriteFoods.add(f2);
//        MainActivity.mProfile.favoriteFoods.add(f3);
//        MainActivity.mProfile.favoriteFoods.add(f4);
//        new updateFavoriteFoodsFromDatabase().execute();
    }

    private void initComponents() {
        _gridView = findViewById(R.id.gridview_food);
        if (MainActivity.mProfile.favoritePlaces.size() != 0) {
            if (_position != -1)
                _adapter = new GridViewArrayAdapter(this, R.layout.gridview_food_items, MainActivity.mProfile.listGroup.get(_position).commonFavoriteFoods);
            else
                _adapter = new GridViewArrayAdapter(this, R.layout.gridview_food_items, MainActivity.mProfile.favoriteFoods);
            _gridView.setAdapter(_adapter);
        }
        _gridView.setAdapter(_adapter);
        _gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(FavoriteFood.this, searchResult.class);
                myIntent.putExtra("query", String.valueOf(MainActivity.mProfile.favoriteFoods.get(position).getDescription()));
                FavoriteFood.this.startActivity(myIntent);
            }
        });

    }


    private void loadDataAdd() {
        AddFood._favoritefoodAdd = new ArrayList<>();
        Food f1 = new Food("Cua rang me", R.drawable.doan1);
        Food f2 = new Food("Món Việt", R.drawable.doan2);
        Food f3 = new Food("Món Thái", R.drawable.doan3);
        Food f4 = new Food("Món Nhật", R.drawable.doan4);
        AddFood._favoritefoodAdd.add(f1);
        AddFood._favoritefoodAdd.add(f2);
        AddFood._favoritefoodAdd.add(f3);
        AddFood._favoritefoodAdd.add(f4);
    }

    public static class updateFavoriteFoodsFromDatabase extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/users/" +
                        MainActivity.mProfile.get_id();
                URL obj;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");
                con.setRequestProperty("X-Appery-Session-Token", MainActivity.mProfile.getSessionToken());

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
                String token = MainActivity.mProfile.sessionToken;
                MainActivity.mProfile = (new Gson()).fromJson(response.toString(), mProfile.class);
                MainActivity.mProfile.sessionToken = token;
                Type listType = new TypeToken<ArrayList<Results>>() {
                }.getType();
                if (MainActivity.mProfile.json_favorite_foods != null) {
                    MainActivity.mProfile.favoriteFoods = new Gson().fromJson(JsonHandling.base64Decode(MainActivity.mProfile.json_favorite_foods), new TypeToken<ArrayList<Food>>() {
                    }.getType());
                }
                if (MainActivity.mProfile.json_favorite_places != null) {
                    MainActivity.mProfile.favoritePlaces = new Gson().fromJson(JsonHandling.base64Decode(MainActivity.mProfile.json_favorite_places), listType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (_adapter != null)
                _adapter.notifyDataSetChanged();
        }
    }
}









