# Deployment notes

## GCloud
First steps:
Set the compute zone which is fit for you. This way you don't have to specify it every time.
```
gcloud config set compute/zone europe-west4-c
```
```
gcloud config set compute/region europe-west4
```
Create a cluster:
```
gcloud container clusters create catering-cluster --num-nodes 4 \
--machine-type n1-standard-1 --scopes cloud-platform
```

Build images from your services:
- I use google cloud *jib-maven-plugin* for this purpose. This way I can
upload the image directly to my GCloud image repository.

Build with every service:
```
mvn compile jib:build
```
This will push the image into gcloud.

Write your kubernetes deployment .yaml files and the kubernetes service descriptions.

Pull your repo to GCloud: git clone ...

Start your deployments:
```
kubectl apply -f ..-deployment.yaml --record
```

Start your services:
```
kubectl apply -f ..-service.yaml --record
```

**MySql**
```
gcloud compute disks create mysql-disk --size 5GB
```
Or:
```
kubectl apply -f mysql-pvc.yaml
```
Deploy MySql
```
kubectl apply -f mysql-deployment.yaml -f mysql-service.yaml --record
```

**UI**
Use the docker file to build ui image.
```
docker build -t catering-ui .
```
Tag the image before push it to gcloud repo.
```
> docker tag catering-ui eu.gcr.io/cateringunitmonitor/catering-ui
> docker push eu.gcr.io/cateringunitmonitor/catering-ui
```
