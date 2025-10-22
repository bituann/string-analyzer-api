package com.bituan.string_analyzer_api.controller;

import com.bituan.string_analyzer_api.entity.DatabaseEntity;
import com.bituan.string_analyzer_api.model.*;
import com.bituan.string_analyzer_api.service.NLQueryParserService;
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
    private final NLQueryParserService nlQueryParserService;

    @Autowired
    public StringAnalyzerApiController (StringAnalyzerService stringAnalyzerService, DatabaseEntity databaseEntity,
                                        NLQueryParserService nlQueryParserService) {
        this.stringAnalyzerService = stringAnalyzerService;
        this.databaseEntity = databaseEntity;
        this.nlQueryParserService = nlQueryParserService;
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

        properties.setString(string);
        properties.setLength(stringAnalyzerService.stringLength(string));
        properties.setIs_palindrome(stringAnalyzerService.isPalindrome(string));
        properties.setUnique_characters(stringAnalyzerService.numberOfUniqueCharacters(string));
        properties.setWord_count(stringAnalyzerService.wordCount(string));
        properties.setSha256_hash(stringAnalyzerService.sha256Hash(string));
        properties.setCharacter_frequency_map(stringAnalyzerService.characterMap(string));

        databaseEntity.addString(string, properties);

        ResponseModel responseModel = new ResponseModel();

        responseModel.setId(properties.getSha256_hash());
        responseModel.setValue(string);
        responseModel.setProperties(properties);
        responseModel.setCreated_at(Instant.now());

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).header("Content-Type", "application/json").body(responseModel);
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
        response.setCreated_at(Instant.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/strings")
    public ResponseEntity<?> getStringByQuery (FilterModel filters) {

        if (filters.getContains_character() != null && filters.getContains_character().isEmpty()) {
            filters.setContains_character(null);
        }

        if (filters.getContains_character() != null && filters.getContains_character().length() > 1) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Invalid query parameter values or types");
        }

        // apply filters and get results from database
        List<StringPropertiesModel> results = databaseEntity.getStringsByFilter(filters);

        List<ResponseModel> resultsMain = new ArrayList<>();

        for (StringPropertiesModel result : results) {
            resultsMain.add(new ResponseModel(result.getString(), result, Instant.now(), result.getSha256_hash()));
        }

        QueryResponseModel response = new QueryResponseModel();
        response.setData(resultsMain);
        response.setCount(results.size());
        response.setFilters_applied(filters);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/strings/filter-by-natural-language")
    public ResponseEntity<NLQueryResponseModel> getStringByNL (@RequestParam String query) {

        // parsed query filters
        FilterModel queryFilters = nlQueryParserService.stringToFilter(query);

        if (queryFilters.getIs_palindrome() == null && queryFilters.getMin_length() == null &&
                queryFilters.getMax_length() == null && queryFilters.getWord_count() == null &&
                queryFilters.getContains_character() == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        // execute query with filters
        List<StringPropertiesModel> results = databaseEntity.getStringsByFilter(queryFilters);
        List<ResponseModel> resultsMain = new ArrayList<>();

        // data part of result
        for (StringPropertiesModel result : results) {
            resultsMain.add(new ResponseModel(result.getString(), result, Instant.now(), result.getSha256_hash()));
        }

        // response model
        NLQueryResponseModel response = new NLQueryResponseModel();

        response.setData(resultsMain);
        response.setCount(resultsMain.size());
        response.setInterpretedQuery(query, queryFilters);

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
