package kacper.bestplaces.places;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.emailSender.Email;
import kacper.bestplaces.validators.AddPlaceValidator;

@Controller
public class AddPlaceController {

	
	@Autowired
	private PlacesService placesService;
	
	@Autowired
	MessageSource messageSource;
	
	@GET
	@RequestMapping(value="/addplace")
	public String addPlaceForm(Model model)
	{
		Places place=new Places();
		model.addAttribute("places", place);
		return "addplace";
	}
	
	
	@POST
	@RequestMapping(value="/placeadded")
	public String addPlaceAction(Places places,@RequestParam("filename") MultipartFile mFile, BindingResult result, Model model, Locale locale) {
		String returnPage=null;
		Places placeExist=placesService.findPlaceByName(places.getName());
		new AddPlaceValidator().validatePlaceExist(placeExist, result);
		new AddPlaceValidator().validateFile(mFile, result);
		new AddPlaceValidator().validate(places, result);
		if(result.hasErrors())
		{
			returnPage="addplace";
		}
		else
		{
				placesService.savePlace(places,mFile);
				List<Places> egPlaces=placesService.getEgPlaces();
				model.addAttribute("egPlaces", egPlaces);
				model.addAttribute("email",new Email());
				model.addAttribute("message", messageSource.getMessage("place.add.success",null, locale));
			returnPage="index";
		}
		return returnPage;
	}

		@GET
		@RequestMapping(value="/places/{mail}/{name}/edit")
		@PreAuthorize("#mail == authentication.name or hasRole('ROLE_ADMIN')")
		public String editPlaceAction(Places place,@PathVariable("mail") String mail,@PathVariable("name") String name, Model model) {
		model.addAttribute("place", placesService.findPlaceByName(name));
		place.setLink(placesService.findPlaceByName(name).getLink());
		return "editplace";
		}
		
		@DELETE
		@RequestMapping(value="/places/{mail}/{name}/delete")
		@PreAuthorize("#mail == authentication.name or hasRole('ROLE_ADMIN')")
		public String deletePlace(@PathVariable("mail") String mail,@PathVariable("name") String name)
		{
			placesService.deletePlace(name);
			return "redirect:/places/1";
		}
		
		@POST
		@RequestMapping(value="/placeupdated")
		public String updateplace(Places place)
		{
		
				placesService.updatePlace(place.getLink(), place.getName(), place.getLoc(), place.getDescrp());
				return "redirect:/places/1";
		}
}
