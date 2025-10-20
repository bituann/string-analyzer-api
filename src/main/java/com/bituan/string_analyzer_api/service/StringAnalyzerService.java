package com.bituan.string_analyzer_api.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringAnalyzerService {
    public int getStringLength (String string) {
        return string.length();
    }

    public boolean isPalindrome (String string) {
        String reverseString = String.valueOf(new StringBuilder(string).reverse());

        return string.equalsIgnoreCase(reverseString);
    }

    public int numberOfUniqueCharacters (String string) {
        Set<String> chars = new HashSet(List.of(string.split("")));
        return chars.size();
    }

    public int wordCount (String string) {
        return string.split(" ").length;
    }

    public String sha256Hash (String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));

        return new BigInteger(1, hash).toString(16);
    }
}
