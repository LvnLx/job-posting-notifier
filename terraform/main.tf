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

module "foundation" {
  source    = "./modules/foundation"
  project_name = var.project_name
}

module "runtime" {
  source    = "./modules/runtime"
  image_name = var.image_name
  ntfy_topic = var.ntfy_topic
  project_id = var.project_id
  project_name = var.project_name
  region = var.region
}
