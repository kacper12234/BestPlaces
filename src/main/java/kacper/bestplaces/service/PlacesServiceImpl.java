package kacper.bestplaces.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.repository.PlacesRepository;
import kacper.bestplaces.model.Reaction;
import kacper.bestplaces.repository.ReactionRepository;
import kacper.bestplaces.model.Type;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.model.User;
import kacper.bestplaces.repository.UserRepository;
import kacper.bestplaces.utilities.UserUtilities;

import static kacper.bestplaces.constants.AppConstants.IMAGES;

@Service("placesService")
@Transactional
@AllArgsConstructor
public class PlacesServiceImpl implements PlacesService {

    private final PlacesRepository placesRepository;
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;

    @Override
    public void savePlace(Place place, MultipartFile[] mFile) {
        place.setUser(userRepository.findByEmail(UserUtilities.getLoggedUser()));
        place.setId(Math.toIntExact(placesRepository.count() + 1));
        String uploadDir = IMAGES + place.getName();
        File file;
        try {
            file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }
            int i = 0;
            for (MultipartFile m : mFile) {
                i++;
                Path fileAndPath = Paths.get(uploadDir, place.getName() + i + ".jpg");
                Files.write(fileAndPath, m.getBytes());
                new File(fileAndPath.toString());
            }
            place.setCount(i);
            placesRepository.save(place);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Place> getRandomPlaces() {
        Set<Integer> indexes = new LinkedHashSet<>();
        Random rand = new Random();
        int numberOfPlaces = Math.toIntExact(placesRepository.count());
        while(indexes.size()<9)
            indexes.add(rand.nextInt(numberOfPlaces)+1);
        return placesRepository.findAllById(indexes);
    }

    @Override
    public Page<Place> getPlaces(Pageable pg) {
        return placesRepository.findAll(pg);
    }

    @Override
    public Page<Place> getPlacesByType(String type, Pageable pg) {
        Page<Place> places = placesRepository.findByType(type, pg);
        return places;
    }

    @Override
    public Place findPlaceByName(String name) {
        return placesRepository.findByName(name);
    }

    @Override
    public void saveReaction(Reaction reaction, Place place, User user, Type type) {
        reaction.setType(type);
        saveReactionAndAddToLists(reaction,user,place);
    }

    @Override
    public void changeRate(Type type, Integer id) {
        reactionRepository.changeRate(String.valueOf(type), id);
    }

    @Override
    public void saveComment(Reaction reaction, Place place, User user, String comment) {
        reaction.setComment(comment);
        saveReactionAndAddToLists(reaction,user,place);
    }


    @Override
    public void changeComment(String comment, Integer id) {
        reactionRepository.changeComment(comment, id);
    }


    @Override
    public Page<Place> findInLoc(String param, Pageable pg) {
        Page<Place> places = placesRepository.findInLoc(param, pg);
        return places;
    }

    @Override
    public Page<Place> findPlacesTypeInLoc(String param, String type, Pageable pg) {
        Page<Place> places = placesRepository.findTypeInLoc(param, type, pg);
        return places;
    }

    @Override
    public User getUser(String mail) {
        return userRepository.findByEmail(mail);
    }

    @Override
    public void updatePlace(Integer id, String newname, String newloc, String newdescrp) {
        placesRepository.placeUpdate(id, newname, newloc, newdescrp);
    }

    @Override
    public void undoRate(Integer id) {
        reactionRepository.undoRate(id);
    }

    @Override
    public void deletePlace(String name) throws IOException {
        Place place = placesRepository.findByName(name);
        int count = Math.toIntExact(placesRepository.count());
        File dir = new File(IMAGES + name);
        placesRepository.delete(place);
        FileUtils.deleteDirectory(dir);
        for (int i = place.getId() + 1; i <= count; i++)
            placesRepository.updateId(i - 1, i);
    }

    @Override
    public void clearComment(Integer id) {
        reactionRepository.clearComment(id);
    }

    @Override
    public void addPhotos(String place, MultipartFile[] mFile) {
        String uploadDir = IMAGES + place;
        File file;
        try {
            file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }
            int i = file.listFiles().length;
            for (MultipartFile m : mFile) {
                i++;
                Path fileAndPath = Paths.get(uploadDir, place + i + ".jpg");
                Files.write(fileAndPath, m.getBytes());
                new File(fileAndPath.toString());
            }
            placesRepository.findByName(place).setCount(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delPhoto(Integer nr, String place) {
        String uploadDir = IMAGES + place;
        File file = new File(uploadDir);
        int i = file.listFiles().length;
        uploadDir += "/" + place;
        file = new File(uploadDir + nr + ".jpg");
        file.delete();
        if (nr < i)
            for (int j = nr + 1; j <= i; j++) {
                File dest = new File(uploadDir + (j - 1) + ".jpg");
                file = new File(uploadDir + j + ".jpg");
                file.renameTo(dest);
            }
        placesRepository.findByName(place).setCount(i - 1);
    }

    private void saveReactionAndAddToLists(Reaction reaction,User user,Place place){
        reaction.setPlace(place);
        reaction.setUser(user);
        reactionRepository.save(reaction);
    }
}
