CREATE TABLE `organi_collegiali_sedute` (
  `id_seduta` INT NOT NULL AUTO_INCREMENT,
  `id_organo_collegiale` INT NOT NULL,
  `data_prevista` DATE NOT NULL,
  `data_convocazione` DATETIME NULL,
  `data_inizio` DATETIME NULL,
  `data_conclusione` DATETIME NULL,
  PRIMARY KEY (`id_seduta`),
  INDEX `organi_collegiali_sedute_fk_idx` (`id_organo_collegiale` ASC),
  CONSTRAINT `organi_collegiali_sedute_fk`
    FOREIGN KEY (`id_organo_collegiale`)
    REFERENCES `organi_collegiali` (`id_organi_collegiali`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `organi_collegiali_sedute` 
CHANGE COLUMN `data_convocazione` `data_convocazione` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `data_inizio` `data_inizio` DATE NULL DEFAULT NULL ,
CHANGE COLUMN `data_conclusione` `data_conclusione` DATE NULL DEFAULT NULL ,
ADD COLUMN `ora_convocazione` VARCHAR(20) NULL AFTER `data_convocazione`,
ADD COLUMN `ora_inizio` VARCHAR(20) NULL AFTER `data_inizio`,
ADD COLUMN `ora_conclusione` VARCHAR(20) NULL AFTER `data_conclusione`;
