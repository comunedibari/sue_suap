CREATE TABLE templates ( id int,
id_template int NOT NULL AUTO_INCREMENT,
template longtext NOT NULL,
descrizione varchar(45) DEFAULT NULL,
nome_file varchar(255) DEFAULT NULL,
mime_type varchar(50) DEFAULT NULL,
file_output varchar(5) NOT NULL DEFAULT 'PDF',
nome_file_originale varchar(255) DEFAULT NULL)