insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Generic_field_form_tip_aggregazione','it','Codice aggregazione');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Generic_field_form_des_aggregazione','it','Descrizione aggregazione');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Generic_header_grid_aggregazione','it','Aggregazione');

INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('territorialitaAllargata', 'TRUE', 'TRUE restrizione su aggiornamento, FALSE restrizione anche su visualizzazione');

alter table sportelli add href_oggetto varchar(50) null;

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Sportelli_field_form_href','it','Href oggetto');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Sportelli_field_form_tit_href','it','Href titolo');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('Sportelli_field_form_impostazioni_oggetto_title','it','Impostazioni oggetto pratica');

commit;