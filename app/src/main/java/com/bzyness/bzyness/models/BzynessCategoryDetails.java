package com.bzyness.bzyness.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "categories"
})
public class BzynessCategoryDetails {

    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("categories")
    private List<CategoryOfBzyness> categories = null;


    @JsonProperty("error")
    public Boolean getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Boolean error) {
        this.error = error;
    }


    @JsonProperty("categories")
    public List<CategoryOfBzyness> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<CategoryOfBzyness> categories) {
        this.categories = categories;
    }

    public class CategoryOfBzyness {

        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("icon")
        private Object icon;
        @JsonProperty("typeId")
        private String typeid;




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

        @JsonProperty("typeId")
        public String getTypeid() {
            return typeid;
        }

        @JsonProperty("typeId")
        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }


    }


}