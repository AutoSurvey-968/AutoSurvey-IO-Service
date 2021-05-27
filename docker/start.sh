#!/bin/sh
#Decode the passed in truststore env variable
echo "$CREDENTIALS_JSON_ENCODED" | base64 -d > $CREDENTIALS_JSON
java -jar io-service.jar