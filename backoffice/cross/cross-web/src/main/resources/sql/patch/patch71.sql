CREATE TABLE `lk_stati_sedute` (
  `id_stato_seduta` INT NOT NULL,
  `descrizione` VARCHAR(45) NOT NULL,
  `cod_stato_seduta`  VARCHAR(1) NOT NULL,
  PRIMARY KEY (`id_stato_seduta`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  ALTER TABLE `organi_collegiali_sedute` 
ADD COLUMN `id_stato_seduta` INT NULL AFTER `ora_conclusione`,
ADD INDEX `orani_collegiali_sedute_fk2_idx` (`id_stato_seduta` ASC);
ALTER TABLE `organi_collegiali_sedute` 
ADD CONSTRAINT `orani_collegiali_sedute_fk2`
  FOREIGN KEY (`id_stato_seduta`)
  REFERENCES `lk_stati_sedute` (`id_stato_seduta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `lk_stati_sedute` (`id_stato_seduta`, `descrizione`, `cod_stato_seduta`) VALUES ('1', 'Da convocare', 'A');
INSERT INTO `lk_stati_sedute` (`id_stato_seduta`, `descrizione`, `cod_stato_seduta`) VALUES ('2', 'Convocata', 'A');
INSERT INTO `lk_stati_sedute` (`id_stato_seduta`, `descrizione`, `cod_stato_seduta`) VALUES ('3', 'Conclusa', 'C');
