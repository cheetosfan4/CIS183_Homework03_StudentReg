package com.example.cis183_homework03_studentreg;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "StudentInfo.db";
    private static final String students_table_name = "Students";
    private static final String majors_table_name = "Majors";
    public DatabaseHelper(Context c) {
        super(c, database_name, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + students_table_name + " (username varchar(50) primary key not null, fName varchar(50), lName varchar(50), email varchar(50), age integer, GPA decimal(3, 2), majorID integer, foreign key (majorID) references " + majors_table_name + " (majorID));");
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

            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES ('chimchimbooboo13', 'ChimChim', 'BooBoo', 'chimchimbooboo@college.edu', 18, 2.15, 1);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES ('ILikeFrog12', 'Henrick', 'Ventylza', 'henrickventylza@college.edu', 17, 4.00, 2);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES ('chicken4', 'Aleck', 'Phubrint', 'aleckphubrint@college.edu', 20, 3.51, 3);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES ('HelloSchool', 'Ghessil', 'Nuprento', 'ghessilnuprento@college.edu', 17, 3.39, 4);");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES ('yarnfriend3', 'Janella', 'Kumplinski', 'janellakumplinski@college.edu', 19, 3.77, 5);");

            db.close();
        }
    }

    private int countRecordsFromTable (String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int rows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        db.close();
        return rows;
    }

    public ArrayList<Major> populateMajorList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + majors_table_name;
        //selects all of the majors table
        Cursor cursor = db.rawQuery(selectAll, null);

        int rows = (int) DatabaseUtils.queryNumEntries(db, majors_table_name);
        ArrayList<Major> majorList = new ArrayList<>();

        //moves cursor to first row of table
        //if there are no rows in the table, this returns false, skipping the if statement
        if (cursor.moveToFirst()) {
            //reads all information from the row and adds it to the list
            do {
                Major major = new Major();
                major.setID(cursor.getInt(0));
                major.setName(cursor.getString(1));
                major.setPrefix(cursor.getString(2));

                majorList.add(major);
            }
            //only continues the loop if there is another row in the table
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return majorList;
    }

    //mostly the same as the above function, but for students
    public ArrayList<Student> populateStudentList(ArrayList<Major> majorList) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + students_table_name;
        Cursor cursor = db.rawQuery(selectAll, null);

        int rows = (int) DatabaseUtils.queryNumEntries(db, students_table_name);
        ArrayList<Student> studentList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int majorID;
                Student student = new Student();
                student.setUsername(cursor.getString(0));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                student.setAge(cursor.getInt(4));
                student.setGPA(cursor.getDouble(5));
                /*
                //1 is subtracted because the majorID increments starting at 1, but the array starts at 0
                majorID = cursor.getInt(6) - 1;
                //uses the major list to give the student the major object
                //based on the student's major id in the table
                student.setMajor(majorList.get(majorID));
                */
                //fixed version of above code, in case a major is removed
                //this would create a gap in the majorIDs
                majorID = cursor.getInt(6);
                for (int i = 0; i < majorList.size(); i++) {
                    if (majorList.get(i).getID() == majorID) {
                        student.setMajor(majorList.get(i));
                        break;
                    }
                }


                studentList.add(student);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    public void addStudentToDatabase(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        String username = student.getUsername();
        String fName = student.getFirstName();
        String lName = student.getLastName();
        String email = student.getEmail();
        int age = student.getAge();
        double GPA = student.getGPA();
        int majorID = student.getMajor().getID();

        String information = "'" + username + "', '" + fName + "', '" + lName + "', '" + email + "', " + age + ", " + GPA + ", " + majorID;
        db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, GPA, majorID) VALUES (" + information + ");");
        db.close();
    }

    public void addMajorToDatabase(Major major) {
        SQLiteDatabase db = this.getWritableDatabase();
        String majorName = major.getName();
        String majorPrefix = major.getPrefix();

        String information = "'" + majorName + "', '" + majorPrefix + "'";
        db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES (" + information + ");");
        db.close();
    }

    public String getStudentsTableName() {
        return students_table_name;
    }
    public String getMajorsTableName() {
        return majors_table_name;
    }

    public void deleteStudentFromDatabase(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + students_table_name + " WHERE username = '" + student.getUsername() + "';");
        db.close();
    }

    public void editStudentDetails(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        String username = student.getUsername();
        String fName = student.getFirstName();
        String lName = student.getLastName();
        String email = student.getEmail();
        int age = student.getAge();
        double GPA = student.getGPA();
        int majorID = student.getMajor().getID();

        String information = "fName = '" + fName + "', lName = '" + lName + "', email = '" + email + "', age = " + age + ", GPA = " + GPA + ", majorID = " + majorID;
        db.execSQL("UPDATE " + students_table_name + " SET " + information + " WHERE username = '" + student.getUsername() + "';");
        db.close();
    }
}