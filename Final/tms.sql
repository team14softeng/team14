-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 10, 2013 at 03:23 PM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tms`
--

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

CREATE TABLE IF NOT EXISTS `announcement` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL,
  `body` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `routeName` varchar(40) NOT NULL,
  `totalNumberOfSeats` mediumint(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`id`, `routeName`, `totalNumberOfSeats`) VALUES
(1, 'Athens-Thessaloniki', 43),
(2, 'Samos-Athens', 20);

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE IF NOT EXISTS `tickets` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `routeName` varchar(40) NOT NULL,
  `date` varchar(40) NOT NULL,
  `time` varchar(40) NOT NULL,
  `seat` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`id`, `routeName`, `date`, `time`, `seat`) VALUES
(1, 'Athens-Thessaloniki', '10/1/2013', '1:38', 1),
(5, 'Athens-Thessaloniki', '10/1/2013', '1:47', 2),
(6, 'Athens-Thessaloniki', '10/1/2013', '3:19', 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `telephone` int(11) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT '0',
  `isCashier` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `username`, `password`, `address`, `email`, `telephone`, `isAdmin`, `isCashier`) VALUES
(1, 'Παναγιώτης', 'Τσουτσουλιανούδης', 'pete', 'petelian', 'Πατησίων 279-281', 'petelian@gmail.com', 2102233444, 1, 0),
(2, 'Κωσταντίνος', 'Φουντουκίδης', 'kostas', 'kostasfou', 'Πάτμου 11', 'kostasfou@gmail.com', 2103344555, 0, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
