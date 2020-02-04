Use opencross;
ALTER TABLE opencross.dati_catastali ADD COLUMN categoria VARCHAR(45) NULL  AFTER cod_immobile ;
INSERT INTO plugin_configuration (name, value) VALUES ('LH00004.categoria', 'd019');
INSERT INTO plugin_configuration (name, value) VALUES ('LH00023.categoria', 'd019');
