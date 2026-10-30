-- da migliorare le performance in quanto estremamente lento
drop procedure popolate_descrizione_evento;
DELIMITER &&  
CREATE PROCEDURE popolate_descrizione_evento ()    

BEGIN    
	DECLARE descrizioneevento text;
    DECLARE idstaging int;
    
    DECLARE n INT DEFAULT 0;
	DECLARE i INT DEFAULT 0;
	SELECT COUNT(*) FROM staging INTO n;

	SET i=0;
	WHILE i<n DO 
	  	SELECT EXTRACTVALUE(CONVERT(xml_ricevuto USING utf8), '//DichiarazioniDinamiche/DichiarazioneDinamica/href[text()=\'LH00026\']/../campiHref/campoHref/valoreUtente') ,id_staging
        into descrizioneevento ,idstaging
        FROM staging LIMIT i,1;

        update pratica
        set descrizione_intervento = descrizioneevento
        where id_staging = idstaging;
        
		SET i = i + 1;

	END WHILE;
    commit;
END &&
DELIMITER ;   

call popolate_descrizione_evento();

