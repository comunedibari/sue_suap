DROP VIEW IF EXISTS endoprocedimenti_view;

CREATE VIEW endoprocedimenti_view AS (
SELECT
  p.id_proc   AS id_proc,
  p.cod_proc  AS cod_proc,
  p.tipo_proc AS tipo_proc,
  c.id_ente   AS id_ente,
  pt.des_proc AS des_proc
FROM procedimenti p
LEFT JOIN procedimenti_enti c ON p.id_proc = c.id_proc
JOIN procedimenti_testi pt ON p.id_proc = pt.id_proc
WHERE ISNULL(c.id_proc));

DROP VIEW IF EXISTS pratiche_allegati_view;
CREATE VIEW pratiche_allegati_view AS (
SELECT DISTINCT
  p.id_pratica      AS id_pratica,
  a.id              AS id,
  a.descrizione     AS descrizione,
  a.nome_file       AS nome_file,
  a.tipo_file       AS tipo_file,
  a.path_file       AS path_file,
  a.id_file_esterno AS id_file_esterno,
  a.file            AS FILE
FROM pratica p
JOIN pratiche_eventi pe ON p.id_pratica = pe.id_pratica
JOIN pratiche_eventi_allegati pea ON pe.id_pratica_evento = pea.id_pratica_evento
JOIN allegati a ON pea.id_allegato = a.id);

DROP VIEW IF EXISTS procedimenti_localizzati_view;
CREATE VIEW procedimenti_localizzati_view AS (
SELECT
  p.id_proc   AS id_proc,
  p.cod_proc  AS cod_proc,
  p.tipo_proc  AS tipo_proc,
  pt.des_proc AS des_proc,
  p.termini   AS termini,
  l.id_lang   AS id_lang,
  l.cod_lang  AS cod_lang
FROM procedimenti p
LEFT JOIN procedimenti_testi pt ON p.id_proc = pt.id_proc
LEFT JOIN lingue l ON pt.id_lang = l.id_lang);

DROP VIEW IF EXISTS procedimento_unico_view;
CREATE VIEW procedimento_unico_view AS (
SELECT
  p1.id_proc     AS id_proc,
  pe1.id_ente    AS id_ente,
  p1.tipo_proc   AS tipo_proc,
  e1.descrizione AS descrizione,
  pt1.des_proc   AS des_proc
FROM procedimenti p1
JOIN procedimenti_enti pe1 ON p1.id_proc = pe1.id_proc
JOIN enti e1 ON pe1.id_ente = e1.id_ente
JOIN procedimenti_testi pt1 ON p1.id_proc = pt1.id_proc);

DROP VIEW IF EXISTS procedimenti_collegati_view;
CREATE VIEW procedimenti_collegati_view AS (
SELECT
  b.id_proc     AS id_proc,
  b.cod_proc    AS cod_proc,
  b.des_proc    AS des_proc,
  a.id_ente     AS id_ente,
  a.descrizione AS des_ente,
  a.id_proc     AS id_procedimento_unico,
  a.des_proc    AS des_procedimento_unico,
  b.tipo_proc   AS tipo_proc
FROM procedimento_unico_view a
JOIN endoprocedimenti_view b ON a.tipo_proc = b.tipo_proc
ORDER BY b.des_proc,a.id_ente);

DROP VIEW IF EXISTS scadenze_da_chiudere_view;
CREATE VIEW scadenze_da_chiudere_view AS (
SELECT
  s.id_scadenza          AS id_scadenza,
  pev.id_evento          AS id_evento,
  p.id_pratica           AS id_pratica,
  s.id_ana_scadenza      AS id_ana_scadenza,
  pes.script_scadenza    AS script_scadenza,
  pe.data_evento         AS data_evento_da_chiudere,
  pe.note                AS note,
  pe.protocollo          AS numero_protocollo,
  s.data_inizio_scadenza AS data_inizio_scadenza,
  s.data_fine_scadenza   AS data_fine_scadenza,
  s.descrizione_scadenza AS des_scadenza,
  pev.des_evento         AS des_evento_origine,
  e.descrizione          AS des_ente
FROM pratica p
JOIN processi_eventi pev ON pev.id_processo = p.id_processo
JOIN processi_eventi_scadenze pes ON pes.id_evento = pev.id_evento
JOIN scadenze s ON s.id_pratica = p.id_pratica AND s.id_ana_scadenza = pes.id_ana_scadenza
JOIN pratiche_eventi pe ON s.id_pratica_evento = pe.id_pratica_evento
LEFT JOIN pratiche_eventi_enti pee ON pee.id_pratica_evento = pe.id_pratica_evento
LEFT JOIN enti e ON e.id_ente = pee.id_ente
WHERE s.id_stato = 'A' AND pes.flg_visualizza_scadenza = 'S'
AND pes.id_stato_scadenza = 'C');