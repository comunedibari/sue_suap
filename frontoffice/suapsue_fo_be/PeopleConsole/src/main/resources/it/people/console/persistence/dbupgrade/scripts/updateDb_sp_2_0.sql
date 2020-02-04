CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;

 	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 2, minor_number = 0, _date = now();
	
	set _output = concat(_output, @newline, 'Altering pc_configuration, adding monitoring scope...', @newline);

	ALTER TABLE pc_configuration CHANGE _scope _scope ENUM('security','mail','general','monitoring') NOT NULL;
  
	set _output = concat(_output, @newline, 'Populating pc_configuration table with monitroing FTP configuration...', @newline);
  
	INSERT INTO pc_configuration (_key, _type, _scope, _value, _label , _description)
		VALUES ('monitoringFTPHost', 'String', 'monitoring', 'ftp.osservatorio.it', 'Host FTP osservatorio','Indirizzo host FTP dell\'osservatorio'),
		('monitoringFTPPort', 'Integer', 'monitoring', 21, 'Porta FTP osservatorio','Porta dell\'host FTP dell\'osservatorio'),
		('monitoringFTPUser', 'String', 'monitoring', 'username', 'Nome utente FTP osservatorio','Nome utente per la connessione FTP dell\'osservatorio'),
		('monitoringFTPPassword', 'String', 'monitoring', 'password', 'Password utente FTP osservatorio','Password utente per la connessione FTP dell\'osservatorio');

 	set foreign_key_checks = 1;
  
	set _log = _output;
  
END