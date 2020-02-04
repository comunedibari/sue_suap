SELECT 
	processi_steps.tipo_operazione, 
    processi_steps.id_evento_trigger, 
    processi_steps.id_evento_result 
FROM 
	processi_eventi processi_eventi, 
    pratiche_eventi pratiche_eventi, 
    pratica pratica, 
    procedimenti_enti procedimenti_enti, 
    processi processi, 
    processi_eventi processi_eventi_1, 
    processi_steps processi_steps 
WHERE 
	( -- Parentesi 1
		((pratica.id_pratica = ?) AND (processi_eventi.id_evento = processi_eventi_1.id_evento)) 
	AND ( -- Parentesi 2
			( -- Parentesi 3
				(  -- Parentesi 4 
					( -- Parentesi 5 
						( -- Parentesi 6 
							(pratiche_eventi.id_pratica = pratica.id_pratica) AND (procedimenti_enti.id_proc_ente = pratica.id_proc_ente)
						) -- Fine parentesi 6 
						AND (processi.id_processo = procedimenti_enti.id_processo)
					) -- Fine parentesi 5 
					AND (processi_eventi.id_evento = pratiche_eventi.id_evento)
				) -- Fine parentesi 4 
				AND (processi_eventi_1.id_processo = processi.id_processo)
			) -- Fine parentesi 3  
			AND (processi_steps.id_evento_trigger = processi_eventi_1.id_evento)
		) -- Fine parentesi 2
	) -- Fine  parentesi 1
ORDER BY 
	pratiche_eventi.id_pratica_evento ASC