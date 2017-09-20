package com.bzyness.bzyness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shwetakumar on 9/20/17.
 */

public class BzynessList {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("bzynesses")
    @Expose
    private List<Bzyness> bzynesses = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Bzyness> getBzynesses() {
        return bzynesses;
    }

    public void setBzynesses(List<Bzyness> bzynesses) {
        this.bzynesses = bzynesses;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public class Bzyness {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("aliasName")
        @Expose
        private String aliasName;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("typeId")
        @Expose
        private Integer typeId;
        @SerializedName("categoryId")
        @Expose
        private Integer categoryId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAliasName() {
            return aliasName;
        }

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

    }

}
