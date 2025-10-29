package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> studentList;

    public StudentListAdapter(Context c, ArrayList<Student> sl) {
        context = c;
        studentList = sl;
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

        TextView tv_j_fName = convertView.findViewById(R.id.tv_v_studentCell_fName);
        TextView tv_j_lName = convertView.findViewById(R.id.tv_v_studentCell_lName);
        TextView tv_j_username = convertView.findViewById(R.id.tv_v_studentCell_username);

        Student student = studentList.get(position);
        tv_j_fName.setText("First Name: " + student.getFirstName());
        tv_j_lName.setText("Last Name: " + student.getLastName());
        tv_j_username.setText("Username: " + student.getUsername());

        return convertView;
    }
}
