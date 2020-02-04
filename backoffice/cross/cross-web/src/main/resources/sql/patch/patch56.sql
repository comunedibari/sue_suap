CREATE TABLE `organi_collegiali` (
  `id_organi_collegiali` INT NOT NULL AUTO_INCREMENT,
  `des_organi_collegiali` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_organi_collegiali`));

CREATE TABLE `lk_stati_pratica_organi_collegiali` (
  `id_stato_pratica_organi_collegiali` INT NOT NULL,
  `des_stato_pratica_organi_collegiali` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_stato_pratica_organi_collegiali`));

ALTER TABLE `lk_stati_pratica_organi_collegiali` 
ADD COLUMN `codice` VARCHAR(10) NOT NULL AFTER `des_stato_pratica_organi_collegiali`;

CREATE TABLE `pratica_organi_collegiali` (
  `id_pratica_organi_collegiali` INT NOT NULL AUTO_INCREMENT,
  `id_pratica` INT NOT NULL,
  `id_organi_collegiali` INT NOT NULL,
  `data_richiesta` DATETIME NOT NULL,
  `id_stato_pratica_organi_collegiali` INT NOT NULL,
  PRIMARY KEY (`id_pratica_organi_collegiali`),
  INDEX `pratica_organi_coll_FK1_idx` (`id_pratica` ASC),
  INDEX `pratica_organi_coll_FK2_idx` (`id_organi_collegiali` ASC),
  CONSTRAINT `pratica_organi_coll_FK1`
    FOREIGN KEY (`id_pratica`)
    REFERENCES `pratica` (`id_pratica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pratica_organi_coll_FK2`
    FOREIGN KEY (`id_organi_collegiali`)
    REFERENCES `organi_collegiali` (`id_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `pratica_organi_coll_FK3`
    FOREIGN KEY (`id_stato_pratica_organi_collegiali`)
    REFERENCES `lk_stati_pratica_organi_collegiali` (`id_stato_pratica_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);