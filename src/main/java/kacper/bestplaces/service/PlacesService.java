package kacper.bestplaces.service;

import java.io.IOException;
import java.util.List;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.model.Reaction;
import kacper.bestplaces.model.Type;
import kacper.bestplaces.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlacesService {

	Place findPlaceByName(String name);
	User getUser(String mail);

	void updatePlace(Integer id, String newname, String newloc, String newdescrp);
	void undoRate(Integer id);
	void changeComment(String comment,Integer id);
	void savePlace(Place place, MultipartFile[] mFile);
	void saveReaction(Reaction like, Place place,User user, Type type);
	void changeRate(Type type, Integer id);
	void addPhotos(String place,MultipartFile[] mFile);
	void delPhoto(Integer nr,String name);
	void saveComment(Reaction like, Place place,User user,String comment);
	void clearComment(Integer id);
	void deletePlace(String name) throws IOException;
	List<Place> getRandomPlaces();
	Page<Place> getPlaces(Pageable pg);
	Page<Place> getPlacesByType(String type, Pageable pg);
	Page<Place> findInLoc(String param, Pageable pg);
	Page<Place> findPlacesTypeInLoc(String param, String type, Pageable pg);
}
