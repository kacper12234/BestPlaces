package kacper.bestplaces.cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kacper.bestplaces.DBTestConfig;
import kacper.bestplaces.exceptions.PlaceNotFoundException;
import kacper.bestplaces.exceptions.ReactionNotFoundException;
import kacper.bestplaces.model.Rate;
import kacper.bestplaces.model.Reaction;
import kacper.bestplaces.repository.PlacesRepository;
import kacper.bestplaces.repository.ReactionRepository;
import kacper.bestplaces.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReactionRepositoryTestSteps extends DBTestConfig {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private UserRepository userRepository;

    private Reaction reaction;

    @Before
    public void prepareData(){
        reaction = new Reaction();
        reaction.setComment("test comment");
        reaction.setPlace(placesRepository.findById(1).orElseThrow(PlaceNotFoundException::new));
        reaction.setUser(userRepository.findByEmail("kacpermochniej1999@gmail.com"));
        reaction.setRate(Rate.LIKE);
    }

    @When("I save the reaction")
    public void saveReaction() throws PendingException {
    Reaction actualReaction = reactionRepository.save(reaction);
    Assertions.assertThat(actualReaction).isEqualTo(reaction);
    }

    @Then("I want to change type")
    public void changeRate() throws PendingException{
        reactionRepository.changeRate(String.valueOf(Rate.DISLIKE),reaction.getId());
        Assertions.assertThat(reaction.getRate()).isNotEqualTo(getChangedReaction().getRate());
    }

    @Then("change comment")
    public void changeComment() throws PendingException{
        reactionRepository.changeComment("changed comment",reaction.getId());
        Assertions.assertThat(reaction.getComment()).isNotEqualTo(getChangedReaction().getComment());
    }

    @Then("undo rate")
    public void undoRate() throws PendingException{
        reactionRepository.undoRate(reaction.getId());
        Assertions.assertThat(getChangedReaction().getRate()).isNull();
    }

    @Then("clear comment")
    public void clearComment() throws PendingException{
        reactionRepository.clearComment(reaction.getId());
        Assertions.assertThat(getChangedReaction().getComment()).isNull();
    }

    @And("finally delete reaction")
    public void deleteReaction() throws PendingException{
        reactionRepository.delete(reaction);
        Assertions.assertThat(reactionRepository.findById(reaction.getId()).isPresent()).isFalse();
    }

    private Reaction getChangedReaction(){
        return reactionRepository.findById(reaction.getId()).orElseThrow(ReactionNotFoundException::new);
    }
}