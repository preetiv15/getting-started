package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {
    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void createUserTest() {
        String payload = "{\"empId\":\"1\", \"empName\":\"Preeti\"}";
        Response res = given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/RestUsers");
        String body = res.getBody().asString();
        System.out.println(body);
        Assertions.assertNotNull(body);
    }

    @Test
    public void getUserTest() {
        int statusCode = given()
                .queryParam("empId", "1")
                .queryParam("empName", "Nitu").when().get("/RestUsers").getStatusCode();
        Assertions.assertEquals(200, statusCode);
        given().when().get("/RestUsers").then().assertThat().statusCode(200);
    }

    @Test
    public void getErrorWithIncorrectEndPoint() {
        int statusCode = given()
                .queryParam("empId", "1")
                .queryParam("empName", "Nitu").when().get("/RestUsersIncorrect").getStatusCode();
        Assertions.assertEquals(404, statusCode);
        given().when().get("/RestUsersIncorrect").then().assertThat().statusCode(404);
    }

}