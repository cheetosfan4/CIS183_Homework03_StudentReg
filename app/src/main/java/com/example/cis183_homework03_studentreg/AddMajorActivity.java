package com.example.cis183_homework03_studentreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddMajorActivity extends AppCompatActivity {
    //for adding new majors

    DatabaseHelper dbHelper;
    Button btn_j_back;
    Button btn_j_add;
    EditText et_j_name;
    EditText et_j_prefix;
    TextView tv_j_error;
    Intent mainActivity;
    Intent cameFrom;
    ArrayList<Major> majorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_major);
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

        btn_j_back = findViewById(R.id.btn_v_addMajor_back);
        btn_j_add = findViewById(R.id.btn_v_addMajor_add);
        et_j_name = findViewById(R.id.et_v_addMajor_name);
        et_j_prefix = findViewById(R.id.et_v_addMajor_prefix);
        tv_j_error = findViewById(R.id.tv_v_addMajor_error);

        mainActivity = new Intent(AddMajorActivity.this, MainActivity.class);
        dbHelper = new DatabaseHelper(this);

        buttonListener();
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
                createMajor();

            }
        });
    }

    private void createMajor() {
        if (et_j_name.getText().toString().isEmpty() || et_j_prefix.getText().toString().isEmpty()) {
            tv_j_error.setVisibility(TextView.VISIBLE);
            tv_j_error.setText("All fields must be filled out!");
        }
        else if (dbHelper.majorTableContains(et_j_name.getText().toString()) || dbHelper.majorTableContains(et_j_prefix.getText().toString())) {
            tv_j_error.setVisibility(TextView.VISIBLE);
            tv_j_error.setText("Cannot have duplicate names or prefixes!");
        }
        else {
            tv_j_error.setVisibility(TextView.INVISIBLE);
            Major major = new Major(
                    et_j_name.getText().toString(),
                    et_j_prefix.getText().toString()
            );
            dbHelper.addMajorToDatabase(major);
            et_j_name.setText("");
            et_j_prefix.setText("");
        }
    }
}