# Job Posting Notifier
A Java Spring Boot application running on Google Cloud Platform (GCP) that periodically checks for new job postings from various API sources.

## Key Features
- Real-time notifications published via Pub/Sub
- Automated and containerized deployment to Compute Engine via Docker, GitHub Actions, and Terraform
- Persistence of postings via BigQuery

## Points of interest
- [Core notification logic](src/main/java/com/lvnlx/job/posting/notifier/service/JobPostingNotifier.java)
- [API clients](src/main/java/com/lvnlx/job/posting/notifier/client)
- [GCP SDK integrations](src/main/java/com/lvnlx/job/posting/notifier/gcp)
- [GCP infrastructure (Terraform)](/terraform)
- [CI/CD pipeline (GHA)](.github)

## How to run
1. Make sure you have a [Google Cloud account](https://cloud.google.com/?hl=en) with a service account you can use for infrastructure deployments
2. Fork the repository, setting up repository secrets `GCP_PROJECT_ID` (your Google Cloud's project ID), `GCP_CREDENTIALS_JSON` (follow [these instructions](https://developers.google.com/workspace/guides/create-credentials) for the service account mentioned in step 1), and `NTFY_TOPIC` (checkout how to receive messages [here](https://docs.ntfy.sh/#step-1-get-the-app))
3. Trigger the GitHub actions pipeline, for which the triggering conditions can be found [here](.github/workflows/deploy.yml) (note that this will start the application in the cloud, so make sure you have your `ntfy.sh` subscribing device setup with the correct topic)
4. From here you have two options:
   1. If you'd like to run the application in your Google Cloud Project, simply wait for the pipeline to complete and within a few minutes notifications should be sent to the topic you previously configured 
   2. If you'd like to run the code locally, set the following environment variables:

| Environment Variable Name | Value                          |
|---------------------------|--------------------------------|
| `NTFY_TOPIC`              | Topic name of your choice      |
| `PROJECT_ID`              | Your Google Cloud's project ID |
| `DATASET_NAME`            | `job_posting_notifier`         |
| `PUBSUB_TOPIC`            | `ntfy`                         |
