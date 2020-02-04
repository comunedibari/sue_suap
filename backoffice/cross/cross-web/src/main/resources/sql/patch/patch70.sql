CREATE TABLE `pratiche_diritti_segreteria` (
  `id_pratica` INT(11) NOT NULL,
  `urb_pagamento_unica_soluzione` VARCHAR(255) NULL,
  `urb_totale` VARCHAR(255) NULL,
  `urb_banca` VARCHAR(255) NULL,
  `urb_data` VARCHAR(255) NULL,
  `urb_importo` VARCHAR(255) NULL,
  `urb_ratauno_importo` VARCHAR(255) NULL,
  `urb_ratauno_data_prevista` VARCHAR(255) NULL,
  `urb_ratauno_data` VARCHAR(255) NULL,
  `urb_ratadue_importo` VARCHAR(255) NULL,
  `urb_ratadue_data_prevista` VARCHAR(255) NULL,
  `urb_ratadue_data` VARCHAR(255) NULL,
  `urb_ratatre_importo` VARCHAR(255) NULL,
  `urb_ratatre_data_prevista` VARCHAR(255) NULL,
  `urb_ratatre_data` VARCHAR(255) NULL,
  `con_totale` VARCHAR(255) NULL,
  `con_banca` VARCHAR(255) NULL,
  `con_data` VARCHAR(255) NULL,
  `con_importo` VARCHAR(255) NULL,
  `con_ratauno_importo` VARCHAR(255) NULL,
  `con_ratauno_data_prevista` VARCHAR(255) NULL,
  `con_ratauno_data` VARCHAR(255) NULL,
  `con_ratadue_importo` VARCHAR(255) NULL,
  `con_ratadue_data_prevista` VARCHAR(255) NULL,
  `con_ratadue_data` VARCHAR(255) NULL,
  `tar_importo_dovuto` VARCHAR(255) NULL,
  `tar_importo_pagato` VARCHAR(255) NULL,
  `tar_importo_conguaglio` VARCHAR(255) NULL,
  PRIMARY KEY (`id_pratica`));

ALTER TABLE `pratiche_diritti_segreteria` 
ADD CONSTRAINT `pratiche_diritti_pratica`
  FOREIGN KEY (`id_pratica`)
  REFERENCES `pratica` (`id_pratica`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
