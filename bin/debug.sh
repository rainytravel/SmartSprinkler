#!/bin/sh
# This script can be used on the PI to start the edge server with debugging support
/usr/lib/jvm/jdk-7-oracle-armhf/bin/java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -jar ../target/temperature-thing-jar-with-dependencies.jar $1 $2 $3

