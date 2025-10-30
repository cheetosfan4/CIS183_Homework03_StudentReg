package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentSearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> studentList;

    public StudentSearchAdapter(Context c, ArrayList<Student> sl) {
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

    public void update(ArrayList<Student> results) {
        studentList = results;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.student_cell_detailed, null);
        }

        TextView tv_j_username = convertView.findViewById(R.id.tv_v_studentCellDetailed_username);
        TextView tv_j_email = convertView.findViewById(R.id.tv_v_studentCellDetailed_email);
        TextView tv_j_fName = convertView.findViewById(R.id.tv_v_studentCellDetailed_fName);
        TextView tv_j_lName = convertView.findViewById(R.id.tv_v_studentCellDetailed_lName);
        TextView tv_j_age = convertView.findViewById(R.id.tv_v_studentCellDetailed_age);
        TextView tv_j_GPA = convertView.findViewById(R.id.tv_v_studentCellDetailed_GPA);
        TextView tv_j_majorPrefix = convertView.findViewById(R.id.tv_v_studentCellDetailed_majorPrefix);
        TextView tv_j_majorName = convertView.findViewById(R.id.tv_v_studentCellDetailed_majorName);

        Student student = studentList.get(position);
        tv_j_username.setText(student.getUsername());
        tv_j_email.setText(student.getEmail());
        tv_j_fName.setText(student.getFirstName());
        tv_j_lName.setText(student.getLastName());
        tv_j_age.setText(Integer.toString(student.getAge()));
        tv_j_GPA.setText(Double.toString(student.getGPA()));
        tv_j_majorPrefix.setText(student.getMajor().getPrefix());
        tv_j_majorName.setText(student.getMajor().getName());

        return convertView;
    }
}
