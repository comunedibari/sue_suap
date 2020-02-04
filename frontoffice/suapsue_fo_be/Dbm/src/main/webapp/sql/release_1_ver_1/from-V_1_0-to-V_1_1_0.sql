alter table sportelli change id_mail_server id_mail_server varchar(250) null;
alter table sportelli change id_protocollo id_protocollo varchar(250) null;
alter table sportelli change id_backoffice id_backoffice varchar(250) null;

alter table sportelli add column template_oggetto_mail_suap TEXT NOT NULL;
alter table sportelli add column template_oggetto_ricevuta TEXT NOT NULL;
alter table sportelli add column template_corpo_ricevuta LONGTEXT NOT NULL;
alter table sportelli add column template_nome_file_zip VARCHAR(500) NOT NULL;
alter table sportelli add column send_zip_file varchar(8) NOT NULL DEFAULT 'true';
alter table sportelli add column send_single_files varchar(8) NOT NULL DEFAULT 'true';
alter table sportelli add column send_xml varchar(8) NOT NULL DEFAULT 'true';
alter table sportelli add column send_signature varchar(8) NOT NULL DEFAULT 'false';
alter table sportelli add column send_ricevuta_dopo_protocollazione varchar(8) NOT NULL DEFAULT 'true';
alter table sportelli add column send_ricevuta_dopo_invio_bo varchar(8) NOT NULL DEFAULT 'true';

update sportelli set template_oggetto_mail_suap = 'U1VBUDogJGlkZW50aWZpY2F0aXZvX3N1YXBfbWl0dGVudGUgLSAkY29kaWNlX2Zpc2NhbGVfcmljaGllZGVudGU=';
update sportelli set template_oggetto_ricevuta = 'U1VBUDogJGlkZW50aWZpY2F0aXZvX3N1YXBfbWl0dGVudGUgLSAkY29kaWNlX2Zpc2NhbGVfaW1wcmVzYSAtICRkZW5vbWluYXppb25lX2ltcHJlc2E=';
update sportelli set template_corpo_ricevuta = 'UHJhdGljYTogJGNvZGljZV9wcmF0aWNhIA0KUHJvdG9jb2xsbzogJGlkZW50aWZpY2F0aXZvX3Byb3RvY29sbG8=';
update sportelli set template_nome_file_zip = 'JGNvZGljZV9maXNjYWxlX2ltcHJlc2FcLSRkYXRhX29yYVwuU1VBUC56aXA=';

/* Modifiche spostamento parametri PEC e nuova gestione Connects >*/


/*< Modifiche integrazione PayER */

alter table sportelli add ae_codice_utente varchar(5) null;
alter table sportelli add ae_codice_ente varchar(5) null;
alter table sportelli add ae_tipo_ufficio varchar(1) null;
alter table sportelli add ae_codice_ufficio varchar(6) null;
alter table sportelli add ae_tipologia_servizio varchar(3) null;


/* Modifiche integrazione PayER >*/

/*< Modifiche spostamento parametri PEC e nuova gestione Connects */

insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_template_oggetto_ricevuta', 'it', 'Template oggetto ricevuta');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_template_corpo_ricevuta', 'it', 'Template corpo ricevuta');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_template_nome_file_zip', 'it', 'Template nome file zip');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_id_mail_server_title', 'it', 'Invio mail PEC');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_zip_file', 'it', 'Invia file ZIP');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_single_files', 'it', 'Invia files singoli');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_xml', 'it', 'Invia XML');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_signature', 'it', 'Invia segnatura cittadino');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ricevuta_utente_title', 'it', 'Ricevuta all\'utente');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_ricevuta_dopo_protocollazione', 'it', 'Invia ricevuta dopo protocollazione');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_send_ricevuta_dopo_invio_bo', 'it', 'Invia ricevuta dopo invio al b.o.');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_template_oggetto_mail_suap', 'it', 'Template oggetto mail SUAP');

update html_testi set des_testo = 'Indirizzo PEC' where cod_testo = 'Sportelli_field_form_id_mail_server';
update html_testi set des_testo = 'Indirizzo WS protocollazione' where cod_testo = 'Sportelli_field_form_id_protocollo';
update html_testi set des_testo = 'Indirizzo b.o.' where cod_testo = 'Sportelli_field_form_id_backoffice';

/* Modifiche spostamento parametri PEC e nuova gestione Connects >*/


/*< Modifiche integrazione PayER */

insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_title', 'it' , 'Identificativo servizio pagamento');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_codice_utente', 'it' , 'Codice utente');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_codice_ente', 'it' , 'Codice ente');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_tipo_ufficio', 'it' , 'Tipo ufficio');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_codice_ufficio', 'it' , 'Codice ufficio');
insert into html_testi(cod_testo, cod_lang, des_testo)
	values('Sportelli_field_form_ae_tipologia_servizio', 'it' , 'Tipologia servizio');

/* Modifiche integrazione PayER >*/


ALTER TABLE `href_campi` ADD COLUMN `pattern` VARCHAR(255) DEFAULT NULL ;
ALTER TABLE `href_campi_testi` ADD COLUMN `err_msg` VARCHAR(255)  DEFAULT NULL ;
ALTER TABLE `anagrafica` ADD COLUMN `pattern` VARCHAR(255) DEFAULT NULL ;
ALTER TABLE `anagrafica_testi` ADD COLUMN `err_msg` VARCHAR(255)  DEFAULT NULL ;

INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Href_field_form_combo_tp_controllo_X', 'it', 'Reg.Expression');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Href_field_form_pattern', 'it', 'Regular Expression');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Href_field_form_err_msg', 'it', 'Messaggio di errore');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Anagrafica_field_form_combo_tp_controllo_X', 'it', 'Reg.Expression');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Anagrafica_field_form_pattern', 'it', 'Regular Expression');
INSERT INTO `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) VALUES ('Anagrafica_field_form_err_msg', 'it', 'Messaggio di errore');

INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('updateTCR', 'TRUE', 'TRUE aggiornamento automatico TCR, FALSE Aggiornamento manuale TCR');