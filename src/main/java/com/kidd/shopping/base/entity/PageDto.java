package com.kidd.shopping.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.util.List;

@ApiModel
public class PageDto<T> {
    @ApiModelProperty(notes = "Index page, bắt đầu từ 0")
    private int pageIndex;
    @ApiModelProperty(notes = "Kích thước page", position = 1)
    private long pageSize;
    @ApiModelProperty(notes = "Tổng số trang", position = 2)
    private long totalPage;
    @ApiModelProperty(notes = "Tổng số items", position = 3)
    private long totalItem;
    @ApiModelProperty(notes = "List items", position = 4)
    private List<T> results;

    public PageDto() {
    }

    public PageDto(List<T> objects,
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

    public PageDto(Page<T> page) {
        this.results = page.getContent();
        this.pageIndex = page.getNumber();
        this.pageSize = page.getSize();
        this.totalItem = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
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
