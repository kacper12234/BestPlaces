package kacper.bestplaces.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import kacper.bestplaces.model.User;


public class ResetPasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		@SuppressWarnings("unused")
		User u = (User) obj;

		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");

	}

	public void validateUserExist(User user,Errors error)
	{
		if(user == null)
			error.rejectValue("name", "error.userNotExist");
	}
	
	}