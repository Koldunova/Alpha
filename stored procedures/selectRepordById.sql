CREATE DEFINER=`root`@`%` PROCEDURE `selectRepordById`(in inid int(11))
BEGIN
select reports.document,persons.person_name from alpha.reports
inner join persons on persons.id_person=reports.id_person
where id_report = inid;
END