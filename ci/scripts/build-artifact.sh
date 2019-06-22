#!/usr/bin/env bash

set -e

#version=`cat version/number`
cd user-service-git
./gradlew build -x test
cp build/libs/*.jar ../artifact-dir/${base_name}.jar

ls ../artifact-dir
cp -r ../user-service-git/* ../sonarqube-analysis-input/
