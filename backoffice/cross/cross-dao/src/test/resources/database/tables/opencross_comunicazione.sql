CREATE TABLE comunicazione ( id int,
id_comunicazione int NOT NULL AUTO_INCREMENT,
id_pratica_evento int NOT NULL,
id_ente int NOT NULL,
id_pratica int DEFAULT NULL,
data_comunicazione timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
status varchar(1) NOT NULL DEFAULT 'U')