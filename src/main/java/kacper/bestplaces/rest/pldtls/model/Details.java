
package kacper.bestplaces.rest.pldtls.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "placeId",
    "view",
    "location",
    "contacts",
    "categories",
    "icon",
    "media",
    "extended",
    "related"
})
public class Details {

    @JsonProperty("name")
    private String name;
    @JsonProperty("placeId")
    private String placeId;
    @JsonProperty("view")
    private String view;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("contacts")
    private Contacts contacts;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("media")
    private Media media;
    @JsonProperty("extended")
    private Extended extended;
    @JsonProperty("related")
    private Related related;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("placeId")
    public String getPlaceId() {
        return placeId;
    }

    @JsonProperty("placeId")
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @JsonProperty("view")
    public String getView() {
        return view;
    }

    @JsonProperty("view")
    public void setView(String view) {
        this.view = view;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("contacts")
    public Contacts getContacts() {
        return contacts;
    }

    @JsonProperty("contacts")
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("media")
    public Media getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Media media) {
        this.media = media;
    }

    @JsonProperty("extended")
    public Extended getExtended() {
        return extended;
    }

    @JsonProperty("extended")
    public void setExtended(Extended extended) {
        this.extended = extended;
    }

    @JsonProperty("related")
    public Related getRelated() {
        return related;
    }

    @JsonProperty("related")
    public void setRelated(Related related) {
        this.related = related;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
