CREATE TABLE allegati (  id int NOT NULL AUTO_INCREMENT,
descrizione varchar(255) DEFAULT NULL,
nome_file varchar(255) NOT NULL,
tipo_file varchar(255) NOT NULL,
path_file varchar(255) DEFAULT NULL,
id_file_esterno varchar(4000) DEFAULT NULL,
file longblob)