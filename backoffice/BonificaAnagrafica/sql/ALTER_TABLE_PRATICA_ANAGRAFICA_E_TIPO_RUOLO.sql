ALTER TABLE `opencross`.`pratica_anagrafica`   
  ADD COLUMN `id_etichetta` INT(11) DEFAULT 0 NOT NULL AFTER `flg_ditta_individuale`;
  
ALTER TABLE `opencross`.`lk_tipo_ruolo`   
  ADD COLUMN `hidden` tinyint(11) DEFAULT 0 NOT NULL AFTER `cod_ruolo`;
  