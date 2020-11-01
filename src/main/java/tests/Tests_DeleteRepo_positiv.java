package tests;

import fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import static config.Config.test_repo;
import static org.junit.Assert.*;

public class Tests_DeleteRepo_positiv extends Fixture {

    @BeforeEach
    public void create_repo_private() {
        System.out.println("create_repo --> " + test_repo + " <-- before test");
        Response create_repo = GitMetods.createRepo(test_repo, true);
        assertEquals("Error create repo before test\n", 201, create_repo.statusCode());
        assertTrue("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "true", false));
    }

    @Test
    public void testdeleteRepo() {
        Response delete_repo = GitMetods.deleteRepo(test_repo);
        Response list_repo = GitMetods.listRepo();
        System.out.println("Repolist names " + list_repo.jsonPath().getString("name"));
        assertFalse("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "true", true));
        assertEquals("Error status code - delete repo\n", 204, delete_repo.statusCode());
    }
}
