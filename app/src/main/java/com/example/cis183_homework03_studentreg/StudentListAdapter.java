package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> studentList;

    public StudentListAdapter(Context c, ArrayList<Student> s) {
        context = c;
        studentList = s;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.student_cell, null);
        }

        //reference gui elements from cell here

        return convertView;
    }
}
