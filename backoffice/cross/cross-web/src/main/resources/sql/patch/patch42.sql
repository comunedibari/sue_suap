alter table dati_catastali drop column id_comune_censuario;
alter table dati_catastali add column (comune_censuario varchar(255) null);

ALTER TABLE lk_tipo_unita DROP FOREIGN KEY lk_tipo_sistema_catastale;
alter table lk_tipo_unita drop column id_tipo_sistema_catastale;
