
package kacper.bestplaces.model.pldtls;

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
    "text",
    "label",
    "isOpen",
    "structured"
})
public class OpeningHours {

    @JsonProperty("text")
    private String text;
    @JsonProperty("label")
    private String label;
    @JsonProperty("isOpen")
    private Boolean isOpen;
    @JsonProperty("structured")
    private List<Structured> structured = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("isOpen")
    public Boolean getIsOpen() {
        return isOpen;
    }

    @JsonProperty("isOpen")
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @JsonProperty("structured")
    public List<Structured> getStructured() {
        return structured;
    }

    @JsonProperty("structured")
    public void setStructured(List<Structured> structured) {
        this.structured = structured;
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
