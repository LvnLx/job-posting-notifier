package com.lvnlx.job.posting.notifier.model.apple.request;

public class SearchRequest {
    public SearchRequest(int page) {
        this.filters = new SearchRequestFilters();
        this.format = new SearchRequestFormat();
        this.locale = "en-us";
        this.page = page;
        this.query = "";
        this.sort = "";
    }

    public SearchRequestFilters filters;
    public SearchRequestFormat format;
    public String locale;
    public int page;
    public String query;
    public String sort;
}
