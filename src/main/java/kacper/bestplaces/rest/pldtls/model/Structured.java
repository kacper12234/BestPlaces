
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
    "start",
    "duration",
    "recurrence"
})
public class Structured {

    @JsonProperty("start")
    private String start;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("recurrence")
    private String recurrence;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("recurrence")
    public String getRecurrence() {
        return recurrence;
    }

    @JsonProperty("recurrence")
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
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
