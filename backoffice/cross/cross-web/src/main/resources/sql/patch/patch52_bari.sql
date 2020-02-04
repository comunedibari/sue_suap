INSERT INTO `plugin_configuration` (`name`, `value`) VALUES ('avbari.sit.endpoint', 'http://web02test.hsh.it/wsIntegrazioneSueSuap/IntegrazioneSueSuap.svc');
INSERT INTO `plugin_configuration` (`name`, `value`) VALUES ('avbari.indirizzi.endpoint', 'http://web02test.hsh.it/wsCatastoFabbricati/CatastoFabbricatiService.svc');
INSERT INTO `plugin_configuration` (`name`, `value`) VALUES ('avbari.catasto.endpoint', 'http://web02test.hsh.it/wsCatasto/Catasto.svc');
UPDATE `configuration` SET `value`='gestionePraticaAvBari' WHERE name='pratica.plugin.id';
UPDATE `configuration` SET `value`='protocolloAvBari' WHERE name='protocollo.plugin.id';
-- abilita lo stradario per Comune Cassano delle Murge
INSERT INTO `plugin_configuration` (idEnte, `name`, `value`) VALUES (2210, 'avbari.esistenza.stradario', 'true');
INSERT INTO `plugin_configuration` (idEnte, `name`, `value`) VALUES (2210,'avbari.esistenza.catasto', 'true');

-- inserisce l'evento automatico su processo PDC e SCIAED
INSERT INTO `processi_eventi` (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`) 
select id_processo, 'AVVIO_AUT','Avvio Automatico',8,'ENTE','ENTE',NULL,'O','N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'integrazioneSITAvBari','S' from processi where cod_processo = 'PDC';

INSERT INTO `processi_eventi` (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`) 
select id_processo, 'AVVIO_AUT','Avvio Automatico',8,'ENTE','ENTE',NULL,'O','N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'integrazioneSITAvBari','S' from processi where cod_processo = 'SCIAED';


insert into processi_steps (id_evento_trigger, id_evento_result, tipo_operazione)
select (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'PDC') and cod_evento = 'APE') , (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'PDC') and cod_evento = 'AVVIO_AUT'), 'ADD' from dual;

insert into processi_steps (id_evento_trigger, id_evento_result, tipo_operazione)
select (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'SCIAED') and cod_evento = 'APE') , (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'SCIAED') and cod_evento = 'AVVIO_AUT'), 'ADD' from dual;

insert into processi_steps (id_evento_trigger, id_evento_result, tipo_operazione)
select (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'PDC') and cod_evento = 'AVVIO_AUT') , (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'PDC') and cod_evento = 'AVVIO_AUT'), 'SUB' from dual;

insert into processi_steps (id_evento_trigger, id_evento_result, tipo_operazione)
select (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'SCIAED') and cod_evento = 'AVVIO_AUT') , (select id_evento from processi_eventi where id_processo = (select id_processo from processi  where cod_processo = 'SCIAED') and cod_evento = 'AVVIO_AUT'), 'ADD' from dual;


INSERT INTO `plugin_configuration` (`name`, `value`) VALUES ('avbari.esistenza.stradario', 'TRUE');
INSERT INTO `plugin_configuration` (`name`, `value`) VALUES ('avbari.esistenza.catasto', 'TRUE');