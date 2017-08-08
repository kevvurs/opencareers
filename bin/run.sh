#!/bin/bash

cd ./careers-ui/
ember build production
cd ./../
mvn jetty:run-war

echo "done"
exit 0;
