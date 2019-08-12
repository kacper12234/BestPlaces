package kacper.bestplaces.places;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kacper.bestplaces.emailSender.Email;
import kacper.bestplaces.utilities.UserUtilities;



@Controller
public class PlacesController {

	private static int ELEMENTS=10;
	
	@Autowired
	private PlacesService placesService;
	
	@GET
	@RequestMapping(value="/places/{page}")
	public String places(@PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.getPlaces(PageRequest.of(page-1, ELEMENTS));
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<Places> placesList=pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{page}")
	public String placesType(@PathVariable("page") int page,@PathVariable("type") String type, Model model)
	{
		Page<Places> pages=placesService.getPlacesByType(type,PageRequest.of(page-1, ELEMENTS));
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<Places> placesList=pages.getContent();
		model.addAttribute("type", type);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/search/{searchLoc}/{page}")
	public String placesInLoc(@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findInLoc(param,PageRequest.of(page-1, ELEMENTS));
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<Places> placesList=pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/cat/{type}/search/{searchLoc}/{page}")
	public String placesInLoc(@PathVariable("type")String type,@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findPlacesTypeInLoc(param, type,PageRequest.of(page-1, ELEMENTS));
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<Places> placesList=pages.getContent();
		model.addAttribute("type", type);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/cat/{type}/{name}")
	public String placeDesc(@PathVariable("type") String type,@PathVariable("name") String name, Model model)
	{
		Places place=placesService.findPlaceByName(name);
		Likes like=placesService.findLikeByPlaceAndUser(name,UserUtilities.getLoggedUser());
		if(like!=null)
		model.addAttribute("like",like.getLikes());
		else
			model.addAttribute("like",0);
		model.addAttribute("place", place);
		model.addAttribute("email",new Email());
		return "placesDesc";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/like")
	public String placeLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,UserUtilities.getLoggedUser());
		if(likeHandle==null)
		{
		placesService.saveLike(like,name);
		placesService.changeUp(placesService.findPlaceByName(name).getUp()+1, name);
		}
		else if(likeHandle.getLikes()==2)
		{
		placesService.changeRate(1, likeHandle.getId());
		placesService.changeUp(placesService.findPlaceByName(name).getUp()+1, name);
		placesService.changeDown(placesService.findPlaceByName(name).getDown()-1, name);
		}
		else
		{
		placesService.undoLike(likeHandle.getId());
		placesService.changeUp(placesService.findPlaceByName(name).getUp()-1, name);
		}
		Places place=placesService.findPlaceByName(name);
		model.addAttribute("place", place);
		model.addAttribute("email",new Email());
		return "placesDesc";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/dislike")
	public String placeDisLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,UserUtilities.getLoggedUser());
		if(likeHandle==null)
		{
		placesService.saveDisLike(like,name);
		placesService.changeDown(placesService.findPlaceByName(name).getDown()+1, name);
		}
		else if(likeHandle.getLikes()==1)
		{
		placesService.changeRate(2, likeHandle.getId());
		placesService.changeDown(placesService.findPlaceByName(name).getDown()+1, name);
		placesService.changeUp(placesService.findPlaceByName(name).getUp()-1, name);
		}
		else
		{
		placesService.undoLike(likeHandle.getId());
		placesService.changeDown(placesService.findPlaceByName(name).getDown()-1, name);
		}
		Places place=placesService.findPlaceByName(name);
		model.addAttribute("place", place);
		model.addAttribute("email",new Email());
		return "placesDesc";
	}
	
}
