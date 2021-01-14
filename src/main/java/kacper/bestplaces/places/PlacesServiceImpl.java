package kacper.bestplaces.places;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import kacper.bestplaces.reactions.Reaction;
import kacper.bestplaces.reactions.ReactionRepository;
import kacper.bestplaces.reactions.Type;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.user.User;
import kacper.bestplaces.user.UserRepository;
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
    public ArrayList<Place> getEgPlaces() {
        ArrayList<Place> getNine = new ArrayList<Place>();
        ArrayList<Long> index = new ArrayList<Long>();
        long num = placesRepository.count();
        for (long i = 1; i <= num; i++)
            index.add(i);
        Random rand = new Random();
        for (int i = 0; i < 9 && i < num; i++) {
            int l = rand.nextInt(index.size());
            getNine.add(placesRepository.getOne(Math.toIntExact(index.get(l))));
            index.remove(l);
        }
        return getNine;
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
    public Reaction findReactionByPlaceAndUser(Place place, User user) {
        return reactionRepository.findByPlaceAndUser(place, user);
    }

    @Override
    public void saveReaction(Reaction reaction, Place place, User user, Type type) {
        reaction.setType(type);
        saveReactionAndAddToLists(reaction,user,place);
    }

    @Override
    public void changeRate(Type type, long id) {
        reactionRepository.changeRate(String.valueOf(type), id);
    }

    @Override
    public void saveComment(Reaction reaction, Place place, User user, String comment) {
        reaction.setComment(comment);
        saveReactionAndAddToLists(reaction,user,place);
    }


    @Override
    public void changeComment(String comment, long id) {
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
    public void updatePlace(long id, String newname, String newloc, String newdescrp) {
        placesRepository.placeUpdate(id, newname, newloc, newdescrp);
    }

    @Override
    public void undoRate(long id) {
        reactionRepository.undoRate(id);
    }

    @Override
    public void deletePlace(String name) throws IOException {
        Place place = placesRepository.findByName(name);
        long i = place.getId() + 1;
        long count = placesRepository.count();
        File r = new File(IMAGES + name);
        placesRepository.delete(place);
        FileUtils.deleteDirectory(r);
        for (; i <= count; i++)
            placesRepository.updateId(i - 1, i);
    }

    @Override
    public void clearComment(long id) {
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
    public void delPhoto(int nr, String place) {
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
