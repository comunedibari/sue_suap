CREATE DATABASE  IF NOT EXISTS `suapsue` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci */;
USE `suapsue`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: suapsue
-- ------------------------------------------------------
-- Server version	5.5.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `enti`
--

DROP TABLE IF EXISTS `enti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(4) COLLATE utf8_general_mysql500_ci NOT NULL,
  `descr` varchar(100) COLLATE utf8_general_mysql500_ci NOT NULL,
  `idEnte` int(11) NOT NULL,
  `codComune` int(11) NOT NULL,
  `url` varchar(300) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  `flag_attivo` bit(1) DEFAULT b'0',
  `comuneEgov` int(11) DEFAULT NULL,
  `urlPagina` varchar(200) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enti`
--

LOCK TABLES `enti` WRITE;
/*!40000 ALTER TABLE `enti` DISABLE KEYS */;
INSERT INTO `enti` VALUES (1,'SUAP','Bari',1001,893,'http://fossb.egov.ba.it/people/initProcess.do?communeCode=000059&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',1,'https://egov.ba.it/suap-bari    '),(2,'SUE','Bari',2001,893,'http://fossb.egov.ba.it/people/initProcess.do?communeCode=000060&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',1,'https://egov.ba.it/sue-bari'),(3,'SUAP','Molfetta',2202,7066,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000005&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(4,'SUE','Cassano delle Murge',2210,2687,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000046&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',10,'https://egov.ba.it/sue-cassano-delle-murge'),(5,'SUE','Giovinazzo',2211,5454,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000020&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',11,'https://egov.ba.it/sue-giovinazzo'),(6,'SUAP','Giovinazzo',2213,5454,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000019&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',11,'https://egov.ba.it/suap-giovinazzo'),(7,'SUAP','Bitetto',2215,1206,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000041&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',6,NULL),(8,'SUE','Bitetto',2216,1206,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000042&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',6,'https://egov.ba.it/sue-bitetto'),(9,'SUE','Noicattaro',2221,7953,'http://fossm.egov.ba.it/people/peopleL2/initProcess.do?communeCode=000024&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',463401,'https://egov.ba.it/sue-noicattaro'),(10,'SUAP','Noicattaro',2222,7953,'http://fossm.egov.ba.it/people/peopleL2/initProcess.do?communeCode=000023&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',463401,'https://egov.ba.it/suap-noicattaro'),(11,'SUAP','Terlizzi',2305,12318,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000033&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9027','\0',NULL,NULL),(12,'SUE','Terlizzi ',2306,12318,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000034&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=8027','\0',NULL,NULL),(13,'SUE','Triggiano',2229,12724,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000036&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',456101,'https://egov.ba.it/sue-triggiano'),(14,'SUAP','Triggiano',2230,12724,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000035&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',456101,'https://egov.ba.it/suap-triggiano'),(15,'SUAP','Valenzano',2233,12920,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000037&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',500702,'https://egov.ba.it/suap-valenzano'),(16,'SUE','Valenzano',2234,12920,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000038&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/sue-valenzano'),(18,'SUAP','Sannicandro di Bari',2236,10874,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000053&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',475851,NULL),(19,'SUE','Sannicandro di Bari',2237,10874,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000054&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',475851,NULL),(20,'SUE','Turi',2240,12783,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000058&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',500706,'https://egov.ba.it/sue-turi'),(21,'SUAP','Ruvo di Puglia',2242,10233,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000031&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',12,'https://egov.ba.it/suap-ruvo-di-puglia'),(22,'SUE','Ruvo di Puglia',2243,10233,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000032&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',12,'https://egov.ba.it/sue-ruvo-di-puglia'),(23,'SUE','Molfetta',2247,7066,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000006&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',NULL,'https://egov.ba.it/sue-molfetta'),(24,'SUE','Bitonto',2249,1207,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000002&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',3,'https://egov.ba.it/sue-bitonto'),(25,'SUAP','Bitonto',2251,1207,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000001&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',3,'https://egov.ba.it/suap-bitonto'),(26,'SUE','Binetto',2254,1185,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000040&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',5,'https://egov.ba.it/sue-binetto'),(27,'SUAP','Binetto',2256,1185,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000039&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',5,NULL),(28,'SUAP','Rutigliano',2259,10230,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000029&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',443051,'https://egov.ba.it/suap-rutigliano'),(29,'SUE','Rutigliano',2260,10230,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000030&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',443051,'https://egov.ba.it/sue-rutigliano'),(30,'SUAP','Bitritto',2261,1208,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000043&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',7,NULL),(31,'SUE','Bitritto',2262,1208,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000044&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',7,'https://egov.ba.it/sue-bitritto'),(32,'SUAP','Modugno',2267,7034,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000003&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(33,'SUE','Modugno',2268,7034,'http://fossg.egov.ba.it/people/initProcess.do?communeCode=000004&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',NULL,'https://egov.ba.it/sue-modugno'),(34,'SUE','Sammichele di Bari',2271,10381,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000052&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/sue-sammichele-di-bari'),(35,'SUAP','Sammichele di Bari',2272,10381,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000051&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(36,'SUAP','Adelfia',2280,70,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000009&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',4,NULL),(37,'SUAP','Capurso',2276,2286,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000011&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',8,'https://egov.ba.it/suap-capurso'),(38,'SUAP','Casamassima',2290,2573,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000013&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',9,NULL),(39,'SUAP','Cassano delle Murge',2296,2687,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000045&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',10,NULL),(40,'SUAP','Cellamare',2297,3309,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000047&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/suap-cellamare'),(41,'SUAP','Conversano',2299,4025,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000015&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(42,'SUAP','Gioia del Colle',2294,5440,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000017&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',444651,'https://egov.ba.it/suap-gioia-del-colle'),(44,'SUAP','Mola di Bari',2284,7062,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000021&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',463151,'https://egov.ba.it/suap-mola-di-bari'),(45,'SUAP','Palo del Colle',2303,8470,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000025&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(46,'SUAP','Polignano a Mare',2225,9120,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000027&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/suap-polignano-a-mare'),(47,'SUAP','Toritto',2307,12464,'ttp://fossp.egov.ba.it/people/initProcess.do?communeCode=000055&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',13,NULL),(48,'SUE','Adelfia',2279,70,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000010&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',4,NULL),(49,'SUE','Capurso',2275,2286,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000012&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',8,NULL),(50,'SUE','Casamassima',2289,2573,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000014&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',9,'https://egov.ba.it/sue-casamassima'),(51,'SUE','Cellamare',2298,3309,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000048&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/sue-cellamare'),(52,'SUE','Conversano',2300,4025,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000016&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/sue-conversano'),(53,'SUE','Gioia del Colle',2293,5440,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000018&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','',444651,'https://egov.ba.it/sue-gioia-del-colle'),(55,'SUE','Mola di Bari',2283,7062,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000022&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',463151,'https://egov.ba.it/sue-mola-di-bari'),(56,'SUE','Palo del Colle',2304,8470,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000026&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,'https://egov.ba.it/sue-palo-del-colle'),(57,'SUE','Polignano a Mare',2226,9120,'http://fossm.egov.ba.it/people/initProcess.do?communeCode=000028&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',NULL,NULL),(58,'SUE','Toritto',2308,12464,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000056&processName=it.people.fsl.servizi.praticheOnLine.visura.myPage&selectingCommune=true','\0',13,'https://egov.ba.it/sue-toritto'),(59,'SUAP','Turi',2286,12783,'http://fossp.egov.ba.it/people/initProcess.do?communeCode=000057&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&selectingCommune=true&codEnte=9030','\0',NULL,NULL);
/*!40000 ALTER TABLE `enti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'suapsue'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-27  9:01:11
