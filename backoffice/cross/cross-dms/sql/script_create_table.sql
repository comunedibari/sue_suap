CREATE TABLE `opencross`.`allineamento_dms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identificativoPratica` varchar(50) NOT NULL,
  `offset` int(11) NOT NULL,
  `flag_elaborato` char(1) NOT NULL DEFAULT 'N',
  `flag_aggiornato` char(1) NOT NULL DEFAULT 'N',
  `data_elaborazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_aggiornamento` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`,`identificativoPratica`)
)