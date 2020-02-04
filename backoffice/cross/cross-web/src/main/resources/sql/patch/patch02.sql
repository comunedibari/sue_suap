-- 11/11/13 : Modifica per gestire correttamente i template degli eventi a cascata
ALTER TABLE `eventi_template` DROP FOREIGN KEY `eventi_template_enti_fk1` , DROP FOREIGN KEY `eventi_template_eventi_fk1` ;
ALTER TABLE `eventi_template` CHANGE COLUMN `id_evento` `id_evento` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `id_ente` `id_ente` INT(11) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `eventi_template_enti_fk1`
  FOREIGN KEY (`id_ente` )
  REFERENCES `enti` (`id_ente` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `eventi_template_eventi_fk1`
  FOREIGN KEY (`id_evento` )
  REFERENCES `processi_eventi` (`id_evento` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, DROP INDEX evento_procedimento_ente_unique 
, ADD UNIQUE INDEX evento_procedimento_ente_unique (id_evento ASC, id_ente ASC, id_procedimento ASC, id_template ASC) ;


