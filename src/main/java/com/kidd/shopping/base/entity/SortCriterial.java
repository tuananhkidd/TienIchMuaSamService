package com.kidd.shopping.base.entity;

public class SortCriterial {
    private String sortBy;
    private String sortType;

    public SortCriterial(String sortBy, String sortType) {
        this.sortBy = sortBy;
        this.sortType = sortType;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
