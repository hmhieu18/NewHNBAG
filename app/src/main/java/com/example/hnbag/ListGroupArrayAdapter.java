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

import java.util.List;

public class ListGroupArrayAdapter extends ArrayAdapter<Group> {
    private Context _context;
    private int _layoutID;
    private List<Group> _items;


    public ListGroupArrayAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects) {
        super(context, resource, objects);
        _context = context;
        _layoutID = resource;
        _items = objects;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    public Group getItem(int position) {
        return _items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutID, null, false);
        }

        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView textView = convertView.findViewById(R.id.place_name);
        TextView textViewSub = convertView.findViewById(R.id.address);
        TextView textViewRating = convertView.findViewById(R.id.rating);
        final Group group = _items.get(position);
        Bitmap bmp = BitmapFactory.decodeResource(_context.getResources(), R.drawable.group);
        imageView.setImageBitmap(bmp);
        textView.setText("Group:" + group.getGroupName());
        textViewSub.setText(group.getUsernamesInString());
        textViewRating.setText("");
        return convertView;
    }

}
