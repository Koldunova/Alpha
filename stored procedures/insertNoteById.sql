CREATE DEFINER=`root`@`%` PROCEDURE `insertNoteById`(in intitle varchar(50), in intext varchar(50), in inid int(11))
BEGIN
INSERT INTO `alpha`.`notes` (`title`, `text_note`, `id_user`) VALUES (intitle, intext, inid);
END