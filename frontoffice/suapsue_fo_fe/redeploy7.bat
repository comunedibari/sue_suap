cd AllegatiService
mvn tomcat7:redeploy -Ptest
cd ..\DynamicOdtServiceWego\
mvn tomcat7:redeploy -Ptest
cd ..\FEService
mvn tomcat7:redeploy -Ptest
cd ..\firmasemplice
mvn tomcat7:redeploy -Ptest
cd ..\people
mvn tomcat7:redeploy -Ptest
cd ..\PeopleService
mvn tomcat7:redeploy -Ptest
cd ..\SimpleDesk
mvn tomcat7:redeploy -Ptest

