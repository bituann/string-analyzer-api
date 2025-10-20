package com.bituan.string_analyzer_api.entity;

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

    public List<StringPropertiesModel> getStringsByFilter (Boolean isPalindrome, Integer minLength, Integer maxLength,
                                                                        Integer wordCount, String containsCharacter) {
        List<StringPropertiesModel> result = (List<StringPropertiesModel>) database.values();

        if (isPalindrome != null) {
            result = result.stream().filter(string -> string.getIs_palindrome() == isPalindrome).collect(Collectors.toList());
        }
        if (minLength != null) {
            result = result.stream().filter(string -> string.getLength() >= minLength).collect(Collectors.toList());
        }
        if (maxLength != null) {
            result = result.stream().filter(string -> string.getLength() <= maxLength).collect(Collectors.toList());
        }
        if (wordCount != null) {
            result = result.stream().filter(string -> string.getWord_count() == wordCount).collect(Collectors.toList());
        }
        if (containsCharacter != null) {
            result = result.stream().filter(string -> string.getCharcater_frequency_map().containsKey(containsCharacter)).collect(Collectors.toList());
        }

        return result;
    }
}
