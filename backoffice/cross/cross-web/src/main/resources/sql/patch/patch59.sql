ALTER TABLE `processi_eventi_anagrafica` 
DROP COLUMN `tipologia`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id_evento`, `id_anagrafica`);

ALTER TABLE `processi_eventi_enti` 
DROP COLUMN `tipologia`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id_evento`, `id_ente`);