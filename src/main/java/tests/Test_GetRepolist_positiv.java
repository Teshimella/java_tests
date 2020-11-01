package tests;

import fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import java.util.LinkedHashMap;

import static config.Config.test_repo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Test_GetRepolist_positiv extends Fixture {

    @BeforeEach
    public void create_repo_private() {
        System.out.println("create_repo --> " + test_repo + " <-- before test");
        Response create_repo = GitMetods.createRepo(test_repo, true);
        assertEquals("Error create repo before test\n", 201, create_repo.statusCode());
        assertTrue("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "true", false));
    }

    @Test
    public void check_private_repo_in_all_repo() {
        Response list_repo_all = GitMetods.listRepo();
        System.out.println("Repolist names " + list_repo_all.jsonPath().getString("name"));
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("visibility", "public");
        Response list_repo_public = GitMetods.listRepo(params);
        assertEquals("Error " + list_repo_public.statusCode() + " != 200\n", 200, list_repo_public.statusCode());
        assertNotEquals("Error " + list_repo_all.jsonPath().getString("name") + "not equals "
                        + list_repo_public.jsonPath().getString("name"),
                list_repo_all.jsonPath().getString("name"),
                list_repo_public.jsonPath().getString("name"));
    }
}
