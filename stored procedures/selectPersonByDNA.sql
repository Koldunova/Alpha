CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByDNA`(in inDNA int(11))
BEGIN
select * from alpha.persons where id_person = inDNA;
END