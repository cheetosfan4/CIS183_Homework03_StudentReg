package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.database.DatabaseUtils;
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
        db.execSQL("CREATE TABLE " + students_table_name + " (username varchar(50) primary key not null, fName varchar(50), lName varchar(50), email varchar(50), age integer, GPA decimal(3, 2), foreign key (major) references " + majors_table_name + " (majorID));");
        db.execSQL("CREATE TABLE " + majors_table_name + " (majorID integer primary key autoincrement not null, majorName varchar(50), majorPrefix varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + students_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + majors_table_name + ";");

        onCreate(db);
    }

    public void dummyData() {
        dummyMajors();
        dummyStudents();
    }

    private void dummyMajors() {
        if (countRecordsFromTable(majors_table_name) == 0) {
            SQLiteDatabase db = this.getReadableDatabase();

            //starts incrementing majorID at 1, not at 0
            db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('Computer Information Systems', 'CIS');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('Electrical Engineering Technology', 'ELEC');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('Fine Arts', 'ART');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('Nuclear Engineering Technology', 'NUET');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('Welding Technology', 'WELD');");

            db.close();
        }
    }
    private void dummyStudents() {
        if (countRecordsFromTable(students_table_name) == 0) {
            SQLiteDatabase db = this.getReadableDatabase();

            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, major) VALUES ('chimchimbooboo13', 'ChimChim', 'BooBoo', 'chimchimbooboo@college.edu', 18, 2.15, 1);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, major) VALUES ('ILikeFrog12', 'Henrick', 'Ventylza', 'henrickventylza@college.edu', 17, 4.00, 2);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, major) VALUES ('chicken4', 'Aleck', 'Phubrint', 'aleckphubrint@college.edu', 20, 3.51, 3);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, major) VALUES ('HelloSchool', 'Ghessil', 'Nuprento', 'ghessilnuprento@college.edu', 17, 3.39, 4);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, major) VALUES ('yarnfriend3', 'Janella', 'Kumplinski', 'janellakumplinski@college.edu', 19, 3.77, 5);");

            db.close();
        }
    }

    private int countRecordsFromTable (String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int rows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        db.close();
        return rows;
    }

    public String getStudentsTableName() {
        return students_table_name;
    }
    public String getMajorsTableName() {
        return majors_table_name;
    }
}