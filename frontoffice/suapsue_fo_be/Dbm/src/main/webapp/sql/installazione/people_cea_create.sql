CREATE DATABASE  IF NOT EXISTS `people_cea` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `people_cea`;

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
DROP TABLE IF EXISTS interventi_seq;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE interventi_seq (
  cod_int_sel varchar(8) NOT NULL DEFAULT '',
  cod_int_prec varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_int_sel,cod_int_prec)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS testo_condizioni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE testo_condizioni (
  cod_cond varchar(10) NOT NULL DEFAULT '',
  testo_cond text NOT NULL,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_cond,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS html_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE html_testi (
  cod_testo varchar(255) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_testo text,
  PRIMARY KEY (cod_testo,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS norme_interventi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE norme_interventi (
  cod_rif varchar(8) NOT NULL DEFAULT '',
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  art_rif text,
  PRIMARY KEY (cod_int,cod_rif),
  KEY norme_interventi_idx (cod_int)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS sportelli_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE sportelli_testi (
  cod_sport varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_sport varchar(255) NOT NULL DEFAULT '',
  orari varchar(255) DEFAULT NULL,
  PRIMARY KEY (cod_sport,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS anagrafica_campi_valori_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE anagrafica_campi_valori_testi (
  nome varchar(10) NOT NULL DEFAULT '',
  val_select varchar(20) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_valore varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (nome,val_select,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS condizioni_di_attivazione;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE condizioni_di_attivazione (
  cod_sett varchar(8) NOT NULL DEFAULT '',
  cod_ope varchar(10) NOT NULL DEFAULT '',
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  tip_aggregazione varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (tip_aggregazione,cod_sett,cod_ope,cod_int),
  KEY condizioni_di_attivazione_idx (cod_sett,cod_ope),
  KEY condizioni_di_attivazione_idx_2 (tip_aggregazione,cod_sett),
  KEY condizioni_di_attivazione_idx_3 (tip_aggregazione,cod_ope),
  KEY condizioni_di_attivazione_idx_4 (tip_aggregazione,cod_int)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href (
  href varchar(50) NOT NULL DEFAULT '',
  tp_href char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (href)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS notifiche;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE notifiche (
  cnt int(10) unsigned NOT NULL AUTO_INCREMENT,
  cod_utente_carico varchar(16) DEFAULT NULL,
  cod_utente_origine varchar(16) NOT NULL,
  data_notifica timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  testo_notifica text NOT NULL,
  stato_notifica char(1) NOT NULL,
  nome_file varchar(255) DEFAULT NULL,
  cod_elemento varchar(200) DEFAULT NULL,
  PRIMARY KEY (cnt),
  KEY idx_notifiche (cod_elemento)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_campi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_campi (
  cod_onere_campo varchar(10) NOT NULL DEFAULT '',
  tp_campo char(1) NOT NULL DEFAULT '',
  lng_campo int(1) NOT NULL DEFAULT '0',
  lng_dec int(1) unsigned DEFAULT NULL,
  PRIMARY KEY (cod_onere_campo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_gerarchia;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_gerarchia (
  cod_padre int(9) unsigned NOT NULL DEFAULT '0',
  cod_figlio int(9) unsigned NOT NULL DEFAULT '0',
  cod_onere_formula varchar(10) DEFAULT NULL,
  PRIMARY KEY (cod_figlio),
  KEY oneri_gerarchia_idx (cod_padre),
  KEY oneri_gerarchia_idx_2 (cod_padre,cod_figlio),
  KEY oneri_gerarchia_idx_3 (cod_figlio,cod_padre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_comuni (
  cod_oneri varchar(8) NOT NULL DEFAULT '',
  cod_com varchar(8) NOT NULL DEFAULT '',
  flg char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_oneri,cod_com),
  KEY oneri_comuni_idx (cod_com)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS interventi_collegati;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE interventi_collegati (
  cod_int_padre int(9) unsigned NOT NULL DEFAULT '0',
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  cod_cond varchar(10) DEFAULT NULL,
  PRIMARY KEY (cod_int,cod_int_padre),
  KEY interventi_collegati_idx (cod_int_padre),
  KEY interventi_collegati_idx2 (cod_cond)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href_testi (
  href varchar(50) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tit_href varchar(255) NOT NULL DEFAULT '',
  piede_href text,
  PRIMARY KEY (href,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_campi_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_campi_testi (
  cod_onere_campo varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  testo_campo varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_onere_campo,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_campi_select_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_campi_select_testi (
  cod_onere_campo varchar(10) NOT NULL DEFAULT '',
  val_select varchar(255) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_val_select varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_onere_campo,val_select,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS gerarchia_operazioni_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gerarchia_operazioni_testi (
  cod_ramo varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_ramo text,
  tip_aggregazione varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_ramo,cod_lang,tip_aggregazione)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS servizi_validita;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE servizi_validita (
  cod_servizio int(4) NOT NULL DEFAULT '0',
  flg_bando char(1) DEFAULT NULL,
  dalla_data date DEFAULT NULL,
  alla_data date DEFAULT NULL,
  PRIMARY KEY (cod_servizio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS normative_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE normative_testi (
  cod_rif varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tit_rif text NOT NULL,
  PRIMARY KEY (cod_rif,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS cud;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cud (
  cod_cud varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_cud)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS templates_vari;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE templates_vari (
  nome_template varchar(50) NOT NULL DEFAULT '',
  des_template varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (nome_template)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS cud_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE cud_testi (
  cod_cud varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_cud varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_cud,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS anagrafica;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE anagrafica (
  contatore int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(10) NOT NULL DEFAULT '',
  riga int(2) NOT NULL DEFAULT '0',
  posizione int(2) NOT NULL DEFAULT '0',
  tp_riga char(1) NOT NULL DEFAULT '',
  tipo char(1) NOT NULL DEFAULT '',
  valore varchar(20) DEFAULT '',
  controllo char(1) DEFAULT '',
  web_serv varchar(255) DEFAULT NULL,
  nome_xsd varchar(255) DEFAULT NULL,
  campo_key varchar(10) DEFAULT NULL,
  campo_dati varchar(255) DEFAULT NULL,
  campo_xml_mod varchar(255) DEFAULT NULL,
  tp_controllo char(1) DEFAULT 'T',
  lunghezza int(4) DEFAULT '30',
  decimali int(4) DEFAULT '0',
  edit char(1) NOT NULL DEFAULT 'S',
  raggruppamento_check varchar(10) DEFAULT NULL,
  campo_collegato varchar(10) DEFAULT NULL,
  val_campo_collegato varchar(20) DEFAULT NULL,
  precompilazione varchar(100) DEFAULT NULL,
  livello int(2) DEFAULT '0',
  azione varchar(50) DEFAULT NULL,
  flg_precompilazione char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (nome,contatore)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href_campi_valori_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href_campi_valori_testi (
  href varchar(50) NOT NULL DEFAULT '',
  nome varchar(10) NOT NULL DEFAULT '',
  val_select varchar(20) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_valore varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (href,nome,val_select,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS procedimenti_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE procedimenti_testi (
  cod_proc varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tit_proc text NOT NULL,
  PRIMARY KEY (cod_proc,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS classi_enti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE classi_enti (
  cod_classe_ente varchar(8) NOT NULL DEFAULT '',
  flg_com char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_classe_ente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS templates_vari_risorse;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE templates_vari_risorse (
  nome_template varchar(50) NOT NULL DEFAULT '',
  cod_sport varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  doc_blob longtext,
  nome_file varchar(255) NOT NULL DEFAULT '',
  tipo_file varchar(255) DEFAULT NULL,
  PRIMARY KEY (nome_template,cod_sport,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS enti_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE enti_comuni (
  cod_ente varchar(8) NOT NULL DEFAULT '',
  indirizzo varchar(255) DEFAULT NULL,
  cap varchar(5) DEFAULT NULL,
  citta varchar(255) DEFAULT NULL,
  prov varchar(4) DEFAULT NULL,
  tel varchar(255) DEFAULT NULL,
  fax varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  ccb varchar(23) DEFAULT NULL,
  ccp varchar(23) DEFAULT NULL,
  tp_prg tinyint(1) unsigned DEFAULT NULL,
  estensione_firma varchar(4) DEFAULT NULL,
  cod_classe_ente varchar(8) NOT NULL DEFAULT '',
  aoo varchar(8) DEFAULT NULL,
  src_pyth text,
  cod_istat varchar(20) DEFAULT NULL,
  cod_bf varchar(20) DEFAULT NULL,
  PRIMARY KEY (cod_ente),
  KEY enti_comuni_idx (cod_classe_ente,cod_ente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS comuni_aggregazione;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE comuni_aggregazione (
  cod_ente varchar(8) NOT NULL DEFAULT '',
  tip_aggregazione varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_ente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS classi_enti_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE classi_enti_testi (
  cod_classe_ente varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_classe_ente varchar(255) DEFAULT NULL,
  PRIMARY KEY (cod_classe_ente,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS servizi_accesslist;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE servizi_accesslist (
  cod_servizio int(4) NOT NULL,
  cod_fisc varchar(20) NOT NULL,
  PRIMARY KEY (cod_servizio,cod_fisc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS normative;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE normative (
  cod_rif varchar(8) NOT NULL DEFAULT '',
  nome_rif varchar(255) DEFAULT NULL,
  cod_tipo_rif varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_rif)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS destinatari;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE destinatari (
  cod_dest varchar(8) NOT NULL DEFAULT '',
  intestazione varchar(255) NOT NULL DEFAULT '',
  nome_dest varchar(255) NOT NULL DEFAULT '',
  tel varchar(20) DEFAULT NULL,
  fax varchar(20) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  indirizzo varchar(255) DEFAULT NULL,
  cap varchar(5) DEFAULT NULL,
  citta varchar(255) DEFAULT NULL,
  prov varchar(4) DEFAULT NULL,
  cod_ente varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_dest)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS interventi_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE interventi_testi (
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tit_int text NOT NULL,
  PRIMARY KEY (cod_int,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS settori_attivita_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE settori_attivita_comuni (
  cod_sett varchar(8) NOT NULL DEFAULT '',
  cod_com varchar(8) NOT NULL DEFAULT '',
  flg char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_sett,cod_com),
  KEY settori_attivita_comuni_idx2 (cod_com,cod_sett)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS allegati_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE allegati_comuni (
  cod_doc varchar(20) NOT NULL DEFAULT '',
  cod_com varchar(8) NOT NULL DEFAULT '',
  flg char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_doc,cod_com),
  KEY allegati_comuni_idx (cod_com)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS allegati;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE allegati (
  cod_doc varchar(20) NOT NULL DEFAULT '',
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  cod_cond varchar(10) DEFAULT NULL,
  flg_autocert int(1) NOT NULL DEFAULT '0',
  copie int(1) unsigned NOT NULL DEFAULT '1',
  flg_obb char(1) DEFAULT 'N',
  tipologie varchar(100) DEFAULT NULL,
  num_max_pag int(4) DEFAULT NULL,
  dimensione_max int(7) DEFAULT NULL,
  ordinamento int(4) DEFAULT '9999',
  PRIMARY KEY (cod_doc,cod_int),
  KEY allegati_idx (cod_int),
  KEY allegati_idx2 (cod_cond)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS help_online;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE help_online (
  id_help varchar(255) NOT NULL DEFAULT '',
  des_help varchar(255) NOT NULL DEFAULT '',
  testo_help text,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (id_help,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS utenti_interventi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE utenti_interventi (
  cod_utente varchar(16) NOT NULL,
  cod_int int(10) unsigned NOT NULL,
  PRIMARY KEY (cod_utente,cod_int)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS templates_immagini;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE templates_immagini (
  nome_immagine varchar(50) NOT NULL DEFAULT '',
  des_immagine varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (nome_immagine)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS utenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE utenti (
  cod_utente varchar(16) NOT NULL,
  cognome_nome varchar(45) NOT NULL,
  email varchar(100) NOT NULL,
  cod_utente_padre varchar(16) DEFAULT NULL,
  ruolo char(1) NOT NULL,
  PRIMARY KEY (cod_utente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS interventi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE interventi (
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  cod_proc varchar(8) NOT NULL DEFAULT '',
  flg_obb char(1) NOT NULL DEFAULT '',
  cod_ente_origine varchar(8) DEFAULT NULL,
  cod_int_origine int(9) DEFAULT NULL,
  PRIMARY KEY (cod_int),
  KEY interventi_idx (cod_proc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS sportelli;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE sportelli (
  cod_sport varchar(10) NOT NULL DEFAULT '',
  nome_rup varchar(255) DEFAULT NULL,
  tel varchar(20) DEFAULT NULL,
  fax varchar(20) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  indirizzo varchar(255) DEFAULT NULL,
  cap varchar(5) DEFAULT NULL,
  citta varchar(255) DEFAULT NULL,
  prov varchar(4) DEFAULT NULL,
  email_cert varchar(255) DEFAULT NULL,
  flg_attivo char(1) NOT NULL DEFAULT 'S',
  flg_pu char(1) NOT NULL DEFAULT '',
  flg_su char(1) DEFAULT NULL,
  id_mail_server varchar(50) DEFAULT NULL,
  id_protocollo varchar(50) DEFAULT NULL,
  id_backoffice varchar(50) DEFAULT NULL,
  PRIMARY KEY (cod_sport)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS gerarchia_settori;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gerarchia_settori (
  cod_ramo varchar(10) NOT NULL DEFAULT '',
  cod_ramo_prec varchar(10) DEFAULT NULL,
  cod_sett varchar(10) DEFAULT NULL,
  cod_rif varchar(8) DEFAULT NULL,
  PRIMARY KEY (cod_ramo),
  KEY gerarchia_settori_idx (cod_ramo_prec),
  KEY gerarchia_settori_idx_2 (cod_ramo,cod_ramo_prec)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS normative_documenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE normative_documenti (
  cod_rif varchar(8) NOT NULL DEFAULT '',
  tip_doc varchar(40) NOT NULL DEFAULT '',
  nome_file varchar(255) NOT NULL DEFAULT '',
  doc_blob longblob NOT NULL,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_rif,cod_lang,tip_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS tipi_rif;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipi_rif (
  cod_tipo_rif char(4) NOT NULL DEFAULT '',
  cod_lang char(2) NOT NULL DEFAULT '',
  tipo_rif char(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_tipo_rif,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS eventi_vita;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE eventi_vita (
  cod_eve_vita int(4) NOT NULL AUTO_INCREMENT,
  cod_lang char(2) NOT NULL DEFAULT 'it',
  des_eve_vita varchar(255) DEFAULT NULL,
  PRIMARY KEY (cod_eve_vita,cod_lang)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_testi (
  cod_oneri varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_oneri text,
  note text,
  PRIMARY KEY (cod_oneri,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS notifiche_documenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE notifiche_documenti (
  cnt int(10) unsigned NOT NULL,
  tip_doc varchar(100) NOT NULL DEFAULT '',
  nome_file varchar(255) NOT NULL DEFAULT '',
  doc_blob longblob NOT NULL,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cnt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS notifiche_utenti_interventi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE notifiche_utenti_interventi (
  cnt int(10) unsigned NOT NULL,
  cod_utente varchar(16) NOT NULL,
  cod_int int(9) unsigned NOT NULL,
  PRIMARY KEY (cnt,cod_utente,cod_int)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_documenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_documenti (
  cod_doc_onere varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tip_doc varchar(40) NOT NULL DEFAULT '',
  nome_file varchar(255) DEFAULT NULL,
  doc_blob longblob,
  PRIMARY KEY (cod_doc_onere,cod_lang,tip_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS gerarchia_settori_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gerarchia_settori_testi (
  cod_ramo varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_ramo text,
  PRIMARY KEY (cod_ramo,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS condizioni_normative;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE condizioni_normative (
  cod_cond varchar(10) NOT NULL DEFAULT '',
  cod_rif varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_cond,cod_rif),
  KEY condizioni_normative_idx (cod_cond)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS norme_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE norme_comuni (
  cod_rif varchar(8) NOT NULL,
  cod_com varchar(8) NOT NULL,
  flg char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_rif,cod_com),
  KEY norme_comuni_idx (cod_com)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS gerarchia_operazioni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE gerarchia_operazioni (
  cod_ramo varchar(10) NOT NULL DEFAULT '',
  cod_ramo_prec varchar(10) DEFAULT NULL,
  cod_ope varchar(10) DEFAULT NULL,
  cod_rif varchar(8) DEFAULT NULL,
  tip_aggregazione varchar(4) NOT NULL DEFAULT '',
  raggruppamento_check varchar(10) DEFAULT NULL,
  flg_sino char(1) DEFAULT 'N',
  PRIMARY KEY (tip_aggregazione,cod_ramo),
  KEY gerarchia_operazioni_idx (tip_aggregazione,cod_ope,cod_ramo),
  KEY gerarchia_operazioni_idx3 (tip_aggregazione, cod_ramo_prec)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href_campi_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href_campi_testi (
  contatore int(11) NOT NULL DEFAULT '0',
  href varchar(50) NOT NULL DEFAULT '',
  nome varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_campo text NOT NULL,
  PRIMARY KEY (href,nome,cod_lang,contatore)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href_campi_valori;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href_campi_valori (
  href varchar(50) NOT NULL DEFAULT '',
  nome varchar(10) NOT NULL DEFAULT '',
  val_select varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (nome,href,val_select)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS href_campi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE href_campi (
  contatore int(11) NOT NULL,
  href varchar(50) NOT NULL DEFAULT '',
  nome varchar(10) NOT NULL DEFAULT '',
  riga int(2) NOT NULL DEFAULT '0',
  posizione int(2) NOT NULL DEFAULT '0',
  tp_riga char(1) NOT NULL DEFAULT '',
  tipo char(1) NOT NULL DEFAULT '',
  valore varchar(20) DEFAULT '',
  controllo char(1) DEFAULT '',
  web_serv varchar(50) DEFAULT NULL,
  nome_xsd varchar(50) DEFAULT NULL,
  campo_key varchar(10) DEFAULT NULL,
  campo_dati varchar(255) DEFAULT NULL,
  campo_xml_mod varchar(255) DEFAULT NULL,
  tp_controllo char(1) DEFAULT 'T',
  lunghezza int(4) DEFAULT '30',
  decimali int(4) DEFAULT '0',
  edit char(1) NOT NULL DEFAULT 'S',
  raggruppamento_check varchar(10) DEFAULT NULL,
  campo_collegato varchar(10) DEFAULT NULL,
  val_campo_collegato varchar(20) DEFAULT NULL,
  marcatore_incrociato varchar(40) NOT NULL,
  precompilazione varchar(100) NOT NULL,
  PRIMARY KEY (href,nome,contatore)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS documenti_documenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documenti_documenti (
  cod_doc varchar(20) NOT NULL DEFAULT '',
  tip_doc varchar(40) NOT NULL DEFAULT '',
  nome_file varchar(255) DEFAULT NULL,
  doc_blob longblob,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_doc,cod_lang,tip_doc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS templates_immagini_immagini;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE templates_immagini_immagini (
  nome_immagine varchar(50) NOT NULL DEFAULT '',
  cod_sport varchar(10) NOT NULL DEFAULT '',
  cod_com varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  immagine longtext,
  nome_file varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (nome_immagine,cod_sport,cod_com,cod_lang)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_gerarchia_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_gerarchia_testi (
  cod_figlio int(9) unsigned NOT NULL DEFAULT '0',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_gerarchia text,
  PRIMARY KEY (cod_figlio,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS configuration;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE configuration (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  communeid varchar(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(400) DEFAULT NULL,
  note varchar(400) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_documenti_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_documenti_testi (
  cod_doc_onere varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_doc_onere varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_doc_onere,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS documenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documenti (
  cod_doc varchar(20) NOT NULL DEFAULT '',
  flg_dic char(1) NOT NULL DEFAULT '',
  href varchar(50) DEFAULT NULL,
  PRIMARY KEY (cod_doc),
  KEY idx_documenti (href)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS documenti_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE documenti_testi (
  cod_doc varchar(20) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  tit_doc text NOT NULL,
  des_doc text,
  PRIMARY KEY (cod_doc,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS interventi_comuni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE interventi_comuni (
  cod_int int(9) unsigned NOT NULL DEFAULT '0',
  cod_com varchar(8) NOT NULL DEFAULT '',
  flg char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (cod_int,cod_com),
  KEY interventi_comuni_idx (cod_com)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS templates;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE templates (
  cod_sport varchar(10) NOT NULL DEFAULT '',
  cod_com varchar(8) NOT NULL DEFAULT '',
  tipo varchar(255) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  template longtext,
  nome_file varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_sport,cod_com,tipo,cod_lang)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS procedimenti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE procedimenti (
  cod_proc varchar(8) NOT NULL DEFAULT '',
  ter_eva int(4) unsigned DEFAULT NULL,
  flg_tipo_proc char(1) NOT NULL DEFAULT '',
  flg_bollo char(1) NOT NULL DEFAULT '',
  cod_cud varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_proc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS relazioni_enti;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE relazioni_enti (
  cod_com varchar(8) NOT NULL DEFAULT '',
  cod_dest varchar(8) NOT NULL DEFAULT '',
  cod_cud varchar(8) NOT NULL DEFAULT '',
  cod_sport varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_com,cod_cud),
  KEY relazioni_enti_idx (cod_com)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS anagrafica_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE anagrafica_testi (
  contatore int(11) NOT NULL DEFAULT '0',
  nome varchar(10) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_campo text NOT NULL,
  PRIMARY KEY (nome,cod_lang,contatore)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS servizi_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE servizi_testi (
  cod_servizio int(4) NOT NULL AUTO_INCREMENT,
  cod_com varchar(8) NOT NULL DEFAULT '',
  cod_eve_vita int(4) NOT NULL DEFAULT '0',
  cod_lang char(2) NOT NULL DEFAULT 'it',
  des_servizio longtext NOT NULL,
  nome_servizio varchar(255) DEFAULT NULL,
  PRIMARY KEY (cod_servizio,cod_com,cod_eve_vita)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_campi_formula;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_campi_formula (
  cod_onere_formula char(10) NOT NULL DEFAULT '',
  cod_onere_campo char(10) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_onere_formula,cod_onere_campo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS anagrafica_campi_valori;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE anagrafica_campi_valori (
  nome varchar(10) NOT NULL DEFAULT '',
  val_select varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (nome,val_select)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS notifiche_sql;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE notifiche_sql (
  cnt int(10) unsigned NOT NULL,
  sql_text longtext NOT NULL,
  PRIMARY KEY (cnt) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_interventi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_interventi (
  cod_int int(9) NOT NULL,
  cod_oneri varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_int,cod_oneri),
  KEY oneri_interventi_idx (cod_int)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS tipi_aggregazione;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE tipi_aggregazione (
  tip_aggregazione varchar(4) NOT NULL DEFAULT '',
  des_aggregazione varchar(255) NOT NULL DEFAULT '',
  href varchar(50) DEFAULT NULL,
  PRIMARY KEY (tip_aggregazione)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS enti_comuni_testi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE enti_comuni_testi (
  cod_ente varchar(8) NOT NULL DEFAULT '',
  cod_lang varchar(2) NOT NULL DEFAULT '',
  des_ente varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_ente,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri (
  cod_oneri varchar(8) NOT NULL DEFAULT '',
  cod_doc_onere varchar(10) DEFAULT NULL,
  imp_acc decimal(10,2) DEFAULT NULL,
  cod_padre int(9) unsigned DEFAULT NULL,
  cod_cud varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_oneri)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS operazioni;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE operazioni (
  cod_ope varchar(10) NOT NULL DEFAULT '',
  des_ope text NOT NULL,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_ope,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS servizi;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE servizi (
  cod_servizio int(4) NOT NULL AUTO_INCREMENT,
  cod_com varchar(8) NOT NULL DEFAULT '',
  cod_eve_vita int(4) NOT NULL DEFAULT '0',
  bookmark_pointer longtext NOT NULL,
  configuration longtext NOT NULL,
  PRIMARY KEY (cod_servizio,cod_com,cod_eve_vita)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS oneri_formula;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE oneri_formula (
  cod_onere_formula varchar(10) NOT NULL DEFAULT '',
  des_formula varchar(255) NOT NULL DEFAULT '',
  formula text NOT NULL,
  PRIMARY KEY (cod_onere_formula)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS settori_attivita;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE settori_attivita (
  cod_sett varchar(8) NOT NULL DEFAULT '',
  des_sett text NOT NULL,
  cod_lang varchar(2) NOT NULL DEFAULT '',
  PRIMARY KEY (cod_sett,cod_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

