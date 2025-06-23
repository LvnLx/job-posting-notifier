terraform {
  backend "gcs" {
    bucket = "lvnlx-terraform"
    prefix = "api-change-notifier"
  }
}

provider "google" {
  project = var.project_id
  region = var.region
}