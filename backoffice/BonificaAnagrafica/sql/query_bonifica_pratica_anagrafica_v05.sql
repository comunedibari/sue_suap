DROP TABLE opencross_prod_prod.tmp_bonifica_pratica_anag;

CREATE TABLE opencross.tmp_bonifica_pratica_anag AS 
SELECT * FROM (
SELECT DISTINCT 

	cross_pra.identificativo_pratica,
	cross_pra.id_pratica,  
	cross_pra_anag.id_anagrafica,
	cross_ruolo.id_tipo_ruolo,
	civ_anag.tipoAnagrafica AS tipoAnagrafica_civ,
	civ_anag.cognome AS cognome_civ,
	civ_anag.nome AS nome_civ,
	civ_anag.codiceFiscale AS codiceFiscale_civ,
	civ_anag.partitaIva AS partitaIva_civ,
	civ_anag.denominazione AS ruolo_civ,

	cross_anag.`tipo_anagrafica` AS tipo_anagrafica_cross, 
	cross_anag.`cognome` AS cognome_cross, 
	cross_anag.`nome` AS nome_cross, 
	cross_anag.`codice_fiscale` AS codice_fiscale_cross, 
	cross_ruolo.`descrizione` AS ruolo_cross, 
	cross_anag.`partita_iva` AS partita_iva_cross, 
	cross_anag.`flg_individuale` AS flg_individuale_cross
		
	FROM `opencross`.`pratica` cross_pra 
	INNER JOIN `civilia_db`.`pratica` civ_pra ON (cross_pra.identificativo_pratica = civ_pra.codice)
	LEFT OUTER JOIN `opencross`.`pratica_anagrafica` cross_pra_anag ON (cross_pra_anag.id_pratica = cross_pra.id_pratica)
	LEFT OUTER JOIN `opencross`.`anagrafica` cross_anag ON (cross_anag.id_anagrafica = cross_pra_anag.id_anagrafica)
	LEFT OUTER JOIN `opencross`.`lk_tipo_ruolo` cross_ruolo ON (cross_ruolo.id_tipo_ruolo = cross_pra_anag.id_tipo_ruolo)
	LEFT OUTER JOIN civilia_db.anagrafica_civilia civ_anag ON (civ_anag.pratica_id = civ_pra.id)
	LEFT OUTER JOIN `opencross`.`ruoli_etichetta_civilia_cross` ruoli_etichetta ON (ruoli_etichetta.ruolo_etichetta_civilia_cross = civ_anag.denominazione)
WHERE 1=1
-- AND cross_pra.identificativo_pratica = 'SCIA-999-2013'
AND (
LOWER(cross_anag.`codice_fiscale`) =  LOWER(civ_anag.codiceFiscale)
OR LOWER(cross_anag.`partita_iva`) = LOWER(civ_anag.partitaIva)
OR ( LOWER(cross_anag.`cognome`) = LOWER(civ_anag.cognome) AND LOWER(cross_anag.`nome`) = LOWER(civ_anag.nome))
)) t;

ALTER TABLE `opencross`.`tmp_bonifica_pratica_anag`   
  CHANGE `id_anagrafica` `id_anagrafica` INT(11) NOT NULL,
  CHANGE `id_tipo_ruolo` `id_tipo_ruolo` INT(11) DEFAULT 0 NOT NULL, 
  ADD  KEY `ixd1` (`id_pratica`, `id_anagrafica`, `id_tipo_ruolo`);

-- select t.* from opencross.pratica_anagrafica pa
UPDATE opencross.pratica_anagrafica pa
JOIN ( SELECT 
	tpa.id_pratica,
	tpa.id_anagrafica,
	tpa.id_tipo_ruolo,
	tpb.id AS id_etichetta
	FROM opencross.tmp_bonifica_pratica_anag tpa
	JOIN ruoli_etichetta_civilia_cross tpb ON (tpb.ruolo_etichetta_civilia_cross = tpa.ruolo_civ)
	) t
SET pa.id_etichetta = t.id_etichetta
WHERE pa.id_pratica = t.id_pratica
AND pa.id_anagrafica = t.id_anagrafica
AND pa.id_tipo_ruolo = t.id_tipo_ruolo
;

 