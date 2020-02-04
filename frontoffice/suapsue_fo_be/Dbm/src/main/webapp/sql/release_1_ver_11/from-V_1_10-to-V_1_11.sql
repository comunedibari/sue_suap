alter table interventi add column note_pubblicazione varchar(500) null after flg_pubblicazione;
alter table interventi add column note_generali_pubblicazione varchar(500) null after note_pubblicazione;
insert into html_testi(cod_testo, cod_lang, des_testo) values('Pubblicazione_header_grid_note_pubblicazione', 'it', 'Note pubblicazione');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Pubblicazione_header_grid_pubblica', 'it', 'Pubblicazione?');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Pubblicazione_field_form_note_generali_pubblicazione', 'it', 'Note generali pubblicazione');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Pubblicazione_field_form_note_generali_pubblicazione_conferma_reset_titolo', 'it', 'Conferma reimpostazione note generali di pubblicazione');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Pubblicazione_field_form_note_generali_pubblicazione_conferma_reset_msg', 'it', 'Si desidera azzerare il valore delle note generali di pubblicazione?');

alter table oneri add column cumulabile varchar(1) not null default 'N'  after tip_aggregazione;
insert into html_testi(cod_testo, cod_lang, des_testo) values('Oneri_field_form_cumulabile', 'it', 'Cumulabile?');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Oneri_header_grid_cumulabile', 'it', 'Cumulabile?');
