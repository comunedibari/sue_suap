CREATE TABLE procedimenti ( id int,
id_proc int NOT NULL AUTO_INCREMENT,
cod_proc varchar(50) NOT NULL,
termini int DEFAULT NULL,
tipo_proc varchar(10) DEFAULT NULL,
classifica varchar(255) DEFAULT NULL,
peso int DEFAULT NULL)