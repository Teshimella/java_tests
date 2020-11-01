package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class Test_GetRepolist_negativ {

    @Test
    public void check_repo_and_param_before() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("affiliation", "owner");
        params.put("type", "owner");
        Response list_repo_param_before = GitMetods.listRepo(params);
        assertEquals("Error " + list_repo_param_before.statusCode() + " != 422\n",
                422, list_repo_param_before.statusCode());
        assertEquals("Error message", "If you specify visibility or affiliation, you cannot specify type.",
                list_repo_param_before.jsonPath().getString("message"));
    }
}


