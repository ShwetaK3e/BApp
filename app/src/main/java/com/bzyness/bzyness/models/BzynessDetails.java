package com.bzyness.bzyness.models;

import java.io.File;
import java.util.List;

/**
 * Created by Pervacio on 5/23/2017.
 */

public class BzynessDetails {

    private int bzyness_id;
    private String bzyness_name;
    private String alias_name;
    private String bzyness_type_id;
    private String bzyness_category_id;
    private List<String> bzyness_tags;
    private String latitude;
    private String longitude;
    private File logoImage;
    private File coverImage1;
    private File coverImage2;
    private File coverImage3;
    private File coverImage4;
    private File coverImage5;
    private String website_link;
    private String phone_no;
    private String apk_link;
    private String ipa_link;


    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getApk_link() {
        return apk_link;
    }

    public void setApk_link(String apk_link) {
        this.apk_link = apk_link;
    }

    public String getIpa_link() {
        return ipa_link;
    }

    public void setIpa_link(String ipa_link) {
        this.ipa_link = ipa_link;
    }

    public File getCoverImage1() {
        return coverImage1;
    }

    public void setCoverImage1(File coverImage1) {
        this.coverImage1 = coverImage1;
    }

    public File getCoverImage2() {
        return coverImage2;
    }

    public void setCoverImage2(File coverImage2) {
        this.coverImage2 = coverImage2;
    }

    public File getCoverImage3() {
        return coverImage3;
    }

    public void setCoverImage3(File coverImage3) {
        this.coverImage3 = coverImage3;
    }

    public File getCoverImage4() {
        return coverImage4;
    }

    public void setCoverImage4(File coverImage4) {
        this.coverImage4 = coverImage4;
    }

    public File getCoverImage5() {
        return coverImage5;
    }

    public void setCoverImage5(File coverImage5) {
        this.coverImage5 = coverImage5;
    }

    public File getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(File logoImage) {
        this.logoImage = logoImage;
    }

    public int getBzyness_id() {
        return bzyness_id;
    }

    public void setBzyness_id(int bzyness_id) {
        this.bzyness_id = bzyness_id;
    }

    public String getBzyness_name() {
        return bzyness_name;
    }

    public void setBzyness_name(String bzyness_name) {
        this.bzyness_name = bzyness_name;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public String getBzyness_type_id() {
        return bzyness_type_id;
    }

    public void setBzyness_type_id(String bzyness_type_id) {
        this.bzyness_type_id = bzyness_type_id;
    }

    public String getBzyness_category_id() {
        return bzyness_category_id;
    }

    public void setBzyness_category_id(String bzyness_category_id) {
        this.bzyness_category_id = bzyness_category_id;
    }

    public List<String> getBzyness_tags() {
        return bzyness_tags;
    }

    public void setBzyness_tags(List<String> bzyness_tags) {
        this.bzyness_tags = bzyness_tags;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
