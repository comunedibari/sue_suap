CREATE TABLE lk_provincie ( id int,
id_provincie int NOT NULL AUTO_INCREMENT,
descrizione varchar(255) NOT NULL,
cod_catastale varchar(15) NOT NULL,
data_fine_validita date NOT NULL DEFAULT '2900-12-31',
flg_infocamere char(1) NOT NULL DEFAULT 'S')