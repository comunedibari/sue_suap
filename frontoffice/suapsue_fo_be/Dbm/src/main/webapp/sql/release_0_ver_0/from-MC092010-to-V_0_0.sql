alter table tipi_aggregazione add column href varchar(50) default null;

alter table oneri drop column cod_classe_ente;

alter table interventi drop column indice_seq;

alter table procedimenti drop column cod_classe_ente;

alter table documenti drop column nome_file;

CREATE TABLE `oneri_interventi` (
  `cod_int` int(9) NOT NULL,
  `cod_oneri` varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (`cod_int`,`cod_oneri`),
  KEY `oneri_interventi_idx` (`cod_int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into oneri_interventi (cod_int,cod_oneri) select cod_int, cod_oneri from oneri_procedimenti a join interventi b on a.cod_proc=b.cod_proc;

CREATE TABLE `norme_comuni` (
  `cod_rif` varchar(8) NOT NULL,
  `cod_com` varchar(8) NOT NULL,
  `flg` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`cod_rif`,`cod_com`),
  KEY `norme_comuni_idx` (`cod_com`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table sportelli_comuni;

alter table cud drop column cod_classe_ente;

CREATE TABLE `templates_vari` (
  `nome_template` varchar(50) NOT NULL DEFAULT '',
  `des_template` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`nome_template`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `templates_vari_risorse` (
  `nome_template` varchar(50) NOT NULL DEFAULT '',
  `cod_sport` varchar(10) NOT NULL DEFAULT '',
  `cod_lang` varchar(2) NOT NULL DEFAULT '',
  `doc_blob` longtext,
  `nome_file` varchar(255) NOT NULL DEFAULT '',
  `tipo_file` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nome_template`,`cod_sport`,`cod_lang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table anagrafica add column azione varchar(50) default null;

alter table anagrafica add column flg_precompilazione char(1) NOT NULL default 'N';

CREATE TABLE `notifiche_documenti` (
  `cnt` int(10) unsigned NOT NULL,
  `tip_doc` varchar(100) NOT NULL DEFAULT '',
  `nome_file` varchar(255) NOT NULL DEFAULT '',
  `doc_blob` longblob NOT NULL,
  `cod_lang` varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (`cnt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `notifiche_sql` (
  `cnt` int(10) unsigned NOT NULL,
  `sql_text` longtext NOT NULL,
  PRIMARY KEY (`cnt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `sportelli` ADD COLUMN `id_mail_server` VARCHAR(50) NULL DEFAULT NULL , ADD COLUMN `id_protocollo` VARCHAR(50) NULL DEFAULT NULL;

ALTER TABLE `allegati` ADD COLUMN `ordinamento` INT(4) NULL DEFAULT 9999;

ALTER TABLE `enti_comuni` CHANGE COLUMN `prg_href` `estensione_firma` VARCHAR(4) NULL DEFAULT NULL;

ALTER TABLE `interventi` ADD COLUMN `cod_ente_origine` VARCHAR(8) NULL DEFAULT NULL , ADD COLUMN `cod_int_origine` INT(9) NULL DEFAULT NULL  ;

ALTER TABLE `notifiche` ADD COLUMN `cod_elemento` VARCHAR(200) NULL DEFAULT NULL ;

ALTER TABLE `notifiche` ADD INDEX `idx_notifiche` (`cod_elemento` ASC) ;

ALTER TABLE `sportelli` ADD COLUMN `id_backoffice` VARCHAR(50) NULL DEFAULT NULL ;

ALTER TABLE `documenti` ADD INDEX `idx_documenti` (`href` ASC) ;

ALTER TABLE `allegati` ADD INDEX `allegati_idx2` (`cod_cond` ASC) ;

ALTER TABLE `interventi_collegati` ADD INDEX `interventi_collegati_idx2` (`cod_cond` ASC) ;
