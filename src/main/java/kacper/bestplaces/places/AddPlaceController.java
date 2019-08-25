package kacper.bestplaces.places;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
}
