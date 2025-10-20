package com.bituan.string_analyzer_api.service;

public class StringAnalyzerService {
    public int getStringLength (String string) {
        return string.length();
    }

    public boolean isPalindrome (String string) {
        String reverseString = String.valueOf(new StringBuilder(string).reverse());

        return string.equalsIgnoreCase(reverseString);

    }
}
