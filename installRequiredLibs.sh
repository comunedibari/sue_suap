#!/bin/bash
echo "INSTALLAZIONE JAR FRONT OFFICE"

cd ./frontoffice
echo "Directory di lavoro: " $PWD
./installLibSoapSue.sh

cd ..
echo "Directory di lavoro: " $PWD

echo "INSTALLAZIONE LIBRERIE PER BACKOFFICE/CROSS"
/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/concessioni-autorizzazioni-1.6.0.jar -DgroupId=it.people -DartifactId=concessioni-autorizzazioni -Dversion=1.6.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/connects-interfaces-2.10.jar -DgroupId=it.people -DartifactId=connects-interfaces -Dversion=2.10 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/e-grammata-1.0.jar -DgroupId=it.people -DartifactId=e-grammata -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/feframework-2.5.jar -DgroupId=it.people -DartifactId=feframework -Dversion=2.5 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/sirac-2.0.2.jar -DgroupId=it.people -DartifactId=sirac -Dversion=2.0.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/sirac-ws-accr121-2.0.2.jar -DgroupId=it.people -DartifactId=sirac-ws-accr121 -Dversion=2.0.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/xmlbeans-authdataholder-2.0.2.jar -DgroupId=it.people -DartifactId=xmlbeans-authdataholder -Dversion=2.0.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/xmlbeans-serviceprofile-2.0.2.jar -DgroupId=it.people -DartifactId=xmlbeans-serviceprofile -Dversion=2.0.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/xml-visura-1.1.jar -DgroupId=it.people -DartifactId=xml-visura -Dversion=1.1 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/cryptolib-1.0.jar -DgroupId=it.wego -DartifactId=cryptolib -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/interoperabilita-1.0.jar -DgroupId=it.wego -DartifactId=interoperabilita -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/opensaml-1.0.jar -DgroupId=it.wego -DartifactId=opensaml -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/utils-1.0.jar -DgroupId=it.wego -DartifactId=utils -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/cas-ssha-sql-authenticator-1.0-SNAPSHOT.jar -DgroupId=it.wego.utils -DartifactId=cas-ssha-sql-authenticator -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/json-utils-1.0-SNAPSHOT.jar -DgroupId=it.wego.utils -DartifactId=json-utils -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/wego-forms-1.2-SNAPSHOT.jar -DgroupId=it.wego.utils -DartifactId=wego-forms -Dversion=1.2-SNAPSHOT -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/wego-forms-1.2-SNAPSHOT.jar -DgroupId=it.wego.utils -DartifactId=wego-forms -Dversion=1.2-SNAPSHOT -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/wego-forms-web-1.1-SNAPSHOT.war -DgroupId=it.wego.utils -DartifactId=wego-forms-web -Dversion=1.1-SNAPSHOT -Dpackaging=war -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/imaging-01012005.jar -DgroupId=com.jhlabs -DartifactId=imaging -Dversion=01012005 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/backoffice/wego-deploy-maven-plugin-1.0-SNAPSHOT.jar -DpomFile=./requiredLibs/backoffice/wego-deploy-maven-plugin-1.0-SNAPSHOT.pom

echo "INSTALLAZIONE LIBRERIE PER BACKOFFICE/REPORTER"

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/jodconverter-2.2.2.jar -DgroupId=com.artofsolving -DartifactId=jodconverter -Dversion=2.2.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/jodconverter-cli-2.2.2.jar -DgroupId=com.artofsolving -DartifactId=jodconverter-cli -Dversion=2.2.2 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-api.jar -DgroupId=webservices-api -DartifactId=webservices-api -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-apiOK.jar -DgroupId=webservices-api -DartifactId=webservices-apiOK -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-extra-api.jar -DgroupId=webservices-extra -DartifactId=webservices-extra-api -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-extra.jar -DgroupId=webservices-extra -DartifactId=webservices-extra -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-rt.jar -DgroupId=webservices-rt -DartifactId=webservices-rt -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/webservices-tools.jar -DgroupId=webservices-tools -DartifactId=webservices-tools -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/xercesImpl.jar -DgroupId=xercesImpl -DartifactId=xercesImpl -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/xom-1.2.6.jar -DgroupId=xom -DartifactId=xom -Dversion=1.2.6 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

/home/angelo/Scrivania/apache-maven-3.6.0/bin/mvn install:install-file -Dfile=./requiredLibs/reporter/xsltc.jar -DgroupId=xsltc -DartifactId=xsltc -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true





