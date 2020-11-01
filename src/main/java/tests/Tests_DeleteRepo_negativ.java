package tests;

import fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.GitMetods;

import static config.Config.test_repo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests_DeleteRepo_negativ extends Fixture {

    @BeforeEach
    public void create_repo() {
        System.out.println("create_repo --> " + test_repo + " <-- before test");
        Response create_repo = GitMetods.createRepo(test_repo, false);
        assertEquals("Error create repo before test\n", 201, create_repo.statusCode());
    }

    @Test
    public void testdeleteRepo_without_right() {
        Response delete_repo = GitMetods.deleteRepo_without_right(test_repo);
        assertTrue("Error " + test_repo + " not in the listRepo",
                GitMetods.check_Reponame_in_listRepo(test_repo, "false", true));
        assertEquals("Error status code - delete repo without right\n", 403, delete_repo.statusCode());
        assertEquals("Error message - delete repo without right\n", delete_repo.jsonPath().getString("message"),
                "Must have admin rights to Repository.");
    }
}