CREATE TABLE dati_catastali (
  id_immobile int NOT NULL AUTO_INCREMENT,
  id_pratica int NOT NULL,
  id_tipo_sistema_catastale int NOT NULL,
  sezione varchar(30) DEFAULT NULL,
  id_tipo_unita int DEFAULT NULL,
  foglio varchar(30) DEFAULT NULL,
  mappale varchar(30) DEFAULT NULL,
  id_tipo_particella int DEFAULT NULL,
  estensione_particella varchar(30) DEFAULT NULL,
  subalterno varchar(30) DEFAULT NULL,
  comune_censuario varchar(255) DEFAULT NULL,
  cod_immobile varchar(20) DEFAULT NULL,
  categoria varchar(45) DEFAULT NULL
  )