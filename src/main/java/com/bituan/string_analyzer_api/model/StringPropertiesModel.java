package com.bituan.string_analyzer_api.model;

import java.time.Instant;
import java.util.Map;

public class StringPropertiesModel {
    private int length;
    private boolean is_palindrome;
    private int unique_characters;
    private int word_count;
    private String sha256_hash;
    private Map<String, Integer> character_frequency_map;
    private Instant created_at;

    public StringPropertiesModel() {}

    public StringPropertiesModel(int length, boolean is_palindrome, int unique_characters, int word_count,
                                 String sha256_hash, Map<String, Integer> character_frequency_map, Instant created_at) {
        this.length = length;
        this.is_palindrome = is_palindrome;
        this.unique_characters = unique_characters;
        this.word_count = word_count;
        this.sha256_hash = sha256_hash;
        this.character_frequency_map = character_frequency_map;
        this.created_at = created_at;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean getIs_palindrome() {
        return is_palindrome;
    }

    public void setIs_palindrome(boolean is_palindrome) {
        this.is_palindrome = is_palindrome;
    }

    public int getUnique_characters() {
        return unique_characters;
    }

    public void setUnique_characters(int unique_characters) {
        this.unique_characters = unique_characters;
    }

    public int getWord_count() {
        return word_count;
    }

    public void setWord_count(int word_count) {
        this.word_count = word_count;
    }

    public String getSha256_hash() {
        return sha256_hash;
    }

    public void setSha256_hash(String sha256_hash) {
        this.sha256_hash = sha256_hash;
    }

    public Map<String, Integer> getCharcater_frequency_map() {
        return character_frequency_map;
    }

    public void setCharacter_frequency_map(Map<String, Integer> character_frequency_map) {
        this.character_frequency_map = character_frequency_map;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
