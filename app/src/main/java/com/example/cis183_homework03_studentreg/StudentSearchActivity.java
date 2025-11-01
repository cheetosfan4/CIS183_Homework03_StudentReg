package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentSearchActivity extends AppCompatActivity {
    //for searching through students using filters

    DatabaseHelper dbHelper;
    Button btn_j_back;
    Button btn_j_search;
    EditText et_j_username;
    EditText et_j_email;
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_minGPA;
    EditText et_j_maxGPA;
    EditText et_j_age;
    Spinner spn_j_major;
    ListView lv_j_results;
    Intent mainActivity;
    Intent cameFrom;
    ArrayList<Major> majorList;
    ArrayList<Student> studentList;
    ArrayList<Student> results;
    MajorSpinnerAdapter msAdapter;
    StudentSearchAdapter ssAdapter;
    Major selectedMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cameFrom = getIntent();

        if(cameFrom.getSerializableExtra("majorList") != null) {
            majorList = (ArrayList<Major>) cameFrom.getSerializableExtra("majorList");
        }
        else {
            majorList = new ArrayList<>();
        }
        if(cameFrom.getSerializableExtra("studentList") != null) {
            studentList = (ArrayList<Student>) cameFrom.getSerializableExtra("studentList");
        }
        else {
            studentList = new ArrayList<>();
        }

        dbHelper = new DatabaseHelper(this);



        btn_j_back = findViewById(R.id.btn_v_studentSearch_back);
        btn_j_search = findViewById(R.id.btn_v_studentSearch_search);
        et_j_username = findViewById(R.id.et_v_studentSearch_username);
        et_j_email = findViewById(R.id.et_v_studentSearch_email);
        et_j_fName = findViewById(R.id.et_v_studentSearch_fName);
        et_j_lName = findViewById(R.id.et_v_studentSearch_lName);
        et_j_minGPA = findViewById(R.id.et_v_studentSearch_minGPA);
        et_j_maxGPA = findViewById(R.id.et_v_studentSearch_maxGPA);
        et_j_age = findViewById(R.id.et_v_studentSearch_age);
        spn_j_major = findViewById(R.id.spn_v_studentSearch_major);
        lv_j_results = findViewById(R.id.lv_v_studentSearch_results);

        mainActivity = new Intent(StudentSearchActivity.this, MainActivity.class);

        ssAdapter = new StudentSearchAdapter(this, studentList);
        lv_j_results.setAdapter(ssAdapter);

        msAdapter = new MajorSpinnerAdapter(this, majorList);
        spn_j_major.setAdapter(msAdapter);
        selectedMajor = null;

        buttonListener();
        spinnerListener();
    }

    private void buttonListener() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivity);
            }
        });
        btn_j_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void spinnerListener() {
        spn_j_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedMajor = null;
                }
                else {
                    selectedMajor = majorList.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMajor = null;
            }
        });
    }

    private void search() {
        String usernameFilter = et_j_username.getText().toString();
        String fNameFilter = et_j_fName.getText().toString();
        String lNameFilter = et_j_lName.getText().toString();
        String emailFilter = et_j_email.getText().toString();

        int ageFilter;
        if (et_j_age.getText().toString().isEmpty()) {
            ageFilter = 999;
        }
        else {
            ageFilter = Integer.parseInt(et_j_age.getText().toString());
        }

        double minGPA;
        if (et_j_minGPA.getText().toString().isEmpty()) {
            minGPA = 999;
        }
        else {
            minGPA = Double.parseDouble(et_j_minGPA.getText().toString());
        }
        double maxGPA;
        if (et_j_maxGPA.getText().toString().isEmpty()) {
            maxGPA = 999;
        }
        else {
            maxGPA = Double.parseDouble(et_j_maxGPA.getText().toString());
        }

        Major major = (Major) spn_j_major.getSelectedItem();
        int majorFilter;
        if (major == null) {
            majorFilter = 999;
        }
        else {
            majorFilter = major.getID();
        }

        results = dbHelper.filterStudents(usernameFilter, fNameFilter, lNameFilter, emailFilter, ageFilter, minGPA, maxGPA, majorFilter, majorList);
        ssAdapter.update(results);
    }
}