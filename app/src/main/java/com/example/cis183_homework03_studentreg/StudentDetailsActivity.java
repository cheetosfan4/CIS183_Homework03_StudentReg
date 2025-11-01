package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    TextView tv_j_error;
    EditText et_j_username;
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_email;
    EditText et_j_age;
    EditText et_j_GPA;
    Spinner spn_j_major;

    Intent mainActivity;
    Intent studentSearchActivity;
    Intent returnToPrevious;
    Intent cameFrom;
    MajorSpinnerAdapter msAdapter;
    ArrayList<Major> majorList;
    Student student;
    Major selectedMajor;

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

        mainActivity = new Intent(StudentDetailsActivity.this, MainActivity.class);
        studentSearchActivity = new Intent(StudentDetailsActivity.this, StudentSearchActivity.class);
        if (cameFrom.getSerializableExtra("starter") != null) {
            returnToPrevious = studentSearchActivity;
        }
        else {
            returnToPrevious = mainActivity;
        }

        btn_j_back = findViewById(R.id.btn_v_studentDetails_back);
        btn_j_update = findViewById(R.id.btn_v_studentDetails_update);
        tv_j_error = findViewById(R.id.tv_v_studentDetails_error);
        et_j_username = findViewById(R.id.et_v_studentDetails_username);
        et_j_fName = findViewById(R.id.et_v_studentDetails_fName);
        et_j_lName = findViewById(R.id.et_v_studentDetails_lName);
        et_j_email = findViewById(R.id.et_v_studentDetails_email);
        et_j_age = findViewById(R.id.et_v_studentDetails_age);
        et_j_GPA = findViewById(R.id.et_v_studentDetails_GPA);
        spn_j_major = findViewById(R.id.spn_v_studentDetails_major);


        dbHelper = new DatabaseHelper(this);

        msAdapter = new MajorSpinnerAdapter(this, majorList);
        spn_j_major.setAdapter(msAdapter);

        et_j_username.setHint(student.getUsername());
        et_j_fName.setText(student.getFirstName());
        et_j_lName.setText(student.getLastName());
        et_j_email.setText(student.getEmail());
        et_j_age.setText(Integer.toString(student.getAge()));
        et_j_GPA.setText(Double.toString(student.getGPA()));

        selectedMajor = null;
        for (int i = 0; i < majorList.size(); i++) {
            if (majorList.get(i).getID() == student.getMajor().getID()) {
                selectedMajor = majorList.get(i);
                spn_j_major.setSelection(i + 1);
                break;
            }
        }

        buttonListener();
        spinnerListener();
    }

    private void buttonListener() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious.putExtra("majorList", majorList);
                startActivity(returnToPrevious);
            }
        });
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStudent();
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

    private void editStudent() {
        String username = et_j_username.getText().toString();
        String fName = et_j_fName.getText().toString();
        String lName = et_j_lName.getText().toString();
        String email = et_j_email.getText().toString();
        String ageS = et_j_age.getText().toString();
        String GPAS = et_j_GPA.getText().toString();

        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || ageS.isEmpty() || GPAS.isEmpty() || selectedMajor == null) {
            tv_j_error.setVisibility(TextView.VISIBLE);
            tv_j_error.setText("All fields must be filled out!");
        }
        else {
            tv_j_error.setVisibility(TextView.INVISIBLE);

            student.setFirstName(fName);
            student.setLastName(lName);
            student.setEmail(email);
            student.setAge(Integer.parseInt(ageS));
            student.setGPA(Double.parseDouble(GPAS));
            student.setMajor(selectedMajor);

            dbHelper.editStudentDetails(student);
        }
    }
}