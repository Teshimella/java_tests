package tests;

import fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import static config.Config.test_repo;
import static org.junit.Assert.*;


public class Test_CreateRepo_positiv extends Fixture {

    @Test
    public void testCreateRepo() {
        Response create_repo = GitMetods.createRepo(test_repo, true);
        Response list_repo = GitMetods.listRepo();
        System.out.println("Repolist names " + list_repo.jsonPath().getString("name"));
        assertEquals("Error " + create_repo.statusCode() + " != 201\n" + list_repo.getBody().asString(),
                201, create_repo.statusCode());
        assertTrue("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "true", false));
    }
}

