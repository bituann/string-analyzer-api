package com.bituan.string_analyzer_api.entity;

import com.bituan.string_analyzer_api.model.StringPropertiesModel;

import java.util.HashMap;
import java.util.Map;

public class DatabaseEntity {
    private Map<String, StringPropertiesModel> database;

    public DatabaseEntity () {
        this.database = new HashMap<>();
    }

    public StringPropertiesModel getString (String string) {
        return database.get(string);
    }

    public void addString (String string, StringPropertiesModel properties) {
        database.put(string, properties);
    }

    public boolean stringExists (String string) {
        return database.containsKey(string);
    }
}
