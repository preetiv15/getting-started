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
                .to("direct:addUser")

                .put()
                .to("direct:updateUser");

        from("direct:addUser")
                .log("${body}")
                .setBody().simple("insert into employee values('${body[empId]}','${body[empName]}')")
                .log("inside add user")
                .to("jdbc:default")
                .log("Inserted camel: ${messageTimestamp}");
        from("direct:getUser")
                .log("Inside get user")
                .setBody(simple("Select * from employee")).to("jdbc:default");
        from("direct:updateUser")
                .log("${body}")
                .setBody().simple("Update employee SET empId = '${body[empId]}', empName= '${body[empName]}'")
                .log("inside updateuser")
                .to("jdbc:default")
                .log("Updated camel: ${messageTimestamp}");
    }
    
}
