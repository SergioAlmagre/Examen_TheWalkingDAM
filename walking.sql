-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 18-06-2012 a las 23:47:31
-- Versión del servidor: 5.5.16
-- Versión de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `walking`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `normales`
--

CREATE TABLE IF NOT EXISTS `normales` (
  `ID_Zombie` varchar(10) NOT NULL,
  PRIMARY KEY (`ID_Zombie`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `normales`
--

INSERT INTO `normales` (`ID_Zombie`) VALUES
('17'),
('18'),
('19'),
('20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personajes`
--

CREATE TABLE IF NOT EXISTS `personajes` (
  `ID_Personaje` varchar(10) NOT NULL,
  `Vida` int(11) NOT NULL,
  `Municion` int(11) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_Personaje`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personajes`
--

INSERT INTO `personajes` (`ID_Personaje`, `Vida`, `Municion`, `Nombre`) VALUES
('1', 68, 12, 'Rick'),
('2', 90, 20, 'Lori'),
('3', 86, 12, 'Shane'),
('4', 72, 10, 'Andrea'),
('5', 36, 9, 'Glenn'),
('6', 29, 18, 'Dale'),
('7', 85, 14, 'Daryl'),
('8', 100, 0, 'Sophie'),
('9', 90, 27, 'Chubaca');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `podridos`
--

CREATE TABLE IF NOT EXISTS `podridos` (
  `ID_Zombie` varchar(10) NOT NULL,
  `Movilidad` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID_Zombie`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `podridos`
--

INSERT INTO `podridos` (`ID_Zombie`, `Movilidad`) VALUES
('1', 1),
('2', 1),
('3', 0),
('4', 1),
('5', 0),
('6', 0),
('7', 1),
('8', 1),
('9', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pupas`
--

CREATE TABLE IF NOT EXISTS `pupas` (
  `ID_Zombie` varchar(10) NOT NULL,
  `Completos` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID_Zombie`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pupas`
--

INSERT INTO `pupas` (`ID_Zombie`, `Completos`) VALUES
('10', 1),
('11', 0),
('12', 0),
('13', 1),
('14', 1),
('15', 0),
('16', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zombies`
--

CREATE TABLE IF NOT EXISTS `zombies` (
  `ID_Zombie` varchar(10) NOT NULL,
  `Velocidad` int(11) NOT NULL,
  `Capacidad` int(11) NOT NULL,
  `Tiempo` int(11) NOT NULL,
  PRIMARY KEY (`ID_Zombie`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `zombies`
--

INSERT INTO `zombies` (`ID_Zombie`, `Velocidad`, `Capacidad`, `Tiempo`) VALUES
('1', 2, 78, 22),
('10', 3, 24, 27),
('11', 5, 32, 27),
('12', 1, 56, 12),
('13', 4, 65, 18),
('14', 1, 78, 2),
('15', 3, 90, 4),
('16', 4, 34, 9),
('17', 5, 21, 6),
('18', 2, 36, 7),
('19', 2, 76, 8),
('2', 5, 90, 27),
('20', 4, 34, 12),
('3', 1, 10, 20),
('4', 3, 67, 30),
('5', 4, 45, 34),
('6', 1, 56, 23),
('7', 2, 23, 21),
('8', 5, 67, 21),
('9', 2, 78, 33);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
