package com.example.hnbag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button eat;
    private Button drinks;
    private Button favorite;
    private Button photo;
    private Button search;
    private EditText search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    private void initComponent() {
        eat = (Button) findViewById(R.id.eat);
        drinks = (Button) findViewById(R.id.eat);
        favorite = (Button) findViewById(R.id.eat);
        photo = (Button) findViewById(R.id.eat);
        search_bar = (EditText) findViewById(R.id.search_bar);
        search = (Button) findViewById(R.id.search);
    }

    public void searchClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, searchResult.class);
        myIntent.putExtra("key", search_bar.getText()); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void photoClicked(View view) {
    }

    public void groupClicked(View view) {
    }

    public void savedPlaceClicked(View view) {
    }

    public void eatClicked(View view) {

    }
}