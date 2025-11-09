//====================================================================================================
//Author        :       Marc McLennan
//Date          :       11-09-2025
//Description   :       CIS183 Homework #3, Program #1; Student Registry System
//====================================================================================================

package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    DatabaseHelper dbHelper;
    TextView tv_j_title;
    ListView lv_j_studentList;
    Button btn_j_addStudent;
    Button btn_j_addMajor;
    Button btn_j_studentSearch;

    Intent addStudentActivity;
    Intent studentDetailsActivity;
    Intent addMajorActivity;
    Intent studentSearchActivity;

    private ArrayList<Student> studentList;
    private ArrayList<Major> majorList;
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
        btn_j_addMajor = findViewById(R.id.btn_v_main_addMajor);
        btn_j_studentSearch = findViewById(R.id.btn_v_main_studentSearch);

        dbHelper = new DatabaseHelper(this);
        //gives both the students table and majors table starting data
        //but, only if they're initially empty
        dbHelper.dummyData();

        studentList = new ArrayList<>();
        majorList = new ArrayList<>();

        //adds all of the data currently in the database to the arraylists
        majorList = dbHelper.populateMajorList();
        studentList = dbHelper.populateStudentList(majorList);

        addStudentActivity = new Intent(MainActivity.this, AddStudentActivity.class);
        studentDetailsActivity = new Intent(MainActivity.this, StudentDetailsActivity.class);
        addMajorActivity = new Intent(MainActivity.this, AddMajorActivity.class);
        studentSearchActivity = new Intent(MainActivity.this, StudentSearchActivity.class);

        slAdapter = new StudentListAdapter(this, studentList);
        lv_j_studentList.setAdapter(slAdapter);

        setListeners();
    }

    private void setListeners() {
        btn_j_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudentActivity.putExtra("majorList", majorList);
                startActivity(addStudentActivity);
            }
        });
        btn_j_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMajorActivity.putExtra("majorList", majorList);
                startActivity(addMajorActivity);
            }
        });
        btn_j_studentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentSearchActivity.putExtra("majorList", majorList);
                startActivity(studentSearchActivity);
            }
        });
        lv_j_studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studentDetailsActivity.putExtra("majorList", majorList);
                studentDetailsActivity.putExtra("student", studentList.get(position));
                startActivity(studentDetailsActivity);
            }
        });
        lv_j_studentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbHelper.deleteStudentFromDatabase(studentList.get(position));
                studentList.remove(position);
                slAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}