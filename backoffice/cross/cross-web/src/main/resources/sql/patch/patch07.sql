-- Aggiunta store_procedure per eliminare una pratica a cascata --- !!! ATTENZIONE: DA USARE SOLAMENTE DA AMMINISTRATORI CAZZUTI !!!
-- Per chiamarla: CALL delete_pratica('CTTTCN57P51F205W-002543-1536668');

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`people_demo`@`%` PROCEDURE `delete_pratica`(identificativo_pratica_par TEXT)
BEGIN

-- DECLARE error INT DEFAULT 0; 
-- DECLARE CONTINUE HANDLER FOR SQLSTATE '23000' SET error = 1; 
-- START TRANSACTION; 


DELETE FROM scadenze WHERE id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par);
DELETE FROM pratiche_eventi_allegati where id_pratica_evento IN (select id_pratica_evento FROM pratiche_eventi where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par));
DELETE FROM pratiche_eventi_anagrafiche where id_pratica_evento IN (select id_pratica_evento FROM pratiche_eventi where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par));
DELETE FROM pratiche_eventi_enti where id_pratica_evento IN (select id_pratica_evento FROM pratiche_eventi where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par));
DELETE FROM email where id_pratica_evento IN (select id_pratica_evento FROM pratiche_eventi where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par));
DELETE FROM pratiche_eventi where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par);
DELETE FROM pratica_procedimenti where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par);
UPDATE pratica set id_staging=NULL WHERE identificativo_pratica=identificativo_pratica_par;
DELETE FROM staging WHERE id_staging IN (select id_staging from pratica p where p.identificativo_pratica=identificativo_pratica_par);

DELETE FROM pratica_anagrafica where id_pratica IN (select id_pratica from pratica p where p.identificativo_pratica=identificativo_pratica_par);
DELETE FROM pratica WHERE identificativo_pratica=identificativo_pratica_par;


-- IF (error = 1) THEN 
-- ROLLBACK; 
-- ELSE 
-- COMMIT; 
-- END IF; 

END

-- 31/01/14 : Aggiunta configurazione URL cross-ws per Console errori
INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('path.ws.cross.ricezione', 'http://localhost:8080/cross-ws/services/CrossService', 'Endpoint webservice ricezione pratiche di cross');
