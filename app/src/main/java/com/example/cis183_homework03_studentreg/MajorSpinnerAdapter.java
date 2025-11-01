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
    ArrayList<Major> spinnerMajorList;

    public MajorSpinnerAdapter(Context c, ArrayList<Major> ml) {
        context = c;
        majorList = ml;
        spinnerMajorList = new ArrayList<>();

        //creates new arraylist based on list of current majors
        //then, appends a null major to position 0
        //this is so the original majorlist is not changed
        //and, the user is able to "deselect" majors by selecting the blank option
        spinnerMajorList.addAll(majorList);
        Major blank = null;
        spinnerMajorList.add(0, blank);
    }

    @Override
    public int getCount() {
        return spinnerMajorList.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerMajorList.get(position);
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

        Major major;
        major = spinnerMajorList.get(position);

        if(major != null) {
            tv_j_name.setText(major.getName());
            tv_j_prefix.setText(major.getPrefix());
        }
        else {
            tv_j_name.setText("");
            tv_j_prefix.setText("");
        }

        return convertView;
    }
}
