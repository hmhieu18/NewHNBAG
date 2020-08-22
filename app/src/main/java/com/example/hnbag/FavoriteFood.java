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
    public static GridView _gridView;
    public static  GridViewArrayAdapter _adapter;
    public static  ArrayList<Food> _favoritefood;
    private Button btaddfood;
    public static int check=0;

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
        MainActivity.profile.favoriteFoods = new ArrayList<>();
        Food f1 = new Food("Cua rang me", R.drawable.doan1);
        Food f2 = new Food( "Món Việt", R.drawable.doan2);
        Food f3 = new Food( "Món Thái", R.drawable.doan3);
        Food f4 = new Food( "Món Nhật", R.drawable.doan4);
        MainActivity.profile.favoriteFoods.add(f1);
        MainActivity.profile.favoriteFoods.add(f2);
        MainActivity.profile.favoriteFoods.add(f3);
        MainActivity.profile.favoriteFoods.add(f4);
    }

    private void initComponents() {
        _gridView = findViewById(R.id.gridview_food);
        _adapter = new GridViewArrayAdapter(this, R.layout.gridview_food_items, MainActivity.profile.favoriteFoods);
        _gridView.setAdapter(_adapter);
    }


    private void loadDataAdd() {
        AddFood._favoritefoodAdd = new ArrayList<>();
        Food f1 = new Food("Bánh bèo bì", R.drawable.banhbeobi);
        Food f2 = new Food("Bánh canh trảng bàng", R.drawable.banhcanhtrangbang);
        Food f3 = new Food("Bánh dừa nướng", R.drawable.banhduanuong);
        Food f4 = new Food("Bánh Huế", R.drawable.banhhue);


        AddFood._favoritefoodAdd.add(f1);
        AddFood._favoritefoodAdd.add(f2);
        AddFood._favoritefoodAdd.add(f3);
        AddFood._favoritefoodAdd.add(f4);

        Food f5 = new Food("Bánh khọt", R.drawable.banhkhot);
        Food f6 = new Food("Bò bía", R.drawable.bobia);
        Food f7 = new Food("Bánh mì phá lấu", R.drawable.banhmiphalau);
        Food f8 = new Food("Bánh tét Long An", R.drawable.banhtetlongan);


        AddFood._favoritefoodAdd.add(f5);
        AddFood._favoritefoodAdd.add(f6);
        AddFood._favoritefoodAdd.add(f7);
        AddFood._favoritefoodAdd.add(f8);


        Food f9 = new Food("Bánh tráng nướng", R.drawable.banhtrangnuong);
        Food f10 = new Food("Bánh tráng trộn", R.drawable.banhtrangtron);
        Food f11 = new Food("Bánh tráng thịt heo", R.drawable.banhtranhthitheo);
        Food f12 = new Food("Bánh xèo", R.drawable.banhxeo);


        AddFood._favoritefoodAdd.add(f9);
        AddFood._favoritefoodAdd.add(f10);
        AddFood._favoritefoodAdd.add(f11);
        AddFood._favoritefoodAdd.add(f12);


        Food f13 = new Food("Bột chiên", R.drawable.botchien);
        Food f14 = new Food("Bún chả cá", R.drawable.bunchaca);
        Food f30 = new Food("Bún chả cá", R.drawable.bunrieu);
        Food f15 = new Food("Bún tôm", R.drawable.buntom);
        Food f16 = new Food("Cao lầu", R.drawable.caolau);


        AddFood._favoritefoodAdd.add(f13);
        AddFood._favoritefoodAdd.add(f14);
        AddFood._favoritefoodAdd.add(f30);
        AddFood._favoritefoodAdd.add(f15);
        AddFood._favoritefoodAdd.add(f16);

        Food f17 = new Food("Cơm cháy chà bông", R.drawable.comchaychabong);
        Food f18 = new Food("Cơm tấm", R.drawable.comtam);
        Food f19 = new Food("Cháo môn lươn", R.drawable.chaomonluon);
        Food f20 = new Food("Chuối nếp nướng", R.drawable.chuoinepnuong);


        AddFood._favoritefoodAdd.add(f17);
        AddFood._favoritefoodAdd.add(f18);
        AddFood._favoritefoodAdd.add(f19);
        AddFood._favoritefoodAdd.add(f20);

        Food f21 = new Food("Gỏi cuốn", R.drawable.goicuon);
        Food f22 = new Food("Gỏi khô bò", R.drawable.goikhobo);
        Food f23 = new Food("Gỏi lục bình", R.drawable.goilucbinh);
        Food f24 = new Food("Gỏi măng cụt", R.drawable.goimangcut);


        AddFood._favoritefoodAdd.add(f21);
        AddFood._favoritefoodAdd.add(f22);
        AddFood._favoritefoodAdd.add(f23);
        AddFood._favoritefoodAdd.add(f24);


        Food f25 = new Food("Hủ tiếu nam vang", R.drawable.hutieunamvang);
        Food f26 = new Food("Lẩu bò mắm ruốc", R.drawable.laubomamruoc);
        Food f27 = new Food("Mì Quảng", R.drawable.miquang);
        Food f28 = new Food("Nem Lái Thiêu", R.drawable.nemlaithieu);
        Food f29 = new Food("Nem Lái Thiêu", R.drawable.ochup);

        AddFood._favoritefoodAdd.add(f25);
        AddFood._favoritefoodAdd.add(f26);
        AddFood._favoritefoodAdd.add(f27);
        AddFood._favoritefoodAdd.add(f28);
        AddFood._favoritefoodAdd.add(f29);
    }
}










