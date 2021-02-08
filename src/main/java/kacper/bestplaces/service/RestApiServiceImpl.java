package kacper.bestplaces.service;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import kacper.bestplaces.model.Place;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import kacper.bestplaces.repository.PlacesRepository;
import kacper.bestplaces.model.pldtls.Details;
import kacper.bestplaces.model.plsearch.ExplorePlaces;
import kacper.bestplaces.model.plsearch.Item;

import static kacper.bestplaces.constants.AppConstants.IMAGES;

@AllArgsConstructor
@Service("restApiService")
public class RestApiServiceImpl implements RestApiService {

    private final PlacesRepository placesRepository;
    private final PlacesService placesService;

    @Override
    @Async("load")
    public void getPlaces() throws IOException, FlickrException {
        String[] coords = {"21.64,52.4,23.59,54.37", "21.79,50.35,23.67,52.26", "21.26,49.1,23.14,50.32", "19.54,49.38,21.76,51.12", "19.53,51.2,21.76,53.43",
                "19.27,53.21,21.75,54.41", "16.68,53.57,19.34,54.82", "17.34,52.42,19.63,53.71", "17.25,51.11,19.45,52.6", "17.25,49.65,19.5,51.03",
                "14.98,50.43,17.23,51.79", "14.57,51.57,17.49,52.91", "14.23,52.9,16.92,54.42"};
        for (int i = 0; i < coords.length; i++) {
            RestTemplate rt = new RestTemplate();
            String hereApiKey = "f0fvPzMKTkmZhlNjS_Ot9QCdpP8PyR49dIdHMnBk2bY";
            String url = "https://places.sit.ls.hereapi.com/places/v1/discover/"
                    + "explore?languages=pl-PL&cat=sights-museums,natural-geographical&in=" + coords[i] + "&size=100&apiKey=" + hereApiKey;
            ResponseEntity<ExplorePlaces> places = rt.exchange(url,
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    ExplorePlaces.class);
            String flickrApiKey = "d4c5f6cfc14f7fed7ff028885ef643d1";
            String flickrSecret = "e2f331a3bf83595e";

            Flickr flickr = new Flickr(flickrApiKey, flickrSecret, new REST());
            PhotosInterface photos;
            SearchParameters params;
            for (Item item : Objects.requireNonNull(places.getBody()).getResults().getItems()) {
                if (placesService.findPlaceByName(item.getTitle()) == null) {
                    url = "https://places.demo.api.here.com/places/v1/places/" + item.getId()
                            + ";context="
                            + "Zmxvdy1pZD0yNWEwOTkyOS0wNDIzLTU3NTUtYWIwYS1mNzkyNDZjNmRkM2VfMTU5MDE1ODcxMjE1N18wXzE1OTkmc2l6ZT01JlgtRldELUFQUC1JRD1EZW1vQXBwSWQwMTA4MjAxM0dBTCZYLU5MUC1UZXN0aW5nPTE"
                            + "?app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";
                    ResponseEntity<Details> placeDetails = rt.exchange(url,
                            HttpMethod.GET,
                            new HttpEntity<>(null),
                            Details.class);

                    String name = Objects.requireNonNull(placeDetails.getBody()).getName();

                    if (placeDetails.getBody().getMedia().getEditorials() != null && name.length() <= 50) {
                        photos = flickr.getPhotosInterface();
                        params = new SearchParameters();
                        params.setText(name);
                        params.setSort(SearchParameters.RELEVANCE);
                        params.setLatitude(placeDetails.getBody().getLocation().getPosition().get(0).toString());
                        params.setLongitude(placeDetails.getBody().getLocation().getPosition().get(1).toString());

                        savePlace(placeDetails.getBody(), photos.search(params, 5, 0), item.getCategory().getId());
                    }
                }
            }
            System.out.println("Progress: " + (double) (i + 1) / coords.length * 100 + "%");
        }
    }

    public void savePlace(Details details, PhotoList<Photo> photoList, String hereCategory) throws IOException {
        if (photoList.size() > 0) {
            String category;
            switch (hereCategory) {
                case "sights-museums":
                case "landmark-attraction":
                case "religious-place":
                    category = "Miejsce zwiedzania";
                    break;
                case "mall":
                case "amusement-holiday-park":
                case "sports-facility-venue":
                    category = "Rozrywka";
                    break;
                case "hotel":
                    category = "Hotele";
                    break;
                case "recreation":
                    category = "Natura";
                    break;
                default:
                    category = "Inne";
                    break;
            }

            String description = details.getMedia().getEditorials().getItems().get(0).getDescription();
            while (description.contains("<")) {
                String replace = description.substring(description.indexOf("<"), description.indexOf(">") + 1);
                description = description.replace(replace, "");
            }
            Place place = Place.builder()
                    .user(placesService.getUser("kacpermochniej1999@gmail.com"))
                    .id(Math.toIntExact(placesRepository.count() + 1))
                    .type(category)
                    .name(details.getName())
                    .descrp(description + "\nZaimportowane z HERE")
                    .loc(details.getLocation().getAddress().getCity())
                    .count(photoList.size())
                    .build();

            String uploadDir = IMAGES + place.getName();

            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
                for (int i = 0; i < photoList.size(); i++)
                    savePhoto(photoList.get(i), uploadDir + '/' + place.getName() + (i + 1) + ".jpg");
            }
            placesRepository.save(place);
        }
    }


    public void savePhoto(Photo photo, String dir) throws IOException {
        FileUtils.copyURLToFile(new URL(photo.getLargeUrl()), new File(dir));

    }
}
