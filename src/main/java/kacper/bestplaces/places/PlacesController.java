package kacper.bestplaces.places;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	class SortbyLikes implements Comparator<Places>
	{
		@Override
		public int compare(Places o1, Places o2) {
			return o2.getUp()-o1.getUp();
		}
	}
	
	@GET
	@RequestMapping(value="/places/{page}")
	public String places(@PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.getPlaces(PageRequest.of(page-1, ELEMENTS));
		placesShow(pages,model);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/sortUp/{page}")
	public String placesSortbyUp(@PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.getPlaces(PageRequest.of(page-1, ELEMENTS));
		placesShowSort(pages,model);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/cat/{type}/{page}")
	public String placesType(@PathVariable("page") int page,@PathVariable("type") String type, Model model)
	{
		Page<Places> pages=placesService.getPlacesByType(type,PageRequest.of(page-1, ELEMENTS));
		placesShow(pages,model);
		model.addAttribute("type", type);
		return "places";
	}
	
	
	@GET
	@RequestMapping(value="/places/cat/{type}/sortUp/{page}")
	public String placesTypeSortbyUp(@PathVariable("page") int page,@PathVariable("type") String type, Model model)
	{
		Page<Places> pages=placesService.getPlacesByType(type,PageRequest.of(page-1, ELEMENTS));
		placesShowSort(pages,model);
		model.addAttribute("type", type);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/search/{searchLoc}/{page}")
	public String placesInLoc(@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findInLoc(param,PageRequest.of(page-1, ELEMENTS));
		placesShow(pages,model);
		model.addAttribute("search", param);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/search/{searchLoc}/sortUp/{page}")
	public String placesInLocSortbyUp(@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findInLoc(param,PageRequest.of(page-1, ELEMENTS));
		placesShowSort(pages,model);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/cat/{type}/search/{searchLoc}/{page}")
	public String placesInLoc(@PathVariable("type")String type,@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findPlacesTypeInLoc(param, type,PageRequest.of(page-1, ELEMENTS));
		placesShow(pages,model);
		model.addAttribute("type", type);
		model.addAttribute("search", true);
		return "places";
	}
	
	@GET
	@RequestMapping(value="/places/cat/{type}/search/{searchLoc}/sortUp/{page}")
	public String placesInLocSortbyUp(@PathVariable("type")String type,@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model)
	{
		Page<Places> pages=placesService.findPlacesTypeInLoc(param, type,PageRequest.of(page-1, ELEMENTS));
		placesShowSort(pages,model);
		model.addAttribute("type", type);
		return "places";
	}
	
	public void placesShowSort(Page<Places> pages,Model model)
	{
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		ArrayList<Places> placesList=new ArrayList<Places> (pages.getContent());
		Collections.sort(placesList,new SortbyLikes());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
	}
	
	public void placesShow(Page<Places> pages,Model model)
	{
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		ArrayList<Places> placesList=new ArrayList<Places> (pages.getContent());
		Collections.reverse(placesList);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("placesList",placesList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		model.addAttribute("email",new Email());
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/{page}")
	public String placeDesc(@PathVariable("type") String type,@PathVariable("name") String name,@PathVariable("page") int page, Model model)
	{
		Places place=placesService.findPlaceByName(name);
		if(UserUtilities.getLoggedUser()!=null)
		{
			model.addAttribute("user",UserUtilities.getLoggedUser());
		Likes like=placesService.findLikeByPlaceAndUser(name,placesService.getUsername(UserUtilities.getLoggedUser()));
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
		model.addAttribute("username",placesService.getUsername(place.getAuthor()));
		return "placesDesc";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/like")
	public String placeLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,placesService.getUsername(UserUtilities.getLoggedUser()));
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
		return "redirect:/places/{type}/{name}/1";
	}
	
	@GET
	@RequestMapping(value="/places/{type}/{name}/dislike")
	public String placeDisLike(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,placesService.getUsername(UserUtilities.getLoggedUser()));
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
		return "redirect:/places/{type}/{name}/1";
	}
	
	@POST
	@RequestMapping(value="/places/{type}/{name}/addcom")
	public String addComment(@PathVariable("type") String type,@PathVariable("name") String name,Likes like, Model model)
	{
		Likes check=placesService.findLikeByPlaceAndUser(name,placesService.getUsername(UserUtilities.getLoggedUser()));
		if(check==null)
			placesService.saveComment(like, name);
		else 
			placesService.changeComment(like.getComment(), check.getId());
		return "redirect:/places/{type}/{name}/1";
	}
	
	@RequestMapping(value="/places/{type}/{name}/delcom")
	public String delComment(@PathVariable("type") String type,@PathVariable("name") String name,Likes like)
	{
		Likes likeHandle=placesService.findLikeByPlaceAndUser(name,placesService.getUsername(UserUtilities.getLoggedUser()));
		if(likeHandle.getLikes()!=0)
			placesService.clearComment(likeHandle.getId());
		else
		placesService.undoLike(likeHandle.getId());
		return "redirect:/places/{type}/{name}/1";
	}
}
