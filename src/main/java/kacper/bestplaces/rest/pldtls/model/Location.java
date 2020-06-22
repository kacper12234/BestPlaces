
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
    "address",
    "access"
})
public class Location {

    @JsonProperty("position")
    private List<Double> position = null;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("access")
    private List<Access> access = null;
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

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("access")
    public List<Access> getAccess() {
        return access;
    }

    @JsonProperty("access")
    public void setAccess(List<Access> access) {
        this.access = access;
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
