package config;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Base64;

public class Config {
    public static final String GIT_URL = "https://api.github.com";
    public static final String OWNER = "Teshimella";
    protected static final String TOKEN = "TOKEN";
    protected static final String TOKEN_WITHOUT_DELETE_RIGHT = "TOKEN_WITHOUT_DELETE_RIGHT";
    public static final String test_repo = "test";

    public static String EncoderToken(String token) {
        Response response = RestAssured
                .given()
                .auth().preemptive().basic(OWNER, token)
                .when()
                .get(GIT_URL + "/user")
                .then()
                .statusCode(200)
                .extract()
                .response();
        assert response.jsonPath().get("login").equals(OWNER) : "Error " + response.getBody().asString();
        return Base64.getEncoder().encodeToString((OWNER + ":" + token).getBytes());
    }
}

