-- phpMyAdmin SQL Dump
-- version 3.4.5deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 31. Okt 2011 um 23:24
-- Server Version: 5.1.58
-- PHP-Version: 5.3.6-13ubuntu3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `acyrancecrm`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `aufgaben`
--

CREATE TABLE IF NOT EXISTS `aufgaben` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `pub` tinyint(1) NOT NULL DEFAULT '0',
  `beschreibung` text,
  `tag` varchar(500) NOT NULL DEFAULT 'Standard',
  `kundeKennung` varchar(100) NOT NULL DEFAULT '-1',
  `versId` int(11) NOT NULL DEFAULT '-1',
  `vertragId` int(11) NOT NULL DEFAULT '-1',
  `stoerfallId` int(11) NOT NULL DEFAULT '-1',
  `schadenId` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `start` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `ende` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `aufgaben`
--

INSERT INTO `aufgaben` (`id`, `creatorId`, `pub`, `beschreibung`, `tag`, `kundeKennung`, `versId`, `vertragId`, `stoerfallId`, `schadenId`, `benutzerId`, `start`, `ende`, `created`, `modified`, `status`) VALUES
(1, 1, 0, 'Störfall Nr. 60003\nVertrag. Policennr (11002)\nGrund: Beitragsrückstand\n\n', 'Standard', '11002', 7, 8, 9, 10, 1, '2011-07-21 22:00:00', '2011-07-22 22:00:00', '2011-07-18 17:10:04', '2011-07-18 17:13:21', 0),
(2, 1, 0, 'Störfall Nr. 60004\nVertrag. Policennrxdcx (11002)\nGrund: Beitragsrückstand\n\n', 'Standard', '11002', -1, 2, 10, -1, 1, '2011-07-18 17:29:19', '2011-07-18 17:29:19', '2011-07-18 17:29:31', '2011-07-18 17:29:31', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `backup`
--

CREATE TABLE IF NOT EXISTS `backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(500) NOT NULL,
  `type` tinyint(2) NOT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `automatic` tinyint(1) NOT NULL DEFAULT '1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `success` tinyint(1) NOT NULL DEFAULT '0',
  `fileAvailable` tinyint(1) NOT NULL DEFAULT '0',
  `backupSize` bigint(20) NOT NULL DEFAULT '-1',
  `filetype` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `backup`
--

INSERT INTO `backup` (`id`, `path`, `type`, `created`, `automatic`, `benutzerId`, `success`, `fileAvailable`, `backupSize`, `filetype`) VALUES
(1, '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/backup/maklerpoint_backup_20110626-145018.zip', 0, '2011-06-26 12:50:32', 0, 1, 1, 0, 72, 0),
(2, '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/backup/maklerpoint_backup_20110626-145018.zip', 0, '2011-06-26 12:52:03', 0, 1, 1, 0, 72, 0),
(3, '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/backup/maklerpoint_backup_20110626-145018.zip', 1, '2011-06-26 12:52:15', 0, 1, 1, 0, 72, 0),
(4, '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/backup/maklerpoint_backup_20110626-145018.zip', 0, '2011-06-26 12:52:24', 0, 1, 1, 0, 72, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bankkonten`
--

CREATE TABLE IF NOT EXISTS `bankkonten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `kundenKennung` varchar(255) NOT NULL DEFAULT '-1',
  `versichererId` int(11) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `kontonummer` varchar(255) DEFAULT NULL,
  `bankleitzahl` varchar(255) DEFAULT NULL,
  `bankinstitut` varchar(255) DEFAULT NULL,
  `kontoinhaber` varchar(500) DEFAULT NULL,
  `iban` varchar(255) DEFAULT NULL,
  `bic` varchar(255) DEFAULT NULL,
  `comments` text,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE IF NOT EXISTS `benutzer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL DEFAULT '-1',
  `firmenId` int(11) NOT NULL DEFAULT '-1',
  `level` int(11) NOT NULL DEFAULT '0',
  `authority` varchar(500) NOT NULL DEFAULT 'ROLE_USER',
  `unterVermittler` tinyint(1) NOT NULL DEFAULT '0',
  `kennung` varchar(255) DEFAULT NULL,
  `anrede` varchar(255) DEFAULT NULL,
  `vorname` varchar(255) DEFAULT NULL,
  `vorname2` varchar(255) DEFAULT NULL,
  `weitereVornamen` varchar(255) DEFAULT NULL,
  `nachname` varchar(255) DEFAULT NULL,
  `firma` varchar(255) DEFAULT NULL,
  `strasse` varchar(255) DEFAULT NULL,
  `strasse2` varchar(255) DEFAULT NULL,
  `plz` varchar(255) DEFAULT NULL,
  `ort` varchar(255) DEFAULT NULL,
  `addresseZusatz` varchar(255) DEFAULT NULL,
  `addresseZusatz2` varchar(255) DEFAULT NULL,
  `bundesland` varchar(500) DEFAULT NULL,
  `land` varchar(500) DEFAULT 'Deutschland',
  `geburtsdatum` varchar(50) DEFAULT NULL,
  `telefon` varchar(255) DEFAULT NULL,
  `telefon2` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fax2` varchar(255) DEFAULT NULL,
  `mobil` varchar(255) DEFAULT NULL,
  `mobil2` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `homepage` varchar(255) DEFAULT NULL,
  `homepage2` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `comments` text,
  `custom1` varchar(255) DEFAULT NULL,
  `custom2` varchar(255) DEFAULT NULL,
  `custom3` varchar(255) DEFAULT NULL,
  `custom4` varchar(255) DEFAULT NULL,
  `custom5` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `lastlogin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `logincount` int(11) NOT NULL DEFAULT '1',
  `webenabled` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`id`, `parentId`, `firmenId`, `level`, `authority`, `unterVermittler`, `kennung`, `anrede`, `vorname`, `vorname2`, `weitereVornamen`, `nachname`, `firma`, `strasse`, `strasse2`, `plz`, `ort`, `addresseZusatz`, `addresseZusatz2`, `bundesland`, `land`, `geburtsdatum`, `telefon`, `telefon2`, `fax`, `fax2`, `mobil`, `mobil2`, `email`, `email2`, `homepage`, `homepage2`, `username`, `password`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `lastlogin`, `logincount`, `webenabled`, `status`) VALUES
(1, -1, 1, 4, 'ROLE_USER', 0, '11001', 'Herr', 'Mark', NULL, NULL, 'Muster', 'Acyrance Versicherungsmakler', 'Libellenstr. 27', NULL, '80939', 'München', NULL, NULL, NULL, 'Deutschland', '19.06.1986', '(089) 976 00 414', '(089) 322 08 134', '(089) 2555 13 1079', '(089) 2555 13 1200', '0176 24 33 14 82', '0177 858 25 74', 'hoppe@acyrance.de', 'info@yves-hoppe.de', 'http://www.acyrance.de', 'http://www.yves-hoppe.de', 'testuser', '12345', 'Bester Mitarbeiter', NULL, NULL, NULL, NULL, NULL, '2010-07-08 22:00:00', '2010-07-15 22:00:00', '2011-10-31 20:52:13', 2703, 1, 0),
(2, -1, -1, 2, 'ROLE_USER', 0, '11003', 'Frau', 'Joanna', NULL, NULL, 'Bolda', NULL, 'Baubergerstr. 13', NULL, '80992', 'München', NULL, NULL, NULL, 'Deutschland', '06.01.1972', '(089) 976 00 414', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'joanna', '12345', NULL, NULL, NULL, NULL, NULL, NULL, '2011-02-14 23:00:00', '2011-02-14 23:00:00', '2011-06-24 10:24:18', 1, 0, 0),
(3, -1, -1, 0, 'ROLE_USER', 0, '11006', 'Herr', 'Benjamin', NULL, NULL, 'Huber', NULL, 'Kämpferstr. 13', NULL, '80335', 'München', NULL, NULL, NULL, 'Deutschland', NULL, '(089) 350 81 331', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'benny', '12345', NULL, NULL, NULL, NULL, NULL, NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', '2011-06-24 10:25:50', 1, 0, 0),
(4, -1, -1, 1, 'ROLE_USER', 0, '11005', 'Herr', 'Markus', NULL, NULL, 'Schmidt', NULL, 'Krautanger 12', NULL, '80331', 'München', NULL, NULL, NULL, 'Deutschland', NULL, '(089) 213 44 09', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'marky', '12345', NULL, NULL, NULL, NULL, NULL, NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', '2011-06-24 10:27:17', 1, 0, 0),
(5, -1, -1, 3, 'ROLE_USER', 0, '11004', 'Frau', 'Katharina', NULL, NULL, 'Kunzlmann', NULL, 'Steigenweg. 12', NULL, '81377', 'München', NULL, NULL, NULL, 'Deutschland', NULL, '(089) 599 01 813', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'kathy', '12345', NULL, NULL, NULL, NULL, NULL, NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', '2011-06-24 10:28:48', 1, 0, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer_acl`
--

CREATE TABLE IF NOT EXISTS `benutzer_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `beratungsprotokolle`
--

CREATE TABLE IF NOT EXISTS `beratungsprotokolle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kundenKennung` varchar(500) DEFAULT NULL,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `versicherungsId` int(11) NOT NULL DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
  `vertragId` int(11) NOT NULL DEFAULT '-1',
  `beratungsVerzicht` tinyint(1) NOT NULL DEFAULT '0',
  `dokumentationsVerzicht` tinyint(1) NOT NULL DEFAULT '0',
  `wiederVorlage` tinyint(1) NOT NULL DEFAULT '0',
  `kundenWuensche` text,
  `kundenBedarf` text,
  `rat` text,
  `entscheidung` text,
  `marktuntersuchung` varchar(500) DEFAULT NULL,
  `kundenBemerkungen` text,
  `versicherungsSparte` varchar(500) DEFAULT NULL,
  `versicherungsGesellschaft` varchar(500) DEFAULT NULL,
  `beratungsVerzichtArt` varchar(500) DEFAULT NULL,
  `kundenAnschreiben` text,
  `dokumente` text,
  `checkKundenAnschreiben` tinyint(1) NOT NULL DEFAULT '1',
  `checkBeratungsDokumentation` tinyint(1) NOT NULL DEFAULT '1',
  `checkBeratungsDokuVerzicht` tinyint(1) NOT NULL DEFAULT '0',
  `checkInformationsPflichten` tinyint(1) NOT NULL DEFAULT '1',
  `checkDruckstuecke` tinyint(1) NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Daten für Tabelle `beratungsprotokolle`
--

INSERT INTO `beratungsprotokolle` (`id`, `kundenKennung`, `benutzerId`, `versicherungsId`, `produktId`, `vertragId`, `beratungsVerzicht`, `dokumentationsVerzicht`, `wiederVorlage`, `kundenWuensche`, `kundenBedarf`, `rat`, `entscheidung`, `marktuntersuchung`, `kundenBemerkungen`, `versicherungsSparte`, `versicherungsGesellschaft`, `beratungsVerzichtArt`, `kundenAnschreiben`, `dokumente`, `checkKundenAnschreiben`, `checkBeratungsDokumentation`, `checkBeratungsDokuVerzicht`, `checkInformationsPflichten`, `checkDruckstuecke`, `created`, `modified`, `status`) VALUES
(6, '11002', 1, 3, -1, -1, 0, 0, 0, '', '', '', '', 'Der Versicherungsmakler stützt seinen Rat auf eine objektive, ausgewogene Marktuntersuchung.', '', 'Gebündelter Vertrag', 'AachenMünchener Versicherung AG (5342)', 'Auf eine Beratung und Dokumentation wird ausdrücklich verzichtet.', 'Sehr geehrter Herr Schulz,\n\nanbei erhalten Sie die Dokumente zu Ihrer Beratung.\n\n\nMit freundlichen Grüßen, \n\nYves Hoppe', '', 1, 1, 0, 1, 1, '2011-06-24 19:11:09', '2011-06-24 19:11:09', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `briefe`
--

CREATE TABLE IF NOT EXISTS `briefe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `categoryId` int(11) DEFAULT '1',
  `creatorId` int(11) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `loeschbar` tinyint(1) DEFAULT '1',
  `privatKunde` tinyint(1) DEFAULT '0',
  `geschKunde` tinyint(1) DEFAULT '0',
  `versicherer` tinyint(1) DEFAULT '0',
  `produkt` tinyint(1) DEFAULT '0',
  `benutzer` tinyint(1) DEFAULT '0',
  `stoerfall` tinyint(1) DEFAULT '0',
  `vertrag` tinyint(1) DEFAULT '0',
  `filename` varchar(1000) NOT NULL,
  `fullpath` text NOT NULL,
  `checksum` varchar(255) DEFAULT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `beschreibung` text,
  `tag` varchar(500) DEFAULT 'Standard',
  `comments` text,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Daten für Tabelle `briefe`
--

INSERT INTO `briefe` (`id`, `type`, `categoryId`, `creatorId`, `benutzerId`, `loeschbar`, `privatKunde`, `geschKunde`, `versicherer`, `produkt`, `benutzer`, `stoerfall`, `vertrag`, `filename`, `fullpath`, `checksum`, `name`, `beschreibung`, `tag`, `comments`, `created`, `modified`, `status`) VALUES
(1, 0, 1, 1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 'allgemeiner_brief.xml', 'templates/word/allgemeiner_brief.xml', NULL, 'Allgem. Brief', 'Ein einfacher Brief ohne Text.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(2, 1, 1, -1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 'allgemeines_fax.xml', 'templates/fax/allgemeines_fax.xml', NULL, 'Allgem. Fax', 'Eine allgemeine Faxvorlage.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 15:24:26', 0),
(3, 2, 1, -1, -1, 0, 1, 1, 1, 1, 1, 1, 1, 'allgemeine_email.html', 'templates/email/allgemeine_email.html', NULL, 'Allgem. E-Mail', 'Eine einfache E-Mail Vorlage ohne Text.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-10 16:04:01', 0),
(4, 0, 1, -1, -1, 0, 1, 1, 1, 0, 0, 0, 0, 'terminbestaetigung.xml', 'templates/word/terminbestaetigung.xml', NULL, 'Terminbestätigung', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(5, 1, 1, -1, -1, 0, 1, 1, 1, 0, 0, 0, 0, 'terminbestaetigung_fax.xml', 'templates/fax/terminbestaetigung_fax.xml', NULL, 'Terminbestätigung Fax', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 15:24:34', 0),
(8, 0, 2, -1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 'geburtstags_brief.xml', 'templates/word/geburtstags_brief.xml', NULL, 'Geburtstagsbrief (Privatkunde)', 'Ein Geburtstagsbrief für Privatkunden.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(6, 2, 1, -1, -1, 0, 1, 1, 1, 0, 0, 0, 0, 'terminbestaetigung_email.html', 'templates/email/terminbestaetigung_email.html', NULL, 'Terminbestätigung E-Mail', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-11 10:52:21', 0),
(9, 0, 3, -1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 'geburtstags_brief_ansprechpartner.xml', 'templates/word/geburtstags_brief_ansprechpartner.xml', NULL, 'Geburtstagsbriefen Geschäftskunde Ansprechpartner', 'Ein Geburtstagsbrief für den Ansprechspartner eines Geschäftskunden.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(10, 0, 3, -1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 'geschaeftsjubilaeum_brief.xml', 'templates/word/geschaeftsjubilaeum_brief.xml', NULL, 'Glückwunschbrief zum Firmen-/Gesellschafts- Jubiläum', 'Ein einfacher Glückwunschbrief zum Firmen Jubiläum eines Geschäftskunden.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(11, 2, 1, -1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 'geburtstags_email.html', 'templates/email/geburtstags_email.html', NULL, 'Geburtstagsemail (Privatkunde)', 'Eine Geburtstags E-Mail für Privatkunden.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-11 10:52:40', 0),
(12, 2, 1, -1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 'geburtstags_email_ansprech.html', 'templates/email/geburtstags_email_ansprech.html', NULL, 'Geburtstagsemail (Geschäftskunde)', 'Eine Geburtstagsemail für den Ansprechpartner eines Geschäftskunden.', 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-11 10:52:57', 0),
(13, 0, 2, -1, -1, 0, 1, 0, 0, 0, 0, 0, 1, 'maklerauftrag.xml', 'templates/word/maklerauftrag.xml', NULL, 'Maklerauftrag (Privatkunde)', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 10:57:47', 0),
(14, 0, 3, -1, -1, 0, 0, 1, 0, 0, 0, 0, 1, 'maklerauftrag_geschaeftskunde.xml', 'templates/word/maklerauftrag_geschaeftskunde.xml', NULL, 'Maklerauftrag (Geschäftskunde)', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 10:57:56', 0),
(15, 0, 2, -1, -1, 0, 1, 0, 0, 0, 0, 0, 1, 'maklereinzelauftrag.xml', 'templates/word/maklerinzelauftrag.xml', NULL, 'Maklereinzelauftrag (Privatkunde)', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 07:18:04', 0),
(16, 0, 1, -1, -1, 0, 0, 1, 0, 0, 0, 0, 1, 'maklerinzelauftrag_geschaeftskunde.xml', 'templates/word/maklerinzelauftrag_geschaeftskunde.xml', NULL, 'Maklereinzelauftrag (Geschäftskunde)', NULL, 'Standard', NULL, '2011-05-31 22:00:00', '2011-06-05 10:58:19', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `briefe_cat`
--

CREATE TABLE IF NOT EXISTS `briefe_cat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Daten für Tabelle `briefe_cat`
--

INSERT INTO `briefe_cat` (`id`, `name`, `created`, `modified`, `status`) VALUES
(1, 'Allgemein', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(2, 'Privatkunden', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(3, 'Geschäftskunden', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(4, 'Versicherungen', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(5, 'Produkte', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(6, 'Verträge', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(7, 'Störfälle', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0),
(8, 'Benutzer', '2011-05-31 22:00:00', '2011-05-31 22:00:00', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `dokumente`
--

CREATE TABLE IF NOT EXISTS `dokumente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `kundenKennung` varchar(255) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
  `bpId` int(11) NOT NULL DEFAULT '-1',
  `stoerId` int(11) NOT NULL DEFAULT '-1',
  `schadenId` int(11) NOT NULL DEFAULT '-1',
  `vertragId` int(11) NOT NULL DEFAULT '-1',
  `filetype` tinyint(3) NOT NULL DEFAULT '-1',
  `name` varchar(500) DEFAULT NULL,
  `fullPath` text,
  `label` varchar(500) DEFAULT NULL,
  `beschreibung` text,
  `checksum` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `lastviewed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `viewcount` int(11) NOT NULL DEFAULT '1',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Daten für Tabelle `dokumente`
--

INSERT INTO `dokumente` (`id`, `creatorId`, `kundenKennung`, `benutzerId`, `versichererId`, `produktId`, `bpId`, `stoerId`, `schadenId`, `vertragId`, `filetype`, `name`, `fullPath`, `label`, `beschreibung`, `checksum`, `tag`, `created`, `modified`, `lastviewed`, `viewcount`, `status`) VALUES
(7, 1, '11002', 1, 3, -1, 6, -1, -1, 1, 2, 'beratungsprotokoll_anschreiben_11002_2406091109.doc', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/beratungsprotokoll/beratungsprotokoll_anschreiben_11002_2406091109.doc', NULL, 'Beratungsprotokoll Anschreiben 24.06.11 09:11', '1648212291', 'Standard', '2011-06-24 19:11:09', '2011-07-18 14:52:22', '2011-07-18 14:40:13', 0, 5),
(9, 1, '11002', 1, 3, -1, 6, -1, -1, 1, 2, 'beratungsprotokoll_informationen_11002_2406091113.doc', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/beratungsprotokoll/beratungsprotokoll_informationen_11002_2406091113.doc', NULL, 'Beratungsprotokoll Kundeninformationen 24.06.11 09:11', '3046939073', 'Standard', '2011-06-24 19:11:13', '2011-07-18 14:52:22', '2011-07-18 14:40:18', 0, 5),
(8, 1, '11002', 1, 3, -1, 6, -1, -1, 1, 2, 'beratungsprotokoll_dokumentation_11002_2406091112.doc', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/beratungsprotokoll/beratungsprotokoll_dokumentation_11002_2406091112.doc', NULL, 'Beratungsprotokoll Dokumentation 24.06.11 09:11', '2642809470', 'Standard', '2011-06-24 19:11:12', '2011-06-25 07:58:45', '2011-06-24 19:11:12', 0, 5),
(10, 1, '11002', 1, -1, -1, -1, -1, -1, -1, 14, 'log.txt', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/log.txt', NULL, NULL, '2307800731', 'Standard', '2011-06-24 19:11:51', '2011-06-25 07:58:45', '2011-06-24 19:11:51', 1, 5),
(11, 1, '11002', 1, -1, -1, -1, -1, -1, -1, 8, 'tree2.png', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/tree2.png', NULL, NULL, '2810935820', 'Standard', '2011-06-24 19:12:09', '2011-06-25 07:58:45', '2011-06-24 19:12:09', 1, 5),
(12, 1, '11002', 1, -1, -1, -1, -1, -1, -1, -1, 'cupp-3.0.tar.gz', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/cupp-3.0.tar.gz', NULL, NULL, '813160577', 'Standard', '2011-06-24 19:13:26', '2011-06-25 07:58:45', '2011-06-24 19:13:26', 1, 5),
(13, 1, '11002', 1, -1, -1, -1, -1, -1, -1, 6, 'CrypToolPresentation-de.pdf', '/media/Projects/Programming/Java-Progs/acyranceCRM/acyranceCRM/filesystem/kunden/11002/CrypToolPresentation-de.pdf', NULL, NULL, '3384763022', 'Standard', '2011-06-24 19:13:51', '2011-06-25 07:58:45', '2011-06-24 19:13:51', 1, 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `emails`
--

CREATE TABLE IF NOT EXISTS `emails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) DEFAULT '-1',
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `kundenKennung` varchar(255) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `versichererId` int(11) DEFAULT '-1',
  `produktId` int(11) DEFAULT '-1',
  `bpId` int(11) DEFAULT '-1',
  `schadenId` int(11) DEFAULT '-1',
  `stoerId` int(11) DEFAULT '-1',
  `vertragId` int(11) DEFAULT '-1',
  `filetype` int(11) DEFAULT '-1',
  `empfaenger` varchar(500) DEFAULT NULL,
  `absender` varchar(500) DEFAULT NULL,
  `cc` varchar(1000) DEFAULT NULL,
  `betreff` varchar(1000) DEFAULT NULL,
  `body` text,
  `nohtml` text,
  `send` tinyint(1) DEFAULT '1',
  `sendTime` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `firmenkunden`
--

CREATE TABLE IF NOT EXISTS `firmenkunden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `creator` int(11) NOT NULL DEFAULT '-1',
  `parentFirma` int(11) NOT NULL DEFAULT '-1',
  `betreuer` int(11) NOT NULL DEFAULT '-1',
  `type` int(11) NOT NULL DEFAULT '-1',
  `kundenNr` varchar(500) NOT NULL,
  `firmenName` varchar(500) NOT NULL,
  `firmenNameZusatz` varchar(500) DEFAULT NULL,
  `firmenNameZusatz2` varchar(500) DEFAULT NULL,
  `firmenStrasse` varchar(500) DEFAULT NULL,
  `firmenPLZ` varchar(30) DEFAULT NULL,
  `firmenStadt` varchar(500) DEFAULT NULL,
  `firmenBundesland` varchar(500) DEFAULT NULL,
  `firmenLand` varchar(500) DEFAULT NULL,
  `firmenTyp` varchar(500) DEFAULT NULL,
  `firmenSize` varchar(500) DEFAULT NULL,
  `firmenSitz` varchar(500) DEFAULT NULL,
  `firmenPostfach` tinyint(1) NOT NULL DEFAULT '0',
  `firmenPostfachName` varchar(500) DEFAULT NULL,
  `firmenPostfachPlz` varchar(500) DEFAULT NULL,
  `firmenPostfachOrt` varchar(500) DEFAULT NULL,
  `firmenRechtsform` varchar(500) DEFAULT NULL,
  `firmenEinkommen` varchar(500) DEFAULT NULL,
  `firmenBranche` varchar(500) DEFAULT NULL,
  `firmenGruendungDatum` varchar(500) DEFAULT NULL,
  `firmenGeschaeftsfuehrer` varchar(500) DEFAULT NULL,
  `firmenProKura` text,
  `firmenStandorte` text,
  `communication1` varchar(500) DEFAULT NULL,
  `communication2` varchar(500) DEFAULT NULL,
  `communication3` varchar(500) DEFAULT NULL,
  `communication4` varchar(500) DEFAULT NULL,
  `communication5` varchar(500) DEFAULT NULL,
  `communication6` varchar(500) DEFAULT NULL,
  `communication1Type` tinyint(5) NOT NULL DEFAULT '0',
  `communication2Type` tinyint(5) NOT NULL DEFAULT '1',
  `communication3Type` tinyint(5) NOT NULL DEFAULT '4',
  `communication4Type` tinyint(5) NOT NULL DEFAULT '7',
  `communication5Type` tinyint(4) NOT NULL DEFAULT '10',
  `communication6Type` tinyint(4) NOT NULL DEFAULT '14',
  `defaultKonto` int(11) NOT NULL DEFAULT '-1',
  `defaultAnsprechpartner` int(11) NOT NULL DEFAULT '-1',
  `werber` varchar(500) DEFAULT NULL,
  `comments` text,
  `custom1` varchar(500) DEFAULT NULL,
  `custom2` varchar(500) DEFAULT NULL,
  `custom3` varchar(500) DEFAULT NULL,
  `custom4` varchar(500) DEFAULT NULL,
  `custom5` varchar(500) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Daten für Tabelle `firmenkunden`
--

INSERT INTO `firmenkunden` (`id`, `mandantenId`, `creator`, `parentFirma`, `betreuer`, `type`, `kundenNr`, `firmenName`, `firmenNameZusatz`, `firmenNameZusatz2`, `firmenStrasse`, `firmenPLZ`, `firmenStadt`, `firmenBundesland`, `firmenLand`, `firmenTyp`, `firmenSize`, `firmenSitz`, `firmenPostfach`, `firmenPostfachName`, `firmenPostfachPlz`, `firmenPostfachOrt`, `firmenRechtsform`, `firmenEinkommen`, `firmenBranche`, `firmenGruendungDatum`, `firmenGeschaeftsfuehrer`, `firmenProKura`, `firmenStandorte`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `defaultKonto`, `defaultAnsprechpartner`, `werber`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, -1, 0, -1, 1, 0, '11006', 'HeinzKunst GmbH', 'Testaria Testoria', '', 'Baubergerstraße 13', '80992', 'München', 'Bayern', 'Deutschland', 'Hauptsitz', '3-5 Mitarbeiter', NULL, 0, '', '', '', 'GmbH', 'bis      1.000.000', 'Kultur', '2008-08-13', 'Test Frau', NULL, NULL, '089 976 00 414', 'info@heinzkunst.de', '', '', 'http://heinzkunst.de', 'http://blog.heinzknust.de', 0, 10, 4, 7, 14, 15, -1, -1, '11002', '', 'Ach was soll man sagen.', '', '', '', '', '2010-08-27 11:40:11', '2011-07-19 10:49:57', 0),
(2, -1, 0, -1, 1, 0, '11010', 'Carasteus Institut', 'Michaela Huber', '', 'Sonnenstr. 12', '80331', 'München', 'Bayern', 'Deutschland', 'Hauptsitz', '10-50 Mitarbeiter', NULL, 0, '', '', '', 'Einzelunternehmen', 'bis         100.000', 'Bildung', NULL, 'Annette Pitton-Hoppe', NULL, NULL, '089/32209419', '', '', '017785824', 'info@asklepiad.de', 'www.asklepiad.de', 0, 1, 4, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-06-17 08:35:56', '2011-07-16 12:33:41', 0),
(3, -1, 0, -1, 1, 0, '11022', 'TGF Sedlmaier GmbH', '', '', 'Otkerstr. 7C', '81547', 'München', 'Bayern', 'Deutschland', 'Hauptsitz', 'Keine Mitarbeiter', NULL, 0, '', '', '', 'GmbH', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '089/6928530', '089/6928535', 'info@tgf-bau.de', '', '', '', 0, 4, 10, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:29:31', '2011-07-16 12:29:31', 0),
(4, -1, 0, -1, 1, 0, '11023', 'Ernst Kohl GmbH & Co', '', '', 'Elly-Staegmeyr-Straße 1', '80911', 'München', 'Unbekannt', 'Deutschland', 'Hauptsitz', '3-5 Mitarbeiter', NULL, 0, '', '', '', 'GmbH', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '089/1505020', '089/1505021', '0174/1221098', '', '', '', 0, 4, 7, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:31:01', '2011-07-16 12:38:17', 0),
(5, -1, 0, -1, 1, 0, '11024', 'Klaus Lehle e. K.', '', '', 'Bussardstraße 6', '82166', 'Gräfelfing', 'Bayern', 'Deutschland', 'Hauptsitz', 'Keine Mitarbeiter', NULL, 0, '', '', '', 'e. K.', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '089/898036', '', '', '', '', '', 0, 1, 4, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:31:57', '2011-07-16 12:31:57', 0),
(6, -1, 0, -1, 1, 0, '11025', 'Offer und Schill GbR', '', '', 'Volpinistraße 2', '80638', 'München', 'Unbekannt', 'Deutschland', 'Hauptsitz', 'Keine Mitarbeiter', NULL, 0, '', '', '', 'GbR', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '089/9506469', '089/95064691', 'offer-und-schill.de', '', '', '', 0, 1, 14, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:35:12', '2011-07-16 12:35:12', 0),
(7, -1, 0, -1, 1, 0, '11026', 'Astureus Informatics', 'Bruno Hartmann', '', 'Herbigstraße 24', '80999', 'München', 'Bayern', 'Deutschland', 'Hauptsitz', 'Keine Mitarbeiter', NULL, 0, '', '', '', 'e. K.', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '0173/34421232', '', '', '', '', '', 7, 1, 4, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:36:49', '2011-07-16 12:36:49', 0),
(8, -1, 0, -1, 1, 0, '11027', 'Dinger & Anton GmbH & Co. KG', '', '', '', '', '', 'Unbekannt', 'Deutschland', 'Hauptsitz', 'Keine Mitarbeiter', NULL, 0, '', '', '', 'e. K.', 'Unbekannt', 'Unbekannt', NULL, '', NULL, NULL, '088/1791355', '', '', '', '', '', 0, 1, 4, 7, 10, 14, -1, -1, 'Unbekannt', '', '', '', '', '', '', '2011-07-16 12:37:55', '2011-07-16 12:37:55', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `firmen_ansprechpartner`
--

CREATE TABLE IF NOT EXISTS `firmen_ansprechpartner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kundenKennung` varchar(50) DEFAULT NULL,
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `creatorId` int(11) DEFAULT '-1',
  `ordering` int(11) DEFAULT '-1',
  `anrede` varchar(500) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `vorname` varchar(500) DEFAULT NULL,
  `nachname` varchar(500) DEFAULT NULL,
  `geburtsdatum` varchar(30) DEFAULT NULL,
  `abteilung` varchar(500) DEFAULT NULL,
  `funktion` varchar(500) DEFAULT NULL,
  `prioritaet` int(11) NOT NULL DEFAULT '0',
  `communication1` varchar(500) DEFAULT NULL,
  `communication2` varchar(500) DEFAULT NULL,
  `communication3` varchar(500) DEFAULT NULL,
  `communication4` varchar(500) DEFAULT NULL,
  `communication5` varchar(500) DEFAULT NULL,
  `communication1Type` int(11) NOT NULL DEFAULT '0',
  `communication2Type` int(11) NOT NULL DEFAULT '1',
  `communication3Type` int(11) NOT NULL DEFAULT '4',
  `communication4Type` int(11) NOT NULL DEFAULT '7',
  `communication5Type` int(11) NOT NULL DEFAULT '10',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Daten für Tabelle `firmen_ansprechpartner`
--

INSERT INTO `firmen_ansprechpartner` (`id`, `kundenKennung`, `versichererId`, `creatorId`, `ordering`, `anrede`, `title`, `vorname`, `nachname`, `geburtsdatum`, `abteilung`, `funktion`, `prioritaet`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `created`, `modified`, `status`) VALUES
(4, '11006', -1, 1, -1, 'Herr', '', 'Benjamin', 'Huber', '18.06.2011', 'Verwaltung', 'Sachbearbeiter/in', 0, '', '', '', '', '', 0, 1, 4, 7, 10, '2011-06-30 10:30:32', '2011-06-30 10:37:00', 0),
(5, '11006', -1, 1, -1, 'Frau', 'Dr.', 'Maria', 'Gruber', '08.06.1958', 'Geschäftsleitung', 'Abteilungsleiter/in', 5, '089322015875', 'maria.gruber@heinzkunst.de', '', '', '', 0, 10, 4, 7, 1, '2011-06-30 10:40:06', '2011-06-30 10:40:06', 0),
(6, NULL, 19, 1, -1, 'Herr', '', 'Klaus', 'Marek', NULL, 'Schadensabteilung', 'Sachbearbeiter/in', 0, '089/322 08 134', 'klaus.marek@allianz-se.de', '', '', '', 0, 10, 4, 7, 10, '2011-07-23 11:32:37', '2011-07-23 11:32:37', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kinder`
--

CREATE TABLE IF NOT EXISTS `kinder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `kindName` varchar(255) DEFAULT NULL,
  `kindVorname` varchar(255) DEFAULT NULL,
  `kindGeburtsdatum` varchar(255) DEFAULT NULL,
  `kindBeruf` varchar(255) DEFAULT NULL,
  `kindWohnort` varchar(255) DEFAULT NULL,
  `comments` text,
  `custom` text,
  `created` timestamp NOT NULL DEFAULT '1971-12-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `kinder`
--

INSERT INTO `kinder` (`id`, `parentId`, `mandantenId`, `kindName`, `kindVorname`, `kindGeburtsdatum`, `kindBeruf`, `kindWohnort`, `comments`, `custom`, `created`, `modified`, `status`) VALUES
(1, 6, -1, 'adsfsdfd', 'yasdf', '04.06.2011', 'dfdf', 'Beim VN', 'dfdf', 'dfdf', '2011-06-12 09:04:34', '2011-06-12 09:04:34', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kontakte`
--

CREATE TABLE IF NOT EXISTS `kontakte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) DEFAULT '-1',
  `kundenKennung` varchar(50) DEFAULT '-1',
  `versichererId` int(11) DEFAULT '-1',
  `produktId` int(11) DEFAULT '-1',
  `vertragId` int(11) DEFAULT '-1',
  `schadenId` int(11) DEFAULT '-1',
  `stoerId` int(11) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `name` varchar(500) DEFAULT NULL,
  `adresse` varchar(1000) DEFAULT NULL,
  `communication1` varchar(500) DEFAULT NULL,
  `communication2` varchar(500) DEFAULT NULL,
  `communication3` varchar(500) DEFAULT NULL,
  `communication4` varchar(500) DEFAULT NULL,
  `communication5` varchar(500) DEFAULT NULL,
  `communication6` varchar(255) DEFAULT NULL,
  `communication1Type` tinyint(4) DEFAULT '0',
  `communication2Type` tinyint(4) DEFAULT '1',
  `communication3Type` tinyint(4) DEFAULT '4',
  `communication4Type` tinyint(4) DEFAULT '7',
  `communication5Type` tinyint(4) DEFAULT '10',
  `communication6Type` tinyint(4) DEFAULT '14',
  `comments` text,
  `custom1` text,
  `custom2` text,
  `custom3` text,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunden`
--

CREATE TABLE IF NOT EXISTS `kunden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `betreuerId` int(11) DEFAULT '-1',
  `creatorId` int(11) DEFAULT '-1',
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `kundenNr` varchar(255) NOT NULL,
  `anrede` varchar(255) DEFAULT NULL,
  `titel` varchar(255) DEFAULT NULL,
  `firma` varchar(1000) DEFAULT NULL,
  `vorname` varchar(1000) NOT NULL,
  `vorname2` varchar(255) DEFAULT NULL,
  `vornameWeitere` varchar(1000) DEFAULT NULL,
  `nachname` varchar(1000) NOT NULL,
  `street` varchar(1000) DEFAULT NULL,
  `plz` varchar(50) DEFAULT NULL,
  `stadt` varchar(255) DEFAULT NULL,
  `bundesland` varchar(255) DEFAULT NULL,
  `land` varchar(1000) DEFAULT NULL,
  `adresseZusatz` varchar(1000) DEFAULT NULL,
  `adresseZusatz2` varchar(1000) DEFAULT NULL,
  `communication1` varchar(500) DEFAULT NULL,
  `communication2` varchar(500) DEFAULT NULL,
  `communication3` varchar(500) DEFAULT NULL,
  `communication4` varchar(500) DEFAULT NULL,
  `communication5` varchar(500) DEFAULT NULL,
  `communication6` varchar(500) DEFAULT NULL,
  `communication1Type` tinyint(4) NOT NULL DEFAULT '0',
  `communication2Type` tinyint(4) NOT NULL DEFAULT '1',
  `communication3Type` tinyint(4) NOT NULL DEFAULT '4',
  `communication4Type` tinyint(4) NOT NULL DEFAULT '7',
  `communication5Type` tinyint(4) NOT NULL DEFAULT '10',
  `communication6Type` tinyint(4) NOT NULL DEFAULT '14',
  `typ` varchar(255) DEFAULT NULL,
  `familienStand` varchar(255) DEFAULT NULL,
  `ehepartnerId` varchar(255) DEFAULT NULL,
  `geburtsdatum` varchar(255) DEFAULT NULL,
  `nationalitaet` varchar(500) DEFAULT 'Unbekannt',
  `beruf` varchar(1000) DEFAULT NULL,
  `berufsTyp` varchar(255) DEFAULT NULL,
  `berufsOptionen` varchar(255) DEFAULT NULL,
  `berufsBesonderheiten` text,
  `anteilBuerotaetigkeit` varchar(500) DEFAULT '100 %',
  `beginnRente` varchar(255) DEFAULT NULL,
  `beamter` tinyint(1) DEFAULT NULL,
  `oeffentlicherDienst` tinyint(1) DEFAULT NULL,
  `einkommen` double(11,2) NOT NULL DEFAULT '0.00',
  `einkommenNetto` double(11,2) NOT NULL DEFAULT '0.00',
  `steuertabelle` varchar(255) DEFAULT NULL,
  `steuerklasse` varchar(255) DEFAULT NULL,
  `kirchenSteuer` varchar(255) DEFAULT NULL,
  `kinderZahl` tinyint(3) DEFAULT '0',
  `kinderFreibetrag` varchar(255) DEFAULT NULL,
  `religion` varchar(255) DEFAULT NULL,
  `rolleImHaushalt` varchar(1000) DEFAULT NULL,
  `weiterePersonen` varchar(255) DEFAULT NULL,
  `weiterePersonenInfo` varchar(255) DEFAULT NULL,
  `familienPlanung` varchar(255) DEFAULT NULL,
  `werberKennung` varchar(255) DEFAULT NULL,
  `defaultKonto` int(11) NOT NULL DEFAULT '-1',
  `comments` text,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `custom4` varchar(1000) DEFAULT NULL,
  `custom5` varchar(1000) DEFAULT NULL,
  `geburtsname` varchar(500) DEFAULT NULL,
  `ehedatum` varchar(100) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Daten für Tabelle `kunden`
--

INSERT INTO `kunden` (`id`, `betreuerId`, `creatorId`, `mandantenId`, `kundenNr`, `anrede`, `titel`, `firma`, `vorname`, `vorname2`, `vornameWeitere`, `nachname`, `street`, `plz`, `stadt`, `bundesland`, `land`, `adresseZusatz`, `adresseZusatz2`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `typ`, `familienStand`, `ehepartnerId`, `geburtsdatum`, `nationalitaet`, `beruf`, `berufsTyp`, `berufsOptionen`, `berufsBesonderheiten`, `anteilBuerotaetigkeit`, `beginnRente`, `beamter`, `oeffentlicherDienst`, `einkommen`, `einkommenNetto`, `steuertabelle`, `steuerklasse`, `kirchenSteuer`, `kinderZahl`, `kinderFreibetrag`, `religion`, `rolleImHaushalt`, `weiterePersonen`, `weiterePersonenInfo`, `familienPlanung`, `werberKennung`, `defaultKonto`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `geburtsname`, `ehedatum`, `created`, `modified`, `status`) VALUES
(1, 1, 1, -1, '11001', 'Herr', '', 'Acyrance Versicherungsmakler', 'Benjamin', 'Alexander', '', 'Huber', 'Hofreinstraße 32', '80939', 'München', 'Baden-Württemberg', 'Deutschland', 'c/o biff', '', '089 144 20 631', '089 2555 13 1079', '0176 1215 112', '', 'huber@web.de', 'http://blog.heinzknust.de', 0, 4, 7, 7, 10, 14, 'Privat', 'ledig', NULL, '06.01.1972', 'deutsch', 'Versicherungsmakler', 'Unbekannt', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 10000.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', -1, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2010-07-05 22:00:00', '2011-07-20 12:25:09', 0),
(4, 1, 1, -1, '11002', 'Herr', 'Dr.', '', 'Martin', '', '', 'Schulz', 'Baubergerstr. 52', '80992', 'München', 'Baden-Württemberg', 'Deutschland', '', '', '089/13049182', '089/13149183', '', '0176/24331482', 'schulz@web.de', 'web.de', 0, 1, 4, 7, 10, 14, 'Privat', 'Ledig / alleinstehend', '', '20.06.1986', 'deutsch', '', 'Arbeitnehmer/-in', NULL, 'Keine', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', -1, '0', '', 'Unbekannt', '', '', NULL, '', -1, '', '', '', '', '', '', '', NULL, '2010-07-13 17:47:38', '2011-06-28 11:44:00', 0),
(6, 2, 1, -1, '11008', 'Frau', '', 'Asklepiad Institut', 'Regina', 'Regina', 'Theresia Katharina', 'Scheurer', 'Pelkovenstr. 27', '80992', 'München', 'Berlin', 'Deutschland', 'oja', '', '089/32209419', '0177/85825574', '', '', 'annette@asklepiad.de', 'www.asklepiad.de', 0, 7, 4, 7, 10, 14, 'Privat', 'Geschieden', '', '03.10.1956', 'deutsch', 'Dozentin', 'Freiberufler/-in', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 30000.00, 25000.00, 'Grundtabelle', 'I', '8 %', 1, '0', 'r.k.', 'Unbekannt', '', '', NULL, 'Keiner', -1, '', '', '', '', '', '', 'Pitton', NULL, '2011-06-08 14:39:49', '2011-07-03 17:09:45', 0),
(7, 3, 1, 1, '11009', 'Frau', 'Dr.', 'Blitz-Blank GmbH', 'Marie', 'Regina', 'Theresia', 'Wälser', 'Bahnhofplatz 3', '80335', 'München', 'Bayern', 'Deutschland', 'c/o Jochen df', '2. Stock', '089/32208134', '089/32209419', '089/2555131079', '0177/8582574', 'asfd@web.de', 'web.de', 0, 1, 4, 7, 10, 14, 'Privat', 'Verheiratet', '11008', '16.06.1960', 'Deutsch', 'Putzfrau', 'Arbeitnehmer/-in', NULL, 'Schichtarbeit', '20%', 'mit 67 Jahren', 0, 0, 20000.00, 15000.00, 'Grundtabelle', 'II', '8 %', 1, '0', 'r.k.', 'Ehepartner', 'Nur Deppen', 'Alles Deppen Infor', NULL, '11008', -1, 'Dummes dumym', 'custom1', 'custom2', 'custom3', 'custom4', 'custom5', 'Schröder', '23.06.1985', '2011-06-09 08:10:31', '2011-07-19 10:49:00', 0),
(8, 1, 1, 1, '11011', 'Herr', '', '', 'Marius', '', '', 'Arndt', 'Thierschstraße 2', '80538', 'München', 'Bayern', 'Deutschland', '', '', '089/14712313', '089/14712316', 'arndt@bwsag.de', '', '', '', 0, 1, 10, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, '11002', -1, '', '', '', '', '', '', '', NULL, '2011-07-16 11:45:40', '2011-07-19 10:49:13', 0),
(9, 1, 1, 1, '11012', 'Herr', '', '', 'Jürgen', '', '', 'Ahlers', 'Hirtenstraße 24', '80335', 'München', 'Baden-Württemberg', 'Deutschland', '', '', '089/55164', 'info@ahlers.de', '', '', '', '', 0, 10, 4, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 11:49:59', '2011-07-16 12:18:12', 0),
(10, 1, 1, 1, '11013', 'Frau', '', '', 'Katrin', '', '', 'Eichert', 'Asamstraße 25', '81541', 'München', 'Bayern', 'Deutschland', '', '', '089/653465', '0173/451873901', 'katrin.eichert@web.de', '', '', '', 0, 7, 10, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 11:51:15', '2011-07-16 11:51:15', 0),
(11, 2, 1, 1, '11014', 'Frau', '', '', 'Michaela', '', '', 'Brückner', 'Mies-van-der-Rohe-Straße 6', '80807', 'München', 'Bayern', 'Deutschland', '', '', '089/36055560', '0173/3441108', 'michaela@familie-brueckner.de', '', '', '', 0, 7, 10, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 11:54:30', '2011-07-16 11:54:30', 0),
(12, 1, 1, 1, '11015', 'Familie', '', '', 'Paul', '', '', 'Dimitrov', 'Mondseestraße 5', '81827', 'München', 'Baden-Württemberg', 'Deutschland', '', '', '089/21580888', '089/215801', 'info@compojoom.com', '', '', '', 0, 1, 10, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 11:56:03', '2011-07-19 10:48:02', 0),
(13, 1, 1, 1, '11016', 'Herr', '', '', 'Jan', '', '', 'Eberat', 'Emil-Geis-Straße 1', '81379', 'München', 'Unbekannt', 'Deutschland', '', '', '089/72442', '0169/33149862', 'info@ra-eberat.de', '', '', '', 0, 7, 10, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:00:57', '2011-07-16 12:00:57', 0),
(14, 1, 1, 1, '11017', 'Herr', 'Dr.', '', 'Jochen', '', '', 'Falkner', 'Gietlstraße 1', '81541', 'München', 'Baden-Württemberg', 'Deutschland', '', '', '089/6928570', '089/6928571', '0170/3321452', '', '', '', 0, 4, 7, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:03:07', '2011-07-16 12:07:28', 0),
(15, 1, 1, 1, '11018', 'Herr', '', '', 'Walter', '', '', 'Schmidt', 'Joseph-Dollinger-Bogen 13', '80337', 'München', 'Bayern', 'Deutschland', '', '', '089/323635', '089/2555123109', '', '', '', '', 0, 4, 4, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:04:12', '2011-07-16 12:04:12', 0),
(16, 1, 1, 1, '11019', 'Herr', '', '', 'Klaus', '', '', 'Schütz', 'Max-Nadler-Straße 11', '81929', 'München', 'Unbekannt', 'Deutschland', '', '', '089/95760555', '', '', '', '', '', 0, 1, 4, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, NULL, '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:04:49', '2011-07-16 12:06:18', 0),
(17, 1, 1, 1, '11020', 'Herr', '', '', 'Christian', '', '', 'Preis', 'Klausenburger Straße 9', '81677', 'München', 'Bayern', 'Deutschland', '', '', '089/930031', '089/32204731', '', '', '', '', 0, 1, 4, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:06:02', '2011-07-16 12:06:02', 0),
(18, 1, 1, 1, '11021', 'Herr', '', '', 'Heinz', '', '', 'Raith', 'Schlagweg 12', '81241', 'München', 'Unbekannt', 'Deutschland', '', '', '089/830258', '', '', '', '', '', 0, 1, 4, 7, 10, 14, 'Privat', 'Unbekannt', NULL, NULL, 'Deutsch', '', 'Unbekannt', NULL, 'Unbekannt', '100%', 'mit 67 Jahren', 0, 0, 0.00, 0.00, 'unbekannt', 'unbekannt', 'unbekannt', 0, '0', '', 'Unbekannt', '', '', NULL, NULL, -1, '', '', '', '', '', '', '', NULL, '2011-07-16 12:10:48', '2011-07-16 12:10:48', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunden_betreuung`
--

CREATE TABLE IF NOT EXISTS `kunden_betreuung` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kundenKennung` varchar(500) NOT NULL,
  `kundenTyp` varchar(500) DEFAULT NULL,
  `prioritaet` tinyint(3) DEFAULT '0',
  `loyalitaet` varchar(500) DEFAULT NULL,
  `zielgruppe` varchar(500) DEFAULT NULL,
  `ersterKontakt` varchar(30) DEFAULT NULL,
  `letzerKontakt` varchar(30) DEFAULT NULL,
  `letzteRoutine` varchar(30) DEFAULT NULL,
  `maklerVertrag` tinyint(1) DEFAULT '0',
  `maklerBeginn` varchar(30) DEFAULT NULL,
  `maklerEnde` varchar(30) DEFAULT NULL,
  `analyse` tinyint(1) DEFAULT '0',
  `analyseLetzte` varchar(30) DEFAULT NULL,
  `analyseNaechste` varchar(30) DEFAULT NULL,
  `erstinformationen` tinyint(1) NOT NULL DEFAULT '0',
  `verwaltungskosten` int(11) DEFAULT NULL,
  `newsletter` tinyint(1) NOT NULL DEFAULT '0',
  `kundenzeitschrift` tinyint(1) DEFAULT '0',
  `geburtstagskarte` tinyint(1) DEFAULT '0',
  `weihnachtskarte` tinyint(1) DEFAULT '0',
  `osterkarte` tinyint(1) DEFAULT '0',
  `dtrentenversicherungNr` varchar(250) DEFAULT NULL,
  `gkvBeitrag` double(11,2) NOT NULL DEFAULT '0.00',
  `kvBeitrag` double(11,2) NOT NULL DEFAULT '0.00',
  `pflegeBeitrag` double(11,2) NOT NULL DEFAULT '0.00',
  `krankenversicherung` varchar(500) DEFAULT NULL,
  `kvTyp` tinyint(4) NOT NULL DEFAULT '0',
  `kvNummer` varchar(500) DEFAULT NULL,
  `gestorben` tinyint(1) NOT NULL DEFAULT '0',
  `gestorbenDatum` varchar(50) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Daten für Tabelle `kunden_betreuung`
--

INSERT INTO `kunden_betreuung` (`id`, `kundenKennung`, `kundenTyp`, `prioritaet`, `loyalitaet`, `zielgruppe`, `ersterKontakt`, `letzerKontakt`, `letzteRoutine`, `maklerVertrag`, `maklerBeginn`, `maklerEnde`, `analyse`, `analyseLetzte`, `analyseNaechste`, `erstinformationen`, `verwaltungskosten`, `newsletter`, `kundenzeitschrift`, `geburtstagskarte`, `weihnachtskarte`, `osterkarte`, `dtrentenversicherungNr`, `gkvBeitrag`, `kvBeitrag`, `pflegeBeitrag`, `krankenversicherung`, `kvTyp`, `kvNummer`, `gestorben`, `gestorbenDatum`, `created`, `modified`, `status`) VALUES
(16, '11010', 'Kunde', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-06-17 08:35:57', '2011-06-17 08:35:57', 0),
(15, '11006', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-06-16 12:37:14', '2011-06-16 12:37:14', 0),
(14, '11009', 'Kunde', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-06-13 13:14:13', '2011-06-13 13:14:13', 0),
(13, '11008', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-06-13 13:14:12', '2011-06-13 13:14:12', 0),
(12, '11002', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 60, 0, 0, 0, 0, 0, NULL, 0.00, 35.00, 0.00, '', 0, '', 0, NULL, '2011-06-13 13:14:10', '2011-06-13 13:14:10', 0),
(17, '11001', 'Interessent', 0, 'Normal', 'Unbekannt', '28.07.2011', NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-06-18 11:08:13', '2011-06-18 11:08:13', 0),
(18, '11015', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 11:56:26', '2011-07-16 11:56:26', 0),
(19, '11016', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:01:16', '2011-07-16 12:01:16', 0),
(20, '11019', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:06:12', '2011-07-16 12:06:12', 0),
(21, '11018', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:06:57', '2011-07-16 12:06:57', 0),
(22, '11017', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:07:21', '2011-07-16 12:07:21', 0),
(23, '11020', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:08:27', '2011-07-16 12:08:27', 0),
(24, '11021', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:11:20', '2011-07-16 12:11:20', 0),
(25, '11013', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:17:30', '2011-07-16 12:17:30', 0),
(26, '11014', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:18:03', '2011-07-16 12:18:03', 0),
(27, '11012', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:18:08', '2011-07-16 12:18:08', 0),
(28, '11011', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, '', 0, '', 0, NULL, '2011-07-16 12:21:46', '2011-07-16 12:21:46', 0),
(29, '11023', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-07-16 12:38:01', '2011-07-16 12:38:01', 0),
(30, '11024', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-07-16 12:38:28', '2011-07-16 12:38:28', 0),
(31, '11026', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-07-16 12:38:29', '2011-07-16 12:38:29', 0),
(32, '11022', 'Interessent', 0, 'Normal', 'Unbekannt', NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, NULL, 0.00, 0.00, 0.00, NULL, 0, NULL, 0, NULL, '2011-07-16 12:38:31', '2011-07-16 12:38:31', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunden_grp`
--

CREATE TABLE IF NOT EXISTS `kunden_grp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grpid` int(11) NOT NULL,
  `kundenKennung` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunden_zusatzadressen`
--

CREATE TABLE IF NOT EXISTS `kunden_zusatzadressen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) DEFAULT NULL,
  `kundenKennung` varchar(500) NOT NULL DEFAULT '-1',
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(500) DEFAULT NULL,
  `nameZusatz` varchar(500) DEFAULT NULL,
  `nameZusatz2` varchar(500) DEFAULT NULL,
  `street` varchar(500) DEFAULT NULL,
  `plz` varchar(50) DEFAULT NULL,
  `ort` varchar(500) DEFAULT NULL,
  `bundesland` varchar(500) DEFAULT NULL,
  `land` varchar(500) DEFAULT NULL,
  `communication1` varchar(500) DEFAULT NULL,
  `communication2` varchar(500) DEFAULT NULL,
  `communication3` varchar(500) DEFAULT NULL,
  `communication4` varchar(500) DEFAULT NULL,
  `communication5` varchar(500) DEFAULT NULL,
  `communication6` varchar(500) DEFAULT NULL,
  `communication1Type` int(11) DEFAULT '0',
  `communication2Type` int(11) DEFAULT '1',
  `communication3Type` int(11) DEFAULT '4',
  `communication4Type` int(11) DEFAULT '7',
  `communication5Type` int(11) DEFAULT '10',
  `communication6Type` int(11) DEFAULT '14',
  `custom1` varchar(500) DEFAULT NULL,
  `custom2` varchar(500) DEFAULT NULL,
  `custom3` varchar(500) DEFAULT NULL,
  `comments` text,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `kunden_zusatzadressen`
--

INSERT INTO `kunden_zusatzadressen` (`id`, `creator`, `kundenKennung`, `versichererId`, `benutzerId`, `name`, `nameZusatz`, `nameZusatz2`, `street`, `plz`, `ort`, `bundesland`, `land`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `custom1`, `custom2`, `custom3`, `comments`, `created`, `modified`, `status`) VALUES
(1, 1, '11008', -1, -1, 'Angelika Grüner', 'c/o Regina Scheurer', NULL, 'Brauerstr. 13', '82113', 'München', 'Bayern', 'Deutschland', '089/53212321', '089/23112323', '', '', '', '', 0, 4, 4, 7, 10, 14, '', '', '', '', '2011-06-11 18:11:23', '2011-07-16 10:33:24', 0),
(2, 1, '11008', -1, -1, 'Aristoteles GmbH', 'z.Hd. Regina Scheurer', NULL, 'Mailingerstr. 13', '83551', 'München', 'Bayern', 'Deutschland', '', '', '', '', '', '', 0, 1, 4, 7, 10, 14, '', '', '', '', '2011-06-18 11:17:15', '2011-07-16 10:33:29', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mandanten`
--

CREATE TABLE IF NOT EXISTS `mandanten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL DEFAULT '-1',
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `firmenName` varchar(500) NOT NULL,
  `firmenZusatz` varchar(500) DEFAULT NULL,
  `firmenZusatz2` varchar(500) DEFAULT NULL,
  `vermittlungNamen` varchar(255) DEFAULT NULL,
  `vertretungsBerechtigtePosition` text,
  `vertretungsBerechtigteVorname` text,
  `vertretungsBerechtigteNachname` text,
  `vertretungsBerechtigteIHKErlaubnis` text,
  `firmenTyp` varchar(255) DEFAULT NULL,
  `firmenRechtsform` varchar(255) DEFAULT NULL,
  `postfach` varchar(255) DEFAULT NULL,
  `postfachPlz` varchar(255) DEFAULT NULL,
  `postfachOrt` varchar(255) DEFAULT NULL,
  `filialTyp` varchar(255) DEFAULT NULL,
  `filialMitarbeiterZahl` varchar(255) DEFAULT NULL,
  `geschaeftsleiter` varchar(255) DEFAULT NULL,
  `gesellschafter` text,
  `steuerNummer` varchar(255) DEFAULT NULL,
  `ustNummer` varchar(255) DEFAULT NULL,
  `vermoegensHaftpflicht` varchar(255) DEFAULT NULL,
  `beteiligungenVU` tinyint(1) NOT NULL DEFAULT '0',
  `beteiligungenMAK` tinyint(1) NOT NULL DEFAULT '0',
  `verbandsMitgliedschaften` varchar(255) DEFAULT NULL,
  `beraterTyp` varchar(255) DEFAULT NULL,
  `ihkName` varchar(255) DEFAULT NULL,
  `ihkRegistriernummer` varchar(255) DEFAULT NULL,
  `ihkStatus` varchar(255) DEFAULT NULL,
  `ihkAbweichungen` text,
  `versicherListe` varchar(255) DEFAULT NULL,
  `is34c` tinyint(1) NOT NULL DEFAULT '0',
  `is34d` tinyint(1) NOT NULL DEFAULT '0',
  `gewerbeamtShow` tinyint(1) NOT NULL DEFAULT '0',
  `gewerbeamtName` varchar(255) DEFAULT NULL,
  `gewerbeamtPLZ` varchar(255) DEFAULT NULL,
  `gewerbeamtOrt` varchar(255) DEFAULT NULL,
  `gewerbeamtStrasse` varchar(255) DEFAULT NULL,
  `handelsregisterShow` tinyint(1) NOT NULL DEFAULT '0',
  `handelsregisterName` varchar(255) DEFAULT NULL,
  `handelsregisterStrasse` varchar(255) DEFAULT NULL,
  `handelsregisterPLZ` varchar(255) DEFAULT NULL,
  `handelsregisterOrt` varchar(255) DEFAULT NULL,
  `handelsregisterRegistrierNummer` varchar(255) DEFAULT NULL,
  `logo` varchar(1000) DEFAULT NULL,
  `beschwerdeStellen` text,
  `adressZusatz` varchar(255) DEFAULT NULL,
  `adressZusatz2` varchar(255) DEFAULT NULL,
  `strasse` varchar(255) DEFAULT NULL,
  `plz` varchar(255) DEFAULT NULL,
  `ort` varchar(255) DEFAULT NULL,
  `bundesland` varchar(255) DEFAULT NULL,
  `land` varchar(255) DEFAULT NULL,
  `bankName` varchar(255) DEFAULT NULL,
  `bankKonto` varchar(255) DEFAULT NULL,
  `bankEigentuemer` varchar(255) DEFAULT NULL,
  `bankLeitzahl` varchar(255) DEFAULT NULL,
  `bankIBAN` varchar(255) DEFAULT NULL,
  `bankBIC` varchar(255) DEFAULT NULL,
  `telefon` varchar(255) DEFAULT NULL,
  `telefon2` varchar(255) DEFAULT NULL,
  `telefon3` varchar(255) DEFAULT NULL,
  `mobil` varchar(255) DEFAULT NULL,
  `mobil2` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fax2` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `secureMail` varchar(255) DEFAULT NULL,
  `emailSignatur` text,
  `homepage` varchar(255) DEFAULT NULL,
  `homepage2` varchar(255) DEFAULT NULL,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `custom4` varchar(1000) DEFAULT NULL,
  `custom5` varchar(1000) DEFAULT NULL,
  `custom6` varchar(1000) DEFAULT NULL,
  `custom7` varchar(1000) DEFAULT NULL,
  `custom8` varchar(1000) DEFAULT NULL,
  `custom9` varchar(1000) DEFAULT NULL,
  `custom10` varchar(1000) DEFAULT NULL,
  `comments` text,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `lastUsed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `mandanten`
--

INSERT INTO `mandanten` (`id`, `parentId`, `creatorId`, `firmenName`, `firmenZusatz`, `firmenZusatz2`, `vermittlungNamen`, `vertretungsBerechtigtePosition`, `vertretungsBerechtigteVorname`, `vertretungsBerechtigteNachname`, `vertretungsBerechtigteIHKErlaubnis`, `firmenTyp`, `firmenRechtsform`, `postfach`, `postfachPlz`, `postfachOrt`, `filialTyp`, `filialMitarbeiterZahl`, `geschaeftsleiter`, `gesellschafter`, `steuerNummer`, `ustNummer`, `vermoegensHaftpflicht`, `beteiligungenVU`, `beteiligungenMAK`, `verbandsMitgliedschaften`, `beraterTyp`, `ihkName`, `ihkRegistriernummer`, `ihkStatus`, `ihkAbweichungen`, `versicherListe`, `is34c`, `is34d`, `gewerbeamtShow`, `gewerbeamtName`, `gewerbeamtPLZ`, `gewerbeamtOrt`, `gewerbeamtStrasse`, `handelsregisterShow`, `handelsregisterName`, `handelsregisterStrasse`, `handelsregisterPLZ`, `handelsregisterOrt`, `handelsregisterRegistrierNummer`, `logo`, `beschwerdeStellen`, `adressZusatz`, `adressZusatz2`, `strasse`, `plz`, `ort`, `bundesland`, `land`, `bankName`, `bankKonto`, `bankEigentuemer`, `bankLeitzahl`, `bankIBAN`, `bankBIC`, `telefon`, `telefon2`, `telefon3`, `mobil`, `mobil2`, `fax`, `fax2`, `email`, `email2`, `secureMail`, `emailSignatur`, `homepage`, `homepage2`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `custom6`, `custom7`, `custom8`, `custom9`, `custom10`, `comments`, `created`, `modified`, `lastUsed`, `status`) VALUES
(1, -1, 1, 'Acyrance Versicherungsmakler', 'Joanna Bolda', NULL, NULL, 'Leitung', 'Joanna', 'Bolda', 'Ja', 'Einzelfirma', 'Einzelfirma', NULL, NULL, NULL, NULL, '1-3 Mitarbeiter', 'Joanna Bolda', NULL, '', 'DE267603520', 'Bis 1.117.000 Euro', 0, 0, NULL, 'Versicherungsmakler', 'Deutscher Industrie- und Handelskammertag (DIHK) e.V.', 'D-EY0Z-OGWA8-78', 'Vorhanden', NULL, NULL, 0, 1, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Röthstraße 28', '80992', 'München', 'Bayern', 'Deutschland', 'Dresdner Bank München', '0591105900', 'Acyrance Versicherungsmakler', '70080000', NULL, NULL, '(089) 976 00 414', NULL, NULL, NULL, NULL, '(089) 2555 13 1079', NULL, 'info@acyrance.de', NULL, NULL, NULL, 'http://www.acyrance.de', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2010-07-31 22:00:00', '2010-07-31 22:00:00', '2010-08-02 11:16:17', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mandantenId` int(11) DEFAULT '-1',
  `senderId` int(11) DEFAULT NULL,
  `empfaengerId` int(11) DEFAULT NULL,
  `md5sum` varchar(255) DEFAULT '3342',
  `betreff` varchar(500) DEFAULT NULL,
  `context` text,
  `tag` varchar(500) DEFAULT NULL,
  `mread` tinyint(1) NOT NULL DEFAULT '0',
  `created` timestamp NULL DEFAULT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `messages`
--

INSERT INTO `messages` (`id`, `mandantenId`, `senderId`, `empfaengerId`, `md5sum`, `betreff`, `context`, `tag`, `mread`, `created`, `modified`, `status`) VALUES
(1, -1, -1, 1, '3342', 'Willkommen bei MaklerPoint Office', 'Herzlich Willkommen zum MaklerPoint Office System.\r\n\r\nIhr MaklerPoint Team', NULL, 1, '2011-06-01 09:29:48', '2011-07-23 11:26:39', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `nachrichten`
--

CREATE TABLE IF NOT EXISTS `nachrichten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mandantenId` int(11) DEFAULT '-1',
  `benutzerId` int(11) NOT NULL,
  `betreff` varchar(255) NOT NULL,
  `context` text NOT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `nachrichten`
--

INSERT INTO `nachrichten` (`id`, `mandantenId`, `benutzerId`, `betreff`, `context`, `tag`, `created`, `modified`, `status`) VALUES
(1, -1, -1, 'Herzlich Willkommen', 'Sehr geehrte Benutzerinnen und Benutzer,\r\n\r\nvielen Dank für den Erwerb von MaklerPoint Office. Unser System garantiert Ihnen einen langfristig zukunftssicheren Einsatz und bietet Ihnen maximale Freiheiten bei der Wahl Ihrer Hard- und Software. \r\n\r\nDabei wächst MaklerPoint Office dynamisch mit Ihrem Unternehmen mit, vom Einzelkämpfer bis hin zur erfolgreichen Versicherungsgesellschaft, passt sich das System dynamisch an Ihre Anforderungen an.\r\n\r\nSollten Sie noch Fragen haben oder auf Probleme stoßen, stehen wir Ihnen gerne zur Verfügung.\r\n\r\nIhr MaklerPoint Team', 'Standard', '2010-07-26 07:45:25', '2011-07-03 17:06:20', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `newsletter`
--

CREATE TABLE IF NOT EXISTS `newsletter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `sender` varchar(1000) DEFAULT NULL,
  `senderMail` varchar(1000) DEFAULT NULL,
  `subject` varchar(1000) DEFAULT NULL,
  `text` text,
  `send` tinyint(1) NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `newsletter_sub`
--

CREATE TABLE IF NOT EXISTS `newsletter_sub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kennung` varchar(1000) DEFAULT NULL,
  `zaId` int(11) NOT NULL DEFAULT '-1',
  `ansprechpartnerId` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=46 ;

--
-- Daten für Tabelle `newsletter_sub`
--

INSERT INTO `newsletter_sub` (`id`, `kennung`, `zaId`, `ansprechpartnerId`, `name`, `email`, `created`, `modified`, `status`) VALUES
(37, '11010', -1, -1, 'Carasteus Institut', 'info@asklepiad.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(38, '11006', -1, 5, 'Maria Gruber', 'maria.gruber@heinzkunst.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(36, '11006', -1, -1, 'HeinzKunst GmbH', 'info@heinzkunst.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(35, '11009', -1, -1, 'Marie Wälser', 'asfd@web.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(33, '11002', -1, -1, 'Martin Schulz', 'schulz@web.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(34, '11008', -1, -1, 'Regina Scheurer', 'annette@asklepiad.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(32, '11001', -1, -1, 'Benjamin Huber', 'huber@web.de', '2011-07-16 10:56:57', '2011-07-16 10:56:57', 0),
(39, '11011', -1, -1, 'Marius Arndt', 'arndt@bwsag.de', '2011-07-20 09:50:04', '2011-07-20 09:50:04', 0),
(40, '11012', -1, -1, 'Jürgen Ahlers', 'info@ahlers.de', '2011-07-20 09:50:04', '2011-07-20 09:50:04', 0),
(41, '11013', -1, -1, 'Katrin Eichert', 'katrin.eichert@web.de', '2011-07-20 09:50:04', '2011-07-20 09:50:04', 0),
(42, '11014', -1, -1, 'Michaela Brückner', 'michaela@familie-brueckner.de', '2011-07-20 09:50:04', '2011-07-20 09:50:04', 0),
(43, '11015', -1, -1, 'Paul Dimitrov', 'info@compojoom.com', '2011-07-20 09:50:04', '2011-07-20 09:50:04', 0),
(44, '11016', -1, -1, 'Jan Eberat', 'info@ra-eberat.de', '2011-07-20 09:50:04', '2011-07-20 09:50:21', 5),
(45, '11022', -1, -1, 'TGF Sedlmaier GmbH', 'info@tgf-bau.de', '2011-07-20 09:50:04', '2011-07-20 09:50:18', 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizen`
--

CREATE TABLE IF NOT EXISTS `notizen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `priv` tinyint(1) NOT NULL DEFAULT '0',
  `kundenKennung` varchar(500) DEFAULT NULL,
  `versichererId` int(11) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
  `vertragId` int(11) NOT NULL DEFAULT '-1',
  `stoerfallId` int(11) NOT NULL DEFAULT '-1',
  `schadenId` int(11) NOT NULL DEFAULT '-1',
  `betreff` varchar(500) DEFAULT NULL,
  `text` text,
  `tag` varchar(500) NOT NULL DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `notizen`
--

INSERT INTO `notizen` (`id`, `creatorId`, `priv`, `kundenKennung`, `versichererId`, `benutzerId`, `produktId`, `vertragId`, `stoerfallId`, `schadenId`, `betreff`, `text`, `tag`, `created`, `modified`, `status`) VALUES
(1, 1, 1, '11001', -1, -1, -1, -1, -1, -1, 'Nicht vergessen', '<p>\n      Du hast den Farbfilm vergessen, mein Michael\n    </p>', 'Standard', '2011-07-01 09:38:07', '2011-07-01 09:41:51', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `produkte`
--

CREATE TABLE IF NOT EXISTS `produkte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `sparteId` int(11) NOT NULL DEFAULT '-1',
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `art` tinyint(4) NOT NULL DEFAULT '-1',
  `tarif` varchar(500) DEFAULT NULL,
  `tarifBasis` varchar(500) DEFAULT NULL,
  `bezeichnung` varchar(500) DEFAULT NULL,
  `kuerzel` varchar(500) DEFAULT NULL,
  `vertragsmaske` tinyint(3) DEFAULT '0',
  `vermittelbar` tinyint(1) NOT NULL DEFAULT '1',
  `versicherungsart` int(11) DEFAULT NULL,
  `risikotyp` int(11) DEFAULT NULL,
  `waehrung` int(11) NOT NULL DEFAULT '1',
  `steuersatz` int(11) NOT NULL DEFAULT '1',
  `versicherungsSumme` double(11,2) DEFAULT '0.00',
  `bewertungsSumme` double(11,2) DEFAULT '0.00',
  `bedingungen` text,
  `selbstbeteiligung` double(11,2) DEFAULT '0.00',
  `nettopraemiePauschal` double(11,2) DEFAULT '0.00',
  `nettopraemieZusatz` double(11,2) DEFAULT '0.00',
  `nettopraemieGesamt` double(11,2) DEFAULT '0.00',
  `zusatzEinschluesse` varchar(500) DEFAULT NULL,
  `zusatzInfo` text,
  `comments` text,
  `custom1` varchar(500) DEFAULT NULL,
  `custom2` varchar(500) DEFAULT NULL,
  `custom3` varchar(500) DEFAULT NULL,
  `custom4` varchar(500) DEFAULT NULL,
  `custom5` varchar(500) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `produkte`
--

INSERT INTO `produkte` (`id`, `versichererId`, `sparteId`, `creatorId`, `art`, `tarif`, `tarifBasis`, `bezeichnung`, `kuerzel`, `vertragsmaske`, `vermittelbar`, `versicherungsart`, `risikotyp`, `waehrung`, `steuersatz`, `versicherungsSumme`, `bewertungsSumme`, `bedingungen`, `selbstbeteiligung`, `nettopraemiePauschal`, `nettopraemieZusatz`, `nettopraemieGesamt`, `zusatzEinschluesse`, `zusatzInfo`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, 15, 2, -1, 0, 'L0DL', '', 'Risikolebensversicherung L0M(DL)', '', -1, 1, -1, -1, 1, 1, 0.00, 0.00, '', 0.00, 1233.00, 0.00, 0.00, '', '', '', '', '', '', '', '', '2011-07-23 11:50:44', '2011-07-23 12:10:39', 0),
(2, 15, 2, -1, 0, 'R1MC', '', 'Zukunftsrente Klassik ohne Beitragsrückzahlung bei Tod', '', -1, 1, -1, -1, 1, 1, 0.00, 0.00, '', 0.00, 234.00, 0.00, 0.00, '', '', '', '', '', '', '', '', '2011-07-23 11:50:55', '2011-07-23 12:09:02', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `produkte_haftung`
--

CREATE TABLE IF NOT EXISTS `produkte_haftung` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produkt` int(11) DEFAULT '-1',
  `haftungZeit` tinyint(3) DEFAULT '0',
  `haftungMonate` tinyint(4) DEFAULT '0',
  `haftungFormel` varchar(1000) DEFAULT NULL,
  `haftungsart` tinyint(3) DEFAULT '0',
  `ratierlich` tinyint(3) DEFAULT '0',
  `ratierlichIntervalle` tinyint(5) DEFAULT '1',
  `ratierlichTabelle` tinyint(3) DEFAULT '-1',
  `kombiniert` tinyint(3) DEFAULT '0',
  `kombiniertVollhaftungsZeit` tinyint(4) NOT NULL DEFAULT '0',
  `kombiniertRatierlich` tinyint(3) DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `produkte_haftung`
--

INSERT INTO `produkte_haftung` (`id`, `produkt`, `haftungZeit`, `haftungMonate`, `haftungFormel`, `haftungsart`, `ratierlich`, `ratierlichIntervalle`, `ratierlichTabelle`, `kombiniert`, `kombiniertVollhaftungsZeit`, `kombiniertRatierlich`, `created`, `modified`, `status`) VALUES
(1, 1, 0, 0, '0', 0, 0, 1, 0, 0, 0, 0, '2011-07-24 11:52:50', '2011-07-24 11:52:50', 0),
(2, 2, 0, 0, '0', 0, 0, 1, 0, 0, 0, 0, '2011-07-24 11:52:55', '2011-07-24 11:52:55', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schaeden`
--

CREATE TABLE IF NOT EXISTS `schaeden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `kundenNr` varchar(255) NOT NULL,
  `vertragsId` int(11) NOT NULL DEFAULT '-1',
  `schadenNr` varchar(500) DEFAULT NULL,
  `meldungArt` varchar(500) DEFAULT NULL,
  `meldungVon` varchar(500) DEFAULT NULL,
  `meldungTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `schaedenTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `schadenPolizei` tinyint(1) DEFAULT '0',
  `schadenKategorie` varchar(500) DEFAULT NULL,
  `schadenBearbeiter` int(11) DEFAULT NULL,
  `schadenOrt` varchar(500) DEFAULT NULL,
  `schadenUmfang` text,
  `schadenHergang` text,
  `vuWeiterleitungTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `vuMeldungArt` varchar(255) DEFAULT NULL,
  `risiko` varchar(255) DEFAULT NULL,
  `schadenHoehe` double(11,2) DEFAULT NULL,
  `schadenAbrechnungArt` tinyint(3) DEFAULT '0',
  `vuGutachten` tinyint(1) DEFAULT '0',
  `vuSchadennummer` varchar(255) DEFAULT NULL,
  `vuStatusDatum` timestamp NULL DEFAULT '1971-01-31 23:00:00',
  `wiedervorlagenId` int(11) DEFAULT '-1',
  `interneInfo` text,
  `notiz` text,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `custom4` varchar(1000) DEFAULT NULL,
  `custom5` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `schaeden`
--

INSERT INTO `schaeden` (`id`, `creatorId`, `mandantenId`, `kundenNr`, `vertragsId`, `schadenNr`, `meldungArt`, `meldungVon`, `meldungTime`, `schaedenTime`, `schadenPolizei`, `schadenKategorie`, `schadenBearbeiter`, `schadenOrt`, `schadenUmfang`, `schadenHergang`, `vuWeiterleitungTime`, `vuMeldungArt`, `risiko`, `schadenHoehe`, `schadenAbrechnungArt`, `vuGutachten`, `vuSchadennummer`, `vuStatusDatum`, `wiedervorlagenId`, `interneInfo`, `notiz`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, 1, -1, '11002', 1, '50001', 'Telefonisch', 'Martin Schulz (11002)', '2011-07-20 08:25:59', '2011-07-01 08:25:00', 0, '', 1, '', '', '', '2011-07-20 08:25:00', 'Telefonisch', '', 350.00, 0, 0, 'AJK2311-U231', '2011-07-20 08:25:51', -1, '', '', '', '', '', '', '', '2011-07-20 08:25:59', '2011-07-23 11:25:30', 0),
(2, 1, -1, '11011', 3, '50002', 'Persönlich', 'Marius Arndt (11011)', '2011-07-20 12:06:40', '2011-07-07 12:05:00', 1, '', 1, 'VN WOhnung', 'Alles zu Bruch gegange', 'Alkohol mach deppen froh', '2011-07-19 12:05:00', 'Telefonisch', 'Hafptlficht', 100.00, 1, 0, 'ASSD-KKMA-23111', '2011-07-20 12:05:15', -1, 'Doofer kunde', '', '', '', '', '', '', '2011-07-20 12:06:40', '2011-07-20 12:06:40', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schaeden_forderungen`
--

CREATE TABLE IF NOT EXISTS `schaeden_forderungen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) DEFAULT '-1',
  `mandantenId` int(11) DEFAULT '-1',
  `schadenId` int(11) DEFAULT NULL,
  `belegVon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `anspruchSteller` varchar(1000) DEFAULT NULL,
  `anspruchArt` varchar(1000) DEFAULT NULL,
  `gesamtforderung` double DEFAULT '0',
  `selbstbeteiligung` double DEFAULT '0',
  `effektiveforderung` double DEFAULT '0',
  `zahltext` varchar(1000) DEFAULT NULL,
  `zahlungvon` varchar(500) DEFAULT NULL,
  `comments` text,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schaeden_zahlungen`
--

CREATE TABLE IF NOT EXISTS `schaeden_zahlungen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) DEFAULT '-1',
  `mandantenId` int(11) DEFAULT '-1',
  `schadenId` int(11) DEFAULT '-1',
  `schadenForderungId` int(11) DEFAULT '-1',
  `belegVon` timestamp NULL DEFAULT NULL,
  `beguenstigt` varchar(1000) DEFAULT NULL,
  `forderungsArt` varchar(1000) DEFAULT NULL,
  `zahlung` double DEFAULT '0',
  `zahltext` varchar(1000) DEFAULT NULL,
  `zahlungvon` varchar(1000) DEFAULT NULL,
  `comments` text,
  `created` timestamp NULL DEFAULT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `session`
--

CREATE TABLE IF NOT EXISTS `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benutzerId` int(11) NOT NULL,
  `start` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `lastrefresh` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_id` varchar(255) NOT NULL,
  `anwendung` tinyint(2) NOT NULL,
  `build` varchar(250) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_id` (`session_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=56 ;

--
-- Daten für Tabelle `session`
--

INSERT INTO `session` (`id`, `benutzerId`, `start`, `lastrefresh`, `session_id`, `anwendung`, `build`, `status`) VALUES
(1, 1, '2011-07-24 11:54:58', '2011-07-24 11:55:15', '32874021975440873734538472962496', 0, '20110720', 5),
(2, 1, '2011-07-24 12:13:36', '2011-07-24 12:14:04', '19923783778491753908658431569063', 0, '20110720', 5),
(3, 1, '2011-07-24 12:19:50', '2011-07-24 12:23:18', '29532960025620528335341628806055', 0, '20110720', 5),
(4, 1, '2011-07-24 12:23:23', '2011-07-24 12:26:25', '11761876523061822930801989849754', 0, '20110720', 5),
(5, 1, '2011-07-24 12:26:30', '2011-07-24 12:26:58', '51401735917607804003254743567665', 0, '20110720', 5),
(6, 1, '2011-07-24 12:37:06', '2011-07-24 12:37:18', '33327686482936663337472127559700', 0, '20110720', 5),
(7, 1, '2011-07-24 13:49:42', '2011-07-24 13:52:26', '95117756010822411569767815821762', 0, '20110720', 5),
(8, 1, '2011-07-24 13:54:38', '2011-07-24 13:56:51', '16503581544089962226447763701755', 0, '20110720', 5),
(9, 1, '2011-07-24 14:08:06', '2011-07-24 14:08:37', '15092211558492479236112576884272', 0, '20110720', 5),
(10, 1, '2011-07-24 14:14:43', '2011-07-24 14:14:57', '26801154066102734005429442277449', 0, '20110720', 5),
(11, 1, '2011-07-25 14:19:34', '2011-07-25 14:19:43', '23378867613027786775459858693429', 0, '20110720', 5),
(12, 1, '2011-07-25 14:30:51', '2011-07-25 14:34:34', '30933370588780247063453195496754', 0, '20110720', 5),
(13, 1, '2011-07-25 14:54:39', '2011-07-25 14:54:43', '18719691843212565919143739098682', 0, '20110720', 5),
(14, 1, '2011-07-25 14:54:54', '2011-07-25 14:55:40', '25847853356782520676488455396280', 0, '20110720', 5),
(15, 1, '2011-07-25 14:55:50', '2011-07-25 14:56:18', '26355706659313503104515825349997', 0, '20110720', 5),
(16, 1, '2011-07-25 14:56:23', '2011-07-25 14:57:13', '22869173485485443265428168017908', 0, '20110720', 5),
(17, 1, '2011-07-25 14:57:25', '2011-07-25 15:00:15', '38812595617047722320653041926374', 0, '20110720', 5),
(18, 1, '2011-07-25 15:10:18', '2011-07-25 15:10:46', '12767017183617666756886949799311', 0, '20110720', 5),
(19, 1, '2011-07-25 15:10:53', '2011-07-25 15:11:05', '14031103974829226519189772628751', 0, '20110720', 5),
(20, 1, '2011-07-25 15:11:56', '2011-07-25 15:12:04', '11957295791514238439335917466430', 0, '20110720', 5),
(21, 1, '2011-07-25 15:14:29', '2011-07-25 15:48:59', '10622313531369862194621345804731', 0, '20110720', 5),
(22, 1, '2011-07-26 09:32:12', '2011-07-26 09:34:22', '21349160807480006270071517674612', 0, '20110720', 5),
(23, 1, '2011-07-26 09:34:52', '2011-07-26 09:35:43', '24769800369103735032678358832342', 0, '20110720', 5),
(24, 1, '2011-07-27 08:13:03', '2011-07-27 08:17:34', '26687082757548815231275286605427', 0, '20110720', 5),
(25, 1, '2011-07-27 08:27:40', '2011-07-27 08:27:42', '90256505411927797148997002821991', 0, '20110720', 5),
(26, 1, '2011-07-27 08:29:51', '2011-07-27 08:30:19', '15525183321316739943795161058560', 0, '20110720', 5),
(27, 1, '2011-07-27 08:32:21', '2011-07-27 08:34:03', '29876514196435040033755637077629', 0, '20110720', 5),
(28, 1, '2011-07-27 08:49:39', '2011-07-27 09:03:13', '10119692199385475414316990634933', 0, '20110720', 5),
(29, 1, '2011-07-27 08:56:09', '2011-07-27 08:56:20', '23176457027280933943341028401998', 0, '20110720', 5),
(30, 1, '2011-07-27 08:58:22', '2011-07-27 08:58:27', '11234014475543048265596781100154', 0, '20110720', 5),
(31, 1, '2011-07-27 09:03:40', '2011-07-27 09:04:12', '15786920904665614231309919062974', 0, '20110720', 5),
(32, 1, '2011-07-27 09:21:52', '2011-07-27 09:22:23', '96749660037652365016284839479452', 0, '20110720', 5),
(33, 1, '2011-07-27 09:23:52', '2011-07-27 09:24:06', '11024288448722451355367164485017', 0, '20110720', 5),
(34, 1, '2011-07-27 09:25:16', '2011-07-27 09:25:26', '27240226247238295223663436031224', 0, '20110720', 5),
(35, 1, '2011-07-27 09:28:10', '2011-07-27 09:28:57', '15525767764811692558605281478117', 0, '20110720', 5),
(36, 1, '2011-07-27 09:38:21', '2011-07-27 09:38:25', '29564856928930861029482497610864', 0, '20110720', 5),
(37, 1, '2011-07-27 10:17:21', '2011-07-27 10:17:51', '11621676150187583163953460147116', 0, '20110720', 5),
(38, 1, '2011-07-27 10:18:38', '2011-07-27 10:19:03', '10967694775956179349509265851355', 0, '20110720', 5),
(39, 1, '2011-07-27 10:30:50', '2011-07-27 10:31:03', '11600780817582382936405923897255', 0, '20110720', 5),
(40, 1, '2011-07-27 13:33:02', '2011-07-27 13:33:50', '44183037639174864853163463776035', 0, '20110720', 5),
(41, 1, '2011-07-27 14:00:21', '2011-07-27 14:02:10', '32782432916005433559585667960401', 0, '20110720', 5),
(42, 1, '2011-07-27 14:02:57', '2011-07-27 14:04:11', '72252295886236334249366701527131', 0, '20110720', 5),
(43, 1, '2011-07-27 14:04:26', '2011-07-27 14:05:22', '80381067379571301648121374105227', 0, '20110720', 5),
(44, 1, '2011-07-28 07:19:25', '2011-07-28 07:31:49', '20651701715676512605741421717668', 0, '20110720', 5),
(45, 1, '2011-07-28 07:32:05', '2011-07-28 07:32:45', '29184330540063796338980081761878', 0, '20110720', 5),
(46, 1, '2011-07-28 07:34:45', '2011-07-28 09:46:25', '13040017388093633785763709772560', 0, '20110720', 5),
(47, 1, '2011-07-28 14:22:18', '2011-07-28 14:22:34', '31010457652295549221341674470412', 0, '20110720', 5),
(48, 1, '2011-07-28 14:23:06', '2011-07-28 14:29:19', '18417112863526625386076718007980', 0, '20110720', 5),
(49, 1, '2011-07-28 14:29:33', '2011-07-28 14:30:39', '89779877154109689846326645151031', 0, '20110720', 5),
(50, 1, '2011-07-28 14:48:27', '2011-07-28 14:49:26', '24140806864725509134524705515485', 0, '20110720', 5),
(51, 1, '2011-07-28 14:53:33', '2011-07-28 14:53:58', '89219537090483025224190373506564', 0, '20110720', 5),
(52, 1, '2011-07-28 14:59:00', '2011-07-28 14:59:17', '22069855660507071594992258516527', 0, '20110720', 5),
(53, 1, '2011-07-28 15:00:16', '2011-07-28 15:08:32', '88564410561864340709636409048769', 0, '20110720', 5),
(54, 1, '2011-07-28 15:10:27', '2011-07-28 16:15:57', '20366398289525001573971175321985', 0, '20110720', 5),
(55, 1, '2011-10-31 20:52:14', '2011-10-31 22:15:51', '16075299086080204003134843013704', 0, '20110720', 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sparten`
--

CREATE TABLE IF NOT EXISTS `sparten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spartenNummer` varchar(100) DEFAULT NULL,
  `bezeichnung` varchar(500) DEFAULT NULL,
  `gruppe` varchar(500) DEFAULT NULL,
  `steuersatz` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=51 ;

--
-- Daten für Tabelle `sparten`
--

INSERT INTO `sparten` (`id`, `spartenNummer`, `bezeichnung`, `gruppe`, `steuersatz`, `status`) VALUES
(1, '000', 'Gebündelter Vertrag', NULL, 19, 0),
(2, '010', 'Lebensversicherung', NULL, 0, 0),
(3, '020', 'Krankenversicherung', '', 0, 0),
(4, '030', 'Unfallversicherung', '', 19, 0),
(5, '040', 'Haftpflichversicherung', '', 19, 0),
(6, '050', 'Kraftfahrtversicherung', '', 19, 0),
(7, '051', 'Kfz-Haftpflicht', NULL, 19, 0),
(8, '052', 'Kfz-Vollkasko', NULL, 19, 0),
(9, '053', 'Kfz-Teilkasko', NULL, 19, 0),
(10, '054', 'Kfz-Unfall', NULL, 19, 0),
(11, '055', 'Kfz-Baustein', '', 19, 0),
(12, '056', 'Kfz-Gepäck', NULL, 19, 0),
(13, '060', 'Luftfahrtversicherung', NULL, 19, 0),
(14, '070', 'Rechtsschutzversicherung', NULL, 19, 0),
(16, '080', 'Feuerversicherung', NULL, 14, 0),
(15, '077', 'Automobil-Club', NULL, 0, 0),
(18, '081', 'Feuer-Industrie-Versicherung', NULL, 14, 0),
(19, '082', 'Landwirtschaftliche Feuer-Versicherung', NULL, 14, 0),
(20, '083', 'Sonstige Feuerversicherung', NULL, 14, 0),
(21, '090', 'Einbruchdiebstahl und Raub-Versicherung', NULL, 19, 0),
(22, '100', 'Leitungswasserversicherung', NULL, 19, 0),
(23, '110', 'Glasversicherung', NULL, 19, 0),
(24, '120', 'Sturmversicherung', NULL, 19, 0),
(25, '130', 'Verbundene Hausratversicherung', NULL, 19, 0),
(26, '140', 'Verbundene Wohngebäudeversicherung', NULL, 19, 0),
(27, '150', 'Hagelversicherung', NULL, 19, 0),
(28, '160', 'Tierversicherung', NULL, 19, 0),
(29, '170', 'Technische Versicherung', NULL, 19, 0),
(30, '171', 'Maschinen-Versicherung', NULL, 19, 0),
(31, '172', 'Elektronik-Versicherung', NULL, 19, 0),
(32, '174', 'Montage-Versicherung', NULL, 19, 0),
(33, '175', 'Haushaltsgeräteversicherung', NULL, 19, 0),
(34, '176', 'Bauleistungs-Versicherung', NULL, 19, 0),
(35, '179', 'Andere technische Versicherung', '', 19, 0),
(36, '180', 'Einheitsversicherung', NULL, 19, 0),
(37, '190', 'Transportversicherung', NULL, 19, 0),
(38, '200', 'Kredit- und Kautionsversicherung', NULL, 19, 0),
(39, '210', 'Versicherung zusätzlicher Gefahren', NULL, 19, 0),
(40, '230', 'Betriebsunterbrechungs-Versicherung', NULL, 19, 0),
(41, '231', 'Feuer-Betriebsunterbrechungs-Versicherung', NULL, 19, 0),
(42, '232', 'Technische-Betriebsunterbrechungs-Versicherung', NULL, 19, 0),
(43, '240', 'Beistandsleistungsversicherung', NULL, 19, 0),
(44, '250', 'Luft- und Raumfahrzeug-Haftpflichtversicherung', NULL, 19, 0),
(45, '290', 'Sonstige Schadenversicherung', NULL, 19, 0),
(46, '300', 'Schaden- und Unfallversicherung', NULL, 19, 0),
(47, '510', 'Verkehrsservice', NULL, 19, 0),
(48, '900', 'Bausparversicherung', NULL, 19, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `stoerfaelle`
--

CREATE TABLE IF NOT EXISTS `stoerfaelle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) DEFAULT '-1',
  `mandantenId` int(11) DEFAULT '-1',
  `kundenNr` varchar(100) DEFAULT '-1',
  `vertragsId` int(11) DEFAULT '-1',
  `betreuerId` int(11) DEFAULT '-1',
  `stoerfallNr` varchar(500) DEFAULT NULL,
  `grund` varchar(1000) DEFAULT NULL,
  `eingang` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `faelligkeit` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `fristTage` tinyint(5) DEFAULT '14',
  `aufgabenId` int(11) DEFAULT '-1',
  `rueckstand` double DEFAULT '0',
  `mahnstatus` varchar(255) DEFAULT NULL,
  `kategorie` varchar(1000) DEFAULT NULL,
  `positivErledigt` tinyint(1) DEFAULT '0',
  `notiz` text,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Daten für Tabelle `stoerfaelle`
--

INSERT INTO `stoerfaelle` (`id`, `creatorId`, `mandantenId`, `kundenNr`, `vertragsId`, `betreuerId`, `stoerfallNr`, `grund`, `eingang`, `faelligkeit`, `fristTage`, `aufgabenId`, `rueckstand`, `mahnstatus`, `kategorie`, `positivErledigt`, `notiz`, `custom1`, `custom2`, `custom3`, `created`, `modified`, `status`) VALUES
(4, 1, 1, '11002', 1, 1, '60002', 'Beitragsrückstand', '2011-07-18 16:56:56', '2011-07-27 16:56:56', 14, -1, 200.21, '0', '', 0, '', '', '', '', '2011-07-18 16:58:07', '2011-07-18 17:01:04', 5),
(10, 1, 1, '11002', 2, 1, '60004', 'Beitragsrückstand', '2011-07-18 17:29:19', '2011-07-18 17:29:19', 14, 2, 100, '', '', 0, '', '', '', '', '2011-07-18 17:29:31', '2011-07-18 17:29:31', 0),
(9, 1, 1, '11002', 1, 1, '60003', 'Kündigung Gesellschaft', '2011-07-18 17:17:39', '2011-07-18 17:17:39', 14, -1, 0, '', '', 0, '', '', '', '', '2011-07-18 17:17:53', '2011-07-18 17:17:53', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `termine`
--

CREATE TABLE IF NOT EXISTS `termine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `pub` tinyint(1) NOT NULL DEFAULT '0',
  `beschreibung` varchar(255) DEFAULT NULL,
  `ort` varchar(255) DEFAULT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `kundeKennung` varchar(50) NOT NULL DEFAULT '-1',
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `vertragId` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `stoerfallId` int(11) NOT NULL DEFAULT '-1',
  `schadenId` int(11) NOT NULL DEFAULT '-1',
  `teilnehmer` varchar(255) DEFAULT NULL,
  `erinnerung` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ende` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Daten für Tabelle `termine`
--

INSERT INTO `termine` (`id`, `creatorId`, `pub`, `beschreibung`, `ort`, `tag`, `kundeKennung`, `versichererId`, `vertragId`, `benutzerId`, `stoerfallId`, `schadenId`, `teilnehmer`, `erinnerung`, `start`, `ende`, `created`, `modified`, `status`) VALUES
(1, 1, 0, 'Lebensversicherung Herr. Wäser', 'Herrenstr. 252, 81223 München', 'Öffentlich', '-1', -1, -1, -1, -1, -1, NULL, '2011-07-23 07:00:14', '2011-07-23 07:00:14', '2011-07-23 08:00:14', '2011-06-24 10:45:40', '2011-07-23 13:39:20', 0),
(2, 1, 0, 'Besprechung MP Software', 'Kalsruherweg. 26, 80641 München', 'Standard', '11002', -1, -1, -1, -1, -1, NULL, '2011-07-23 10:00:14', '2011-07-23 08:01:00', '2011-07-23 08:59:00', '2011-06-24 10:46:39', '2011-07-23 13:52:36', 0),
(3, 1, 0, 'Alg. Beratung Altersvorsorge + PKV  Fr. Grüner', 'Neuherrnstr. 27, 81137 München', 'Standard', '-1', -1, -1, -1, -1, -1, NULL, '2011-07-23 08:30:14', '2011-07-23 09:01:00', '2011-07-23 12:00:14', '2011-06-24 10:48:48', '2011-07-23 13:45:48', 0),
(4, 1, 0, 'Anruf Geburtstag Mareike', '', 'Persönlich', '-1', -1, -1, -1, -1, -1, NULL, '2011-07-23 11:00:14', '2011-07-23 11:00:14', '2011-07-23 11:30:14', '2011-06-24 10:50:27', '2011-07-23 13:53:30', 0),
(5, 1, 0, 'Teambesprechung', NULL, 'Dienstlich', '-1', -1, -1, -1, -1, -1, NULL, '2011-06-23 13:00:00', '2011-07-23 13:00:14', '2011-07-23 14:00:14', '2011-06-24 10:51:40', '2011-07-23 13:53:47', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `textbausteine`
--

CREATE TABLE IF NOT EXISTS `textbausteine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grp` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(500) NOT NULL,
  `beschreibung` text,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Daten für Tabelle `textbausteine`
--

INSERT INTO `textbausteine` (`id`, `grp`, `benutzerId`, `produktId`, `name`, `beschreibung`, `created`, `modified`, `status`) VALUES
(1, 2, -1, -1, 'Gesprächsinitiative Vermittler', 'Die Gesprächsinitiative ging vom Vermittler aus.', '2010-01-31 23:00:00', '2010-08-21 07:35:04', 0),
(2, 2, -1, -1, 'Gesprächsinitiative Kunden', 'Die Gesprächsinitiative ging vom Kunden aus.', '2010-01-31 23:00:00', '2010-08-21 07:35:04', 0),
(3, 2, -1, -1, 'Versicherungswunsch', 'Der Kunde wünscht eine XXX - Versicherung.', '2010-01-31 23:00:00', '2010-08-21 07:35:42', 0),
(4, 2, -1, -1, 'Autokauf', 'Gesprächsanlass war der Kauf eines Autos und die Überprüfung der Kfz-Risiken.', '2010-01-31 23:00:00', '2010-08-21 07:38:07', 0),
(5, 2, -1, -1, 'Betriebsänderung', 'Gesprächsanlass war eine Betriebsänderung des Kunden und die Überprüfung der Versorgungssituation des Betriebes.', '2010-01-31 23:00:00', '2010-08-21 07:38:07', 0),
(6, 2, -1, -1, 'Berufliche Veränderung', 'Gesprächsanlass war eine berufliche Änderung.', '2010-01-31 23:00:00', '2010-08-21 07:40:43', 0),
(7, 2, -1, -1, 'Existenzgründung', 'Gesprächsanlass war eine Existenzgründung durch den Kunden und die Überprüfung der Versorgungssituation.', '2010-01-31 23:00:00', '2010-08-21 07:40:43', 0),
(8, 2, -1, -1, 'Hausbau/-kauf', 'Gesprächsanlass war der Hausbau/-kauf und die Überprüfung der Versorgungssituation.', '2010-01-31 23:00:00', '2010-08-21 07:42:15', 0),
(10, 2, -1, -1, 'Geburt eines Kindes', 'Gesprächsanlass war die Geburt eines Kindes und die Überprüfung der Versorgungssituation der Familie.', '2010-01-31 23:00:00', '2010-08-21 07:44:06', 0),
(11, 2, -1, -1, 'Heirat', 'Gesprächsanlass war die Heirat des Kunden.', '2010-01-31 23:00:00', '2010-08-21 08:32:23', 0),
(12, 2, -1, -1, 'Scheidung', 'Gesprächsanlass war die Scheidung des Kunden und die damit verbundene Überprüfung der Versicherungen.', '2010-01-31 23:00:00', '2010-08-21 07:46:10', 0),
(13, 2, -1, -1, 'Umzug', 'Gesprächsanlass war ein Umzug des Kunden und die damit verbundene Überprüfung und Aktualisierung der Versicherungen.', '2010-01-31 23:00:00', '2010-08-21 07:46:10', 0),
(14, 2, -1, -1, 'Rat Abschluss', 'Empfohlen wir der Abschluss einer XXX Tarif XXX bei der XXX (Versicherungsgesellschaft).', '2010-01-31 23:00:00', '2010-08-21 07:50:12', 0),
(15, 2, -1, -1, 'Rat Begründung 1', 'Der angebotene Versicherungsvertrag deckt die in der Risikoanalyse festgestellten Risiken ab.', '2010-01-31 23:00:00', '2010-08-21 07:50:12', 0),
(16, 2, -1, -1, 'Rat Begründung 2', 'Der angebotene Versicherungsvertrag deckt die in der Risikoanalyse festgestellten Risiken nicht ab.', '2010-01-31 23:00:00', '2010-08-21 07:52:28', 0),
(17, 2, -1, -1, 'Rat Begründung 3', 'Der angebotene Versicherungsvertrag deckt die in der Risikoanalyse festgestellten Risiken mit Ausnahme von XXX ab.', '2010-01-31 23:00:00', '2010-08-21 07:52:28', 0),
(18, 2, -1, -1, 'Rat Begründung 4', 'Das Produkt weist ein ausgewogenes Preis-/Leistungsverhältnis auf.', '2010-01-31 23:00:00', '2010-08-21 07:53:56', 0),
(19, 2, -1, -1, 'Rat Begründung 5', 'Das Produkt wird dem festgestellten Bedarf des Kunden im besonderem Maß gerecht.', '2010-01-31 23:00:00', '2010-08-21 07:53:56', 0),
(20, 2, -1, -1, 'Rat Begründung 6', 'Der Kunde wünscht ausdrücklich dieses Produkt.', '2010-01-31 23:00:00', '2010-08-21 07:55:14', 0),
(21, 2, -1, -1, 'Entscheidung Annahme', 'Der Kunde nimmt die Empfehlung an.', '2010-01-31 23:00:00', '2010-08-21 08:15:34', 0),
(22, 2, -1, -1, 'Entscheidung Ablehnung', 'Der Kunde lehnt die Empfehlung ab.', '2010-01-31 23:00:00', '2010-08-21 08:04:47', 0),
(23, 2, -1, -1, 'Entscheidung Zurückstellung', 'Der Kunde stellt eine Entscheidung zu der Empfehlung vorläufig zurück.', '2010-01-31 23:00:00', '2010-08-21 08:06:18', 0),
(24, 2, -1, -1, 'Entscheidung Datum', 'Der Kunde will seine Entscheidung zu der Empfehlung bis zum XX.XX.XXXX bekannt geben.', '2010-01-31 23:00:00', '2010-08-21 08:06:18', 0),
(25, 2, -1, -1, 'Entscheidung Termin', 'Der Kunde will seine Entscheidung zu der Empfehlung beim nächstem Termin am XX.XX.XXXX treffen.', '2010-01-31 23:00:00', '2010-08-21 08:07:35', 0),
(26, 2, -1, -1, 'Entscheidung Begründung 1', 'Die angebotene Versicherung ist ihm zu teuer.', '2010-01-31 23:00:00', '2010-08-21 08:07:35', 0),
(27, 2, -1, -1, 'Entscheidung Begründung 2', 'Die angebotene Versicherung entspricht nicht seinen Wünschen und Vorstellungen.', '2010-01-31 23:00:00', '2010-08-21 08:08:42', 0),
(28, 2, -1, -1, 'Entscheidung Begründung 3', 'Das Preis-/Leistungsverhältnis entspricht nicht den Vorstellungen des Kunden.', '2010-01-31 23:00:00', '2010-08-21 08:08:42', 0),
(29, 2, -1, -1, 'Entscheidung Begründung 4', 'Es besteht bereits ausreichender Versicherungsschutz.', '2010-01-31 23:00:00', '2010-08-21 08:09:39', 0),
(30, 2, -1, -1, 'Entscheidung Begründung 5', 'Der Kunde möchte Bedenkzeit haben.', '2010-01-31 23:00:00', '2010-08-21 08:09:39', 0),
(31, 3, 1, 1, 'Produktbewertung Risikolebensversicherung L0M(DL)', 'Ein toller Bewertungstext testy', '2011-07-24 10:24:52', '2011-07-24 10:25:40', 0),
(32, 3, 1, 2, 'Produktbewertung Zukunftsrente Klassik ohne Beitragsrückzahlung bei Tod', 'test 234', '2011-07-24 10:34:04', '2011-07-24 10:34:05', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `textbausteine_grp`
--

CREATE TABLE IF NOT EXISTS `textbausteine_grp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `textbausteine_grp`
--

INSERT INTO `textbausteine_grp` (`id`, `name`, `status`) VALUES
(1, 'Allgemein', 0),
(2, 'Beratungsprotokoll', 0),
(3, 'Produktbewertungen', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `versicherer`
--

CREATE TABLE IF NOT EXISTS `versicherer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `parentName` varchar(500) DEFAULT NULL,
  `vuNummer` varchar(255) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `nameZusatz` varchar(500) DEFAULT NULL,
  `nameZusatz2` varchar(500) DEFAULT NULL,
  `kuerzel` varchar(255) DEFAULT NULL,
  `gesellschaftsNr` varchar(255) DEFAULT NULL,
  `strasse` varchar(500) DEFAULT NULL,
  `plz` varchar(100) DEFAULT NULL,
  `stadt` varchar(255) DEFAULT NULL,
  `bundesLand` varchar(255) DEFAULT NULL,
  `land` varchar(255) NOT NULL DEFAULT 'Deutschland',
  `postfach` tinyint(1) NOT NULL DEFAULT '0',
  `postfachName` varchar(255) DEFAULT NULL,
  `postfachPlz` varchar(255) DEFAULT NULL,
  `postfachOrt` varchar(255) DEFAULT NULL,
  `vermittelbar` tinyint(1) NOT NULL DEFAULT '1',
  `communication1` varchar(255) DEFAULT NULL,
  `communication2` varchar(255) DEFAULT NULL,
  `communication3` varchar(255) DEFAULT NULL,
  `communication4` varchar(255) DEFAULT NULL,
  `communication5` varchar(255) DEFAULT NULL,
  `communication6` varchar(255) DEFAULT NULL,
  `communication1Type` tinyint(5) NOT NULL DEFAULT '0',
  `communication2Type` tinyint(5) NOT NULL DEFAULT '1',
  `communication3Type` tinyint(5) NOT NULL DEFAULT '4',
  `communication4Type` tinyint(5) NOT NULL DEFAULT '7',
  `communication5Type` tinyint(5) NOT NULL DEFAULT '10',
  `communication6Type` tinyint(5) NOT NULL DEFAULT '14',
  `comments` text,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `custom4` varchar(1000) DEFAULT NULL,
  `custom5` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vuNummer` (`vuNummer`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=623 ;

--
-- Daten für Tabelle `versicherer`
--

INSERT INTO `versicherer` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, NULL, NULL, '5448', 'SCHWEIZER NATIONAL VERSICHERUNGS-AKTIENGESELLSCHAFT IN DEUTSCHLAND', NULL, NULL, NULL, NULL, 'Querstraße 8-10', '60322', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2011-06-21 09:54:08', 0),
(2, NULL, NULL, '1001', 'AachenMünchener Lebensversicherung AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(3, NULL, NULL, '5342', 'AachenMünchener Versicherung AG', NULL, NULL, NULL, NULL, 'Aureliusstraße 2', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(4, NULL, NULL, '5135', 'ADAC Autoversicherung AG', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(5, NULL, NULL, '5826', 'ADAC-RECHTSSCHUTZ VERSICHERUNGS -AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(6, NULL, NULL, '5498', 'ADAC-SCHUTZBRIEF VERSICHERUNGS -AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(7, NULL, NULL, '5581', 'ADLER Versicherung AG', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(8, NULL, NULL, '5809', 'AdvoCard Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 81', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(9, NULL, NULL, '5035', 'AGILA Haustierversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Breite Straße 6 - 8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(10, NULL, NULL, '1318', 'Aioi Life Insurance of Europe Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Toyota-Allee 5', '50858', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(11, NULL, NULL, '2268', 'Allgemeine Rentenanstalt Pensionskasse AG', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(12, NULL, NULL, '3080', 'Allgemeine Sterbekasse Essen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Hollestraße 1 Haus der Technik', '45127', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(13, NULL, NULL, '3101', 'Allgemeine Sterbekasse Oberhausen/Duisburg (ehem. Sterbekasse der Belegschaft Thyssen Stahl AG)', NULL, NULL, NULL, NULL, 'Zur Eisenhütte 7', '46047', 'Oberhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(14, NULL, NULL, '5370', 'Allianz Global Corporate & Specialty AG', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(15, NULL, NULL, '1006', 'Allianz Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(16, NULL, NULL, '3304', 'Allianz Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(17, NULL, NULL, '2273', 'Allianz Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(18, NULL, NULL, '4034', 'Allianz Private Krankenversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(19, NULL, NULL, '6949', 'Allianz SE', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(20, NULL, NULL, '5312', 'Allianz Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(21, NULL, NULL, '2018', 'Allianz Versorgungskasse Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(22, NULL, NULL, '5825', 'Allrecht Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Liesegangstraße 15', '40211', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(23, NULL, NULL, '5785', 'almeda Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 116 a', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(24, NULL, NULL, '1007', 'ALTE LEIPZIGER Lebensversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(25, NULL, NULL, '3320', 'ALTE LEIPZIGER Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(26, NULL, NULL, '2276', 'ALTE LEIPZIGER Pensionskasse AG', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(27, NULL, NULL, '5405', 'Alte Leipziger Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(28, NULL, NULL, '4142', 'ALTE OLDENBURGER Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Moorgärten 12-14', '49377', 'Vechta', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(29, NULL, NULL, '4010', 'Alte Oldenburger Krankenversicherung Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Moorgärten 12 - 14', '49377', 'Vechta', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(30, NULL, NULL, '2088', 'Alters- und Hinterbliebenen -Versicherung der Technischen Überwachungs -Vereine-VVaG', NULL, NULL, NULL, NULL, 'Kurfürstenstraße 56', '45138', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(31, NULL, NULL, '2004', 'Altersversorgungskasse des Kaiserswerther Verbandes deutscher Diakonissen -Mutterhäuser', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(32, NULL, NULL, '5068', 'AMMERLÄNDER VERSICHERUNG Versicherungsverein a.G. (VVaG)', NULL, NULL, NULL, NULL, 'Bahnhofstraße 8', '26655', 'Westerstede', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(33, NULL, NULL, '2105', 'Angest.-Pensionskasse der dt. Geschäftsbetriebe der Georg Fischer Aktiengesellschaft Schaffhausen (Schweiz)', NULL, NULL, NULL, NULL, 'Julius-Bührer-Straße 12', '78224', 'Singen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(34, NULL, NULL, '5800', 'ARAG Allgemeine Rechtsschutz-Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(35, NULL, NULL, '5455', 'ARAG Allgemeine Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(36, NULL, NULL, '4112', 'ARAG Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(37, NULL, NULL, '1035', 'ARAG Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(38, NULL, NULL, '2231', 'Arbeiter-Pensionskasse der Firma Villeroy & Boch, Aktiengesellschaft Mettlach/Saar - VVaG', NULL, NULL, NULL, NULL, 'Postfach 11 20', '66688', 'Mettlach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(39, NULL, NULL, '1181', 'ASPECTA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(40, NULL, NULL, '1303', 'Asstel Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schanzenstraße 28', '51063', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(41, NULL, NULL, '5397', 'ASSTEL Sachversicherung AG', NULL, NULL, NULL, NULL, 'Schanzenstraße 28', '51063', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(42, NULL, NULL, '4129', 'Augenoptiker Ausgleichskasse VVaG (AKA)', NULL, NULL, NULL, NULL, 'Ruhrallee 9 (Ellipson)', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(43, NULL, NULL, '5801', 'AUXILIA Rechtsschutz-Versicherungs- Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Uhlandstr. 7', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(44, NULL, NULL, '5132', 'Avetas Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schöne Aussicht 8a', '61348', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(45, NULL, NULL, '3106', 'AVK Allgemeine Versicherungskasse VVaG -Todesfallversicherung', NULL, NULL, NULL, NULL, 'Langestraße 63', '27749', 'Delmenhorst', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(46, NULL, NULL, '5077', 'AXA ART Versicherung AG', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(47, NULL, NULL, '4095', 'AXA Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(48, NULL, NULL, '1020', 'AXA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia-Allee 10 - 20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(49, NULL, NULL, '5515', 'AXA Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia-Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(50, NULL, NULL, '2186', 'Babcock Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Duisburger Straße 375', '46049', 'Oberhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(51, NULL, NULL, '2251', 'Baden-Badener Pensionskasse', NULL, NULL, NULL, NULL, 'Quettigstraße 23 Haus Quettig', '76530', 'Baden-Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(52, NULL, NULL, '5792', 'Baden-Badener Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schlackenbergstraße 20', '66386', 'St. Ingbert', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(53, NULL, NULL, '5593', 'Badische Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(54, NULL, NULL, '5838', 'Badische Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(55, NULL, NULL, '5316', 'Badischer Gemeinde-Versicherungs-Verband (BGV)', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(56, NULL, NULL, '5317', 'Barmenia Allgemeine Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(57, NULL, NULL, '4042', 'Barmenia Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(58, NULL, NULL, '1011', 'Barmenia Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(59, NULL, NULL, '2114', 'BASF Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Carl-Bosch-Straße 127-129', '67056', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(60, NULL, NULL, '3034', 'BASF Sterbekasse VVaG', NULL, NULL, NULL, NULL, 'Carl-Bosch-Straße 127-129', '67056', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(61, NULL, NULL, '5318', 'Basler  Versicherung AG Direktion für Deutschland', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(62, NULL, NULL, '1012', 'Basler Leben AG Direktion für Deutschland Herr Dr. Frank Grund', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(63, NULL, NULL, '5633', 'Basler Securitas Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(64, NULL, NULL, '3143', 'BAVARIA Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(65, NULL, NULL, '3019', 'Bayer Beistandskasse', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(66, NULL, NULL, '1013', 'Bayerische Beamten Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Str. 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(67, NULL, NULL, '5310', 'Bayerische Beamten Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(68, NULL, NULL, '4134', 'Bayerische Beamtenkrankenkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(69, NULL, NULL, '5319', 'Bayerische Hausbesitzer -Versicherungs-Gesellschaft a.G.', NULL, NULL, NULL, NULL, 'Sonnenstraße 13/V', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(70, NULL, NULL, '5043', 'Bayerische Landesbrandversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(71, NULL, NULL, '5324', 'Bayerischer Versicherungsverband Versicherungsaktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(72, NULL, NULL, '1015', 'Bayern-Versicherung Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstr. 53', '81535', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(73, NULL, NULL, '2017', 'Bayer-Pensionskasse', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(74, NULL, NULL, '3105', 'BERGBAU-STERBEKASSE - Vorsorge-Versicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Shamrockring 1', '44623', 'Herne', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(75, NULL, NULL, '5326', 'Bergische Brandversicherung Allgemeine Feuerversicherung V.a.G.', NULL, NULL, NULL, NULL, 'Hofkamp 86', '42103', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(76, NULL, NULL, '2151', 'Betriebspensionskasse der Firma Carl Schenck AG VVaG Darmstadt', NULL, NULL, NULL, NULL, 'Landwehrstraße 55', '64293', 'Darmstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(77, NULL, NULL, '5146', 'BGV-Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(78, NULL, NULL, '3140', 'Bochumer Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kortumstraße 102-104', '44787', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(79, NULL, NULL, '3313', 'Bosch Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidehofstraße 31', '70184', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(80, NULL, NULL, '5098', 'Bruderhilfe Sachversicherung AG im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Kölnische Straße 108-112', '34119', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(81, NULL, NULL, '3327', 'BVV Pensionsfonds des Bankgewerbes AG', NULL, NULL, NULL, NULL, 'Kurfürstendamm 111/113', '10711', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(82, NULL, NULL, '2048', 'BVV Versicherungsverein des Bankgewerbes a.G.', NULL, NULL, NULL, NULL, 'Kurfürstendamm 111-113', '10711', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(83, NULL, NULL, '4004', 'Central Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaring 40 - 50', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(84, NULL, NULL, '5547', 'CG CAR-GARANTIE Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gündlinger Straße 12', '79111', 'Freiburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(85, NULL, NULL, '3301', 'CHEMIE Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Kaufingerstraße 9', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(86, NULL, NULL, '5861', 'Coface Kreditversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Isaac-Fulda-Allee 1', '55124', 'Mainz a Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(87, NULL, NULL, '4118', 'Concordia Krankenversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(88, NULL, NULL, '1122', 'Concordia Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(89, NULL, NULL, '5831', 'Concordia Rechtsschutz-Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 5', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(90, NULL, NULL, '5338', 'Concordia Versicherungs-Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(91, NULL, NULL, '5339', 'Condor Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(92, NULL, NULL, '1021', 'Condor Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(93, NULL, NULL, '5004', 'CONSTANTIA Versicherungen a.G.', NULL, NULL, NULL, NULL, 'Große Straße 40', '26721', 'Emden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(94, NULL, NULL, '4001', 'Continentale Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(95, NULL, NULL, '1078', 'Continentale Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Baierbrunner Straße 31-33', '81379', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(96, NULL, NULL, '5340', 'Continentale Sachversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(97, NULL, NULL, '1022', 'COSMOS Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halbergstraße 50-60', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(98, NULL, NULL, '5552', 'Cosmos Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halbergstraße 50-60', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(99, NULL, NULL, '5802', 'D.A.S. Deutscher Automobil Schutz  Allgemeine Rechtsschutz-Versicherungs-AG', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 2', '81728', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(100, NULL, NULL, '5343', 'DA Deutsche Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Oberstedter Straße 14', '61440', 'Oberursel (Taunus)', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(101, NULL, NULL, '5771', 'DARAG Deutsche Versicherungs -und Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gustav-Adolf-Straße 130', '13086', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(102, NULL, NULL, '5311', 'DBV Deutsche Beamtenversicherung AG', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65178', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(103, NULL, NULL, '1146', 'DBV Deutsche Beamtenversicherung Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(104, NULL, NULL, '5549', 'Debeka Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56073', 'Koblenz am Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(105, NULL, NULL, '4028', 'Debeka Krankenversicherungsverein auf Gegenseitigkeit Sitz Koblenz am Rhein', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(106, NULL, NULL, '1023', 'Debeka Lebensversicherungsverein auf Gegenseitigkeit Sitz Koblenz am Rhein', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Str. 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(107, NULL, NULL, '2256', 'Debeka Pensionskasse AG', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(108, NULL, NULL, '2038', 'Debeka Zusatzversorgungskasse VaG', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(109, NULL, NULL, '1167', 'Delta Direkt Lebensversicherung Aktiengesellschaft München', NULL, NULL, NULL, NULL, 'Ottostraße 16', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(110, NULL, NULL, '1017', 'Delta Lloyd Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gustav-Stresemann-Ring 7 - 9', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(111, NULL, NULL, '2279', 'Delta Lloyd Pensionskasse AG', NULL, NULL, NULL, NULL, 'Wittelsbacher Straße 1', '65185', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(112, NULL, NULL, '5632', 'Delvag Luftfahrtversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Von-Gablenz-Str. 2-6', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(113, NULL, NULL, '6950', 'Delvag Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Von-Gablenz-Straße 2-6', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(114, NULL, NULL, '5803', 'DEURAG Deutsche Rechtsschutz -Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Abraham-Lincoln-Straße 3', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(115, NULL, NULL, '1180', 'Deutsche Ärzteversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Börsenplatz 1', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(116, NULL, NULL, '5084', 'deutsche internet versicherung aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(117, NULL, NULL, '1148', 'Deutsche Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An den Treptowers 3', '12435', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(118, NULL, NULL, '3330', 'Deutsche Post Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Straße 20', '53113', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(119, NULL, NULL, '5631', 'Deutsche Rhederei Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Bergstraße 26', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(120, NULL, NULL, '6907', 'Deutsche Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(121, NULL, NULL, '2211', 'Deutsche Steuerberater-Versicherung - Pensionskasse des steuerberatenden Berufs VVaG', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 24', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(122, NULL, NULL, '3303', 'Deutscher Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25-33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(123, NULL, NULL, '5859', 'Deutscher Reisepreis -Sicherungsverein VVaG', NULL, NULL, NULL, NULL, 'Rosenheimer Str.116', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(124, NULL, NULL, '4013', 'DEUTSCHER RING Krankenversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(125, NULL, NULL, '1028', 'DEUTSCHER RING Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(126, NULL, NULL, '5350', 'DEUTSCHER RING Sachversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(127, NULL, NULL, '1136', 'DEVK Allgemeine Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(128, NULL, NULL, '5513', 'DEVK Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(129, NULL, NULL, '1025', 'DEVK Deutsche Eisenbahn Ver sicherung Lebensversicherungs verein a.G. Betr. Sozialein richtung der Deutschen Bahn AG', NULL, NULL, NULL, NULL, 'Riehler Str. 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(130, NULL, NULL, '5344', 'DEVK Deutsche Eisenbahn Versicherung Sach- und HUK -Versicherungsverein a.G. Betriebliche Sozialeinrichtung der Deutschen Bahn', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(131, NULL, NULL, '4131', 'DEVK Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(132, NULL, NULL, '3314', 'DEVK Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(133, NULL, NULL, '5829', 'DEVK Rechtsschutz -Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(134, NULL, NULL, '6973', 'DEVK Rückversicherungs- und Beteiligungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(135, NULL, NULL, '5129', 'DFV Deutsche Familienversicherung AG', NULL, NULL, NULL, NULL, 'Beethovenstraße 71', '60325', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(136, NULL, NULL, '1113', 'Dialog Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halderstraße 29', '86150', 'Augsburg, Bay', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(137, NULL, NULL, '3073', 'Die Vorsorge Sterbekasse der Werksangehörigen der Degussa Aktiengesellschaft Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lipper Weg 190', '45764', 'Marl', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(138, NULL, NULL, '6978', 'Diehl Assekuranz Rückversicherungs- und Vermittlungs-AG', NULL, NULL, NULL, NULL, 'Stephanstraße 49', '90478', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(139, NULL, NULL, '5055', 'Direct Line Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rheinstraße 7 a', '14513', 'Teltow', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(140, NULL, NULL, '1110', 'Direkte Leben Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(141, NULL, NULL, '4044', 'DKV Deutsche Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 300', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(142, NULL, NULL, '5834', 'DMB Rechtsschutz-Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Bonner Straße 323', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(143, NULL, NULL, '5328', 'DOCURA VVaG', NULL, NULL, NULL, NULL, 'Königsallee 57', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(144, NULL, NULL, '5522', 'Dolleruper Freie Brandgilde', NULL, NULL, NULL, NULL, 'Am Wasserwerk 3', '24972', 'Steinbergkirche', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(145, NULL, NULL, '2271', 'DPK Deutsche Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(146, NULL, NULL, '2188', 'Dr.-Richard-Bruhn-Hilfe-Alters versorgung d. AUTO UNION GmbH Versicherungsverein auf Gegenseitigk. (VVaG) Ingolst./Donau', NULL, NULL, NULL, NULL, 'Auto-Union-Straße', '85045', 'Ingolstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(147, NULL, NULL, '2121', 'Dresdener Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Ludwig-Crößmann-Straße 2', '95326', 'Kulmbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(148, NULL, NULL, '4115', 'Düsseldorfer Versicherung Krankenversicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Konrad-Adenauer-Platz 12', '40210', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(149, NULL, NULL, '6908', 'E+S Rückversicherung AG', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 50', '30629', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(150, NULL, NULL, '5141', 'East-West Assekuranz AG', NULL, NULL, NULL, NULL, 'Mauerstraße 83/84', '10117', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(151, NULL, NULL, '4121', 'ENVIVAS Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gereonswall 68', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(152, NULL, NULL, '4126', 'ERGO Direkt Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(153, NULL, NULL, '1130', 'ERGO Direkt Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(154, NULL, NULL, '5562', 'ERGO Direkt Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(155, NULL, NULL, '1184', 'ERGO Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Überseering 45', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(156, NULL, NULL, '3322', 'ERGO Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(157, NULL, NULL, '5472', 'ERGO Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(158, NULL, NULL, '3068', 'Erste Kieler Beerdigungskasse', NULL, NULL, NULL, NULL, 'Kronshagener Weg 8', '24103', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(159, NULL, NULL, '5852', 'Euler Hermes Kreditversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Friedensallee 254', '22763', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(160, NULL, NULL, '5038', 'EURO-AVIATION Versicherungs-AG', NULL, NULL, NULL, NULL, 'Hochallee 80', '20149', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(161, NULL, NULL, '5541', 'EUROP ASSISTANCE Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Infanteriestraße 11', '80797', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(162, NULL, NULL, '4089', 'EUROPA Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(163, NULL, NULL, '1107', 'EUROPA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(164, NULL, NULL, '5508', 'EUROPA Sachversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(165, NULL, NULL, '5356', 'Europäische Reiseversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Vogelweidestraße 5', '81677', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(166, NULL, NULL, '5148', 'European Warranty Partners Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Georgswall 7', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(167, NULL, NULL, '5097', 'EXTREMUS Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 75', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(168, NULL, NULL, '5656', 'F. Laeisz Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Trostbrücke 1', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(169, NULL, NULL, '5470', 'Fahrlehrerversicherung Verein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 5', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(170, NULL, NULL, '1310', 'FAMILIENFÜRSORGE Lebensversicherung AG im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(171, NULL, NULL, '1175', 'Familienschutz Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(172, NULL, NULL, '5357', 'Feuer- und Einbruchschaden kasse der BBBank in Karlsruhe, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Herrenstraße 2-10', '76133', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(173, NULL, NULL, '3091', 'Feuerbestattungsverein VVaG, Selb/Bayern', NULL, NULL, NULL, NULL, 'Tschirnhausweg 6', '95100', 'Selb', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(174, NULL, NULL, '5024', 'Feuersozietät Berlin Brandenburg Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4-5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(175, NULL, NULL, '1162', 'Fortis Deutschland Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Herzberger Landstraße 25', '37085', 'Göttingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(176, NULL, NULL, '4053', 'Freie Arzt- und Medizinkasse der Angehörigen der Berufsfeuerwehr und der Polizei VVaG', NULL, NULL, NULL, NULL, 'Friedrich-Ebert-Anlage 3', '60327', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(177, NULL, NULL, '6984', 'Freudenberg Rückversicherung AG', NULL, NULL, NULL, NULL, 'Höhnerweg 2-4', '69469', 'Weinheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(178, NULL, NULL, '3151', 'Fürsorgekasse von 1908 vormals Sterbekasse der Neuapostolischen Kirche des Landes Nordrhein-Westfalen', NULL, NULL, NULL, NULL, 'Uerdinger Straße 323', '47800', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(179, NULL, NULL, '5505', 'GARANTA Versicherungs- Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstr. 100', '90482', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(180, NULL, NULL, '5346', 'Gartenbau-Versicherung VVaG', NULL, NULL, NULL, NULL, 'Von-Frerichs-Straße 8', '65191', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(181, NULL, NULL, '3063', 'GE.BE.IN Versicherungen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Nordstraße 5-11', '28217', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(182, NULL, NULL, '5539', 'Gebäudeversicherungsgilde für Föhr,Amrum und Halligen', NULL, NULL, NULL, NULL, 'Lung Jaat 11', '25938', 'Utersum/Föhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(183, NULL, NULL, '5365', 'GEGENSEITIGKEIT Versicherung Oldenburg', NULL, NULL, NULL, NULL, 'Osterstraße 15', '26122', 'Oldenburg (Oldb)', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(184, NULL, NULL, '5366', 'Gemeinnützige Haftpflichtversicherungsanstalt der Gartenbau-Berufsgenossenschaft', NULL, NULL, NULL, NULL, 'Frankfurter Straße 126', '34121', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(185, NULL, NULL, '6918', 'General Reinsurance AG', NULL, NULL, NULL, NULL, 'Theodor-Heuss-Ring 11', '50668', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(186, NULL, NULL, '6971', 'Generali Deutschland Holding AG', NULL, NULL, NULL, NULL, 'Tunisstraße 19-23', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(187, NULL, NULL, '2267', 'Generali Deutschland Pensionskasse AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(188, NULL, NULL, '3300', 'Generali Deutschland Pensor Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Oeder Weg 151', '60318', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(189, NULL, NULL, '1139', 'Generali Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(190, NULL, NULL, '5473', 'Generali Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(191, NULL, NULL, '2135', 'Geno Pensionskasse VVaG, Karlsruhe', NULL, NULL, NULL, NULL, 'Lauterbergstraße 1', '76137', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(192, NULL, NULL, '2044', 'Gerling Versorgungskasse', NULL, NULL, NULL, NULL, 'Hohenzollernring 72', '50672', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(193, NULL, NULL, '5033', 'GERMAN ASSISTANCE VERSICHERUNG AG', NULL, NULL, NULL, NULL, 'Große Viehstr. 5-7', '48653', 'Coesfeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(194, NULL, NULL, '3075', 'Gerther Versicherungs -Gemeinschaft Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Lothringer Straße 13', '44805', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(195, NULL, NULL, '5589', 'GGG KraftfahrzeugReparaturkosten-Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Magdeburger Straße 7', '30880', 'Laatzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(196, NULL, NULL, '5007', 'Glasschutzkasse a.G. von 1923 zu Hamburg', NULL, NULL, NULL, NULL, 'Bei dem Neuen Krahn 2', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(197, NULL, NULL, '6912', 'GLOBALE Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Im Mediapark 4b', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(198, NULL, NULL, '5858', 'Gothaer Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gothaer Allee 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(199, NULL, NULL, '6994', 'Gothaer Finanzholding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(200, NULL, NULL, '4119', 'Gothaer Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(201, NULL, NULL, '1108', 'Gothaer Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(202, NULL, NULL, '2255', 'Gothaer Pensionskasse AG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(203, NULL, NULL, '5372', 'GOTHAER Versicherungsbank VVaG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(204, NULL, NULL, '2111', 'Grün + Bilfinger Wohlfahrts -und Pensionskasse a.G.', NULL, NULL, NULL, NULL, 'Carl-Reiß-Platz 1 - 5', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(205, NULL, NULL, '5485', 'GRUNDEIGENTÜMER-VERSICHERUNG Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Große Bäckerstraße 7', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(206, NULL, NULL, '5469', 'GVV-Kommunalversicherung, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Aachener Straße 952-958', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(207, NULL, NULL, '5585', 'GVV-Privatversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 952-958', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(208, NULL, NULL, '5044', 'Haftpflichtgemeinschaft Deutscher Nahverkehrs- und Versorgungsunternehmen (HDN)', NULL, NULL, NULL, NULL, 'Arndtstraße 26', '44787', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(209, NULL, NULL, '5374', 'Haftpflichtkasse Darmstadt - Haftpflichtversicherung des Deutschen Hotel- und Gaststättengewerbes - VVaG', NULL, NULL, NULL, NULL, 'Arheilger Weg 5', '64380', 'Roßdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(210, NULL, NULL, '5445', 'Hagelgilde Versicherungs-Verein a.G. gegr. 1811', NULL, NULL, NULL, NULL, 'Hof Altona', '23730', 'Sierksdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(211, NULL, NULL, '5557', 'HÄGER VERSICHERUNGSVEREIN auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Engerstraße 119', '33824', 'Werther', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(212, NULL, NULL, '4043', 'HALLESCHE Krankenversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Reinsburgstraße 10', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(213, NULL, NULL, '5011', 'Hamburger Beamten-Feuer -und Einbruchskasse', NULL, NULL, NULL, NULL, 'Hermannstraße 46', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(214, NULL, NULL, '5032', 'Hamburger Feuerkasse Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kleiner Burstah 6-10', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(215, NULL, NULL, '5756', 'HAMBURGER HOF Versicherungs -Aktiengesellschaft Im Hause EON Risk Consulting', NULL, NULL, NULL, NULL, 'Kennedydamm 17', '40476', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(216, NULL, NULL, '6917', 'Hamburger Internationale Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halstenbeker Weg 96a', '25462', 'Rellingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(217, NULL, NULL, '1040', 'Hamburger Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Wittelsbacherstraße 1', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(218, NULL, NULL, '5012', 'Hamburger Lehrer-Feuerkasse', NULL, NULL, NULL, NULL, 'Müssenredder 96c', '22399', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(219, NULL, NULL, '2001', 'HAMBURGER PENSIONSKASSE VON 1905 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(220, NULL, NULL, '2247', 'Hamburger Pensionsrückdeckungskasse VVaG', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(221, NULL, NULL, '2260', 'Hamburg-Mannheimer Pensionskasse AG', NULL, NULL, NULL, NULL, 'Überseering 45', '22287', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(222, NULL, NULL, '5828', 'Hamburg-Mannheimer Rechtsschutzversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Überseering 45', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(223, NULL, NULL, '6941', 'Hannover Rückversicherung AG', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 50', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(224, NULL, NULL, '2249', 'Hannoversche Alterskasse  VVaG', NULL, NULL, NULL, NULL, 'Pelikanplatz 23', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(225, NULL, NULL, '5131', 'Hannoversche Direktversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(226, NULL, NULL, '1312', 'Hannoversche Lebensversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(227, NULL, NULL, '2246', 'Hannoversche Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Pelikanplatz 23', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(228, NULL, NULL, '6935', 'Hanseatica Rückversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Stadthausbrücke 12', '20355', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(229, NULL, NULL, '5754', 'Hanse-Marine-Versicherung AG', NULL, NULL, NULL, NULL, 'Großer Grasbrook 10', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(230, NULL, NULL, '5501', 'HanseMerkur Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20352', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(231, NULL, NULL, '4144', 'HanseMerkur Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(232, NULL, NULL, '4018', 'HanseMerkur Krankenversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(233, NULL, NULL, '1114', 'HanseMerkur Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(234, NULL, NULL, '5496', 'HanseMerkur Reiseversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(235, NULL, NULL, '4122', 'HanseMerkur Speziale Krankenversicherung', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(236, NULL, NULL, '1192', 'HanseMerkur24 Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(237, NULL, NULL, '5544', 'Harsewinkeler Versicherungsverein auf Gegenseitigkeit zu Harsewinkel', NULL, NULL, NULL, NULL, 'Tecklenburger Weg 1', '33428', 'Harsewinkel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(238, NULL, NULL, '5085', 'HDI Direkt Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(239, NULL, NULL, '5377', 'HDI Haftpflichtverband der Deutschen Industrie Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(240, NULL, NULL, '5512', 'HDI-Gerling Firmen und Privat Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(241, NULL, NULL, '6989', 'HDI-Gerling Friedrich Wilhelm Rückversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(242, NULL, NULL, '5096', 'HDI-Gerling Industrie Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(243, NULL, NULL, '1033', 'HDI-Gerling Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(244, NULL, NULL, '3306', 'HDI-Gerling Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(245, NULL, NULL, '2264', 'HDI-Gerling Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(246, NULL, NULL, '5827', 'HDI-Gerling Rechtsschutz Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Günther-Wagner-Allee 14', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(247, NULL, NULL, '6988', 'HDI-Gerling Welt Service AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(248, NULL, NULL, '2157', 'HEAG Pensionszuschusskasse Versicherungsverein auf Gegenseitigkeit, Darmstadt', NULL, NULL, NULL, NULL, 'Luisenplatz 5A', '64283', 'Darmstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(249, NULL, NULL, '1158', 'Heidelberger Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Forum 7', '69126', 'Heidelberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(250, NULL, NULL, '5596', 'HELVETIA INTERNATIONAL Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 56-58', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(251, NULL, NULL, '1137', 'HELVETIA schweizerische Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Weißadlergasse 2', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(252, NULL, NULL, '5384', 'Helvetia Schweizerische Versicherungsgesellschaft AG Direktion für Deutschland Herrn Prof.Dr. Wolfram Wrabetz', NULL, NULL, NULL, NULL, 'Berliner Straße 56 - 58', '60311', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(253, NULL, NULL, '3031', 'Hilfskasse BVG', NULL, NULL, NULL, NULL, 'Holzmarktstraße 15-17', '10179', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(254, NULL, NULL, '3092', 'Hinterbliebenenkasse der Heilberufe Versicherungsverein a.G. in München', NULL, NULL, NULL, NULL, 'Arcisstraße 50', '80799', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(255, NULL, NULL, '6992', 'Hochrhein Internationale Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Stemmerstraße 14', '78266', 'Büsingen am Hochrhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(256, NULL, NULL, '2250', 'Höchster Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(257, NULL, NULL, '3028', 'Höchster Sterbekasse VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65929', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(258, NULL, NULL, '2040', 'Hoffmann''s Pensions- und Unterstützungskasse', NULL, NULL, NULL, NULL, 'Hoffmannstraße 16', '32105', 'Bad Salzuflen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(259, NULL, NULL, '5126', 'Hübener Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ballindamm 37', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(260, NULL, NULL, '5086', 'HUK24 AG', NULL, NULL, NULL, NULL, 'Willi-Hussong-Straße 2', '96442', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(261, NULL, NULL, '5375', 'HUK-COBURG Haftpflicht-Unterstützungs-Kasse kraftfahrender Beamter Deutschlands a.G. in Coburg', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(262, NULL, NULL, '1055', 'HUK-COBURG Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(263, NULL, NULL, '5521', 'HUK-COBURG-Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(264, NULL, NULL, '6982', 'HUK-COBURG-Holding AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96444', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(265, NULL, NULL, '4117', 'HUK-COBURG-Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96444', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(266, NULL, NULL, '5818', 'HUK-COBURG-Rechtsschutzversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(267, NULL, NULL, '5083', 'HVAG Hamburger Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Grimm 14', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(268, NULL, NULL, '3329', 'HVB Trust Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Arabellastraße 12', '81925', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(269, NULL, NULL, '2241', 'IBM Deutschland Pensionskasse Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Am Fichtenberg 1', '71083', 'Herrenberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(270, NULL, NULL, '1047', 'Ideal Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Kochstraße 26', '10969', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(271, NULL, NULL, '5573', 'IDEAL Versicherung AG', NULL, NULL, NULL, NULL, 'Kochstraße 66', '10969', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(272, NULL, NULL, '1048', 'IDUNA Vereinigte Lebensversicherung aG für Handwerk, Handel und Gewerbe', NULL, NULL, NULL, NULL, 'Neue Rabenstr. 15 - 19', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(273, NULL, NULL, '6993', 'Incura AG', NULL, NULL, NULL, NULL, 'Binger Straße 173', '55216', 'Ingelheim am Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(274, NULL, NULL, '5546', 'Inter Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(275, NULL, NULL, '4031', 'INTER Krankenversicherung aG', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(276, NULL, NULL, '1330', 'INTER Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(277, NULL, NULL, '5057', 'Interlloyd Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(278, NULL, NULL, '1119', 'InterRisk Lebensversicherungs-AG Vienna Insurance Group', NULL, NULL, NULL, NULL, 'Karl-Bosch-Straße 5', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(279, NULL, NULL, '5780', 'InterRisk Versicherungs-AG Vienna Insurance Group', NULL, NULL, NULL, NULL, 'Karl-Bosch-Straße 5', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(280, NULL, NULL, '5393', 'ISSELHORSTER Versicherung V.a.G.', NULL, NULL, NULL, NULL, 'Haller Straße 90', '33334', 'Gütersloh', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(281, NULL, NULL, '1128', 'Itzehoer Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(282, NULL, NULL, '5401', 'Itzehoer Versicherung/Brandgilde von 1691 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(283, NULL, NULL, '5078', 'Janitos Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Im Breitspiel 2-4', '69126', 'Heidelberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(284, NULL, NULL, '5812', 'Jurpartner Rechtsschutz- Versicherung AG', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(285, NULL, NULL, '3022', 'Justiz-Versicherungskasse Lebensversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Drosselweg 44', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(286, NULL, NULL, '1045', 'Karlsruher Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Friedrich-Scholl-Platz', '76137', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(287, NULL, NULL, '6925', 'Kieler Rückversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Von-der-Goltz-Allee 93', '24113', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(288, NULL, NULL, '2254', 'Kölner Pensionskasse Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Dürener Straße 341', '50935', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(289, NULL, NULL, '5396', 'Kölnische Hagel-Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Wilhelmstr. 25', '35392', 'Gießen, Lahn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(290, NULL, NULL, '3008', 'KölnVorsorge -Sterbeversicherung VVaG', NULL, NULL, NULL, NULL, 'Unter Käster 1', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(291, NULL, NULL, '4104', 'Krankenunterstützungskasse der Berufsfeuerwehr Hannover', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 60 B', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(292, NULL, NULL, '5058', 'KRAVAG-ALLGEMEINE Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(293, NULL, NULL, '6968', 'KRAVAG-HOLDING Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(294, NULL, NULL, '5080', 'KRAVAG-LOGISTIC Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(295, NULL, NULL, '5399', 'KRAVAG-SACH Versicherung des Deutschen Kraftverkehrs Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(296, NULL, NULL, '5534', 'KS Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Uhlandstraße 7', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(297, NULL, NULL, '4011', 'Landeskrankenhilfe V.V.a.G.', NULL, NULL, NULL, NULL, 'Uelzener Straße 120', '21335', 'Lüneburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(298, NULL, NULL, '1054', 'Landeslebenshilfe V.V.a.G.', NULL, NULL, NULL, NULL, 'Uelzener Str. 120', '21335', 'Lüneburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(299, NULL, NULL, '5362', 'Landesschadenhilfe Versicherung VaG', NULL, NULL, NULL, NULL, 'Vogteistraße 3', '29683', 'Fallingbostel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(300, NULL, NULL, '5400', 'Landschaftliche Brandkasse Hannover', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(301, NULL, NULL, '5264', 'Lauenburg-Alslebener Schiffsversicherung Verein a.G.', NULL, NULL, NULL, NULL, 'Elbstraße 52', '21481', 'Lauenburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(302, NULL, NULL, '5404', 'LBN -Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Groß-Buchholzer Kirchweg 49', '30655', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(303, NULL, NULL, '1062', 'Lebensversicherung von 1871 auf Gegenseitigkeit München', NULL, NULL, NULL, NULL, 'Maximiliansplatz 5', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(304, NULL, NULL, '5013', 'Lehrer-Feuerversicherungsverein für Schleswig-Holstein, Hamburg und MecklenburgVorpommern', NULL, NULL, NULL, NULL, 'Helsinkistraße 70', '24109', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(305, NULL, NULL, '4051', 'LIGA Krankenversicherung katholischer Priester Versicherungsverein auf Gegenseitigkeit Regensburg', NULL, NULL, NULL, NULL, 'Dr. Theobald-Schrems-Straße 3', '93055', 'Regensburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(306, NULL, NULL, '3321', 'Lippische Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Simon-August-Straße 2', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(307, NULL, NULL, '4103', 'Lohnfortzahlungskasse Aurich VVaG', NULL, NULL, NULL, NULL, 'Lambertistraße 16', '26603', 'Aurich', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(308, NULL, NULL, '4102', 'Lohnfortzahlungskasse Leer VVaG', NULL, NULL, NULL, NULL, 'Brunnenstraße 22', '26789', 'Leer', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(309, NULL, NULL, '6957', 'Lucura Rückversicherungs AG', NULL, NULL, NULL, NULL, 'Wöhlerstraße 19', '67063', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(310, NULL, NULL, '4109', 'LVM Krankenversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(311, NULL, NULL, '5402', 'LVM Landwirtschaftlicher Versicherungsverein Münster a.G.', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster, Westf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(312, NULL, NULL, '1112', 'LVM Lebensversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48126', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(313, NULL, NULL, '3312', 'LVM Pensionsfonds-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(314, NULL, NULL, '5815', 'LVM Rechtsschutzversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48126', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(315, NULL, NULL, '1198', 'mamax Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(316, NULL, NULL, '3326', 'MAN Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ungererstraße 69', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(317, NULL, NULL, '6979', 'Mannheimer Aktiengesellschaft Holding', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(318, NULL, NULL, '4123', 'Mannheimer Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage  66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(319, NULL, NULL, '5061', 'Mannheimer Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(320, NULL, NULL, '4141', 'Mecklenburgische Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berckhusenstraße 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(321, NULL, NULL, '1109', 'Mecklenburgische Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berckhusenstr. 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(322, NULL, NULL, '5412', 'Mecklenburgische Versicherungs-Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Berckhusenstraße 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(323, NULL, NULL, '5334', 'MEDIEN-VERSICHERUNG aG KARLSRUHE vorm. Buchgewerbe -Feuerversicherung', NULL, NULL, NULL, NULL, 'Borsigstraße 5', '76185', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(324, NULL, NULL, '2037', 'MER-Pensionskasse V.V.a.G.', NULL, NULL, NULL, NULL, 'Emil-von-Behring-Straße 6', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(325, NULL, NULL, '5671', 'Minerva Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Herrlichkeit 6', '28199', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(326, NULL, NULL, '2043', 'Müllerei-Pensionskasse Versicherungsverein a.G.(MPK)', NULL, NULL, NULL, NULL, 'An der Charlottenburg 1', '47804', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(327, NULL, NULL, '2106', 'Münchener Rück Versorgungskasse VVaG', NULL, NULL, NULL, NULL, 'Königinstraße 107', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(328, NULL, NULL, '6921', 'Münchener Rückversicherungs-Gesellschaft Aktiengesellschaft in München', NULL, NULL, NULL, NULL, 'Königinstraße 107', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(329, NULL, NULL, '5413', 'Münchener und Magdeburger Agrarversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Albert-Schweitzer-Straße 62', '81735', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(330, NULL, NULL, '5414', 'MÜNCHENER VEREIN Allgemeine Versicherungs-AG', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(331, NULL, NULL, '4037', 'MÜNCHENER VEREIN Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(332, NULL, NULL, '1064', 'MÜNCHENER VEREIN Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(333, NULL, NULL, '1193', 'Neckermann Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Nürnberger Straße 91-95', '90762', 'Fürth', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(334, NULL, NULL, '5070', 'Neckermann Versicherung AG', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(335, NULL, NULL, '3328', 'Nestlé Pensionsfonds AG c/o Neversa eG', NULL, NULL, NULL, NULL, 'Lyoner Straße 23', '60528', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(336, NULL, NULL, '2196', 'NESTLE PENSIONSKASSE', NULL, NULL, NULL, NULL, 'Lyoner Straße 23', '60528', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(337, NULL, NULL, '2245', 'NESTLÉ RÜCKDECKUNGSKASSE', NULL, NULL, NULL, NULL, 'Lyoner Straße 23 (Nestle-Haus)', '60528', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(338, NULL, NULL, '1134', 'NEUE BAYERISCHE BEAMTEN LEBENSVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(339, NULL, NULL, '1164', 'neue leben Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(340, NULL, NULL, '2261', 'neue leben Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(341, NULL, NULL, '5591', 'neue leben Unfallversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(342, NULL, NULL, '5805', 'Neue RechtsschutzVersicherungsgesellschaft -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augusta-Anlage 25', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(343, NULL, NULL, '5014', 'Neuendorfer Brand-Bau-Gilde z.Hd. Herrn Helmut Drews', NULL, NULL, NULL, NULL, 'Kirchdorf 40', '25335', 'Neuendorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(344, NULL, NULL, '5016', 'Nordhemmer Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lavelsloher Weg 9', '32479', 'Hille', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(345, NULL, NULL, '5426', 'NÜRNBERGER Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstr. 100', '90482', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(346, NULL, NULL, '5686', 'NÜRNBERGER BEAMTEN ALLGEMEINE VERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(347, NULL, NULL, '1131', 'Nürnberger Beamten Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(348, NULL, NULL, '4125', 'Nürnberger Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(349, NULL, NULL, '1147', 'Nürnberger Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(350, NULL, NULL, '3323', 'Nürnberger Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90334', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(351, NULL, NULL, '2278', 'NÜRNBERGER Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90334', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(352, NULL, NULL, '5015', 'NV-Versicherungen VVaG', NULL, NULL, NULL, NULL, 'Johann-Remmers-Mammen-Weg 2', '26427', 'Neuharlingersiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(353, NULL, NULL, '6995', 'Nycomed Re Insurance AG', NULL, NULL, NULL, NULL, 'Byk-Gulden-Straße 2', '78467', 'Konstanz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(354, NULL, NULL, '1177', 'oeco capital Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(355, NULL, NULL, '1056', 'Öffentliche Lebensversicherung Berlin Brandenburg Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4 - 5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(356, NULL, NULL, '5786', 'OKV - Ostdeutsche Kommunalversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Konrad-Wolf-Straße 91/92', '13055', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(357, NULL, NULL, '1115', 'ONTOS Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(358, NULL, NULL, '4080', 'Opel Aktiv Plus, die Kranken -Zuschuss-Kasse der Adam Opel GmbH, Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Bahnhofsplatz 1', '65428', 'Rüsselsheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(359, NULL, NULL, '2257', 'Optima Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(360, NULL, NULL, '5519', 'Optima Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(361, NULL, NULL, '5813', 'ÖRAG Rechtsschutzversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaallee 199', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(362, NULL, NULL, '5017', 'OSTANGLER BRANDGILDE Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Flensburger Straße 5', '24376', 'Kappeln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(363, NULL, NULL, '5556', 'OSTBEVERNER Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Hauptstraße 27', '48346', 'Ostbevern', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(364, NULL, NULL, '5787', 'OVAG Ostdeutsche Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4-5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(365, NULL, NULL, '5499', 'Pallas Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gebäude Q 26', '51368', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(366, NULL, NULL, '4143', 'PAX-FAMILIENFÜRSORGE Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(367, NULL, NULL, '1194', 'PB Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ProActiv-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(368, NULL, NULL, '3308', 'PB Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Neustraße 62', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(369, NULL, NULL, '2275', 'PB Pensionskasse AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(370, NULL, NULL, '5074', 'PB Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Pro-Activ-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(371, NULL, NULL, '1145', 'PBV Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ProActiv-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(372, NULL, NULL, '2166', 'Pensionär- u. Hinterbliebenen -Unterstützungsverband der Kruppschen Werke (Puhuv) VVaG c/o AON J&H, PM', NULL, NULL, NULL, NULL, 'Luxemburger Allee 4', '45481', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(373, NULL, NULL, '2059', 'Pensions-, Witwen- u. Waisenkasse der v. Bodelschwinghschen Anstalten Bethel,Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(374, NULL, NULL, '2287', 'Pensionsanstalt für die Rechtsanwälte Bayerns VVaG', NULL, NULL, NULL, NULL, 'Barerstraße 3/I', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(375, NULL, NULL, '2046', 'Pensionskasse Berolina VVaG', NULL, NULL, NULL, NULL, 'Unileverhaus,Strandkai 1', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(376, NULL, NULL, '2147', 'Pensionskasse d. Angest. der I.G.Farbenindustrie AG WolfenBitterf. VVaG i.L.c/o Hoechst', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(377, NULL, NULL, '2123', 'Pensionskasse Degussa Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lipper Weg 190', '45764', 'Marl', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(378, NULL, NULL, '2141', 'Pensionskasse der Angestellten der ehemaligen Aschaffenburger Zellstoffwerke Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 33', '83064', 'Raubling', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(379, NULL, NULL, '2080', 'Pensionskasse der Angestellten der ehemaligen GASOLIN AG Versicherungsverein auf Gegenseitigkeit c/o Mercer Deutschland GmbH', NULL, NULL, NULL, NULL, 'Obere Saarlandstraße 2', '45470', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(380, NULL, NULL, '2136', 'Pensionskasse der Angestellten der Matth. Hohner AG Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Andreas-Koch-Straße 9', '78647', 'Trossingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(381, NULL, NULL, '2013', 'Pensionskasse der Angestellten der Thuringia Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81731', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(382, NULL, NULL, '2055', 'Pensionskasse der BERLIN-KÖLNISCHE Versicherungen', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(383, NULL, NULL, '2237', 'Pensionskasse der Betriebsangehörigen der Elektrizitätswerk Mittelbaden AG & Co.KG, Lahr/Schwarzwald, VVaG', NULL, NULL, NULL, NULL, 'Lotzbeckstraße 45', '77933', 'Lahr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(384, NULL, NULL, '2119', 'Pensionskasse der Bewag', NULL, NULL, NULL, NULL, 'Flottwellstraße 4 - 5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(385, NULL, NULL, '2179', 'Pensionskasse der BHW Bausparkasse', NULL, NULL, NULL, NULL, 'Lubahnstraße 2', '31789', 'Hameln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(386, NULL, NULL, '2027', 'Pensionskasse der BOGESTRA Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Universitätsstraße 50-54', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(387, NULL, NULL, '2101', 'Pensionskasse der EDEKA Organisation V.V.a.G.', NULL, NULL, NULL, NULL, 'New-York-Ring 6', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(388, NULL, NULL, '2214', 'PENSIONSKASSE DER ENOVOS DEUTSCHLAND AG VVAG', NULL, NULL, NULL, NULL, 'Am Halberg 3', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(389, NULL, NULL, '2102', 'Pensionskasse der Firma Coca-Cola GmbH,Mülheim a.d.R., VVaG,Aon Jauch & Hübener Consulting GmbH', NULL, NULL, NULL, NULL, 'Luxemburger Allee 4', '45481', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(390, NULL, NULL, '2089', 'Pensionskasse der Firma Schenker & Co GmbH VVaG', NULL, NULL, NULL, NULL, 'Geleitsstraße 10', '60599', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(391, NULL, NULL, '2167', 'Pensionskasse der Frankfurter Bank', NULL, NULL, NULL, NULL, 'Bockenheimer Landstraße 10', '60323', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(392, NULL, NULL, '2155', 'Pensionskasse der Frankfurter Sparkasse', NULL, NULL, NULL, NULL, 'Große Gallusstraße 16 (6.Etage', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(393, NULL, NULL, '2219', 'Pensionskasse der Genossenschaftsorganisation Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Theresienstraße 19', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(394, NULL, NULL, '2063', 'Pensionskasse der Gewerkschaft Eisenhütte Westfalia c/o DBT GmbH', NULL, NULL, NULL, NULL, 'Industriestraße 1', '44534', 'Lünen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(395, NULL, NULL, '2067', 'PENSIONSKASSE der Hamburger Hochbahn Aktiengesellschaft - VVaG', NULL, NULL, NULL, NULL, 'Mattentwiete 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(396, NULL, NULL, '2223', 'Pensionskasse der HELVETIA Schweizerische Versicherungsgesellschaft Direktion für Deutschland', NULL, NULL, NULL, NULL, 'Berliner Straße 56/58', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(397, NULL, NULL, '2144', 'Pensionskasse der HypoVereinsbank', NULL, NULL, NULL, NULL, 'Fürstenfelder Straße 5', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(398, NULL, NULL, '2076', 'Pensionskasse der Lotsenbrüderschaft Elbe', NULL, NULL, NULL, NULL, 'Elbchaussee 330', '22609', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(399, NULL, NULL, '2204', 'Pensionskasse der Mitarbeiter der ehemaligen Frankona Rückversicherungs-AG V.V.a.G.', NULL, NULL, NULL, NULL, 'Dieselstraße 11', '85774', 'Unterföhring', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(400, NULL, NULL, '2154', 'Pensionskasse der Mitarbeiter der Hoechst-Gruppe VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(401, NULL, NULL, '2169', 'Pensionskasse der Novartis Pharma GmbH in Nürnberg Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Roonstraße 25', '90429', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(402, NULL, NULL, '2232', 'Pensionskasse der Rechtsanwälte und Notare VVaG', NULL, NULL, NULL, NULL, 'Weddinghofer Straße 85B', '59174', 'Kamen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(403, NULL, NULL, '2217', 'Pensionskasse der Schülke & Mayr GmbH V.V.a.G.', NULL, NULL, NULL, NULL, 'Caffamacherreihe 16', '20355', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(404, NULL, NULL, '2108', 'Pensionskasse der SV SparkassenVersicherung Lebensversicherung Aktiengesellschaft Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(405, NULL, NULL, '2116', 'Pensionskasse der Vereinigten Hagelversicherung VVaG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 25', '35392', 'Gießen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(406, NULL, NULL, '2235', 'Pensionskasse der VHV-Versicherungen', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(407, NULL, NULL, '2143', 'Pensionskasse der Wacker Chemie Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Hanns-Seidel-Platz 4', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(408, NULL, NULL, '2177', 'Pensionskasse der Wasserwirtschaftlichen Verbände, Essen VVaG', NULL, NULL, NULL, NULL, 'Kronprinzenstraße 37', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(409, NULL, NULL, '2109', 'Pensionskasse der Württembergischen', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(410, NULL, NULL, '2228', 'Pensionskasse des BDH Bundesverband für Rehabilitation und Interessenvertretung Behinderter, VVaG', NULL, NULL, NULL, NULL, 'Eifelstraße 7', '53119', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(411, NULL, NULL, '2248', 'Pensionskasse Deutscher Eisenbahnen und Straßenbahnen VVaG', NULL, NULL, NULL, NULL, 'Volksgartenstraße 54a', '50677', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(412, NULL, NULL, '2244', 'Pensionskasse Dynamit Nobel Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kaiserstraße 52', '53840', 'Troisdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(413, NULL, NULL, '2008', 'Pensionskasse für Angestellte der Continental Aktiengesellschaft VVaG, Hannover', NULL, NULL, NULL, NULL, 'Goseriede 8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(414, NULL, NULL, '2035', 'Pensionskasse für die Angest. der BARMER Ersatzkasse (Versicherungsverein auf Gegenseitigkeit)', NULL, NULL, NULL, NULL, 'Lichtscheider Straße 89', '42285', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(415, NULL, NULL, '2227', 'Pensionskasse für die Arbeitnehmerinnen und Arbeitnehmer des ZDF Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'ZDF-Straße 1', '55127', 'Mainz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(416, NULL, NULL, '2052', 'Pensionskasse für die Deutsche Wirtschaft vormals Pensionskasse der chemischen Industrie Deutschlands', NULL, NULL, NULL, NULL, 'Am Burgacker 37', '47051', 'Duisburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(417, NULL, NULL, '2034', 'Pensionskasse HT Troplast Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kaiserstraße', '53840', 'Troisdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(418, NULL, NULL, '2142', 'Pensionskasse Konzern Versicherungskammer Bayern VVaG', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '81530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(419, NULL, NULL, '2145', 'Pensionskasse Maxhütte VVaG', NULL, NULL, NULL, NULL, 'Kunst-Fischer-Gasse 2', '92237', 'Sulzbach-Rosenberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(420, NULL, NULL, '2226', 'PENSIONSKASSE PEUGEOT DEUTSCHLAND VVaG', NULL, NULL, NULL, NULL, 'Koßmannstraße 19', '66119', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(421, NULL, NULL, '2190', 'Pensionskasse Raiffeisen -Schulze-Delitzsch Norddeutschland Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Raiffeisenstraße 1-3', '24768', 'Rendsburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(422, NULL, NULL, '2225', 'Pensionskasse Rundfunk', NULL, NULL, NULL, NULL, 'Bertramstraße 8', '60320', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(423, NULL, NULL, '2138', 'Pensionskasse Schoeller & Hoesch VVaG', NULL, NULL, NULL, NULL, 'Hördener Straße 5', '76593', 'Gernsbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(424, NULL, NULL, '2095', 'Pensionskasse SIGNAL Versicherungen', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(425, NULL, NULL, '2092', 'Pensionskasse westdeutscher Genossenschaften VVaG', NULL, NULL, NULL, NULL, 'Mecklenbecker Straße 235-239', '48163', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(426, NULL, NULL, '5856', 'PENSIONS-SICHERUNGS-VEREIN Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Berlin-Kölnische-Allee 2-4', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(427, NULL, NULL, '2096', 'Philips Pensionskasse (Versicherungsverein auf Gegenseitigkeit)', NULL, NULL, NULL, NULL, 'Lübeckertordamm 1-3', '20099', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(428, NULL, NULL, '2007', 'Phoenix Pensionskasse von 1925 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(429, NULL, NULL, '1123', 'PLUS Lebensversicherungs AG', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(430, NULL, NULL, '2258', 'Pro BAV Pensionskasse AG', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(431, NULL, NULL, '5147', 'ProTect Versicherung AG', NULL, NULL, NULL, NULL, 'Kölner Landstrasse 33', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(432, NULL, NULL, '1309', 'Protektor Lebensversicherungs -AG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 43G', '10117', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(433, NULL, NULL, '4135', 'Provinzial Krankenversicherung Hannover AG', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(434, NULL, NULL, '1081', 'Provinzial Lebensversicherung Hannover', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(435, NULL, NULL, '5446', 'Provinzial Nord Brandkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sophienblatt 33', '24114', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(436, NULL, NULL, '6985', 'Provinzial NordWest Holding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Provinzial-Allee 1', '48131', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(437, NULL, NULL, '1083', 'Provinzial NordWest Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sophienblatt 33', '24114', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(438, NULL, NULL, '2269', 'Provinzial Pensionskasse Hannover AG', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(439, NULL, NULL, '6986', 'Provinzial Rheinland Holding', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(440, NULL, NULL, '1082', 'Provinzial Rheinland Lebensversicherung AG Die Versicherung der Sparkassen', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(441, NULL, NULL, '5095', 'Provinzial Rheinland Versicherung AG Die Versicherung der Sparkassen', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(442, NULL, NULL, '1111', 'PRUDENTIA-Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Theodor-Str. 6', '40213', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(443, NULL, NULL, '5583', 'PVAG Polizeiversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(444, NULL, NULL, '5836', 'R + V Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(445, NULL, NULL, '5438', 'R+V ALLGEMEINE VERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(446, NULL, NULL, '5137', 'R+V Direktversicherung AG', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(447, NULL, NULL, '3311', 'R+V Gruppenpensionsfonds AG', NULL, NULL, NULL, NULL, 'Kaufingerstraße 9', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(448, NULL, NULL, '4116', 'R+V Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(449, NULL, NULL, '1085', 'R+V Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Taunusstr. 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(450, NULL, NULL, '1141', 'R+V LEBENSVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(451, NULL, NULL, '3305', 'R+V Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(452, NULL, NULL, '2285', 'R+V PENSIONSKASSE AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(453, NULL, NULL, '2045', 'R+V PENSIONSVERSICHERUNG a.G.', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(454, NULL, NULL, '6960', 'R+V Versicherung AG', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(455, NULL, NULL, '5799', 'Real Garant Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Strohgäustraße 5', '73765', 'Neuhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(456, NULL, NULL, '2148', 'Rentenzuschußkasse der N-ERGIE Aktiengesellschaft Nürnberg', NULL, NULL, NULL, NULL, 'Hainstraße 34', '90461', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(457, NULL, NULL, '2011', 'Renten-Zuschuß-Kasse des Norddeutschen Lloyd', NULL, NULL, NULL, NULL, 'Am Wall 140', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(458, NULL, NULL, '6998', 'REVIUM Rückversicherung AG', NULL, NULL, NULL, NULL, 'Carl Braun Straße 1', '34212', 'Melsungen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(459, NULL, NULL, '2282', 'Rheinische Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(460, NULL, NULL, '3154', 'Rheinisch-Westfälische Sterbekasse Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Lindenallee 74', '45127', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(461, NULL, NULL, '1018', 'RheinLand Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(462, NULL, NULL, '5798', 'RheinLand Versicherungs Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rheinlandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(463, NULL, NULL, '5121', 'Rhion Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz 1', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(464, NULL, NULL, '6946', 'RISICOM Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marktplatz 3', '82031', 'Grünwald', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(465, NULL, NULL, '5807', 'ROLAND Rechtsschutz -Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(466, NULL, NULL, '5528', 'ROLAND Schutzbrief -Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(467, NULL, NULL, '5901', 'RS Reise- Schutz Versicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofstraße 10', '74189', 'Weinsberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(468, NULL, NULL, '2012', 'Ruhegeld-, Witwen- und Waisenkasse d. Bergischen Elektrizitäts-Versorgungs-GmbH (VVaG)', NULL, NULL, NULL, NULL, 'Bromberger Straße 39-41', '42281', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(469, NULL, NULL, '2028', 'Ruhegeldkasse der Bremer Straßenbahn (VVaG)', NULL, NULL, NULL, NULL, 'Flughafendamm 12', '28199', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(470, NULL, NULL, '3325', 'RWE Pensionsfonds AG c/o RWE AG', NULL, NULL, NULL, NULL, 'Opernplatz 1', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(471, NULL, NULL, '5051', 'S DirektVersicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kölner Landstraße 33', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(472, NULL, NULL, '5773', 'Saarland Feuerversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mainzer Straße 32-34', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(473, NULL, NULL, '1150', 'SAARLAND Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mainzer Straße 32-34', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(474, NULL, NULL, '2176', 'Scheufelen-Versorgungskasse Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Papierfabrik Scheufelen', '73252', 'Lenningen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(475, NULL, NULL, '5491', 'Schleswiger Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Gildehaus', '25924', 'Emmelsbüll-Horsbüll', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(476, NULL, NULL, '5559', 'SCHNEVERDINGER Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Rotenburger Straße 1-3', '29640', 'Schneverdingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(477, NULL, NULL, '5018', 'Schutzverein Deutscher Rheder V.a.G.', NULL, NULL, NULL, NULL, 'Am Kaiserkai 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(478, NULL, NULL, '5690', 'SCHWARZMEER UND OSTSEE Versicherungs -Aktiengesellschaft SOVAG', NULL, NULL, NULL, NULL, 'Schwanenwik 37', '22087', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(479, NULL, NULL, '1168', 'Schwestern-Versicherungsverein vom Roten Kreuz in Deutschland a.G.', NULL, NULL, NULL, NULL, 'Heilsbachstraße 32', '53123', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(480, NULL, NULL, '6901', 'SCOR Rückversicherung (Deutschland) AG', NULL, NULL, NULL, NULL, 'Im Mediapark 8', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(481, NULL, NULL, '2164', 'SELBSTHILFE Pensionskasse der Caritas VVaG', NULL, NULL, NULL, NULL, 'Dürener Straße 341', '50935', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(482, NULL, NULL, '5008', 'SHB Allgemeine Versicherung VVaG', NULL, NULL, NULL, NULL, 'Johannes-Albers-Allee 2', '53639', 'Königswinter', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(483, NULL, NULL, '3324', 'Siemens Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marktplatz 3', '82031', 'Grünwald', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(484, NULL, NULL, '5125', 'SIGNAL IDUNA Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(485, NULL, NULL, '2252', 'SIGNAL IDUNA Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Neue Rabenstraße 15', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(486, NULL, NULL, '4002', 'SIGNAL Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(487, NULL, NULL, '5451', 'SIGNAL Unfallversicherung a.G.', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(488, NULL, NULL, '1157', 'Skandia Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kaiserin-Augusta-Allee 108', '10553', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(489, NULL, NULL, '3079', 'SOLIDAR Versicherungsgemeinschaft Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Alleestraße 119', '44793', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(490, NULL, NULL, '4082', 'SONO Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Hans-Böckler-Straße 51', '46236', 'Bottrop', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(491, NULL, NULL, '3150', 'SONO Sterbegeldversicherung a.G.', NULL, NULL, NULL, NULL, 'Hans-Böckler-Straße 51', '46236', 'Bottrop', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(492, NULL, NULL, '3316', 'Sparkassen Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Gustav-Heinemann-Ufer 56', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(493, NULL, NULL, '2265', 'Sparkassen Pensionskasse AG', NULL, NULL, NULL, NULL, 'Gustav-Heinemann-Ufer 56', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(494, NULL, NULL, '5781', 'Sparkassen-Versicherung Sachsen Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An der Flutrinne 12', '01139', 'Dresden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(495, NULL, NULL, '1153', 'Sparkassen-Versicherung Sachsen Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An der Flutrinne 12', '01139', 'Dresden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(496, NULL, NULL, '4106', 'St. Martinus Priesterverein d. Diözese Rottenburg-Stuttgart Kranken- und Sterbekasse-(KSK) Vers.Verein auf Gegenseitigk.', NULL, NULL, NULL, NULL, 'Hohenzollernstraße 23', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(497, NULL, NULL, '3083', 'Sterbekasse "Hoffnung" (vorm. Kindersterbekasse "Hoffnung")', NULL, NULL, NULL, NULL, 'Loher Straße 14', '42283', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(498, NULL, NULL, '3084', 'Sterbekasse der Bediensteten der Stadtverwaltung Dortmund', NULL, NULL, NULL, NULL, 'Weischedestraße 25', '44265', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(499, NULL, NULL, '3138', 'Sterbekasse der Belegschaft der Saarstahl AG Völklingen Werk Völklingen und Werk Burbach', NULL, NULL, NULL, NULL, 'Haldenweg 9', '66333', 'Völklingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(500, NULL, NULL, '3153', 'Sterbekasse der Beschäftigten der Deutschen Rentenversicherung Knappschaft-Bahn-See VVaG', NULL, NULL, NULL, NULL, 'Königsallee 175', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(501, NULL, NULL, '3135', 'Sterbekasse der Betriebsangehörigen der BVG (ehem. Sterbekasse der U-Bahn)', NULL, NULL, NULL, NULL, 'Holzmarktstraße 15-17', '10179', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(502, NULL, NULL, '3155', 'Sterbekasse der Feuerwehren, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Röntgenstraße 60', '31675', 'Bückeburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(503, NULL, NULL, '3139', 'Sterbekasse der Saarbergleute VVaG', NULL, NULL, NULL, NULL, 'Hafenstraße 25', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(504, NULL, NULL, '3009', 'Sterbekasse Evangelischer Freikirchen VVaG', NULL, NULL, NULL, NULL, 'Am Kleinen Wannsee 5', '14109', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(505, NULL, NULL, '3102', 'Sterbekasse f. d. Angestellten der BHF-BANK', NULL, NULL, NULL, NULL, 'Bockenheimer Landstraße 10', '60323', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(506, NULL, NULL, '3057', 'Sterbekasse für d. Belegschaft der Hamburger Wasserwerke GmbH Hamburg VVaG, Hamburg', NULL, NULL, NULL, NULL, 'Billhorner Deich 2', '20539', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(507, NULL, NULL, '3147', 'Sterbekasse für den Niederrhein und das ganze Ruhrgebiet', NULL, NULL, NULL, NULL, 'Brabanterstraße 14', '47533', 'Kleve', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(508, NULL, NULL, '3017', 'Sterbekasse für die Angestellten der Deutschen Bank', NULL, NULL, NULL, NULL, 'Alfred-Herrhausen-Allee 16-24', '65760', 'Eschborn am Taunus', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(509, NULL, NULL, '3152', 'Sterbekasse Sozialversicherung - gegr. in der LVA Rheinprovinz - Düsseldorf', NULL, NULL, NULL, NULL, 'Königsallee 71', '40194', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(510, NULL, NULL, '3067', 'Sterbe-Unterstützungs -Vereinigung der Beschäftigten der Stadt München', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 118', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(511, NULL, NULL, '1104', 'Stuttgarter Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(512, NULL, NULL, '5586', 'Stuttgarter Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(513, NULL, NULL, '5025', 'Süddeutsche Allgemeine Versicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(514, NULL, NULL, '4039', 'Süddeutsche Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(515, NULL, NULL, '1089', 'Süddeutsche Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(516, NULL, NULL, '5036', 'SV SparkassenVersicherung Gebäudeversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(517, NULL, NULL, '6964', 'SV SparkassenVersicherung Holding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(518, NULL, NULL, '1091', 'SV SparkassenVersicherung Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(519, NULL, NULL, '1090', 'Swiss Life AG, Niederlassung für Deutschland', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(520, NULL, NULL, '1321', 'Swiss Life Insurance Solutions AG', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(521, NULL, NULL, '1334', 'Swiss Life Insurance Solutions S.A. Niederlassung für Deutschland Herr René Macher', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(522, NULL, NULL, '3315', 'Swiss Life Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(523, NULL, NULL, '2270', 'Swiss Life Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(524, NULL, NULL, '1132', 'TARGO Lebensversicherung AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(525, NULL, NULL, '5790', 'TARGO Versicherung AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(526, NULL, NULL, '3302', 'Telekom-Pensionsfonds a.G.', NULL, NULL, NULL, NULL, 'Friedrich-Ebert-Allee 140', '53113', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(527, NULL, NULL, '5767', 'Thüga Schadenausgleichskasse München VVaG', NULL, NULL, NULL, NULL, 'Hansjakobstraße 129', '81825', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(528, NULL, NULL, '6996', 'ThyssenKrupp Reinsurance AG', NULL, NULL, NULL, NULL, 'Am Thyssenhaus 3', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(529, NULL, NULL, '5567', 'TRIAS Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximiliansplatz 5', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(530, NULL, NULL, '5459', 'Uelzener Allgemeine Versicherungs-Gesellschaft a.G.', NULL, NULL, NULL, NULL, 'Veerßer Straße 67', '29525', 'Uelzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(531, NULL, NULL, '1152', 'Uelzener Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Veerßer Straße 67', '29525', 'Uelzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(532, NULL, NULL, '4108', 'UNION KRANKENVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Peter-Zimmer-Straße 2', '66123', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(533, NULL, NULL, '5094', 'Union Reiseversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(534, NULL, NULL, '5462', 'United Services Automobile Association, San Antonio Texas/USA Direktion für Deutschland Herrn Martin Göller', NULL, NULL, NULL, NULL, 'Königsberger Straße 1', '60487', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(535, NULL, NULL, '5463', 'uniVersa Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Sulzbacher Str. 1-7', '90489', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(536, NULL, NULL, '4045', 'uniVersa Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Sulzbacher Straße 1-7', '90489', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(537, NULL, NULL, '1092', 'uniVersa Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Sulzbacher Str. 1 - 7', '90489', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(538, NULL, NULL, '3319', 'VdW Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Leonhard-Stinnes-Straße 66', '45470', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(539, NULL, NULL, '6930', 'VERBAND ÖFFENTLICHER VERSICHERER', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(540, NULL, NULL, '5419', 'Vereinigte Hagelversicherung VVaG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 25', '35392', 'Gießen, Lahn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(541, NULL, NULL, '2103', 'Vereinigte Pensionskassen VVaG', NULL, NULL, NULL, NULL, 'Mattentwiete 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(542, NULL, NULL, '1093', 'Vereinigte Postversicherung Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 19', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(543, NULL, NULL, '5530', 'Vereinigte Schiffs -Versicherung V.a.G.', NULL, NULL, NULL, NULL, 'Seelhorststraße 7', '30175', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(544, NULL, NULL, '5348', 'Vereinigte Tierversicherung, Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Sonnenberger Straße 2', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(545, NULL, NULL, '5511', 'Vereinigte Vers.Ges.v. Deutschland Zweign.d. Combined Insurance Company of America Chicago, Illinois, USA John Gerard Noonan', NULL, NULL, NULL, NULL, 'Friedrich-Bergius-Straße 9', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(546, NULL, NULL, '4132', 'Vereinte Spezial Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(547, NULL, NULL, '5441', 'Vereinte Spezial Versicherung AG', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(548, NULL, NULL, '2009', 'VERKA Kirchliche Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Schellendorffstraße 17-19', '14199', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(549, NULL, NULL, '2041', 'Verseidag-Werks -Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Girmesgath 5', '47803', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(550, NULL, NULL, '6970', 'Versicherungskammer Bayern Konzern-Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gewürzmühlstraße 13', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(551, NULL, NULL, '2277', 'Versicherungskammer Bayern Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(552, NULL, NULL, '5042', 'Versicherungskammer Bayern, Versicherungsanstalt des öffentlichen Rechts', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(553, NULL, NULL, '5468', 'Versicherungsverband Deutscher Eisenbahnen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Breite Straße 147 - 151', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(554, NULL, NULL, '3060', 'Versicherungsverein "Kurhessische Poststerbekasse"', NULL, NULL, NULL, NULL, 'Wilhelmsstraße 6 III', '34117', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(555, NULL, NULL, '3072', 'Versicherungsverein Rasselstein', NULL, NULL, NULL, NULL, 'Koblenzer Straße 141', '56626', 'Andernach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(556, NULL, NULL, '2283', 'Versorgungsanstalt des Bundes und der Länder', NULL, NULL, NULL, NULL, 'Hans-Thoma-Straße 19', '76133', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(557, NULL, NULL, '2289', 'Versorgungsausgleichskasse Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(558, NULL, NULL, '2158', 'Versorgungskasse der Angest. der Vereinigte Deutsche Metallwerke AG VVaG', NULL, NULL, NULL, NULL, 'Lurgiallee 5', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(559, NULL, NULL, '2159', 'Versorgungskasse der Angestellten der Metallgesellschaft AG VVaG', NULL, NULL, NULL, NULL, 'Lurgiallee 5', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(560, NULL, NULL, '2073', 'Versorgungskasse der Angestellten der Norddeutschen Affinerie', NULL, NULL, NULL, NULL, 'Hovestraße 50', '20539', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(561, NULL, NULL, '2129', 'Versorgungskasse der Arbeiter und Angestellten der ehemalig. Großkraftwerk Franken AG', NULL, NULL, NULL, NULL, 'Felsenstraße 14', '90449', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(562, NULL, NULL, '2238', 'Versorgungskasse der Bayerischen Milchindustrie Landshut eG Nürnberg VVaG', NULL, NULL, NULL, NULL, 'Platenstraße 31', '90441', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(563, NULL, NULL, '2047', 'Versorgungskasse der Deutscher Herold Versicherungsgesellschaften,Versicherungsverein a.G., Bonn', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25 - 33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(564, NULL, NULL, '2183', 'Versorgungskasse der ehemaligen Bayernwerk AG, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Brienner Straße 40', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(565, NULL, NULL, '2032', 'Versorgungskasse der Firma M. DuMont Schauberg Expedition der Kölnischen Zeitung Neven DuMont Haus', NULL, NULL, NULL, NULL, 'Amsterdamer Straße 92', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(566, NULL, NULL, '2020', 'Versorgungskasse der Volksfürsorge VVaG', NULL, NULL, NULL, NULL, 'Besenbinderhof 43', '20099', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(567, NULL, NULL, '2010', 'Versorgungskasse des Norddeutschen Lloyd', NULL, NULL, NULL, NULL, 'Am Wall 140', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(568, NULL, NULL, '2031', 'Versorgungskasse Deutscher Unternehmen, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Zum Dänischen Wohld 1 - 3', '24159', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(569, NULL, NULL, '2093', 'Versorgungskasse Energie Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Tresckowstraße 3', '30457', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(570, NULL, NULL, '2029', 'Versorgungskasse f. d. Angest. d. AachenMünchener Versicherung AG u.d. Generali Deutschland Holding AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(571, NULL, NULL, '2056', 'Versorgungskasse Fritz Henkel Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(572, NULL, NULL, '2099', 'Versorgungskasse Gothaer Versicherungsbank VVaG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(573, NULL, NULL, '2175', 'Versorgungskasse Radio Bremen', NULL, NULL, NULL, NULL, 'Diepenau 2', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(574, NULL, NULL, '2194', 'Versorgungslasten-Ausgleichskasse des Genossenschaftsverbandes Norddeutschland e.V. - VVaG - Hannover', NULL, NULL, NULL, NULL, 'Hannoversche Straße 149', '30627', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(575, NULL, NULL, '5862', 'VHV Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(576, NULL, NULL, '1314', 'VHV Lebensversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(577, NULL, NULL, '5464', 'VHV Vereinigte Hannoversche Versicherung a.G.', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(578, NULL, NULL, '4105', 'Victoria Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 2', '40477', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(579, NULL, NULL, '1140', 'Victoria Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40477', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(580, NULL, NULL, '2259', 'Victoria Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(581, NULL, NULL, '3309', 'VIFA Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Schellendorffstraße 17/19', '14199', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(582, NULL, NULL, '2272', 'Volksfürsorge Pensionskasse AG', NULL, NULL, NULL, NULL, 'Besenbinderhof 43', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(583, NULL, NULL, '6997', 'Volkswagen Reinsurance AG', NULL, NULL, NULL, NULL, 'Gifhorner Straße 57', '38112', 'Braunschweig', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(584, NULL, NULL, '1099', 'Volkswohl-Bund Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Südwall 37-41', '44137', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(585, NULL, NULL, '5484', 'VOLKSWOHL-BUND SACHVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Südwall 37-41', '44128', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(586, NULL, NULL, '1151', 'Vorsorge Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rather Straße 110a', '40476', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(587, NULL, NULL, '3029', 'Vorsorgekasse der Commerzbank Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Turmstraße 83-84', '10551', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(588, NULL, NULL, '3107', 'Vorsorgekasse Hoesch Dortmund Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Oesterholzstraße 124', '44145', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(589, NULL, NULL, '3122', 'Vorsorgeversicherung Siemens VaG', NULL, NULL, NULL, NULL, 'Gugelstraße 115p', '90459', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(590, NULL, NULL, '5461', 'VPV Allgemeine Versicherungs-AG', NULL, NULL, NULL, NULL, 'Pohligstraße 3', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(591, NULL, NULL, '1160', 'VPV Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 19', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(592, NULL, NULL, '5099', 'VRK Versicherungsverein auf Gegenseitigkeit im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Kölnische Straße 108', '34119', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(593, NULL, NULL, '5082', 'Waldenburger Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Alfred-Leikam-Straße 25', '74523', 'Schwäbisch Hall', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(594, NULL, NULL, '5488', 'WERTGARANTIE Technische Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Breite Straße 6-8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(595, NULL, NULL, '3317', 'West Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(596, NULL, NULL, '2266', 'West Pensionskasse AG', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(597, NULL, NULL, '5093', 'Westfälische Provinzial Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Provinzial-Allee 1', '48159', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(598, NULL, NULL, '1149', 'WGV-Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(599, NULL, NULL, '5525', 'WGV-Versicherung AG', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(600, NULL, NULL, '2262', 'winsecura Pensionkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65178', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(601, NULL, NULL, '2288', 'Wuppertaler Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Lichtscheider Straße 89-95', '42285', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(602, NULL, NULL, '5479', 'Württembergische Gemeinde-Versicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(603, NULL, NULL, '4139', 'Württembergische Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(604, NULL, NULL, '1005', 'Württembergische Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(605, NULL, NULL, '5783', 'Württembergische Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(606, NULL, NULL, '5590', 'Würzburger Versicherungs-AG', NULL, NULL, NULL, NULL, 'Bahnhofstraße 11', '97070', 'Würzburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(607, NULL, NULL, '6958', 'Wüstenrot & Württembergische AG', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(608, NULL, NULL, '5476', 'WWK Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marsstr. 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(609, NULL, NULL, '1103', 'WWK Lebensversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Marsstraße 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(610, NULL, NULL, '3318', 'WWK Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marsstraße 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(611, NULL, NULL, '2281', 'Zentrales Versorgungswerk für das Dachdeckerhandwerk VVaG', NULL, NULL, NULL, NULL, 'Rosenstraße 2', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(612, NULL, NULL, '1138', 'Zurich Deutscher Herold Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25 - 33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(613, NULL, NULL, '5050', 'Zurich Versicherung Aktiengesellschaft (Deutschland)', NULL, NULL, NULL, NULL, 'Solmsstraße 27-37', '60486', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(614, NULL, NULL, '2222', 'Zusatzversorgungskasse der Steine- und Erden-Industrie u. des Betonsteinhandwerks VVaG Die Bayerische Pensionskasse', NULL, NULL, NULL, NULL, 'Bavariaring 23', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(615, NULL, NULL, '2189', 'Zusatzversorgungskasse des Baugewerbes AG', NULL, NULL, NULL, NULL, 'Wettinerstraße 7', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(616, NULL, NULL, '2209', 'Zusatzversorgungskasse des Dachdeckerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Rosenstraße 2', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(617, NULL, NULL, '2242', 'Zusatzversorgungskasse des Gerüstbaugewerbes VVaG', NULL, NULL, NULL, NULL, 'Mainzer Straße 98 - 102', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(618, NULL, NULL, '2236', 'Zusatzversorgungskasse des Maler- u. Lackiererhandwerks VVaG', NULL, NULL, NULL, NULL, 'John-F.-Kennedy-Straße 6', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(619, NULL, NULL, '2218', 'Zusatzversorgungskasse des Steinmetz- und Steinbildhauerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Washingtonstraße 75', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(620, NULL, NULL, '2220', 'Zusatzversorgungskasse für die Beschäftigten der Deutschen Brot- und Backwarenindustrie VVaG', NULL, NULL, NULL, NULL, 'In den Diken 33', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(621, NULL, NULL, '2221', 'Zusatzversorgungskasse für die Beschäftigten des Deutschen Bäckerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Bondorfer Straße 23', '53604', 'Bad Honnef', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(622, NULL, NULL, '2253', 'Zusatzversorgungswerk für Arbeitnehmer in der Land- und Forstwirtschaft-ZLF VVaG', NULL, NULL, NULL, NULL, 'Druseltalstraße 51', '34131', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `versicherer_all`
--

CREATE TABLE IF NOT EXISTS `versicherer_all` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `parentName` varchar(500) DEFAULT NULL,
  `vuNummer` varchar(255) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `nameZusatz` varchar(500) DEFAULT NULL,
  `nameZusatz2` varchar(500) DEFAULT NULL,
  `kuerzel` varchar(255) DEFAULT NULL,
  `gesellschaftsNr` varchar(255) DEFAULT NULL,
  `strasse` varchar(500) DEFAULT NULL,
  `plz` varchar(100) DEFAULT NULL,
  `stadt` varchar(255) DEFAULT NULL,
  `bundesLand` varchar(255) DEFAULT NULL,
  `land` varchar(255) NOT NULL DEFAULT 'Deutschland',
  `postfach` tinyint(1) NOT NULL DEFAULT '0',
  `postfachName` varchar(255) DEFAULT NULL,
  `postfachPlz` varchar(255) DEFAULT NULL,
  `postfachOrt` varchar(255) DEFAULT NULL,
  `vermittelbar` tinyint(1) NOT NULL DEFAULT '1',
  `communication1` varchar(255) DEFAULT NULL,
  `communication2` varchar(255) DEFAULT NULL,
  `communication3` varchar(255) DEFAULT NULL,
  `communication4` varchar(255) DEFAULT NULL,
  `communication5` varchar(255) DEFAULT NULL,
  `communication6` varchar(255) DEFAULT NULL,
  `communication1Type` tinyint(5) NOT NULL DEFAULT '0',
  `communication2Type` tinyint(5) NOT NULL DEFAULT '1',
  `communication3Type` tinyint(5) NOT NULL DEFAULT '4',
  `communication4Type` tinyint(5) NOT NULL DEFAULT '7',
  `communication5Type` tinyint(5) NOT NULL DEFAULT '10',
  `communication6Type` tinyint(5) NOT NULL DEFAULT '14',
  `comments` text,
  `custom1` varchar(1000) DEFAULT NULL,
  `custom2` varchar(1000) DEFAULT NULL,
  `custom3` varchar(1000) DEFAULT NULL,
  `custom4` varchar(1000) DEFAULT NULL,
  `custom5` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vuNummer` (`vuNummer`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=623 ;

--
-- Daten für Tabelle `versicherer_all`
--

INSERT INTO `versicherer_all` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, NULL, NULL, '5448', 'SCHWEIZER NATIONAL VERSICHERUNGS-AKTIENGESELLSCHAFT IN DEUTSCHLAND', NULL, NULL, NULL, NULL, 'Querstraße 8-10', '60322', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2011-06-21 09:54:31', 0),
(2, NULL, NULL, '1001', 'AachenMünchener Lebensversicherung AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(3, NULL, NULL, '5342', 'AachenMünchener Versicherung AG', NULL, NULL, NULL, NULL, 'Aureliusstraße 2', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(4, NULL, NULL, '5135', 'ADAC Autoversicherung AG', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(5, NULL, NULL, '5826', 'ADAC-RECHTSSCHUTZ VERSICHERUNGS -AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(6, NULL, NULL, '5498', 'ADAC-SCHUTZBRIEF VERSICHERUNGS -AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Am Westpark 8', '81373', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(7, NULL, NULL, '5581', 'ADLER Versicherung AG', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(8, NULL, NULL, '5809', 'AdvoCard Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 81', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(9, NULL, NULL, '5035', 'AGILA Haustierversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Breite Straße 6 - 8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(10, NULL, NULL, '1318', 'Aioi Life Insurance of Europe Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Toyota-Allee 5', '50858', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(11, NULL, NULL, '2268', 'Allgemeine Rentenanstalt Pensionskasse AG', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(12, NULL, NULL, '3080', 'Allgemeine Sterbekasse Essen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Hollestraße 1 Haus der Technik', '45127', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(13, NULL, NULL, '3101', 'Allgemeine Sterbekasse Oberhausen/Duisburg (ehem. Sterbekasse der Belegschaft Thyssen Stahl AG)', NULL, NULL, NULL, NULL, 'Zur Eisenhütte 7', '46047', 'Oberhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(14, NULL, NULL, '5370', 'Allianz Global Corporate & Specialty AG', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(15, NULL, NULL, '1006', 'Allianz Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(16, NULL, NULL, '3304', 'Allianz Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(17, NULL, NULL, '2273', 'Allianz Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(18, NULL, NULL, '4034', 'Allianz Private Krankenversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(19, NULL, NULL, '6949', 'Allianz SE', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(20, NULL, NULL, '5312', 'Allianz Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(21, NULL, NULL, '2018', 'Allianz Versorgungskasse Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Königinstraße 28', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(22, NULL, NULL, '5825', 'Allrecht Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Liesegangstraße 15', '40211', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(23, NULL, NULL, '5785', 'almeda Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 116 a', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(24, NULL, NULL, '1007', 'ALTE LEIPZIGER Lebensversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(25, NULL, NULL, '3320', 'ALTE LEIPZIGER Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(26, NULL, NULL, '2276', 'ALTE LEIPZIGER Pensionskasse AG', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(27, NULL, NULL, '5405', 'Alte Leipziger Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Alte Leipziger-Platz 1', '61440', 'Oberursel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(28, NULL, NULL, '4142', 'ALTE OLDENBURGER Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Moorgärten 12-14', '49377', 'Vechta', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(29, NULL, NULL, '4010', 'Alte Oldenburger Krankenversicherung Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Moorgärten 12 - 14', '49377', 'Vechta', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(30, NULL, NULL, '2088', 'Alters- und Hinterbliebenen -Versicherung der Technischen Überwachungs -Vereine-VVaG', NULL, NULL, NULL, NULL, 'Kurfürstenstraße 56', '45138', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(31, NULL, NULL, '2004', 'Altersversorgungskasse des Kaiserswerther Verbandes deutscher Diakonissen -Mutterhäuser', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(32, NULL, NULL, '5068', 'AMMERLÄNDER VERSICHERUNG Versicherungsverein a.G. (VVaG)', NULL, NULL, NULL, NULL, 'Bahnhofstraße 8', '26655', 'Westerstede', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(33, NULL, NULL, '2105', 'Angest.-Pensionskasse der dt. Geschäftsbetriebe der Georg Fischer Aktiengesellschaft Schaffhausen (Schweiz)', NULL, NULL, NULL, NULL, 'Julius-Bührer-Straße 12', '78224', 'Singen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(34, NULL, NULL, '5800', 'ARAG Allgemeine Rechtsschutz-Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(35, NULL, NULL, '5455', 'ARAG Allgemeine Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(36, NULL, NULL, '4112', 'ARAG Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(37, NULL, NULL, '1035', 'ARAG Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(38, NULL, NULL, '2231', 'Arbeiter-Pensionskasse der Firma Villeroy & Boch, Aktiengesellschaft Mettlach/Saar - VVaG', NULL, NULL, NULL, NULL, 'Postfach 11 20', '66688', 'Mettlach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(39, NULL, NULL, '1181', 'ASPECTA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(40, NULL, NULL, '1303', 'Asstel Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schanzenstraße 28', '51063', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(41, NULL, NULL, '5397', 'ASSTEL Sachversicherung AG', NULL, NULL, NULL, NULL, 'Schanzenstraße 28', '51063', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(42, NULL, NULL, '4129', 'Augenoptiker Ausgleichskasse VVaG (AKA)', NULL, NULL, NULL, NULL, 'Ruhrallee 9 (Ellipson)', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(43, NULL, NULL, '5801', 'AUXILIA Rechtsschutz-Versicherungs- Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Uhlandstr. 7', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(44, NULL, NULL, '5132', 'Avetas Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schöne Aussicht 8a', '61348', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(45, NULL, NULL, '3106', 'AVK Allgemeine Versicherungskasse VVaG -Todesfallversicherung', NULL, NULL, NULL, NULL, 'Langestraße 63', '27749', 'Delmenhorst', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(46, NULL, NULL, '5077', 'AXA ART Versicherung AG', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(47, NULL, NULL, '4095', 'AXA Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(48, NULL, NULL, '1020', 'AXA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia-Allee 10 - 20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(49, NULL, NULL, '5515', 'AXA Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Colonia-Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(50, NULL, NULL, '2186', 'Babcock Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Duisburger Straße 375', '46049', 'Oberhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(51, NULL, NULL, '2251', 'Baden-Badener Pensionskasse', NULL, NULL, NULL, NULL, 'Quettigstraße 23 Haus Quettig', '76530', 'Baden-Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(52, NULL, NULL, '5792', 'Baden-Badener Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Schlackenbergstraße 20', '66386', 'St. Ingbert', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(53, NULL, NULL, '5593', 'Badische Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(54, NULL, NULL, '5838', 'Badische Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(55, NULL, NULL, '5316', 'Badischer Gemeinde-Versicherungs-Verband (BGV)', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(56, NULL, NULL, '5317', 'Barmenia Allgemeine Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(57, NULL, NULL, '4042', 'Barmenia Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(58, NULL, NULL, '1011', 'Barmenia Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Kronprinzenallee 12 - 18', '42119', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(59, NULL, NULL, '2114', 'BASF Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Carl-Bosch-Straße 127-129', '67056', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(60, NULL, NULL, '3034', 'BASF Sterbekasse VVaG', NULL, NULL, NULL, NULL, 'Carl-Bosch-Straße 127-129', '67056', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(61, NULL, NULL, '5318', 'Basler  Versicherung AG Direktion für Deutschland', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(62, NULL, NULL, '1012', 'Basler Leben AG Direktion für Deutschland Herr Dr. Frank Grund', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(63, NULL, NULL, '5633', 'Basler Securitas Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Basler Straße 4', '61352', 'Bad Homburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(64, NULL, NULL, '3143', 'BAVARIA Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Hollerithstraße 11', '81829', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(65, NULL, NULL, '3019', 'Bayer Beistandskasse', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(66, NULL, NULL, '1013', 'Bayerische Beamten Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Str. 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(67, NULL, NULL, '5310', 'Bayerische Beamten Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(68, NULL, NULL, '4134', 'Bayerische Beamtenkrankenkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(69, NULL, NULL, '5319', 'Bayerische Hausbesitzer -Versicherungs-Gesellschaft a.G.', NULL, NULL, NULL, NULL, 'Sonnenstraße 13/V', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(70, NULL, NULL, '5043', 'Bayerische Landesbrandversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(71, NULL, NULL, '5324', 'Bayerischer Versicherungsverband Versicherungsaktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(72, NULL, NULL, '1015', 'Bayern-Versicherung Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstr. 53', '81535', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(73, NULL, NULL, '2017', 'Bayer-Pensionskasse', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(74, NULL, NULL, '3105', 'BERGBAU-STERBEKASSE - Vorsorge-Versicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Shamrockring 1', '44623', 'Herne', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(75, NULL, NULL, '5326', 'Bergische Brandversicherung Allgemeine Feuerversicherung V.a.G.', NULL, NULL, NULL, NULL, 'Hofkamp 86', '42103', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(76, NULL, NULL, '2151', 'Betriebspensionskasse der Firma Carl Schenck AG VVaG Darmstadt', NULL, NULL, NULL, NULL, 'Landwehrstraße 55', '64293', 'Darmstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(77, NULL, NULL, '5146', 'BGV-Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Durlacher Allee 56', '76131', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(78, NULL, NULL, '3140', 'Bochumer Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kortumstraße 102-104', '44787', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(79, NULL, NULL, '3313', 'Bosch Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidehofstraße 31', '70184', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(80, NULL, NULL, '5098', 'Bruderhilfe Sachversicherung AG im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Kölnische Straße 108-112', '34119', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(81, NULL, NULL, '3327', 'BVV Pensionsfonds des Bankgewerbes AG', NULL, NULL, NULL, NULL, 'Kurfürstendamm 111/113', '10711', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(82, NULL, NULL, '2048', 'BVV Versicherungsverein des Bankgewerbes a.G.', NULL, NULL, NULL, NULL, 'Kurfürstendamm 111-113', '10711', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(83, NULL, NULL, '4004', 'Central Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaring 40 - 50', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(84, NULL, NULL, '5547', 'CG CAR-GARANTIE Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gündlinger Straße 12', '79111', 'Freiburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(85, NULL, NULL, '3301', 'CHEMIE Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Kaufingerstraße 9', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(86, NULL, NULL, '5861', 'Coface Kreditversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Isaac-Fulda-Allee 1', '55124', 'Mainz a Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(87, NULL, NULL, '4118', 'Concordia Krankenversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(88, NULL, NULL, '1122', 'Concordia Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(89, NULL, NULL, '5831', 'Concordia Rechtsschutz-Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 5', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(90, NULL, NULL, '5338', 'Concordia Versicherungs-Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(91, NULL, NULL, '5339', 'Condor Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(92, NULL, NULL, '1021', 'Condor Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(93, NULL, NULL, '5004', 'CONSTANTIA Versicherungen a.G.', NULL, NULL, NULL, NULL, 'Große Straße 40', '26721', 'Emden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(94, NULL, NULL, '4001', 'Continentale Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(95, NULL, NULL, '1078', 'Continentale Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Baierbrunner Straße 31-33', '81379', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(96, NULL, NULL, '5340', 'Continentale Sachversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(97, NULL, NULL, '1022', 'COSMOS Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halbergstraße 50-60', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(98, NULL, NULL, '5552', 'Cosmos Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halbergstraße 50-60', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(99, NULL, NULL, '5802', 'D.A.S. Deutscher Automobil Schutz  Allgemeine Rechtsschutz-Versicherungs-AG', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 2', '81728', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(100, NULL, NULL, '5343', 'DA Deutsche Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Oberstedter Straße 14', '61440', 'Oberursel (Taunus)', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(101, NULL, NULL, '5771', 'DARAG Deutsche Versicherungs -und Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gustav-Adolf-Straße 130', '13086', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(102, NULL, NULL, '5311', 'DBV Deutsche Beamtenversicherung AG', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65178', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(103, NULL, NULL, '1146', 'DBV Deutsche Beamtenversicherung Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(104, NULL, NULL, '5549', 'Debeka Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56073', 'Koblenz am Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(105, NULL, NULL, '4028', 'Debeka Krankenversicherungsverein auf Gegenseitigkeit Sitz Koblenz am Rhein', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(106, NULL, NULL, '1023', 'Debeka Lebensversicherungsverein auf Gegenseitigkeit Sitz Koblenz am Rhein', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Str. 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(107, NULL, NULL, '2256', 'Debeka Pensionskasse AG', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(108, NULL, NULL, '2038', 'Debeka Zusatzversorgungskasse VaG', NULL, NULL, NULL, NULL, 'Ferdinand-Sauerbruch-Straße 18', '56058', 'Koblenz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(109, NULL, NULL, '1167', 'Delta Direkt Lebensversicherung Aktiengesellschaft München', NULL, NULL, NULL, NULL, 'Ottostraße 16', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(110, NULL, NULL, '1017', 'Delta Lloyd Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gustav-Stresemann-Ring 7 - 9', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(111, NULL, NULL, '2279', 'Delta Lloyd Pensionskasse AG', NULL, NULL, NULL, NULL, 'Wittelsbacher Straße 1', '65185', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(112, NULL, NULL, '5632', 'Delvag Luftfahrtversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Von-Gablenz-Str. 2-6', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(113, NULL, NULL, '6950', 'Delvag Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Von-Gablenz-Straße 2-6', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(114, NULL, NULL, '5803', 'DEURAG Deutsche Rechtsschutz -Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Abraham-Lincoln-Straße 3', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(115, NULL, NULL, '1180', 'Deutsche Ärzteversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Börsenplatz 1', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(116, NULL, NULL, '5084', 'deutsche internet versicherung aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ruhrallee 92', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(117, NULL, NULL, '1148', 'Deutsche Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An den Treptowers 3', '12435', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(118, NULL, NULL, '3330', 'Deutsche Post Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Straße 20', '53113', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(119, NULL, NULL, '5631', 'Deutsche Rhederei Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Bergstraße 26', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(120, NULL, NULL, '6907', 'Deutsche Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(121, NULL, NULL, '2211', 'Deutsche Steuerberater-Versicherung - Pensionskasse des steuerberatenden Berufs VVaG', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 24', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(122, NULL, NULL, '3303', 'Deutscher Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25-33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(123, NULL, NULL, '5859', 'Deutscher Reisepreis -Sicherungsverein VVaG', NULL, NULL, NULL, NULL, 'Rosenheimer Str.116', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(124, NULL, NULL, '4013', 'DEUTSCHER RING Krankenversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(125, NULL, NULL, '1028', 'DEUTSCHER RING Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(126, NULL, NULL, '5350', 'DEUTSCHER RING Sachversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ludwig-Erhard-Straße 22', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(127, NULL, NULL, '1136', 'DEVK Allgemeine Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(128, NULL, NULL, '5513', 'DEVK Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(129, NULL, NULL, '1025', 'DEVK Deutsche Eisenbahn Ver sicherung Lebensversicherungs verein a.G. Betr. Sozialein richtung der Deutschen Bahn AG', NULL, NULL, NULL, NULL, 'Riehler Str. 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(130, NULL, NULL, '5344', 'DEVK Deutsche Eisenbahn Versicherung Sach- und HUK -Versicherungsverein a.G. Betriebliche Sozialeinrichtung der Deutschen Bahn', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(131, NULL, NULL, '4131', 'DEVK Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(132, NULL, NULL, '3314', 'DEVK Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(133, NULL, NULL, '5829', 'DEVK Rechtsschutz -Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(134, NULL, NULL, '6973', 'DEVK Rückversicherungs- und Beteiligungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Riehler Straße 190', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(135, NULL, NULL, '5129', 'DFV Deutsche Familienversicherung AG', NULL, NULL, NULL, NULL, 'Beethovenstraße 71', '60325', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(136, NULL, NULL, '1113', 'Dialog Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halderstraße 29', '86150', 'Augsburg, Bay', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(137, NULL, NULL, '3073', 'Die Vorsorge Sterbekasse der Werksangehörigen der Degussa Aktiengesellschaft Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lipper Weg 190', '45764', 'Marl', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(138, NULL, NULL, '6978', 'Diehl Assekuranz Rückversicherungs- und Vermittlungs-AG', NULL, NULL, NULL, NULL, 'Stephanstraße 49', '90478', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(139, NULL, NULL, '5055', 'Direct Line Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rheinstraße 7 a', '14513', 'Teltow', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(140, NULL, NULL, '1110', 'Direkte Leben Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer_all` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(141, NULL, NULL, '4044', 'DKV Deutsche Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 300', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(142, NULL, NULL, '5834', 'DMB Rechtsschutz-Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Bonner Straße 323', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(143, NULL, NULL, '5328', 'DOCURA VVaG', NULL, NULL, NULL, NULL, 'Königsallee 57', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(144, NULL, NULL, '5522', 'Dolleruper Freie Brandgilde', NULL, NULL, NULL, NULL, 'Am Wasserwerk 3', '24972', 'Steinbergkirche', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(145, NULL, NULL, '2271', 'DPK Deutsche Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(146, NULL, NULL, '2188', 'Dr.-Richard-Bruhn-Hilfe-Alters versorgung d. AUTO UNION GmbH Versicherungsverein auf Gegenseitigk. (VVaG) Ingolst./Donau', NULL, NULL, NULL, NULL, 'Auto-Union-Straße', '85045', 'Ingolstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(147, NULL, NULL, '2121', 'Dresdener Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Ludwig-Crößmann-Straße 2', '95326', 'Kulmbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(148, NULL, NULL, '4115', 'Düsseldorfer Versicherung Krankenversicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Konrad-Adenauer-Platz 12', '40210', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(149, NULL, NULL, '6908', 'E+S Rückversicherung AG', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 50', '30629', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(150, NULL, NULL, '5141', 'East-West Assekuranz AG', NULL, NULL, NULL, NULL, 'Mauerstraße 83/84', '10117', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(151, NULL, NULL, '4121', 'ENVIVAS Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gereonswall 68', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(152, NULL, NULL, '4126', 'ERGO Direkt Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(153, NULL, NULL, '1130', 'ERGO Direkt Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(154, NULL, NULL, '5562', 'ERGO Direkt Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(155, NULL, NULL, '1184', 'ERGO Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Überseering 45', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(156, NULL, NULL, '3322', 'ERGO Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(157, NULL, NULL, '5472', 'ERGO Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(158, NULL, NULL, '3068', 'Erste Kieler Beerdigungskasse', NULL, NULL, NULL, NULL, 'Kronshagener Weg 8', '24103', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(159, NULL, NULL, '5852', 'Euler Hermes Kreditversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Friedensallee 254', '22763', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(160, NULL, NULL, '5038', 'EURO-AVIATION Versicherungs-AG', NULL, NULL, NULL, NULL, 'Hochallee 80', '20149', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(161, NULL, NULL, '5541', 'EUROP ASSISTANCE Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Infanteriestraße 11', '80797', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(162, NULL, NULL, '4089', 'EUROPA Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(163, NULL, NULL, '1107', 'EUROPA Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(164, NULL, NULL, '5508', 'EUROPA Sachversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Piusstraße 137', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(165, NULL, NULL, '5356', 'Europäische Reiseversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Vogelweidestraße 5', '81677', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(166, NULL, NULL, '5148', 'European Warranty Partners Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Georgswall 7', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(167, NULL, NULL, '5097', 'EXTREMUS Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 75', '50931', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(168, NULL, NULL, '5656', 'F. Laeisz Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Trostbrücke 1', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(169, NULL, NULL, '5470', 'Fahrlehrerversicherung Verein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 5', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(170, NULL, NULL, '1310', 'FAMILIENFÜRSORGE Lebensversicherung AG im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(171, NULL, NULL, '1175', 'Familienschutz Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(172, NULL, NULL, '5357', 'Feuer- und Einbruchschaden kasse der BBBank in Karlsruhe, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Herrenstraße 2-10', '76133', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(173, NULL, NULL, '3091', 'Feuerbestattungsverein VVaG, Selb/Bayern', NULL, NULL, NULL, NULL, 'Tschirnhausweg 6', '95100', 'Selb', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(174, NULL, NULL, '5024', 'Feuersozietät Berlin Brandenburg Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4-5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(175, NULL, NULL, '1162', 'Fortis Deutschland Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Herzberger Landstraße 25', '37085', 'Göttingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(176, NULL, NULL, '4053', 'Freie Arzt- und Medizinkasse der Angehörigen der Berufsfeuerwehr und der Polizei VVaG', NULL, NULL, NULL, NULL, 'Friedrich-Ebert-Anlage 3', '60327', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(177, NULL, NULL, '6984', 'Freudenberg Rückversicherung AG', NULL, NULL, NULL, NULL, 'Höhnerweg 2-4', '69469', 'Weinheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(178, NULL, NULL, '3151', 'Fürsorgekasse von 1908 vormals Sterbekasse der Neuapostolischen Kirche des Landes Nordrhein-Westfalen', NULL, NULL, NULL, NULL, 'Uerdinger Straße 323', '47800', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(179, NULL, NULL, '5505', 'GARANTA Versicherungs- Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstr. 100', '90482', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(180, NULL, NULL, '5346', 'Gartenbau-Versicherung VVaG', NULL, NULL, NULL, NULL, 'Von-Frerichs-Straße 8', '65191', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(181, NULL, NULL, '3063', 'GE.BE.IN Versicherungen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Nordstraße 5-11', '28217', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(182, NULL, NULL, '5539', 'Gebäudeversicherungsgilde für Föhr,Amrum und Halligen', NULL, NULL, NULL, NULL, 'Lung Jaat 11', '25938', 'Utersum/Föhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(183, NULL, NULL, '5365', 'GEGENSEITIGKEIT Versicherung Oldenburg', NULL, NULL, NULL, NULL, 'Osterstraße 15', '26122', 'Oldenburg (Oldb)', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(184, NULL, NULL, '5366', 'Gemeinnützige Haftpflichtversicherungsanstalt der Gartenbau-Berufsgenossenschaft', NULL, NULL, NULL, NULL, 'Frankfurter Straße 126', '34121', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(185, NULL, NULL, '6918', 'General Reinsurance AG', NULL, NULL, NULL, NULL, 'Theodor-Heuss-Ring 11', '50668', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(186, NULL, NULL, '6971', 'Generali Deutschland Holding AG', NULL, NULL, NULL, NULL, 'Tunisstraße 19-23', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(187, NULL, NULL, '2267', 'Generali Deutschland Pensionskasse AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(188, NULL, NULL, '3300', 'Generali Deutschland Pensor Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Oeder Weg 151', '60318', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(189, NULL, NULL, '1139', 'Generali Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(190, NULL, NULL, '5473', 'Generali Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(191, NULL, NULL, '2135', 'Geno Pensionskasse VVaG, Karlsruhe', NULL, NULL, NULL, NULL, 'Lauterbergstraße 1', '76137', 'Karlsruhe, Baden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(192, NULL, NULL, '2044', 'Gerling Versorgungskasse', NULL, NULL, NULL, NULL, 'Hohenzollernring 72', '50672', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(193, NULL, NULL, '5033', 'GERMAN ASSISTANCE VERSICHERUNG AG', NULL, NULL, NULL, NULL, 'Große Viehstr. 5-7', '48653', 'Coesfeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(194, NULL, NULL, '3075', 'Gerther Versicherungs -Gemeinschaft Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Lothringer Straße 13', '44805', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(195, NULL, NULL, '5589', 'GGG KraftfahrzeugReparaturkosten-Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Magdeburger Straße 7', '30880', 'Laatzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(196, NULL, NULL, '5007', 'Glasschutzkasse a.G. von 1923 zu Hamburg', NULL, NULL, NULL, NULL, 'Bei dem Neuen Krahn 2', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(197, NULL, NULL, '6912', 'GLOBALE Rückversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Im Mediapark 4b', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(198, NULL, NULL, '5858', 'Gothaer Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gothaer Allee 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(199, NULL, NULL, '6994', 'Gothaer Finanzholding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(200, NULL, NULL, '4119', 'Gothaer Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(201, NULL, NULL, '1108', 'Gothaer Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(202, NULL, NULL, '2255', 'Gothaer Pensionskasse AG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(203, NULL, NULL, '5372', 'GOTHAER Versicherungsbank VVaG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(204, NULL, NULL, '2111', 'Grün + Bilfinger Wohlfahrts -und Pensionskasse a.G.', NULL, NULL, NULL, NULL, 'Carl-Reiß-Platz 1 - 5', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(205, NULL, NULL, '5485', 'GRUNDEIGENTÜMER-VERSICHERUNG Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Große Bäckerstraße 7', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(206, NULL, NULL, '5469', 'GVV-Kommunalversicherung, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Aachener Straße 952-958', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(207, NULL, NULL, '5585', 'GVV-Privatversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Aachener Straße 952-958', '50933', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(208, NULL, NULL, '5044', 'Haftpflichtgemeinschaft Deutscher Nahverkehrs- und Versorgungsunternehmen (HDN)', NULL, NULL, NULL, NULL, 'Arndtstraße 26', '44787', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(209, NULL, NULL, '5374', 'Haftpflichtkasse Darmstadt - Haftpflichtversicherung des Deutschen Hotel- und Gaststättengewerbes - VVaG', NULL, NULL, NULL, NULL, 'Arheilger Weg 5', '64380', 'Roßdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(210, NULL, NULL, '5445', 'Hagelgilde Versicherungs-Verein a.G. gegr. 1811', NULL, NULL, NULL, NULL, 'Hof Altona', '23730', 'Sierksdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(211, NULL, NULL, '5557', 'HÄGER VERSICHERUNGSVEREIN auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Engerstraße 119', '33824', 'Werther', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(212, NULL, NULL, '4043', 'HALLESCHE Krankenversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Reinsburgstraße 10', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(213, NULL, NULL, '5011', 'Hamburger Beamten-Feuer -und Einbruchskasse', NULL, NULL, NULL, NULL, 'Hermannstraße 46', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(214, NULL, NULL, '5032', 'Hamburger Feuerkasse Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kleiner Burstah 6-10', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(215, NULL, NULL, '5756', 'HAMBURGER HOF Versicherungs -Aktiengesellschaft Im Hause EON Risk Consulting', NULL, NULL, NULL, NULL, 'Kennedydamm 17', '40476', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(216, NULL, NULL, '6917', 'Hamburger Internationale Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Halstenbeker Weg 96a', '25462', 'Rellingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(217, NULL, NULL, '1040', 'Hamburger Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Wittelsbacherstraße 1', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(218, NULL, NULL, '5012', 'Hamburger Lehrer-Feuerkasse', NULL, NULL, NULL, NULL, 'Müssenredder 96c', '22399', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(219, NULL, NULL, '2001', 'HAMBURGER PENSIONSKASSE VON 1905 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(220, NULL, NULL, '2247', 'Hamburger Pensionsrückdeckungskasse VVaG', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(221, NULL, NULL, '2260', 'Hamburg-Mannheimer Pensionskasse AG', NULL, NULL, NULL, NULL, 'Überseering 45', '22287', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(222, NULL, NULL, '5828', 'Hamburg-Mannheimer Rechtsschutzversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Überseering 45', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(223, NULL, NULL, '6941', 'Hannover Rückversicherung AG', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 50', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(224, NULL, NULL, '2249', 'Hannoversche Alterskasse  VVaG', NULL, NULL, NULL, NULL, 'Pelikanplatz 23', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(225, NULL, NULL, '5131', 'Hannoversche Direktversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(226, NULL, NULL, '1312', 'Hannoversche Lebensversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(227, NULL, NULL, '2246', 'Hannoversche Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Pelikanplatz 23', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(228, NULL, NULL, '6935', 'Hanseatica Rückversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Stadthausbrücke 12', '20355', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(229, NULL, NULL, '5754', 'Hanse-Marine-Versicherung AG', NULL, NULL, NULL, NULL, 'Großer Grasbrook 10', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(230, NULL, NULL, '5501', 'HanseMerkur Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20352', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(231, NULL, NULL, '4144', 'HanseMerkur Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(232, NULL, NULL, '4018', 'HanseMerkur Krankenversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(233, NULL, NULL, '1114', 'HanseMerkur Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(234, NULL, NULL, '5496', 'HanseMerkur Reiseversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(235, NULL, NULL, '4122', 'HanseMerkur Speziale Krankenversicherung', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(236, NULL, NULL, '1192', 'HanseMerkur24 Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Siegfried-Wedells-Platz 1', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(237, NULL, NULL, '5544', 'Harsewinkeler Versicherungsverein auf Gegenseitigkeit zu Harsewinkel', NULL, NULL, NULL, NULL, 'Tecklenburger Weg 1', '33428', 'Harsewinkel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(238, NULL, NULL, '5085', 'HDI Direkt Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(239, NULL, NULL, '5377', 'HDI Haftpflichtverband der Deutschen Industrie Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(240, NULL, NULL, '5512', 'HDI-Gerling Firmen und Privat Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(241, NULL, NULL, '6989', 'HDI-Gerling Friedrich Wilhelm Rückversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(242, NULL, NULL, '5096', 'HDI-Gerling Industrie Versicherung AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(243, NULL, NULL, '1033', 'HDI-Gerling Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(244, NULL, NULL, '3306', 'HDI-Gerling Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(245, NULL, NULL, '2264', 'HDI-Gerling Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Charles-de-Gaulle-Platz 1', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(246, NULL, NULL, '5827', 'HDI-Gerling Rechtsschutz Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Günther-Wagner-Allee 14', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(247, NULL, NULL, '6988', 'HDI-Gerling Welt Service AG', NULL, NULL, NULL, NULL, 'Riethorst 2', '30659', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(248, NULL, NULL, '2157', 'HEAG Pensionszuschusskasse Versicherungsverein auf Gegenseitigkeit, Darmstadt', NULL, NULL, NULL, NULL, 'Luisenplatz 5A', '64283', 'Darmstadt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(249, NULL, NULL, '1158', 'Heidelberger Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Forum 7', '69126', 'Heidelberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(250, NULL, NULL, '5596', 'HELVETIA INTERNATIONAL Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 56-58', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(251, NULL, NULL, '1137', 'HELVETIA schweizerische Lebensversicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Weißadlergasse 2', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(252, NULL, NULL, '5384', 'Helvetia Schweizerische Versicherungsgesellschaft AG Direktion für Deutschland Herrn Prof.Dr. Wolfram Wrabetz', NULL, NULL, NULL, NULL, 'Berliner Straße 56 - 58', '60311', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(253, NULL, NULL, '3031', 'Hilfskasse BVG', NULL, NULL, NULL, NULL, 'Holzmarktstraße 15-17', '10179', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(254, NULL, NULL, '3092', 'Hinterbliebenenkasse der Heilberufe Versicherungsverein a.G. in München', NULL, NULL, NULL, NULL, 'Arcisstraße 50', '80799', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(255, NULL, NULL, '6992', 'Hochrhein Internationale Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Stemmerstraße 14', '78266', 'Büsingen am Hochrhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(256, NULL, NULL, '2250', 'Höchster Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(257, NULL, NULL, '3028', 'Höchster Sterbekasse VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65929', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(258, NULL, NULL, '2040', 'Hoffmann''s Pensions- und Unterstützungskasse', NULL, NULL, NULL, NULL, 'Hoffmannstraße 16', '32105', 'Bad Salzuflen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(259, NULL, NULL, '5126', 'Hübener Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ballindamm 37', '20095', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(260, NULL, NULL, '5086', 'HUK24 AG', NULL, NULL, NULL, NULL, 'Willi-Hussong-Straße 2', '96442', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(261, NULL, NULL, '5375', 'HUK-COBURG Haftpflicht-Unterstützungs-Kasse kraftfahrender Beamter Deutschlands a.G. in Coburg', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(262, NULL, NULL, '1055', 'HUK-COBURG Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(263, NULL, NULL, '5521', 'HUK-COBURG-Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(264, NULL, NULL, '6982', 'HUK-COBURG-Holding AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96444', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(265, NULL, NULL, '4117', 'HUK-COBURG-Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96444', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(266, NULL, NULL, '5818', 'HUK-COBURG-Rechtsschutzversicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofsplatz', '96450', 'Coburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(267, NULL, NULL, '5083', 'HVAG Hamburger Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Grimm 14', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(268, NULL, NULL, '3329', 'HVB Trust Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Arabellastraße 12', '81925', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(269, NULL, NULL, '2241', 'IBM Deutschland Pensionskasse Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Am Fichtenberg 1', '71083', 'Herrenberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(270, NULL, NULL, '1047', 'Ideal Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Kochstraße 26', '10969', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(271, NULL, NULL, '5573', 'IDEAL Versicherung AG', NULL, NULL, NULL, NULL, 'Kochstraße 66', '10969', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(272, NULL, NULL, '1048', 'IDUNA Vereinigte Lebensversicherung aG für Handwerk, Handel und Gewerbe', NULL, NULL, NULL, NULL, 'Neue Rabenstr. 15 - 19', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(273, NULL, NULL, '6993', 'Incura AG', NULL, NULL, NULL, NULL, 'Binger Straße 173', '55216', 'Ingelheim am Rhein', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(274, NULL, NULL, '5546', 'Inter Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(275, NULL, NULL, '4031', 'INTER Krankenversicherung aG', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(276, NULL, NULL, '1330', 'INTER Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Erzbergerstraße 9-15', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(277, NULL, NULL, '5057', 'Interlloyd Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ARAG Platz 1', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(278, NULL, NULL, '1119', 'InterRisk Lebensversicherungs-AG Vienna Insurance Group', NULL, NULL, NULL, NULL, 'Karl-Bosch-Straße 5', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(279, NULL, NULL, '5780', 'InterRisk Versicherungs-AG Vienna Insurance Group', NULL, NULL, NULL, NULL, 'Karl-Bosch-Straße 5', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(280, NULL, NULL, '5393', 'ISSELHORSTER Versicherung V.a.G.', NULL, NULL, NULL, NULL, 'Haller Straße 90', '33334', 'Gütersloh', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(281, NULL, NULL, '1128', 'Itzehoer Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer_all` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(282, NULL, NULL, '5401', 'Itzehoer Versicherung/Brandgilde von 1691 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Itzehoer Platz', '25521', 'Itzehoe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(283, NULL, NULL, '5078', 'Janitos Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Im Breitspiel 2-4', '69126', 'Heidelberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(284, NULL, NULL, '5812', 'Jurpartner Rechtsschutz- Versicherung AG', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(285, NULL, NULL, '3022', 'Justiz-Versicherungskasse Lebensversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Drosselweg 44', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(286, NULL, NULL, '1045', 'Karlsruher Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Friedrich-Scholl-Platz', '76137', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(287, NULL, NULL, '6925', 'Kieler Rückversicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Von-der-Goltz-Allee 93', '24113', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(288, NULL, NULL, '2254', 'Kölner Pensionskasse Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Dürener Straße 341', '50935', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(289, NULL, NULL, '5396', 'Kölnische Hagel-Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Wilhelmstr. 25', '35392', 'Gießen, Lahn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(290, NULL, NULL, '3008', 'KölnVorsorge -Sterbeversicherung VVaG', NULL, NULL, NULL, NULL, 'Unter Käster 1', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(291, NULL, NULL, '4104', 'Krankenunterstützungskasse der Berufsfeuerwehr Hannover', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 60 B', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(292, NULL, NULL, '5058', 'KRAVAG-ALLGEMEINE Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(293, NULL, NULL, '6968', 'KRAVAG-HOLDING Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(294, NULL, NULL, '5080', 'KRAVAG-LOGISTIC Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(295, NULL, NULL, '5399', 'KRAVAG-SACH Versicherung des Deutschen Kraftverkehrs Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Heidenkampsweg 102', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(296, NULL, NULL, '5534', 'KS Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Uhlandstraße 7', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(297, NULL, NULL, '4011', 'Landeskrankenhilfe V.V.a.G.', NULL, NULL, NULL, NULL, 'Uelzener Straße 120', '21335', 'Lüneburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(298, NULL, NULL, '1054', 'Landeslebenshilfe V.V.a.G.', NULL, NULL, NULL, NULL, 'Uelzener Str. 120', '21335', 'Lüneburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(299, NULL, NULL, '5362', 'Landesschadenhilfe Versicherung VaG', NULL, NULL, NULL, NULL, 'Vogteistraße 3', '29683', 'Fallingbostel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(300, NULL, NULL, '5400', 'Landschaftliche Brandkasse Hannover', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(301, NULL, NULL, '5264', 'Lauenburg-Alslebener Schiffsversicherung Verein a.G.', NULL, NULL, NULL, NULL, 'Elbstraße 52', '21481', 'Lauenburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(302, NULL, NULL, '5404', 'LBN -Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Groß-Buchholzer Kirchweg 49', '30655', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(303, NULL, NULL, '1062', 'Lebensversicherung von 1871 auf Gegenseitigkeit München', NULL, NULL, NULL, NULL, 'Maximiliansplatz 5', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(304, NULL, NULL, '5013', 'Lehrer-Feuerversicherungsverein für Schleswig-Holstein, Hamburg und MecklenburgVorpommern', NULL, NULL, NULL, NULL, 'Helsinkistraße 70', '24109', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(305, NULL, NULL, '4051', 'LIGA Krankenversicherung katholischer Priester Versicherungsverein auf Gegenseitigkeit Regensburg', NULL, NULL, NULL, NULL, 'Dr. Theobald-Schrems-Straße 3', '93055', 'Regensburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(306, NULL, NULL, '3321', 'Lippische Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Simon-August-Straße 2', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(307, NULL, NULL, '4103', 'Lohnfortzahlungskasse Aurich VVaG', NULL, NULL, NULL, NULL, 'Lambertistraße 16', '26603', 'Aurich', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(308, NULL, NULL, '4102', 'Lohnfortzahlungskasse Leer VVaG', NULL, NULL, NULL, NULL, 'Brunnenstraße 22', '26789', 'Leer', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(309, NULL, NULL, '6957', 'Lucura Rückversicherungs AG', NULL, NULL, NULL, NULL, 'Wöhlerstraße 19', '67063', 'Ludwigshafen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(310, NULL, NULL, '4109', 'LVM Krankenversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(311, NULL, NULL, '5402', 'LVM Landwirtschaftlicher Versicherungsverein Münster a.G.', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster, Westf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(312, NULL, NULL, '1112', 'LVM Lebensversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48126', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(313, NULL, NULL, '3312', 'LVM Pensionsfonds-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48151', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(314, NULL, NULL, '5815', 'LVM Rechtsschutzversicherungs-AG', NULL, NULL, NULL, NULL, 'Kolde-Ring 21', '48126', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(315, NULL, NULL, '1198', 'mamax Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(316, NULL, NULL, '3326', 'MAN Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ungererstraße 69', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(317, NULL, NULL, '6979', 'Mannheimer Aktiengesellschaft Holding', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(318, NULL, NULL, '4123', 'Mannheimer Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage  66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(319, NULL, NULL, '5061', 'Mannheimer Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augustaanlage 66', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(320, NULL, NULL, '4141', 'Mecklenburgische Krankenversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berckhusenstraße 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(321, NULL, NULL, '1109', 'Mecklenburgische Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berckhusenstr. 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(322, NULL, NULL, '5412', 'Mecklenburgische Versicherungs-Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Berckhusenstraße 146', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(323, NULL, NULL, '5334', 'MEDIEN-VERSICHERUNG aG KARLSRUHE vorm. Buchgewerbe -Feuerversicherung', NULL, NULL, NULL, NULL, 'Borsigstraße 5', '76185', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(324, NULL, NULL, '2037', 'MER-Pensionskasse V.V.a.G.', NULL, NULL, NULL, NULL, 'Emil-von-Behring-Straße 6', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(325, NULL, NULL, '5671', 'Minerva Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Herrlichkeit 6', '28199', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(326, NULL, NULL, '2043', 'Müllerei-Pensionskasse Versicherungsverein a.G.(MPK)', NULL, NULL, NULL, NULL, 'An der Charlottenburg 1', '47804', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(327, NULL, NULL, '2106', 'Münchener Rück Versorgungskasse VVaG', NULL, NULL, NULL, NULL, 'Königinstraße 107', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(328, NULL, NULL, '6921', 'Münchener Rückversicherungs-Gesellschaft Aktiengesellschaft in München', NULL, NULL, NULL, NULL, 'Königinstraße 107', '80802', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(329, NULL, NULL, '5413', 'Münchener und Magdeburger Agrarversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Albert-Schweitzer-Straße 62', '81735', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(330, NULL, NULL, '5414', 'MÜNCHENER VEREIN Allgemeine Versicherungs-AG', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(331, NULL, NULL, '4037', 'MÜNCHENER VEREIN Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(332, NULL, NULL, '1064', 'MÜNCHENER VEREIN Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Pettenkoferstraße 19', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(333, NULL, NULL, '1193', 'Neckermann Lebensversicherung AG', NULL, NULL, NULL, NULL, 'Nürnberger Straße 91-95', '90762', 'Fürth', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(334, NULL, NULL, '5070', 'Neckermann Versicherung AG', NULL, NULL, NULL, NULL, 'Karl-Martell-Straße 60', '90344', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(335, NULL, NULL, '3328', 'Nestlé Pensionsfonds AG c/o Neversa eG', NULL, NULL, NULL, NULL, 'Lyoner Straße 23', '60528', 'Frankfurt am Main', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(336, NULL, NULL, '2196', 'NESTLE PENSIONSKASSE', NULL, NULL, NULL, NULL, 'Lyoner Straße 23', '60528', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(337, NULL, NULL, '2245', 'NESTLÉ RÜCKDECKUNGSKASSE', NULL, NULL, NULL, NULL, 'Lyoner Straße 23 (Nestle-Haus)', '60528', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(338, NULL, NULL, '1134', 'NEUE BAYERISCHE BEAMTEN LEBENSVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Thomas-Dehler-Straße 25', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(339, NULL, NULL, '1164', 'neue leben Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(340, NULL, NULL, '2261', 'neue leben Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(341, NULL, NULL, '5591', 'neue leben Unfallversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sachsenkamp 5', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(342, NULL, NULL, '5805', 'Neue RechtsschutzVersicherungsgesellschaft -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Augusta-Anlage 25', '68165', 'Mannheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(343, NULL, NULL, '5014', 'Neuendorfer Brand-Bau-Gilde z.Hd. Herrn Helmut Drews', NULL, NULL, NULL, NULL, 'Kirchdorf 40', '25335', 'Neuendorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(344, NULL, NULL, '5016', 'Nordhemmer Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lavelsloher Weg 9', '32479', 'Hille', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(345, NULL, NULL, '5426', 'NÜRNBERGER Allgemeine Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstr. 100', '90482', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(346, NULL, NULL, '5686', 'NÜRNBERGER BEAMTEN ALLGEMEINE VERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(347, NULL, NULL, '1131', 'Nürnberger Beamten Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(348, NULL, NULL, '4125', 'Nürnberger Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(349, NULL, NULL, '1147', 'Nürnberger Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90482', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(350, NULL, NULL, '3323', 'Nürnberger Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90334', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(351, NULL, NULL, '2278', 'NÜRNBERGER Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Ostendstraße 100', '90334', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(352, NULL, NULL, '5015', 'NV-Versicherungen VVaG', NULL, NULL, NULL, NULL, 'Johann-Remmers-Mammen-Weg 2', '26427', 'Neuharlingersiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(353, NULL, NULL, '6995', 'Nycomed Re Insurance AG', NULL, NULL, NULL, NULL, 'Byk-Gulden-Straße 2', '78467', 'Konstanz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(354, NULL, NULL, '1177', 'oeco capital Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Wiechert-Allee 55', '30625', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(355, NULL, NULL, '1056', 'Öffentliche Lebensversicherung Berlin Brandenburg Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4 - 5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(356, NULL, NULL, '5786', 'OKV - Ostdeutsche Kommunalversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Konrad-Wolf-Straße 91/92', '13055', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(357, NULL, NULL, '1115', 'ONTOS Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(358, NULL, NULL, '4080', 'Opel Aktiv Plus, die Kranken -Zuschuss-Kasse der Adam Opel GmbH, Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Bahnhofsplatz 1', '65428', 'Rüsselsheim', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(359, NULL, NULL, '2257', 'Optima Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(360, NULL, NULL, '5519', 'Optima Versicherungs-Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Admiralitätstraße 67', '20459', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(361, NULL, NULL, '5813', 'ÖRAG Rechtsschutzversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Hansaallee 199', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(362, NULL, NULL, '5017', 'OSTANGLER BRANDGILDE Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Flensburger Straße 5', '24376', 'Kappeln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(363, NULL, NULL, '5556', 'OSTBEVERNER Versicherungsverein auf Gegenseitigkeit (VVaG)', NULL, NULL, NULL, NULL, 'Hauptstraße 27', '48346', 'Ostbevern', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(364, NULL, NULL, '5787', 'OVAG Ostdeutsche Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Am Karlsbad 4-5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(365, NULL, NULL, '5499', 'Pallas Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gebäude Q 26', '51368', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(366, NULL, NULL, '4143', 'PAX-FAMILIENFÜRSORGE Krankenversicherung AG', NULL, NULL, NULL, NULL, 'Doktorweg 2-4', '32756', 'Detmold', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(367, NULL, NULL, '1194', 'PB Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ProActiv-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(368, NULL, NULL, '3308', 'PB Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Neustraße 62', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(369, NULL, NULL, '2275', 'PB Pensionskasse AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(370, NULL, NULL, '5074', 'PB Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Pro-Activ-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(371, NULL, NULL, '1145', 'PBV Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'ProActiv-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(372, NULL, NULL, '2166', 'Pensionär- u. Hinterbliebenen -Unterstützungsverband der Kruppschen Werke (Puhuv) VVaG c/o AON J&H, PM', NULL, NULL, NULL, NULL, 'Luxemburger Allee 4', '45481', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(373, NULL, NULL, '2059', 'Pensions-, Witwen- u. Waisenkasse der v. Bodelschwinghschen Anstalten Bethel,Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(374, NULL, NULL, '2287', 'Pensionsanstalt für die Rechtsanwälte Bayerns VVaG', NULL, NULL, NULL, NULL, 'Barerstraße 3/I', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(375, NULL, NULL, '2046', 'Pensionskasse Berolina VVaG', NULL, NULL, NULL, NULL, 'Unileverhaus,Strandkai 1', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(376, NULL, NULL, '2147', 'Pensionskasse d. Angest. der I.G.Farbenindustrie AG WolfenBitterf. VVaG i.L.c/o Hoechst', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(377, NULL, NULL, '2123', 'Pensionskasse Degussa Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Lipper Weg 190', '45764', 'Marl', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(378, NULL, NULL, '2141', 'Pensionskasse der Angestellten der ehemaligen Aschaffenburger Zellstoffwerke Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 33', '83064', 'Raubling', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(379, NULL, NULL, '2080', 'Pensionskasse der Angestellten der ehemaligen GASOLIN AG Versicherungsverein auf Gegenseitigkeit c/o Mercer Deutschland GmbH', NULL, NULL, NULL, NULL, 'Obere Saarlandstraße 2', '45470', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(380, NULL, NULL, '2136', 'Pensionskasse der Angestellten der Matth. Hohner AG Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Andreas-Koch-Straße 9', '78647', 'Trossingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(381, NULL, NULL, '2013', 'Pensionskasse der Angestellten der Thuringia Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Adenauerring 7', '81731', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(382, NULL, NULL, '2055', 'Pensionskasse der BERLIN-KÖLNISCHE Versicherungen', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(383, NULL, NULL, '2237', 'Pensionskasse der Betriebsangehörigen der Elektrizitätswerk Mittelbaden AG & Co.KG, Lahr/Schwarzwald, VVaG', NULL, NULL, NULL, NULL, 'Lotzbeckstraße 45', '77933', 'Lahr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(384, NULL, NULL, '2119', 'Pensionskasse der Bewag', NULL, NULL, NULL, NULL, 'Flottwellstraße 4 - 5', '10785', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(385, NULL, NULL, '2179', 'Pensionskasse der BHW Bausparkasse', NULL, NULL, NULL, NULL, 'Lubahnstraße 2', '31789', 'Hameln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(386, NULL, NULL, '2027', 'Pensionskasse der BOGESTRA Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Universitätsstraße 50-54', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(387, NULL, NULL, '2101', 'Pensionskasse der EDEKA Organisation V.V.a.G.', NULL, NULL, NULL, NULL, 'New-York-Ring 6', '22297', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(388, NULL, NULL, '2214', 'PENSIONSKASSE DER ENOVOS DEUTSCHLAND AG VVAG', NULL, NULL, NULL, NULL, 'Am Halberg 3', '66121', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(389, NULL, NULL, '2102', 'Pensionskasse der Firma Coca-Cola GmbH,Mülheim a.d.R., VVaG,Aon Jauch & Hübener Consulting GmbH', NULL, NULL, NULL, NULL, 'Luxemburger Allee 4', '45481', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(390, NULL, NULL, '2089', 'Pensionskasse der Firma Schenker & Co GmbH VVaG', NULL, NULL, NULL, NULL, 'Geleitsstraße 10', '60599', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(391, NULL, NULL, '2167', 'Pensionskasse der Frankfurter Bank', NULL, NULL, NULL, NULL, 'Bockenheimer Landstraße 10', '60323', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(392, NULL, NULL, '2155', 'Pensionskasse der Frankfurter Sparkasse', NULL, NULL, NULL, NULL, 'Große Gallusstraße 16 (6.Etage', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(393, NULL, NULL, '2219', 'Pensionskasse der Genossenschaftsorganisation Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Theresienstraße 19', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(394, NULL, NULL, '2063', 'Pensionskasse der Gewerkschaft Eisenhütte Westfalia c/o DBT GmbH', NULL, NULL, NULL, NULL, 'Industriestraße 1', '44534', 'Lünen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(395, NULL, NULL, '2067', 'PENSIONSKASSE der Hamburger Hochbahn Aktiengesellschaft - VVaG', NULL, NULL, NULL, NULL, 'Mattentwiete 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(396, NULL, NULL, '2223', 'Pensionskasse der HELVETIA Schweizerische Versicherungsgesellschaft Direktion für Deutschland', NULL, NULL, NULL, NULL, 'Berliner Straße 56/58', '60311', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(397, NULL, NULL, '2144', 'Pensionskasse der HypoVereinsbank', NULL, NULL, NULL, NULL, 'Fürstenfelder Straße 5', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(398, NULL, NULL, '2076', 'Pensionskasse der Lotsenbrüderschaft Elbe', NULL, NULL, NULL, NULL, 'Elbchaussee 330', '22609', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(399, NULL, NULL, '2204', 'Pensionskasse der Mitarbeiter der ehemaligen Frankona Rückversicherungs-AG V.V.a.G.', NULL, NULL, NULL, NULL, 'Dieselstraße 11', '85774', 'Unterföhring', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(400, NULL, NULL, '2154', 'Pensionskasse der Mitarbeiter der Hoechst-Gruppe VVaG', NULL, NULL, NULL, NULL, 'Brüningstraße 50', '65926', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(401, NULL, NULL, '2169', 'Pensionskasse der Novartis Pharma GmbH in Nürnberg Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Roonstraße 25', '90429', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(402, NULL, NULL, '2232', 'Pensionskasse der Rechtsanwälte und Notare VVaG', NULL, NULL, NULL, NULL, 'Weddinghofer Straße 85B', '59174', 'Kamen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(403, NULL, NULL, '2217', 'Pensionskasse der Schülke & Mayr GmbH V.V.a.G.', NULL, NULL, NULL, NULL, 'Caffamacherreihe 16', '20355', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(404, NULL, NULL, '2108', 'Pensionskasse der SV SparkassenVersicherung Lebensversicherung Aktiengesellschaft Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(405, NULL, NULL, '2116', 'Pensionskasse der Vereinigten Hagelversicherung VVaG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 25', '35392', 'Gießen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(406, NULL, NULL, '2235', 'Pensionskasse der VHV-Versicherungen', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(407, NULL, NULL, '2143', 'Pensionskasse der Wacker Chemie Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Hanns-Seidel-Platz 4', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(408, NULL, NULL, '2177', 'Pensionskasse der Wasserwirtschaftlichen Verbände, Essen VVaG', NULL, NULL, NULL, NULL, 'Kronprinzenstraße 37', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(409, NULL, NULL, '2109', 'Pensionskasse der Württembergischen', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(410, NULL, NULL, '2228', 'Pensionskasse des BDH Bundesverband für Rehabilitation und Interessenvertretung Behinderter, VVaG', NULL, NULL, NULL, NULL, 'Eifelstraße 7', '53119', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(411, NULL, NULL, '2248', 'Pensionskasse Deutscher Eisenbahnen und Straßenbahnen VVaG', NULL, NULL, NULL, NULL, 'Volksgartenstraße 54a', '50677', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(412, NULL, NULL, '2244', 'Pensionskasse Dynamit Nobel Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kaiserstraße 52', '53840', 'Troisdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(413, NULL, NULL, '2008', 'Pensionskasse für Angestellte der Continental Aktiengesellschaft VVaG, Hannover', NULL, NULL, NULL, NULL, 'Goseriede 8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(414, NULL, NULL, '2035', 'Pensionskasse für die Angest. der BARMER Ersatzkasse (Versicherungsverein auf Gegenseitigkeit)', NULL, NULL, NULL, NULL, 'Lichtscheider Straße 89', '42285', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(415, NULL, NULL, '2227', 'Pensionskasse für die Arbeitnehmerinnen und Arbeitnehmer des ZDF Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'ZDF-Straße 1', '55127', 'Mainz', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(416, NULL, NULL, '2052', 'Pensionskasse für die Deutsche Wirtschaft vormals Pensionskasse der chemischen Industrie Deutschlands', NULL, NULL, NULL, NULL, 'Am Burgacker 37', '47051', 'Duisburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(417, NULL, NULL, '2034', 'Pensionskasse HT Troplast Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Kaiserstraße', '53840', 'Troisdorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(418, NULL, NULL, '2142', 'Pensionskasse Konzern Versicherungskammer Bayern VVaG', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '81530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(419, NULL, NULL, '2145', 'Pensionskasse Maxhütte VVaG', NULL, NULL, NULL, NULL, 'Kunst-Fischer-Gasse 2', '92237', 'Sulzbach-Rosenberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(420, NULL, NULL, '2226', 'PENSIONSKASSE PEUGEOT DEUTSCHLAND VVaG', NULL, NULL, NULL, NULL, 'Koßmannstraße 19', '66119', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer_all` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(421, NULL, NULL, '2190', 'Pensionskasse Raiffeisen -Schulze-Delitzsch Norddeutschland Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Raiffeisenstraße 1-3', '24768', 'Rendsburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(422, NULL, NULL, '2225', 'Pensionskasse Rundfunk', NULL, NULL, NULL, NULL, 'Bertramstraße 8', '60320', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(423, NULL, NULL, '2138', 'Pensionskasse Schoeller & Hoesch VVaG', NULL, NULL, NULL, NULL, 'Hördener Straße 5', '76593', 'Gernsbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(424, NULL, NULL, '2095', 'Pensionskasse SIGNAL Versicherungen', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(425, NULL, NULL, '2092', 'Pensionskasse westdeutscher Genossenschaften VVaG', NULL, NULL, NULL, NULL, 'Mecklenbecker Straße 235-239', '48163', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(426, NULL, NULL, '5856', 'PENSIONS-SICHERUNGS-VEREIN Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Berlin-Kölnische-Allee 2-4', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(427, NULL, NULL, '2096', 'Philips Pensionskasse (Versicherungsverein auf Gegenseitigkeit)', NULL, NULL, NULL, NULL, 'Lübeckertordamm 1-3', '20099', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(428, NULL, NULL, '2007', 'Phoenix Pensionskasse von 1925 Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(429, NULL, NULL, '1123', 'PLUS Lebensversicherungs AG', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(430, NULL, NULL, '2258', 'Pro BAV Pensionskasse AG', NULL, NULL, NULL, NULL, 'Colonia Allee 10-20', '51067', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(431, NULL, NULL, '5147', 'ProTect Versicherung AG', NULL, NULL, NULL, NULL, 'Kölner Landstrasse 33', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(432, NULL, NULL, '1309', 'Protektor Lebensversicherungs -AG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 43G', '10117', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(433, NULL, NULL, '4135', 'Provinzial Krankenversicherung Hannover AG', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(434, NULL, NULL, '1081', 'Provinzial Lebensversicherung Hannover', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(435, NULL, NULL, '5446', 'Provinzial Nord Brandkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sophienblatt 33', '24114', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(436, NULL, NULL, '6985', 'Provinzial NordWest Holding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Provinzial-Allee 1', '48131', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(437, NULL, NULL, '1083', 'Provinzial NordWest Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Sophienblatt 33', '24114', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(438, NULL, NULL, '2269', 'Provinzial Pensionskasse Hannover AG', NULL, NULL, NULL, NULL, 'Schiffgraben 4', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(439, NULL, NULL, '6986', 'Provinzial Rheinland Holding', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(440, NULL, NULL, '1082', 'Provinzial Rheinland Lebensversicherung AG Die Versicherung der Sparkassen', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(441, NULL, NULL, '5095', 'Provinzial Rheinland Versicherung AG Die Versicherung der Sparkassen', NULL, NULL, NULL, NULL, 'Provinzialplatz 1', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(442, NULL, NULL, '1111', 'PRUDENTIA-Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Karl-Theodor-Str. 6', '40213', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(443, NULL, NULL, '5583', 'PVAG Polizeiversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(444, NULL, NULL, '5836', 'R + V Rechtsschutzversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(445, NULL, NULL, '5438', 'R+V ALLGEMEINE VERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(446, NULL, NULL, '5137', 'R+V Direktversicherung AG', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(447, NULL, NULL, '3311', 'R+V Gruppenpensionsfonds AG', NULL, NULL, NULL, NULL, 'Kaufingerstraße 9', '80331', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(448, NULL, NULL, '4116', 'R+V Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(449, NULL, NULL, '1085', 'R+V Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Taunusstr. 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(450, NULL, NULL, '1141', 'R+V LEBENSVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(451, NULL, NULL, '3305', 'R+V Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(452, NULL, NULL, '2285', 'R+V PENSIONSKASSE AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(453, NULL, NULL, '2045', 'R+V PENSIONSVERSICHERUNG a.G.', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(454, NULL, NULL, '6960', 'R+V Versicherung AG', NULL, NULL, NULL, NULL, 'Taunusstraße 1', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(455, NULL, NULL, '5799', 'Real Garant Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Strohgäustraße 5', '73765', 'Neuhausen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(456, NULL, NULL, '2148', 'Rentenzuschußkasse der N-ERGIE Aktiengesellschaft Nürnberg', NULL, NULL, NULL, NULL, 'Hainstraße 34', '90461', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(457, NULL, NULL, '2011', 'Renten-Zuschuß-Kasse des Norddeutschen Lloyd', NULL, NULL, NULL, NULL, 'Am Wall 140', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(458, NULL, NULL, '6998', 'REVIUM Rückversicherung AG', NULL, NULL, NULL, NULL, 'Carl Braun Straße 1', '34212', 'Melsungen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(459, NULL, NULL, '2282', 'Rheinische Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Hauptstraße 105', '51373', 'Leverkusen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(460, NULL, NULL, '3154', 'Rheinisch-Westfälische Sterbekasse Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Lindenallee 74', '45127', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(461, NULL, NULL, '1018', 'RheinLand Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(462, NULL, NULL, '5798', 'RheinLand Versicherungs Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rheinlandplatz', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(463, NULL, NULL, '5121', 'Rhion Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'RheinLandplatz 1', '41460', 'Neuss', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(464, NULL, NULL, '6946', 'RISICOM Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marktplatz 3', '82031', 'Grünwald', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(465, NULL, NULL, '5807', 'ROLAND Rechtsschutz -Versicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(466, NULL, NULL, '5528', 'ROLAND Schutzbrief -Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Deutz-Kalker-Straße 46', '50679', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(467, NULL, NULL, '5901', 'RS Reise- Schutz Versicherung AG', NULL, NULL, NULL, NULL, 'Bahnhofstraße 10', '74189', 'Weinsberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(468, NULL, NULL, '2012', 'Ruhegeld-, Witwen- und Waisenkasse d. Bergischen Elektrizitäts-Versorgungs-GmbH (VVaG)', NULL, NULL, NULL, NULL, 'Bromberger Straße 39-41', '42281', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(469, NULL, NULL, '2028', 'Ruhegeldkasse der Bremer Straßenbahn (VVaG)', NULL, NULL, NULL, NULL, 'Flughafendamm 12', '28199', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(470, NULL, NULL, '3325', 'RWE Pensionsfonds AG c/o RWE AG', NULL, NULL, NULL, NULL, 'Opernplatz 1', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(471, NULL, NULL, '5051', 'S DirektVersicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kölner Landstraße 33', '40591', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(472, NULL, NULL, '5773', 'Saarland Feuerversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mainzer Straße 32-34', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(473, NULL, NULL, '1150', 'SAARLAND Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mainzer Straße 32-34', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(474, NULL, NULL, '2176', 'Scheufelen-Versorgungskasse Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Papierfabrik Scheufelen', '73252', 'Lenningen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(475, NULL, NULL, '5491', 'Schleswiger Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Gildehaus', '25924', 'Emmelsbüll-Horsbüll', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(476, NULL, NULL, '5559', 'SCHNEVERDINGER Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Rotenburger Straße 1-3', '29640', 'Schneverdingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(477, NULL, NULL, '5018', 'Schutzverein Deutscher Rheder V.a.G.', NULL, NULL, NULL, NULL, 'Am Kaiserkai 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(478, NULL, NULL, '5690', 'SCHWARZMEER UND OSTSEE Versicherungs -Aktiengesellschaft SOVAG', NULL, NULL, NULL, NULL, 'Schwanenwik 37', '22087', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(479, NULL, NULL, '1168', 'Schwestern-Versicherungsverein vom Roten Kreuz in Deutschland a.G.', NULL, NULL, NULL, NULL, 'Heilsbachstraße 32', '53123', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(480, NULL, NULL, '6901', 'SCOR Rückversicherung (Deutschland) AG', NULL, NULL, NULL, NULL, 'Im Mediapark 8', '50670', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(481, NULL, NULL, '2164', 'SELBSTHILFE Pensionskasse der Caritas VVaG', NULL, NULL, NULL, NULL, 'Dürener Straße 341', '50935', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(482, NULL, NULL, '5008', 'SHB Allgemeine Versicherung VVaG', NULL, NULL, NULL, NULL, 'Johannes-Albers-Allee 2', '53639', 'Königswinter', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(483, NULL, NULL, '3324', 'Siemens Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marktplatz 3', '82031', 'Grünwald', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(484, NULL, NULL, '5125', 'SIGNAL IDUNA Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(485, NULL, NULL, '2252', 'SIGNAL IDUNA Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Neue Rabenstraße 15', '20354', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(486, NULL, NULL, '4002', 'SIGNAL Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(487, NULL, NULL, '5451', 'SIGNAL Unfallversicherung a.G.', NULL, NULL, NULL, NULL, 'Joseph-Scherer-Straße 3', '44139', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(488, NULL, NULL, '1157', 'Skandia Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Kaiserin-Augusta-Allee 108', '10553', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(489, NULL, NULL, '3079', 'SOLIDAR Versicherungsgemeinschaft Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Alleestraße 119', '44793', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(490, NULL, NULL, '4082', 'SONO Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Hans-Böckler-Straße 51', '46236', 'Bottrop', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(491, NULL, NULL, '3150', 'SONO Sterbegeldversicherung a.G.', NULL, NULL, NULL, NULL, 'Hans-Böckler-Straße 51', '46236', 'Bottrop', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(492, NULL, NULL, '3316', 'Sparkassen Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Gustav-Heinemann-Ufer 56', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(493, NULL, NULL, '2265', 'Sparkassen Pensionskasse AG', NULL, NULL, NULL, NULL, 'Gustav-Heinemann-Ufer 56', '50968', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(494, NULL, NULL, '5781', 'Sparkassen-Versicherung Sachsen Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An der Flutrinne 12', '01139', 'Dresden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(495, NULL, NULL, '1153', 'Sparkassen-Versicherung Sachsen Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'An der Flutrinne 12', '01139', 'Dresden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(496, NULL, NULL, '4106', 'St. Martinus Priesterverein d. Diözese Rottenburg-Stuttgart Kranken- und Sterbekasse-(KSK) Vers.Verein auf Gegenseitigk.', NULL, NULL, NULL, NULL, 'Hohenzollernstraße 23', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(497, NULL, NULL, '3083', 'Sterbekasse "Hoffnung" (vorm. Kindersterbekasse "Hoffnung")', NULL, NULL, NULL, NULL, 'Loher Straße 14', '42283', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(498, NULL, NULL, '3084', 'Sterbekasse der Bediensteten der Stadtverwaltung Dortmund', NULL, NULL, NULL, NULL, 'Weischedestraße 25', '44265', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(499, NULL, NULL, '3138', 'Sterbekasse der Belegschaft der Saarstahl AG Völklingen Werk Völklingen und Werk Burbach', NULL, NULL, NULL, NULL, 'Haldenweg 9', '66333', 'Völklingen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(500, NULL, NULL, '3153', 'Sterbekasse der Beschäftigten der Deutschen Rentenversicherung Knappschaft-Bahn-See VVaG', NULL, NULL, NULL, NULL, 'Königsallee 175', '44789', 'Bochum', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(501, NULL, NULL, '3135', 'Sterbekasse der Betriebsangehörigen der BVG (ehem. Sterbekasse der U-Bahn)', NULL, NULL, NULL, NULL, 'Holzmarktstraße 15-17', '10179', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(502, NULL, NULL, '3155', 'Sterbekasse der Feuerwehren, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Röntgenstraße 60', '31675', 'Bückeburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(503, NULL, NULL, '3139', 'Sterbekasse der Saarbergleute VVaG', NULL, NULL, NULL, NULL, 'Hafenstraße 25', '66111', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(504, NULL, NULL, '3009', 'Sterbekasse Evangelischer Freikirchen VVaG', NULL, NULL, NULL, NULL, 'Am Kleinen Wannsee 5', '14109', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(505, NULL, NULL, '3102', 'Sterbekasse f. d. Angestellten der BHF-BANK', NULL, NULL, NULL, NULL, 'Bockenheimer Landstraße 10', '60323', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(506, NULL, NULL, '3057', 'Sterbekasse für d. Belegschaft der Hamburger Wasserwerke GmbH Hamburg VVaG, Hamburg', NULL, NULL, NULL, NULL, 'Billhorner Deich 2', '20539', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(507, NULL, NULL, '3147', 'Sterbekasse für den Niederrhein und das ganze Ruhrgebiet', NULL, NULL, NULL, NULL, 'Brabanterstraße 14', '47533', 'Kleve', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(508, NULL, NULL, '3017', 'Sterbekasse für die Angestellten der Deutschen Bank', NULL, NULL, NULL, NULL, 'Alfred-Herrhausen-Allee 16-24', '65760', 'Eschborn am Taunus', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(509, NULL, NULL, '3152', 'Sterbekasse Sozialversicherung - gegr. in der LVA Rheinprovinz - Düsseldorf', NULL, NULL, NULL, NULL, 'Königsallee 71', '40194', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(510, NULL, NULL, '3067', 'Sterbe-Unterstützungs -Vereinigung der Beschäftigten der Stadt München', NULL, NULL, NULL, NULL, 'Rosenheimer Straße 118', '81669', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(511, NULL, NULL, '1104', 'Stuttgarter Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(512, NULL, NULL, '5586', 'Stuttgarter Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rotebühlstraße 120', '70197', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(513, NULL, NULL, '5025', 'Süddeutsche Allgemeine Versicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(514, NULL, NULL, '4039', 'Süddeutsche Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(515, NULL, NULL, '1089', 'Süddeutsche Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Raiffeisenplatz 5', '70736', 'Fellbach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(516, NULL, NULL, '5036', 'SV SparkassenVersicherung Gebäudeversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(517, NULL, NULL, '6964', 'SV SparkassenVersicherung Holding Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(518, NULL, NULL, '1091', 'SV SparkassenVersicherung Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Löwentorstraße 65', '70376', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(519, NULL, NULL, '1090', 'Swiss Life AG, Niederlassung für Deutschland', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(520, NULL, NULL, '1321', 'Swiss Life Insurance Solutions AG', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(521, NULL, NULL, '1334', 'Swiss Life Insurance Solutions S.A. Niederlassung für Deutschland Herr René Macher', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(522, NULL, NULL, '3315', 'Swiss Life Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(523, NULL, NULL, '2270', 'Swiss Life Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Berliner Straße 85', '80805', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(524, NULL, NULL, '1132', 'TARGO Lebensversicherung AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(525, NULL, NULL, '5790', 'TARGO Versicherung AG', NULL, NULL, NULL, NULL, 'ProACTIV-Platz 1', '40721', 'Hilden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(526, NULL, NULL, '3302', 'Telekom-Pensionsfonds a.G.', NULL, NULL, NULL, NULL, 'Friedrich-Ebert-Allee 140', '53113', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(527, NULL, NULL, '5767', 'Thüga Schadenausgleichskasse München VVaG', NULL, NULL, NULL, NULL, 'Hansjakobstraße 129', '81825', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(528, NULL, NULL, '6996', 'ThyssenKrupp Reinsurance AG', NULL, NULL, NULL, NULL, 'Am Thyssenhaus 3', '45128', 'Essen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(529, NULL, NULL, '5567', 'TRIAS Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximiliansplatz 5', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(530, NULL, NULL, '5459', 'Uelzener Allgemeine Versicherungs-Gesellschaft a.G.', NULL, NULL, NULL, NULL, 'Veerßer Straße 67', '29525', 'Uelzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(531, NULL, NULL, '1152', 'Uelzener Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Veerßer Straße 67', '29525', 'Uelzen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(532, NULL, NULL, '4108', 'UNION KRANKENVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Peter-Zimmer-Straße 2', '66123', 'Saarbrücken', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(533, NULL, NULL, '5094', 'Union Reiseversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(534, NULL, NULL, '5462', 'United Services Automobile Association, San Antonio Texas/USA Direktion für Deutschland Herrn Martin Göller', NULL, NULL, NULL, NULL, 'Königsberger Straße 1', '60487', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(535, NULL, NULL, '5463', 'uniVersa Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'Sulzbacher Str. 1-7', '90489', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(536, NULL, NULL, '4045', 'uniVersa Krankenversicherung a.G.', NULL, NULL, NULL, NULL, 'Sulzbacher Straße 1-7', '90489', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(537, NULL, NULL, '1092', 'uniVersa Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Sulzbacher Str. 1 - 7', '90489', 'Nürnberg, Mittelfr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(538, NULL, NULL, '3319', 'VdW Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Leonhard-Stinnes-Straße 66', '45470', 'Mülheim an der Ruhr', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(539, NULL, NULL, '6930', 'VERBAND ÖFFENTLICHER VERSICHERER', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(540, NULL, NULL, '5419', 'Vereinigte Hagelversicherung VVaG', NULL, NULL, NULL, NULL, 'Wilhelmstraße 25', '35392', 'Gießen, Lahn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(541, NULL, NULL, '2103', 'Vereinigte Pensionskassen VVaG', NULL, NULL, NULL, NULL, 'Mattentwiete 6', '20457', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(542, NULL, NULL, '1093', 'Vereinigte Postversicherung Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 19', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(543, NULL, NULL, '5530', 'Vereinigte Schiffs -Versicherung V.a.G.', NULL, NULL, NULL, NULL, 'Seelhorststraße 7', '30175', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(544, NULL, NULL, '5348', 'Vereinigte Tierversicherung, Gesellschaft auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Sonnenberger Straße 2', '65193', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(545, NULL, NULL, '5511', 'Vereinigte Vers.Ges.v. Deutschland Zweign.d. Combined Insurance Company of America Chicago, Illinois, USA John Gerard Noonan', NULL, NULL, NULL, NULL, 'Friedrich-Bergius-Straße 9', '65203', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(546, NULL, NULL, '4132', 'Vereinte Spezial Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(547, NULL, NULL, '5441', 'Vereinte Spezial Versicherung AG', NULL, NULL, NULL, NULL, 'Fritz-Schäffer-Straße 9', '81737', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(548, NULL, NULL, '2009', 'VERKA Kirchliche Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Schellendorffstraße 17-19', '14199', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(549, NULL, NULL, '2041', 'Verseidag-Werks -Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Girmesgath 5', '47803', 'Krefeld', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(550, NULL, NULL, '6970', 'Versicherungskammer Bayern Konzern-Rückversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gewürzmühlstraße 13', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(551, NULL, NULL, '2277', 'Versicherungskammer Bayern Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80538', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(552, NULL, NULL, '5042', 'Versicherungskammer Bayern, Versicherungsanstalt des öffentlichen Rechts', NULL, NULL, NULL, NULL, 'Maximilianstraße 53', '80530', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(553, NULL, NULL, '5468', 'Versicherungsverband Deutscher Eisenbahnen Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Breite Straße 147 - 151', '50667', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(554, NULL, NULL, '3060', 'Versicherungsverein "Kurhessische Poststerbekasse"', NULL, NULL, NULL, NULL, 'Wilhelmsstraße 6 III', '34117', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(555, NULL, NULL, '3072', 'Versicherungsverein Rasselstein', NULL, NULL, NULL, NULL, 'Koblenzer Straße 141', '56626', 'Andernach', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(556, NULL, NULL, '2283', 'Versorgungsanstalt des Bundes und der Länder', NULL, NULL, NULL, NULL, 'Hans-Thoma-Straße 19', '76133', 'Karlsruhe', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(557, NULL, NULL, '2289', 'Versorgungsausgleichskasse Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Reinsburgstraße 19', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(558, NULL, NULL, '2158', 'Versorgungskasse der Angest. der Vereinigte Deutsche Metallwerke AG VVaG', NULL, NULL, NULL, NULL, 'Lurgiallee 5', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(559, NULL, NULL, '2159', 'Versorgungskasse der Angestellten der Metallgesellschaft AG VVaG', NULL, NULL, NULL, NULL, 'Lurgiallee 5', '60439', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(560, NULL, NULL, '2073', 'Versorgungskasse der Angestellten der Norddeutschen Affinerie', NULL, NULL, NULL, NULL, 'Hovestraße 50', '20539', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(561, NULL, NULL, '2129', 'Versorgungskasse der Arbeiter und Angestellten der ehemalig. Großkraftwerk Franken AG', NULL, NULL, NULL, NULL, 'Felsenstraße 14', '90449', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);
INSERT INTO `versicherer_all` (`id`, `parentId`, `parentName`, `vuNummer`, `name`, `nameZusatz`, `nameZusatz2`, `kuerzel`, `gesellschaftsNr`, `strasse`, `plz`, `stadt`, `bundesLand`, `land`, `postfach`, `postfachName`, `postfachPlz`, `postfachOrt`, `vermittelbar`, `communication1`, `communication2`, `communication3`, `communication4`, `communication5`, `communication6`, `communication1Type`, `communication2Type`, `communication3Type`, `communication4Type`, `communication5Type`, `communication6Type`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(562, NULL, NULL, '2238', 'Versorgungskasse der Bayerischen Milchindustrie Landshut eG Nürnberg VVaG', NULL, NULL, NULL, NULL, 'Platenstraße 31', '90441', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(563, NULL, NULL, '2047', 'Versorgungskasse der Deutscher Herold Versicherungsgesellschaften,Versicherungsverein a.G., Bonn', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25 - 33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(564, NULL, NULL, '2183', 'Versorgungskasse der ehemaligen Bayernwerk AG, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Brienner Straße 40', '80333', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(565, NULL, NULL, '2032', 'Versorgungskasse der Firma M. DuMont Schauberg Expedition der Kölnischen Zeitung Neven DuMont Haus', NULL, NULL, NULL, NULL, 'Amsterdamer Straße 92', '50735', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(566, NULL, NULL, '2020', 'Versorgungskasse der Volksfürsorge VVaG', NULL, NULL, NULL, NULL, 'Besenbinderhof 43', '20099', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(567, NULL, NULL, '2010', 'Versorgungskasse des Norddeutschen Lloyd', NULL, NULL, NULL, NULL, 'Am Wall 140', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(568, NULL, NULL, '2031', 'Versorgungskasse Deutscher Unternehmen, Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Zum Dänischen Wohld 1 - 3', '24159', 'Kiel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(569, NULL, NULL, '2093', 'Versorgungskasse Energie Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Tresckowstraße 3', '30457', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(570, NULL, NULL, '2029', 'Versorgungskasse f. d. Angest. d. AachenMünchener Versicherung AG u.d. Generali Deutschland Holding AG', NULL, NULL, NULL, NULL, 'AachenMünchener-Platz 1', '52064', 'Aachen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(571, NULL, NULL, '2056', 'Versorgungskasse Fritz Henkel Versicherungsverein auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Adenauerallee 21', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(572, NULL, NULL, '2099', 'Versorgungskasse Gothaer Versicherungsbank VVaG', NULL, NULL, NULL, NULL, 'Arnoldiplatz 1', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(573, NULL, NULL, '2175', 'Versorgungskasse Radio Bremen', NULL, NULL, NULL, NULL, 'Diepenau 2', '28195', 'Bremen', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(574, NULL, NULL, '2194', 'Versorgungslasten-Ausgleichskasse des Genossenschaftsverbandes Norddeutschland e.V. - VVaG - Hannover', NULL, NULL, NULL, NULL, 'Hannoversche Straße 149', '30627', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(575, NULL, NULL, '5862', 'VHV Allgemeine Versicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(576, NULL, NULL, '1314', 'VHV Lebensversicherung AG', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(577, NULL, NULL, '5464', 'VHV Vereinigte Hannoversche Versicherung a.G.', NULL, NULL, NULL, NULL, 'VHV Platz 1', '30177', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(578, NULL, NULL, '4105', 'Victoria Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 2', '40477', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(579, NULL, NULL, '1140', 'Victoria Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40477', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(580, NULL, NULL, '2259', 'Victoria Pensionskasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Victoriaplatz 1', '40198', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(581, NULL, NULL, '3309', 'VIFA Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Schellendorffstraße 17/19', '14199', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(582, NULL, NULL, '2272', 'Volksfürsorge Pensionskasse AG', NULL, NULL, NULL, NULL, 'Besenbinderhof 43', '20097', 'Hamburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(583, NULL, NULL, '6997', 'Volkswagen Reinsurance AG', NULL, NULL, NULL, NULL, 'Gifhorner Straße 57', '38112', 'Braunschweig', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(584, NULL, NULL, '1099', 'Volkswohl-Bund Lebensversicherung a.G.', NULL, NULL, NULL, NULL, 'Südwall 37-41', '44137', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(585, NULL, NULL, '5484', 'VOLKSWOHL-BUND SACHVERSICHERUNG AKTIENGESELLSCHAFT', NULL, NULL, NULL, NULL, 'Südwall 37-41', '44128', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(586, NULL, NULL, '1151', 'Vorsorge Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Rather Straße 110a', '40476', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(587, NULL, NULL, '3029', 'Vorsorgekasse der Commerzbank Versicherungsverein a.G.', NULL, NULL, NULL, NULL, 'Turmstraße 83-84', '10551', 'Berlin', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(588, NULL, NULL, '3107', 'Vorsorgekasse Hoesch Dortmund Sterbegeldversicherung VVaG', NULL, NULL, NULL, NULL, 'Oesterholzstraße 124', '44145', 'Dortmund', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(589, NULL, NULL, '3122', 'Vorsorgeversicherung Siemens VaG', NULL, NULL, NULL, NULL, 'Gugelstraße 115p', '90459', 'Nürnberg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Sterbekasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(590, NULL, NULL, '5461', 'VPV Allgemeine Versicherungs-AG', NULL, NULL, NULL, NULL, 'Pohligstraße 3', '50969', 'Köln', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(591, NULL, NULL, '1160', 'VPV Lebensversicherungs -Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Mittlerer Pfad 19', '70499', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(592, NULL, NULL, '5099', 'VRK Versicherungsverein auf Gegenseitigkeit im Raum der Kirchen', NULL, NULL, NULL, NULL, 'Kölnische Straße 108', '34119', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(593, NULL, NULL, '5082', 'Waldenburger Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Alfred-Leikam-Straße 25', '74523', 'Schwäbisch Hall', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(594, NULL, NULL, '5488', 'WERTGARANTIE Technische Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Breite Straße 6-8', '30159', 'Hannover', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(595, NULL, NULL, '3317', 'West Pensionsfonds AG', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(596, NULL, NULL, '2266', 'West Pensionskasse AG', NULL, NULL, NULL, NULL, 'Hansaallee 177', '40549', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(597, NULL, NULL, '5093', 'Westfälische Provinzial Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Provinzial-Allee 1', '48159', 'Münster', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(598, NULL, NULL, '1149', 'WGV-Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(599, NULL, NULL, '5525', 'WGV-Versicherung AG', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(600, NULL, NULL, '2262', 'winsecura Pensionkasse Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Frankfurter Straße 50', '65178', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(601, NULL, NULL, '2288', 'Wuppertaler Pensionskasse VVaG', NULL, NULL, NULL, NULL, 'Lichtscheider Straße 89-95', '42285', 'Wuppertal', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(602, NULL, NULL, '5479', 'Württembergische Gemeinde-Versicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Tübinger Straße 55', '70178', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(603, NULL, NULL, '4139', 'Württembergische Krankenversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Krankenversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(604, NULL, NULL, '1005', 'Württembergische Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(605, NULL, NULL, '5783', 'Württembergische Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(606, NULL, NULL, '5590', 'Würzburger Versicherungs-AG', NULL, NULL, NULL, NULL, 'Bahnhofstraße 11', '97070', 'Würzburg', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(607, NULL, NULL, '6958', 'Wüstenrot & Württembergische AG', NULL, NULL, NULL, NULL, 'Gutenbergstraße 30', '70176', 'Stuttgart', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Rückversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(608, NULL, NULL, '5476', 'WWK Allgemeine Versicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marsstr. 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(609, NULL, NULL, '1103', 'WWK Lebensversicherung auf Gegenseitigkeit', NULL, NULL, NULL, NULL, 'Marsstraße 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(610, NULL, NULL, '3318', 'WWK Pensionsfonds Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Marsstraße 37', '80335', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionsfonds', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(611, NULL, NULL, '2281', 'Zentrales Versorgungswerk für das Dachdeckerhandwerk VVaG', NULL, NULL, NULL, NULL, 'Rosenstraße 2', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(612, NULL, NULL, '1138', 'Zurich Deutscher Herold Lebensversicherung Aktiengesellschaft', NULL, NULL, NULL, NULL, 'Poppelsdorfer Allee 25 - 33', '53115', 'Bonn', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Lebensversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(613, NULL, NULL, '5050', 'Zurich Versicherung Aktiengesellschaft (Deutschland)', NULL, NULL, NULL, NULL, 'Solmsstraße 27-37', '60486', 'Frankfurt', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Schaden- und Unfallversicherer', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(614, NULL, NULL, '2222', 'Zusatzversorgungskasse der Steine- und Erden-Industrie u. des Betonsteinhandwerks VVaG Die Bayerische Pensionskasse', NULL, NULL, NULL, NULL, 'Bavariaring 23', '80336', 'München', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(615, NULL, NULL, '2189', 'Zusatzversorgungskasse des Baugewerbes AG', NULL, NULL, NULL, NULL, 'Wettinerstraße 7', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(616, NULL, NULL, '2209', 'Zusatzversorgungskasse des Dachdeckerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Rosenstraße 2', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(617, NULL, NULL, '2242', 'Zusatzversorgungskasse des Gerüstbaugewerbes VVaG', NULL, NULL, NULL, NULL, 'Mainzer Straße 98 - 102', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(618, NULL, NULL, '2236', 'Zusatzversorgungskasse des Maler- u. Lackiererhandwerks VVaG', NULL, NULL, NULL, NULL, 'John-F.-Kennedy-Straße 6', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(619, NULL, NULL, '2218', 'Zusatzversorgungskasse des Steinmetz- und Steinbildhauerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Washingtonstraße 75', '65189', 'Wiesbaden', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(620, NULL, NULL, '2220', 'Zusatzversorgungskasse für die Beschäftigten der Deutschen Brot- und Backwarenindustrie VVaG', NULL, NULL, NULL, NULL, 'In den Diken 33', '40472', 'Düsseldorf', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(621, NULL, NULL, '2221', 'Zusatzversorgungskasse für die Beschäftigten des Deutschen Bäckerhandwerks VVaG', NULL, NULL, NULL, NULL, 'Bondorfer Straße 23', '53604', 'Bad Honnef', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0),
(622, NULL, NULL, '2253', 'Zusatzversorgungswerk für Arbeitnehmer in der Land- und Forstwirtschaft-ZLF VVaG', NULL, NULL, NULL, NULL, 'Druseltalstraße 51', '34131', 'Kassel', NULL, 'Deutschland', 0, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 4, 7, 10, 14, 'Pensionskasse', NULL, NULL, NULL, NULL, NULL, '2010-09-06 09:03:56', '2010-09-06 09:03:56', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `vertraege`
--

CREATE TABLE IF NOT EXISTS `vertraege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL DEFAULT '-1',
  `versichererId` int(11) NOT NULL,
  `produktId` int(11) NOT NULL,
  `kundenKennung` varchar(255) NOT NULL,
  `bankkontoId` int(11) NOT NULL DEFAULT '-1',
  `zusatzadresseId` int(11) NOT NULL DEFAULT '-1',
  `beratungsprotokollId` int(11) DEFAULT '-1',
  `mandantenId` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL,
  `vertragsTyp` tinyint(4) NOT NULL DEFAULT '0',
  `vertragGrp` int(11) NOT NULL DEFAULT '-1',
  `policennr` varchar(500) DEFAULT NULL,
  `policeDatum` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `wertungDatum` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `courtage` tinyint(1) NOT NULL DEFAULT '0',
  `zahlWeise` tinyint(4) NOT NULL DEFAULT '0',
  `zahlArt` varchar(500) DEFAULT NULL,
  `selbstbeteiligung` int(11) NOT NULL DEFAULT '0',
  `jahresNetto` double(11,2) NOT NULL DEFAULT '0.00',
  `steuer` double(11,2) NOT NULL DEFAULT '0.00',
  `gebuehr` double(11,2) NOT NULL DEFAULT '0.00',
  `jahresBrutto` double(11,2) NOT NULL DEFAULT '0.00',
  `rabatt` double(11,2) NOT NULL DEFAULT '0.00',
  `zuschlag` double(11,2) NOT NULL DEFAULT '0.00',
  `waehrungId` int(11) NOT NULL DEFAULT '1',
  `erstbeitrag_bezahlt` tinyint(1) NOT NULL DEFAULT '0',
  `honorar_bezahlt` tinyint(1) NOT NULL DEFAULT '0',
  `antrag` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `faellig` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `hauptfaellig` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `beginn` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `ablauf` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `maklerEingang` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `stornoDatum` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `storno` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `stornoGrund` text,
  `laufzeit` int(11) NOT NULL DEFAULT '0',
  `courtage_datum` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `comments` text,
  `custom1` text,
  `custom2` text,
  `custom3` text,
  `custom4` text,
  `custom5` text,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `vertraege`
--

INSERT INTO `vertraege` (`id`, `parentId`, `versichererId`, `produktId`, `kundenKennung`, `bankkontoId`, `zusatzadresseId`, `beratungsprotokollId`, `mandantenId`, `benutzerId`, `vertragsTyp`, `vertragGrp`, `policennr`, `policeDatum`, `wertungDatum`, `courtage`, `zahlWeise`, `zahlArt`, `selbstbeteiligung`, `jahresNetto`, `steuer`, `gebuehr`, `jahresBrutto`, `rabatt`, `zuschlag`, `waehrungId`, `erstbeitrag_bezahlt`, `honorar_bezahlt`, `antrag`, `faellig`, `hauptfaellig`, `beginn`, `ablauf`, `maklerEingang`, `stornoDatum`, `storno`, `stornoGrund`, `laufzeit`, `courtage_datum`, `comments`, `custom1`, `custom2`, `custom3`, `custom4`, `custom5`, `created`, `modified`, `status`) VALUES
(1, -1, 15, 1, '11002', -1, -1, -1, -1, 1, 0, -1, 'ABX-2312233-R', '2011-06-22 22:00:00', '2011-06-27 22:00:00', 1, 0, 'Überweisung', 0, 864.00, 16.00, 0.00, 120.00, 0.00, 4.00, 1, 0, 0, '2011-06-27 22:00:00', '2011-06-27 22:00:00', '2011-06-27 22:00:00', '2011-06-27 22:00:00', '2011-06-27 22:00:00', '2011-06-27 22:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', NULL, 0, '1971-01-31 23:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '2011-06-21 22:00:00', '2011-07-23 12:23:42', 0),
(2, -1, 4, 1, '11002', -1, -1, -1, -1, 0, 0, -1, 'AJK-2312312/07', '1971-01-31 23:00:00', '1971-01-31 23:00:00', 0, 0, NULL, 0, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1, 0, 0, '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', '1971-01-31 23:00:00', NULL, 0, '1971-01-31 23:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '1971-01-31 23:00:00', '2011-07-23 12:31:08', 0),
(3, -1, 15, 2, '11011', -1, -1, -1, -1, 1, 0, -1, 'ABI-KJR17-231333', '2010-01-31 23:00:00', '2010-01-31 23:00:00', 0, 0, NULL, 0, 1412.00, 10.00, 0.00, 107.00, 3.00, 0.00, 1, 0, 0, '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', '2010-01-31 23:00:00', NULL, 3, '2010-01-31 23:00:00', NULL, NULL, NULL, NULL, NULL, NULL, '2010-01-31 23:00:00', '2011-07-23 12:25:17', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `vertraege_grp`
--

CREATE TABLE IF NOT EXISTS `vertraege_grp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grpid` int(11) NOT NULL,
  `vertragid` int(11) NOT NULL,
  `comments` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `waehrungen`
--

CREATE TABLE IF NOT EXISTS `waehrungen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordering` int(11) NOT NULL DEFAULT '-1',
  `isocode` varchar(255) DEFAULT NULL,
  `bezeichnung` varchar(1000) NOT NULL,
  `neuanlage` tinyint(1) NOT NULL DEFAULT '1',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Daten für Tabelle `waehrungen`
--

INSERT INTO `waehrungen` (`id`, `ordering`, `isocode`, `bezeichnung`, `neuanlage`, `status`) VALUES
(1, 0, 'EUR', '€', 1, 0),
(2, 1, 'DEM', 'DM', 0, 0),
(3, 2, 'USD', '$', 1, 0),
(4, 3, 'GBP', '£', 1, 0),
(5, 4, 'CHF', 'SFr', 1, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `webconfig`
--

CREATE TABLE IF NOT EXISTS `webconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(500) NOT NULL DEFAULT '-1',
  `which` varchar(500) NOT NULL,
  `value` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `webconfig`
--

INSERT INTO `webconfig` (`id`, `username`, `which`, `value`) VALUES
(2, 'yves', 'tableColumnsSparten', '1,2,3,4'),
(3, 'yves', 'tableColumnsWaehrungen', '3,2,4,1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `wiedervorlagen`
--

CREATE TABLE IF NOT EXISTS `wiedervorlagen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `kundeKennung` varchar(500) DEFAULT NULL,
  `type` tinyint(3) NOT NULL DEFAULT '-1',
  `pub` tinyint(1) NOT NULL DEFAULT '0',
  `beschreibung` text,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `params` varchar(1000) DEFAULT NULL,
  `erinnerung` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `date` date NOT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `wissendokumente`
--

CREATE TABLE IF NOT EXISTS `wissendokumente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `filetype` tinyint(3) NOT NULL DEFAULT '-1',
  `category` varchar(500) NOT NULL DEFAULT 'Unbekannt',
  `name` varchar(1000) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fullPath` varchar(1000) NOT NULL,
  `label` varchar(1000) DEFAULT NULL,
  `beschreibung` text,
  `checksum` varchar(255) DEFAULT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-01-31 23:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `triggerClass` varchar(500) DEFAULT NULL,
  `pubAvailable` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Daten für Tabelle `wissendokumente`
--

INSERT INTO `wissendokumente` (`id`, `creator`, `benutzerId`, `filetype`, `category`, `name`, `fileName`, `fullPath`, `label`, `beschreibung`, `checksum`, `tag`, `created`, `modified`, `triggerClass`, `pubAvailable`, `status`) VALUES
(1, -1, -1, -1, 'Allgemein', 'Maklerauftrag', 'maklerauftrag.xml', 'templates/word/maklerauftrag.xml', NULL, 'Ein "Standard" Maklerauftrag (auf Basis des Maklerauftrags vom "Arbeitskreis EU-Vermittlerrichtlinie Dokumentation") für den Kunden. Adressdaten etc. werden automatich eingefügt', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-05 11:34:54', 'de.acyrance.CRM.Dokumente.Trigger.MaklerAuftragTrigger', 0, 0),
(2, -1, -1, 0, 'Allgemein', 'Maklerauftrag mit Hinweisen (pdf)', 'Maklerauftrag_mit_Hinweisen.pdf', 'vorlagen/Maklerauftrag_mit_Hinweisen.pdf', NULL, 'Die Hinweise des Arbeitskreises Vermittlerrichtlinie Dokumentation zum Maklerauftrag als PDF.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-05 11:34:55', NULL, 0, 0),
(3, -1, -1, 0, 'Riskoanalyse', 'Kfz Risikoanalyse', 'kfz risikoanalyse.doc', 'vorlagen/kfz risikoanalyse.doc', NULL, 'Vorlage für eine Kfz Risikoanalyse beim Kunden (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:55:39', NULL, 0, 0),
(4, -1, -1, -1, 'Riskoanalyse', 'Hausrat Risikoanalyse', 'gebauede risikoanalyse.doc', 'vorlagen/gebauede risikoanalyse.doc', NULL, 'Vorlage für eine Hausrat Risikoanalyse beim Kunden (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:55:54', NULL, 0, 0),
(5, -1, -1, -1, 'Riskoanalyse', 'Gebäudeversicherung Risikoanalyse', 'gebauede risikoanalyse.doc', 'vorlagen/gebauede risikoanalyse.doc', NULL, 'Vorlage für eine Gebäudeversicherungs-Risikoanalyse beim Kunden (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:56:19', NULL, 0, 0),
(6, -1, -1, -1, 'Riskoanalyse', 'Privathaftpflicht Risikoanalyse', 'phv risikoanalyse.doc', 'vorlagen/phv risikoanalyse.doc', NULL, 'Vorlage für eine private Haftpflichtversicherungs-Risikoanalyse beim Kunden (blanko). Erstellt vom "Arbeitskreis Vermittlerrichtlinie Dokumentation".', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:56:27', NULL, 0, 0),
(7, -1, -1, -1, 'Riskoanalyse', 'Rechtsschutz Risikoanalyse', 'rechtsschutz risikoanalyse.doc', 'vorlagen/rechtsschutz risikoanalyse.doc', NULL, 'Vorlage für eine Rechtsschutz Risikoanalyse beim Kunden (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:56:34', NULL, 0, 0),
(8, -1, -1, -1, 'Riskoanalyse', 'Unfallversicherung Risikoanalyse', 'unfall risikoanalyse.doc', 'vorlagen/unfall risikoanalyse.doc', NULL, 'Vorlage für eine Unfallversicherungs- Risikoanalyse beim Kunden (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:56:41', NULL, 0, 0),
(9, -1, -1, -1, 'Riskoanalyse', 'Betr. Altersvorsorge Risikoanalyse', 'bav risikoanalyse.doc', 'vorlagen/bAV/bav risikoanalyse.doc', NULL, 'Vorlage für eine Risikoanalyse der betrieblichen Altersvorsorge (bAV) (blanko). Erstellt vom Arbeitskreis Vermittlerrichtlinie Dokumentation.', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-02 15:56:49', NULL, 0, 0),
(10, -1, -1, -1, 'Allgemein', 'Maklereinzelauftrag', 'maklereinzelauftrag.xml', 'templates/word/maklereinzelauftrag.xml', NULL, 'Ein "Standard" Maklereinzelauftrag (auf Basis des Maklereinzelauftrages vom "Arbeitskreis EU-Vermittlerrichtlinie Dokumentation") für den Kunden. Adressdaten etc. werden automatich eingefügt', NULL, 'Standard', '2010-07-31 22:00:00', '2011-06-05 11:35:03', 'de.acyrance.CRM.Dokumente.Trigger.MaklerEinzelTrigger', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
