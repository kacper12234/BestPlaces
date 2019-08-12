package kacper.bestplaces.places;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.utilities.UserUtilities;

@Service("placesService")
@Transactional
public class PlacesServiceImpl implements PlacesService{

	@Autowired
	private PlacesRepository placesRepository;
	 
	@Autowired
	private LikesRepository likesRepository;
	
	@Override
	public void savePlace(Places place,MultipartFile mFile)
	{
		place.setAuthor(UserUtilities.getLoggedUser());
		place.setId(placesRepository.count()+1);
		String uploadDir = System.getProperty("user.dir")+"/target/classes/static/images/"+place.getAuthor();
		File file;
		try {
			file = new File(uploadDir);
			if (!file.exists()) {
				file.mkdir();
			}
			Path fileAndPath = Paths.get(uploadDir,mFile.getOriginalFilename());
			Files.write(fileAndPath, mFile.getBytes());
			file = new File(fileAndPath.toString());	
		place.setLink(file.getName());
			placesRepository.save(place);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Places> getEgPlaces()
	{
		ArrayList<Places> getNine=new ArrayList<Places>();
		ArrayList<Long> index=new ArrayList<Long>();
		long num=placesRepository.count();
		for(long i=1;i<=num;i++)
			index.add(i);
		Random rand = new Random();
		for(int i=0;i<9&&i<num;i++)
		{
			int l=rand.nextInt(index.size());
			getNine.add(placesRepository.getOne(index.get(l)));
			index.remove(l);
		}
		
		return getNine;
	}
	
	@Override
	public Page<Places> getPlaces(Pageable pg)
	{
		Page<Places> places=placesRepository.findAll(pg);
		return places;
	}

	@Override
	public Page<Places> getPlacesByType(String type,Pageable pg)
	{
		Page<Places> places=placesRepository.findByType(type, pg);
		return places;
	}
	
	@Override
	public Places findPlaceByName(String name) {
		return placesRepository.findByName(name);
	}

	@Override
	public void saveLike(Likes like,String name) {
		like.setName(name);
		like.setUser(UserUtilities.getLoggedUser());
		like.setLikes(1);
		likesRepository.save(like);
	}
	
	@Override
	public void saveDisLike(Likes like,String name) {
		like.setName(name);
		like.setUser(UserUtilities.getLoggedUser());
		like.setLikes(2);
		likesRepository.save(like);
	}

	@Override
	public Likes findLikeByPlaceAndUser(String place,String user) {
		return likesRepository.findByPlaceAndUser(place,user);
	}

	@Override
	public void changeRate(int status, int id) {
		likesRepository.changeRate(status, id);
	}

	@Override
	public void undoLike(int id) {
		likesRepository.undoLike(id);
	}

	@Override
	public void changeUp(int up, String name) {
		placesRepository.changeUp(up, name);
	}

	@Override
	public void changeDown(int down, String name) {
		placesRepository.changeDown(down, name);
	}

	@Override
	public Page<Places> findInLoc(String param, Pageable pg) {
		Page<Places> places=placesRepository.findInLoc(param, pg);
		return places;
	}

	@Override
	public Page<Places> findPlacesTypeInLoc(String param, String type, Pageable pg) {
		Page<Places> places=placesRepository.findTypeInLoc(param, type, pg);
		return places;
	}

}
