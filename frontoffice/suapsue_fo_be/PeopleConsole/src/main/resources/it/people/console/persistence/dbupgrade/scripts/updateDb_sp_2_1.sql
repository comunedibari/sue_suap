CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';
	declare _engine varchar(64);

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;

	set foreign_key_checks = 0;
	
	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 2, minor_number = 1, _date = now();
	
	set _output = concat(_output, @newline, 'Altering pc_authorities_catalog, adding people admin authority...', @newline);

	insert into pc_authorities_catalog (authority, label, description) values ('PEOPLE_ADMIN', 'Amministratore People', 'Amministratore People');

	set _engine = (select lower(engine) from information_schema.TABLES where table_schema = database() and table_name = 'service');

	set _output = concat(_output, @newline, 'Create pc_users_allowed_ppladmin_nodes table...', @newline);

	if ((strcmp(_engine, 'innodb') != 0) and (strcmp(_engine, 'pbxt') != 0) and (strcmp(_engine, 'soliddb') != 0)) then
		CREATE TABLE pc_users_allowed_ppladmin_nodes (
		  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
		  userRef int(10) unsigned NOT NULL,
		  nodeId int(11) NOT NULL,
		  PRIMARY KEY (id),
		  KEY FK_pc_users_allowed_ppladmin_nodes (userRef)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	else
		CREATE TABLE pc_users_allowed_ppladmin_nodes (
		  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
		  userRef int(10) unsigned NOT NULL,
		  nodeId int(11) NOT NULL,
		  PRIMARY KEY (id),
		  KEY FK_pc_users_allowed_ppladmin_nodes (userRef),
		  CONSTRAINT FK_pc_users_allowed_ppladmin_nodes FOREIGN KEY (userRef) REFERENCES pc_users (id)
		) DEFAULT CHARSET=utf8;
	end if;
	
	set _output = concat(_output, @newline, 'Altering pc_users, adding email_receiver_types_flags column...', @newline);
	alter table pc_users add column email_receiver_types_flags varchar(10) null;

 	set foreign_key_checks = 1;
  
 	set _log = _output;
 	  
END