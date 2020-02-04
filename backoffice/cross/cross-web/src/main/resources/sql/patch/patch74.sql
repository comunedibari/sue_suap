ALTER TABLE `processi_eventi` 
ADD COLUMN `forza_chiusura_scadenze` VARCHAR(1) NULL DEFAULT 'N' AFTER `flg_automatico`;
