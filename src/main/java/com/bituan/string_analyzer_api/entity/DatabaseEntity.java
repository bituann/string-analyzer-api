package com.bituan.string_analyzer_api.entity;

import com.bituan.string_analyzer_api.model.FilterModel;
import com.bituan.string_analyzer_api.model.StringPropertiesModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    public void removeString (String string) {
        database.remove(string);
    }

    public List<StringPropertiesModel> getStringsByFilter (FilterModel filters) {
        List<StringPropertiesModel> result = new ArrayList<>(database.values());

        if (filters.getIs_palindrome() != null) {
            result = result.stream().filter(string -> string.getIs_palindrome() == filters.getIs_palindrome()).collect(Collectors.toList());
        }
        if (filters.getMin_length() != null) {
            result = result.stream().filter(string -> string.getLength() >= filters.getMin_length()).collect(Collectors.toList());
        }
        if (filters.getMax_length() != null) {
            result = result.stream().filter(string -> string.getLength() <= filters.getMax_length()).collect(Collectors.toList());
        }
        if (filters.getWord_count() != null) {
            result = result.stream().filter(string -> string.getWord_count() == filters.getWord_count()).collect(Collectors.toList());
        }
        if (filters.getContains_character() != null) {
            result = result.stream().filter(string -> string.getCharacter_frequency_map().containsKey(filters.getContains_character())).collect(Collectors.toList());
        }

        return result;
    }
}
