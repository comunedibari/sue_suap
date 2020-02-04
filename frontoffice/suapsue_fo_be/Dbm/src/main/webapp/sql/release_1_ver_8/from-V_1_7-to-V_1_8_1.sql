insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('DuplicazioneInterventi_field_form_interventi_collegati','it','Interventi collegati');


/*
TPGDE - Persona giuridica - Denominazione
TPGRLNO - Persona giuridica - Rappresentante legale - Nome
TPGRLCO - Persona giuridica - Rappresentante legale - Cognome
TPGRLCF - Persona giuridica - Rappresentante legale - CF

TPFNO - Persona fisica - Nome
TPFCO - Persona fisica - Cognome

TCF - Codice fiscale
TPI - Partita IVA
TIN - Indirizzo
*/

create table tipi_campo_titolare(
	tipo varchar(10) not null,
	descrizione varchar(400) null,
	primary key(tipo)
);

insert into tipi_campo_titolare(tipo, descrizione) values('', 'No'), ('TPGDE', 'Persona giuridica - Denominazione'), 
('TPGRLNO', 'Persona giuridica - Rappresentante legale - Nome'), 
('TPGRLCO', 'Persona giuridica - Rappresentante legale - Cognome'), 
('TPGRLCF', 'Persona giuridica - Rappresentante legale - CF'), 
('TPFNO', 'Persona fisica - Nome'), 
('TPFCO', 'Persona fisica - Cognome'), 
('TCF', 'Codice fiscale'), 
('TPI', 'Partita IVA'), 
('TIN', 'Indrizzo'),
('TMAIL', 'Indirizzo di posta elettronica');

alter table anagrafica add column campo_titolare enum('TPGDE', 'TPGRLNO', 'TPGRLCO', 'TPGRLCF', 'TPFNO', 'TPFCO', 'TCF', 'TPI', 'TIN', 'TMAIL') null;

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Anagrafica_field_form_campo_titolare','it','Campo titolare');

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Anagrafica_field_form_campo_titolare_select','it','Nessuno');


update anagrafica set campo_titolare = 'TPFNO' where campo_xml_mod = 'ANAG_RAPPPRIV_NOMERAP';
update anagrafica set campo_titolare = 'TPFCO' where campo_xml_mod = 'ANAG_RAPPPRIV_COGNOMERAP';
update anagrafica set campo_titolare = 'TCF' where campo_xml_mod = 'ANAG_RAPPPRIV_CODFISCRAP';
update anagrafica set campo_titolare = 'TIN' where campo_xml_mod = 'ANAG_RAPPPRIV_VIARAP';

update anagrafica set campo_titolare = 'TPGDE' where campo_xml_mod = 'ANAG_RAPPENTE_DENOM';
update anagrafica set campo_titolare = 'TIN' where campo_xml_mod = 'ANAG_RAPPENTE_VIASEDE';
update anagrafica set campo_titolare = 'TMAIL' where campo_xml_mod = 'ANAG_RAPPENTE_EMAILSEDE';
update anagrafica set campo_titolare = 'TCF' where campo_xml_mod = 'ANAG_RAPPENTE_CODFISC';
update anagrafica set campo_titolare = 'TPI' where campo_xml_mod = 'ANAG_RAPPENTE_PIVA';

update anagrafica set campo_titolare = 'TPFNO' where campo_xml_mod = 'ANAG_PFISICA_NOME';
update anagrafica set campo_titolare = 'TPFCO' where campo_xml_mod = 'ANAG_PFISICA_COGNOME';
update anagrafica set campo_titolare = 'TCF' where campo_xml_mod = 'ANAG_PFISICA_CODFISC';
update anagrafica set campo_titolare = 'TIN' where campo_xml_mod = 'ANAG_PFISICA_VIARES';

update anagrafica set campo_titolare = 'TPGDE' where campo_xml_mod = 'ANAG_PGIURIDICA_DENOM';
update anagrafica set campo_titolare = 'TIN' where campo_xml_mod = 'ANAG_PGIURIDICA_VIASEDE';
update anagrafica set campo_titolare = 'TMAIL' where campo_xml_mod = 'ANAG_PGIURIDICA_EMAILSEDE';
update anagrafica set campo_titolare = 'TCF' where campo_xml_mod = 'ANAG_PGIURIDICA_CODFISC';
update anagrafica set campo_titolare = 'TPI' where campo_xml_mod = 'ANAG_PGIURIDICA_PIVA';

update anagrafica set campo_titolare = 'TPGRLNO' where campo_xml_mod = 'ANAG_PGIURIDICA_NOMELEG';
update anagrafica set campo_titolare = 'TPGRLCO' where campo_xml_mod = 'ANAG_PGIURIDICA_COGNOMELEG';
update anagrafica set campo_titolare = 'TPGRLCF' where campo_xml_mod = 'ANAG_PGIURIDICA_CODFISCLEG';





commit;