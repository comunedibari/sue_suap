-- 20/11/13 : Modifica per gestire correttamentela data di protocollazione di un evento. In uscita generalmente coincide con la data dell'evento, in ingresso invece può essere molto differente.
ALTER TABLE `pratiche_eventi` ADD COLUMN `data_protocollo` DATE NULL AFTER `protocollo`;
