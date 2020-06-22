
package kacper.bestplaces.rest.pldtls.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "images",
    "editorials",
    "reviews",
    "ratings"
})
public class Media {

    @JsonProperty("images")
    private Images images;
    @JsonProperty("editorials")
    private Editorials editorials;
    @JsonProperty("reviews")
    private Reviews reviews;
    @JsonProperty("ratings")
    private Ratings ratings;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("images")
    public Images getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(Images images) {
        this.images = images;
    }

    @JsonProperty("editorials")
    public Editorials getEditorials() {
        return editorials;
    }

    @JsonProperty("editorials")
    public void setEditorials(Editorials editorials) {
        this.editorials = editorials;
    }

    @JsonProperty("reviews")
    public Reviews getReviews() {
        return reviews;
    }

    @JsonProperty("reviews")
    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    @JsonProperty("ratings")
    public Ratings getRatings() {
        return ratings;
    }

    @JsonProperty("ratings")
    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
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
