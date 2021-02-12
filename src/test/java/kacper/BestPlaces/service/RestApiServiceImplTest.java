package kacper.bestplaces.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import static kacper.bestplaces.constants.AppConstants.hereApiKey;
import static kacper.bestplaces.constants.AppConstants.hereURL;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@ExtendWith(MockitoExtension.class)
class RestApiConnectionTest {

    @Mock
    private RestTemplate restTemplate;
    private MockRestServiceServer serviceServer;


    @BeforeEach
    void setup(){
        serviceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @DisplayName("Should get list of data")
    @Test
    void getPlace(){
        serviceServer.expect(requestTo(hereURL + "21.64,52.4,23.59,54.37" + "&size=100&apiKey=" + hereApiKey)).
                andExpect(method(HttpMethod.GET));
    }
}