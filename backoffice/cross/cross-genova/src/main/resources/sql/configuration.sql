INSERT INTO configuration (NAME, VALUE, note) VALUES ('anagrafe.user','CONTI999', 'Username per integrazione servizio toponomastica');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('anagrafe.password','CONTI999', 'Password per integrazione servizio toponomastica');
INSERT INTO configuration (NAME, VALUE, note) values ('anagrafe.fisica.plugin.id', 'anagrafeGenova', '');
INSERT INTO configuration (NAME, VALUE, note) values ('anagrafe.endpoint', 'http://wstoponomastica.comune.genova.it:8080/toponomastica/Toponomastica.jws', 'Endpoint servizio di anagrafe');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.edilizia.bo.endpoint','http://wstoponomastica.comune.genova.it:8080/SUE/Sue.jws', 'Endpoint del serivizio di ricezione delle pratiche del BO Edilizia');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.edilizia.bo.comunicazione.endpoint','http://wstoponomastica.comune.genova.it:8080/SUE/Sue.jws', 'Endpoint del serivizio di ricezione delle comunicazioni del BO Edilizia');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('webservices.plugin.id','webservicesGenova', 'Plugin di gestione dei webservices');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('views.plugin.id','genovaCustom', 'Customizzazione dati catastali');

INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.toponomastica.endpoint','http://wstoponomastica.comune.genova.it:8080/toponomastica/Toponomastica.jws', 'Endpoint servizio toponomastica');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.attachment.url.visura','http://localhost:8081/cross/services/RicercaPraticheService', 'Endpoint servizio visura di cross');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.attachment.url.mypage','http://localhost:8080/BEService/services/setEventService', 'Endpoint servizio setEventService');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.attachment.tmp.folder','C:\\Temp\\tmpCross', 'Path salvataggio allegati');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.attachment.nodo','000001', 'Nodo su cui caricare gli allegati');
INSERT INTO configuration (NAME, VALUE, note) VALUES ('genova.attachment.file.method','getFile', '');