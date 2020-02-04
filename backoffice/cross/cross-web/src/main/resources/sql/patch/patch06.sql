-- 14/01/14 : Aggiunta tabella plugin_configuration
CREATE TABLE `plugin_configuration` (
  `id` int(11) NOT NULL auto_increment,
  `id_ente` int(11) default NULL,
  `id_comune` int(11) default NULL,
  `name` varchar(255) default NULL,
  `value` varchar(255) default NULL,
  `note` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `plugin_configuration_fk1` (`id_ente`),
  KEY `plugin_configuration_fk2` (`id_comune`),
  CONSTRAINT `plugin_configuration_fk1` FOREIGN KEY (`id_ente`) REFERENCES `enti` (`id_ente`),
  CONSTRAINT `plugin_configuration_fk2` FOREIGN KEY (`id_comune`) REFERENCES `lk_comuni` (`id_comune`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

ALTER TABLE `plugin_configuration` 
CHANGE COLUMN `value` `value` LONGTEXT NULL DEFAULT NULL ;
