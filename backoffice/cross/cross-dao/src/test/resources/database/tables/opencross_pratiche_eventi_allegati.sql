CREATE TABLE pratiche_eventi_allegati ( id int,
id_pratica_evento int NOT NULL,
id_allegato int NOT NULL,
flg_is_principale varchar(1) NOT NULL DEFAULT 'N',
flg_is_to_send varchar(1) NOT NULL DEFAULT 'N')