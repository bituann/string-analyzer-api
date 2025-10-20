package com.bituan.string_analyzer_api.controller;

import com.bituan.string_analyzer_api.service.StringAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringAnalyzerApiController {
    private final StringAnalyzerService stringAnalyzerService;

    @Autowired
    public StringAnalyzerApiController (StringAnalyzerService stringAnalyzerService) {
        this.stringAnalyzerService = stringAnalyzerService;
    }
}
