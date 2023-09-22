package com.webtintuc.sqlbuilder;

public class Pageable {
    private Integer offset;
    private Integer limit;
    private Sorter sorter;

    public Pageable(int page, int limit) {
        this.offset = (page - 1) * limit;
        this.limit = limit;
    }

    public Pageable(int page, int limit, Sorter sorter) {
        this.offset = (page - 1) * limit;
        this.limit = limit;
        this.sorter = sorter;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }
}
