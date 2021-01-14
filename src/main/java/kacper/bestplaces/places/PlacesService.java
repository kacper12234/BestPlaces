package kacper.bestplaces.places;

import java.io.IOException;
import java.util.ArrayList;

import kacper.bestplaces.reactions.Reaction;
import kacper.bestplaces.reactions.Type;
import kacper.bestplaces.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlacesService {

	Place findPlaceByName(String name);
	Reaction findReactionByPlaceAndUser(Place place,User user);
	User getUser(String mail);

	void updatePlace(long id, String newname, String newloc, String newdescrp);
	void undoRate(long id);
	void changeComment(String comment,long id);
	void savePlace(Place place, MultipartFile[] mFile);
	void saveReaction(Reaction like, Place place,User user, Type type);
	void changeRate(Type type, long id);
	void addPhotos(String place,MultipartFile[] mFile);
	void delPhoto(int nr,String name);
	void saveComment(Reaction like, Place place,User user,String comment);
	void clearComment(long id);
	void deletePlace(String name) throws IOException;
	ArrayList<Place> getEgPlaces();
	Page<Place> getPlaces(Pageable pg);
	Page<Place> getPlacesByType(String type, Pageable pg);
	Page<Place> findInLoc(String param, Pageable pg);
	Page<Place> findPlacesTypeInLoc(String param, String type, Pageable pg);
}
