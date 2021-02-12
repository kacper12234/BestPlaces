package kacper.bestplaces.cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kacper.bestplaces.DBTestConfig;
import kacper.bestplaces.exceptions.ReactionNotFoundException;
import kacper.bestplaces.model.User;
import kacper.bestplaces.repository.UserRepository;
import kacper.bestplaces.service.UserServiceImpl;
import kacper.bestplaces.validators.UserRegisterValidator;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreatingAccountTestSteps extends DBTestConfig {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    private UserRegisterValidator validator;

    private User incorrectUser[], user;

    @Before
    public void init() {
        validator = new UserRegisterValidator();
        user = new User();
        user.setName("Tester");
        user.setLastName("Tester");
        user.setPassword("HardPass123!");
        user.setActivationCode("code");
        user.setEmail("test@mail.com");
        incorrectUser = new User[]{user.clone(), user.clone(), user.clone()};
    }

    @Given("The entered data is incorrect")
    public void prepareIncorrectData() {
        incorrectUser[0].setLastName("");
        incorrectUser[1].setPassword("pass");
        incorrectUser[2].setEmail("email");
    }

    @Then("it should be rejected")
    public void incorrectDataValidate() throws PendingException {
        for (User data : incorrectUser)
            Assertions.assertThat(validate(data)).isTrue();
    }

    @When("data is valid")
    public void correctDataValidate() throws PendingException {
        Assertions.assertThat(validate(user)).isFalse();
    }

    @Then("account can be set up")
    public void register() throws PendingException {
        userService.saveUser(user);
        Assertions.assertThat(user).usingRecursiveComparison().ignoringFields("password", "active", "roles").isEqualTo(getUser());
    }

    @Then("it should be activated")
    public void activate() throws PendingException {

        userService.updateUserActivation(1, user.getActivationCode());
        Assertions.assertThat(user.getActive()).isNotEqualTo(getUser().getActive());
    }

    @And("account can be removed")
    public void remove() throws PendingException {
        userRepository.delete(getUser());
        Assertions.assertThat(userRepository.findById(user.getId()).isPresent()).isFalse();
    }

    private boolean validate(User data) {
        Errors error = new BeanPropertyBindingResult(data, "data");
        validator.validate(data, error);
        System.out.println(error.getAllErrors());
        return error.hasErrors();
    }

    private User getUser() {
        return userRepository.findById(user.getId()).orElseThrow(ReactionNotFoundException::new);
    }
}
