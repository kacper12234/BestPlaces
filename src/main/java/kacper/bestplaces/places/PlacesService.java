package kacper.bestplaces.places;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlacesService {

	public Places findPlaceByName(String name);
	public Likes findLikeByPlaceAndUser(String place,String user);
	public void changeRate(int status,int id);
	public void undoLike(int id);
	public void changeUp(int up,String name);
	public void changeDown(int down,String name);
	public void savePlace(Places place,MultipartFile mFile);
	public void saveLike(Likes like,String name);
	public void saveDisLike(Likes like,String name);
	ArrayList<Places> getEgPlaces();
	Page<Places> getPlaces(Pageable pg);
	Page<Places> getPlacesByType(String type,Pageable pg);
	Page<Places> findInLoc(String param,Pageable pg);
	Page<Places> findPlacesTypeInLoc(String param,String type,Pageable pg);
}