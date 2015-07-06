#!/bin/sh
cd ..
mvn install:install-file -Dfile=lib/thingworx-common-5.0.0.jar -DgroupId=com.thingworx -DartifactId=thingworx-common -Dversion=5.0.0 -Dpackaging=jar
