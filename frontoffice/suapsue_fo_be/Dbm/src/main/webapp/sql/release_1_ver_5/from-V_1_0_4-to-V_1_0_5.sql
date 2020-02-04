alter table sportelli add column allegati_dimensione_max int default -1 comment 'Dimensione massima degli allegati';
alter table sportelli add column allegati_dimensione_max_um enum('B', 'KB', 'MB') not null default 'MB' comment 'Unità di misura della dimensione massima degli allegati';

insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_dim_max_all_um', 'it', 'U.M.');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_dim_max_all_um_select', 'it', 'Scegliere ...');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_combo_dim_max_all_um_B_label', 'it', 'B (Bytes)');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_combo_dim_max_all_um_KB_label', 'it', 'KB (KiloBytes)');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_combo_dim_max_all_um_MB_label', 'it', 'MB (MegaBytes)');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_dim_max_all', 'it', 'Valore');
insert into html_testi(cod_testo, cod_lang, des_testo) values('Sportelli_field_form_dim_max_all_settings', 'it', 'Dimensione massima allegati');

commit;