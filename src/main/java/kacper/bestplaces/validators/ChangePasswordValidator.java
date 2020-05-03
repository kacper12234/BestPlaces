package kacper.bestplaces.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kacper.bestplaces.constants.AppDemoConstants;
import kacper.bestplaces.user.User;
import kacper.bestplaces.utilities.AppUtils;

public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		@SuppressWarnings("unused")
		User u = (User) obj;

		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");

	}

	public void checkPasswords(String newPass, Errors errors) {

		if (!newPass.equals(null)) {
			boolean isMatch = AppUtils.checkEmailOrPassword(AppDemoConstants.passwordPattern, newPass);
			if(!isMatch) {
				errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
			}
		}
	}
}