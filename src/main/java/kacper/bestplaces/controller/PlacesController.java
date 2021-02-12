package kacper.bestplaces.controller;

import java.util.*;
import javax.ws.rs.GET;

import kacper.bestplaces.model.Place;
import kacper.bestplaces.model.Rate;
import kacper.bestplaces.service.PlacesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import kacper.bestplaces.model.Email;

@Controller
@AllArgsConstructor
public class PlacesController {

    private final PlacesService placesService;

    private final int ELEMENTS = 10;

    private class SortbyLikes implements Comparator<Place> {
        @Override
        public int compare(Place p1, Place p2) {
            return p2.getRate(Rate.LIKE) - p1.getRate(Rate.LIKE);
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

}
