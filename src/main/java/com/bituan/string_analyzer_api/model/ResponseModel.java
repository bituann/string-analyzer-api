package com.bituan.string_analyzer_api.model;

import java.time.Instant;

public class ResponseModel {
    private String id;
    private String value;
    private StringPropertiesModel properties;
    private Instant created_at;

    public ResponseModel() {}

    public ResponseModel(String value, StringPropertiesModel properties, Instant created_at, String id) {
        this.value = value;
        this.properties = properties;
        this.created_at = created_at;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringPropertiesModel getProperties() {
        return properties;
    }

    public void setProperties(StringPropertiesModel properties) {
        this.properties = properties;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
