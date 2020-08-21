package com.example.hnbag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hnbag.Food;

import java.util.ArrayList;
import java.util.List;

public class GridViewArrayAdapter extends ArrayAdapter<Food> {
    private Context _context;
    private int _layoutID;
    private ArrayList<Food> _food;

    public GridViewArrayAdapter(@NonNull Context context, int resource,
                                @NonNull List<Food> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _food = (ArrayList<Food>) objects;
    }

    @Override
    public int getCount() {
        return _food.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        ImageView imageView = convertView.findViewById(R.id.img_logo);
        TextView textView = convertView.findViewById(R.id.txt_name);

        Food food = _food.get(position);
        Bitmap bmp = BitmapFactory.decodeResource(_context.getResources(), food.getLogoID());
        imageView.setImageBitmap(bmp);
        textView.setText(food.getDescription());

        return convertView;
    }
}
