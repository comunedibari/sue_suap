INSERT INTO `plugin_configuration` (`id_ente`,`id_comune`,`name`,`value`,`note`) VALUES (NULL,NULL,'avbari.documento.determina.endpoint','http://web99.linksmt.it/amministrazione-atti-portlet/determina',NULL);

INSERT ignore INTO `processi_eventi` (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`)
select id_processo,'INTEGRZIONE_ATTI','Integrazione atti/Delibere',8,NULL,NULL,NULL,'I','N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'integrazioneAttiAvBari','N' from processi;

insert ignore into processi_steps ( id_evento_trigger, id_evento_result, tipo_operazione) 
select p.id_evento, p1.id_evento, 'ADD' from processi_eventi p join processi_eventi p1 on p1.id_processo=p.id_processo and p1.cod_evento = 'INTEGRZIONE_ATTI' where p.cod_evento='APE';


