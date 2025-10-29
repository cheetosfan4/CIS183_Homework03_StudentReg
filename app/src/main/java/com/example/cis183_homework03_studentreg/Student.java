package com.example.cis183_homework03_studentreg;

import java.io.Serializable;

public class Student implements Serializable {
    private String username;
    private String fName;
    private String lName;
    private String email;
    private int age;
    private double GPA;
    private Major major;

    public Student() {

    }
    public Student(String u, String f, String l, String e, int a, double g, Major m) {
        username = u;
        fName = f;
        lName = l;
        email = e;
        age = a;
        GPA = g;
        major = m;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return fName;
    }
    public String getLastName() {
        return lName;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public double getGPA() {
        return GPA;
    }
    public Major getMajor() {
        return major;
    }

    //setters
    public void setUsername(String u) {
        username = u;
    }
    public void setFirstName(String f) {
        fName = f;
    }
    public void setLastName(String l) {
        lName = l;
    }
    public void setEmail(String e) {
        email = e;
    }
    public void setAge(int a) {
        age = a;
    }
    public void setGPA(double g) {
        GPA = g;
    }
    public void setMajor(Major m) {
        major = m;
    }
}
