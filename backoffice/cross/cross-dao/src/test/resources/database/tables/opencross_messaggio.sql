CREATE TABLE messaggio (  id_messaggio int NOT NULL AUTO_INCREMENT,
testo text NOT NULL,
data_messaggio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
id_utente_mittente int NOT NULL,
id_utente_destinatario int NOT NULL,
id_pratica int DEFAULT NULL,
status varchar(1) NOT NULL DEFAULT 'U')