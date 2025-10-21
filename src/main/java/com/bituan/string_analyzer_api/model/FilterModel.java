package com.bituan.string_analyzer_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterModel {
    private Boolean is_palindrome;
    private Integer min_length;
    private Integer max_length;
    private Integer word_count;
    private String contains_character;

    public FilterModel() {}

    public FilterModel(Boolean is_palindrome, Integer min_length, Integer max_length, Integer word_count, String contains_character) {
        this.is_palindrome = is_palindrome;
        this.min_length = min_length;
        this.max_length = max_length;
        this.word_count = word_count;
        this.contains_character = contains_character;
    }

    public Boolean getIs_palindrome() {
        return is_palindrome;
    }

    public void setIs_palindrome(Boolean is_palindrome) {
        this.is_palindrome = is_palindrome;
    }

    public Integer getMin_length() {
        return min_length;
    }

    public void setMin_length(Integer min_length) {
        this.min_length = min_length;
    }

    public Integer getMax_length() {
        return max_length;
    }

    public void setMax_length(Integer max_length) {
        this.max_length = max_length;
    }

    public Integer getWord_count() {
        return word_count;
    }

    public void setWord_count(Integer word_count) {
        this.word_count = word_count;
    }

    public String getContains_character() {
        return contains_character;
    }

    public void setContains_character(String contains_character) {
        this.contains_character = contains_character;
    }
}
