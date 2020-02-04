CREATE TABLE `utente_ruolo_ente` (
  `id_utente_ruolo_ente` INT NOT NULL AUTO_INCREMENT,
  `id_utente` INT NOT NULL,
  `id_ente` INT NOT NULL,
  `cod_permesso` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id_utente_ruolo_ente`),
  INDEX `utente_ruolo_ente_fk_idx` (`id_utente` ASC),
  INDEX `utente_ruolo_ente_fk2_idx` (`id_ente` ASC),
  INDEX `utente_ruolo_ente_fk3_idx` (`cod_permesso` ASC),
  CONSTRAINT `utente_ruolo_ente_fk`
    FOREIGN KEY (`id_utente`)
    REFERENCES `utente` (`id_utente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `utente_ruolo_ente_fk2`
    FOREIGN KEY (`id_ente`)
    REFERENCES `enti` (`id_ente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `utente_ruolo_ente_fk3`
    FOREIGN KEY (`cod_permesso`)
    REFERENCES `permessi` (`cod_permesso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
CREATE TABLE `utente_ruolo_procedimento` (
  `id_utente_ruolo_ente` INT NOT NULL,
  `id_proc_ente` INT NOT NULL,
  PRIMARY KEY (`id_utente_ruolo_ente`, `id_proc_ente`),
  INDEX `utente_ruolo_procedimento_fk1_idx` (`id_proc_ente` ASC),
  CONSTRAINT `utente_ruolo_procedimento_fk`
    FOREIGN KEY (`id_utente_ruolo_ente`)
    REFERENCES `utente_ruolo_ente` (`id_utente_ruolo_ente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `utente_ruolo_procedimento_fk1`
    FOREIGN KEY (`id_proc_ente`)
    REFERENCES `procedimenti_enti` (`id_proc_ente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
ALTER TABLE `utente_ruolo_ente` 
ADD UNIQUE INDEX `utente_ruolo_ente_idx` (`id_utente` ASC, `id_ente` ASC, `cod_permesso` ASC);
	
insert into utente_ruolo_ente (id_utente,id_ente,cod_permesso) select distinct a.id_utente, p.id_ente, a.cod_permesso from ruoli a join procedimenti_enti p on a.id_proc_ente=p.id_proc_ente;
insert into utente_ruolo_procedimento (id_utente_ruolo_ente, id_proc_ente) select distinct u.id_utente_ruolo_ente, r.id_proc_ente from utente_ruolo_ente u join ruoli r on u.id_utente = r.id_utente and u.cod_permesso = r.cod_permesso 
join procedimenti_enti p on p.id_ente = u.id_ente and p.id_proc_ente = r.id_proc_ente; 