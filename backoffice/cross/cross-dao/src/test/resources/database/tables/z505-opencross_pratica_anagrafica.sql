CREATE TABLE pratica_anagrafica (
  id_pratica int NOT NULL,
  id_anagrafica int NOT NULL,
  id_tipo_ruolo int NOT NULL,
  data_inizio_validita date DEFAULT NULL,
  id_tipo_qualifica int DEFAULT NULL,
  descrizione_titolarita varchar(255) DEFAULT NULL,
  assenso_uso_pec char(1) DEFAULT NULL,
  pec varchar(255) DEFAULT NULL,
  id_recapito_notifica int DEFAULT NULL,
  flg_ditta_individuale varchar(1) DEFAULT NULL
) 