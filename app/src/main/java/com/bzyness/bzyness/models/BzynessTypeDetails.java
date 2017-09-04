package com.bzyness.bzyness.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "typesOfBzyness",
})
public class BzynessTypeDetails {

    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("typesOfBzyness")
    private List<TypesOfBzyness> typesOfBzyness = null;



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


    public class TypesOfBzyness {

        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("icon")
        private Object icon;


        @JsonProperty("id")
        public String getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("icon")
        public Object getIcon() {
            return icon;
        }

        @JsonProperty("icon")
        public void setIcon(Object icon) {
            this.icon = icon;
        }


    }



}