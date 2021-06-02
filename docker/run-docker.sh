#!/bin/sh

# Check if autosurvey-network network exists
if [ -z "$(docker network ls -q -f name=autosurvey-network)" ]; then
    docker network create autosurvey-network
fi

# rm io-service container if it exists
if [ -n "$(docker container ls -aqf name=io-service)" ]; then
    echo "Removing io-service"
    docker container stop io-service
    docker container rm io-service
fi

#start analytics-service container
docker container run -d --name io-service --network autosurvey-network -e EUREKA_URL -e CREDENTIALS_JSON -e CREDENTIALS_JSON_ENCODED -e FIREBASE_API_KEY -e SERVICE_ACCOUNT_ID -e EMAIL_ADDRESS -e EMAIL_PASSWORD -e SQS_PASS -e SQS_QUEUE_NAME -e SQS_USER autosurvey/io-service