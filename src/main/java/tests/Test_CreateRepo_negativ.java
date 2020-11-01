package tests;

import fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import static config.Config.test_repo;
import static org.junit.Assert.*;

public class Test_CreateRepo_negativ extends Fixture {

    @BeforeEach
    public void create_repo() {
        System.out.println("create_repo --> " + test_repo + " <-- before test");
        Response create_repo = GitMetods.createRepo(test_repo, false);
        assertEquals("Error create repo before test\n", create_repo.statusCode(), 201);
    }

    @Test
    public void testCreateRepo_dublicate() {
        Response create_repo_dublicate = GitMetods.createRepo(test_repo, true);
        Response list_repo = GitMetods.listRepo();
        System.out.println("Repolist names " + list_repo.jsonPath().getString("name"));
        assertEquals("Error " + create_repo_dublicate.statusCode() + " != 201\n" + list_repo.getBody().asString(),
                422, create_repo_dublicate.statusCode());
        assertTrue("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "false", false));
    }
}

