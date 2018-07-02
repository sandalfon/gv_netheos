-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 02 juil. 2018 à 17:28
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `netheosfaq`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`id`, `password`, `role`, `username`) VALUES
(1, '$2a$10$ahOIFBWTx/OcY3fbG9eNyewizplfx8wp9SEjLkcy5Y9yy9b8cdk5C', 'ADMIN', 'netheos'),
(2, '$2a$10$XIxDOUAvypy9phfAXIk8jO7QmHfn22bHV4Fgp7ECxlftcPFdAvx7C', 'USER', 'netheosUser');

-- --------------------------------------------------------

--
-- Structure de la table `faq`
--

DROP TABLE IF EXISTS `faq`;
CREATE TABLE IF NOT EXISTS `faq` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `answer` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `faq`
--

INSERT INTO `faq` (`id`, `question`, `answer`) VALUES
(6, 'q1 is?', 'a1 and a lot of text'),
(7, 'q3 is?', 'a3 and a lot of text'),
(8, 'q2 is?', 'a2 and a lot of text');

-- --------------------------------------------------------

--
-- Structure de la table `faq_tag`
--

DROP TABLE IF EXISTS `faq_tag`;
CREATE TABLE IF NOT EXISTS `faq_tag` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `faq_id` bigint(11) NOT NULL,
  `tag_id` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `faq_id` (`faq_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `faq_tag`
--

INSERT INTO `faq_tag` (`id`, `faq_id`, `tag_id`) VALUES
(1, 6, 1),
(2, 6, 2),
(3, 6, 3),
(4, 7, 3),
(5, 7, 4),
(6, 7, 5),
(7, 8, 3),
(8, 8, 1),
(9, 8, 5);

-- --------------------------------------------------------

--
-- Structure de la table `tag`
--

DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tag`
--

INSERT INTO `tag` (`id`, `content`) VALUES
(1, 'java'),
(2, 'python'),
(3, 'other'),
(4, 'Ruby'),
(5, 'perl');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `faq_tag`
--
ALTER TABLE `faq_tag`
  ADD CONSTRAINT `FK5sw6pw9vai736ny14g9rxgit2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
  ADD CONSTRAINT `FKidkjbdkg4wd96lkj811cvjges` FOREIGN KEY (`faq_id`) REFERENCES `faq` (`id`),
  ADD CONSTRAINT `faq_tag_ibfk_1` FOREIGN KEY (`faq_id`) REFERENCES `faq` (`id`),
  ADD CONSTRAINT `faq_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
