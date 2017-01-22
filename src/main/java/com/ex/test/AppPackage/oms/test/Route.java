package com.ex.test.AppPackage.oms.test;

import com.ex.test.AppPackage.oms.test.model.FullName;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.HTTP_METHOD;

/**
 * Created by delhivery on 2/1/17.
 */

@Component
public class Route extends FatJarRouter{

    @Autowired
    Main main;

    @Autowired
    FullName fullName;



    @Value("${START}")
    static String start;

    public static final String X_AUTH_TOKEN_HEADER = "X-Auth-Token";
    public static final String X_SELLET_AUTHZ_TOKEN = "X-Seller-Authz-Token";
    public static final String CLIENT_ID_HEADER = "ClientId";
    @Override
    public void configure() throws Exception{
        from("{{START}}")
                .autoStartup(true)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("start route");
                    }
                })
                .setHeader(X_AUTH_TOKEN_HEADER, constant("f60f8e3f-df30-3006-8700-4d1112c42b99"))
                .setHeader(X_SELLET_AUTHZ_TOKEN, constant("bdf5971f-3552-4c27-843b-8d119aa2f838"))
                .setHeader(CLIENT_ID_HEADER , constant(74))
                .setHeader(HTTP_METHOD, simple("GET"))
                .setHeader(Exchange.HTTP_QUERY ,simple("fModes=DROPSHIP&pageSize=25&pageNumber=1"))
                .setHeader(Exchange.CONTENT_TYPE , simple("application/json"))
                .to("http:staging-apigateway.snapdeal.com/seller-api/orders/new")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Inside Route");
                    }
                })
                .bean(fullName, "getFullName(RAJ,Kumar)")
                .to("direct:next");
        from("direct:next")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("next route");
                    }
                })
                .to("{{END}}");
    }
}
