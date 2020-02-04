INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (1,'P.C.T. (Particella catastale terreni)','F');
INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (2,'P.C.E. (Particella catastale edificiale)','E');
INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (3,'P.C.N. (Particella catastale nuova)','F');
INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (4,'CAT.T. (Catastale Terreni)','F');
INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (5,'CAT.E. (Catastale Edificiale)','E');
INSERT IGNORE INTO `lk_tipo_particella` (`id_tipo_particella`,`descrizione`,`cod_tipo_particella`) VALUES (6,'Scrittura Teresiana','F');

update lk_tipo_particella set descrizione = 'P.C.T. (Particella catastale terreni)', cod_tipo_particella= 'F' where id_tipo_particella = 1;
update lk_tipo_particella set descrizione = 'P.C.E. (Particella catastale edificiale)', cod_tipo_particella= 'E' where id_tipo_particella = 2;
update lk_tipo_particella set descrizione = 'P.C.N. (Particella catastale nuova)', cod_tipo_particella= 'F' where id_tipo_particella = 3;
update lk_tipo_particella set descrizione = 'CAT.T. (Catastale Terreni)', cod_tipo_particella= 'F' where id_tipo_particella = 4;
update lk_tipo_particella set descrizione = 'CAT.E. (Catastale Edificiale)', cod_tipo_particella= 'E' where id_tipo_particella = 5;
update lk_tipo_particella set descrizione = 'Scrittura Teresiana', cod_tipo_particella= 'F' where id_tipo_particella = 6;

delete from lk_tipo_unita where id_tipo_unita > 2;