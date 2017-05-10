package com.bzyness.bzyness.models;


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
        "error",
        "typesOfBzyness",
        "categories"
})
public class BusinessTypeDetails {

    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("typesOfBzyness")
    private List<TypesOfBzyness> typesOfBzyness = null;
    @JsonProperty("categories")
    private List<TypesOfBzyness> categories = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("error")
    public Boolean getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Boolean error) {
        this.error = error;
    }

    @JsonProperty("typesOfBzyness")
    public List<TypesOfBzyness> getTypesOfBzyness() {
        return typesOfBzyness;
    }

    @JsonProperty("typesOfBzyness")
    public void setTypesOfBzyness(List<TypesOfBzyness> typesOfBzyness) {
        this.typesOfBzyness = typesOfBzyness;
    }

    @JsonProperty("categories")
    public List<TypesOfBzyness> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<TypesOfBzyness> categories) {
        this.categories = categories;
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