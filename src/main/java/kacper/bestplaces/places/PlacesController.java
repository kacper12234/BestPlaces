package kacper.bestplaces.places;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

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
	@RequestMapping(value="/places/cat/{type}/{page}")
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
	@RequestMapping(value="/places/{type}/{name}/{page}")
	public String placeDesc(@PathVariable("type") String type,@PathVariable("name") String name,@PathVariable("page") int page, Model model)
	{
		Places place=placesService.findPlaceByName(name);
		if(UserUtilities.getLoggedUser()!=null)
		{
		Likes like=placesService.findLikeByPlaceAndUser(name,placesService.getUsername());
		if(like!=null)
		{
		model.addAttribute("like",like.getLikes());
		if(like.getComment()!=null)
			model.addAttribute("text",like);
		else
			model.addAttribute("text",new Likes());
		}
		else
		{
		model.addAttribute("like",0);
		model.addAttribute("text",new Likes());
		}
		}
		Page<Likes> pages=placesService.findByPlace(name,PageRequest.of(page-1, ELEMENTS));
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<Likes> rev=pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("rev", rev);
		model.addAttribute("place", place);
		model.addAttribute("email",new Email());
		return "placesDesc";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/like")
	public String placeLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,placesService.getUsername());
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
		else if(likeHandle.getLikes()==0)
		{
			placesService.changeRate(1, likeHandle.getId());
			placesService.changeUp(placesService.findPlaceByName(name).getUp()+1, name);
		}
		else if(likeHandle.getComment()!=null)
		{
			placesService.changeRate(0, likeHandle.getId());
			placesService.changeUp(placesService.findPlaceByName(name).getUp()-1, name);
		}
		else
		{
		placesService.undoLike(likeHandle.getId());
		placesService.changeUp(placesService.findPlaceByName(name).getUp()-1, name);
		}
		return "redirect:/places/{type}/{name}";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/dislike")
	public String placeDisLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,placesService.getUsername());
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
		else if(likeHandle.getLikes()==0)
		{
			placesService.changeRate(1, likeHandle.getId());
			placesService.changeUp(placesService.findPlaceByName(name).getDown()+1, name);
		}
		else if(likeHandle.getComment()!=null)
		{
			placesService.changeRate(0, likeHandle.getId());
			placesService.changeUp(placesService.findPlaceByName(name).getDown()-1, name);
		}
		else
		{
		placesService.undoLike(likeHandle.getId());
		placesService.changeDown(placesService.findPlaceByName(name).getDown()-1, name);
		}
		return "redirect:/places/{type}/{name}";
	}
	
	@POST
	@RequestMapping(value="/places/{type}/{name}/addcom")
	public String addComment(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		Likes check=placesService.findLikeByPlaceAndUser(name,placesService.getUsername());
		if(check==null)
			placesService.saveComment(like, name);
		else 
			placesService.changeComment(like.getComment(), check.getId());
		return "redirect:/places/{type}/{name}";
	}
}
