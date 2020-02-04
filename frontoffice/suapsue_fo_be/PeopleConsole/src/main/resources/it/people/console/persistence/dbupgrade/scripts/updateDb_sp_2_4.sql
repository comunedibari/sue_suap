CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;

	set foreign_key_checks = 0;
	
	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 2, minor_number = 4, _date = now();
	
	set _output = concat(_output, @newline, 'Adding velocity_templates table...', @newline);

	CREATE TABLE velocity_templates (
	  _communeId varchar(6) DEFAULT NULL,
	  _serviceId int(4) DEFAULT NULL,
	  _key varchar(150) NOT NULL,
	  _value text NOT NULL,
	  _description varchar(3000) DEFAULT NULL,
	  PRIMARY KEY (_key),
	  UNIQUE KEY IDX_commune_service_key (_communeId,_serviceId,_key)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set _output = concat(_output, @newline, 'Velocity_templates table added.', @newline);
  
  set foreign_key_checks = 1;
  
  set _log = _output;
  
END