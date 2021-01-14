package kacper.bestplaces.places;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.emailSender.Email;
import kacper.bestplaces.validators.AddPlaceValidator;

@AllArgsConstructor
@Controller
public class AddPlaceController {

    private final PlacesService placesService;
    private final MessageSource messageSource;

    @GET
    @RequestMapping(value = "/addplace")
    public String addPlaceForm(Model model) {
        Place place = new Place();
        model.addAttribute("place", place);
        return "addplace";
    }


    @POST
    @RequestMapping(value = "/placeadded")
    public String addPlaceAction(Place place, @RequestParam("filename[]") MultipartFile[] mFile, BindingResult result, Model model, Locale locale) {
        String returnPage;
        Place placeExist = placesService.findPlaceByName(place.getName());
        new AddPlaceValidator().validatePlaceExist(placeExist, result);
        new AddPlaceValidator().validateFile(mFile, result);
        new AddPlaceValidator().validate(place, result);
        if (result.hasErrors()) {
            returnPage = "addplace";
        } else {
            placesService.savePlace(place, mFile);
            List<Place> egPlaces = placesService.getEgPlaces();
            model.addAttribute("egPlaces", egPlaces);
            model.addAttribute("email", new Email());
            model.addAttribute("message", messageSource.getMessage("place.add.success", null, locale));
            returnPage = "index";
        }
        return returnPage;
    }

    @GET
    @RequestMapping(value = "/place/{mail}/{name}/edit")
    @PreAuthorize("#mail == authentication.name or hasRole('ROLE_ADMIN')")
    public String editPlaceAction(Place place, @PathVariable("mail") String mail, @PathVariable("name") String name, Model model) {
        model.addAttribute("place", placesService.findPlaceByName(name));
        return "editplace";
    }

    @DELETE
    @RequestMapping(value = "/place/{mail}/{name}/delete")
    @PreAuthorize("#mail == authentication.name or hasRole('ROLE_ADMIN')")
    public String deletePlace(@PathVariable("mail") String mail, @PathVariable("name") String name) throws IOException {
        placesService.deletePlace(name);
        return "redirect:/places/1";
    }

    @POST
    @RequestMapping(value = "/place/{mail}/{name}/phsend")
    public String addPhotos(@RequestParam("filename[]") MultipartFile[] mFile, @PathVariable("mail") String mail, @PathVariable("name") String name) {
        placesService.addPhotos(name, mFile);
        return "redirect:/places/{mail}/{name}/1";
    }

    @RequestMapping(value = "/place/{mail}/{name}/delph")
    public String delPhoto(@RequestParam("photonr") int nr, @PathVariable("name") String name) {
        placesService.delPhoto(nr, name);
        return "redirect:/places/{mail}/{name}/1";
    }

    @POST
    @RequestMapping(value = "/placeupdated")
    public String updateplace(Place place) {

        placesService.updatePlace(place.getId(), place.getName(), place.getLoc(), place.getDescrp());
        return "redirect:/places/1";
    }
}
