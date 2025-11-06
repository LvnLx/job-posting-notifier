resource "google_service_account" "vm" {
  account_id   = "compute-instance"
  display_name = "VM Service Account"
}

resource "google_project_iam_custom_role" "artifact_download" {
  role_id     = "artifactDownload"
  title       = "Artifact Download"

  permissions = [
    "artifactregistry.repositories.downloadArtifacts"
  ]
}

resource "google_project_iam_member" "vm_artifact_download" {
  project = var.project_id
  role    = google_project_iam_custom_role.artifact_download.name
  member  = "serviceAccount:${google_service_account.vm.email}"
}
