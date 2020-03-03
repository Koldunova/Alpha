CREATE DEFINER=`root`@`%` PROCEDURE `deleteNoteById`(in inid int(11))
BEGIN
DELETE FROM `alpha`.`notes` WHERE (`id_note` = inid);
END