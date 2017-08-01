package com.bzyness.bzyness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shwetakumar on 7/27/17.
 */

public class ProductCategory {

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
