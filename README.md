# API Change Notifier
A Java service running on Google Cloud Platform (GCP) that periodically checks for API changes and sends real-time notifications using [ntfy](https://ntfy.sh/). Infrastructure is managed with Terraform, and CI/CD is handled using GitHub Actions.

## Points of interest
- [API clients](src/main/java/com/lvnlx/api/change/notifier/client)
- [Core notification logic](src/main/java/com/lvnlx/api/change/notifier/service/ApiChangeNotifier.java)
- [GitHub Actions pipeline](.github/workflows/deploy.yml)