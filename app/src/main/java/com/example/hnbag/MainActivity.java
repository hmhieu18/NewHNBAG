package com.example.hnbag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button eat;
    private Button drinks;
    private Button favorite;
    private Button photo;
    private Button search;
    private EditText search_bar;
    private ImageView imageView;
    public static mProfile mProfile = new mProfile();

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


        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchClicked(search_bar);
                    return true;
                }
                return false;
            }
        });
        (new FavoritePlaces.updateFavoritePlacesFromDatabase()).execute();
    }

    public void searchClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, searchResult.class);
        myIntent.putExtra("query", String.valueOf(search_bar.getText()));
        MainActivity.this.startActivity(myIntent);
    }

    public void randomClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, RandomFood.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void groupClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, GroupDisplay.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void savedPlaceClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, FavoritePlaces.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void eatClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, FavoriteFood.class);
        MainActivity.this.startActivity(myIntent);
    }
    public void predictClicked(View view) {
        Intent myIntent = new Intent(MainActivity.this, Prediction.class);
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}