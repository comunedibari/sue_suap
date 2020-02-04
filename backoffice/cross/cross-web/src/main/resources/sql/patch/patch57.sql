ALTER TABLE `errori` DROP FOREIGN KEY `fk_errori_dizionario`;
ALTER TABLE `errori` CHANGE COLUMN `cod_errore` `cod_errore` VARCHAR(100) NULL DEFAULT NULL ;
ALTER TABLE `dizionario_errori` CHANGE COLUMN `cod_errore` `cod_errore` VARCHAR(100) NOT NULL ;
ALTER TABLE `errori` ADD CONSTRAINT `fk_errori_dizionario` FOREIGN KEY (`cod_errore`) REFERENCES `dizionario_errori` (`cod_errore`) ON DELETE NO ACTION ON UPDATE NO ACTION;
INSERT INTO `dizionario_errori` (`cod_errore`, `descrizione`) values ('ERRORE_WORKFLOW_EXECUTE', 'Errore nella esecuzione dell\'evento');
INSERT INTO `dizionario_errori` (`cod_errore`, `descrizione`) values ('ERRORE_WORKFLOW_AUTOMATIC_EVENT', 'Errore nella creazione degli eventi automatici');
INSERT INTO `dizionario_errori` (`cod_errore`, `descrizione`) values ('ERRORE_WORKFLOW_EXECUTE_AUTOMATIC_EVENT', 'Errore nella esecuzione degli eventi automatici');