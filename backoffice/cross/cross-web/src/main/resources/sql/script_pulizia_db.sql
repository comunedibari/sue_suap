TRUNCATE TABLE email;
TRUNCATE TABLE ri_integrazione_rea;
TRUNCATE TABLE dati_catastali;
TRUNCATE TABLE pratica_anagrafica;
TRUNCATE TABLE pratica_procedimenti;
TRUNCATE TABLE pratiche_eventi_allegati;
TRUNCATE TABLE pratiche_eventi_anagrafiche;
TRUNCATE TABLE pratiche_eventi_enti;
TRUNCATE TABLE scadenze;
TRUNCATE TABLE anagrafica_recapiti;

-- Aggiunta 12/12/13
TRUNCATE TABLE relazioni_anagrafiche;

TRUNCATE TABLE anagrafica;
TRUNCATE TABLE recapiti;
UPDATE pratica SET id_recapito = NULL;
TRUNCATE TABLE recapiti;
UPDATE pratica SET id_modello = NULL;
TRUNCATE TABLE allegati;
UPDATE pratica SET id_pratica_padre = NULL;
TRUNCATE TABLE note_pratica;
TRUNCATE TABLE pratiche_protocollo;
UPDATE pratiche_protocollo SET id_staging = NULL, data_presa_in_carico_cross = NULL;
TRUNCATE TABLE at_record_dettaglio;
TRUNCATE TABLE comunicazione;
TRUNCATE TABLE pratiche_eventi;
-- Pulizia completa, comprese le configurazione degli utenti, enti, processi
-- TRUNCATE TABLE procedimenti_testi;
-- TRUNCATE TABLE ruoli;
-- TRUNCATE TABLE procedimenti_enti;
-- TRUNCATE TABLE procedimenti;
-- TRUNCATE TABLE enti_comuni;
-- TRUNCATE TABLE enti;
-- TRUNCATE TABLE eventi_template;
-- TRUNCATE TABLE enti;
-- TRUNCATE TABLE processi_steps;
-- TRUNCATE TABLE processi_eventi_scadenze;
-- TRUNCATE TABLE processi_eventi;
-- TRUNCATE TABLE processi;
TRUNCATE TABLE recapiti;

-- Aggiunta 12/12/13
TRUNCATE TABLE eventi_mail_messaggi;
TRUNCATE TABLE eventi_mail;
TRUNCATE TABLE messaggio;

TRUNCATE TABLE pratica;
TRUNCATE TABLE staging;
TRUNCATE TABLE errori;

-- Aggiunta 12/12/13
TRUNCATE TABLE qrtz_blob_triggers;
TRUNCATE TABLE qrtz_calendars;
TRUNCATE TABLE qrtz_cron_triggers;
TRUNCATE TABLE qrtz_fired_triggers;
TRUNCATE TABLE qrtz_job_details;
TRUNCATE TABLE qrtz_job_listeners;
TRUNCATE TABLE qrtz_locks;
TRUNCATE TABLE qrtz_paused_trigger_grps;
TRUNCATE TABLE qrtz_scheduler_state;
TRUNCATE TABLE qrtz_simple_triggers;
TRUNCATE TABLE qrtz_trigger_listeners;
TRUNCATE TABLE qrtz_triggers;