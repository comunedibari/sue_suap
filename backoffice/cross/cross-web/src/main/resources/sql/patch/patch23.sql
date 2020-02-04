ALTER TABLE `email` ADD COLUMN `tipo_destinazione` VARCHAR(45) NULL DEFAULT 'SCONOSCIUTA' AFTER `gruppo`;
UPDATE email set tipo_destinazione = 'SCONOSCIUTA';
COMMIT;
