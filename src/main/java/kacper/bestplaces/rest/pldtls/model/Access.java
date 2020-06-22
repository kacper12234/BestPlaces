
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
    "position",
    "accessType",
    "sideOfStreet"
})
public class Access {

    @JsonProperty("position")
    private List<Double> position = null;
    @JsonProperty("accessType")
    private String accessType;
    @JsonProperty("sideOfStreet")
    private String sideOfStreet;
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

    @JsonProperty("accessType")
    public String getAccessType() {
        return accessType;
    }

    @JsonProperty("accessType")
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @JsonProperty("sideOfStreet")
    public String getSideOfStreet() {
        return sideOfStreet;
    }

    @JsonProperty("sideOfStreet")
    public void setSideOfStreet(String sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
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
