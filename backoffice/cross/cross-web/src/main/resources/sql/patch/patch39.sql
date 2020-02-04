ALTER TABLE `lk_tipo_unita` 
ADD COLUMN `id_tipo_sistema_catastale` INT(11) NOT NULL DEFAULT 1 AFTER `cod_tipo_unita`,
ADD INDEX `lk_tipo_sistema_catastale_idx` (`id_tipo_sistema_catastale` ASC);
ALTER TABLE `lk_tipo_unita` 
ADD CONSTRAINT `lk_tipo_sistema_catastale`
  FOREIGN KEY (`id_tipo_sistema_catastale`)
  REFERENCES `lk_tipo_sistema_catastale` (`id_tipo_sistema_catastale`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `lk_tipo_unita` CHANGE COLUMN `cod_tipo_unita` `cod_tipo_unita` VARCHAR(16) NULL DEFAULT NULL ;
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('P.C.T. (Particella catastale terreni)', 'PCT', '2');
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('P.C.E. (Particella catastale edificiale)', 'PCE', '2');
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('P.C.N. (Particella catastale nuova)', 'PCN', '2');
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('CAT.T. (Catastale Terreni)', 'CATT', '2');
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('CAT.E. (Catastale Edificiale)', 'CATE', '2');
INSERT INTO `lk_tipo_unita` (`descrizione`, `cod_tipo_unita`, `id_tipo_sistema_catastale`) VALUES ('Scrittura Teresiana', 'ST', '2');

