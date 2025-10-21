package com.bituan.string_analyzer_api.model;

import java.util.List;
import java.util.Map;

public class QueryResponseModel {
    private List<ResponseModel> data;
    private int count;
    private FilterModel filters_applied;

    public QueryResponseModel() {}

    public QueryResponseModel(List<ResponseModel> data, int count, FilterModel filters_applied) {
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

    public FilterModel getFilters_applied() {
        return filters_applied;
    }

    public void setFilters_applied(FilterModel filters_applied) {
        this.filters_applied = filters_applied;
    }
}
