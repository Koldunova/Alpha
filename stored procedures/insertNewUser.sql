CREATE DEFINER=`root`@`%` PROCEDURE `insertNewUser`(in inlogin varchar(50), in inpass varchar(50))
BEGIN
INSERT INTO `alpha`.`users` (`email`, `password`) VALUES (inlogin, inpass);
END