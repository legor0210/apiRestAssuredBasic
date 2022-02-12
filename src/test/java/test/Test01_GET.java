package test;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Test01_GET {

    private static final Logger LOG = LogManager.getLogger(Test01_GET.class);


    //@Test
    public void test_01 () {

        Response response = get("https://reqres.in/api/users?page=2");
        LOG.info(response.asString());
        LOG.info(response.getBody().asString());
        LOG.info(response.getStatusCode());
        LOG.info(response.getStatusLine());
        LOG.info(response.getTime());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);

    }

    @Test
    public void test_02 () {
        given().get("https://reqres.in/api/users?page=2").then().statusCode(200)
                .body("data.id[0]",equalTo(7))
                .body("data.email[0]",equalTo("michael.lawson@reqres.in"))
                .body("data.first_name",hasItems("Michael","Lindsay"));
    }
}
