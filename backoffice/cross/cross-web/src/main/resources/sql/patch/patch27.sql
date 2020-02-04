ALTER TABLE `pratiche_protocollo` 
CHANGE COLUMN `n_protocollo` `n_protocollo` VARCHAR(255) NULL DEFAULT NULL AFTER `cod_registro`,
CHANGE COLUMN `anno_protocollo` `anno_fascicolo` INT(4) NULL DEFAULT NULL ,
ADD COLUMN `n_fascicolo` VARCHAR(255) NULL DEFAULT NULL AFTER `anno_fascicolo`;

