CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 4, _date = now();
	
	set _output = concat(_output, @newline, 'Altering table service add column embedattachment...', @newline);
	set _output = concat(_output, @newline, 'Altering table service add column showprivacydisclaimer...', @newline);
	set _output = concat(_output, @newline, 'Altering table service add column privacydisclaimerrequireacceptance...', @newline);
	
	alter table service 
		add column embedattachmentinxml int(1) DEFAULT 1 NOT NULL after sendmailtoowner,
  		add column showprivacydisclaimer INT(1) DEFAULT 0 NOT NULL AFTER embedAttachmentInXml,
  		add column privacydisclaimerrequireacceptance INT(1) DEFAULT 0 NOT NULL AFTER showprivacydisclaimer;

	set _output = concat(_output, @newline, 'Altering table fenode add column announcementMessage...', @newline);
	set _output = concat(_output, @newline, 'Altering table fenode add column showAnnouncement...', @newline);
	
	alter table fenode   
  		add column announcementMessage TEXT(5000) NULL AFTER aooprefix,
  		add column showAnnouncement INT(1) DEFAULT 0 NOT NULL AFTER announcementMessage;
	
	set foreign_key_checks = 1;
	
  	set _log = _output;
  
  /*	select _output into outfile 'c:/updateDb.log';*/

	END
