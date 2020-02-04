INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('cross.processo.default.sottopratiche.codice', null, 'Processo di default per i procedimenti-enti');

ALTER TABLE `pratiche_eventi` 
ADD COLUMN `pratica_evento_ref` INT NULL AFTER `id_recapito_notifica`,
ADD INDEX `pratica_eventi_pratica_eventi_idx` (`pratica_evento_ref` ASC);
ALTER TABLE `pratiche_eventi` 
ADD CONSTRAINT `pratica_eventi_pratica_eventi`
  FOREIGN KEY (`pratica_evento_ref`)
  REFERENCES `pratiche_eventi` (`id_pratica_evento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `pratiche_protocollo` 
ADD COLUMN `id_pratica_evento` INT NULL AFTER `modalita`,
ADD INDEX `pratiche_protocollo_pratiche_eventi_idx` (`id_pratica_evento` ASC);
ALTER TABLE `pratiche_protocollo` 
ADD CONSTRAINT `pratiche_protocollo_pratiche_eventi`
  FOREIGN KEY (`id_pratica_evento`)
  REFERENCES `pratiche_eventi` (`id_pratica_evento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
