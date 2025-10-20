package com.bituan.string_analyzer_api.controller;

import com.bituan.string_analyzer_api.entity.DatabaseEntity;
import com.bituan.string_analyzer_api.model.ResponseModel;
import com.bituan.string_analyzer_api.model.StringPropertiesModel;
import com.bituan.string_analyzer_api.service.StringAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Map;

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

        databaseEntity.addString(string, properties);

        ResponseModel responseModel = new ResponseModel();

        responseModel.setId(properties.getSha256_hash());
        responseModel.setValue(string);
        responseModel.setProperties(properties);
        responseModel.setCreated_at(Instant.now());

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

    @DeleteMapping("/strings/{string_value}")
    public ResponseEntity<String> deleteString (@PathVariable("string_value") String string) {
        if (!databaseEntity.stringExists(string)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("String does not exist in the system");
        }
        databaseEntity.removeString(string);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
