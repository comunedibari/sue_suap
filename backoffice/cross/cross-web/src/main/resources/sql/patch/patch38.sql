ALTER TABLE staging CHANGE COLUMN `tipo_messaggio` `tipo_messaggio` VARCHAR(255) NOT NULL ;

ALTER TABLE pratiche_protocollo CHANGE COLUMN `modalita` `modalita` VARCHAR(255) NULL DEFAULT NULL ;
