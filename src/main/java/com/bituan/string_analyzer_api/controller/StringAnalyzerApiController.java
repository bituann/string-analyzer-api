package com.bituan.string_analyzer_api.controller;

import com.bituan.string_analyzer_api.entity.DatabaseEntity;
import com.bituan.string_analyzer_api.model.QueryResponseModel;
import com.bituan.string_analyzer_api.model.ResponseModel;
import com.bituan.string_analyzer_api.model.StringPropertiesModel;
import com.bituan.string_analyzer_api.service.StringAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@RestController
public class StringAnalyzerApiController {
    private final StringAnalyzerService stringAnalyzerService;
    private final DatabaseEntity databaseEntity;

    @Autowired
    public StringAnalyzerApiController (StringAnalyzerService stringAnalyzerService, DatabaseEntity databaseEntity) {
        this.stringAnalyzerService = stringAnalyzerService;
        this.databaseEntity = databaseEntity;
    }

    @PostMapping("/strings")
    public ResponseEntity<?> postString (@RequestBody Map<String, ?> request) throws NoSuchAlgorithmException {
        // check that value is a string
        if (!(request.get("value") instanceof String)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(422)).body("Invalid data type for \"value\" (must be string)");
        }

        // get string stored in "value"
        String string = (String) request.get("value");

        // string exists
        if (databaseEntity.stringExists(string)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body("String already exists in the system");
        }

        // not empty string
        if (string.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Invalid request body or missing \"value\" field");
        }

        StringPropertiesModel properties = new StringPropertiesModel();

        properties.setLength(stringAnalyzerService.stringLength(string));
        properties.setIs_palindrome(stringAnalyzerService.isPalindrome(string));
        properties.setUnique_characters(stringAnalyzerService.numberOfUniqueCharacters(string));
        properties.setWord_count(stringAnalyzerService.wordCount(string));
        properties.setSha256_hash(stringAnalyzerService.sha256Hash(string));
        properties.setCharacter_frequency_map(stringAnalyzerService.characterMap(string));
        properties.setCreated_at(Instant.now());

        databaseEntity.addString(string, properties);

        ResponseModel responseModel = new ResponseModel();

        responseModel.setId(properties.getSha256_hash());
        responseModel.setValue(string);
        responseModel.setProperties(properties);
        responseModel.setCreated_at(properties.getCreated_at());

        return ResponseEntity.ok().header("Content-Type", "application/json").body(responseModel);
    }

    @GetMapping("/strings/{string_value}")
    public ResponseEntity<?> getString (@PathVariable("string_value") String string) {
        if (!databaseEntity.stringExists(string)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("String does not exist in the system");
        }

        StringPropertiesModel properties = databaseEntity.getString(string);
        ResponseModel response = new ResponseModel();

        response.setId(properties.getSha256_hash());
        response.setValue(string);
        response.setProperties(properties);
        response.setCreated_at(properties.getCreated_at());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/strings")
    public ResponseEntity<?> getStringByQuery (
            @RequestParam(required = false) Boolean is_palindrome,
            @RequestParam(required = false) Integer min_length,
            @RequestParam(required = false) Integer max_length,
            @RequestParam(required = false) Integer word_count,
            @RequestParam(required = false) String contains_character) {

        if (contains_character.length() > 1) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Invalid query parameter values or types");
        }

        // apply filters and get results from database
        List<StringPropertiesModel> results = databaseEntity.getStringsByFilter(is_palindrome, min_length, max_length,
                word_count, contains_character);

        List<ResponseModel> resultsMain = new ArrayList<>();

        for (StringPropertiesModel result : results) {
            resultsMain.add(new ResponseModel(result.getString(), result, result.getCreated_at(), result.getSha256_hash()));
        }

        final Map<String, Object> filters_applied = new HashMap<>();

        filters_applied.put("is_palindrome", is_palindrome);
        filters_applied.put("min_length", min_length);
        filters_applied.put("max_length", max_length);
        filters_applied.put("word_count", word_count);
        filters_applied.put("contains_character", contains_character);

        filters_applied.keySet().stream().filter(key -> {
            if (filters_applied.get(key) == null) filters_applied.remove(key);
            return true;
        });

        QueryResponseModel response = new QueryResponseModel();
        response.setData(resultsMain);
        response.setCount(results.size());
        response.setFilters_applied(filters_applied);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/strings/{string_value}")
    public ResponseEntity<String> deleteString (@PathVariable("string_value") String string) {
        if (!databaseEntity.stringExists(string)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("String does not exist in the system");
        }
        databaseEntity.removeString(string);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
