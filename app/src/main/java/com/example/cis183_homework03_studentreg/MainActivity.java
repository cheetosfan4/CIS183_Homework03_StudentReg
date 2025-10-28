package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//for viewing all current students
    View main;
    TextView tv_j_title;
    ListView lv_j_studentList;
    Button btn_j_addStudent;
    Button btn_j_studentDetails;
    Button btn_j_addMajor;
    Button btn_j_studentSearch;

    Intent addStudentActivity;
    Intent studentDetailsActivity;
    Intent addMajorActivity;
    Intent studentSearchActivity;

    private ArrayList<Student> studentList;
    StudentListAdapter slAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main = findViewById(R.id.main);
        tv_j_title = findViewById(R.id.tv_v_main_title);
        lv_j_studentList = findViewById(R.id.lv_v_main_studentList);
        btn_j_addStudent = findViewById(R.id.btn_v_main_addStudent);
        btn_j_studentDetails = findViewById(R.id.btn_v_main_studentDetails);
        btn_j_addMajor = findViewById(R.id.btn_v_main_addMajor);
        btn_j_studentSearch = findViewById(R.id.btn_v_main_studentSearch);

        studentList = new ArrayList<>();

        addStudentActivity = new Intent(MainActivity.this, AddStudentActivity.class);
        studentDetailsActivity = new Intent(MainActivity.this, StudentDetailsActivity.class);
        addMajorActivity = new Intent(MainActivity.this, AddMajorActivity.class);
        studentSearchActivity = new Intent(MainActivity.this, StudentSearchActivity.class);

        setListeners();
    }

    //call this after arraylist has data in it already
    private void fillListView() {
        slAdapter = new StudentListAdapter(this, studentList);
        lv_j_studentList.setAdapter(slAdapter);
    }

    private void setListeners() {
        btn_j_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addStudentActivity);
            }
        });
        btn_j_studentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(studentDetailsActivity);
            }
        });
        btn_j_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addMajorActivity);
            }
        });
        btn_j_studentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(studentSearchActivity);
            }
        });
    }
}