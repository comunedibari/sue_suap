CREATE TABLE errori ( id int,
id_errore int NOT NULL AUTO_INCREMENT,
id_pratica int DEFAULT NULL,
id_utente int DEFAULT NULL,
cod_errore varchar(100) DEFAULT NULL,
status char(1) DEFAULT 'A',
data timestamp NULL DEFAULT CURRENT_TIMESTAMP,
descrizione text,
trace text)