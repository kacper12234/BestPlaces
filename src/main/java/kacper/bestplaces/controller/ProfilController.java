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
import kacper.bestplaces.utilities.UserUtilities;
import kacper.bestplaces.validators.ChangePasswordValidator;
import kacper.bestplaces.validators.EditUserProfileValidator;
import kacper.bestplaces.validators.ResetPasswordValidator;

import static kacper.bestplaces.utilities.AppUtils.BASE_URL;

@AllArgsConstructor
@Controller
public class ProfilController {

	private final UserService userService;
	private final PlacesService placesService;
	private final MessageSource messageSource;
	private final EmailSender emailSender;
	
	@GET
	@RequestMapping(value = "/profil")
	public String showUserProfilePage(Model model) {
		String username = UserUtilities.getLoggedUser();
		
		User user = userService.findUserByEmail(username);
		
		int nrRoli = user.getRoles().iterator().next().getId();
		
		user.setNrRoli(nrRoli);
		
		model.addAttribute("user", user);
		
		return "profil";
	}
	
	@GET
	@RequestMapping(value="/editpassword")
	public String editUserPassword(Model model)
	{
		String username=UserUtilities.getLoggedUser();
		User user=userService.findUserByEmail(username);
		model.addAttribute("user", user);
		return "editpassword";
	}
	
	@GET
	@RequestMapping(value="/resetpass")
	public String resetPassword(Model model)
	{
		model.addAttribute("user", new User());
		return "resetPassword";
	}
	
	@POST
	@RequestMapping(value="/resetpass/sent")
	public String resetPassword(User user, BindingResult result, Model model, Locale locale)
	{
		String returnPage;
		new ResetPasswordValidator().validate(user, result);
		new ResetPasswordValidator().validateUserExist(userService.findUserByEmail(user.getEmail()), result);
		if(result.hasErrors())
		{
			returnPage="resetPassword";
		}
		else
		{
			user=userService.findUserByEmail(user.getEmail());
			String text="Jeżeli chcesz zresetować hasło naciśnij link "+
					BASE_URL +"/resetpass/" + user.getActivationCode();
			emailSender.sendEmail(user.getEmail(), "Wysłano prośbę o zresetowanie hasła", text);
			model.addAttribute("message", messageSource.getMessage("email.reset.sent", null,locale));
			List<Place> randomPlaces=placesService.getRandomPlaces();
			model.addAttribute("randomPlaces", randomPlaces);
			model.addAttribute("email",new Email());
			returnPage="index";
		}
		return returnPage;
	}
		
	@POST
	@RequestMapping(value="/resetpass/{activationCode}")
	public String resetPassword(@PathVariable("activationCode") String activationCode, Model model, Locale locale)
	{
		String pass=AppUtils.randomPasswordGenerator();
		userService.resetUserPassword(pass, activationCode);
		model.addAttribute("message", messageSource.getMessage("user.reset.success", null,locale)+pass+" "+messageSource.getMessage("user.reset.successcd", null,locale));
		List<Place> randomPlaces=placesService.getRandomPlaces();
		model.addAttribute("randomPlaces", randomPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
	@POST
	@RequestMapping(value="/updatepass")
	public String changeUserPassword(User user, BindingResult result, Model model, Locale locale)
	{
		String returnPage;
		new ChangePasswordValidator().validate(user, result);
		new ChangePasswordValidator().checkPasswords(user.getNewPassword(), result);
		if(result.hasErrors())
		{
			returnPage="editPassword";
		}
		else
		{
			userService.updateUserPassword(user.getNewPassword(), user.getEmail());
			returnPage="index";
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
			model.addAttribute("email",new Email());
			List<Place> randomPlaces=placesService.getRandomPlaces();
			model.addAttribute("randomPlaces", randomPlaces);
		}
		
		return returnPage;
	}
	
	@GET
	@RequestMapping(value = "/editprofil")
	public String changeUserData(Model model) {
		String username = UserUtilities.getLoggedUser();
		User user = userService.findUserByEmail(username);
		model.addAttribute("user", user);
		return "editprofil";
	}

	@POST
	@RequestMapping(value = "/updateprofil")
	public String changeUserDataAction(User user, BindingResult result, Model model, Locale locale) {
		String returnPage;
		new EditUserProfileValidator().validate(user, result);
		if (result.hasErrors()) {
			returnPage = "editprofil";
		} else {
			userService.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), user.getId());
			model.addAttribute("message", messageSource.getMessage("profilEdit.success", null, locale));
			returnPage = "afteredit";
		}
		return returnPage;
	}
}
