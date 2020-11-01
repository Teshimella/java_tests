package services;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


public class Auth extends Config {
    public static RequestSpecification requestSpec = given()
            .baseUri(Config.GIT_URL)
            .contentType(ContentType.JSON)
            .header("Authorization", "Basic " + Config.EncoderToken(Config.TOKEN));

    static RequestSpecification requestSpec_without_right = given()
            .baseUri(Config.GIT_URL)
            .contentType(ContentType.JSON)
            .header("Authorization", "Basic " + Config.EncoderToken(Config.TOKEN_WITHOUT_DELETE_RIGHT));

    static ResponseSpecification responseSpec = RestAssured
            .expect()
            .time(lessThan(5000L));
}

