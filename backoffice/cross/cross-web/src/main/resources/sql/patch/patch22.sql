
-- Lanciare per sistemare il database per valorizzare correttamente la colonna tipo_fila che precedentemente aveva come unico valore octect-stream.
-- La correzione è relativa solo ai formati p7m, xml, docx, txt e pdf.
-- Nel caso si volesse estendere la patch per correggere diversi formati sostituire nel LIKE l'estenzione del file e nel SET il tipo file che si ottiene effettuando un upload del file 
--(può essere visualizzato nell'html della pagina, in un input nascosto nello stesso td contenente l'icona del tipo)


UPDATE allegati SET allegati.tipo_file = 'application/pkcs7-mime' WHERE nome_file LIKE '%.p7m';

UPDATE allegati SET allegati.tipo_file = 'text/xml' WHERE nome_file LIKE '%.xml';

UPDATE allegati SET allegati.tipo_file = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' WHERE nome_file LIKE '%.docx';

UPDATE allegati SET allegati.tipo_file = 'text/plain' WHERE nome_file LIKE '%.txt';

UPDATE allegati SET allegati.tipo_file = 'application/pdf' WHERE nome_file LIKE '%.pdf';