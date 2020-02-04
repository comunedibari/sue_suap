CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 8, _date = now();
	
	set _output = concat(_output, @newline, 'Creating Table tablevalues and tablevalues_properties...', @newline);

  CREATE TABLE tablevalues(  
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    tableId VARCHAR(250) NOT NULL,
    processName VARCHAR(250) NOT NULL,
    nodeId VARCHAR(15),
    locale VARCHAR(6),
    charset VARCHAR(50),
    PRIMARY KEY (id),
    UNIQUE INDEX unique_tablevalues (tableId, processName (250), nodeId, locale)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  CREATE TABLE tablevalues_properties(  
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    value TEXT NOT NULL,
    tablevalueRef INT(10) UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX value (value (250), tablevalueRef),
    INDEX fk_tablevalues_id (tablevalueRef)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  set _output = concat(_output, @newline, 'Adding FK constraints..', @newline);
  
  ALTER TABLE `tablevalues_properties`  
  ADD CONSTRAINT `FK_tablevalues_properties` FOREIGN KEY (`tablevalueRef`) REFERENCES `tablevalues`(`id`);

	set foreign_key_checks = 1;

  set _log = _output;
  
	END
