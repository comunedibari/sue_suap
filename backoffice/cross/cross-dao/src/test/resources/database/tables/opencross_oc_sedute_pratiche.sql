CREATE TABLE oc_sedute_pratiche (  id_seduta_pratica int NOT NULL AUTO_INCREMENT,
id_seduta int NOT NULL,
id_pratica_organi_collegiali int DEFAULT NULL,
sequenza int DEFAULT NULL,
note text)