CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 6, _date = now();
	
	set _output = concat(_output, @newline, 'Altering table service add column firmaOnLine...', @newline);
	
	alter table service add column firmaOnLine int(1) NOT NULL DEFAULT 1;

	set _output = concat(_output, @newline, 'Altering table service add column firmaOffLine...', @newline);
	
	alter table service add column firmaOffLine int(1) NOT NULL DEFAULT 0;

	set foreign_key_checks = 1;

  	set _log = _output;
  
  /*	select _output into outfile 'c:/updateDb.log';*/

	END
