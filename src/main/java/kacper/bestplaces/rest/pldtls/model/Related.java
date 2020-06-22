
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
    "recommended",
    "public-transport"
})
public class Related {

    @JsonProperty("recommended")
    private Recommended recommended;
    @JsonProperty("public-transport")
    private PublicTransport publicTransport;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("recommended")
    public Recommended getRecommended() {
        return recommended;
    }

    @JsonProperty("recommended")
    public void setRecommended(Recommended recommended) {
        this.recommended = recommended;
    }

    @JsonProperty("public-transport")
    public PublicTransport getPublicTransport() {
        return publicTransport;
    }

    @JsonProperty("public-transport")
    public void setPublicTransport(PublicTransport publicTransport) {
        this.publicTransport = publicTransport;
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
