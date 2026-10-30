CREATE TABLE lk_stato_pratica ( id int,
id_stato_pratica int NOT NULL AUTO_INCREMENT,
descrizione varchar(255) NOT NULL,
codice varchar(10) DEFAULT NULL,
grp_stato_pratica varchar(10) DEFAULT NULL)