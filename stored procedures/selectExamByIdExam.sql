CREATE DEFINER=`root`@`%` PROCEDURE `selectExamByIdExam`(in inid int(11))
BEGIN
select examination.*,persons.person_name from alpha.examination
inner join persons on persons.id_person=examination.id_person
 where id_exam = inid;
END