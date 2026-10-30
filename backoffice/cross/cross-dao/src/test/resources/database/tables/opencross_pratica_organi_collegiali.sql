CREATE TABLE pratica_organi_collegiali ( id int,
id_pratica_organi_collegiali int NOT NULL AUTO_INCREMENT,
id_pratica int NOT NULL,
id_organi_collegiali int NOT NULL,
data_richiesta datetime NOT NULL,
id_stato_pratica_organi_collegiali int NOT NULL,
id_seduta int DEFAULT NULL)