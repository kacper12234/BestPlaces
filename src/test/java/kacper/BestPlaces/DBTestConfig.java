package kacper.bestplaces;

import kacper.bestplaces.cucumber.CucumberContext;
import org.testcontainers.containers.MySQLContainer;

public class DBTestConfig extends CucumberContext {
    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("bestplaces_test")
            .withUsername("testuser")
            .withPassword("pass")
            .withReuse(true);

    static {
        mySQLContainer.start();
    }
}
