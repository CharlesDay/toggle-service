#!/bin/bash
mvn clean install
docker build -t toggle-service .
docker-compose -f ./docker-compose.yml up -d