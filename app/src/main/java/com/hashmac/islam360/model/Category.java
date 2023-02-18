package com.hashmac.islam360.model;

public class Category {
    // private variables

    String name;
    String fileName;
    String count;

    // Empty constructor
    public Category() {

    }

    // constructor
    public Category(String name, String fileName, String count) {

        this.name = name;
        this.fileName = fileName;
        this.count = count;
    }

    // getting name
    public String getName() {
        return this.name;
    }

    // setting name
    public void setName(String name) {
        this.name = name;
    }

    // getting fileName
    public String getFileName() {
        return this.fileName;
    }

    // setting fileName
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // getting counter
    public String getCount() {
        return this.count;
    }

    // setting counter
    public void setCount(String count) {
        this.count = count;
    }

}
