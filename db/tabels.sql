CREATE TABLE `alerts` (
  `id_alert` int(11) NOT NULL,
  `text` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_alert`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `codes` (
  `id_code` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  `num_quest` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_code`),
  KEY `type_idx` (`id_type`),
  KEY `rep_idx` (`code`),
  CONSTRAINT `type` FOREIGN KEY (`id_type`) REFERENCES `types` (`id_type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `examination` (
  `id_exam` int(11) NOT NULL AUTO_INCREMENT,
  `id_person` int(11) NOT NULL,
  `title` varchar(30) COLLATE utf8_bin NOT NULL,
  `description` varchar(10000) COLLATE utf8_bin NOT NULL,
  `audio` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_exam`),
  KEY `person_idx` (`id_person`),
  CONSTRAINT `personexam` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8548 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `mini_games` (
  `id_game` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_game`)
) ENGINE=InnoDB AUTO_INCREMENT=2804 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `money` (
  `id_money` int(11) NOT NULL,
  `count_money` int(11) NOT NULL,
  PRIMARY KEY (`id_money`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `notes` (
  `id_note` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `text_note` varchar(80) COLLATE utf8_bin NOT NULL,
  `date_note` datetime NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id_note`),
  KEY `user_idx` (`id_user`),
  CONSTRAINT `user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `persons` (
  `id_person` int(11) NOT NULL AUTO_INCREMENT,
  `person_name` varchar(30) COLLATE utf8_bin NOT NULL,
  `position` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(3000) COLLATE utf8_bin NOT NULL,
  `picture` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_person`)
) ENGINE=InnoDB AUTO_INCREMENT=433 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `prints` (
  `id_print` int(11) NOT NULL AUTO_INCREMENT,
  `id_person` int(11) NOT NULL,
  `code` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_print`),
  KEY `print_idx` (`id_person`),
  CONSTRAINT `personprint` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `reports` (
  `id_report` int(11) NOT NULL AUTO_INCREMENT,
  `id_person` int(11) NOT NULL,
  `document` varchar(5000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_report`),
  KEY `report_idx` (`id_person`),
  CONSTRAINT `person` FOREIGN KEY (`id_person`) REFERENCES `persons` (`id_person`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1995 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `types` (
  `id_type` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(100) COLLATE utf8_bin NOT NULL,
  `money` int(11) NOT NULL,
  `ending_capture` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=946876 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
