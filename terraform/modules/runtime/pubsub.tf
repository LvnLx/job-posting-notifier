resource "google_pubsub_topic" "ntfy" {
  name = "ntfy"
}

resource "google_pubsub_subscription" "ntfy" {
  enable_exactly_once_delivery = true
  name  = "ntfy"
  topic = google_pubsub_topic.ntfy

  expiration_policy {
    ttl = ""
  }

  push_config {
    push_endpoint = "https://ntfy.sh/"
  }
}
