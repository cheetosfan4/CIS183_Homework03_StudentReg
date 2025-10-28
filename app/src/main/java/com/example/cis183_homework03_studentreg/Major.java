package com.example.cis183_homework03_studentreg;

public class Major {
    private int ID;
    private String name;
    private String prefix;

    public Major() {

    }
    public Major(int i, String n, String p) {
        ID = i;
        name = n;
        prefix = p;
    }

    //getters
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getPrefix() {
        return prefix;
    }

    //setters
    public void setID(int i) {
        ID = i;
    }
    public void setName(String n) {
        name = n;
    }
    public void setPrefix(String p) {
        prefix = p;
    }
}
