CREATE TABLE risultato_caricamento_pratiche ( id int,
id_risultato_caricamento int NOT NULL AUTO_INCREMENT,
identificativo_pratica varchar(20) DEFAULT NULL,
id_pratica int DEFAULT NULL,
esito_caricamento varchar(20) DEFAULT NULL,
descrizione_errore varchar(200) DEFAULT NULL,
data_caricamento datetime DEFAULT NULL,
id_utente int DEFAULT NULL,
nome_file_caricato varchar(200) DEFAULT NULL)