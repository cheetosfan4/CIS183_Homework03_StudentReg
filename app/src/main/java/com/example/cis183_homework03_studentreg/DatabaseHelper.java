package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "StudentInfo.db";
    private static final String students_table_name = "Students";
    private static final String majors_table_name = "Majors";
    public DatabaseHelper(Context c) {
        super(c, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + students_table_name + " (username integer primary key autoincrement not null, fName varchar(50), lName varchar(50), email varchar(50), age integer, GPA decimal(3, 2), foreign key (major) references " + majors_table_name + " (majorID));");
        db.execSQL("CREATE TABLE " + majors_table_name + " (majorID integer primary key autoincrement not null, majorName varchar(50), majorPrefix varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
