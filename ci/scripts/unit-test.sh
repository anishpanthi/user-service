#!/usr/bin/env bash

set -e

export GRADLE_OPTS=-Dorg.gradle.native=true
cd user-service-git
./gradlew test
