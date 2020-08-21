package com.example.hnbag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FavoriteFood extends AppCompatActivity {
    private GridView _gridView;
    private GridViewArrayAdapter _adapter;
    private ArrayList<Food> _favoritefood;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_food);
        loadData();
        initComponents();
    }


    private void loadData() {
        _favoritefood = new ArrayList<>();
        Food f1 = new Food("Món ăn 1", "Cua rang me", R.drawable.doan1);
        Food f2 = new Food("Món ăn 2", "Món Việt", R.drawable.doan2);
        Food f3 = new Food("Món ăn 3", "Món Thái", R.drawable.doan3);
        Food f4 = new Food("Món ăn 4", "Món Nhật", R.drawable.doan4);
        _favoritefood.add(f1);
        _favoritefood.add(f2);
        _favoritefood.add(f3);
        _favoritefood.add(f4);
    }

    private void initComponents() {
        _gridView = findViewById(R.id.gridview_food);
        _adapter = new GridViewArrayAdapter(this, R.layout.gridview_food_items, _favoritefood);
        _gridView.setAdapter(_adapter);
    }
}










