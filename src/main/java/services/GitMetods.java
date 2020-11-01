package services;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static services.Auth.*;

public class GitMetods {

    public static Response createRepo(String name, Boolean privat_repo) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        if (privat_repo)
            map.put("private", true);
        return RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .body(map)
                .post("/user/repos")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public static Response deleteRepo(String name) {
        return RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .delete(String.format("/repos/%s/%s", Config.OWNER, name))
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public static Response deleteRepo_without_right(String name) {
        return requestSpec_without_right
                .when()
                .delete(String.format("/repos/%s/%s", Config.OWNER, name))
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public static Response listRepo() {
        return RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/user/repos")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }


    public static Response listRepo(LinkedHashMap<String, String> params) {
        return RestAssured
                .given()
                .spec(requestSpec)
                .queryParams(params)
                .when()
                .get("/user/repos")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }


    public static Boolean check_Reponame_in_listRepo(String name, String privat_repo, boolean afterdeletion) {
        long start = System.currentTimeMillis();
        timer:
        while (System.currentTimeMillis() - start < 10000) {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("visibility", "all");
            Response listRepo = GitMetods.listRepo(params);
            ArrayList<LinkedHashMap<String, Object>> list_name_repo = listRepo.jsonPath().get();
            for (LinkedHashMap<String, Object> repo_name : list_name_repo) {
                if (afterdeletion & System.currentTimeMillis() - start < 6000)
                    continue timer;
                else if (privat_repo.equals("all"))
                    return true;
                else if (repo_name.get("name").equals(name) &
                        repo_name.get("private").equals(Boolean.valueOf(privat_repo)))
                    return true;
            }
        }
        return false;
    }
}
