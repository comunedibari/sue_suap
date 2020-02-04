
ALTER TABLE indirizzi_intervento ADD COLUMN latitudine VARCHAR(45) NULL, ADD COLUMN longitudine VARCHAR(45) NULL, ADD COLUMN dato_esteso_1 VARCHAR(255) NULL, ADD COLUMN dato_esteso_2 VARCHAR(255) NULL;
ALTER TABLE `dati_catastali`  DROP COLUMN `latitudine` , DROP COLUMN `longitudine`;