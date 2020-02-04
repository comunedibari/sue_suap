ALTER TABLE `href_campi` CHANGE COLUMN `contatore` `contatore` INT(11) NOT NULL  ;

ALTER TABLE `href_campi` ENGINE = InnoDB ;

ALTER TABLE `gerarchia_operazioni` ADD INDEX `gerarchia_operazioni_idx3` (`tip_aggregazione` ASC, `cod_ramo_prec` ASC) ;

insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('DuplicazioneInterventi_grid_esiste','it','Esiste');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('DuplicazioneInterventi_grid_icon','it','Stato');
insert into `html_testi` (`cod_testo`, `cod_lang`, `des_testo`) values('DuplicazioneInterventi_grid_new_code','it','Nuovo codice');