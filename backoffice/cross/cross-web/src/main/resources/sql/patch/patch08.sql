-- Aggiunta tabella ugm_ugd_proc (unità gestione mittente e unità gestione destinatario) 

CREATE TABLE `ugm_ugd_proc` (
  `id_ugm` INT NOT NULL,
  `id_ugd_proc` INT NOT NULL,
  PRIMARY KEY (`id_ugm`, `id_ugd_proc`));

ALTER TABLE `ugm_ugd_proc` 
ADD INDEX `ugg_proc_fk_idx` (`id_ugd_proc` ASC);
ALTER TABLE `ugm_ugd_proc` 
ADD CONSTRAINT `ugm_fk`
  FOREIGN KEY (`id_ugm`)
  REFERENCES `enti` (`id_ente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `ugd_proc_fk`
  FOREIGN KEY (`id_ugd_proc`)
  REFERENCES `procedimenti_enti` (`id_proc_ente`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
