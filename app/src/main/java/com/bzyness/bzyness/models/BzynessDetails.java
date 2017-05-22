package com.bzyness.bzyness.models;

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
}
