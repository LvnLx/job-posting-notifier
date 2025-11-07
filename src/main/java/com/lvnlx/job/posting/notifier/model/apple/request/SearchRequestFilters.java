package com.lvnlx.job.posting.notifier.model.apple.request;

import java.util.ArrayList;
import java.util.List;

public class SearchRequestFilters {
    SearchRequestFilters() {
        this.homeOffice = true;
        this.locations = new ArrayList<>();
        this.locations.add("postLocation-USA");
    }

    public final boolean homeOffice;
    public final List<String> locations;
}
