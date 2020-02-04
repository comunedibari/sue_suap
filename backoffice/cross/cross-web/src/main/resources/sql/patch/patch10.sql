-- cambiata la dimensione

CREATE TABLE `indirizzi_intervento` (
  `id_indirizzo_intervento` int(11) NOT NULL AUTO_INCREMENT,
  `id_pratica` int(11) NOT NULL,
  `id_comune` int(11) DEFAULT NULL,
  `descrizione_comune` int(11) DEFAULT NULL,
  `localita` varchar(255) DEFAULT NULL,
  `id_dug` int(11) DEFAULT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `civico` varchar(45) DEFAULT NULL,
  `cap` varchar(20) DEFAULT NULL,
  `altre_informazioni_indirizzo` varchar(255) DEFAULT NULL,
  `cod_civico` varchar(15) DEFAULT NULL,
  `cod_via` varchar(15) DEFAULT NULL,
  `interno_numero` varchar(15) DEFAULT NULL,
  `interno_lettera` varchar(15) DEFAULT NULL,
  `interno_scala` varchar(15) DEFAULT NULL,
  `lettera` varchar(15) DEFAULT NULL,
  `colore` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_indirizzo_intervento`),
  KEY `indirizzo_intervento_fk1` (`id_pratica`),
  KEY `indirizzo_intervento_fk2` (`id_comune`),
  KEY `indirizzo_intervento_fk3` (`id_dug`),
  CONSTRAINT `indirizzo_intervento_fk1` FOREIGN KEY (`id_pratica`) REFERENCES `pratica` (`id_pratica`),
  CONSTRAINT `indirizzo_intervento_fk2` FOREIGN KEY (`id_comune`) REFERENCES `lk_comuni` (`id_comune`),
  CONSTRAINT `indirizzo_intervento_fk3` FOREIGN KEY (`id_dug`) REFERENCES `lk_dug` (`id_dug`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into indirizzi_intervento 
(id_pratica,id_comune,localita,id_dug,indirizzo,civico,cap,altre_informazioni_indirizzo,
cod_civico,cod_via,interno_numero,interno_lettera,interno_scala, lettera,colore)
select id_pratica,id_comune,localita,id_dug,indirizzo,civico,cap,altre_informazioni_indirizzo,
cod_civico,cod_via,interno_numero,interno_lettera,interno_scala, lettera,colore from dati_catastali;

delete from indirizzi_intervento where indirizzo is null or trim(indirizzo) = '';

ALTER TABLE `dati_catastali` DROP FOREIGN KEY `dati_catastali_fk7` , DROP FOREIGN KEY `dati_catastali_fk6` ;
ALTER TABLE `dati_catastali` DROP COLUMN `colore` , DROP COLUMN `lettera` , DROP COLUMN `interno_scala` , DROP COLUMN `interno_lettera` , DROP COLUMN `interno_numero` , DROP COLUMN `cod_civico` , DROP COLUMN `cod_via` , DROP COLUMN `cap` , DROP COLUMN `altre_informazioni_indirizzo` , DROP COLUMN `civico` , DROP COLUMN `indirizzo` , DROP COLUMN `id_dug` , DROP COLUMN `id_provincia` , DROP COLUMN `localita` 
, DROP INDEX `dati_catastali_fk7` 
, DROP INDEX `dati_catastali_fk6` ;

ALTER TABLE `pratiche_eventi` ADD COLUMN `descrizione_evento` VARCHAR(4000) NULL DEFAULT NULL  AFTER `id_evento` ;

ALTER TABLE `pratica` DROP COLUMN `cod_fascicolo` ;

ALTER TABLE `pratiche_protocollo` DROP COLUMN `n_fascicolo` , CHANGE COLUMN `anno_fascicolo` `anno_protocollo` INT(4) NULL DEFAULT NULL  ;
