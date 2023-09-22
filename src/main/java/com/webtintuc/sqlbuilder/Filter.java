package com.webtintuc.sqlbuilder;

public class Filter {
    private String keyword;
    private String dateFormat;
    private String categoryCode;

    public Filter(String keyword, String dateFormat, String categoryCode) {
        this.keyword = keyword;
        this.dateFormat = dateFormat;
        this.categoryCode = categoryCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
