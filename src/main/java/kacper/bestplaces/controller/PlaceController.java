package kacper.bestplaces.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.model.Rate;
import kacper.bestplaces.model.Reaction;
import kacper.bestplaces.model.User;
import kacper.bestplaces.service.AuthService;
import kacper.bestplaces.service.PlacesService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kacper.bestplaces.model.Email;
import kacper.bestplaces.validators.AddPlaceValidator;

@AllArgsConstructor
@Controller
public class PlaceController {

    private final PlacesService placesService;
    private final MessageSource messageSource;
    private final AuthService authService;

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
            List<Place> randomPlaces = placesService.getRandomPlaces();
            model.addAttribute("egPlaces", randomPlaces);
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

    @PUT
    @RequestMapping(value = "/placeupdated")
    public String updateplace(Place place) {

        placesService.updatePlace(place.getId(), place.getName(), place.getLoc(), place.getDescrp());
        return "redirect:/places/1";
    }

    @GET
    @RequestMapping(value = "/places/{param}/{name}/rate/{type}")
    public String ratePlace(@PathVariable("type") Rate rate, @PathVariable("name") String name, Reaction like, Model model, @PathVariable String param) {
        Place place = placesService.findPlaceByName(name);
        User user = placesService.getUser(authService.getLoggedUser());
        Optional<Reaction> optional = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny();
        if (optional.isPresent()) {
            Reaction reaction = optional.get();
            if (reaction.getRate() != rate)
                placesService.changeRate(rate, reaction.getId());
            else
                placesService.undoRate(reaction.getId());
        } else placesService.saveReaction(like, place, user, rate);
        return "redirect:/places/{param}/{name}/1";
    }

    @POST
    @RequestMapping(value = "/places/{param}/{name}/addcom")
    public String addComment(@PathVariable("param") String type, @PathVariable("name") String name, Reaction reaction, Model model) {
        Place place = placesService.findPlaceByName(name);
        User user = placesService.getUser(authService.getLoggedUser());
        Optional<Reaction> optional = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny();
        if (!optional.isPresent())
            placesService.saveComment(reaction, place, user, reaction.getComment());
        else
            placesService.changeComment(reaction.getComment(), optional.get().getId());
        return "redirect:/places/{param}/{name}/1";
    }

    @DELETE
    @RequestMapping(value = "/place/{type}/{name}/delcom")
    public String delComment(@PathVariable("name") String name, @PathVariable String type) {
        User user = placesService.getUser(authService.getLoggedUser());
        Place place = placesService.findPlaceByName(name);
        Reaction reaction = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny().get();
        if (reaction.getRate() != null)
            placesService.clearComment(reaction.getId());
        else
            placesService.undoRate(reaction.getId());
        return "redirect:/places/{type}/{name}/1";
    }

    @GET
    @RequestMapping(value = "/places/{type}/{name}/{page}")
    public String placeDesc(@PathVariable("type") String type, @PathVariable("name") String name, @PathVariable("page") int page, Model model) {
        User user = placesService.getUser(authService.getLoggedUser());
        Place place = placesService.findPlaceByName(name);
        if (user != null) {
            model.addAttribute("user", user.getEmail());
            Optional<Reaction> optionalReaction = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny();
            if (optionalReaction.isPresent())
                model.addAttribute("reaction", optionalReaction.get());
            else
                model.addAttribute("reaction", new Reaction());
        }
        Page<Reaction> pages = new PageImpl<>(place.getReactionList(), PageRequest.of(page - 1, 10), place.getReactionList().size());
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("rev", pages.getContent());
        model.addAttribute("place", place);
        model.addAttribute("email", new Email());
        return "placesDesc";
    }
}
