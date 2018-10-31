#!/bin/sh
echo "Starting!"
cd $(dirname "$0")
echo "Starting task gradlew setupDecompWorkspace"
./gradlew setupDecompWorkspace
echo "Finished task gradlew setupDecompWorkspace"
echo "Starting task gradlew eclipse"
./gradlew eclipse
echo "Finished task gradlew eclipse"
echo "Done!"