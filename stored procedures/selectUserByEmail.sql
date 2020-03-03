CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmail`(IN `inemail` VARCHAR(11))
BEGIN
select * from alpha.users where email = inemail;
END