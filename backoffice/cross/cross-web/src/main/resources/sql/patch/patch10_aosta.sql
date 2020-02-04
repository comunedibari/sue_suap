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
  `latitudine` varchar(45) DEFAULT NULL,
  `longitudine` varchar(45) DEFAULT NULL,
  `dato_esteso_1` varchar(255) DEFAULT NULL,
  `dato_esteso_2` varchar(255) DEFAULT NULL,
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
cod_civico,cod_via,interno_numero,interno_lettera,interno_scala, lettera,colore, latitudine, longitudine, dato_esteso_1, dato_esteso_2)
select id_pratica,id_comune,localita,id_dug,indirizzo,civico,cap,altre_informazioni_indirizzo,
cod_civico,cod_via,interno_numero,interno_lettera,interno_scala, lettera,colore, null, null, latitudine, longitudine  from dati_catastali;

delete from indirizzi_intervento where indirizzo is null or trim(indirizzo) = '';

ALTER TABLE `dati_catastali` DROP FOREIGN KEY `dati_catastali_fk7` , DROP FOREIGN KEY `dati_catastali_fk6` ;
ALTER TABLE `dati_catastali` DROP COLUMN `colore` , DROP COLUMN `lettera` , 
DROP COLUMN `interno_scala` , DROP COLUMN `interno_lettera` , DROP COLUMN `interno_numero` , 
DROP COLUMN `cod_civico` , DROP COLUMN `cod_via` , DROP COLUMN `cap` , DROP COLUMN `altre_informazioni_indirizzo` ,
 DROP COLUMN `civico` , DROP COLUMN `indirizzo` , DROP COLUMN `id_dug` , DROP COLUMN `id_provincia` , DROP COLUMN `localita`
, DROP COLUMN `longitudine`, DROP COLUMN `latitudine` 
, DROP INDEX `dati_catastali_fk7` 
, DROP INDEX `dati_catastali_fk6` ;

ALTER TABLE `pratiche_eventi` ADD COLUMN `descrizione_evento` VARCHAR(4000) NULL DEFAULT NULL  AFTER `id_evento` ;

ALTER TABLE `pratica` DROP COLUMN `cod_fascicolo` ;

ALTER TABLE `pratiche_protocollo` DROP COLUMN `n_fascicolo` , CHANGE COLUMN `anno_fascicolo` `anno_protocollo` INT(4) NULL DEFAULT NULL  ;


DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='296';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='314';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='332';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='350';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='368';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='386';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='404';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='422';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='458';

DELETE FROM `cross_aosta`.`plugin_configuration` WHERE `id`='440';


insert into plugin_configuration (name,value) values('h00601.cod_civico','');
insert into plugin_configuration (name,value) values('h00601.cod_via','');
insert into plugin_configuration (name,value) values('h00601.colore','');
insert into plugin_configuration (name,value) values('h00601.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00601.interno_lettera','');
insert into plugin_configuration (name,value) values('h00601.interno_numero','');
insert into plugin_configuration (name,value) values('h00601.interno_scala','');
insert into plugin_configuration (name,value) values('h00601.lettera','');
insert into plugin_configuration (name,value) values('h00601.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00601.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00313.cod_civico','');
insert into plugin_configuration (name,value) values('h00313.cod_via','');
insert into plugin_configuration (name,value) values('h00313.colore','');
insert into plugin_configuration (name,value) values('h00313.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00313.interno_lettera','');
insert into plugin_configuration (name,value) values('h00313.interno_numero','');
insert into plugin_configuration (name,value) values('h00313.interno_scala','');
insert into plugin_configuration (name,value) values('h00313.lettera','');
insert into plugin_configuration (name,value) values('h00313.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00313.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00228.cod_civico','');
insert into plugin_configuration (name,value) values('h00228.cod_via','');
insert into plugin_configuration (name,value) values('h00228.colore','');
insert into plugin_configuration (name,value) values('h00228.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00228.interno_lettera','');
insert into plugin_configuration (name,value) values('h00228.interno_numero','');
insert into plugin_configuration (name,value) values('h00228.interno_scala','');
insert into plugin_configuration (name,value) values('h00228.lettera','');
insert into plugin_configuration (name,value) values('h00228.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00228.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00133.cod_civico','');
insert into plugin_configuration (name,value) values('h00133.cod_via','');
insert into plugin_configuration (name,value) values('h00133.colore','');
insert into plugin_configuration (name,value) values('h00133.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00133.interno_lettera','');
insert into plugin_configuration (name,value) values('h00133.interno_numero','');
insert into plugin_configuration (name,value) values('h00133.interno_scala','');
insert into plugin_configuration (name,value) values('h00133.lettera','');
insert into plugin_configuration (name,value) values('h00133.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00133.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00126.cod_civico','');
insert into plugin_configuration (name,value) values('h00126.cod_via','');
insert into plugin_configuration (name,value) values('h00126.colore','');
insert into plugin_configuration (name,value) values('h00126.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00126.interno_lettera','');
insert into plugin_configuration (name,value) values('h00126.interno_numero','');
insert into plugin_configuration (name,value) values('h00126.interno_scala','');
insert into plugin_configuration (name,value) values('h00126.lettera','');
insert into plugin_configuration (name,value) values('h00126.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00126.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00071.cod_civico','');
insert into plugin_configuration (name,value) values('h00071.cod_via','');
insert into plugin_configuration (name,value) values('h00071.colore','');
insert into plugin_configuration (name,value) values('h00071.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00071.interno_lettera','');
insert into plugin_configuration (name,value) values('h00071.interno_numero','');
insert into plugin_configuration (name,value) values('h00071.interno_scala','');
insert into plugin_configuration (name,value) values('h00071.lettera','');
insert into plugin_configuration (name,value) values('h00071.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00071.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00011.cod_civico','');
insert into plugin_configuration (name,value) values('h00011.cod_via','');
insert into plugin_configuration (name,value) values('h00011.colore','');
insert into plugin_configuration (name,value) values('h00011.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00011.interno_lettera','');
insert into plugin_configuration (name,value) values('h00011.interno_numero','');
insert into plugin_configuration (name,value) values('h00011.interno_scala','');
insert into plugin_configuration (name,value) values('h00011.lettera','');
insert into plugin_configuration (name,value) values('h00011.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00011.dato_esteso_2','');

insert into plugin_configuration (name,value) values ('indirizzi_intervento','h00001,h0011,h00071,h00126,h00133,h00228,h00313,h00358,h00601');
UPDATE plugin_configuration SET value='h00001,h00011,h00071,h00126,h00133,h00228,h00251,h00313,h00601' WHERE id='283';

insert into plugin_configuration (name,value) values('h00001.cod_civico','');
insert into plugin_configuration (name,value) values('h00001.cod_via','');
insert into plugin_configuration (name,value) values('h00001.colore','');
insert into plugin_configuration (name,value) values('h00001.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00001.interno_lettera','');
insert into plugin_configuration (name,value) values('h00001.interno_numero','');
insert into plugin_configuration (name,value) values('h00001.interno_scala','');
insert into plugin_configuration (name,value) values('h00001.lettera','');
insert into plugin_configuration (name,value) values('h00001.dato_esteso_1','');
insert into plugin_configuration (name,value) values('h00001.dato_esteso_2','');

insert into plugin_configuration (name,value) values('h00358.cod_civico','');
insert into plugin_configuration (name,value) values('h00358.cod_via','');
insert into plugin_configuration (name,value) values('h00358.colore','');
insert into plugin_configuration (name,value) values('h00358.descrizione_comune','');
insert into plugin_configuration (name,value) values('h00358.interno_lettera','');
insert into plugin_configuration (name,value) values('h00358.interno_numero','');
insert into plugin_configuration (name,value) values('h00358.interno_scala','');
insert into plugin_configuration (name,value) values('h00358.lettera','');
insert into plugin_configuration (name,value) values('h00358.dato_esteso_1','d010');
insert into plugin_configuration (name,value) values('h00358.dato_esteso_2','d020');