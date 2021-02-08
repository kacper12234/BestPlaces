package kacper.bestplaces.controller;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kacper.bestplaces.model.Email;
import kacper.bestplaces.service.EmailSender;
import kacper.bestplaces.model.Place;
import kacper.bestplaces.service.PlacesService;
import kacper.bestplaces.utilities.UserUtilities;

@AllArgsConstructor
@Controller
public class MainPageController {

	private final PlacesService placesService;
	private final EmailSender emailSender;
	private final MessageSource messageSource;
	
	@GET
	@RequestMapping(value = {"/", "/index"})
	public String showMainPage(Model model) {	
		List<Place> randomPlaces=placesService.getRandomPlaces();
		model.addAttribute("randomPlaces", randomPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
	@POST
	@RequestMapping(value="/send")
	public String sendEmail(Email email,Locale locale,Model model)
	{
		List<Place> randomPlaces=placesService.getRandomPlaces();
		model.addAttribute("randomPlaces", randomPlaces);
		emailSender.sendEmail("bestplaces.noreply@gmail.com",email.getType()+" "+UserUtilities.getLoggedUser(), email.getMsg());
		model.addAttribute("message", messageSource.getMessage("mail.sent", null, locale));
		return "index";
	}

}
