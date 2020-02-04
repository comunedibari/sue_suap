-- 11/12/13 : Modifica per valorizzare la data_protocollazione di tutte le pratiche caricate da protocollo e prive di questo campo
UPDATE pratica p SET p.data_protocollazione = (
    SELECT pp.data_protocollazione 
    FROM pratiche_protocollo pp 
    WHERE p.id_staging = pp.id_staging AND pp.tipo_documento = 'PRAONLINE') 
WHERE p.data_protocollazione IS NULL AND p.id_pratica_padre IS NULL ;