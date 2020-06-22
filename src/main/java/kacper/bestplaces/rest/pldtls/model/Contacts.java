
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
    "phone",
    "website"
})
public class Contacts {

    @JsonProperty("phone")
    private List<Phone> phone = null;
    @JsonProperty("website")
    private List<Website> website = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("phone")
    public List<Phone> getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    @JsonProperty("website")
    public List<Website> getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(List<Website> website) {
        this.website = website;
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
