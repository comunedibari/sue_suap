CREATE PROCEDURE updateDb(out _log longtext)
BEGIN

	DECLARE loop_cntr INT DEFAULT 0;

	declare foundBeNode int(1);
	declare alreadyRegisteredBeNode int(1);

	declare _nodobe varchar(200);
	declare _reference varchar(255);
	declare _useenvelope int(1);
	declare _disablecheckdelegate int(1);

	declare _serviceId int(4);
	declare _nodeid int(11);
	declare _refName varchar(255);
	declare _refValue varchar(255);

	declare _output longtext default '';

	DECLARE done INT DEFAULT 0;
	DECLARE benode_cursor CURSOR FOR SELECT nodobe, reference, useenvelope, disablecheckdelegate FROM benode;
	DECLARE services_cursor CURSOR FOR select srvc.id, srvc.nodeid, ref.name, ref.value from service srvc, reference ref 
		where ref.serviceid = srvc.id;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	CREATE TABLE _version (major_number int(11) DEFAULT NULL, minor_number int(11) DEFAULT NULL, 
		_date timestamp NULL DEFAULT NULL);

	insert into _version(major_number, minor_number, _date) values(1, 0, now());

	drop table if exists benode_tmp;
	
	create temporary table benode_tmp (nodobe varchar(200), reference varchar(255), useenvelope int(1), disablecheckdelegate int(1));

	/* Read benode services configuration and store them in tmp table before cleaning benode table */
	set _output = concat(_output, 'Reading benode services configuration and store them in tmp table before cleaning benode table...', @newline, @newline);
	set loop_cntr = 0;
	OPEN benode_cursor;
	read_loop: LOOP
		FETCH benode_cursor INTO _nodobe, _reference, _useenvelope, _disablecheckdelegate;
		IF done THEN
			LEAVE read_loop;
		END IF;
		insert into benode_tmp(nodobe, reference, useenvelope, disablecheckdelegate) values(_nodobe, _reference, _useenvelope, _disablecheckdelegate);
		set loop_cntr = loop_cntr + 1;
		set _output = concat(_output, _nodobe, ' ', _reference, ' ', _useenvelope, ' ', _disablecheckdelegate, @newline);
	END LOOP;
	CLOSE benode_cursor;
	set _output = concat(_output, @newline, 'Read ', loop_cntr, ' be nodes', @newline, @newline);

	/* Clear benode table */
	set _output = concat(_output, 'Clearing benode table...', @newline, @newline);
	truncate table benode;

	/* Upgrading benode table */
	set _output = concat(_output, 'Upgrading benode table...', @newline, @newline);
	alter table benode add column nodeid int(11) NOT NULL after id;

	/* Read all services nodeid, name and reference value */
	set _output = concat(_output, 'Reading all services nodeid, name and reference value...', @newline, @newline);
	set done = 0;
	set loop_cntr = 0;
	OPEN services_cursor;
	read_loop: LOOP
		FETCH services_cursor INTO _serviceId, _nodeid, _refName, _refValue;
		IF done THEN
			LEAVE read_loop;
		END IF;

		select count(*) into foundBeNode from benode_tmp where nodobe = _refValue;
		if (_refValue is not null and _refValue != 'UNDEFINED' and _refValue != '' and foundBeNode = 1) then
			select nodobe, reference, useenvelope, disablecheckdelegate into _nodobe, _reference, _useenvelope, _disablecheckdelegate 
				from benode_tmp where nodobe = _refValue;

			select count(*) into alreadyRegisteredBeNode from benode where nodeid = _nodeId and 
				nodobe = _refValue;

			set loop_cntr = loop_cntr + 1;
			set _output = concat(_output, 'Service data: ', _serviceId, ' ', _nodeId, ' ', _refName, ' ', _refValue, @newline);
			set _output = concat(_output, 'Benode tmp data: ', _nodobe, ' ', _reference, ' ', _useenvelope, ' ', _disablecheckdelegate, @newline);

			if (alreadyRegisteredBeNode = 0) then
				insert into benode(nodeid, nodobe, reference, useenvelope, disablecheckdelegate) 
					values(_nodeid, _nodobe, _reference, _useenvelope, _disablecheckdelegate);
			else
				set _output = concat(_output, 'ALREADY REGISTERED BE NODE... SKIPPING... ', @newline);
			end if;

		end if;
	END LOOP;
	CLOSE services_cursor;
	set _output = concat(_output, @newline, 'Read ', loop_cntr, ' services data', @newline);

	set foreign_key_checks = 1;
	
	set _log = _output;

/*	select _output into outfile 'c:/updateDb.log';*/
	drop table benode_tmp;

    END