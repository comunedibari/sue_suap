ALTER TABLE `enti_comuni` ADD COLUMN `cod_nodo` VARCHAR(45) NULL;

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('EntiComuni_header_grid_cod_nodo', 'it', 'Nodo People');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('EntiComuni_field_form_cod_nodo', 'it', 'Codice nodo People');

ALTER TABLE `interventi` ADD COLUMN `ordinamento` INT(9) NULL DEFAULT NULL ;
UPDATE `interventi` set `ordinamento`=`cod_int`;
ALTER TABLE `interventi` CHANGE COLUMN `ordinamento` `ordinamento` INT(9) NOT NULL  ;

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Interventi_field_form_ordinamento', 'it', 'Ordinamento');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Interventi_header_grid_ordinamento', 'it', 'Ordinamento');