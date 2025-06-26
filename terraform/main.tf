terraform {
  backend "gcs" {
    bucket = "lvnlx-terraform"
    prefix = "job-posting-notifier"
  }
}

provider "google" {
  project = var.project_id
  region = var.region
  zone = "us-central1-a"
}