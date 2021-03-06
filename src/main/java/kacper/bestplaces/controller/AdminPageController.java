package kacper.bestplaces.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flickr4java.flickr.FlickrException;

import kacper.bestplaces.model.Email;
import kacper.bestplaces.service.PlacesService;
import kacper.bestplaces.service.RestApiService;
import kacper.bestplaces.model.User;

@AllArgsConstructor
@Controller
@Secured(value= {"ROLE_ADMIN"})
public class AdminPageController {
	
	private static final int ELEMENTS=10;

	private final AdminService adminService;
	private final MessageSource messageSource;
	private final RestApiService restApiService;
	private final PlacesService placesService;
	
	@GET
	@RequestMapping(value="/admin/users/{page}")
	public String openAdminUsersPage(@PathVariable("page") int page, Model model)
	{
		Page<User> pages=getAllUsersPageable(page-1);
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<User> userList=pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("userList",userList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		return "admin/users";
	}
	
	@POST
	@RequestMapping(value="/admin/places")
	public String getPlaces(Model model) throws IOException, FlickrException
	{
		restApiService.getPlaces();
		List<Place> egPlaces=placesService.getRandomPlaces();
		model.addAttribute("egPlaces", egPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
		
	@GET
	@RequestMapping(value="/admin/users/search/{searchWord}/{page}")
	public String openSearchAllUsersPage(@PathVariable("searchWord") String searchWord,@PathVariable("page") int page, Model model)
	{
		Page<User> pages=getSearchUsersPageable(searchWord,page-1);
		int totalPages=pages.getTotalPages();
		int currentPage=pages.getNumber();
		List<User> userList=pages.getContent();
		model.addAttribute("totalSearchPages", totalPages);
		model.addAttribute("currentSearchPage", currentPage+1);
		model.addAttribute("userSearchList",userList);
		model.addAttribute("recordStartCounter2", currentPage*ELEMENTS);
		model.addAttribute("searchWord", searchWord);
		return "admin/usersearch";
	}
	
	@GET
	@RequestMapping(value = "/admin/users/edit/{id}")
	@Secured(value= {"ROLE_ADMIN"})
	public String getUserToEdit(@PathVariable("id") int id, Model model)
	{
		User user=adminService.findUserById(id);
		
		Map<Integer, String> roleMap=prepareRoleMap();
		
		Map<Integer, String> activityMap=prepareActivityMap();
		
		int role=user.getRoles().iterator().next().getId();
		user.setRoleId(role);
		
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		model.addAttribute("user", user);
		
		return "admin/useredit";
	}
	
	@PUT
	@RequestMapping(value = "/admin/updateuser/{id}")
	public String updateUser(@PathVariable("id") int id, User user)
	{
		int roldeId=user.getRoleId();
		int ifActive=user.getRoleId();
		adminService.updateUser(id, roldeId, ifActive);
		return "redirect:/admin/users/1";
	}
	
	@DELETE
	@RequestMapping(value="/admin/users/delete/{id}")
	public String deleteUser(@PathVariable("id") int id)
	{
		adminService.deleteUserById(id);
		return "redirect:/admin/users/1";
	}
	
	private Page<User> getAllUsersPageable(int page){
		Page<User> pages=adminService.findAll(PageRequest.of(page, ELEMENTS));
		for(User users:pages)
		{
			int roleId=users.getRoles().iterator().next().getId();
			users.setRoleId(roleId);
		}
		return pages;
	}
	
	private Page<User> getSearchUsersPageable(String searchWord,int page){
		Page<User> pages=adminService.findAllSearch(searchWord,PageRequest.of(page, ELEMENTS));
		for(User users:pages)
		{
			int roleId=users.getRoles().iterator().next().getId();
			users.setRoleId(roleId);
		}
		return pages;
	}
	
	public Map<Integer, String> prepareRoleMap()
	{
		Locale locale=Locale.getDefault();
		Map<Integer, String> roleMap= new HashMap<>();
		roleMap.put(1, messageSource.getMessage("word.admin",null, locale));
		roleMap.put(2, messageSource.getMessage("word.user",null, locale));
		return roleMap;
	}
	
	public Map<Integer, String> prepareActivityMap()
	{
		Locale locale=Locale.getDefault();
		Map<Integer, String> activityMap= new HashMap<>();
		activityMap.put(0, messageSource.getMessage("word.nie",null, locale));
		activityMap.put(1, messageSource.getMessage("word.tak",null, locale));
		return activityMap;
	}
}
