ALTER TABLE `oc_sedute_commissione` 
DROP FOREIGN KEY `oc_sedute_commissione_fk3`,
DROP FOREIGN KEY `oc_sedute_commisione_fk4`;
ALTER TABLE `oc_sedute_commissione` 
DROP COLUMN `id_ruolo_commissione`,
DROP COLUMN `id_anagrafica`,
DROP INDEX `oc_sedute_commisione_fk4_idx` ,
DROP INDEX `oc_sedute_commissione_fk3_idx` ;

ALTER TABLE `oc_sedute_commissione` 
RENAME TO  `oc_sedute_pratiche` ;

ALTER TABLE `oc_sedute_pratiche` 
CHANGE COLUMN `id_seduta_commissione` `id_seduta_pratica` INT(11) NOT NULL AUTO_INCREMENT ;

CREATE TABLE `oc_pratica_commissione` (
  `id_seduta_pratica` int(11) NOT NULL,
  `id_anagrafica` int(11) NOT NULL,
  `id_ruolo_commissione` int(11) NOT NULL,
  PRIMARY KEY (`id_seduta_pratica`,`id_anagrafica`),
  KEY `oc_pratica_commissione_fk2_idx` (`id_anagrafica`),
  KEY `oc_pratica_commissione_fk3_idx` (`id_ruolo_commissione`),
  CONSTRAINT `oc_pratica_commissione_fk` FOREIGN KEY (`id_seduta_pratica`) REFERENCES `oc_sedute_pratiche` (`id_seduta_pratica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `oc_pratica_commissione_fk2` FOREIGN KEY (`id_anagrafica`) REFERENCES `anagrafica` (`id_anagrafica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `oc_pratica_commissione_fk3` FOREIGN KEY (`id_ruolo_commissione`) REFERENCES `lk_ruoli_commissione` (`id_ruolo_commissione`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `lk_ruoli_commissione` 
ADD COLUMN `peso` INT NOT NULL DEFAULT 0 AFTER `descrizione`;

ALTER TABLE `pratica_organi_collegiali` 
ADD COLUMN `id_seduta` INT NULL AFTER `id_stato_pratica_organi_collegiali`,
ADD INDEX `pratica_organi_coll_FK4_idx` (`id_seduta` ASC);
ALTER TABLE `pratica_organi_collegiali` 
ADD CONSTRAINT `pratica_organi_coll_FK4`
  FOREIGN KEY (`id_seduta`)
  REFERENCES `organi_collegiali_sedute` (`id_seduta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `oc_sedute_pratiche` 
ADD COLUMN `sequenza` INT NULL AFTER `id_pratica_organi_collegiali`;