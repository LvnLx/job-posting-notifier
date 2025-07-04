# Job Posting Notifier
A Dockerized Java Spring Boot service running on Google Cloud Platform (GCP) that periodically checks for new job postings from various API sources and sends real-time notifications using [ntfy](https://ntfy.sh/).

Infrastructure is managed with Terraform, and CI/CD is handled using GitHub Actions (GHA).

## How to run
- Clone the repository to the machine you would like to run the program on
- Add to or modify the [clients](src/main/java/com/lvnlx/job/posting/notifier/client) and [models](src/main/java/com/lvnlx/job/posting/notifier/model) as needed to integrate with desired APIs and postings
  - The clients simply need to implement the interface defined [here](https://github.com/LvnLx/job-posting-notifier/blob/main/src/main/java/com/lvnlx/job/posting/notifier/client/Client.java)
  - The response models should be wrapped in a class specific to the client, which simply need to implement the interface defined [here](src/main/java/com/lvnlx/job/posting/notifier/model/Job.java)
- Install and run [ntfy](https://ntfy.sh/) on any devices you wish to receieve notifications on, subscribing to the topic you set in the next step
  - [ntfy](https://ntfy.sh/) allows for self hosting if you'd like to ensure privacy or guarantee topic names
- Compile and run the program, ensuring that the `NOTIFICATION_TOPIC` environment variable is set to a unique value (it will be used for your [ntfy.sh](https://ntfy/) topic

## Points of interest
- [API clients](src/main/java/com/lvnlx/job/posting/notifier/client)
- [Core notification logic](src/main/java/com/lvnlx/job/posting/notifier/service/JobPostingNotifier.java)
- [GCP infrastructure (Terraform)](/terraform)
- [CI/CD pipeline (GHA)](.github/workflows/deploy.yml)
