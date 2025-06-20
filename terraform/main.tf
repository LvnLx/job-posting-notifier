terraform {
  backend "gcs" {
    bucket = "lvnlx-terraform"
    prefix = "api-notifier"
  }
}
