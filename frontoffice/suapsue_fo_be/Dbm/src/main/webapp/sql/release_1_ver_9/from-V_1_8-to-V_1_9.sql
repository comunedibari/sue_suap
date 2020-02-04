alter table oneri_campi add column accetta_valore_zero varchar(1) not null default 'S';

insert into html_testi(cod_testo, cod_lang, des_testo) values('OneriCampi_field_form_accetta_valore_zero', 'it', 'Accetta valore zero');
