ALTER TABLE `indirizzi_intervento` CHANGE COLUMN `descrizione_comune` `des_comune` VARCHAR(255) NULL DEFAULT NULL  ;

ALTER TABLE `staging` ADD COLUMN `identificativo_provenienza` VARCHAR(255) NULL ;

update staging set identificativo_provenienza = substring(xml_ricevuto,instr(xml_ricevuto,'<ogg:CodiceAmministrazione>') + 27,
instr(xml_ricevuto,'</ogg:CodiceAmministrazione>')-instr(xml_ricevuto,'<ogg:CodiceAmministrazione>') - 27);