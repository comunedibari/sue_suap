CREATE TABLE `processi_eventi_anagrafica` (
  `id_evento` int(11) NOT NULL,
  `id_anagrafica` int(11) NOT NULL,
  `tipologia` varchar(45) NOT NULL,
  PRIMARY KEY (`id_evento`,`id_anagrafica`,`tipologia`),
  KEY `processi_eventi_anagrafica_FK2_idx` (`id_anagrafica`),
  CONSTRAINT `processi_eventi_anagrafia_FK1` FOREIGN KEY (`id_evento`) REFERENCES `processi_eventi` (`id_evento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `processi_eventi_anagrafica_FK2` FOREIGN KEY (`id_anagrafica`) REFERENCES `anagrafica` (`id_anagrafica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `processi_eventi_enti` (
  `id_evento` int(11) NOT NULL,
  `id_ente` int(11) NOT NULL,
  `tipologia` varchar(45) NOT NULL,
  PRIMARY KEY (`id_evento`,`id_ente`,`tipologia`),
  KEY `processi_eventi_enti_FK2_idx` (`id_ente`),
  CONSTRAINT `processi_eventi_enti_FK1` FOREIGN KEY (`id_evento`) REFERENCES `processi_eventi` (`id_evento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `processi_eventi_enti_FK2` FOREIGN KEY (`id_ente`) REFERENCES `enti` (`id_ente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `configuration` (`name`, `value`, `note`) VALUES ('cross.eventi.getione.anagrafiche', 'TRUE', 'Abilita la gestione dei destinatari e anagrafiche di inoltro sull\'evento');