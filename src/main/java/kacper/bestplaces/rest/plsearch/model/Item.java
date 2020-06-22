
package kacper.bestplaces.rest.plsearch.model;

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
    "position",
    "distance",
    "title",
    "averageRating",
    "category",
    "icon",
    "vicinity",
    "having",
    "type",
    "href",
    "id",
    "openingHours",
    "alternativeNames"
})
public class Item {

    @JsonProperty("position")
    private List<Double> position = null;
    @JsonProperty("distance")
    private Integer distance;
    @JsonProperty("title")
    private String title;
    @JsonProperty("averageRating")
    private Double averageRating;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("vicinity")
    private String vicinity;
    @JsonProperty("having")
    private List<Object> having = null;
    @JsonProperty("type")
    private String type;
    @JsonProperty("href")
    private String href;
    @JsonProperty("id")
    private String id;
    @JsonProperty("openingHours")
    private OpeningHours openingHours;
    @JsonProperty("alternativeNames")
    private List<AlternativeName> alternativeNames = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("position")
    public List<Double> getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(List<Double> position) {
        this.position = position;
    }

    @JsonProperty("distance")
    public Integer getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("averageRating")
    public Double getAverageRating() {
        return averageRating;
    }

    @JsonProperty("averageRating")
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("vicinity")
    public String getVicinity() {
        return vicinity;
    }

    @JsonProperty("vicinity")
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @JsonProperty("having")
    public List<Object> getHaving() {
        return having;
    }

    @JsonProperty("having")
    public void setHaving(List<Object> having) {
        this.having = having;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("openingHours")
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    @JsonProperty("openingHours")
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    @JsonProperty("alternativeNames")
    public List<AlternativeName> getAlternativeNames() {
        return alternativeNames;
    }

    @JsonProperty("alternativeNames")
    public void setAlternativeNames(List<AlternativeName> alternativeNames) {
        this.alternativeNames = alternativeNames;
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
