# SUAPSUE AREA VASTA
## INTRODUZIONE
Il seguente readme ha l'obiettivo di descrivere brevemente i moduli che compongono l'applicazione SUAPSUE per Area Vasta e descrivere i prerequisiti e gli step necessari alla costruzione dell'artefatto
 
## INFORMAZIONI GENERALI
Per area vasta SUAPSUE si compone dei seguenti moduli:

- **frontoffice backend**: In particolare si troveranno i seguenti war per il backend:
    - **BEService**: 
    - **Dbm**: 
    - **idp_people**: 
    - **IdpPeopleAdmin**: 
    - **PeopleConsole**: 
    - **scauth**: 
    - **sirac**: 
    - **ws_accr_121**: 
    - **ws_regauth**:

- **frontoffice frontend**: In particolare si troveranno i seguenti war per il frontend:
	- **AllegatiService**: 
    - **DynamicOdtServiceWego**: 
    - **FEService**: 
    - **firmasemplice**: 
    - **people**: 
    - **PeopleService**: 
    - **SimpleDesk**: 
        
- **backoffice**: 

- **portale-egov**: 

Nell'archivio **EXPRIVIA_SUAP_SUE.zip** sono presenti i codici sorgenti originali. 

### DEPLOY APPLICAZIONI
#### Verifica se il comando java funziona
InetAddress.getLocalHost().getHostAddress()
Sul server dare il comando hostname
Lanciare il comando java -jar testHostName.jar 

#### Verifica dei datasource
Bisogna controllare che i context.xml delle applicazioni di frontend siano configurati come riportato sotto /apache-tomcat/conf/Catalina/localhost

#### Frontoffice backend
Scaricare: Apache Maven 3.5.2

Scaricare: Java 1.8.0_162, vendor: Oracle Corporation

Scaricare: apache-tomcat-6.0.44 o **apache-tomcat-7.0.85** o apache-tomcat-8.5.30 o apache-tomcat-9.0.7

Caricare le lib condivise presenti nella directory TOMCAT_FO in: apache-tomcat-x/lib

Modificare il file server.xml mettendo: 

'Server port="9005"', 'Connector port="9080" protocol="HTTP/1.1"', 'Connector port="9009" protocol="AJP/1.3"'

Modificare il file tomcat-user.xml aggiugendo:

role rolename="manager-script
role rolename="manager-gui"
user username="admin" password="admpass" roles="manager-gui"
user username="admin1" password="admpass1" roles="manager-gui,manager-script"

Modificare il file settings.xml di maven aggiungendo:

< server>
< id>TomcatLocalhost< /id>
< username>admin1< /username>
< password>admpass1< /password>
< /server>

Verificare il file di configurazione ed eventualmente modificarlo: suapsue_fo_be/filters/sviluppo/configuration.properties

Caricare i database del frontoffice di suapsue come sotto riportato e lanciare i seguenti script per aggiornare i seguenti path:  
#Per ambiente di sviluppo comuni medi (Gioia del Colle)
#http://G5:8080/people/initProcess.do?processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&idBookmark=44&codEnte=8014&codEveVita=16&communeCode=000018&selectingCommune=true

UPDATE `people_cea`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  

UPDATE `peopledb_m`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `name` = 'absPathToService' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://192.168.11.128:8080/cross-ws/services/CrossService' WHERE nodobe = 'CROSS'  limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='1';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='4';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='9';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='12';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='17';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='20';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='25';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='28';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='33';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='36';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='41';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='44';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='49';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='52';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='57';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='60';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='65';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='68';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='73';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='76';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='81';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='84';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='89';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='92';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='97';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='100';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='105';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='108';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='113';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='116';
UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='121';


UPDATE `peopledb_m`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE nodobe = 'SIMPLE_DESK' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://192.168.11.20/wsCatasto/Catasto.svc' WHERE nodobe ='WSCATASTO' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://192.168.11.20/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE nodobe ='WSINDIRIZZI' limit 1000;

UPDATE `fedb_m`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE name = 'absPathToService' limit 1000;

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/esempi/ComuneHome.html' WHERE `id`='28';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/PeopleService/services/ServiceSoap' WHERE `id`='15';



# server di posta elettronica di gmail, smtp.gmail.com, abilitare in gmail le APP meno sicure
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='PASSWORD_EMAIL' WHERE `id`='9';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='INDIRIZZO_EMAIL' WHERE `id`='21';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='INDIRIZZO_EMAIL' WHERE `id`='22';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='465' WHERE `id`='25';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.gmail.com' WHERE `id`='5';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='true' WHERE `id`='14';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='INDIRIZZO_EMAIL' WHERE `id`='17';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='true' WHERE `id`='26';

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/tmp' WHERE `id`='41';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/workflow' WHERE `id`='7';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/services' WHERE `id`='16';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/people/attachments' WHERE `id`='6';

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000007&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9002' WHERE `id`='42';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000008&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8002' WHERE `id`='58';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000009&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9003' WHERE `id`='43';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000010&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8003' WHERE `id`='59';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000011&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9008' WHERE `id`='44';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000012&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8008' WHERE `id`='60';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000013&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9009' WHERE `id`='45';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000014&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8009' WHERE `id`='61';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000015&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9012' WHERE `id`='46';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000016&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8012' WHERE `id`='62';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000017&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9014' WHERE `id`='47';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000018&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8014' WHERE `id`='63';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000019&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9015' WHERE `id`='48';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000020&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8015' WHERE `id`='64';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000021&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9018' WHERE `id`='49';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000022&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8018' WHERE `id`='65';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000023&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9020' WHERE `id`='50';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000024&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8020' WHERE `id`='66';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000025&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9021' WHERE `id`='51';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000026&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8021' WHERE `id`='67';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000027&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9022' WHERE `id`='52';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000028&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8022' WHERE `id`='68';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000029&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9023' WHERE `id`='53';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000030&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8023' WHERE `id`='69';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000031&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9024' WHERE `id`='54';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000032&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8024' WHERE `id`='70';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000033&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9027' WHERE `id`='55';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000034&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8027' WHERE `id`='71';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000035&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9029' WHERE `id`='56';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000036&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8029' WHERE `id`='72';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000037&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9031' WHERE `id`='57';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000038&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8031' WHERE `id`='73';

# indirizzo email con cui si aprono le segnalazioni su JIRA
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='7';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='8';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='9';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='10';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='11';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='12';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='13';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='14';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='15';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='16';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='17';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='18';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='19';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='20';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='21';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='22';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='23';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='24';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='25';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='26';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='27';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='28';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='29';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='30';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='31';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='32';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='33';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='34';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='35';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='36';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='37';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='38';

# OpenCross
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/attachments' WHERE `id`='271';
UPDATE `opencross`.`configuration` SET `value`='http://suapsuebe.collaudoavmtb/BEService/services/setEventService' WHERE `name`='attachment.url.mypage' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/documents' WHERE `id`='65';
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/email' WHERE `id`='92';
UPDATE `opencross`.`configuration` SET `value`='http://suapsuecross.collaudoavmtb/cross-ws/services/RicercaPraticheService' WHERE `id`='155';
UPDATE `opencross`.`configuration` SET `value`='mail.host' WHERE `name`='mail.host' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.imap.host' WHERE `name`='mail.imap.host' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.imap.port' WHERE `name`='mail.imap.port' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.mittente' WHERE `name`='mail.mittente' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.password' WHERE `name`='mail.password' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.replyTo' WHERE `name`='mail.replyTo' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.username' WHERE `name`='mail.username' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='http://suapsuecross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='268';

UPDATE `opencross`.`plugin_configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/attachments' WHERE `id`='447';
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsCatasto/Catasto.svc' WHERE `id`='452';
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.fascicolo.autore' WHERE `name`='avbari.fascicolo.autore' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='451';
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.protocollo.ws.password' WHERE `name`='avbari.protocollo.ws.password' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.protocollo.ws.username' WHERE `name`='avbari.protocollo.ws.username' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsIntegrazioneSueSuap/IntegrazioneSueSuap.svc' WHERE `id`='450';

UPDATE `opencross`.`plugin_configuration` SET `value`='http://atticonservazione.collaudoavmtb/amministrazione-atti-portlet/determina' WHERE `name`='avbari.documento.determina.endpoint' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://protocollo.collaudoavmtb/ws_fascicolo' WHERE `name`='avbari.fascicolo.ws.endpoint' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://protocollo.collaudoavmtb/ws_fascicolo' WHERE `name`='avbari.protocollo.ws.endpoint' limit 1000;


Lanciare il comando di suapsue_fo_be: mvn clean install

Lanciare il comando di Apache Tomcat: ./catalina.sh run

Lanciare il comando: redeploy.sh

#### Frontoffice frontend
Scaricare: apache-tomcat-6.0.44 o **apache-tomcat-7.0.85**

Creare la directory: apache-tomcat-x/backup/directoryTemp

Caricare le lib condivise presenti nella directory TOMCAT_FO condivise in: apache-tomcat-6.0.44/lib

Modificare il file tomcat-user.xml aggiugendo:

<role rolename="manager-script"/>
<role rolename="manager-gui"/>
<user username="admin" password="admpass" roles="manager-gui"/>
<user username="admin1" password="admpass1" roles="manager-gui,manager-script" />

Verificare il file di configurazione ed eventualmente modificarlo: suapsue_fo_fe/filters/sviluppo/configuration.xml

Lanciare il comando di suapsue_fo_fe: mvn clean install

Lanciare il comando di Apache Tomcat: ./catalina.sh run

Lanciare il comando di suapsue_fo_fe: redeploy.sh

Installare openoffice 3.0.0 (OOO300_m9_native_packed-1_it.9358) o openoffice 3.1.1 () scaricato a partire da:

Ubuntu 18.04 - http://ftp5.gwdg.de/pub/openoffice/archive/localized/it/3.1.1/OOo_3.1.1_LinuxX86-64_install_it_deb.tar.gz
Centos 7 - http://ftp5.gwdg.de/pub/openoffice/archive/localized/it/3.1.1/OOo_3.1.1_LinuxX86-64_install_wJRE_it.tar.gz

ed avviare il servizio con i seguenti comandi:

    cd /opt/openoffice.org3/program
    ./soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
    
Installare plugin Chrome: Modify Headers
Importare il file nel plugin Modify Headers: modifyheaders-Bitonto-ambienteTest.json o modifyheaders-GioiaDelColle-ambienteTest.json


### FILE DI CONFIGURAZIONE APPLICATIVI

#### Frontoffice backend

##### NOTA PER Dbm
Una volta avviato il server Tomcat la url principale è: http://localhost:8080/Dbm

Username: CTTTCN57P51F205W@idp-people.it
Password: wego2013

Per modificare il template di partenza:
http://localhost:8080/Dbm -> Menu di secondo livello -> Template
Editare il file template_BARI_SUAP_V4.odt con OpenOffice 3.0.0 o OpenOffice 4.4.1

##### NOTA PER BEService
Una volta avviato il server Tomcat, i services dei ws rispondono all'url: http://localhost:9080/BEService/services

##### NOTA PER ws_regauth
Una volta avviato il server Tomcat, i services dei ws rispondono all'url: http://localhost:9080/ws_regauth/services

##### NOTA PER ws_accr_121
Una volta avviato il server Tomcat, i services dei ws rispondono all'url: http://localhost:9080/ws_accr121/services

#### Frontoffice frontend

##### NOTA PER people
Una volta avviato il server Tomcat la url principale è: http://localhost:8080/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006

##### NOTA PER DynamicOdtServiceWego
Una volta avviato il server Tomcat, i services dei ws rispondono all'url: http://localhost:8080/DynamicOdtServiceWego/services

## BUILDING E DEPLOY APPLICAZIONE

### PREREQUISITI BUILDING APPLICAZIONE
Per poter buildare l'applicativo è necessario che siano soddisfatti i seguenti prerequisiti sulla macchina target su cui buildare

- JDK: è necessario che la JDK sia installata. Si richiede installazione di JDK 1.8
- Maven: è necessario che il software Apache Maven sia installato sulla macchina. Il software è stato testato su Apache Maven 3.5.2. Quindi una qalsiasi versione di Maven 3.x dovrebbe essere sufficiente

### PREREUISITI DEPLOY APPLICAZIONE

Per poter deployare l'applicazione è necessario che i seguenti prerequisiti siano soddisfatti:
- MySQL: è necessario avere un DB MySQL installato e configurato come indicato nella sezione di creazione DB


### CONFIGURAZIONE E CREAZIONE DATABASE

I DB da SUAPSUE sono installati su my-sql. 

È importante configurare my-sql come segue:

- **case insensitive per il nome delle tabelle**: questo pur essendo fondamentale per SUAP-SUE è importante anche per il protocollo. È possibile configurare my-sql editando il file **mysql.cnf** aggiungendo la proprietà **lower_case_table_names    = 1**
- **settaggio sql-mode opportuno**: nelle nuove versioni di my-sql vengono imposte delle restrizioni sulle query SQL. In adoc alcune query non sono SQL compliant al 100%; per evitare problemi è importante editare il file **mysql.cnf** aggiungendo la proprietà **sql-mode = ""**
- **settaggio max_allowed_packet opportuno**: per l'import sul db di grossi dump. In **mysql.cnf** si configura la property **max_allowed_packet=64M**

I DB utilizzati da SUAPSUE sono per il frontoffice
- **fedb_bari**
- **fedb_g**
- **idppeople_bari**
- **idppeople_g**
- **people_bari**
- **people_cea**
- **people_cea_edilizia**
- **peopledb_g**
- **people_g**
- **sirac_bari**
- **sirac_g**

È possibile partire dai dump dei db di produzione del 04/04/2018 presenti nel file SS_DB_BCK_Databases.rar


### PROCEDURA CONFIGURAZIONE SERVER LDAP


### BUILDING ARTEFATTI SOFTWARE

I progetti si basano su maven. Per la compilazione e l'import degli stessi in un IDE (e.g.) i passi da effettuare sono i seguenti:

1. configurare sulla macchina target apache maven e jdk (i test sono stati effettuati con apache maven 3.5.2 e JDK oracle 1.8)
2. clonare il repository GIT utilizzando l'apposito URL
3. installare le librerie necessarie nel repository locale di maven utilizzando **installazioneLibrerieNecessarie.sh** per macchine linux oppure **installazioneLibrerieNecessarie.bat** per macchine windows
4. configurare il proprio IDE di riferimento affinché utilizzi il maven configurato sulla macchina di sviluppo
5. importare i progetti come progetti maven esistenti
6. configurare nel proprio IDE l'utilizzo di un servlet container (e.g. apache tomcat)
7. aggiungere nel gitignore i file che non si vogliono tenere sotto tracciatura git (per esempio tutti i file di configurazione del proprio IDE)

La scelta dell'IDE è lasciata al team di sviluppo e sarà compito degli sviluppatori importare e configurare il progetto come meglio si crede. Si noti che è **obbligatorio non committare e pushare su GIT file di configurazione relativi agli IDE**. Questi file se presenti verranno cancellati

### DETTAGLI ORGANIZZAZIONE MAVEN

Si noti che si utilizza il seguente approcio. Ad esempio, nella directory aaf-adoc/src/main/webapp è stata creata la directory META-INF con all'interno il file **context.xml** (aaf-adoc/src/main/webapp/META-INF) contenente tutte le configurazioni di adoc. 

Per queste configurazioni vengono utilizzati i filtri di maven. In particolare nella directory *aaf-adoc* (contenente il sorgente del protocollo) e nella directory *smartgov --> smartgov-controlpanel* è presenta la directory **filters** contenente le configurazioni per i vari ambienti. I profili di riferimento per entrambi i progetti sono:
- **sviluppo**: rappresenta il profilo attivo per default e orientato allo sviluppo sulle macchine di development
- **collaudo**: rappresenta il profilo orientato alla costruzione degli artefatti configurati per l'ambiente di test/collaudo del comune di Bari
- **produzione**: rappresenta il profilo orientato alla costruzione degli artefatti configurati per l'ambiente di produzione del comune di Bari

Per costruire i vari artefatti digitare dopo aver lanciato i file `.sh` o `.bat` indicati in precedenza è sufficiente digitare:
- `mvn clean install -DskipTests`: verranno creati gli artefatti dell'ambiente di sviluppo (attivo di default)
- `mvn clean install -P collaudo -DskipTests`: verranno creati gli artefatti dell'ambiente di collaudo
- `mvn clean install -P produzione -DskipTests`: verranno creati gli artefatti dell'ambiente di produzione

Per l'ambiente di sviluppo comuni grandi (Bitonto)
UPDATE `people_cea`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:9080/BEService/services/BEService' WHERE `id`='1';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='3';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='18';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='30';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='34';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='40';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='43';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='24';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='25';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='31';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='36';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='41';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='44';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:9080/BEService/services/BEService' WHERE `id`='29';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='27';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='37';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='46';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='28';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='38';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='45';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='21';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='22';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='32';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='35';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='39';
UPDATE `peopledb_g`.`benode` SET `reference`='http://192.168.11.28:8080/cross-ws/services/CrossService' WHERE `id`='42';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='1';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='2';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='3';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='4';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='5';
UPDATE `peopledb_g`.`fenode` SET `reference`='http://G5:8080/FEService/services/FEInterface' WHERE `id`='6';
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `fedb_g`.`configuration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';  
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/esempi/ComuneHome.html' WHERE `id`='28';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/PeopleService/services/ServiceSoap' WHERE `id`='15';

#smtp.gmail.com
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='xyz' WHERE `id`='9';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='gfcinque@gmail.com' WHERE `id`='21';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='gfcinque@gmail.com' WHERE `id`='22';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='465' WHERE `id`='25';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.gmail.com' WHERE `id`='5';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='true' WHERE `id`='14';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='gfcinque@gmail.com' WHERE `id`='17';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='true' WHERE `id`='26';
# email non corretto, per non fare inviare email
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.password' WHERE `id`='9';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.sender' WHERE `id`='21';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.sender.backend' WHERE `id`='22';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.serviceport' WHERE `id`='25';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.serviceurl' WHERE `id`='5';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.useauth' WHERE `id`='14';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.username' WHERE `id`='17';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='smtp.mail.usessl' WHERE `id`='26';

UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/tmp' WHERE `id`='41';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/workflow' WHERE `id`='7';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/logs/services' WHERE `id`='16';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/giuseppe/Documents/java/project/AreaVasta/sviluppo/suapsue/frontoffice/fe/apache-tomcat-7.0.85_8080/backup/people/attachments' WHERE `id`='6';

Per l'ambiente di test
#Per ambiente di test comuni medi (Gioia del Colle)
#http://suapsuefe.collaudoavmtb/people/initProcess.do?processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&idBookmark=44&codEnte=8014&codEveVita=16&communeCode=000018&selectingCommune=true

UPDATE `people_cea`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  

UPDATE `peopledb_m`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `name` = 'absPathToService' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuecross.collaudoavmtb/cross-ws/services/CrossService' WHERE nodobe = 'CROSS'  limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='1';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='4';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='9';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='12';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='17';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='20';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='25';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='28';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='33';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='36';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='41';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='44';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='49';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='52';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='57';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='60';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='65';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='68';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='73';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='76';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='81';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='84';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='89';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='92';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='97';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='100';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='105';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='108';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='113';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWegoSue/services/DynamicOdtService' WHERE `id`='116';
UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='121';


UPDATE `peopledb_m`.`benode` SET `reference`='http://suapsuefe.collaudoavmtb/SimpleDesk/services/ProcessDataPdfService' WHERE nodobe = 'SIMPLE_DESK' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://testsit.egov.ba.it/wsCatasto/Catasto.svc' WHERE nodobe ='WSCATASTO' limit 1000;

UPDATE `peopledb_m`.`benode` SET `reference`='http://testsit.egov.ba.it/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE nodobe ='WSINDIRIZZI' limit 1000;

UPDATE `fedb_m`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE name = 'absPathToService' limit 1000;

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/esempi/ComuneHome.html' WHERE `id`='28';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/PeopleService/services/ServiceSoap' WHERE `id`='15';



# server di posta elettronica di gmail, smtp.gmail.com, abilitare in gmail le APP meno sicure
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.password' WHERE `id`='9';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.sender' WHERE `id`='21';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.sender.backend' WHERE `id`='22';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.serviceport' WHERE `id`='25';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.serviceurl' WHERE `id`='5';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.useauth' WHERE `id`='14';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.username' WHERE `id`='17';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='smtp.mail.usessl' WHERE `id`='26';

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/backup/tmp' WHERE `id`='41';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/backup/workflow' WHERE `id`='7';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/services' WHERE `id`='16';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/backup/people/attachments' WHERE `id`='6';

UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000007&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9002' WHERE `id`='42';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000008&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8002' WHERE `id`='58';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000009&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9003' WHERE `id`='43';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000010&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8003' WHERE `id`='59';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000011&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9008' WHERE `id`='44';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000012&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8008' WHERE `id`='60';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000013&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9009' WHERE `id`='45';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000014&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8009' WHERE `id`='61';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000015&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9012' WHERE `id`='46';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000016&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8012' WHERE `id`='62';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000017&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9014' WHERE `id`='47';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000018&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8014' WHERE `id`='63';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000019&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9015' WHERE `id`='48';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000020&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8015' WHERE `id`='64';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000021&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9018' WHERE `id`='49';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000022&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8018' WHERE `id`='65';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000023&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9020' WHERE `id`='50';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000024&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8020' WHERE `id`='66';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000025&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9021' WHERE `id`='51';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000026&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8021' WHERE `id`='67';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000027&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9022' WHERE `id`='52';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000028&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8022' WHERE `id`='68';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000029&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9023' WHERE `id`='53';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000030&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8023' WHERE `id`='69';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000031&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9024' WHERE `id`='54';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000032&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8024' WHERE `id`='70';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000033&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9027' WHERE `id`='55';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000034&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8027' WHERE `id`='71';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000035&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9029' WHERE `id`='56';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000036&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8029' WHERE `id`='72';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000037&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9031' WHERE `id`='57';
UPDATE `fedb_m`.`nodeconfiguration` SET `value`='http://suapsuefe.collaudoavmtb/people/initProcess.do?communeCode=000038&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8031' WHERE `id`='73';

# indirizzo email con cui si aprono le segnalazioni su JIRA
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='7';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='8';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='9';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='10';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='11';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='12';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='13';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='14';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='15';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='16';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='17';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='18';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='19';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='20';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='21';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='22';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='23';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='24';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='25';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='26';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='27';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='28';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='29';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='30';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='31';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='32';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='33';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='34';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='35';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='36';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='37';
UPDATE `people_m`.`user_profile` SET `E_MAIL`='INDIRIZZO_EMAIL_RICEZIONE_SEGNALAZIONE' WHERE `OID`='38';
# OpenCross
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/attachments' WHERE `id`='271';
UPDATE `opencross`.`configuration` SET `value`='http://suapsuebe.collaudoavmtb/BEService/services/setEventService' WHERE `name`='attachment.url.mypage' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/documents' WHERE `id`='65';
UPDATE `opencross`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/email' WHERE `id`='92';
UPDATE `opencross`.`configuration` SET `value`='http://suapsuecross.collaudoavmtb/cross-ws/services/RicercaPraticheService' WHERE `id`='155';
UPDATE `opencross`.`configuration` SET `value`='mail.host' WHERE `name`='mail.host' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.imap.host' WHERE `name`='mail.imap.host' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.imap.port' WHERE `name`='mail.imap.port' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.mittente' WHERE `name`='mail.mittente' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.password' WHERE `name`='mail.password' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.replyTo' WHERE `name`='mail.replyTo' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='mail.username' WHERE `name`='mail.username' limit 1000;
UPDATE `opencross`.`configuration` SET `value`='http://suapsuecross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='268';

UPDATE `opencross`.`plugin_configuration` SET `value`='/home/areavasta/server/apache-tomcat-8.0.48_9090/cross_files/attachments' WHERE `id`='447';
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsCatasto/Catasto.svc' WHERE `id`='452';
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.fascicolo.autore' WHERE `name`='avbari.fascicolo.autore' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='451';
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.protocollo.ws.password' WHERE `name`='avbari.protocollo.ws.password' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='avbari.protocollo.ws.username' WHERE `name`='avbari.protocollo.ws.username' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://testsit.egov.ba.it/wsIntegrazioneSueSuap/IntegrazioneSueSuap.svc' WHERE `id`='450';

UPDATE `opencross`.`plugin_configuration` SET `value`='http://atticonservazione.collaudoavmtb/amministrazione-atti-portlet/determina' WHERE `name`='avbari.documento.determina.endpoint' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://protocollo.collaudoavmtb/ws_fascicolo' WHERE `name`='avbari.fascicolo.ws.endpoint' limit 1000;
UPDATE `opencross`.`plugin_configuration` SET `value`='http://protocollo.collaudoavmtb/ws_fascicolo' WHERE `name`='avbari.protocollo.ws.endpoint' limit 1000;

Per l'ambiente di test senza porta
UPDATE `fedb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `fedb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='17';  
UPDATE `people_cea`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `peopledb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';  
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_fe_collaudo.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='18';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_fe_collaudo.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='34';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_fe_collaudo.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='43';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_fe_collaudo.collaudoavmtb/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='5';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='1';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='4';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='21';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='22';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='32';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='35';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='39';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross.collaudoavmtb/cross-ws/services/CrossService' WHERE `id`='42';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='27';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='37';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='46';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='28';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='38';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='45';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='7';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatasto/Catasto.svc' WHERE `id`='10';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='9';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo.collaudoavmtb/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='11';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_9080/backup/people/attachments' WHERE `id`='6';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_9080/logs/services' WHERE `id`='16';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://suap_sue_fe_collaudo.collaudoavmtb/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
UPDATE `peopledb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='17';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://localhost.collaudoavmtb/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='3';


Per l'ambiente di sviluppo collegandosi al db di test
UPDATE `fedb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `fedb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='17';  
UPDATE `people_cea`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/logs/errors' WHERE `id`='1';  
UPDATE `peopledb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `peopledb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='5';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='19';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='33';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='48';  
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='63';
UPDATE `fedb_g`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='78';  
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='18';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='34';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='43';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='5';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='1';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='4';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='21';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='22';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='32';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='35';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='39';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='42';

UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='27';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='37';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='46';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='28';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='38';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='45';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='7';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='10';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='9';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='11';

UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_9080/backup/people/attachments' WHERE `id`='6';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_9080/logs/services' WHERE `id`='16';

UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
# nuovo
UPDATE `peopledb_bari`.`configuration` SET `value`='/home/areavasta/server/apache-tomcat-7.0.85_8080/webapps/people/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico' WHERE `id`='17';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://localhost:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='3';



Per l'ambiente di sviluppo collegandosi al db in localhost in Windows 7
UPDATE `fedb_bari`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='5';  
UPDATE `fedb_bari`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='17';  
UPDATE `people_cea`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\logs\errors' WHERE `id`='1';  
UPDATE `people_cea_edilizia`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\logs\errors' WHERE `id`='1';  
UPDATE `peopledb_bari`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='5';  
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='19';  
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='33';
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='48';
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='63';
UPDATE `peopledb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='78';
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='5';  
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='19';  
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='33';  
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='48';  
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='63';
UPDATE `fedb_g`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='78';  
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='18';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='34';
UPDATE `peopledb_g`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='43';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://G5:8080/DynamicOdtServiceWego/services/DynamicOdtService' WHERE `id`='5';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='1';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='4';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='21';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='22';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='32';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='35';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='39';
UPDATE `peopledb_g`.`benode` SET `reference`='http://suap_sue_cross:8180/cross-ws/services/CrossService' WHERE `id`='42';

UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='27';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='37';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='46';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='28';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='38';
UPDATE `peopledb_g`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='45';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='7';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatasto/Catasto.svc' WHERE `id`='10';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='9';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://sit_collaudo/wsCatastoFabbricati/CatastoFabbricatiService.svc' WHERE `id`='11';

UPDATE `fedb_g`.`nodeconfiguration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_9080\backup\people\attachments' WHERE `id`='6';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_9080\logs\services' WHERE `id`='16';

UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8019' WHERE `id`='46';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9019' WHERE `id`='45';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8017' WHERE `id`='44';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9017' WHERE `id`='43';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8006' WHERE `id`='42';
UPDATE `fedb_g`.`nodeconfiguration` SET `value`='http://G5:8080/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9006' WHERE `id`='29';
# nuovo
UPDATE `peopledb_bari`.`configuration` SET `value`='C:\Users\User\Documents\java\program\server\eclipse\apache-tomcat-7.0.85_8080\webapps\people\WEB-INF\classes\it\people\fsl\servizi\concessioniedautorizzazioni\servizicondivisi\procedimentounico' WHERE `id`='17';
UPDATE `peopledb_bari`.`benode` SET `reference`='http://localhost:8080/SimpleDesk/services/ProcessDataPdfService' WHERE `id`='3';

