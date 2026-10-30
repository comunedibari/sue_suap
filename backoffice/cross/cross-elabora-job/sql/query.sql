SELECT 	
	c.`id_ente` AS id_ente,  
	e.descrizione AS descrizione_ente,
	ec.`id_comune` AS id_comune, 
	lkc.descrizione AS descrizione_comune,
	c.`name` AS nome_parametro, 
	c.`value` AS valore_parametro
 FROM ((((`opencross`.`configuration` c )
 JOIN `opencross`.`enti` e)
 JOIN opencross.enti_comuni ec)
 JOIN opencross.lk_comuni lkc)
 WHERE ((e.id_ente = c.id_ente)
 AND (ec.id_ente = e.id_ente)
 AND (lkc.id_comune = ec.id_comune))
 AND c.name IN (
'mail.host', 
'mail.imap.port',
'webmail.host',
'webmail.username',
'webmail.password',
'abilita.elab.inbox',
'abilita.elab.inbox.massivo'
) 
 AND e.descrizione IN (
'SUE Comune di Bari', 
'SUE Comune di Palo del Colle', 
'SUE Comune di Gioia del Colle',
'SUE Comune di Ruvo di Puglia'
)
 AND c.value IS NOT NULL
 AND c.value <> '';
 
 
 SELECT COUNT(1), c.id_ente, e.descrizione AS descrizione_ente, YEAR(c.data_invio_mail) anno
FROM (email_consegnate c
 JOIN `opencross`.`enti` e)
WHERE e.id_ente = c.id_ente 
GROUP BY c.id_ente, YEAR(c.data_invio_mail), e.descrizione
ORDER BY YEAR(c.data_invio_mail) DESC;

SELECT ec.* FROM (
SELECT 	
	e.`id_email`, 
	e.`id_pratica_evento`, 
	e.`data_inserimento`, 
	e.`data_aggiornamento`, 
	e.`path_eml`, 
	e.`id_messaggio`, 
	e.`id_messaggio_pec`, 
	e.`email_destinatario`, 
	e.`corpo_email`, 
	e.`oggetto_email`, 
	c.`oggetto_risposta`, 
	c.`corpo_risposta`, 
	e.`stato`, 
	e.`gruppo`, 
	e.`tipo_destinazione`
	 
FROM `opencross`.`email` e 
LEFT OUTER JOIN email_consegnate c ON (e.id_messaggio = msgid)
) ec 
WHERE ec.oggetto_risposta IS NOT NULL 
AND  ec.corpo_risposta IS NOT NULL 
LIMIT 0, 1000;


CREATE TABLE `email_consegnate_stacktrace` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `stacktrace` VARCHAR(255) DEFAULT NULL,
  `dataora` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


-- getEntiElabInboxGiornaliero
 SELECT DISTINCT c.`id_ente`, e.descrizione AS descrizione_ente, c2.value AS abilita
 FROM (((((`opencross`.`configuration` c )
 JOIN `opencross`.`enti` e)
 JOIN opencross.enti_comuni ec)
 JOIN opencross.lk_comuni lkc)
 JOIN `opencross`.`configuration` c2 ON (c2.id_ente = c.id_ente 
	AND LOWER(c2.name) = 'abilita.elab.inbox' AND c2.value = 'S'))
 WHERE ((e.id_ente = c.id_ente)
 AND (ec.id_ente = e.id_ente)
 AND (lkc.id_comune = ec.id_comune))
 AND c.name = 'mail.imap.host'
 AND c.value IS NOT NULL
 AND c.value <> ''
 AND e.descrizione LIKE 'SUE%';
 
 -- getEntiElabInboxMassivo 
SELECT 
DISTINCT c.`id_ente`, e.descrizione AS descrizione_ente, c2.value AS abilita
FROM (((((`opencross`.`configuration` c )
JOIN `opencross`.`enti` e)
JOIN opencross.enti_comuni ec)
JOIN opencross.lk_comuni lkc)
JOIN `opencross`.`configuration` c2 ON (c2.id_ente = c.id_ente 
AND LOWER(c2.name) = 'abilita.elab.inbox.massivo' AND c2.value = 'S'))
WHERE ((e.id_ente = c.id_ente)
AND (ec.id_ente = e.id_ente)
AND (lkc.id_comune = ec.id_comune))
AND c.name = 'mail.imap.host'
AND c.value IS NOT NULL
AND c.value <> ''
AND e.descrizione LIKE 'SUE%';	