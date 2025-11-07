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

    public final SearchRequestFilters filters;
    public final SearchRequestFormat format;
    public final String locale;
    public final int page;
    public final String query;
    public final String sort;
}
