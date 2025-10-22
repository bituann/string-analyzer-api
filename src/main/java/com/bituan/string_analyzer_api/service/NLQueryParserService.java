package com.bituan.string_analyzer_api.service;

import com.bituan.string_analyzer_api.model.FilterModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NLQueryParserService {

    public NLQueryParserService() {}

    public FilterModel stringToFilter (String string) {
        FilterModel filters = new FilterModel();

        // palindrome check
        if (string.contains("palindrome") || string.contains("palindromic")) filters.setIs_palindrome(true);
        System.out.println(filters.getIs_palindrome());

        // min length check
        Integer minLength = stringToMinLengthFilter(string);
        filters.setMin_length(minLength);
        System.out.println(filters.getMin_length());

        // max length check
        Integer maxLength = stringToMaxLengthFilter(string);
        filters.setMax_length(maxLength);
        System.out.println(filters.getMax_length());

        // word count check
        Integer wordCount = stringToWordCountFilter(string);
        filters.setWord_count(wordCount);
        System.out.println(filters.getWord_count());

        // contains character check
        String containsCharacter = stringToContainsCharacterFilter(string);
        filters.setContains_character(containsCharacter);
        System.out.println(containsCharacter);

        return filters;
    }

    private Integer stringToMinLengthFilter (String string) {
        String regex = "(?:min|longer)[a-zA-Z\\s]*\\d+";
        return matchNumberRegex(string, regex);
    }

    private Integer stringToMaxLengthFilter (String string) {
        String regex = "(?:max|shorter)[a-zA-Z\\s]*\\d+";
        return matchNumberRegex(string, regex);
    }

    private Integer stringToWordCountFilter (String string) {
        String regex = "((\\d+\\s*(?:word(s)?))|((single|double|triple)\\s*word(s)?))";

        String match = matchRegexPattern(string, regex);

        if (match == null) {
            return null;
        }

        if (match.toLowerCase().contains("single") || match.toLowerCase().contains("double") ||
                match.toLowerCase().contains("triple") || match.toLowerCase().contains("quadruple")) {
            match = match.toLowerCase().strip();
            Map<String, Integer> numbers = new HashMap<>();
            numbers.put("single", 1);
            numbers.put("double", 2);
            numbers.put("triple", 3);
            numbers.put("quadruple", 4);

            return numbers.get(match.split(" ")[0]);
        }

        return matchNumberRegex(string, regex);
    }

    private String stringToContainsCharacterFilter (String string) {
        String regex = "((?:containing|contain)[a-zA-Z\\s]*letter\\s*[a-zA-Z]{1}(?:\\s?))|" +
                "(?:first|second|third|fourth|fifth)\\s*vowel";
        String match = matchRegexPattern(string, regex);

        if (match == null) {
            return null;
        }

        if (match.toLowerCase().contains("vowel")) {
            match = match.toLowerCase().strip();
            Map<String, String> vowels = new HashMap<>();
            vowels.put("first vowel", "a");
            vowels.put("second vowel", "e");
            vowels.put("third vowel", "i");
            vowels.put("fourth vowel", "o");
            vowels.put("fifth vowel", "u");

            return vowels.get(match);
        }

        // reuse regex & match to get character from string
        regex = "[a-zA-Z](?:\\s*)$";
        match = matchRegexPattern(match, regex);

        if (match == null) {
            return null;
        }

        return match.strip();
    }

    private String matchRegexPattern (String string, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);
        String matchedString;

        if (!matcher.find()) {
            return null;
        }

        matchedString = matcher.group();

        return matchedString;
    }

    private Integer matchNumberRegex (String string, String regex) {
        String match = matchRegexPattern(string, regex);

        if (match == null) {
            return null;
        }

        // reuse regex & match to get digit from string
        regex = "\\d+";
        match = matchRegexPattern(match, regex);

        if (match == null) {
            return null;
        }

        return Integer.valueOf(match);
    }
}
