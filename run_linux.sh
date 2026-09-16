#!/usr/bin/env bash
set -e
java -version
mvn -version
mvn clean test
mvn javafx:run
