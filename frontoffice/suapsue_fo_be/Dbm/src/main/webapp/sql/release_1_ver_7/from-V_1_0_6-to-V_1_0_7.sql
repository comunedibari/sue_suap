alter table sportelli add column visualizza_stampa_modello_in_bianco char(1) not null default 'S', 
add column visualizza_versione_pdf char(1) not null default 'S', 
add column firma_on_line char(1) not null default 'S', 
add column firma_off_line char(1) not null default 'N';

insert into html_testi(cod_testo, cod_lang, des_testo) 
values("Sportelli_field_form_visualizza_stampa_modello_in_bianco", "it", "Visualizza stampa modello in bianco");
insert into html_testi(cod_testo, cod_lang, des_testo) 
values("Sportelli_field_form_visualizza_versione_pdf", "it", "Visualizza versione PDF");
insert into html_testi(cod_testo, cod_lang, des_testo) 
values("Sportelli_field_form_firma_on_line", "it", "Attiva firma on line");
insert into html_testi(cod_testo, cod_lang, des_testo) 
values("Sportelli_field_form_firma_off_line", "it", "Attiva firma off line");
insert into html_testi(cod_testo, cod_lang, des_testo) 
values("Sportelli_field_form_pagina_firma_title", "it", "Impostazioni pagina di firma");

alter table href add column tip_aggregazione varchar(4) null default null;
alter table interventi add column tip_aggregazione varchar(4) null default null;
alter table normative add column tip_aggregazione varchar(4) null default null;
alter table procedimenti add column tip_aggregazione varchar(4) null default null;
alter table documenti add column tip_aggregazione varchar(4) null default null;
alter table oneri add column tip_aggregazione varchar(4) null default null;
alter table oneri_campi add column tip_aggregazione varchar(4) null default null;
alter table oneri_documenti add column tip_aggregazione varchar(4) null default null;
alter table oneri_formula add column tip_aggregazione varchar(4) null default null;
alter table oneri_gerarchia add column tip_aggregazione varchar(4) null default null;
alter table testo_condizioni add column tip_aggregazione varchar(4) null default null;
alter table utenti add column tip_aggregazione varchar(4) null default null;

insert into html_testi (cod_testo, cod_lang, des_testo) values ('Utenti_header_grid_aggregazione', 'it', 'Aggregazione');

insert into html_testi (cod_testo, cod_lang, des_testo) values ('Utenti_field_form_des_aggregazione', 'it', 'Des. Aggregazione');

insert into html_testi (cod_testo, cod_lang, des_testo) values ('Utenti_field_form_tip_aggregazione', 'it', 'Cod. Aggregazione');


alter table configuration change value value varchar(800) not null;

insert into documenti(cod_doc, flg_dic, href, tip_aggregazione) values('SYSCPVERS', 'N', null, null);
insert into documenti_testi(cod_doc, cod_lang, tit_doc, des_doc) values('SYSCPVERS', 'it', 'copia del versamento oneri anticipati', 'copia del versamento oneri anticipati');

commit;



