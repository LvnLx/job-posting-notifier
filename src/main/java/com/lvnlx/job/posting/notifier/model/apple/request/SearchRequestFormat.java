package com.lvnlx.job.posting.notifier.model.apple.request;

public class SearchRequestFormat {
    SearchRequestFormat() {
        this.longDate = "MMMM D, YYYY";
        this.mediumDate = "MMM D, YYYY";
    }

    public String longDate;
    public String mediumDate;
}
