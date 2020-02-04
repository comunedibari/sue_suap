CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;

	set foreign_key_checks = 0;
	
	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 2, minor_number = 3, _date = now();
	
	set _output = concat(_output, @newline, 'Altering pc_users_allowed_beservices, dropping column serviceid...', @newline);

	alter table pc_users_allowed_beservices drop column serviceid;

	set _output = concat(_output, @newline, 'Altering pc_users_allowed_beservices, adding column service_pkg...', @newline);
	
	alter table pc_users_allowed_beservices add column service_pkg varchar(1000) not null;

	set _output = concat(_output, @newline, 'Altering pc_users_allowed_feservices, dropping serviceid...', @newline);
	
	alter table pc_users_allowed_feservices drop column serviceid;

	set _output = concat(_output, @newline, 'Altering pc_users_allowed_feservices, adding column service_pkg...', @newline);
	
	alter table pc_users_allowed_feservices add column service_pkg varchar(1000) not null;
  
  set foreign_key_checks = 1;
  
  set _log = _output;
  
END