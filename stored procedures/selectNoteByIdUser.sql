CREATE DEFINER=`root`@`%` PROCEDURE `selectNoteByIdUser`(in inid int(11))
BEGIN
select * from alpha.notes where id_user = inid;
END