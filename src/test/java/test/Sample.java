package test;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Sample {

    private static final Logger LOG = LogManager.getLogger(Sample.class);

    @Test(priority = 1)
    public void test_getAllVideoGames() {
        given().when().get("http://localhost:8080/app/videogames")
                .then().statusCode(200);
    }

    @Test(priority = 2)
    public void test_addNewVideoGame() {
        HashMap data = new HashMap();
        data.put("id","100");
        data.put("name","Spider-Man");
        data.put("releaseDate","2021-02-15T08:02:42.574Z");
        data.put("reviewScore","5");
        data.put("category","Adventure");
        data.put("rating","Universal");

        Response res =

        given().contentType("application/json").body(data).when().post("http://localhost:8080/app/videogames")
                .then().statusCode(200).log().body().extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
    }

    @Test(priority = 3)
    public void test_getVideoGame() {
        given().when().get("http://localhost:8080/app/videogames/100")
                .then().statusCode(200).body("videoGame.id",equalTo("100"))
                .body("videoGame.name",equalTo("Spider-Man"));
    }

    @Test(priority = 4)
    public void test_UpdateVideoGame() {
        HashMap data = new HashMap();
        data.put("id","100");
        data.put("name","Lastik-Man");
        data.put("releaseDate","2021-02-16T08:01:42.574Z");
        data.put("reviewScore","3");
        data.put("category","Action");
        data.put("rating","Universals");

        given().contentType("application/json").body(data).when().put("http://localhost:8080/app/videogames/100")
                .then().statusCode(200).log().body()
                .body("videoGame.id",equalTo("100"))
                .body("videoGame.name",equalTo("Lastik-Man"));
    }

    @Test(priority = 5)
    public void test_DeleteVideoGame() {

        Response res =

                given().when().delete("http://localhost:8080/app/videogames/100")
                .then().statusCode(200).log().body().extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);

    }
}
