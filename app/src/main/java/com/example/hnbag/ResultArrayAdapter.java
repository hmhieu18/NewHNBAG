package com.example.hnbag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ResultArrayAdapter extends ArrayAdapter<Root.Results> {
    private Context _context;
    private int _layoutID;
    private List<Root.Results> _items;


    public ResultArrayAdapter(@NonNull Context context, int resource, @NonNull List<Root.Results> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    public Root.Results getItem(int position) {
        return _items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView place_name = convertView.findViewById(R.id.place_name);
        TextView address = convertView.findViewById(R.id.address);
        TextView rating = convertView.findViewById(R.id.rating);
        final Root.Results result = _items.get(position);
        Picasso.get().load(result.getIcon()).into(icon);
        place_name.setText(result.getName());
        address.setText(result.getFormatted_address());
        rating.setText(result.getRating());
        return convertView;
    }
}
