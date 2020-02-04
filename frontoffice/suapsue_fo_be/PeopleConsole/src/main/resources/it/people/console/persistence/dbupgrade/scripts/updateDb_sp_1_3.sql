CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 3, _date = now();
	
	set _output = concat(_output, @newline, 'Create pc_users_preferences table...', @newline);
	CREATE TABLE pc_users_preferences (id int(10) unsigned NOT NULL AUTO_INCREMENT, userRef int(11) NOT NULL, 
		_key varchar(100) NOT NULL, _value varchar(100) NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set foreign_key_checks = 1;

	set _log = _output;

/*	select _output into outfile 'c:/updateDb.log';*/

    END