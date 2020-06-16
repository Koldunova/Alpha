-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Июн 16 2020 г., 19:30
-- Версия сервера: 5.7.30-0ubuntu0.16.04.1
-- Версия PHP: 7.0.33-0ubuntu0.16.04.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `alpha`
--
CREATE DATABASE IF NOT EXISTS `alpha` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `alpha`;

DELIMITER $$
--
-- Процедуры
--
DROP PROCEDURE IF EXISTS `activate_game`$$
CREATE DEFINER=`root`@`%` PROCEDURE `activate_game` (IN `iduser` INT(11), IN `ingame` INT(11))  BEGIN
	UPDATE `alpha`.`realised_games` SET `id_user` = iduser WHERE (`id_game` = ingame	);
END$$

DROP PROCEDURE IF EXISTS `addCodeForCapture`$$
CREATE DEFINER=`root`@`%` PROCEDURE `addCodeForCapture` (IN `inuser` VARCHAR(45), IN `incode` INT(11))  BEGIN
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

DROP PROCEDURE IF EXISTS `changeCountOfMoney`$$
CREATE DEFINER=`root`@`%` PROCEDURE `changeCountOfMoney` (IN `inuser` INT(11), `incode` INT(11))  BEGIN
	DECLARE mon INT default 0;
    DECLARE monUser INT default 0;
    SET mon = (SELECT count_money FROM money WHERE id_money=incode);
    Set monUser=(SELECT money FROM users WHERE id_user=inuser);
    if monUser+mon>=0 then
		UPDATE `users` SET `money` = `money`+mon WHERE (`id_user` = inuser);
    end if;
    Select money,'money' as `type` FROM users WHERE id_user=inuser;
END$$

DROP PROCEDURE IF EXISTS `deleteNoteById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `deleteNoteById` (IN `inid` INT(11))  BEGIN
DELETE FROM `alpha`.`notes` WHERE (`id_note` = inid);
END$$

DROP PROCEDURE IF EXISTS `insertBuy`$$
CREATE DEFINER=`root`@`%` PROCEDURE `insertBuy` (IN `userName` VARCHAR(45), IN `email` VARCHAR(45), IN `mes` VARCHAR(1000))  BEGIN
INSERT INTO `alpha`.`purchase_buy` (`userName`, `email`, `mes`) VALUES (userName, email, mes);
END$$

DROP PROCEDURE IF EXISTS `insertNewUser`$$
CREATE DEFINER=`root`@`%` PROCEDURE `insertNewUser` (IN `inlogin` VARCHAR(50), IN `inpass` VARCHAR(50))  BEGIN
INSERT INTO `alpha`.`users` (`email`, `password`,`money`) VALUES (inlogin, inpass,30);
END$$

DROP PROCEDURE IF EXISTS `insertNoteById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `insertNoteById` (IN `intitle` VARCHAR(50), IN `intext` VARCHAR(50), IN `inid` INT(11))  BEGIN
INSERT INTO `alpha`.`notes` (`title`, `text_note`, `id_user`) VALUES (intitle, intext, inid);
END$$

DROP PROCEDURE IF EXISTS `isCompliteEndingCapture`$$
CREATE DEFINER=`root`@`%` PROCEDURE `isCompliteEndingCapture` (IN `inuser` INT(11), OUT `outres` VARCHAR(5))  BEGIN
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

DROP PROCEDURE IF EXISTS `selectAlertById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectAlertById` (IN `inid` INT(11))  BEGIN
	Select `text`,'alert' as `type` FROM alerts WHERE id_alert=inid;
END$$

DROP PROCEDURE IF EXISTS `selectExamByIdExam`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectExamByIdExam` (IN `inid` INT(11))  BEGIN
select examination.*,persons.person_name,'exam' as `type` from alpha.examination
inner join persons on persons.id_person=examination.id_person
 where id_exam = inid;
END$$

DROP PROCEDURE IF EXISTS `selectGameById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectGameById` (IN `inid` INT(11))  BEGIN
	select link,'game' as `type` FROM mini_games where id_game=inid;
END$$

DROP PROCEDURE IF EXISTS `selectNoteByIdUser`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectNoteByIdUser` (IN `inid` INT(11))  BEGIN
select * from alpha.notes where id_user = inid;
END$$

DROP PROCEDURE IF EXISTS `selectPersonByDNA`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByDNA` (IN `inDNA` INT(11))  BEGIN
select * from alpha.persons where id_person = inDNA;
END$$

DROP PROCEDURE IF EXISTS `selectPersonByName`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByName` (IN `inname` VARCHAR(50))  BEGIN
select * from alpha.persons where lower(person_name) like lower(inname);
END$$

DROP PROCEDURE IF EXISTS `selectPersonByPrint`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectPersonByPrint` (IN `inprint` VARCHAR(50))  BEGIN
Select persons.* from prints
inner join persons on prints.id_person=persons.id_person
where prints.`code` = inprint;
END$$

DROP PROCEDURE IF EXISTS `selectReportById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectReportById` (IN `inid` INT(11))  BEGIN
select reports.document,persons.person_name, 'report' as `type` from alpha.reports
inner join persons on persons.id_person=reports.id_person
where id_report = inid;
END$$

DROP PROCEDURE IF EXISTS `selectUserByEmail`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmail` (IN `inemail` VARCHAR(100))  BEGIN
select * from alpha.users where email like inemail;
END$$

DROP PROCEDURE IF EXISTS `selectUserByEmailAndPass`$$
CREATE DEFINER=`root`@`%` PROCEDURE `selectUserByEmailAndPass` (IN `inemail` VARCHAR(50), IN `inpass` VARCHAR(50))  BEGIN
 select * from alpha.users 
 where email like inemail 
 and `password` like MD5(inpass);
END$$

DROP PROCEDURE IF EXISTS `switchTypeCodeAndNextOperation`$$
CREATE DEFINER=`root`@`%` PROCEDURE `switchTypeCodeAndNextOperation` (IN `iduser` INT(11), IN `incode` INT(11))  BEGIN
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

DROP PROCEDURE IF EXISTS `updateNoteById`$$
CREATE DEFINER=`root`@`%` PROCEDURE `updateNoteById` (IN `inid` INT(11), IN `intitle` VARCHAR(20), IN `intext` VARCHAR(80))  BEGIN
UPDATE `alpha`.`notes` 
SET `title` = intitle, `text_note` = intext 
WHERE (`id_note` = inid);
END$$

DROP PROCEDURE IF EXISTS `updateUserPassword`$$
CREATE DEFINER=`root`@`%` PROCEDURE `updateUserPassword` (IN `iniduser` INT(11), IN `inpass` VARCHAR(45))  BEGIN
UPDATE `alpha`.`users` SET `password` = inpass WHERE (`id_user` = iniduser);
END$$

DROP PROCEDURE IF EXISTS `whatKindOfEndingEnabled`$$
CREATE DEFINER=`root`@`%` PROCEDURE `whatKindOfEndingEnabled` (IN `inuser` INT(11))  BEGIN
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

-- --------------------------------------------------------

--
-- Структура таблицы `alerts`
--

DROP TABLE IF EXISTS `alerts`;
CREATE TABLE `alerts` (
  `id_alert` int(11) NOT NULL,
  `text` varchar(200) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `alerts`
--

INSERT INTO `alerts` (`id_alert`, `text`) VALUES
(1264, 'If you now have action card 3, then you can visit the work of the missing. To do this, take action card 4. (Time: -30 minutes, Money: -2 coins)'),
(7021, 'The endings "Capture" (code 2803), "Sewerage" (code 1221) are available to you.'),
(7022, 'You can find the endings "Diversion" (code 2020), "Sewerage" (code 1221).'),
(7023, 'You can find the endings "Sewerage" (code 1221).'),
(7024, 'The endings "Sabotage" (code 2020), "Sewerage" (code 1221) and "Capture" (code 2803) are available to you.');

-- --------------------------------------------------------

--
-- Структура таблицы `codes`
--

DROP TABLE IF EXISTS `codes`;
CREATE TABLE `codes` (
  `id_code` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  `num_quest` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `codes`
--

INSERT INTO `codes` (`id_code`, `code`, `id_type`, `num_quest`) VALUES
(1, 1264, 2, 1),
(2, 732, 5, 1),
(3, 1994, 4, 1),
(4, 1749, 4, 1),
(5, 1776, 4, 1),
(6, 996, 5, 1),
(7, 358, 4, 1),
(8, 650, 4, 1),
(9, 756, 4, 1),
(10, 100, 5, 1),
(11, 420, 6, 1),
(12, 682, 6, 1),
(13, 111, 6, 1),
(14, 702, 7, 1),
(15, 982, 1, NULL),
(16, 329, 1, NULL),
(17, 415, 1, NULL),
(18, 593, 1, NULL),
(19, 601, 1, NULL),
(20, 776, 1, NULL),
(21, 931, 1, NULL),
(22, 711, 1, NULL),
(23, 32, 5, 1),
(24, 634, 5, 1),
(25, 749, 4, 1),
(26, 947, 4, 1),
(27, 999, 5, 1),
(28, 1048, 5, 1),
(29, 8547, 5, 1),
(30, 1103, 5, 1),
(31, 844, 4, 1),
(32, 1221, 3, 1),
(33, 2803, 3, 1),
(34, 2020, 3, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `examination`
--

DROP TABLE IF EXISTS `examination`;
CREATE TABLE `examination` (
  `id_exam` int(11) NOT NULL,
  `id_person` int(11) NOT NULL,
  `title` varchar(30) COLLATE utf8_bin NOT NULL,
  `description` varchar(10000) COLLATE utf8_bin NOT NULL,
  `audio` varchar(45) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `examination`
--

INSERT INTO `examination` (`id_exam`, `id_person`, `title`, `description`, `audio`) VALUES
(32, 9, '#32', '\n-And so, good afternoon .. So you are Earl Vernon Hold. Yeah, not a lot, not a lot. Well, as you explain, the fact that you led Lady Amelia Wood from the building of Baron Percival Stone. Moreover, she is missing and we are already looking for her a certain amount of time.\n-I AM. You. Nothing. Not. I will say it.\n-Expectedly, usually errand boys say so. Cover your master. You know, now it really doesn’t matter who they put me in prison right now. But ... I still get to the truth and find your master.\n-Get it.\n-Take him away!\n.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/32.mp3'),
(100, 9, '#100', '-I\'d like to talk to you about Baron Percival Stone.What can you say about this person?\n-I? Nothing ... I can only say that he is not ready for change, does not want to get up from his throne.\n-I look u mutual dislike. What is it based on?\n- Do you see me promoting a new source of energy to the masses, and this ... The Baron is sitting on his own pair and does not move, does not let anyone pass and strangles everyone. But this does not work out for me, because the future lies with me.\n-Very interesting. So you can’t divide the throne, okay. The Baron expressed the idea that you sent the kidnapped to him to find out something. This is true?\n- Well, I have a little relationship, I sometimes merged information about him to her. But you know, she planned to visit his office a couple of days before the loss, we really didn’t communicate there at that time, but when I started looking for her she already disappeared, maybe she disappeared in his office. Apparently learned a lot about him.\n- That sounds nice ... Check it out. Is that all you can tell us?\n-I think yes.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/test.mp3'),
(111, 10, '#111', '-Do you know anything?\n- Yes, they did. Your prints in her apartment.\n- This is irrelevant.\n-I don’t understand, you were at her house?\n- I had to pick up the paper, so I went somehow to her.\n- Why didn’t I know about this? Those. you play on some women, for some documents. And then you come home and say how you love me? How many times have I forgiven you for your adventures, but you still hang around and hang around like a cable ....\n- Madame Adalin Ross, please. Wait outside the door.\n-I\'ll wait some more ... (leaves)\n- Thank you, she would have turned it around here now.\n-You during the last conversation, you once said strange things about your meetings with the kidnapped one. Let\'s be clean. Do you have a romantic relationship?\n-Maybe, but that doesn’t mean anything to the cause.\n- (something slurred yell)\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n-I think everyone wanted to ask this, I’m probably going to glue my family again.\n', 'http://194.87.111.127/exam/test.mp3'),
(420, 9, '#420', '\n\n- As you yourself allowed me to call in this gadyushnik. I tell you that errand boy?\n-No, but we have a couple of questions for you. I am Duke Cerillon Ton and will ask you these questions.\n-You have a couple of minutes, then I\'ll leave.\n- Well, then closer to the point. The girl disappeared - Lady Amelia Wood (holds out a photo), during a search of her apartment we found that she was looking for something at her house, but the one who did it was careless and left a couple of his prints. Guess whose they are?\n- Are you implying that they are mine? Pfff is funny. This is the first time I see this girl. Is that all, so can I go?\n- Go, I certainly understand that there is little evidence against you, but if it’s you, then we’ll collect enough evidence to remove you and sit down.\n- Well, look, look ... You will not find. Good luck.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/420.mp3'),
(634, 8, '#634', '\n-Thanks a lot! I didn’t even expect anyone to find me.\n-Not at all ... Actually, Lord Septimus Wake began to worry about you. He turned to us.\n-Hmm ... Sep turned to you, funny ...\n-Amelia, I want to ask you a couple of questions. Can you answer now?\n-Yes, I will try.\n- And so, we are more concerned with who abducted you. We found you accompanied by Earl Vernon Hold. In fact, little is known about him, but it seems to us that he didn’t abduct you, but was only at the beck and call.\n- Actually, I don\'t remember much. I was in the office of Baron Percival Stone, we quarreled, before I left, I went into the ladies room, and then the gas and everything. After I woke up, I did not see anyone. I didn’t hear anything. I\'m kind of useless ... The only one I saw was that man who led me out and only at that moment. I can’t tell you anything more about them. Sorry.\n-I understood you. Why did you quarrel with Percival?\n- Well, I wanted to write an article about energy sources, about Baron\'s attitude to electricity. But he was categorically against speaking on this subject and reacted so vividly.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n-Yeah, I see. Well, apparently I won’t learn more from you. That is probably all ... Thank you for the time, we will help you get home.\n- I\'m sorry that I know so little. I hope this helps you though. Thank you.\n\n\n', 'http://194.87.111.127/exam/634.mp3'),
(682, 8, '#682', '\n\n-Hello. Me, Captain Jasper Eddle. As far as I look you are Baron Percival Stone.\n- For what reason am I here?\n- (rustles with paper) Recently, this girl disappeared (holds out a photo). Do you know her?\n-No, should I?\n-This is Lady Amelia Wood, and during a search of her apartment, we found your footprints, so to speak? Well, are you still sure that you don’t know her?\n- Well, I didn’t recognize right away, it’s kind of like a journalist. She tried to interview me about business. But I started asking unnecessary questions and I left.\n- And what questions did she start to ask you?\n- About the case of Duke Nikola Tesla, to which I have nothing to do. About alternative energy sources, I do not remember how I feel about this and anything else.\n-When did you last see her?\n- Well, maybe a week ago, I don’t remember\n-Why do you think she asked you questions of this kind?\n- How do I know? Maybe Wake sent her to me. He is trying to stick his nose into my affairs and business. Although he knows that he will not know anything and will not receive from a giant like me.\n-I see you do not like Lord Wake. What do you think, why is he trying to “stick his nose into your affairs” as you say?\n- He wants to raise his business, at the expense of mine. I’m a monopolist in the energy sector at the moment, and he is there with his electricity, he wants to destroy everything that I built before. Okay, I have to go. I hope I answered all your questions.\n-Well, all the best.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/682.mp3'),
(732, 10, '#732', '\n\n-Good day, one more time. Greetings at my agency, I\'m Captain Jasper Addle. How can I help? And what is the best way to contact you?\n-Kind. It will be enough to call me Lord. I have a tort case.\n- (Interrupting) We have all delicate matters. Sorry, but let\'s get to the point.\n-Good. I have a friend, so she disappeared a few days ago. He doesn’t get in touch and there is no news from her at all.\n“What is the name of your friend and when was the last time you saw her, Lord?”\n- Her name is Amelia Wood, beautiful, young, ambitious ...\n“Lord, please answer the question!”\n- Oh yes. In general, a good girl. So, the last time I saw her about 2 days ago, we met on the street and walked (choked), but now it\'s not about that.\n- So, a couple of days ago you saw each other ... We drank coffee ...\n-Yes Yes Yes...\n- Well, so you walked or drank coffee. Dear Lord, let\'s have no secrets!\n- Well done, well, went to the cafe. What does this have to do with the matter?\n- Well, well (thoughtfully) ... In what condition was she? Maybe she was alarmed by something or didn’t usually behave?\n-Yes ... No, not really, I was calm and friendly, well, as always.\n-What did you discuss?\n-Just chatted.\n- Hmm ... Of course you gave us a lot of information. Can you ask who this girl is for you?\n-No. This is the address where she lived, maybe it will help. I hope for you. All the best, Captain!\n-Goodbye.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/test.mp3'),
(996, 8, '#996', '-Hello, my name is Jasper, I would like to know about Lady Amelia Wood from you.\n-Hi Jasper, why do you need such information and who are you?\n-I\'m the captain of the detective agency WICKED GENTELMEN, but I want to learn about Amelia, because she is considered missing. Tell me, when was the last time you saw her?\n- Um, she’s already like 3 or 4 days on vacation ... Therefore, I can’t help with anything, I’m sorry, I have to go.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/996.mp3'),
(999, 8, '#999', '\n-Thanks a lot! I didn’t even expect anyone to find me. It’s just not clear how you found me.\n-Not at all ... Actually, Lord Septimus Wake began to worry about you. He turned to us. And we found you ... To admit by chance. We examined the sewer system, it seemed strange to us that on more recent drawings those rooms disappeared.\n-Hmm ... Sep turned to you, funny ... But I was really lucky.\n-Amelia, I want to ask you a couple of questions. Can you answer now?\n-Yes, I will try.\n-And so, we are more concerned with who abducted you. You were under the Baron Percival Stone building.\n- Actually, I don\'t remember much. I was in the office of Baron Percival Stone, we quarreled, before leaving I went into the ladies room, and then the gas and everything. After I woke up, I did not see anyone. I didn’t hear anything. I\'m kind of useless ... I\'m sorry.\n-I understood you. Why did you quarrel with Percival?\n- Well, I wanted to write an article about energy sources, about Baron’s attitude to electricity. But he was categorically against speaking on this subject and reacted so vividly.\n-Yeah, I see. Well, apparently I won’t learn more from you. That is probably all ... Thank you for the time, we will help you get home.\n- I\'m sorry that I know so little. I hope this helps you though. Thank you.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/999.mp3'),
(1048, 8, '#1048', '\n-Thanks a lot! I didn’t even expect anyone to find me.\n-Not at all ... Lord Septimus Wake turned to us, he noticed your loss, so to speak.\n-Hmm ... Sep turned to you, funny ...\n- Amelia, I want to ask you a couple of questions. Can you answer now?\n-Yes, I will try.\n-In fact, we already know a lot, but still I want to know, have you seen the face of the person who abducted you?\n- Actually, I don\'t remember much. I was in the office of Baron Percival Stone, we quarreled, before I left, I went into the ladies room, and then the gas and everything. After I woke up, I did not see anyone. I didn’t hear anything. I\'m kind of useless ... I\'m sorry.\n-I understood you. Why did you quarrel with Percival?\n- Well, I wanted to write an article about energy sources, about Baron\'s attitude to electricity. But he was categorically against speaking on this subject and reacted so vividly.\n-Yeah, I see. Well, apparently I won’t learn more from you. That is probably all ... Thank you for the time, we will help you get home.\n- Im sorry that I know so little. I hope this helps you though. Thank you.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/1048.mp3'),
(1103, 9, '#1103', '\n- Well, here you are again. Remember you said that we will not find anything against you, but we have found. Documents in Baron’s office, very interesting reading. Do you know what we found there? Contract between you.\n- What would you like? Money? New office? More people?\n- Justice! (menacingly) No one has the right to kidnap defenseless girls, no one has the right to imprison people if he wants to. Even a person like you, with your status, which you will not have for long, does not have the right to do this! And you know that we will deal with your case with Tesla and I am sure that you are also involved there.\n- We\'ll see. I will no longer speak with you. Please leave me alone.\n- I’ll decide what and who will leave here, so remember, you’ll be sitting for a long time for everything.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/1103.mp3'),
(8547, 10, '#8547', '\n- And so ... Baron Percival Stone, here you are. You know, I don’t want to talk with you for a long time and I won’t. Only…\n- (interrupted) Girl, I won’t stay here long! You have nothing and never will be against me. You do not prove anything. Do you understand?\n-Hmm ... we’ll see it again, your friend with his connections will not help either, and no one will help him yet. You better tell me why you need this girl? Did she put her nose in the wrong place or something else?\n- This means absolutely nothing already, and I don’t want to talk to you.Anyway, I\'ll be out soon.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/exam/8547.mp3');

-- --------------------------------------------------------

--
-- Структура таблицы `mini_games`
--

DROP TABLE IF EXISTS `mini_games`;
CREATE TABLE `mini_games` (
  `id_game` int(11) NOT NULL,
  `link` varchar(45) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `mini_games`
--

INSERT INTO `mini_games` (`id_game`, `link`) VALUES
(1221, 'http://194.87.111.127/game/1221'),
(2020, 'http://194.87.111.127/game/2020'),
(2803, 'http://194.87.111.127/game/2803');

-- --------------------------------------------------------

--
-- Структура таблицы `money`
--

DROP TABLE IF EXISTS `money`;
CREATE TABLE `money` (
  `id_money` int(11) NOT NULL,
  `count_money` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `money`
--

INSERT INTO `money` (`id_money`, `count_money`) VALUES
(329, -2),
(415, -3),
(593, -4),
(601, -5),
(711, 10),
(776, -6),
(931, 15),
(982, -1);

-- --------------------------------------------------------

--
-- Структура таблицы `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE `notes` (
  `id_note` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `text_note` varchar(80) COLLATE utf8_bin NOT NULL,
  `date_note` datetime NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `notes`
--

INSERT INTO `notes` (`id_note`, `title`, `text_note`, `date_note`, `id_user`) VALUES
(1, 't1', 'd1', '2020-04-12 18:00:17', 1),
(3, 't3', 'd3', '2020-04-12 16:11:42', 1),
(4, 't4', 'd4', '2020-04-12 16:11:42', 1),
(5, 't5', 'd5', '2020-04-12 16:11:43', 1),
(6, 't6', 'd6', '2020-04-12 14:11:55', 1),
(8, 'Test', 'My note', '2020-04-12 18:17:19', 946872),
(9, 'Test2', '', '2020-04-12 18:17:31', 946872),
(11, 'Title', 'Text', '2020-04-28 16:53:56', 946872),
(12, 'Rgh', '', '2020-06-15 18:38:10', 1);

--
-- Триггеры `notes`
--
DROP TRIGGER IF EXISTS `notes_BEFORE_INSERT`;
DELIMITER $$
CREATE TRIGGER `notes_BEFORE_INSERT` BEFORE INSERT ON `notes` FOR EACH ROW BEGIN
SET NEW.date_note = now();
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `notes_BEFORE_UPDATE`;
DELIMITER $$
CREATE TRIGGER `notes_BEFORE_UPDATE` BEFORE UPDATE ON `notes` FOR EACH ROW BEGIN
SET NEW.date_note = now();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `persons`
--

DROP TABLE IF EXISTS `persons`;
CREATE TABLE `persons` (
  `id_person` int(11) NOT NULL,
  `person_name` varchar(30) COLLATE utf8_bin NOT NULL,
  `position` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(3000) COLLATE utf8_bin NOT NULL,
  `picture` varchar(45) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `persons`
--

INSERT INTO `persons` (`id_person`, `person_name`, `position`, `description`, `picture`) VALUES
(1, 'Lord Septimus Wake', 'Big boss', 'Age: about 45 years old\nPlace of residence: * classified *\nWork: * classified *\n\nLord Septimus Wake was born and lives in London. He is the only child in the Wake family, his parents ***.\n\nSeptimus is married to Madame Adalin Ross. They do not have children, this is due to ***.\n\nThe Lord is a well-known inventor in certain circles. His inventions are actively used by the military. However, over the past few years, he has ceased to engage in inventive activity and began to invest in progressive in his opinion development.\n\nThe main direction of his investments is the development of an alternative energy source, namely ***.\n\n*** In this regard, he is often a participant in scandals. Researchers, inventors and people whose business is connected with the smoke industry do not understand Wake and often accuse him of sponsoring uncompetitive inventions. Most often, Wake refuted these allegations and argued that the future lies with this alternative source.\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/1.jpg'),
(2, 'Madame Adalin Ross', 'Wife big bumps', 'Age 43 years old\nPlace of residence: * classified *\nWork: Without work\n\nMadame Adalin Ross is from France, her family is native French. As far as Ross’s parents are known, influential businessmen, but Adalin cut off all ties with her family 20 years ago and left the country.\n\nAdalin Ross is the wife of one of the most famous inventors, namely, Lord Septimus Wake. No children, ***. Ross got married almost immediately after moving to London, this is her first and only marriage.\n\nShe never worked, all her life she lives at the expense of others.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/2.jpg'),
(3, 'Lady Amelia Wood', 'Stolen', 'Age 25 years old\nLocation: 28A Denholme Rd - 12, London\nWork: Publishing house "Laputa"\n\nLady Amelia Wood lives in London, but information about the place of birth is not available. They are an orphan, literally after birth, she ended up in an orphanage, and so she spent most of her childhood from one orphanage to another. So she got to London.\n\nWood\'s personal life is unknown.\n\nAfter coming of age, the state allocated her an apartment and funds. And she moved to where she lives now. Wood began journalism. A couple of months after its first publication, it was noticed by the Laputa publishing house.\n\nShe always takes the most relevant scandals as the basis of her articles and conducts her own investigations. All her investigations are always professionally conducted using all available and inaccessible sources, so her articles often include a lot of unique information and sometimes even incriminating information. For this, her work is highly regarded by the publisher.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/3.jpg'),
(4, 'Sir Leopold Rumbel', 'Prosecutor', '\n* Some information is classified *\nAge 52 years old\nPlace of residence: * classified *\nJob: London City Prosecutor\n\nSer Leopold Rumbel was born and lives in London. His family is one of the most famous and respected families in the city. His father and mother in the past were members of the State Chamber and did a lot for their country. My father wanted Leopold to work in this area too, so he trained him and put him in the prosecutor’s office by all means and means, where he still works.\n\nAt an early age, parents found for Rumbel, in their opinion, a successful batch - ***. Leopold was forced to obey his parents. They lived together for 27 years and, according to rumors, they were cold to each other, but in their union a child was born whose name was hidden from the public. About 7 years ago, his wife and young children died under strange circumstances. According to unverified information, he and his family were supposed to fly on an airship, but just before takeoff, Leopold left him for an unknown reason and during the flight there was some damage that led to the crash. No one survived.\n\nLeopold is a tough, purposeful person. The price that must be paid to achieve the goal does not bother him. However, these of his qualities are not manifested in the work. Cases for which he undertakes periodically fall apart or he simply loses them in court.\n\nHis affairs: ***, the assassination of Duke Nikola Tesla.\n\nLeopold’s last case is rather complicated and, according to rumors, the public believes that this case will not be solved. There is also an opinion about Rumbel\'s involvement in this murder.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/4.jpg'),
(5, 'Duke Nikola Tesla', 'Electricity creator', 'Age: dead\nPlace of residence: dead\nJob: dead\n\nNikola Tesla was born in the village of Smilyan. Tesla\'s father was a priest, and his mother was an ordinary housewife. From childhood, Nikola\'s parents said that the path of the priest was intended for him. However, once he was crippled by a disease and his father promised his son that if he gets to his feet, then he will be allowed to study engineering. After Tesla recovered, he traveled around the world studying new technologies and inventions.\n\nTesla was distinguished by an extravagant character and strange habits. Many women fell in love with him, but he did not reciprocate and was not married. Adhered to the belief that family life, the birth of children are incompatible with scientific work.\n\nTesla, after he left his parents\' house, did not have his own house. Lived in a laboratory or in hotel rooms. He slept two hours a day, and once spent 84 hours at work without feeling tired. At one time, he drank whiskey daily, believing that this would extend his life. At the same time, he suffered from neurosis and obsessive states.\n* information deleted *\n\nInventions:\n- Alternating current\n- AC motor\n\nThe public does not accept and does not understand Tesla’s inventions, therefore they are not very successful.\n\nThe duke was killed in his laboratory under strange circumstances. The details of this case are not reliably known to the public; nevertheless, there is information about the poisoning.\n\nInvestigated by: Sir Leopold Rumbel.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/5.jpg'),
(7, 'Count Vernon Hold', 'Kidnapper', 'Earl Vernon Hold was born and lives in London. There is no permanent place of residence. Born in an ordinary family of workers. The current whereabouts of the parents are unknown.\n\nVernon in childhood worked at a factory for the production of various spare parts - a packer.\n\nIn adolescence, a couple of times caught in a small robbery * data deleted *.\n\nThere is no Hold data since the last detention.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/7.jpg'),
(8, 'Captain Jasper Eddle', 'Detective 1', 'Age: about 45 years old\nPlace of residence: * classified *\nWork: * classified *\n\nLord Septimus Wake was born and lives in London. He is the only child in the Wake family, his parents ***.\n\nSeptimus is married to Madame Adalin Ross. They do not have children, this is due to ***.\n\nThe Lord is a well-known inventor in certain circles. His inventions are actively used by the military. However, over the past few years, he has ceased to engage in inventive activity and began to invest in progressive in his opinion development.\n\nThe main direction of his investments is the development of an alternative energy source, namely ***.\n\n*** In this regard, he is often a participant in scandals. Researchers, inventors and people whose business is connected with the smoke industry do not understand Wake and often accuse him of sponsoring uncompetitive inventions. Most often, Wake refuted these allegations and argued that the future lies with this alternative source.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/8.jpg'),
(9, 'Duke Cerill Ton', 'Detective 2', 'DETECTIVE AGENCY OPERATOR "Wicked Gentlemen"\nAge 34 years old\nLocation: 6 Ashfield Rd, London\nJob: Private detective agency "Wicked Gentlemen"\n\nDuke Cerill Ton was born into a military family in London, where he now lives. At the moment, his parents moved to Liverpool and live there on an ongoing basis.\n\nSince childhood, Cerill had excellent physical fitness. He constantly participated in various sports events.\n\nSince then, there is no data about him, until the opening of his business.\n\nAt a more conscious age, Ton opened his own gym, it was very popular among high-ranking people and the military was often involved in it. There he met Jasper, who was very struck by the physical fitness of Cerill.\n\nLater, Jasper invited Thon to join his detective agency, to which Сerill responded positively.\n\nNow Duke Cerill Tone works in the detective agency "Wicked Gentlemen".\n\nFEATURES:\n- excellent masking and implementation skills\n- trained in martial arts\n- excellent command of weapons\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/9.jpg'),
(10, 'Lady Henrietta Field', 'Detective 3', 'HATSKER DETECTIVE AGENCY "Wicked Gentlemen"\nAge: about 29 years old\nLocation: (information deleted)\n\nJob: Private detective agency "Wicked Gentlemen"\n\nLady Henrietta Field lives in London. The place of her birth is unknown.\n\nThe first information about Henrietta appeared after the first arrest for complicity in the robbery of the main bank of London. In this robbery, she took part as a coordinator and cracker.\n\nHowever, its qualities as a coordinator and cracker interested the government. Therefore, as a result, she was offered to work with them instead of punishment.\n\nWhile working for the government, Field met Jasper (current senior detective at Wicked Gentlemen), who later invited her to work with her.\n\nNow she works in an agency led by Captain Jasper as a cracker and coordinator.\n\nFEATURES:\n- excellent hacking skills\n- excellent skills in coordination and planning\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/10.jpg'),
(432, 'Baron Percival Stone', 'Competitor', 'Age 49 years old\nLocation: 18 Pardoner St - 8, London\nJob: Owner of the company "Wild West"\n\nBaron Percival Stone is the richest businessman in the world. He was born and lives in London. Stone\'s parents died when he was 30 years old.\n\nFrom unverified sources, they died during the experiment, which conducted ***.\n\nPercival is divorced, no children. First wife ***.\n\nAll his life he studied the steam industry, created various inventions using the latest technologies. Over time, it became a business that began to grow rapidly and in this token, the company "Wild West" became the flagship in the steam industry. As a result, Baron became the richest and most famous businessman and inventor in this field.\n\nPercival was previously involved in various criminal cases, but they were all closed, and he was acquitted.\n\n* Data on criminal records and criminal prosecutions is hidden *\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'http://194.87.111.127/persons/432.jpg');

-- --------------------------------------------------------

--
-- Структура таблицы `prints`
--

DROP TABLE IF EXISTS `prints`;
CREATE TABLE `prints` (
  `id_print` int(11) NOT NULL,
  `id_person` int(11) NOT NULL,
  `code` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `prints`
--

INSERT INTO `prints` (`id_print`, `id_person`, `code`) VALUES
(104, 4, '4-1'),
(105, 4, '4-2'),
(106, 4, '4-3'),
(107, 4, '4-4'),
(108, 4, '4-5'),
(109, 1, '1-1'),
(110, 1, '1-2'),
(111, 1, '1-3'),
(112, 1, '1-4'),
(113, 1, '1-5'),
(114, 1, '1-6'),
(115, 1, '1-7'),
(116, 1, '1-8'),
(117, 1, '1-9'),
(118, 1, '1-10'),
(119, 3, '3-1'),
(120, 3, '3-2'),
(121, 3, '3-3'),
(122, 3, '3-4'),
(123, 3, '3-5'),
(124, 3, '3-6'),
(125, 3, '3-7'),
(126, 3, '3-8'),
(127, 3, '3-9'),
(128, 3, '3-10'),
(129, 8, '8-5'),
(130, 9, '9-1'),
(131, 10, '10-10');

-- --------------------------------------------------------

--
-- Структура таблицы `reports`
--

DROP TABLE IF EXISTS `reports`;
CREATE TABLE `reports` (
  `id_report` int(11) NOT NULL,
  `id_person` int(11) NOT NULL,
  `document` varchar(5000) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `reports`
--

INSERT INTO `reports` (`id_report`, `id_person`, `document`) VALUES
(358, 10, '28A Denholme Rd - 12, London. The usual sleeping area, all at hand shops, pharmacies and transport. Lady Amelia Wood lives in a small apartment, but she is nicely placed, despite the mess, which is definitely not a design decision. All cabinets are open and torn apart, things are lying around anywhere. Something is even broken. Someone was obviously here and apparently was looking for something.'),
(650, 8, 'During a search of Lady Amelia Wood’s apartment, evidence was found: 51, 814, 171, 920, 131, 432, 836, 212, 559, 68, 263, 219, 839, 224, 297, 103, 879, 593, 789, 1 , 35, 249, 994, 312, 300. After analysis, it was revealed that these evidence belong to the following persons: Lady Amelia Wood, Sir Leopold Rumbel, Baron Percival Stone. After a veil inspection, it became clear that the apartment was clearly looking for something, but it was not clear what.\n'),
(749, 10, 'During the sabotage, you installed a bomb in the building of Baron Percival Stone, in order to find out what was stolen in the building. Your assumption was correct. During the panic, you noticed the girl and the man who led her. The girl was abducted, and that man was Earl Vernon Hold. Naturally, this man was detained. The detectives interrogated him and the girl to find out what happened. Codes 32, 634.'),
(765, 8, 'During a search of Lady Amelia Wood’s apartment, evidence was found: 451, 814, 44, 171, 920, 131, 432, 836, 212, 559, 68, 263, 219, 839, 224, 297, 103, 879, 593, 789 , 1, 35, 249, 994, 312, 300. After analysis, it was revealed that these evidence belong to the following individuals: Lady Amelia Wood, Sir Leopold Rumbel, Baron Percival Stone, Lord Septimus Wake. After the vial inspection, it became clear that the apartment was clearly looking for something, but it was not clear what.'),
(844, 10, 'During the investigation, you collected enough evidence to capture the suspect Baron. During the operation, you learned about the connection between Rumbel and Stone. The tumbling resisted during the detention, so I had to use force. Lady Amelia Wood was found, she is all right. The interrogation of Amelia, Stone and Baron was conducted: 1048, 8547, 1103, 844.'),
(947, 9, '\nDuring the investigation, drawings of the building were discovered. They were suspicious, they had somewhere depicted the basement, somewhere not. You decided to check yourself if there are basements in the building. During the study, you found the room in which the abducted girl was. You evacuated her and conducted a survey with her code 999.'),
(1776, 8, 'Publishing house "Laputa" pretty well-known publisher. A lot of journalists, different genres, directions work in it. This publisher has a very good, even excellent building. No wonder the agency received many awards, only for architectural solutions. In the building there are a lot of portraits of prominent journalists who worked here and, accordingly, are very proud of them. The building has many offices and in each of them there are usually no more than 4 employees. The management of the publishing house takes care of their employee and their personal space, so the workplace of each employee is incredibly huge and comfortable.'),
(1994, 9, 'Early autumn. London is the center for the development of the steam industry, a leader among advanced technologies. If you walk around the city, then around every corner gushing steam from some new unknown thing. The city is crammed with various inventions and therefore it is considered the most technological and progressive in the world. In all areas, various steam technologies are used that help residents in their professional and everyday lives. Airships are constantly flying over the city. Here it is one of the types of tourist transport.\nThe city itself is not poor and from a distance it seems even very beautiful, but if you leave the center, you can meet not quite beautiful houses and generally something similar to slums. All buildings in the city are made in brown tones, which creates a special, pleasant atmosphere when walking. Well, if we talk about people, then the closer to the center, then they become more and more selfish and selfish.\nLondon is a city of conflict and disagreement. Here, every inventor will find a place for himself and his inventions, but no one guarantees that the public will accept and support. And because of this, conflicts and skirmishes quite often occur, on the basis of which various publishers inflate information bombs.\nOne of the last such bombs was associated with the development of a new alternative energy source. This idea, according to the author himself, was supposed to radically change the world. Society in London has always been conservative, so is it worth saying that it is not ready to change its foundations and rules and it is logical that this idea was criticized and not accepted. Only a very small circle of people supported and still supports these studies.');

-- --------------------------------------------------------

--
-- Структура таблицы `types`
--

DROP TABLE IF EXISTS `types`;
CREATE TABLE `types` (
  `id_type` int(11) NOT NULL,
  `type_name` varchar(45) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `types`
--

INSERT INTO `types` (`id_type`, `type_name`) VALUES
(1, 'money'),
(2, 'alert'),
(3, 'game'),
(4, 'report'),
(5, 'examination'),
(6, 'exam_capture'),
(7, 'ending_capture');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `email` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(100) COLLATE utf8_bin NOT NULL,
  `money` int(11) NOT NULL,
  `ending_capture` varchar(45) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id_user`, `email`, `password`, `money`, `ending_capture`) VALUES
(1, '1', 'c4ca4238a0b923820dcc509a6f75849b', 5, '111 682 420'),
(3, 'qwe@r.ty', 'cf6ebf3453bf1877ee3f1dce7bd1ec19', 30, '111'),
(4, 'test@mail.ru', '8459d70c344917c311aeac9216969e3b', 14, '111 682 420'),
(946872, 'casavkk@gmail.com', 'c33367701511b4f6020ec61ded352059', 1500, NULL),
(946873, 'koldunova121@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 5, NULL),
(946874, 'qqq@rr.com', 'e10adc3949ba59abbe56e057f20f883e', 30, NULL),
(946875, 'inqaberezina@yandex.ru', '46f94c8de14fb36680850768ff1b7f2a', 30, NULL);

--
-- Триггеры `users`
--
DROP TRIGGER IF EXISTS `BeforeUpdateHashPass`;
DELIMITER $$
CREATE TRIGGER `BeforeUpdateHashPass` BEFORE UPDATE ON `users` FOR EACH ROW BEGIN
		if old.`password`<> new.`password` then
			SET NEW.email = new.email;
			SET NEW.`password` = md5(NEW.`password`);
        end if;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `users_BEFORE_INSERT`;
DELIMITER $$
CREATE TRIGGER `users_BEFORE_INSERT` BEFORE INSERT ON `users` FOR EACH ROW BEGIN
		SET NEW.email = new.email;
		SET NEW.`password` = md5(NEW.`password`);
END
$$
DELIMITER ;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `alerts`
--
ALTER TABLE `alerts`
  ADD PRIMARY KEY (`id_alert`);

--
-- Индексы таблицы `codes`
--
ALTER TABLE `codes`
  ADD PRIMARY KEY (`id_code`),
  ADD KEY `type_idx` (`id_type`),
  ADD KEY `rep_idx` (`code`);

--
-- Индексы таблицы `examination`
--
ALTER TABLE `examination`
  ADD PRIMARY KEY (`id_exam`),
  ADD KEY `person_idx` (`id_person`);

--
-- Индексы таблицы `mini_games`
--
ALTER TABLE `mini_games`
  ADD PRIMARY KEY (`id_game`);

--
-- Индексы таблицы `money`
--
ALTER TABLE `money`
  ADD PRIMARY KEY (`id_money`);

--
-- Индексы таблицы `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id_note`),
  ADD KEY `user_idx` (`id_user`);

--
-- Индексы таблицы `persons`
--
ALTER TABLE `persons`
  ADD PRIMARY KEY (`id_person`);

--
-- Индексы таблицы `prints`
--
ALTER TABLE `prints`
  ADD PRIMARY KEY (`id_print`),
  ADD KEY `print_idx` (`id_person`);

--
-- Индексы таблицы `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id_report`),
  ADD KEY `report_idx` (`id_person`);

--
-- Индексы таблицы `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id_type`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `codes`
--
ALTER TABLE `codes`
  MODIFY `id_code` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT для таблицы `examination`
--
ALTER TABLE `examination`
  MODIFY `id_exam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8548;
--
-- AUTO_INCREMENT для таблицы `mini_games`
--
ALTER TABLE `mini_games`
  MODIFY `id_game` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2804;
--
-- AUTO_INCREMENT для таблицы `notes`
--
ALTER TABLE `notes`
  MODIFY `id_note` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT для таблицы `persons`
--
ALTER TABLE `persons`
  MODIFY `id_person` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=433;
--
-- AUTO_INCREMENT для таблицы `prints`
--
ALTER TABLE `prints`
  MODIFY `id_print` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=132;
--
-- AUTO_INCREMENT для таблицы `reports`
--
ALTER TABLE `reports`
  MODIFY `id_report` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1995;
--
-- AUTO_INCREMENT для таблицы `types`
--
ALTER TABLE `types`
  MODIFY `id_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=946876;
--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `codes`
--
ALTER TABLE `codes`
  ADD CONSTRAINT `type` FOREIGN KEY (`id_type`) REFERENCES `types` (`id_type`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `examination`
--
ALTER TABLE `examination`
  ADD CONSTRAINT `personexam` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `prints`
--
ALTER TABLE `prints`
  ADD CONSTRAINT `personprint` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `person` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
