locals {
  vm_member = "serviceAccount:${google_service_account.vm.email}"
}
