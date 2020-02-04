CREATE TABLE `lk_ruoli_commissione` (
  `id_ruolo_commissione` INT NOT NULL AUTO_INCREMENT,
  `descrizione` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_ruolo_commissione`));

CREATE TABLE `organi_collegiali_commissione` (
  `id_organi_collegiali` INT NOT NULL,
  `id_anagrafica` INT NOT NULL,
  `id_ruolo_commissione` INT NOT NULL,
  PRIMARY KEY (`id_organi_collegiali`, `id_anagrafica`),
  INDEX `oc_commissione_fk2_idx` (`id_anagrafica` ASC),
  INDEX `oc_commissione_fk3_idx` (`id_ruolo_commissione` ASC),
  CONSTRAINT `oc_commissione_fk`
    FOREIGN KEY (`id_organi_collegiali`)
    REFERENCES `organi_collegiali` (`id_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oc_commissione_fk2`
    FOREIGN KEY (`id_anagrafica`)
    REFERENCES `anagrafica` (`id_anagrafica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oc_commissione_fk3`
    FOREIGN KEY (`id_ruolo_commissione`)
    REFERENCES `lk_ruoli_commissione` (`id_ruolo_commissione`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);