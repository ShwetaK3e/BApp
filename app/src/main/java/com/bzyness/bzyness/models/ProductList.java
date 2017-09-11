package com.bzyness.bzyness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shwetakumar on 9/12/17.
 */

public class ProductList {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("bzyness_photos")
    @Expose
    private List<BzynessPhoto> bzynessPhotos = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<BzynessPhoto> getBzynessPhotos() {
        return bzynessPhotos;
    }

    public void setBzynessPhotos(List<BzynessPhoto> bzynessPhotos) {
        this.bzynessPhotos = bzynessPhotos;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public class BzynessPhoto {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("categoryId")
        @Expose
        private Integer categoryId;
        @SerializedName("bzynessId")
        @Expose
        private Integer bzynessId;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;
        @SerializedName("description")
        @Expose
        private Object description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getBzynessId() {
            return bzynessId;
        }

        public void setBzynessId(Integer bzynessId) {
            this.bzynessId = bzynessId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

    }

}