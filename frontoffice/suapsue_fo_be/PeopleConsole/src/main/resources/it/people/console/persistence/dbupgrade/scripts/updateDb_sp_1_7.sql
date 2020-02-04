CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 7, _date = now();
	
	set _output = concat(_output, @newline, 'Altering table fenode add column firmaOnLine...', @newline);
	
	ALTER TABLE fenode ADD COLUMN firmaOnLine INT(1) DEFAULT 1  NOT NULL AFTER showAnnouncement;

	set _output = concat(_output, @newline, 'Altering table fenode add column firmaOffLine...', @newline);
	
	ALTER TABLE fenode ADD COLUMN firmaOffLine INT(1) DEFAULT 0  NOT NULL AFTER firmaOnLine;

	set foreign_key_checks = 1;

  	set _log = _output;
  
  /*	select _output into outfile 'c:/updateDb.log';*/

	END
