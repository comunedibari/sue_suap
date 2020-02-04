ALTER TABLE scadenze ADD COLUMN data_fine_scadenza_calcolata DATE NULL DEFAULT NULL AFTER data_fine_scadenza;
UPDATE scadenze set data_fine_scadenza_calcolata = data_fine_scadenza;