CREATE TABLE `oc_sedute_commissione` (
  `id_seduta_commissione` INT NOT NULL AUTO_INCREMENT,
  `id_seduta` INT NOT NULL,
  `id_pratica_organi_collegiali` INT NULL,
  `id_anagrafica` INT NOT NULL,
  `id_ruolo_commissione` INT NOT NULL,
  PRIMARY KEY (`id_seduta_commissione`),
  UNIQUE INDEX `oc_sedute_commissione_idx` (`id_seduta` ASC, `id_pratica_organi_collegiali` ASC),
  INDEX `oc_sedute_commissione_fk2_idx` (`id_pratica_organi_collegiali` ASC),
  INDEX `oc_sedute_commissione_fk3_idx` (`id_anagrafica` ASC),
  INDEX `oc_sedute_commisione_fk4_idx` (`id_ruolo_commissione` ASC),
  CONSTRAINT `oc_sedute_commissione_fk`
    FOREIGN KEY (`id_seduta`)
    REFERENCES `organi_collegiali_sedute` (`id_seduta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oc_sedute_commissione_fk2`
    FOREIGN KEY (`id_pratica_organi_collegiali`)
    REFERENCES `pratica_organi_collegiali` (`id_pratica_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oc_sedute_commissione_fk3`
    FOREIGN KEY (`id_anagrafica`)
    REFERENCES `anagrafica` (`id_anagrafica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oc_sedute_commisione_fk4`
    FOREIGN KEY (`id_ruolo_commissione`)
    REFERENCES `lk_ruoli_commissione` (`id_ruolo_commissione`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
