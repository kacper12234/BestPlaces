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
import kacper.bestplaces.utilities.AppUtils;
import kacper.bestplaces.utilities.UserUtilities;
import kacper.bestplaces.validators.ChangePasswordValidator;
import kacper.bestplaces.validators.EditUserProfileValidator;
import kacper.bestplaces.validators.ResetPasswordValidator;

@Controller
public class ProfilController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlacesService placesService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	EmailSender emailSender;
	
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
		String returnPage=null;
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
					"https://bestplaces.azurewebsites.net/resetpass/" + user.getActivationCode();
			emailSender.sendEmail(user.getEmail(), "Wysłano prośbę o zresetowanie hasła", text);
			model.addAttribute("message", messageSource.getMessage("email.reset.sent", null,locale));
			List<Places> egPlaces=placesService.getEgPlaces();
			model.addAttribute("egPlaces", egPlaces);
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
		List<Places> egPlaces=placesService.getEgPlaces();
		model.addAttribute("egPlaces", egPlaces);
		model.addAttribute("email",new Email());
		return "index";
	}
	
	@POST
	@RequestMapping(value="/updatepass")
	public String changeUserPassword(User user, BindingResult result, Model model, Locale locale)
	{
		String returnPage=null;
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
			List<Places> egPlaces=placesService.getEgPlaces();
			model.addAttribute("egPlaces", egPlaces);
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
		String returnPage = null;
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
