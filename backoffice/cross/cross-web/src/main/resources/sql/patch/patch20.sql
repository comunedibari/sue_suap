
--Aggiunto flg_is_to_send per memorizzare quali allegati inviare nelle comunicazioni
ALTER TABLE `pratiche_eventi_allegati` ADD COLUMN `flg_is_to_send` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `flg_is_principale`;
