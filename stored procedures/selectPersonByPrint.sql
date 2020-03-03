CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByPrint`(in inprint varchar(50))
BEGIN
Select persons.*, prints.title from persons
inner join prints on prints.id_person=persons.id_person
where id_print = inprint;
END