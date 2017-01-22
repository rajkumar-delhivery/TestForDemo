package com.ex.test.AppPackage.oms.test;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by delhivery on 2/1/17.
 */

@Service
public class Main {

    @Autowired
    CamelContext camelContext;

    public void startRoute(){
        camelContext.createProducerTemplate().sendBody("direct:start", "Hello Brother");
    }

    public void printHelloWorld(){
        System.out.println("Hello World");
    }
}


