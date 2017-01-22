
package com.ex.test.AppPackage.oms.test;

import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static com.ex.test.AppPackage.oms.test.Route.*;
import static org.apache.camel.Exchange.HTTP_METHOD;

/**
 * Created by delhivery on 2/1/17.
 */

@RunWith(CamelSpringJUnit4ClassRunner.class)
@SpringBootApplication
@ContextConfiguration(classes = {RouteTest.class}, loader = CamelSpringDelegatingTestContextLoader.class)
@PropertySource("classpath:application.properties")
public class RouteTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri = "{{END}}")
    MockEndpoint mockEndpoint;

    @Produce(uri = "{{START}}")
    ProducerTemplate producerTemplate;

//    @Autowired
//    Route route;

    @ComponentScan
    @Configuration
    static class Config {
        @Bean
        public RouteBuilder route() {
            com.ex.test.AppPackage.oms.test.Route route = new com.ex.test.AppPackage.oms.test.Route();
            return route;
        }
    }

    @Before
    public void setUp() throws Exception {

        CamelContext context = producerTemplate.getCamelContext();

        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
//                replaceFromWith("direct:start");
                Predicate p1 = header(X_AUTH_TOKEN_HEADER).isEqualTo("f60f8e3f-df30-3006-8700-4d1112c42b99");
                Predicate p2 = header(X_SELLET_AUTHZ_TOKEN).isEqualTo("bdf5971f-3552-4c27-843b-8d119aa2f838");
                Predicate p3 = header(CLIENT_ID_HEADER).isEqualTo(74);
                Predicate p4 = header(HTTP_METHOD).isEqualTo("GET");

                Predicate p_aggregate = PredicateBuilder.and(p1, p2, p3, p4);
//                mockEndpoints("http:*");
                interceptSendToEndpoint("http:staging-apigateway.snapdeal.com/seller-api/orders/new").when(p_aggregate).skipSendToOriginalEndpoint().setBody(simple("Prdeep"));
                mockEndpointsAndSkip("file:*");

            }
        });

    }

    @Test
    public void testRoute() throws Exception {
        String test = "Hello Bro";
        mockEndpoint.expectedBodiesReceived(test);
        producerTemplate.sendBody(test);
        mockEndpoint.setAssertPeriod(5000);
        mockEndpoint.assertIsSatisfied();

    }
}