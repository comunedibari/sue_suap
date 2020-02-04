CREATE PROCEDURE updateDb(out _log text)
BEGIN

	declare _output text default '';

	declare continue handler for 1050 begin end;
	declare continue handler for 1060 begin end;


	set foreign_key_checks = 0;

	set @newline = char(13);

	set _output = concat(_output, @newline, 'Updating version table...', @newline);
	
	update _version set major_number = 1, minor_number = 2, _date = now();
	
	set _output = concat(_output, @newline, 'Create pc_languages_codes table...', @newline);
	CREATE TABLE pc_languages_codes (iso_code varchar(6) NOT NULL, _name varchar(400) NOT NULL, 
		PRIMARY KEY (iso_code)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	set _output = concat(_output, @newline, 'Populating pc_states table...', @newline);
	insert  into pc_languages_codes(iso_code, _name) values ('aa','Afar'),('ab','Abkhazian'),
		('ae','Avestan'),('af','Afrikaans'),('ak','Akan'),('am','Amharic'),('an','Aragonese'),
		('ar','Arabic'),('as','Assamese'),('av','Avaric'),('ay','Aymara'),('az','Azerbaijani'),
		('ba','Bashkir'),('be','Belarusian'),('bg','Bulgarian'),('bh','Bihari languages'),
		('bi','Bislama'),('bm','Bambara'),('bn','Bengali'),('bo','Tibetan'),('br','Breton'),
		('bs','Bosnian'),('ca','Catalan'),('ce','Chechen'),('ch','Chamorro'),('co','Corsican'),
		('cr','Cree'),('cs','Czech'),('cu','Church Slavic'),('cv','Chuvash'),('cy','Welsh'),
		('da','Danish'),('de','German'),('dv','Divehi'),('dz','Dzongkha'),('ee','Ewe'),
		('el','Greek, Modern (1453-)'),('en','English'),('eo','Esperanto'),('es','Spanish'),
		('et','Estonian'),('eu','Basque'),('fa','Persian'),('ff','Fulah'),('fi','Finnish'),
		('fj','Fijian'),('fo','Faroese'),('fr','French'),('fy','Western Frisian'),('ga','Irish'),
		('gd','Gaelic'),('gl','Galician'),('gn','Guarani'),('gu','Gujarati'),('gv','Manx'),
		('ha','Hausa'),('he','Hebrew'),('hi','Hindi'),('ho','Hiri Motu'),('hr','Croatian'),
		('ht','Haitian'),('hu','Hungarian'),('hy','Armenian'),('hz','Herero'),('ia','Interlingua'),
		('id','Indonesian'),('ie','Interlingue'),('ig','Igbo'),('ii','Sichuan Yi'),('ik','Inupiaq'),
		('io','Ido'),('is','Icelandic'),('it','Italian'),('iu','Inuktitut'),('ja','Japanese'),
		('jv','Javanese'),('ka','Georgian'),('kg','Kongo'),('ki','Kikuyu'),('kj','Kuanyama'),
		('kk','Kazakh'),('kl','Kalaallisut'),('km','Central Khmer'),('kn','Kannada'),('ko','Korean'),
		('kr','Kanuri'),('ks','Kashmiri'),('ku','Kurdish'),('kv','Komi'),('kw','Cornish'),
		('ky','Kirghiz'),('la','Latin'),('lb','Luxembourgish'),('lg','Ganda'),('li','Limburgan'),
		('ln','Lingala'),('lo','Lao'),('lt','Lithuanian'),('lu','Luba-Katanga'),('lv','Latvian'),
		('mg','Malagasy'),('mh','Marshallese'),('mi','Maori'),('mk','Macedonian'),('ml','Malayalam'),
		('mn','Mongolian'),('mr','Marathi'),('ms','Malay'),('mt','Maltese'),('my','Burmese'),
		('na','Nauru'),('nb','BokmÃ¥l, Norwegian'),('nd','Ndebele, North'),('ne','Nepali'),
		('ng','Ndonga'),('nl','Dutch'),('nn','Norwegian Nynorsk'),('no','Norwegian'),
		('nr','Ndebele, South'),('nv','Navajo'),('ny','Chichewa'),('oc','Occitan (post 1500)'),
		('oj','Ojibwa'),('om','Oromo'),('or','Oriya'),('os','Ossetian'),('pa','Panjabi'),('pi','Pali'),
		('pl','Polish'),('ps','Pushto'),('pt','Portuguese'),('qu','Quechua'),('rm','Romansh'),
		('rn','Rundi'),('ro','Romanian'),('ru','Russian'),('rw','Kinyarwanda'),('sa','Sanskrit'),
		('sc','Sardinian'),('sd','Sindhi'),('se','Northern Sami'),('sg','Sango'),('si','Sinhala'),
		('sk','Slovak'),('sl','Slovenian'),('sm','Samoan'),('sn','Shona'),('so','Somali'),('sq','Albanian'),
		('sr','Serbian'),('ss','Swati'),('st','Sotho, Southern'),('su','Sundanese'),('sv','Swedish'),
		('sw','Swahili'),('ta','Tamil'),('te','Telugu'),('tg','Tajik'),('th','Thai'),('ti','Tigrinya'),
		('tk','Turkmen'),('tl','Tagalog'),('tn','Tswana'),('to','Tonga (Tonga Islands)'),('tr','Turkish'),
		('ts','Tsonga'),('tt','Tatar'),('tw','Twi'),('ty','Tahitian'),('ug','Uighur'),('uk','Ukrainian'),
		('ur','Urdu'),('uz','Uzbek'),('ve','Venda'),('vi','Vietnamese'),('vo','VolapÃ¼k'),('wa','Walloon'),
		('wo','Wolof'),('xh','Xhosa'),('yi','Yiddish'),('yo','Yoruba'),('za','Zhuang'),('zh','Chinese'),('zu','nameZulu');
	
	set foreign_key_checks = 1;
	
	set _log = _output;

/*	select _output into outfile 'c:/updateDb.log';*/

    END