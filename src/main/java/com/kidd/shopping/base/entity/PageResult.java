package com.kidd.shopping.base.entity;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResult {
    private List<?> results;
    private int pageIndex;
    private long pageSize;
    private long totalPage;
    private long totalItem;

    public PageResult() {
    }

    public PageResult(List<?> objects,
                      int pageIndex,
                      Integer pageSize,
                      long foundNumber) {
        this.results = objects;
        this.totalItem = foundNumber;
        if (pageSize == null) {
            this.pageIndex = 0;
            this.pageSize = foundNumber;
            this.totalPage = 1;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
            this.totalPage = (long) Math.ceil(foundNumber * 1.0 / pageSize);
        }
    }

    public PageResult(Page<?> page) {
        this.results = page.getContent();
        this.pageIndex = page.getNumber();
        this.pageSize = page.getSize();
        this.totalItem = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }
}
