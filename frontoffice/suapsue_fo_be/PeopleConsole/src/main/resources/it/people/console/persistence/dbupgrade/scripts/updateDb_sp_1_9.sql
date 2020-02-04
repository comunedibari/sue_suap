CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 9, _date = now();
	
	set _output = concat(_output, @newline, 'Creating Table pc_audit_processors and service_audit_processors...', @newline);

	CREATE TABLE service_required_user_data (
	  id int unsigned NOT NULL AUTO_INCREMENT,
	  serviceid int(4) unsigned NOT NULL DEFAULT '0',
	  userdata varchar(20) NOT NULL,
	  PRIMARY KEY (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
	
	CREATE TABLE pc_audit_processors(  
	  id INT(4) NOT NULL AUTO_INCREMENT,
	  auditProcessor VARCHAR(255) NOT NULL,
	  descrizione MEDIUMTEXT,
	  PRIMARY KEY (id),
	  UNIQUE INDEX unique_audit_processors (auditProcessor)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set _engine = (select lower(engine) from information_schema.TABLES where table_schema = database() and table_name = 'service');
	
	if ((strcmp(_engine, 'innodb') != 0) and (strcmp(_engine, 'pbxt') != 0) and (strcmp(_engine, 'soliddb') != 0)) then
		CREATE TABLE service_audit_processors( 
		  id INT(4) NOT NULL AUTO_INCREMENT,
		  serviceId INT(4) NOT NULL,
		  auditProcessor VARCHAR(255) NOT NULL,
		  active INT(1) DEFAULT 0 NOT NULL,
		  PRIMARY KEY (id),
		  UNIQUE INDEX unique_service_audit_processors (serviceId, auditProcessor)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	else
		CREATE TABLE service_audit_processors( 
		  id INT(4) NOT NULL AUTO_INCREMENT,
		  serviceId INT(4) NOT NULL,
		  auditProcessor VARCHAR(255) NOT NULL,
		  active INT(1) DEFAULT 0 NOT NULL,
		  PRIMARY KEY (id),
		  UNIQUE INDEX unique_service_audit_processors (serviceId, auditProcessor),
		  CONSTRAINT fk_service_id FOREIGN KEY (serviceId) REFERENCES service(id)
		) DEFAULT CHARSET=utf8;
	end if;
		
	
	set _output = concat(_output, @newline, 'Inserting values into pc_audit_processors...', @newline);
	
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.ExitProcessLogoffProcessor','Logout utente');
		
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  
	VALUES ('it.people.action.processor.ConfirmExitProcessProcessor','Conferma dell''uscita dal servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.ExitProcessHomeProcessor','Ritorno alla pagina principale');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.ExitProcessProcessor','Uscita dal sistema');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  
	VALUES ('it.people.action.processor.ExitProcessServiceProcessor','Uscita dal servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.GoActivityProcessProcessor','Cambio attivita''');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.InitProcessProcessor','Inizializzazione del servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  
	VALUES ('it.people.action.processor.InitProcesswithRedirectProcessor','Inizializzazione del servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  
	VALUES ('it.people.action.processor.NextStepProcessProcessor','Passaggio allo step successivo');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.PrevStepProcessProcessor','Passaggio allo step precedente');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  
	VALUES ('it.people.action.processor.SaveProcessProcessor','Salvataggio dello stato del servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.SaveUploadFileProcessor','Caricamento file');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.PostLoadProcessProcessor','Attivazione del servizio');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione) 
	VALUES ('it.people.action.processor.SendProcessProcessor','Invio delle pratiche');
	
	INSERT INTO pc_audit_processors (auditProcessor, descrizione)  

	VALUES ('it.people.backend.client.processor.WebServiceConnectorProcessor','Scambio XML tra f.e. e b.e.');

	set foreign_key_checks = 1;
	
  set _log = _output;
  
	END
