resource "google_service_account" "vm" {
  account_id   = "compute-instance"
  display_name = "VM Service Account"
}

resource "google_project_iam_member" "vm_artifact_registry_reader" {
  member  = local.vm_member
  project = var.project_id
  role    = "roles/artifactregistry.reader"
}

resource "google_project_iam_member" "vm_pubsub_publisher" {
  member  = local.vm_member
  project = var.project_id
  role    = "roles/pubsub.publisher"
}

resource "google_project_iam_member" "vm_pubsub_viewer" {
  member  = local.vm_member
  project = var.project_id
  role    = "roles/pubsub.viewer"
}
