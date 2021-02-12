package kacper.bestplaces.controller;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.service.UserService;
import kacper.bestplaces.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kacper.bestplaces.model.Email;
import kacper.bestplaces.service.EmailSender;
import kacper.bestplaces.service.PlacesService;
import kacper.bestplaces.utilities.AppUtils;
import kacper.bestplaces.validators.UserRegisterValidator;

import static kacper.bestplaces.utilities.ServletUtils.BASE_URL;


@AllArgsConstructor
@Controller
public class RegisterController {

	private final UserService userService;
	private final PlacesService placesService;
	private final MessageSource messageSource;
	private final EmailSender emailSender;
	
	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	@POST
	@RequestMapping(value = "/adduser")
	public String registerAction(User user, BindingResult result, Model model, Locale locale) {
				
		String returnPage;
		
		User userExist = userService.findUserByEmail(user.getEmail());
		
		new UserRegisterValidator().validateEmailExist(userExist, result);
				
		new UserRegisterValidator().validate(user, result);
				
		if (result.hasErrors()) {
			returnPage = "register";
		} else {
			user.setActivationCode(AppUtils.randomStringGenerator());
			String content = "Wymagane potwierdzenie rejestracji. Kliknij w poniższy link aby aktywować konto: " +
					BASE_URL+"/activatelink/" + user.getActivationCode();
			emailSender.sendEmail(user.getEmail(), "Potwierdzenie rejestracji", content);
			userService.saveUser(user);
			List<Place> egPlaces=placesService.getRandomPlaces();
			model.addAttribute("egPlaces", egPlaces);
			model.addAttribute("message", messageSource.getMessage("user.register.success.email", null, locale));
			model.addAttribute("user", new User());
			model.addAttribute("email",new Email());
			returnPage = "index";
		}
		
		return returnPage;
	}

	@POST
	@RequestMapping(value="/activatelink/{activationCode}")
	public String activateAccount(@PathVariable("activationCode") String activationCode, Model model, Locale locale)
	{
		userService.updateUserActivation(1, activationCode);
		model.addAttribute("message", messageSource.getMessage("user.register.success", null,locale));
		List<Place> egPlaces=placesService.getRandomPlaces();
		model.addAttribute("egPlaces", egPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
}
