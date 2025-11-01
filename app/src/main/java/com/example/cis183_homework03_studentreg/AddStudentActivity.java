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

public class AddStudentActivity extends AppCompatActivity {
    //for adding new students

    DatabaseHelper dbHelper;
    Button btn_j_back;
    Button btn_j_add;
    TextView tv_j_error;
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
    Major selectedMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cameFrom = getIntent();

        //receives majorList from the main activity
        if(cameFrom.getSerializableExtra("majorList") != null) {
            majorList = (ArrayList<Major>) cameFrom.getSerializableExtra("majorList");
        }
        //if it is somehow null or not received majorList is initialized, but is empty
        else {
            majorList = new ArrayList<>();
        }

        btn_j_back = findViewById(R.id.btn_v_addStudent_back);
        btn_j_add = findViewById(R.id.btn_v_addStudent_add);
        tv_j_error = findViewById(R.id.tv_v_addStudent_error);
        et_j_username = findViewById(R.id.et_v_addStudent_username);
        et_j_fName = findViewById(R.id.et_v_addStudent_fName);
        et_j_lName = findViewById(R.id.et_v_addStudent_lName);
        et_j_email = findViewById(R.id.et_v_addStudent_email);
        et_j_age = findViewById(R.id.et_v_addStudent_age);
        et_j_GPA = findViewById(R.id.et_v_addStudent_GPA);
        spn_j_major = findViewById(R.id.spn_v_addStudent_major);

        mainActivity = new Intent(AddStudentActivity.this, MainActivity.class);
        dbHelper = new DatabaseHelper(this);

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
        btn_j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
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

    private void addStudent() {
        String username = et_j_username.getText().toString();
        String fName = et_j_fName.getText().toString();
        String lName = et_j_lName.getText().toString();
        String email = et_j_email.getText().toString();
        String ageS = et_j_age.getText().toString();
        String GPAS = et_j_GPA.getText().toString();

        if (username.isEmpty() || fName.isEmpty() || lName.isEmpty() || email.isEmpty() || ageS.isEmpty() || GPAS.isEmpty() || selectedMajor == null) {
            tv_j_error.setVisibility(TextView.VISIBLE);
            tv_j_error.setText("All fields must be filled out!");
        }
        else if (dbHelper.studentTableContains(username)) {
            tv_j_error.setVisibility(TextView.VISIBLE);
            tv_j_error.setText("Cannot have duplicate usernames!");
        }
        else {
            tv_j_error.setVisibility(TextView.INVISIBLE);

            Student student = new Student(username, fName, lName, email, Integer.parseInt(ageS), Double.parseDouble(GPAS), selectedMajor);
            dbHelper.addStudentToDatabase(student);
            et_j_username.setText("");
            et_j_fName.setText("");
            et_j_lName.setText("");
            et_j_email.setText("");
            et_j_age.setText("");
            et_j_GPA.setText("");
            spn_j_major.setSelection(0);
        }
    }
}