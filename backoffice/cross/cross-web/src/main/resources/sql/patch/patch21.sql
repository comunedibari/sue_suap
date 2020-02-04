
--Eliminato il vincolo di obbligatorieta della funzione applicativa su un processo
ALTER TABLE `processi_eventi` CHANGE COLUMN `funzione_applicativa` `funzione_applicativa` VARCHAR(255) NULL DEFAULT 'comunicazione' ;
