package com.example.hnbag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class RandomFood extends AppCompatActivity {
    private ImageView imageView;
    private Button random;
    private Button find;
    private TextView desciption;
    private ArrayList<Food> foodArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_food);
        initComponent();
    }

    private void initComponent() {
        imageView = findViewById(R.id.foodimg);
        desciption = findViewById(R.id.dishname);
        random = findViewById(R.id.randombutton);
        find = findViewById(R.id.randomfindplace);
        foodArrayList = MainActivity.mProfile.favoriteFoods;
    }

    public void randomFoocClicked(View view) {
        random.setEnabled(false);
        desciption.setText("Please wait...");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < foodArrayList.size(); j++) {
                Random rn = new Random();
                int range = foodArrayList.size() + 1;
                int randomNum = rn.nextInt(range);
                if (i == 2 && j == randomNum) {
                    showFood(foodArrayList.get(randomNum));
                    break;
                }
            }
        random.setEnabled(true);
    }

    private void showFood(Food food) {
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), food.getLogoID());
        Bitmap cropImg;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        if (width > height) {
            int crop = (width - height) / 2;
            cropImg = Bitmap.createBitmap(bmp, crop, 0, height, height);
        } else {
            int crop = (height - width) / 2;
            cropImg = Bitmap.createBitmap(bmp, crop, 0, width, width);
        }

        imageView.setImageBitmap(cropImg);
        desciption.setText(food.getDescription());
    }

    public void randomFindPlaceClicked(View view) {
        if (desciption.getText() != "") {
            Intent myIntent = new Intent(RandomFood.this, searchResult.class);
            myIntent.putExtra("query", String.valueOf(desciption.getText()));
            RandomFood.this.startActivity(myIntent);
        }
    }

}