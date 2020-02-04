ALTER TABLE `pratiche_eventi_allegati` ADD COLUMN `flg_is_principale` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `id_allegato`;
