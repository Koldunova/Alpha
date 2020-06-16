CREATE DATABASE `alpha` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `activate_game`(in iduser int(11), in ingame int(11))
BEGIN
	UPDATE `alpha`.`realised_games` SET `id_user` = iduser WHERE (`id_game` = ingame	);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `addCodeForCapture`(in inuser varchar(45), in incode int(11))
BEGIN
	declare line varchar(45) default "";	
    declare newline varchar(45) default "";	
    declare ind int default -1;
    
    set line =(SELECT ending_capture From users where id_user=inuser);
    set ind = (SELECT locate(incode,line));



    if ind=0 then
		set newline = concat(line," ",incode);
        UPDATE `alpha`.`users` SET `ending_capture` = newline WHERE (`id_user` = inuser);
    end if;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `changeCountOfMoney`(in inuser int(11), incode int(11))
BEGIN
	DECLARE mon INT default 0;
    DECLARE monUser INT default 0;
    SET mon = (SELECT count_money FROM money WHERE id_money=incode);
    Set monUser=(SELECT money FROM users WHERE id_user=inuser);
    if monUser+mon>=0 then
		UPDATE `users` SET `money` = `money`+mon WHERE (`id_user` = inuser);
    end if;
    Select money,'money' as `type` FROM users WHERE id_user=inuser;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `deleteNoteById`(in inid int(11))
BEGIN
DELETE FROM `alpha`.`notes` WHERE (`id_note` = inid);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `insertBuy`(in userName varchar(45), in email varchar(45), in mes varchar(1000))
BEGIN
INSERT INTO `alpha`.`purchase_buy` (`userName`, `email`, `mes`) VALUES (userName, email, mes);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `insertNewUser`(in inlogin varchar(50), in inpass varchar(50))
BEGIN
INSERT INTO `alpha`.`users` (`email`, `password`,`money`) VALUES (inlogin, inpass,30);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `insertNoteById`(in intitle varchar(50), in intext varchar(50), in inid int(11))
BEGIN
INSERT INTO `alpha`.`notes` (`title`, `text_note`, `id_user`) VALUES (intitle, intext, inid);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `isCompliteEndingCapture`(in inuser int(11) , out outres varchar(5))
BEGIN
	declare love int default -1;
    declare confl int default -1;
    declare prok int default -1;
    declare line varchar(45) default -1;
    declare res int default 0;
    
    set line = (SELECT ending_capture FROM users WHERE id_user = inuser);
    
    set love = (Select locate('111',line));
    set confl = (Select locate('682',line));
    set prok = (Select locate('420',line));
    
    if love >0 then
		set res = res +1;
	end if;
    
    if confl >0 then
    	set res = res +1;
	end if;
    
    if prok >0 then
    	set res = res +1;
	end if;
	   
    
    if res = 3 then
		set outres = "true";
	else
		set outres = "false";
	end if;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectAlertById`(in inid int(11))
BEGIN
	Select `text`,'alert' as `type` FROM alerts WHERE id_alert=inid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectExamByIdExam`(in inid int(11))
BEGIN
select examination.*,persons.person_name,'exam' as `type` from alpha.examination
inner join persons on persons.id_person=examination.id_person
 where id_exam = inid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectGameById`(in inid int(11))
BEGIN
	select link,'game' as `type` FROM mini_games where id_game=inid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectNoteByIdUser`(in inid int(11))
BEGIN
select * from alpha.notes where id_user = inid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByDNA`(in inDNA int(11))
BEGIN
select * from alpha.persons where id_person = inDNA;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByName`(in inname varchar(50))
BEGIN
select * from alpha.persons where lower(person_name) like lower(inname);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByPrint`(in inprint varchar(50))
BEGIN
Select persons.* from prints
inner join persons on prints.id_person=persons.id_person
where prints.`code` = inprint;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectReportById`(in inid int(11))
BEGIN
select reports.document,persons.person_name, 'report' as `type` from alpha.reports
inner join persons on persons.id_person=reports.id_person
where id_report = inid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmail`(IN `inemail` VARCHAR(100))
BEGIN
select * from alpha.users where email like inemail;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmailAndPass`(in inemail varchar(50), in inpass varchar(50))
BEGIN
 select * from alpha.users 
 where email like inemail 
 and `password` like MD5(inpass);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `switchTypeCodeAndNextOperation`(in iduser int(11), in incode int(11))
BEGIN
	declare t varchar(45) default "";
	declare num_q int(11) default null;
	declare count_act int (11) default 0;


#	SET num_q =(SELECT num_quest FROM alpha.codes);
#   if (num_q is not null) then
#		SET count_act = (SELECT * FROM alpha.realised_games where 
#        num_quest=num_q and id_user=iduser);
#        if (count_act=1) then
            SET t = (select `types`.`type_name` 
			from codes
			inner join `types` on `types`.`id_type`=`codes`.`id_type`
			where `codes`.`code`=incode limit 1);

			if t="money" then
				call changeCountOfMoney(iduser,incode);
			end if;

			if t="alert" then
				call selectAlertById(incode);
			end if;
			
			if t="game" then
				call selectGameById(incode);
			end if;

			if t="report" then
				call selectReportById(incode);
			end if;
			
			if t="examination" then
				call selectExamByIdExam(incode);
			end if;
			
			if t="ending_capture" then
				call whatKindOfEndingEnabled(iduser);
			end if;
            
            if t="exam_capture" then
                call addCodeForCapture(iduser,incode);
				call selectExamByIdExam(incode);
			end if;
 #       end if;
 #   end if; 
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `updateNoteById`(in inid int(11), in intitle varchar(20), in intext varchar(80))
BEGIN
UPDATE `alpha`.`notes` 
SET `title` = intitle, `text_note` = intext 
WHERE (`id_note` = inid);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `updateUserPassword`(in iniduser int(11),in inpass varchar(45))
BEGIN
UPDATE `alpha`.`users` SET `password` = inpass WHERE (`id_user` = iniduser);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `whatKindOfEndingEnabled`(in inuser int (11))
BEGIN
	declare capture varchar(5) default "false";
    declare mon int(11) default 0;
    
    call alpha.isCompliteEndingCapture(inuser, capture);
	set mon = (select money from users where id_user = inuser);
    
    if capture="true" and mon>=14 then
		if mon>=15 then
			Select `text`,'alert' as `type` From alerts WHERE id_alert =7024;
		else
			Select `text`,'alert' as `type` From alerts WHERE id_alert =7021;
		end if;
	else
		if mon>=15 then
			Select `text`,'alert' as `type` From alerts WHERE id_alert =7022;
		else
			Select `text`,'alert' as `type` From alerts WHERE id_alert =7023;
		end if;
	end if;
END$$
DELIMITER ;
