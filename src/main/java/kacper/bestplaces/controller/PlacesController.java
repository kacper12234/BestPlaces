package kacper.bestplaces.controller;

import java.util.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.model.Reaction;
import kacper.bestplaces.model.Type;
import kacper.bestplaces.service.PlacesService;
import kacper.bestplaces.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import kacper.bestplaces.model.Email;
import kacper.bestplaces.utilities.UserUtilities;


@Controller
@AllArgsConstructor
public class PlacesController {

    private final PlacesService placesService;

    private final int ELEMENTS = 10;

    private class SortbyLikes implements Comparator<Place> {
        @Override
        public int compare(Place p1, Place p2) {
            return p2.getRate(Type.LIKE) - p1.getRate(Type.LIKE);
        }
    }

    @GET
    @RequestMapping(value = "/places/{page}")
    public String places(@PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.getPlaces(PageRequest.of(page - 1, ELEMENTS));
        placesShow(pages, model);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/sortDate/{page}")
    public String placesSortbyDate(@PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.getPlaces(PageRequest.of(page - 1, ELEMENTS));
        placesShowSort(pages, model);
        model.addAttribute("sort", true);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/cat/{type}/{page}")
    public String placesType(@PathVariable("page") int page, @PathVariable("type") String type, Model model) {
        Page<Place> pages = placesService.getPlacesByType(type, PageRequest.of(page - 1, ELEMENTS));
        placesShow(pages, model);
        model.addAttribute("type", type);
        return "places";
    }


    @GET
    @RequestMapping(value = "/places/cat/{type}/sortDate/{page}")
    public String placesTypeSortbyDate(@PathVariable("page") int page, @PathVariable("type") String type, Model model) {
        Page<Place> pages = placesService.getPlacesByType(type, PageRequest.of(page - 1, ELEMENTS));
        placesShowSort(pages, model);
        model.addAttribute("type", type);
        model.addAttribute("sort", true);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/search/{searchLoc}/{page}")
    public String placesInLoc(@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.findInLoc(param, PageRequest.of(page - 1, ELEMENTS));
        placesShow(pages, model);
        model.addAttribute("search", param);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/search/{searchLoc}/sortDate/{page}")
    public String placesInLocSortbyDate(@PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.findInLoc(param, PageRequest.of(page - 1, ELEMENTS));
        placesShowSort(pages, model);
        model.addAttribute("sort", true);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/cat/{type}/search/{searchLoc}/{page}")
    public String placesInLoc(@PathVariable("type") String type, @PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.findPlacesTypeInLoc(param, type, PageRequest.of(page - 1, ELEMENTS));
        placesShow(pages, model);
        model.addAttribute("type", type);
        model.addAttribute("search", true);
        return "places";
    }

    @GET
    @RequestMapping(value = "/places/cat/{type}/search/{searchLoc}/sortDate/{page}")
    public String placesInLocSortbyDate(@PathVariable("type") String type, @PathVariable("searchLoc") String param, @PathVariable("page") int page, Model model) {
        Page<Place> pages = placesService.findPlacesTypeInLoc(param, type, PageRequest.of(page - 1, ELEMENTS));
        placesShowSort(pages, model);
        model.addAttribute("type", type);
        model.addAttribute("sort", true);
        return "places";
    }

    private void placesShow(Page<Place> pages, Model model) {
        List<Place> placesList = new ArrayList<>(pages.getContent());
        placesList.sort(new SortbyLikes());
        bindPlaces(pages,model,placesList);
    }

    private void placesShowSort(Page<Place> pages, Model model) {
        List<Place> placesList = new ArrayList<>(pages.getContent());
        Collections.reverse(placesList);
        bindPlaces(pages,model,placesList);
        model.addAttribute("sort", true);
    }

    private void bindPlaces(Page<Place> pages, Model model, List<Place> placesList){
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("placesList", placesList);
        model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
        model.addAttribute("email", new Email());
    }


    @GET
    @RequestMapping(value = "/places/{type}/{name}/{page}")
    public String placeDesc(@PathVariable("type") String type, @PathVariable("name") String name, @PathVariable("page") int page, Model model) {
        User user = placesService.getUser(UserUtilities.getLoggedUser());
        Place place = placesService.findPlaceByName(name);
        if (user != null) {
            model.addAttribute("user", user.getEmail());
            Optional<Reaction> optionalReaction = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny();
            if (optionalReaction.isPresent())
                model.addAttribute("reaction", optionalReaction.get());
            else
                model.addAttribute("reaction", new Reaction());
        }
        Page<Reaction> pages = new PageImpl<>(place.getReactionList(), PageRequest.of(page - 1, ELEMENTS), place.getReactionList().size());
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        //List<Reaction> rates = place.getReactionList();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("rev", pages.getContent());
        model.addAttribute("place", place);
        model.addAttribute("email", new Email());
        return "placesDesc";
    }


    @GET
    @RequestMapping(value = "/places/{param}/{name}/rate/{type}")
    public String ratePlace(@PathVariable("type") Type type, @PathVariable("name") String name, Reaction like, Model model, @PathVariable String param) {
        Place place = placesService.findPlaceByName(name);
        User user = placesService.getUser(UserUtilities.getLoggedUser());
        Optional<Reaction> optional = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny();
        if (optional.isPresent()) {
            Reaction reaction = optional.get();
            if (reaction.getType() != type)
                placesService.changeRate(type, reaction.getId());
            else
                placesService.undoRate(reaction.getId());
        } else placesService.saveReaction(like, place, user, type);
        return "redirect:/places/{param}/{name}/1";
    }

    @POST
    @RequestMapping(value = "/places/{param}/{name}/addcom")
    public String addComment(@PathVariable("param") String type, @PathVariable("name") String name, Reaction reaction, Model model) {
        Place place = placesService.findPlaceByName(name);
        User user = placesService.getUser(UserUtilities.getLoggedUser());
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
        User user = placesService.getUser(UserUtilities.getLoggedUser());
        Place place = placesService.findPlaceByName(name);
        Reaction reaction = user.getReactionList().stream().filter(place.getReactionList()::contains).findAny().get();
        if (reaction.getType() != null)
            placesService.clearComment(reaction.getId());
        else
            placesService.undoRate(reaction.getId());
        return "redirect:/places/{type}/{name}/1";
    }
}
