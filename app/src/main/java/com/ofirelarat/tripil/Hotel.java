package com.ofirelarat.tripil;

import java.util.Dictionary;
import java.util.Map;

public class Hotel {
    private int productKey;
    private String name;
    private String phone;
    private String cellPhone;
    private String website;
    private Map<String, String> attributes;
    private String description;

    public Hotel( Map<String, String> attributes){
        this.attributes = attributes;
    }

    public Hotel(int productKey, String name, String phone, String cellPhone, String website, Map<String, String> attributes, String description) {
        this.productKey = productKey;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.website = website;
        this.attributes = attributes;
        this.description = description;
    }

    public int getProductKey() {
        return productKey;
    }

    public void setProductKey(int productKey) {
        this.productKey = productKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getWebsite() {
        return website;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
