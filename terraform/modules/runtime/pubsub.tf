resource "google_pubsub_topic" "ntfy" {
  name = "ntfy"
}

resource "google_pubsub_subscription" "ntfy" {
  name  = "ntfy"
  topic = google_pubsub_topic.ntfy.name

  expiration_policy {
    ttl = ""
  }

  push_config {
    push_endpoint = "https://ntfy.sh/"
  }
}
