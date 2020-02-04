CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 1, _date = now();
	
	set _output = concat(_output, @newline, 'Create pc_states table...', @newline);
	CREATE TABLE pc_states (id int(10) unsigned NOT NULL AUTO_INCREMENT, name varchar(50) NOT NULL,
	  label varchar(100) NOT NULL, PRIMARY KEY (name), UNIQUE KEY id (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set _output = concat(_output, @newline, 'Populating pc_states table...', @newline);
	insert  into pc_states(id,name,label) values (1,'ACTIVE','Attivo'),(3,'DELETED','Eliminato'),
		(2,'DISABLED','Disabilitato');

	set _output = concat(_output, @newline, 'Create pc_authorities_catalog table...', @newline);
	CREATE TABLE pc_authorities_catalog (
	  id int(11) unsigned NOT NULL AUTO_INCREMENT,
	  authority varchar(50) NOT NULL,
	  label varchar(200) NOT NULL,
	  description varchar(500) DEFAULT NULL,
	  state int(1) unsigned NOT NULL DEFAULT '1',
	  PRIMARY KEY (id),
	  UNIQUE KEY authority (authority),
	  KEY FK_pc_authorities_catalog (state),
	  CONSTRAINT FK_pc_authorities_catalog FOREIGN KEY (state) REFERENCES pc_states (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Populating pc_authorities_catalog table...', @newline);
	insert  into pc_authorities_catalog(id,authority,label,description,state) values 
		(1,'CONSOLE_ADMIN','Amministratore console','Amministratore console',1),
		(2,'NODE_ADMIN','Amministratore nodi di f.e.','Amministratore nodi di f.e.',1),
		(3,'FE_SERVICES_ADMIN','Amministratore servizi di f.e.','Amministratore servizi di f.e.',1),
		(4,'BE_SERVICES_ADMIN','Amministratore servizi di b.e.','Amministratore servizi di b.e.',1),
		(5,'SECURITY_LOGS_VIEWER','Visualizzazione logs sicurezza','Visualizzazione logs sicurezza',1),
		(6,'SECURITY_XML_LOGS_VIEWER','Visualizzazione in chiaro degli xml dei logs sicurezza','Visualizzazione in chiaro degli xml dei logs sicurezza',1),
		(7,'STATISTICS_VIEWER','Visualizzazione di dati statistici','Visualizzazione di dati statistici',1),
		(8,'SECURITY_XML_LOGS_ASSIGNER','Gestione del certificati per la visualizzazione dei logs sicurezza','Gestione del certificati per la visualizzazione dei logs sicurezza',1),
		(9,'ADVICES_ADMIN','Amministrazione accreditamenti','Amministrazione accreditamenti',1);
	
	set _output = concat(_output, @newline, 'Create pc_configuration table...', @newline);
	CREATE TABLE pc_configuration (
	  _key varchar(255) NOT NULL,
	  _type enum('Array','Boolean','Byte','Character','Double','Float','Integer','Long','Short','String') DEFAULT NULL,
	  _scope enum('security','mail','general') NOT NULL DEFAULT 'general',
	  _value varchar(300) NOT NULL, 
	  _label varchar(50) not null,
	  _description mediumtext,
	  PRIMARY KEY (_key)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Populating pc_configuration table...', @newline);
	insert  into pc_configuration(_key,_type,_scope,_value,_label,_description) values 
		('certificatesStandardValidity','Integer','security','365','Giorni validita\' certificato','Numero di giorni di validità  standard dei certificati per la visualizzazione in \r\nchiaro degli XML scambiati tra f.e. e b.e.'),
		('mailTransportAuth','Boolean','mail','true','Richiesta autenticazione',NULL),
		('mailTransportFrom','String','mail','people.console@peopleconsole.it','Indirizzo mittente',NULL),
		('mailTransportHost','String','mail','people.console.it','Mail host',NULL),
		('mailTransportPassword','String','mail','xxxx','Password utente',NULL),
		('mailTransportPort','Integer','mail','25','Porta',NULL),
		('mailTransportProtocol','String','mail','smtp','Protocollo',NULL),
		('mailTransportUsername','String','mail','peopleconsole','Nome utente',NULL),
		('mailTransportUsessl','Boolean','mail','false','Utilizza SSL',NULL),
		('mailTransportUsetls','Boolean','mail','false','Utilizza TLS',NULL),
		('rootAndConsoleAdminAlwaysGrantAccess','Boolean','security','true','Accesso incondizionato root e console admins','Indica se l\'accesso debba essere sempre consentito senza restrizioni ai ruoli \r\n\'root\' e \'console_admin\'.'),
		('rootPasswordMinLength','Integer','security','8','Lunghezza minima password','Lunghezza minima delle password.');
	
	set _output = concat(_output, @newline, 'Create pc_log_categories_levels table...', @newline);
	CREATE TABLE pc_log_categories_levels (
	  _category varchar(200) NOT NULL,
	  _level enum('ALL','INFO','WARN','ERROR','FATAL','DEBUG') DEFAULT NULL,
	  PRIMARY KEY (_category)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Populating pc_log_categories_levels table...', @newline);
	insert  into pc_log_categories_levels(_category,_level) values 
		('it.people.console.config.ConsoleConfiguration','DEBUG'),
		('it.people.console.java.util.PagedListHoldersTreeMap','INFO'),
		('it.people.console.persistence.dbupgrade.DbUpgradeManager','DEBUG'),
		('it.people.console.persistence.jdbc.core.EditableRowColumnMapRowMapper','INFO'),
		('it.people.console.persistence.jdbc.core.RowMapperResultSetExtractorWithMetaData','INFO'),
		('it.people.console.security.auth.AccessDecisionManager','DEBUG'),
		('it.people.console.security.auth.AuthenticationManager','DEBUG'),
		('it.people.console.security.auth.AuthenticationProvider','DEBUG'),
		('it.people.console.security.auth.filters.AuthenticationFilter','DEBUG'),
		('it.people.console.security.auth.filters.AuthorizationFilter','DEBUG'),
		('it.people.console.security.auth.handlers.AccessDeniedHandlerImpl','DEBUG'),
		('it.people.console.security.auth.SecurityMetadataSource','DEBUG'),
		('it.people.console.web.controllers','DEBUG'),
		('it.people.console.web.controllers.fenodes.FENodesListAndAddController','DEBUG'),
		('it.people.console.web.servlet.mvc.AbstractListableController','INFO'),
		('it.people.console.web.servlet.tags.form.ListHolderTableTag','INFO'),
		('it.people.console.web.utils.WebUtils','INFO');
	
	set _output = concat(_output, @newline, 'Create pc_security_metadata_urls table...', @newline);
	CREATE TABLE pc_security_metadata_urls (
	  id int(10) unsigned NOT NULL AUTO_INCREMENT,
	  url varchar(250) NOT NULL,
	  method enum('DELETE','GET','HEAD','OPTIONS','POST','PUT','TRACE') DEFAULT NULL,
	  configAttribute varchar(50) NOT NULL,
	  PRIMARY KEY (id),
	  UNIQUE KEY url (url,method,configAttribute)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Populating pc_security_metadata_urls table...', @newline);
	insert  into pc_security_metadata_urls(id,url,method,configAttribute) values 
		(1,'/Amministrazione/paginaPrincipale.do',NULL,'CONSOLE_ADMIN, ROOT');
	
	set _output = concat(_output, @newline, 'Create pc_users table...', @newline);
	CREATE TABLE pc_users (
	  id int(11) unsigned NOT NULL AUTO_INCREMENT,
	  first_name varchar(40) DEFAULT NULL,
	  last_name varchar(40) DEFAULT NULL,
	  tax_code varchar(16) CHARACTER SET ascii NOT NULL,
	  email varchar(100) DEFAULT ' ',
	  description mediumtext,
	  state int(1) unsigned NOT NULL DEFAULT '1',
	  PRIMARY KEY (id),
	  UNIQUE KEY tax_code (tax_code),
	  KEY FK_pc_users (state),
	  CONSTRAINT FK_pc_users FOREIGN KEY (state) REFERENCES pc_states (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Create pc_users_allowed_beservices table...', @newline);
	CREATE TABLE pc_users_allowed_beservices (
	  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	  userRef int(10) unsigned NOT NULL,
	  serviceId int(11) NOT NULL,
	  PRIMARY KEY (id),
	  KEY FK_pc_users_allowed_beservices (userRef),
	  CONSTRAINT FK_pc_users_allowed_beservices FOREIGN KEY (userRef) REFERENCES pc_users (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
	set _output = concat(_output, @newline, 'Create pc_users_allowed_feservices table...', @newline);
	CREATE TABLE pc_users_allowed_feservices (
	  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	  userRef int(10) unsigned NOT NULL,
	  serviceId int(11) NOT NULL,
	  PRIMARY KEY (id),
	  KEY FK_pc_users_allowed_feservices (userRef),
	  CONSTRAINT FK_pc_users_allowed_feservices FOREIGN KEY (userRef) REFERENCES pc_users (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Create pc_users_allowed_nodes table...', @newline);
	CREATE TABLE pc_users_allowed_nodes (
	  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	  userRef int(10) unsigned NOT NULL,
	  nodeId int(11) NOT NULL,
	  PRIMARY KEY (id),
	  KEY FK_pc_users_allowed_nodes (userRef),
	  CONSTRAINT FK_pc_users_allowed_nodes FOREIGN KEY (userRef) REFERENCES pc_users (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Create pc_users_authorities table...', @newline);
	CREATE TABLE pc_users_authorities (
	  id int(10) unsigned NOT NULL AUTO_INCREMENT,
	  userRef int(10) unsigned NOT NULL,
	  authorityRef int(11) unsigned NOT NULL,
	  PRIMARY KEY (id),
	  UNIQUE KEY userRef (userRef,authorityRef),
	  KEY authorityRef (authorityRef),
	  CONSTRAINT FK_pc_users_authorities FOREIGN KEY (userRef) REFERENCES pc_users (id),
	  CONSTRAINT FK_pc_users_authorities_catalog FOREIGN KEY (authorityRef) REFERENCES pc_authorities_catalog (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	set _output = concat(_output, @newline, 'Create pc_users_certificates table...', @newline);
	CREATE TABLE pc_users_certificates (
	  id int(10) unsigned NOT NULL AUTO_INCREMENT,
	  userRef int(10) unsigned NOT NULL,
	  alias varchar(50) NOT NULL,
	  validity int(10) unsigned NOT NULL,
	  generationTimestamp timestamp NULL DEFAULT NULL,
	  certificate longblob,
	  sentMail int(1) NOT NULL DEFAULT '0',
	  PRIMARY KEY (id),
	  KEY FK_pc_users_certificates (userRef),
	  CONSTRAINT FK_pc_users_certificates FOREIGN KEY (userRef) REFERENCES pc_users (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set foreign_key_checks = 1;

	set _log = _output;

/*	select _output into outfile 'c:/updateDb.log';*/

    END