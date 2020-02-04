
drop table `organi_collegiali_template`;
drop TABLE `pratica_organi_collegiali`;
drop TABLE `organi_collegiali`;

CREATE TABLE `organi_collegiali` (
  `id_organi_collegiali` int(11) NOT NULL AUTO_INCREMENT,
  `id_ente` int(11) NOT NULL,
  `des_organo_collegiale` varchar(255) NOT NULL,
  PRIMARY KEY (`id_organi_collegiali`),
  UNIQUE KEY `organi_collegiali_idx` (`id_ente`),
  KEY `organi_collegiali_fk1_idx` (`id_ente`),
  CONSTRAINT `organi_collegiali_fk1` FOREIGN KEY (`id_ente`) REFERENCES `enti` (`id_ente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `pratica_organi_collegiali` (
  `id_pratica_organi_collegiali` int(11) NOT NULL AUTO_INCREMENT,
  `id_pratica` int(11) NOT NULL,
  `id_organi_collegiali` int(11) NOT NULL,
  `data_richiesta` datetime NOT NULL,
  `id_stato_pratica_organi_collegiali` int(11) NOT NULL,
  PRIMARY KEY (`id_pratica_organi_collegiali`),
  KEY `pratica_organi_coll_FK1_idx` (`id_pratica`),
  KEY `pratica_organi_coll_FK2_idx` (`id_organi_collegiali`),
  KEY `pratica_organi_coll_FK3` (`id_stato_pratica_organi_collegiali`),
  CONSTRAINT `pratica_organi_coll_FK1` FOREIGN KEY (`id_pratica`) REFERENCES `pratica` (`id_pratica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pratica_organi_coll_FK2` FOREIGN KEY (`id_organi_collegiali`) REFERENCES `organi_collegiali` (`id_organi_collegiali`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pratica_organi_coll_FK3` FOREIGN KEY (`id_stato_pratica_organi_collegiali`) REFERENCES `lk_stati_pratica_organi_collegiali` (`id_stato_pratica_organi_collegiali`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `organi_collegiali_template` (
  `id_organi_collegiali_template` int(11) NOT NULL AUTO_INCREMENT,
  `id_organi_collegiali` int(11) NOT NULL,
  `tipologia_template` varchar(45) NOT NULL,
  `id_template` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_organi_collegiali_template`),
  UNIQUE KEY `organi_collegiali_template_idx` (`id_organi_collegiali`,`tipologia_template`),
  KEY `organi_collegiali_fk_idx` (`id_organi_collegiali`),
  KEY `template_fk_idx` (`id_template`),
  CONSTRAINT `organi_collegiali_templ_fk` FOREIGN KEY (`id_organi_collegiali`) REFERENCES `organi_collegiali` (`id_organi_collegiali`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `template_fk` FOREIGN KEY (`id_template`) REFERENCES `templates` (`id_template`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


