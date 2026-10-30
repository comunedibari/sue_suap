CREATE TABLE lk_stati ( id int,
id_stato int NOT NULL AUTO_INCREMENT,
descrizione varchar(255) NOT NULL,
cod_istat varchar(15) NOT NULL,
data_inizio date NOT NULL DEFAULT '1900-01-01',
data_fine date NOT NULL DEFAULT '2900-12-31',
cod_stato varchar(45) DEFAULT NULL)