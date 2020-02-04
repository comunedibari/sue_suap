CREATE TABLE `organi_collegiali_template` (
  `id_organi_collegiali_template` INT NOT NULL AUTO_INCREMENT,
  `id_organi_collegiali` INT NOT NULL,
  `tipologia_template` VARCHAR(45) NOT NULL,
  `id_template` INT NULL,
  PRIMARY KEY (`id_organi_collegiali_template`),
  INDEX `organi_collegiali_fk_idx` (`id_organi_collegiali` ASC),
  INDEX `template_fk_idx` (`id_template` ASC),
  UNIQUE INDEX `organi_collegiali_template_idx` (`id_organi_collegiali` ASC, `tipologia_template` ASC),
  CONSTRAINT `organi_collegiali_fk`
    FOREIGN KEY (`id_organi_collegiali`)
    REFERENCES `organi_collegiali` (`id_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `template_fk`
    FOREIGN KEY (`id_template`)
    REFERENCES `templates` (`id_template`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);