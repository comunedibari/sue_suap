/*
SQLyog Community v12.4.3 (32 bit)
MySQL - 5.6.40 : Database - opencross_prod
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`opencross` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `opencross`;

/*Table structure for table `ruoli_etichetta_civilia_cross` */

DROP TABLE IF EXISTS `ruoli_etichetta_civilia_cross`;

CREATE TABLE `ruoli_etichetta_civilia_cross` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `ruolo_etichetta_civilia_cross` VARCHAR(255) NOT NULL,
  `id_ruolo_cross` INT(11) NOT NULL,
  `ruolo_cross` varchar(255) NOT NULL,
  `data_inserimento` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_ruolo_cross` (`ruolo_cross`),
  KEY `idx_ruolo_etichetta` (`ruolo_etichetta_civilia_cross`),
  KEY `idx__id_ruolo_cross` (`id_ruolo_cross`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

/*Data for the table `ruoli_etichetta_civilia_cross` */

insert  into `ruoli_etichetta_civilia_cross`(`id`,`ruolo_etichetta_civilia_cross`,`id_ruolo_cross`,`ruolo_cross`,`data_inserimento`) values 
(1,'Agente Rappresentante',1,'Richiedente','2023-06-08 10:29:10'),
(2,'Amministratore',2,'Beneficiario','2023-06-08 10:29:10'),
(3,'Amministratore Condominiale',2,'Beneficiario','2023-06-08 10:29:10'),
(4,'Amministratore Unico',2,'Beneficiario','2023-06-08 10:29:10'),
(5,'Asseverante',3,'Tecnico professionista','2023-06-08 10:29:10'),
(6,'Asseverante/Direttore dei Lavori',3,'Tecnico professionista','2023-06-08 10:29:10'),
(7,'Calcolatore delle Strutture',3,'Tecnico professionista','2023-06-08 10:29:10'),
(8,'Collaudatore',3,'Tecnico professionista','2023-06-08 10:29:10'),
(9,'Commissario Straordinario',2,'Beneficiario','2023-06-08 10:29:10'),
(10,'Comodatario',2,'Beneficiario','2023-09-06 10:16:47'),
(11,'Comproprietario',2,'Beneficiario','2023-06-08 10:29:10'),
(12,'Comproprietario Area',2,'Beneficiario','2023-06-08 10:29:10'),
(13,'Comproprietario area in %',2,'Beneficiario','2023-06-08 10:29:10'),
(14,'Comproprietario Fabbricato',2,'Beneficiario','2023-06-08 10:29:10'),
(15,'Concedente',2,'Beneficiario','2023-06-08 10:29:10'),
(16,'Concessionario',2,'Beneficiario','2023-06-08 10:29:10'),
(17,'Conduttore',2,'Beneficiario','2023-06-08 10:29:10'),
(18,'Coordinatore della Sicurezza in fase di escuzione',3,'Tecnico professionista','2023-06-08 10:29:10'),
(19,'Curatore fallimentare',2,'Beneficiario','2023-06-08 10:29:10'),
(20,'Custode Giudiziario',2,'Beneficiario','2023-06-08 10:29:10'),
(21,'Delegato dalla proprietà',1,'Richiedente','2023-06-08 10:29:10'),
(22,'Dichiarante/Denunciante',2,'Beneficiario','2023-06-08 10:29:10'),
(23,'Direttore',3,'Tecnico professionista','2023-06-08 10:29:10'),
(24,'Direttore dei lavori',3,'Tecnico professionista','2023-06-08 10:29:10'),
(25,'Direttore Tecnico',1,'Richiedente','2023-06-08 10:29:10'),
(26,'Esecutore Testamentario',2,'Beneficiario','2023-06-08 10:29:10'),
(27,'Ex Amministratore Condominiale',2,'Beneficiario','2023-06-08 10:29:10'),
(28,'Ex Asseverante/Direttore Lavori',3,'Tecnico professionista','2023-06-08 10:29:10'),
(29,'Ex Collaudatore',3,'Tecnico professionista','2023-06-08 10:29:10'),
(30,'Ex Direttore Lavori',3,'Tecnico professionista','2023-06-08 10:29:10'),
(31,'Ex Impresa',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(32,'Ex Legale Rappresentante',2,'Beneficiario','2023-06-08 10:29:10'),
(33,'Ex Progettista',3,'Tecnico professionista','2023-06-08 10:29:10'),
(34,'Ex Proprietario',2,'Beneficiario','2023-06-08 10:29:10'),
(35,'Ex richiedente',1,'Richiedente','2023-06-08 10:29:10'),
(36,'Geologo',3,'Tecnico professionista','2023-06-08 10:29:10'),
(37,'Gestore',1,'Richiedente','2023-06-08 10:29:10'),
(38,'Impresa',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(39,'Impresa Affidataria Opere a Fluido',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(40,'Impresa Affidataria Opere Edili',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(41,'Impresa Affidataria Opere Elettriche',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(42,'Impresa esecutrice dei lavori',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(43,'Istruttore',5,'Istruttore','2023-06-08 10:29:10'),
(44,'Legale Rappresentante',2,'Beneficiario','2023-06-08 10:29:10'),
(45,'Liquidatore',2,'Beneficiario','2023-06-08 10:29:10'),
(46,'Locatario',2,'Beneficiario','2023-06-08 10:29:10'),
(47,'Nuovo Richiedente/Proprietario',2,'Beneficiario','2023-06-08 10:29:10'),
(48,'Presidente',2,'Beneficiario','2023-06-08 10:29:10'),
(49,'Procuratore',1,'Richiedente','2023-06-08 10:29:10'),
(50,'Progettista',3,'Tecnico professionista','2023-06-08 10:29:10'),
(51,'Promissario Acquirente',1,'Richiedente','2023-06-08 10:29:10'),
(52,'Proponente (001 Old)',2,'Beneficiario','2023-06-08 10:29:10'),
(53,'Proprietario',2,'Beneficiario','2023-06-08 10:29:10'),
(54,'Proprietario Fabbricato',2,'Beneficiario','2023-06-08 10:29:10'),
(55,'Responsabile procedimento',7,'Responsabile Procedimento','2023-06-08 10:29:10'),
(56,'Richiedente',2,'Beneficiario','2023-06-08 10:29:10'),
(57,'Richiedente/Condominio',2,'Beneficiario','2023-06-08 10:29:10'),
(58,'Richiedente/Denunciante',2,'Beneficiario','2023-06-08 10:29:10'),
(59,'Soggetto Esercente',1,'Richiedente','2023-06-08 10:29:10'),
(60,'Tecnico (Old)',3,'Tecnico professionista','2023-06-08 10:29:10'),
(61,'Tecnico Rilevatore',3,'Tecnico professionista','2023-06-08 10:29:10'),
(62,'Titolare',2,'Beneficiario','2023-06-08 10:29:10'),
(63,'Titolare altro diritto sull\'immobile',2,'Beneficiario','2023-06-08 10:29:10'),
(64,'Titolare Ex Impresa',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(65,'Titolare Impresa Affidataria Lavori Edili',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(66,'Titolare impresa esecutrice',4,'Impresa costruttrice','2023-06-08 10:29:10'),
(67,'Usufruttuario',1,'Bneficiario','2023-06-08 10:29:10'),
(68,'Usufruttuario con patto di riservato dominio',1,'Bneficiario','2023-06-08 10:29:10'),
(69,'Usufruttuario parziale per %',1,'Bneficiario','2023-06-08 10:29:10'),
(70,'Utilista della superficie',1,'Bneficiario','2023-06-08 10:29:10'),
(71,'Verbalizzato 1',6,'Verbalizzato','2023-06-08 10:29:10'),
(72,'Verbalizzato 2',6,'Verbalizzato','2023-06-08 10:29:10'),
(73,'Verbalizzato 3',6,'Verbalizzato','2023-06-08 10:29:10'),
(74,'Verbalizzato 4',6,'Verbalizzato','2023-06-08 10:29:10'),
(75,'Verbalizzato 5',6,'Verbalizzato','2023-06-08 10:29:10');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
