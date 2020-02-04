#!/bin/bash

mvn clean install && scp target/wego-cas-filter-1.0-SNAPSHOT.jar genova:tomcat/webapps/cross/WEB-INF/lib/


