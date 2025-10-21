package com.bituan.string_analyzer_api.model;

import java.util.List;
import java.util.Map;

public class QueryResponseModel {
    private List<ResponseModel> data;
    private int count;
    private Map<String, ?> filters_applied;

    public QueryResponseModel() {}

    public QueryResponseModel(List<ResponseModel> data, int count, Map<String, ?> filters_applied) {
        this.data = data;
        this.count = count;
        this.filters_applied = filters_applied;
    }

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

    public Map<String, ?> getFilters_applied() {
        return filters_applied;
    }

    public void setFilters_applied(Map<String, ?> filters_applied) {
        this.filters_applied = filters_applied;
    }
}
