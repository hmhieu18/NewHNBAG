package com.example.hnbag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FavoriteFood extends AppCompatActivity {
<<<<<<< HEAD
    public static GridView _gridView;
    public static  GridViewArrayAdapter _adapter;
    public static  ArrayList<Food> _favoritefood;
    private Button btaddfood;
    public static int check=0;
=======
    private GridView _gridView;
    private GridViewArrayAdapter _adapter;
>>>>>>> 61149e5d9cee730477597080ab0341ef6cca04b4
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_food);
        check = check + 1;
        if (check == 1)
        {
            loadData();
            loadDataAdd();
            initComponents();
        } else if (check > 1) {
            initComponents();
        }
        btaddfood=(Button)findViewById(R.id.btaddfood);

    }

    public void addfoodClicked(View view) {
        Intent myIntent = new Intent(FavoriteFood.this, AddFood.class);
        FavoriteFood.this.startActivity(myIntent);
    }


    private void loadData() {
<<<<<<< HEAD
        _favoritefood = new ArrayList<>();
        Food f1 = new Food("Cua rang me", R.drawable.doan1);
        Food f2 = new Food( "Món Việt", R.drawable.doan2);
        Food f3 = new Food( "Món Thái", R.drawable.doan3);
        Food f4 = new Food( "Món Nhật", R.drawable.doan4);
        _favoritefood.add(f1);
        _favoritefood.add(f2);
        _favoritefood.add(f3);
        _favoritefood.add(f4);
=======
        MainActivity.profile.favoriteFoods = new ArrayList<>();
        Food f1 = new Food("Món ăn 1", "Cua rang me", R.drawable.doan1);
        Food f2 = new Food("Món ăn 2", "Món Việt", R.drawable.doan2);
        Food f3 = new Food("Món ăn 3", "Món Thái", R.drawable.doan3);
        Food f4 = new Food("Món ăn 4", "Món Nhật", R.drawable.doan4);
        MainActivity.profile.favoriteFoods.add(f1);
        MainActivity.profile.favoriteFoods.add(f2);
        MainActivity.profile.favoriteFoods.add(f3);
        MainActivity.profile.favoriteFoods.add(f4);
>>>>>>> 61149e5d9cee730477597080ab0341ef6cca04b4
    }

    private void initComponents() {
        _gridView = findViewById(R.id.gridview_food);
        _adapter = new GridViewArrayAdapter(this, R.layout.gridview_food_items, MainActivity.profile.favoriteFoods);
        _gridView.setAdapter(_adapter);
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
}










