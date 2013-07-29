-- phpMyAdmin SQL Dump
-- version 3.3.7deb2build0.10.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 23, 2010 at 08:46 PM
-- Server version: 5.1.49
-- PHP Version: 5.3.3-1ubuntu9.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `acyrancecrm`
--

-- --------------------------------------------------------

--
-- Table structure for table `aufgaben`
--

CREATE TABLE IF NOT EXISTS `aufgaben` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `besitzerId` int(11) NOT NULL,
  `pub` tinyint(1) NOT NULL DEFAULT '0',
  `beschreibung` varchar(255) DEFAULT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `kundeKennung` varchar(500) NOT NULL DEFAULT '-1',
  `start` varchar(255) NOT NULL DEFAULT '1971-02-01 00:00:00',
  `ende` varchar(255) NOT NULL DEFAULT '1971-02-01 00:00:00',
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `backup`
--

CREATE TABLE IF NOT EXISTS `backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(500) NOT NULL,
  `type` tinyint(2) NOT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `automatic` tinyint(1) NOT NULL DEFAULT '1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `success` tinyint(1) NOT NULL DEFAULT '0',
  `fileAvailable` tinyint(1) NOT NULL DEFAULT '0',
  `backupSize` bigint(20) NOT NULL DEFAULT '-1',
  `filetype` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `benutzer`
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastlogin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `logincount` int(11) NOT NULL DEFAULT '1',
  `webenabled` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `beratungsprotokolle`
--

CREATE TABLE IF NOT EXISTS `beratungsprotokolle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kundenKennung` varchar(500) DEFAULT NULL,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `versicherungsId` int(11) NOT NULL DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dokumente`
--

CREATE TABLE IF NOT EXISTS `dokumente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `kundenKennung` varchar(255) DEFAULT NULL,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `filetype` tinyint(3) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `fullPath` varchar(1000) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `beschreibung` varchar(255) DEFAULT NULL,
  `checksum` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastviewed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `viewcount` int(11) NOT NULL DEFAULT '1',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `firmenkunden`
--

CREATE TABLE IF NOT EXISTS `firmenkunden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `kontonummer` varchar(500) DEFAULT NULL,
  `bankleitzahl` varchar(500) DEFAULT NULL,
  `bankname` varchar(500) DEFAULT NULL,
  `bankeigentuemer` varchar(500) DEFAULT NULL,
  `kontoiban` varchar(500) DEFAULT NULL,
  `kontobic` varchar(500) DEFAULT NULL,
  `werber` varchar(500) DEFAULT NULL,
  `comments` text,
  `custom1` varchar(500) DEFAULT NULL,
  `custom2` varchar(500) DEFAULT NULL,
  `custom3` varchar(500) DEFAULT NULL,
  `custom4` varchar(500) DEFAULT NULL,
  `custom5` varchar(500) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `firmen_ansprechpartner`
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
  `geburtdatum` date DEFAULT NULL,
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kinder`
--

CREATE TABLE IF NOT EXISTS `kinder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `kindName` varchar(255) DEFAULT NULL,
  `kindVorname` varchar(255) DEFAULT NULL,
  `kindGeburtsdatum` varchar(255) DEFAULT NULL,
  `kindBeruf` varchar(255) DEFAULT NULL,
  `kindWohnort` varchar(255) DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kunden`
--

CREATE TABLE IF NOT EXISTS `kunden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `betreuer` varchar(255) DEFAULT NULL,
  `besitzer` int(11) DEFAULT NULL,
  `kundenNr` varchar(255) NOT NULL,
  `anrede` varchar(255) DEFAULT NULL,
  `titel` varchar(255) DEFAULT NULL,
  `firma` varchar(255) DEFAULT NULL,
  `vorname` varchar(255) DEFAULT NULL,
  `vorname2` varchar(255) DEFAULT NULL,
  `vornameWeitere` varchar(255) DEFAULT NULL,
  `nachname` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `plz` varchar(50) DEFAULT NULL,
  `stadt` varchar(255) DEFAULT NULL,
  `bundesland` varchar(255) DEFAULT NULL,
  `land` varchar(255) DEFAULT NULL,
  `adresseZusatz` varchar(500) DEFAULT NULL,
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
  `beruf` varchar(255) DEFAULT NULL,
  `berufsTyp` varchar(255) DEFAULT NULL,
  `berufsOptionen` varchar(255) DEFAULT NULL,
  `beamter` tinyint(1) DEFAULT NULL,
  `oeffentlicherDienst` tinyint(1) DEFAULT NULL,
  `einkommen` varchar(255) DEFAULT NULL,
  `einkommenNetto` varchar(255) DEFAULT NULL,
  `steuerklasse` varchar(255) DEFAULT NULL,
  `kinderZahl` int(11) DEFAULT NULL,
  `religion` varchar(255) DEFAULT NULL,
  `weiterePersonen` varchar(255) DEFAULT NULL,
  `weiterePersonenInfo` varchar(255) DEFAULT NULL,
  `familienPlanung` varchar(255) DEFAULT NULL,
  `werberKennung` varchar(255) DEFAULT NULL,
  `kontonummer` varchar(255) DEFAULT NULL,
  `bankleitzahl` varchar(255) DEFAULT NULL,
  `bankname` varchar(255) DEFAULT NULL,
  `bankeigentuemer` varchar(255) DEFAULT NULL,
  `kontoiban` varchar(255) DEFAULT NULL,
  `kontobic` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `custom1` varchar(255) DEFAULT NULL,
  `custom2` varchar(255) DEFAULT NULL,
  `custom3` varchar(255) DEFAULT NULL,
  `custom4` varchar(255) DEFAULT NULL,
  `custom5` varchar(255) DEFAULT NULL,
  `geburtsname` varchar(500) DEFAULT NULL,
  `ehedatum` varchar(100) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kunden_betreuung`
--

CREATE TABLE IF NOT EXISTS `kunden_betreuung` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kundenKennung` varchar(500) NOT NULL,
  `kundenTyp` varchar(500) DEFAULT NULL,
  `prioritaet` tinyint(3) DEFAULT '0',
  `loyalitaet` varchar(500) DEFAULT NULL,
  `zielgruppe` varchar(500) DEFAULT NULL,
  `ersterKontakt` date DEFAULT NULL,
  `letzerKontakt` date DEFAULT NULL,
  `letzteRoutine` date DEFAULT NULL,
  `maklerVertrag` tinyint(1) DEFAULT '0',
  `maklerBeginn` date DEFAULT NULL,
  `maklerEnde` date DEFAULT NULL,
  `analyse` tinyint(1) DEFAULT '0',
  `analyseLetzte` date DEFAULT NULL,
  `analyseNaechste` date DEFAULT NULL,
  `erstinformationen` tinyint(1) NOT NULL DEFAULT '0',
  `verwaltungskosten` int(11) DEFAULT NULL,
  `newsletter` tinyint(1) NOT NULL DEFAULT '0',
  `kundenzeitschrift` tinyint(1) DEFAULT '0',
  `geburtstagskarte` tinyint(1) DEFAULT '0',
  `weihnachtskarte` tinyint(1) DEFAULT '0',
  `osterkarte` tinyint(1) DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kunden_zusatzadressen`
--

CREATE TABLE IF NOT EXISTS `kunden_zusatzadressen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) DEFAULT NULL,
  `kundenKennung` varchar(500) DEFAULT NULL,
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `mandanten`
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastUsed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `nachrichten`
--

CREATE TABLE IF NOT EXISTS `nachrichten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mandantenId` int(11) DEFAULT '-1',
  `benutzerId` int(11) NOT NULL,
  `betreff` varchar(255) NOT NULL,
  `context` text NOT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `newsletter`
--

CREATE TABLE IF NOT EXISTS `newsletter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `sender` varchar(1000) DEFAULT NULL,
  `senderMail` varchar(1000) DEFAULT NULL,
  `subject` varchar(1000) DEFAULT NULL,
  `text` text,
  `send` tinyint(1) NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `newsletter_sub`
--

CREATE TABLE IF NOT EXISTS `newsletter_sub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kennung` varchar(1000) DEFAULT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notizen`
--

CREATE TABLE IF NOT EXISTS `notizen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creatorId` int(11) NOT NULL DEFAULT '-1',
  `priv` tinyint(1) NOT NULL DEFAULT '0',
  `kundenKennung` varchar(500) DEFAULT NULL,
  `versichererId` int(11) DEFAULT '-1',
  `benutzerId` int(11) DEFAULT '-1',
  `produktId` int(11) NOT NULL DEFAULT '-1',
  `betreff` varchar(500) DEFAULT NULL,
  `text` text,
  `tag` varchar(500) NOT NULL DEFAULT 'Standard',
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `produkte`
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
  `versicherungsSumme` double(11,2) DEFAULT '0.00',
  `bewertungsSumme` double(11,2) DEFAULT '0.00',
  `bedingungen` varchar(500) DEFAULT NULL,
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE IF NOT EXISTS `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benutzerId` int(11) NOT NULL,
  `start` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastrefresh` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_id` varchar(255) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_id` (`session_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sparten`
--

CREATE TABLE IF NOT EXISTS `sparten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spartenNummer` varchar(100) DEFAULT NULL,
  `bezeichnung` varchar(500) DEFAULT NULL,
  `gruppe` varchar(500) DEFAULT NULL,
  `steuersatz` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `termine`
--

CREATE TABLE IF NOT EXISTS `termine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `besitzer` int(11) NOT NULL,
  `pub` tinyint(1) NOT NULL DEFAULT '0',
  `beschreibung` varchar(255) DEFAULT NULL,
  `ort` varchar(255) DEFAULT NULL,
  `tag` varchar(255) NOT NULL DEFAULT 'Standard',
  `kundeKennung` varchar(500) DEFAULT NULL,
  `versichererId` int(11) NOT NULL DEFAULT '-1',
  `teilnehmer` varchar(255) DEFAULT NULL,
  `erinnerung` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ende` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `textbausteine`
--

CREATE TABLE IF NOT EXISTS `textbausteine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grp` int(11) NOT NULL DEFAULT '-1',
  `benutzerId` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(500) NOT NULL,
  `beschreibung` text,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `textbausteine_grp`
--

CREATE TABLE IF NOT EXISTS `textbausteine_grp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `versicherer`
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vuNummer` (`vuNummer`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `versicherer_all`
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vuNummer` (`vuNummer`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `waehrungen`
--

CREATE TABLE IF NOT EXISTS `waehrungen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordering` int(11) NOT NULL DEFAULT '-1',
  `isocode` varchar(255) DEFAULT NULL,
  `bezeichnung` varchar(1000) NOT NULL,
  `neuanlage` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `webconfig`
--

CREATE TABLE IF NOT EXISTS `webconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(500) NOT NULL DEFAULT '-1',
  `which` varchar(500) NOT NULL,
  `value` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `wiedervorlagen`
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
  `erinnerung` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `date` date NOT NULL,
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `wissendokumente`
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
  `created` timestamp NOT NULL DEFAULT '1971-02-01 00:00:00',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `triggerClass` varchar(500) DEFAULT NULL,
  `pubAvailable` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;
