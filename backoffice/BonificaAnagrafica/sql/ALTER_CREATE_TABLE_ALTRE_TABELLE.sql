CREATE DATABASE /*!32312 IF NOT EXISTS*/`civilia_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `civilia_db`;

CREATE TABLE civilia_db.`anagrafica_cross_delete` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pratica_id` int(11) NOT NULL,
  `anagrafica_id_cross` int(11) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=256563 DEFAULT CHARSET=latin1


CREATE TABLE civilia_db.`anagrafica_cross_insert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pratica_id` int(11) NOT NULL,
  `anagrafica_id_civ` int(11) NOT NULL,
  `anagrafica_id_cross` int(11) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=333768 DEFAULT CHARSET=latin1

CREATE TABLE civilia_db.`cross_pra_eventi_nuova_anag` (
  `id_pratica_evento` int(11) NOT NULL DEFAULT '0',
  `id_pratica` int(11) NOT NULL,
  `id_evento` int(11) NOT NULL,
  PRIMARY KEY (`id_pratica_evento`,`id_evento`),
  KEY `IND_ID_PRATICA` (`id_pratica`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1


ALTER TABLE `civilia_db`.`pratica`   
  ADD COLUMN `thread_id` INT(11) DEFAULT 0 NULL AFTER `fasi_attive`, 
  ADD  KEY `idx_thread_id` (`thread_id`);


CREATE TABLE civilia_db.`pratica_anagrafica_update` (
  `id_pratica` int(11) NOT NULL,
  `id_anagrafica` int(11) NOT NULL,
  `id_etichetta` int(11) NOT NULL,
  `id_ruolo` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_pratica`,`id_anagrafica`,`id_etichetta`)
)