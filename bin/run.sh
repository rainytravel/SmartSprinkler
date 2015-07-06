#!/bin/sh
# This script can be used to start the edge server on the PI
/usr/lib/jvm/jdk-7-oracle-armhf/bin/java -jar ../target/temperature-thing-jar-with-dependencies.jar $1 $2 $3

