ALTER TABLE `interventi` ADD COLUMN `data_modifica` TIMESTAMP NULL DEFAULT NULL ;
ALTER TABLE `interventi` ADD COLUMN `data_pubblicazione` TIMESTAMP NULL DEFAULT NULL ;
ALTER TABLE `interventi` ADD COLUMN `flg_pubblicazione` CHAR(1) NULL DEFAULT NULL ;

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('menu_2_34', 'it', 'Pubblicazione interventi');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Pubblicazione_header_grid_cod_int', 'it', 'Codice intervento');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Pubblicazione_header_grid_tit_int', 'it', 'Titolo intervento');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Pubblicazione_header_grid_data_modifica', 'it', 'Ultima modifica');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Pubblicazione_header_grid_pubblicazione', 'it', 'Pubblicazione interventi');

INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('urlTCR', 'http://localhost:8080/TCR/Pubblicazione', 'url di pubblicazione (TCR)');