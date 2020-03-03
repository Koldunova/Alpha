CREATE DEFINER=`root`@`%` PROCEDURE `updateUserPassword`(in iniduser int(11),in inpass varchar(45))
BEGIN
UPDATE `alpha`.`users` SET `password` = inpass WHERE (`id_user` = iniduser);
END