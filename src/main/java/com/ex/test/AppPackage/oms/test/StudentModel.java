package com.ex.test.AppPackage.oms.test;

/**
 * Created by delhivery on 2/1/17.
 */
public class StudentModel {

    private String name;
    private String rollNo;

    public StudentModel(String name, String rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
