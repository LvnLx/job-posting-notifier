terraform {
  backend "gcs" {
    bucket = "lvnlx-terraform"
    prefix = var.project_name
  }
}

provider "google" {
  project = var.project_id
  region = var.region
}