CREATE TABLE processi_eventi_scadenze ( id int,
id_evento int NOT NULL,
id_ana_scadenza varchar(4) NOT NULL,
id_stato_scadenza varchar(4) NOT NULL COMMENT 'Se A apro la scadenza,
se C chiudo la scadenza',
termini_scadenza int DEFAULT NULL,
script_scadenza varchar(255) DEFAULT NULL,
flg_visualizza_scadenza varchar(1) NOT NULL DEFAULT 'N')