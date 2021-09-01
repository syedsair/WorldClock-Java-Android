package com.example.clock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class Main2_Adapter extends ArrayAdapter<Country> {
    private Context mContext;
    private Integer mResource;
    public Main2_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Country> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String Name = getItem(position).Name;

        String time = getItem(position).Time;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView);
        TextView tvTime = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(Name);
        tvTime.setText(time);
        return convertView;
    }
}
