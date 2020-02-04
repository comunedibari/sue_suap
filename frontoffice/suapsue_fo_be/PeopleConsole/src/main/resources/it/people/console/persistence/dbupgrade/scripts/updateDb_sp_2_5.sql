CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;

	set foreign_key_checks = 0;
	
	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 2, minor_number = 5, _date = now();
	
	set _output = concat(_output, @newline, 'Updating showprivacydisclaimer default to 0...', @newline);

	ALTER TABLE `service` CHANGE `showprivacydisclaimer` `showprivacydisclaimer` INT(1) NOT NULL DEFAULT '0';
	
	set _output = concat(_output, @newline, 'service.showprivacydisclaimer field Default updated.', @newline);
  
	set foreign_key_checks = 1;
  
	set _log = _output;
  
END