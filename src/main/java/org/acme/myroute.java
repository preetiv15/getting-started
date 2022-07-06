package org.acme;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class myroute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.json);
//             from("timer://check?period=5s")
//                .log("running")
//                ;
        rest("/RestUsers")
                .get()
                .to("direct:getUser")

                .post()
                .to("direct:addUser");

        from("direct:addUser")
                .log("${body}")
                .setBody().simple("insert into employee values('${body[empId]}','${body[empName]}')")
                .log("inside add user")
                .to("jdbc:default")
                .log("Inserted camel: ${messageTimestamp}");
        from("direct:getUser")
                .log("Inside get user")
                .setBody(simple("Select * from employee")).to("jdbc:default");
    }
    
}
