package com.bituan.string_analyzer_api.model;

import java.util.List;
import java.util.Map;

public class NLQueryResponseModel {
    private List<ResponseModel> data;
    private int count;
    private InterpretedQuery interpretedQuery;

    public NLQueryResponseModel () {}

    public List<ResponseModel> getData() {
        return data;
    }

    public void setData(List<ResponseModel> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InterpretedQuery getInterpretedQuery() {
        return interpretedQuery;
    }

    public void setInterpretedQuery(String original, FilterModel parsed_filters) {
        this.interpretedQuery = new InterpretedQuery(original, parsed_filters);
    }
}

class InterpretedQuery {
    private String original;
    private FilterModel parsed_filters;

    public InterpretedQuery(String original, FilterModel parsed_filters) {
        this.original = original;
        this.parsed_filters = parsed_filters;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public FilterModel getParsed_filters() {
        return parsed_filters;
    }

    public void setParsed_filters(FilterModel parsed_filters) {
        this.parsed_filters = parsed_filters;
    }
}
