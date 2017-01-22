package com.ex.test.AppPackage.oms.test.model;

import org.springframework.stereotype.Repository;

/**
 * Created by delhivery on 18/1/17.
 */
@Repository
public class FullName {
    private String fName;
    private String lName;

    public String getFullName(String fName, String lName){
        return String.format("%s %s", fName, lName);
    }
}
