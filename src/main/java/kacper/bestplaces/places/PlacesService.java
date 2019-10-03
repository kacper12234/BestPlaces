package kacper.bestplaces.places;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlacesService {

	public Places findPlaceByName(String name);
	public Likes findLikeByPlaceAndUser(String place,String user);
	public String getUsername(String mail);
	public void updatePlace(long id,String newname,String newloc,String newdescrp);
	public void changeRate(int status,int id);
	public void undoLike(int id);
	public void changeUp(int up,String name);
	public void changeDown(int down,String name);
	public void changeComment(String comment,int id);
	public void savePlace(Places place,MultipartFile[] mFile);
	public void saveLike(Likes like,String name);
	public void saveDisLike(Likes like,String name);
	public void saveComment(Likes like,String name);
	public void clearComment(int id);
	void deletePlace(String name) throws IOException;
	Page<Likes> findByPlace(String place,Pageable pg);
	ArrayList<Places> getEgPlaces();
	Page<Places> getPlaces(Pageable pg);
	Page<Places> getPlacesByType(String type,Pageable pg);
	Page<Places> findInLoc(String param,Pageable pg);
	Page<Places> findPlacesTypeInLoc(String param,String type,Pageable pg);
}
