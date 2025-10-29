package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MajorSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Major> majorList;

    public MajorSpinnerAdapter(Context c, ArrayList<Major> ml) {
        context = c;
        majorList = ml;
    }

    @Override
    public int getCount() {
        return majorList.size();
    }

    @Override
    public Object getItem(int position) {
        return majorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.major_cell, null);
        }

        TextView tv_j_name = convertView.findViewById(R.id.tv_v_majorCell_name);
        TextView tv_j_prefix = convertView.findViewById(R.id.tv_v_majorCell_prefix);

        Major major = majorList.get(position);
        tv_j_name.setText(major.getName());
        tv_j_prefix.setText(major.getPrefix());

        return convertView;
    }
}
