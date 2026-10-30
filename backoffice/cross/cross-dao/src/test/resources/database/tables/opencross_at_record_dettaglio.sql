CREATE TABLE at_record_dettaglio ( id int,
id_at_record_dettaglio int NOT NULL AUTO_INCREMENT,
id_pratica int NOT NULL,
cod_fornitura varchar(5) NOT NULL,
record_dettaglio longblob NOT NULL,
data_inserimento date NOT NULL,
anno_riferimento int NOT NULL)