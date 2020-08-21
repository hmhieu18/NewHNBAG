package com.example.hnbag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
        (FavoriteFood._favoritefood).add(s);
        _favoritefoodAdd.remove(i);
        (_adapterAdd).notifyDataSetChanged();
    }
}