package com.ex.test.AppPackage.oms.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by delhivery on 2/1/17.
 */

@RestController
public class AppController {

    @Autowired
    Main main;
    @RequestMapping("/apptest")
    public StudentModel getModel(){
        main.startRoute();
        return new StudentModel("RAJ", "ISE2014003");
    }
}
