CREATE TABLE `lingue_aggregazioni` (
  `tip_aggregazione` varchar(4) NOT NULL,
  `cod_lang` varchar(4) NOT NULL,
  PRIMARY KEY (`tip_aggregazione`,`cod_lang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE  TABLE `destinatari_testi` (
  `cod_dest` VARCHAR(8) NOT NULL ,
  `cod_lang` VARCHAR(2) NOT NULL ,
  `intestazione` VARCHAR(255) NOT NULL ,
  `nome_dest` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`cod_dest`, `cod_lang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `destinatari_testi` (`cod_dest`,`cod_lang`,`intestazione`,`nome_dest`) 
select `cod_dest`,'it',`intestazione`,`nome_dest` from `destinatari`;

ALTER TABLE `destinatari` DROP COLUMN `nome_dest` , DROP COLUMN `intestazione` ;

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('menu_1_8','it','Lingue');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_header_grid_tip_aggregazione','it','Cod. Aggregazione');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_header_grid_des_aggregazione','it','Des. Aggregazione');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_header_grid_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_field_form_tip_aggregazione','it','Cod. Aggregazione');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_field_form_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_field_form_des_aggregazione','it','Des. Aggregazione');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('LingueAggregazioni_header_form','it','Lingue Aggregazioni');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('TemplateVari_field_form_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('TemplateVari_field_form_cod_lang_select','it','Selezionare ...');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('TemplateVari_header_grid_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('ImmaginiTemplate_field_form_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('ImmaginiTemplate_field_form_cod_lang_select','it','Selezionare ...');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('ImmaginiTemplate_header_grid_cod_lang','it','Cod. Lingua');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Anagrafica_field_marcatore_incrociato','it','Marcatore incrociato');

CREATE TABLE `sportelli_param_prot_ws` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ref_sport` varchar(10) NOT NULL,
  `name` varchar(250) NOT NULL,
  `value` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `sportelli` ADD COLUMN `send_protocollo_param` VARCHAR(8) NOT NULL  DEFAULT 'false' AFTER `firma_off_line` ;
ALTER TABLE `sportelli` ADD COLUMN `flg_option_allegati` VARCHAR(1) NOT NULL  DEFAULT 'N';
ALTER TABLE `sportelli` ADD COLUMN `flg_oggetto_riepilogo` VARCHAR(1) NOT NULL  DEFAULT 'S';
ALTER TABLE `sportelli` ADD COLUMN `flg_oggetto_ricevuta` VARCHAR(1) NOT NULL  DEFAULT 'S';

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Sportelli_field_form_protocollo', 'it', 'Invia parametri protocollo');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_option_allegati','it','Radio allegati facoltativi');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_option_allegati_select','it','Selezionare ...');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_option_allegati_S','it','Attiva selezione obbligatoria');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_option_allegati_N','it','Attiva selezione facoltativa');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_oggetto_riepilogo','it','Oggetto nel riepilogo');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_oggetto_riepilogo_select','it','Selezionare ...');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_oggetto_riepilogo_S','it','Inserisci oggetto nel riepilogo');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_oggetto_riepilogo_N','it','Non inserire l''oggetto nel riepilogo');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_oggetto_ricevuta','it','Oggetto nella ricevuta');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_flg_oggetto_ricevuta_select','it','Selezionare ...');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_oggetto_ricevuta_S','it','Inserisci oggetto nella ricevuta');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values ('Sportelli_field_form_combo_flg_oggetto_ricevuta_N','it','Non inserire l''oggetto nella ricevuta');

ALTER TABLE `allegati`   
  ADD COLUMN `flg_verifica_firma` INT(1) DEFAULT 0  NOT NULL AFTER `ordinamento`;

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Allegati_field_form_combo_flg_verifica_firma_0','it','No');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Allegati_field_form_combo_flg_verifica_firma_1','it','Si\'');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Allegati_field_form_combo_flg_verifica_firma','it','Verifica firma');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Allegati_field_form_combo_flg_verifica_firma_select','it','Selezionare ...');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Allegati_header_grid_flg_verifica_firma','it','Ver. firma');

commit;