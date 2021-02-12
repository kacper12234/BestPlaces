package kacper.bestplaces.service;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.repository.PlacesRepository;
import kacper.bestplaces.repository.ReactionRepository;
import kacper.bestplaces.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class PlacesServiceTest {

    @Mock
    private PlacesRepository placesRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReactionRepository reactionRepository;
    @Mock
    private AuthService authService;

    @Captor
    private ArgumentCaptor<Place> placeCaptor;

    @Captor
    private ArgumentCaptor<Set<Integer>> indexesCaptor;

    private PlacesServiceImpl placesService;

    @BeforeEach
    void setup(){
        placesService = new PlacesServiceImpl(placesRepository,reactionRepository,userRepository, authService);
    }

    @Test
    @DisplayName("should save place")
    void savePlace() {
        Place place = Place.builder()
                .name("Place")
                .type("Test")
                .descrp("this is a test place")
                .loc("test")
                .build();


        Mockito.when(authService.getLoggedUser()).thenReturn("test");
        placesService.savePlace(place,new MultipartFile[]{});
        Mockito.verify(placesRepository,Mockito.times(1)).save(placeCaptor.capture());

        Assertions.assertThat(placeCaptor.getValue().getName()).isEqualTo("Place");
        Assertions.assertThat(placeCaptor.getValue().getType()).isEqualTo("Test");
        Assertions.assertThat(placeCaptor.getValue().getDescrp()).isEqualTo("this is a test place");
        Assertions.assertThat(placeCaptor.getValue().getLoc()).isEqualTo("test");
    }

    @Test
    @DisplayName("should get 9 random places")
    void getRandomPlaces() {
        Mockito.when(placesRepository.count()).thenReturn(100L);
        placesService.getRandomPlaces();
        Mockito.verify(placesRepository,Mockito.times(1)).findAllById(indexesCaptor.capture());
        Assertions.assertThat(indexesCaptor.getValue().size()).isEqualTo(9);
    }

}