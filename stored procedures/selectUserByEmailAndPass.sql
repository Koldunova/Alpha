CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmailAndPass`(in inemail varchar(50), in inpass varchar(50))
BEGIN
 select * from alpha.users 
 where email like inemail 
 and `password` like MD5(inpass);
END