package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentDetailsActivity extends AppCompatActivity {
    //for viewing and editing the details of a specific student
    //admin cannot edit primary key

    DatabaseHelper dbHelper;
    Button btn_j_back;
    Button btn_j_update;
    EditText et_j_username;
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_email;
    EditText et_j_age;
    EditText et_j_GPA;
    Spinner spn_j_major;

    Intent mainActivity;
    Intent cameFrom;
    MajorSpinnerAdapter msAdapter;
    ArrayList<Major> majorList;
    Student student;
    int majorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_details);
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
        //same as above, but gets the student sent from the main activity
        if(cameFrom.getSerializableExtra("student") != null) {
            student = (Student) cameFrom.getSerializableExtra("student");
        }
        else {
            student = new Student();
        }

        btn_j_back = findViewById(R.id.btn_v_studentDetails_back);
        btn_j_update = findViewById(R.id.btn_v_studentDetails_update);
        et_j_username = findViewById(R.id.et_v_studentDetails_username);
        et_j_fName = findViewById(R.id.et_v_studentDetails_fName);
        et_j_lName = findViewById(R.id.et_v_studentDetails_lName);
        et_j_email = findViewById(R.id.et_v_studentDetails_email);
        et_j_age = findViewById(R.id.et_v_studentDetails_age);
        et_j_GPA = findViewById(R.id.et_v_studentDetails_GPA);
        spn_j_major = findViewById(R.id.spn_v_studentDetails_major);

        mainActivity = new Intent(StudentDetailsActivity.this, MainActivity.class);
        dbHelper = new DatabaseHelper(this);

        msAdapter = new MajorSpinnerAdapter(this, majorList);
        spn_j_major.setAdapter(msAdapter);

        et_j_username.setHint(student.getUsername());
        et_j_fName.setText(student.getFirstName());
        et_j_lName.setText(student.getLastName());
        et_j_email.setText(student.getEmail());
        et_j_age.setText(Integer.toString(student.getAge()));
        et_j_GPA.setText(Double.toString(student.getGPA()));

        Major major = majorList.get(0);
        for (int i = 0; i < majorList.size(); i++) {
            if (majorList.get(i).getID() == student.getMajor().getID()) {
                spn_j_major.setSelection(i);
                break;
            }
        }

        majorID = 0;
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
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major major = majorList.get(0);
                for (int i = 0; i < majorList.size(); i++) {
                    if (majorList.get(i).getID() == majorID) {
                        major = majorList.get(i);
                        break;
                    }
                }
                student.setFirstName(et_j_fName.getText().toString());
                student.setLastName(et_j_lName.getText().toString());
                student.setEmail(et_j_email.getText().toString());
                student.setAge(Integer.parseInt(et_j_age.getText().toString()));
                student.setGPA(Double.parseDouble(et_j_GPA.getText().toString()));
                student.setMajor(major);

                dbHelper.editStudentDetails(student);
            }
        });
    }

    private void spinnerListener() {
        spn_j_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorID = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorID = 0;
            }
        });
    }
}