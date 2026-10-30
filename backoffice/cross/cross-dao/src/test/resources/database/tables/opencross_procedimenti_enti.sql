CREATE TABLE procedimenti_enti ( id int,
id_proc_ente int NOT NULL AUTO_INCREMENT,
id_proc int NOT NULL,
id_ente int NOT NULL,
id_processo int DEFAULT NULL,
responsabile_procedimento varchar(255) DEFAULT NULL,
id_ufficio int DEFAULT NULL)