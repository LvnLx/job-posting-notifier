package com.lvnlx.job.posting.notifier.model.job.apple.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseResSearchResultTeam {
    public String teamCode;
}
