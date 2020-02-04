CREATE TABLE `lk_tipo_oggetto` (
  `id_tipo_oggetto` int(11) NOT NULL AUTO_INCREMENT,
  `des_tipo_oggetto` varchar(45) NOT NULL,
  PRIMARY KEY (`id_tipo_oggetto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `def_dati_estesi` (
  `id_dati_estesi` int(11) NOT NULL AUTO_INCREMENT,
  `id_tipo_oggetto` int(11) NOT NULL,
  `id_istanza` varchar(45) NOT NULL,
  `cod_value` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_dati_estesi`),
  UNIQUE KEY `def_dati_estesi_idx1` (`id_tipo_oggetto`,`id_istanza`,`cod_value`),
  KEY `def_dati_estesi_fk1_idx` (`id_tipo_oggetto`),
  CONSTRAINT `def_dati_estesi_fk1` FOREIGN KEY (`id_tipo_oggetto`) REFERENCES `lk_tipo_oggetto` (`id_tipo_oggetto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

