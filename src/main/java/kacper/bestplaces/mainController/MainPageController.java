package kacper.bestplaces.mainController;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kacper.bestplaces.emailSender.Email;
import kacper.bestplaces.emailSender.EmailSender;
import kacper.bestplaces.places.Places;
import kacper.bestplaces.places.PlacesService;
import kacper.bestplaces.utilities.UserUtilities;

@Controller
public class MainPageController {
	
	//private static final Logger LOG=LoggerFactory.getLogger(MainPageController.class);
	
	@Autowired
	private PlacesService placesService;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private MessageSource messageSource;
	
	@GET
	@RequestMapping(value = {"/", "/index"})
	public String showMainPage(Model model) {	
		List<Places> egPlaces=placesService.getEgPlaces();
		model.addAttribute("egPlaces", egPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
	@POST
	@RequestMapping(value="/send")
	public String sendEmail(Email email,Locale locale,Model model)
	{
		List<Places> egPlaces=placesService.getEgPlaces();
		model.addAttribute("egPlaces", egPlaces);
		emailSender.sendEmail("kacpermochniej1999@gmail.com",email.getType()+" "+UserUtilities.getLoggedUser(), email.getMsg());
		model.addAttribute("message", messageSource.getMessage("mail.sent", null, locale));
		return "index";
	}

}
