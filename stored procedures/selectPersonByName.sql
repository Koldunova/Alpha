CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByName`(in inname varchar(50))
BEGIN
select * from alpha.persons where person_name like inname;
END