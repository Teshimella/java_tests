package fixture;

import org.junit.jupiter.api.AfterEach;
import services.GitMetods;

import static config.Config.test_repo;
import static org.junit.Assert.assertTrue;

public class Fixture {

    @AfterEach
    void after() {
        GitMetods.deleteRepo(test_repo);
        System.out.println("Delete repo after test");
        assertTrue(GitMetods.check_Reponame_in_listRepo(test_repo, "all", true));
    }
}
