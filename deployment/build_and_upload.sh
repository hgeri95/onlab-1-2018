#!/bin/bash

modules=(
    api-gateway-service
    catering-unit-service
    notification-service
    rating-service
    swagger-service
    user-service
    statistic-service
)

for i in "${modules[@]}"; do
    echo "Build and upload $i..."
    cd ..
    cd $i
    mvn compile jib:build
done
