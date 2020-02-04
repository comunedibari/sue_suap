CREATE TABLE `pratica_eventi_procedimenti` (
  `id_pratica_evento` int(11) NOT NULL,
  `id_proc_ente` int(11) NOT NULL,
  PRIMARY KEY (`id_pratica_evento`,`id_proc_ente`),
  KEY `pratica_eventi_procedimenti_fk2_idx` (`id_proc_ente`),
  CONSTRAINT `pratica_eventi_procedimenti_fk1` FOREIGN KEY (`id_pratica_evento`) REFERENCES `pratiche_eventi` (`id_pratica_evento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pratica_eventi_procedimenti_fk2` FOREIGN KEY (`id_proc_ente`) REFERENCES `procedimenti_enti` (`id_proc_ente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


---- per attivare la nuova funzionalità di attivazione scelta procedimenti su comunicazione
---- 1) attivare il flg_visualizza_procedimenti = 'S'
---- 2) se si desidera limitare la scelta dei procedimenti da selezionare attivare il max_destinatari; se 0 o null unlimited, se # da 0 o null uno solo

insert ignore procedimenti_enti (id_proc,id_ente) select distinct id_procedimento, id_ente from pratica_procedimenti;

INSERT INTO `processi` (`cod_processo`,`des_processo`) VALUES ('SYS_DEFAULT','Processo fittizio enti non gestiti');

INSERT INTO `processi_eventi` (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`) 
select id_processo, 'RIC','Ricezione pratica',1,'ENTE',NULL,NULL,'I','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N' from processi where cod_processo = 'SYS_DEFAULT';

INSERT INTO `processi_eventi` (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`) 
select id_processo,'CLEAR_RICPAR','Evento di chiusura da ricezione parere da pratica padre',2,NULL,'ENTE',NULL,'O','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N' from processi where cod_processo = 'SYS_DEFAULT';

INSERT INTO `processi_steps` (`id_evento_trigger`,`id_evento_result`,`tipo_operazione`)
select id_evento, id_evento, 'SUB' from processi_eventi where id_processo = (select id_processo from processi where cod_processo = 'SYS_DEFAULT') and cod_evento = 'RIC';

INSERT INTO `processi_steps` (`id_evento_trigger`,`id_evento_result`,`tipo_operazione`)
select id_evento, (select id_evento from processi_eventi where id_processo = (select id_processo from processi where cod_processo = 'SYS_DEFAULT' and cod_evento = 'CLEAR_RICPAR')), 'ADD' from processi_eventi where id_processo = (select id_processo from processi where cod_processo = 'SYS_DEFAULT') and cod_evento = 'RIC';


INSERT INTO `processi_steps` (`id_evento_trigger`,`id_evento_result`,`tipo_operazione`)
select id_evento, id_evento, 'SUB' from processi_eventi where id_processo = (select id_processo from processi where cod_processo = 'SYS_DEFAULT' and cod_evento = 'CLEAR_RICPAR';


update procedimenti_enti set id_processo = (select id_processo from processi where cod_processo = 'SYS_DEFAULT')  where id_processo is null;