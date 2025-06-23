resource "google_artifact_registry_repository" "docker" {
  repository_id      = var.project_name
  format             = "DOCKER"
}