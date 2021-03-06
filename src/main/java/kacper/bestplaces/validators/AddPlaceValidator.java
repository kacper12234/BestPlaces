package kacper.bestplaces.validators;

import kacper.bestplaces.model.Place;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class AddPlaceValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Place.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "type", "error.placeType.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "error.placeName.empty");
		ValidationUtils.rejectIfEmpty(errors, "loc", "error.placeLoc.empty");
		ValidationUtils.rejectIfEmpty(errors, "descrp", "error.placeDescrp.empty");
	}
	
	public void validateFile(MultipartFile[] mFile,Errors error)
	{
		if(mFile==null)
			error.rejectValue("filename", "error.placeImg.empty");
	}

	public void validatePlaceExist(Place place, Errors error)
	{
		if(place != null)
			error.rejectValue("name", "error.placeExist");
	}
}
