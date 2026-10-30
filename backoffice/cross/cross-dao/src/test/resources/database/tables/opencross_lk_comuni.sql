CREATE TABLE lk_comuni ( id int,
id_comune int NOT NULL AUTO_INCREMENT,
descrizione varchar(255) NOT NULL,
cod_catastale varchar(10) NOT NULL,
data_fine_validita date NOT NULL DEFAULT '2900-12-31',
id_provincia int DEFAULT NULL,
id_stato int DEFAULT NULL,
flg_tavolare varchar(5) NOT NULL DEFAULT 'false')