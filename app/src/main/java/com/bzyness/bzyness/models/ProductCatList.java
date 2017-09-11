package com.bzyness.bzyness.models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shwetakumar on 9/9/17.
 */

public class ProductCatList {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("bzyness_categories")
    @Expose
    private List<BzynessCategory> bzynessCategories = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<BzynessCategory> getBzynessCategories() {
        return bzynessCategories;
    }

    public void setBzynessCategories(List<BzynessCategory> bzynessCategories) {
        this.bzynessCategories = bzynessCategories;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public static class BzynessCategory {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("categoryThumbnail")
        @Expose
        private String categoryThumbnail;
        @SerializedName("bzynessId")
        @Expose
        private Integer bzynessId;
        @SerializedName("description")
        @Expose
        private Object description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryThumbnail() {
            return categoryThumbnail;
        }

        public void setCategoryThumbnail(String categoryThumbnail) {
            this.categoryThumbnail = categoryThumbnail;
        }

        public Integer getBzynessId() {
            return bzynessId;
        }

        public void setBzynessId(Integer bzynessId) {
            this.bzynessId = bzynessId;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

    }

}