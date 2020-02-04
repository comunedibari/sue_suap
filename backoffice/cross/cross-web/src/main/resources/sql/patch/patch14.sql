ALTER TABLE `lk_tipo_particella` DROP FOREIGN KEY `llk_tipo_particella_lk_tipo_sistema_catastale_fk1` ;
ALTER TABLE `lk_tipo_particella` DROP COLUMN `id_tipo_sistema_catastale` 
, DROP INDEX `llk_tipo_particella_lk_tipo_sistema_catastale_fk1` ;

DELETE FROM `lk_tipo_particella` WHERE `id_tipo_particella`='3';
DELETE FROM `lk_tipo_particella` WHERE `id_tipo_particella`='4';

ALTER TABLE `lk_comuni` ADD COLUMN `flg_tavolare` VARCHAR(5) NOT NULL DEFAULT 'false'  AFTER `id_stato` ;

ALTER TABLE `dati_catastali` DROP FOREIGN KEY `dati_catastali_fk5` ;
ALTER TABLE `dati_catastali` CHANGE COLUMN `id_comune` `id_comune_censuario` INT(11) NULL DEFAULT NULL  ;
ALTER TABLE `indirizzi_intervento` DROP FOREIGN KEY `indirizzo_intervento_fk2` ;
ALTER TABLE `indirizzi_intervento` DROP COLUMN `des_comune` , DROP COLUMN `id_comune` 
, DROP INDEX `indirizzo_intervento_fk2` ;