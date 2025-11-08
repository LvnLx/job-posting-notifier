package com.lvnlx.job.posting.notifier.gcp;

import com.google.cloud.bigquery.*;
import com.lvnlx.job.posting.notifier.client.Client;
import com.lvnlx.job.posting.notifier.constant.Column;
import com.lvnlx.job.posting.notifier.constant.Environment;
import com.lvnlx.job.posting.notifier.constant.Table;
import com.lvnlx.job.posting.notifier.model.job.Job;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class BigQuery {
    private final com.google.cloud.bigquery.BigQuery bigQuery;

    BigQuery() {
        this.bigQuery = BigQueryOptions.getDefaultInstance().getService();
    }

    public void createJobPostings(List<Job<?, ?>> jobs) throws RuntimeException {
        if (jobs.isEmpty()) {
            return;
        }

        TableId table = TableId.of(Environment.DATASET_NAME, Table.JOB_POSTINGS);
        InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(table);
        jobs.forEach(job -> {
            Map<String, Object> row = new HashMap<>();
            row.put(Column.POSTING_ID, job.getId());
            row.put(Column.COMPANY_ID, job.getCompanyId());
            row.put(Column.TITLE, job.getTitle());
            row.put(Column.URL, job.getLink());
            row.put(Column.CREATED_AT, Instant.now().toString());
            builder.addRow(row);
        });
        InsertAllResponse response = bigQuery.insertAll(builder.build());

        if (response.hasErrors()) {
            throw new RuntimeException(String.format("Failed to create job postings %s", response.getInsertErrors()));
        }
    }

    public void createCompany(Client<?> client) throws RuntimeException {
        TableId table = TableId.of(Environment.DATASET_NAME, Table.COMPANIES);
        Map<String, Object> row = Map.of(Column.COMPANY_ID, client.getId(), Column.COMPANY_NAME, client.getName());
        InsertAllRequest request = InsertAllRequest.newBuilder(table).addRow(row).build();
        InsertAllResponse response = bigQuery.insertAll(request);

        if (response.hasErrors()) {
            throw new RuntimeException(String.format("Failed to create company %s", response.getInsertErrors()));
        }
    }

    public boolean doesCompanyExist(String companyId) throws InterruptedException {
        String sql = companyIdCount(companyId);
        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(sql).build();
        return bigQuery.query(query).streamAll().map(row -> row.get(0).getLongValue()).toList().get(0) == 1;
    }

    public Stream<String> getPostingIds() throws InterruptedException {
        String sql = postingIds();
        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(sql).build();
        return bigQuery.query(query).streamAll().map(row -> row.get(Column.POSTING_ID).getStringValue());
    }

    private String companyIdCount(String companyId) {
        return String.format("""
                SELECT count(*)
                FROM `%s`
                WHERE company_id = '%s'
                """, tableName(Table.COMPANIES), companyId);
    }

    private String postingIds() {
        return String.format("""
                SELECT posting_id
                FROM `%s`
                """, tableName(Table.JOB_POSTINGS));
    }

    private String tableName(String tableName) {
        return String.format("%s.%s.%s", Environment.PROJECT_ID, Environment.DATASET_NAME, tableName);
    }
}
