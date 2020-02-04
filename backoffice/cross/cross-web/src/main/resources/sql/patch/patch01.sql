-- Gestione nazionalita
CREATE  TABLE `lk_nazionalita` (
  `id_nazionalita` INT NOT NULL AUTO_INCREMENT ,
  `descrizione` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id_nazionalita`) );
ALTER TABLE `lk_nazionalita` ENGINE = InnoDB ;
ALTER TABLE `lk_nazionalita` ADD COLUMN `cod_nazionalita` VARCHAR(10) NOT NULL  AFTER `id_nazionalita` ;


ALTER TABLE `anagrafica` ADD COLUMN `id_nazionalita` INT NULL  AFTER `id_cittadinanza` ;

ALTER TABLE `anagrafica` 
  ADD CONSTRAINT `anagrafica_nazionalita`
  FOREIGN KEY (`id_nazionalita` )
  REFERENCES `lk_nazionalita` (`id_nazionalita` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `anagrafica_nazionalita_idx` (`id_nazionalita` ASC) ;

-- Aggiunta colonna cod_ente 
ALTER TABLE `enti` ADD COLUMN `cod_ente` VARCHAR(255) NULL  AFTER `id_ente` ;

DROP TABLE IF EXISTS `errori`;

CREATE TABLE `errori` (
  `id_errore` int(11) NOT NULL auto_increment,
  `id_pratica` varchar(45) default NULL,
  `id_utete` int(11) default NULL,
  `cod_errore` varchar(5) default NULL,
  `status` char(1) default 'A',
  `data` timestamp NULL default CURRENT_TIMESTAMP,
  `descrizione` varchar(255) default NULL,
  `trace` text,
  PRIMARY KEY  (`id_errore`),
  KEY `fk_errori_dizionario` (`cod_errore`),
  KEY `fk_utenti` (`id_utete`),
  CONSTRAINT `fk_errori_dizionario` FOREIGN KEY (`cod_errore`) REFERENCES `dizionario_errori` (`cod_errore`) ON UPDATE NO ACTION,
  CONSTRAINT `fk_utenti` FOREIGN KEY (`id_utete`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

insert into `configuration` (`name`, `value`) values ('giorni.sincronizzazione.protocollo', '10');

ALTER TABLE `errori` DROP FOREIGN KEY `fk_utenti` ;
ALTER TABLE `errori` CHANGE COLUMN `id_pratica` `id_pratica` INT NULL DEFAULT NULL  , CHANGE COLUMN `id_utete` `id_utente` INT(11) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `fk_utenti`
  FOREIGN KEY (`id_utente` )
  REFERENCES `utente` (`id_utente` )
  ON DELETE RESTRICT
  ON UPDATE RESTRICT, 
  ADD CONSTRAINT `fk_errori_id_pratica`
  FOREIGN KEY (`id_pratica` )
  REFERENCES `pratica` (`id_pratica` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_errori_id_pratica_idx` (`id_pratica` ASC) ;

INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('abilita.lettura.email', 'S', 'Attiva lo scheduler per la lettura delle email');
INSERT INTO `dizionario_errori` (`cod_errore`, `descrizione`) values ('SAPC', 'Errore salvando la pratica Comunica');
ALTER TABLE `enti` ADD COLUMN `identificativo_suap` VARCHAR(10) NULL  AFTER `codice_amministrazione` ;

CREATE  TABLE `messaggio` (
  `id_messaggio` INT NOT NULL AUTO_INCREMENT ,
  `testo` TEXT NOT NULL ,
  `data_messaggio` TIMESTAMP NOT NULL ,
  `id_utente_mittente` INT NOT NULL ,
  `id_utente_destinatario` INT NOT NULL ,
  `id_pratica` INT NULL ,
  PRIMARY KEY (`id_messaggio`) ,
  INDEX `messaggio_id_utente_mittente_idx` (`id_utente_mittente` ASC) ,
  INDEX `messaggio_id_utente_destinatario_idx` (`id_utente_destinatario` ASC) ,
  INDEX `messaggio_id_pratica_idx` (`id_pratica` ASC) ,
  CONSTRAINT `messaggio_id_utente_mittente`
    FOREIGN KEY (`id_utente_mittente` )
    REFERENCES `utente` (`id_utente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `messaggio_id_utente_destinatario`
    FOREIGN KEY (`id_utente_destinatario` )
    REFERENCES `utente` (`id_utente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `messaggio_id_pratica`
    FOREIGN KEY (`id_pratica` )
    REFERENCES `pratica` (`id_pratica` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `messaggio` ADD COLUMN `status` VARCHAR(1) NOT NULL DEFAULT 'U'  AFTER `id_pratica` ;

drop table `eventi_mail_messaggi`;
drop table `eventi_mail`;

CREATE  TABLE `comunicazione` (
  `id_comunicazione` INT NOT NULL AUTO_INCREMENT ,
  `id_pratica_evento` INT NOT NULL ,
  `id_ente` INT NOT NULL ,
  `id_pratica` INT NULL ,
  `data_comunicazione` TIMESTAMP NOT NULL ,
  `status` VARCHAR(1) NOT NULL DEFAULT 'U' ,
  PRIMARY KEY (`id_comunicazione`) ,
  INDEX `comunicazione_ente_destinatario_idx` (`id_ente` ASC) ,
  INDEX `comunicazione_pratica_collegata_idx` (`id_pratica` ASC) ,
  CONSTRAINT `comunicazione_pratica_evento`
    FOREIGN KEY (`id_pratica_evento` )
    REFERENCES `pratiche_eventi` (`id_pratica_evento` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comunicazione_ente_destinatario`
    FOREIGN KEY (`id_ente` )
    REFERENCES `enti` (`id_ente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comunicazione_pratica_collegata`
    FOREIGN KEY (`id_pratica` )
    REFERENCES `pratica` (`id_pratica` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `pratiche_eventi` ADD COLUMN `oggetto` VARCHAR(255) NULL  AFTER `id_utente` , ADD COLUMN `comunicazione` TEXT NULL  AFTER `oggetto` ;

INSERT INTO lk_stato_pratica (descrizione, codice, grp_stato_pratica) VALUES ('Ininfluente', 'IN', 'IN');

ALTER TABLE `pratica_anagrafica` ADD COLUMN `flg_ditta_individuale` VARCHAR(1) NULL  AFTER `id_recapito_notifica` ;

INSERT INTO `processi_eventi` 
(`id_processo`, `cod_evento`, `des_evento`, `stato_post`, `verso`, `flg_portale`, `flg_mail`, `flg_all_mail`, 
`flg_protocollazione`, `flg_ricevuta`, `flg_destinatari`, `flg_firmato`, `flg_apri_sottopratica`, 
`flg_destinatari_solo_enti`, `flg_visualizza_procedimenti`, `id_script_evento`, 
`id_script_protocollo`, `oggetto_email`, `corpo_email`, `funzione_applicativa`, `flg_automatico`) 
VALUES ('1', 'ANAG', 'Collegamento nuova anagrafica', '8', 'I', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'collegaanagrafica', 'N');
INSERT INTO `processi_eventi` 
(`id_processo`, `cod_evento`, `des_evento`, `stato_post`, `verso`, `flg_portale`, `flg_mail`, `flg_all_mail`, 
`flg_protocollazione`, `flg_ricevuta`, `flg_destinatari`, `flg_firmato`, `flg_apri_sottopratica`, 
`flg_destinatari_solo_enti`, `flg_visualizza_procedimenti`, `id_script_evento`, 
`id_script_protocollo`, `oggetto_email`, `corpo_email`, `funzione_applicativa`, `flg_automatico`) 
VALUES ('1', 'RANAG', 'Rimozione anagrafica', '8', 'I', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'collegaanagrafica', 'N');

UPDATE pratica p SET p.id_proc_ente=(SELECT pe.id_proc_ente FROM procedimenti_enti pe WHERE pe.id_ente=p.id_ente AND pe.id_proc=p.id_proc);

ALTER TABLE `pratiche_protocollo` ADD COLUMN `anno_fascicolo` INT(4) NULL  AFTER `n_fascicolo` ;

UPDATE pratiche_protocollo SET anno_fascicolo = anno_riferimento;

DROP VIEW IF EXISTS procedimenti_localizzati_view;
CREATE VIEW procedimenti_localizzati_view AS (
SELECT
  p.id_proc   AS id_proc,
  p.cod_proc  AS cod_proc,
  p.tipo_proc  AS tipo_proc,
  pt.des_proc AS des_proc,
  p.termini   AS termini,
  l.id_lang   AS id_lang,
  l.cod_lang  AS cod_lang
FROM procedimenti p
LEFT JOIN procedimenti_testi pt ON p.id_proc = pt.id_proc
LEFT JOIN lingue l ON pt.id_lang = l.id_lang);