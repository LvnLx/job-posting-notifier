resource "google_bigquery_dataset" "job_posting_notifier" {
  dataset_id                 = "job_posting_notifier"
  friendly_name              = "Job Posting Notifier"
  is_case_insensitive        = true
  location                   = var.region
  delete_contents_on_destroy = true
}

resource "google_bigquery_table" "companies" {
  dataset_id          = google_bigquery_dataset.job_posting_notifier.dataset_id
  deletion_protection = false
  friendly_name       = "Companies"
  schema              = file("${path.module}/schema/companies.json")
  table_id            = "companies"
}

resource "google_bigquery_table" "job_postings" {
  dataset_id          = google_bigquery_dataset.job_posting_notifier.dataset_id
  deletion_protection = false
  friendly_name       = "Job Postings"
  schema              = file("${path.module}/schema/job_postings.json")
  table_id            = "job_postings"

  time_partitioning {
    type          = "DAY"
    field         = "created_at"
    expiration_ms = 2592000000 # 30 days
  }
}
