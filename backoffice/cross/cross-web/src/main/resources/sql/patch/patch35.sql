CREATE TABLE `pratica_pratica` (
  `id_pratica_a` INT NOT NULL,
  `id_pratica_b` INT NOT NULL,
  PRIMARY KEY (`id_pratica_a`, `id_pratica_b`));

insert  into `lk_stati_mail`(`descrizione`,`codice`) values ('Email da protocollare','P');