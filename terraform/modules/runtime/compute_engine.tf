resource "google_compute_instance" "app" {
  machine_type = "e2-micro"
  name = var.project_name

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-11"
    }
  }

  network_interface {
    network = "default"
    access_config {}
  }

  service_account {
    email = google_service_account.vm.email
    scopes = ["https://www.googleapis.com/auth/cloud-platform"]
  }

  metadata_startup_script = <<-EOT
    #!/bin/bash
    sudo apt-get update
    sudo apt-get install -y docker.io
    gcloud auth configure-docker ${var.region}-docker.pkg.dev --quiet
    sudo docker pull ${var.region}-docker.pkg.dev/${var.project_id}/${var.project_name}/app:latest
    sudo docker run --rm -e NOTIFICATION_TOPIC="${var.notification_topic}" ${var.region}-docker.pkg.dev/${var.project_id}/${var.project_name}/${var.image_name}:latest
  EOT

  lifecycle {
    replace_triggered_by = [
      null_resource.always_run
    ]
  }
}

resource "null_resource" "always_run" {
  triggers = {
    timestamp = timestamp()
  }
}