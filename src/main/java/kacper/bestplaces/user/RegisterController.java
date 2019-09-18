package kacper.bestplaces.user;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kacper.bestplaces.emailSender.Email;
import kacper.bestplaces.emailSender.EmailSender;
import kacper.bestplaces.places.Places;
import kacper.bestplaces.places.PlacesService;
import kacper.bestplaces.utilities.AppdemoUtils;
import kacper.bestplaces.validators.UserRegisterValidator;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlacesService placesService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	EmailSender emailSender;
	
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
				
		String returnPage = null;
		
		User userExist = userService.findUserByEmail(user.getEmail());
		
		new UserRegisterValidator().validateEmailExist(userExist, result);
				
		new UserRegisterValidator().validate(user, result);
				
		if (result.hasErrors()) {
			returnPage = "register";
		} else {
			user.setActivationCode(AppdemoUtils.randomStringGenerator());
			String content = "Wymagane potwierdzenie rejestracji. Kliknij w poniższy link aby aktywować konto: " +
					"https://bestplaces.azurewebsites.net/activatelink/" + user.getActivationCode();
			emailSender.sendEmail(user.getEmail(), "Potwierdzenie rejestracji", content);
			userService.saveUser(user);
			List<Places> egPlaces=placesService.getEgPlaces();
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
		List<Places> egPlaces=placesService.getEgPlaces();
		model.addAttribute("egPlaces", egPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
}
