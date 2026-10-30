ALTER TABLE lk_tipo_pratica
ADD COLUMN visibilita_tutti char(1) DEFAULT null;

-- 

CREATE INDEX dati_catastali_ricerca
ON opencross.dati_catastali(id_tipo_unita, sezione, foglio, mappale, subalterno);

 CREATE INDEX indirizzi_intervento_indirizzo_civico
ON opencross.indirizzi_intervento(indirizzo, civico);

--

INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (3,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (5,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (7,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (8,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (9,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (10,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (11,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (12,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (13,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (14,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (15,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (16,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (17,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (18,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (19,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (20,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (21,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (22,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (23,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (24,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (25,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (26,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (27,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (28,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (29,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (30,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (31,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (32,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (33,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (34,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (35,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (36,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (37,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (38,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (39,'MODPROC','Cambio tecnico procuratore', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');

--

create index search_like_address in indirizzi_intervento(indirizzo);

--

ALTER TABLE pratica ADD descrizione_intervento text;

--

alter table pratica_anagrafica add id_etichetta int(11);
alter table lk_tipo_ruolo add column hidden tinyint(11);

--
-- (modified 24-01-15 - modified codice in codice_operazione and added codice)

create table operazione (
	id int auto_increment primary key,
	codice_operazione varchar(15) unique,
	codice varchar(15),
	descrizione text
);

--

create table pratica_operazione(
	id_pratica int,
    id_operazione int,
    primary key(id_pratica, id_operazione),
    foreign key (id_pratica) REFERENCES  pratica(id_pratica),
    foreign key (id_operazione) REFERENCES  operazione(id)
);

--

update pratica
set anno = year(data_ricezione)
where anno is null;

--
-- DF2 create new event (RIAS_PRAT) and associate it with RIC event

ALTER TABLE processi_eventi ADD giorni_default int;

DELIMITER $$
CREATE PROCEDURE populateRiasPrat ()
BEGIN
      DECLARE ric_id INTEGER DEFAULT 0;
	  DECLARE c_ric_id INTEGER DEFAULT 0;
      DECLARE rias_prat INTEGER DEFAULT 0;
      DECLARE finished INTEGER DEFAULT 0;
      DECLARE idProc integer DEFAULT 0;
      
	  DEClARE curs 
            CURSOR FOR 
                  SELECT ID_PROCESSO FROM processi;

      -- declare NOT FOUND handler
      DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

      OPEN curs;
	  SELECT ID_PROCESSO FROM processi;
      
      looper: LOOP
            FETCH curs INTO idProc;
            IF finished = 1 THEN 
                  LEAVE looper;
            END IF;
            INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (idProc,'RIASPRAT','Riassegnazione temporizzata', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
            SELECT count(*) into c_ric_id FROM PROCESSI_EVENTI WHERE COD_EVENTO = 'RIC' AND ID_PROCESSO = idProc;
            IF c_ric_id > 0 then
				SELECT ID_EVENTO into ric_id FROM PROCESSI_EVENTI WHERE COD_EVENTO = 'RIC' AND ID_PROCESSO = idProc;
                SELECT ID_EVENTO into rias_prat FROM PROCESSI_EVENTI WHERE COD_EVENTO = 'RIASPRAT' AND ID_PROCESSO = idProc;
				insert into PROCESSI_STEPS value(ric_id, rias_prat, 'ADD');
            end if;
      END LOOP looper;
      CLOSE curs;
END$$
DELIMITER ;

ALTER TABLE pratiche_eventi
add id_utente_riassegnazione int;

ALTER TABLE pratiche_eventi
ADD FOREIGN KEY (id_utente_riassegnazione) REFERENCES utente(id_utente);

ALTER TABLE pratiche_eventi
add data_riassegnazione date;

-- END DF2

-- R10: Basic authentication
INSERT INTO PLUGIN_CONFIGURATION(ID_ente, NAME, VALUE) VALUES(2243, 'avbari.sit.username', 'd3NfcnV2b2RpcHVnbGlh');
INSERT INTO PLUGIN_CONFIGURATION(ID_ENTE, NAME, VALUE) VALUES(2243, 'avbari.sit.password', 'cGFzc3dvcmQ=');
INSERT INTO PLUGIN_CONFIGURATION(ID_ENTE, NAME, VALUE) VALUES(2243, 'avbari.sit.parameter', 'Authentication');
-- END R10

-- R9: Multiple attachments split in more emails
insert into configuration(id_ente, id_comune, name, value) values (2001,893, 'mail.attachments.maxsize', 50000000),
	(2001,893, 'mail.attachments.maxnumber', 25),
	(2001,893, 'mail.attachments.protocol.maxsize', 50000000),
    (2304,8470, 'mail.attachments.maxsize', 50000000),
	(2304,8470, 'mail.attachments.maxnumber', 25),
	(2304,8470, 'mail.attachments.protocol.maxsize', 50000000),
   (2243,10233, 'mail.attachments.maxsize', 50000000),
	(2243,10233, 'mail.attachments.maxnumber', 25),
	(2243,10233, 'mail.attachments.protocol.maxsize', 50000000),
	 (2293,5440, 'mail.attachments.maxsize', 50000000),
	(2293,5440, 'mail.attachments.maxnumber', 25),
	(2293,5440, 'mail.attachments.protocol.maxsize', 50000000);
-- END R9

INSERT INTO PLUGIN_CONFIGURATION(ID_ente, NAME, VALUE) VALUES(2243, 'avbari.sit.username', 'd3NfcnV2b2RpcHVnbGlh');
INSERT INTO PLUGIN_CONFIGURATION(ID_ENTE, NAME, VALUE) VALUES(2243, 'avbari.sit.password', 'dGVzdCBpbnB1dA==');
INSERT INTO PLUGIN_CONFIGURATION(ID_ENTE, NAME, VALUE) VALUES(2243, 'avbari.sit.parameter', 'Authentication');

--
INSERT INTO CONFIGURATION(NAME, VALUE, note) VALUES ('cross.prod', 'TRUE', 'Indica che il sistema viene eseguito in ambiente di produzione');

--
alter table pratica add data_ultimo_evento_esterno datetime default '1900-01-01T00:00:00';

--

DELIMITER $$
CREATE PROCEDURE populateChangeStatus ()
BEGIN
      DECLARE ric_id INTEGER DEFAULT 0;
	  DECLARE c_ric_id INTEGER DEFAULT 0;
      DECLARE rias_prat INTEGER DEFAULT 0;
      DECLARE finished INTEGER DEFAULT 0;
      DECLARE idProc integer DEFAULT 0;
      
	  DEClARE curs 
            CURSOR FOR 
                  SELECT ID_PROCESSO FROM processi;

      -- declare NOT FOUND handler
      DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

      OPEN curs;
	  SELECT ID_PROCESSO FROM processi;
      
      looper: LOOP
            FETCH curs INTO idProc;
            IF finished = 1 THEN 
                  LEAVE looper;
            END IF;
            INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (idProc,'CNGSTS','Cambio stato', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
      END LOOP looper;
      CLOSE curs;
END$$
DELIMITER ;

-- temporizzazione preimpostata per alcuni eventi

update opencross.processi_eventi pre set giorni_default = 30
  where pre.id_processo in (
 select p.id_processo from opencross.procedimenti_enti pe
join opencross.processi p on pe.id_processo = p.id_processo
 where id_ente = 2001
 ) and pre.des_evento in ('(amm) Invio Comunicazione al richiedente',
'(amm) Invia Comunicazione ad Ente/Uffici Interni',
'(amm) Invia Avvenuta conformazione',
'(amm) Invia Richiesta conformazione e Divieto prosecuzione lavori');

--
DELIMITER $$
CREATE PROCEDURE populateAutomateReassignPaperwork ()
BEGIN
      DECLARE finished INTEGER DEFAULT 0;
      DECLARE idProc integer DEFAULT 0;
      
	  DEClARE curs 
            CURSOR FOR 
                  SELECT ID_PROCESSO FROM processi;

      -- declare NOT FOUND handler
      DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

      OPEN curs;
	  SELECT ID_PROCESSO FROM processi;
      
      looper: LOOP
            FETCH curs INTO idProc;
            IF finished = 1 THEN 
                  LEAVE looper;
            END IF;
            INSERT INTO processi_eventi (`id_processo`,`cod_evento`,`des_evento`,`stato_post`,`id_tipo_mittente`,`id_tipo_destinatario`,`script_scadenza_evento`,`verso`,`flg_portale`,`flg_mail`,`flg_all_mail`,`flg_protocollazione`,`flg_ricevuta`,`flg_destinatari`,`flg_firmato`,`flg_apri_sottopratica`,`flg_destinatari_solo_enti`,`flg_visualizza_procedimenti`,`id_procedimento_riferimento`,`id_script_evento`,`id_script_protocollo`,`max_destinatari`,`oggetto_email`,`corpo_email`,`funzione_applicativa`,`flg_automatico`,`forza_chiusura_scadenze`,`flg_integrazione`) VALUES (idProc,'RIASPRATAUTO','Riassegnazione automatica', 8, NULL, NULL ,NULL, NULL,'N','N','N','N','N','N','N','N','N','N',NULL,NULL,NULL,NULL,NULL,NULL,'comunicazione','N','N','N');
      END LOOP looper;
      CLOSE curs;
END$$
DELIMITER ;

-- 24-03-14

ALTER TABLE processi_eventi 
ADD COLUMN nascondi_tab_documenti varchar(1) default 'N';


ALTER TABLE processi_eventi 
ADD COLUMN nascondi_tab_template varchar(1) default 'N';

-- AREA VASTA OLD

insert into lk_stato_pratica(descrizione, codice, grp_stato_pratica) values ('Archiviata','AR','C');

-- new lk_stato_pratica set from autoincrement to 11
update processi_eventi 
set stato_post = 11
where cod_evento in(
'ARCHIV',
'CHIU_PROC_AMM',
'CHIUS_PRAT'
);

---------
-- SUAP
---------

ALTER TABLE pratica_procedimenti ADD COLUMN id_endoprocedimento_suap int DEFAULT -1;

ALTER TABLE pratica_procedimenti DROP PRIMARY KEY, ADD PRIMARY KEY(id_pratica, id_procedimento, id_ente, id_endoprocedimento_suap);

--
-- riscrittura tabella
drop TABLE lk_associazione_procedimento_ente_utente_suap;

CREATE TABLE lk_associazione_procedimento_ente_utente_suap (
	id_ente int,
	cod_endoprocedimento_suap varchar(200),
	id_utente int, 
	id_responsabile_procedimento int,
    priorita_utente int,
    priorita_responsabile int,
    PRIMARY KEY (id_ente,cod_endoprocedimento_suap),
    foreign key (id_utente) REFERENCES  UTENTE(ID_UTENTE),
    foreign key (id_responsabile_procedimento) REFERENCES UTENTE(ID_UTENTE),
    foreign key (id_ente) REFERENCES enti(id_ente)
);

----

insert into plugin_configuration(name, value) values('semaphore.automatic.registering', 'false');

-------
- R9 (CR)
-------
alter table allegati add column dimensione bigint default null;

create table pratica_procuratore(
	id int auto_increment primary key,
	id_pratica int not null,
	id_anagrafica int not null,
	data_inizio_incarico datetime NOT NULL,
	data_fine_incarico datetime,
	foreign key (id_pratica) references Pratica(id_pratica),
	foreign key (id_anagrafica) references anagrafica(id_anagrafica)
);


create table tmp_pratica_owner(
	id_pratica int primary key,
	technician_tax_code varchar(30)
);


---------
-- AVE-72
---------
insert into permessi(cod_permesso, descrizione) values('CNS','Consultazione')

---------
-- AVE-151
---------
insert into configuration (name, value) values ("attachments.placeholder", "ATTACHMENT_PLACEHOLDER");
insert into configuration (name, value) values ("tmp.folder", "/usr/local/apache-tomcat-6.0.41/cross_files/tmp");
update configuration set value = "/usr/local/apache-tomcat-6.0.41/cross_files/ATTACHMENT_PLACEHOLDER/documents" where name = "documenti.fs.folder";
update configuration set value = "/usr/local/apache-tomcat-6.0.41/cross_files/ATTACHMENT_PLACEHOLDER/email" where name = "mail.folder";
update configuration set value = "/usr/local/apache-tomcat-6.0.41/cross_files/ATTACHMENT_PLACEHOLDER/attachments" where name = "attachment.tmp.folder";

---------
-- AVE-163
---------
insert into configuration (name, value) values ('building.export.type', 'SCIA'),('building.export.type', 'AGIB'),('building.export.type', 'PDC');
