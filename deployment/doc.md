# Deployment notes

- Build with:
**mvn compile jib:build**
This will push the image into gcloud.

- Pull repo to glcoud
- kubectl apply -f ..-deployment.yaml 