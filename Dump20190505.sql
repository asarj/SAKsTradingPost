-- MySQL dump 10.13  Distrib 5.7.26, for macos10.14 (x86_64)
--
-- Host: mysql4.cs.stonybrook.edu    Database: snisonoff
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `Id` int(11) NOT NULL,
  `DateOpened` date DEFAULT NULL,
  `Client` int(11) NOT NULL,
  `Stock` char(20) NOT NULL,
  `NumShares` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`,`Client`,`Stock`),
  KEY `Stock_idx` (`Stock`),
  KEY `Client` (`Client`),
  CONSTRAINT `Client` FOREIGN KEY (`Client`) REFERENCES `Client` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Stock` FOREIGN KEY (`Stock`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES (222222222,'2006-10-15',222222222,'IBM',50),(333333333,'2010-10-01',999999999,'IBM',55),(444444444,'2006-10-01',444444444,'F',100),(444444444,'2006-10-01',444444444,'GM',250);
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Client` (
  `Email` char(32) DEFAULT NULL,
  `Rating` int(11) DEFAULT NULL,
  `CreditCardNumber` varchar(20) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `Id` FOREIGN KEY (`Id`) REFERENCES `Person` (`SSN`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES ('syang@cs.sunysb.edu',1,'1234567812345678',101111111),('cus@customer.org',1,'1234567890123456',110010001),('snisonoff@gmail.com',9,'1111-1111-1111-1134',122337666),('vicdu@cs.sunysb.edu',1,'5678123456781234',222222222),('jsmith@ic.sunysb.edu',1,'2345678923456789',333333333),('pml@cs.sunysb.edu',1,'6789234567892345',444444444),('snisonoff@cs.sunysb.edu',1,'1234567890123456',555555555),('b@b.com',1,'1234567890123456',777777777),('ajay.sarjoo@sunysb.edu',7,'6666666666666666',999999999);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`snisonoff`@`%`*/ /*!50003 TRIGGER `snisonoff`.`Client_CHECKIDRANGE` BEFORE INSERT ON `Client` FOR EACH ROW
BEGIN
	IF (@Id > 0 AND @Id < 1000000000)
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ConditionalPriceHistory`
--

DROP TABLE IF EXISTS `ConditionalPriceHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ConditionalPriceHistory` (
  `OrderID1` int(11) NOT NULL AUTO_INCREMENT,
  `CurrentSharePrice` decimal(4,2) DEFAULT NULL,
  `PriceType1` char(20) DEFAULT NULL,
  `StopPriceAmt` decimal(4,2) DEFAULT NULL,
  `TimeStamp` datetime NOT NULL,
  PRIMARY KEY (`OrderID1`),
  CONSTRAINT `OrderID1` FOREIGN KEY (`OrderID1`) REFERENCES `Order` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ConditionalPriceHistory`
--

LOCK TABLES `ConditionalPriceHistory` WRITE;
/*!40000 ALTER TABLE `ConditionalPriceHistory` DISABLE KEYS */;
INSERT INTO `ConditionalPriceHistory` VALUES (1,34.23,'Market',30.00,'2010-10-10 00:00:00'),(2,99.00,'TrailingStop',50.00,'2010-10-10 00:00:00');
/*!40000 ALTER TABLE `ConditionalPriceHistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`snisonoff`@`%`*/ /*!50003 TRIGGER `snisonoff`.`ConditionalPriceHistory_CHECKTYPE` BEFORE INSERT ON `ConditionalPriceHistory` FOR EACH ROW
BEGIN
	IF(@PriceType1 <> 'Market' OR @PriceType1 <> 'MarketOnClose' OR @PriceType1 <> 'TrailingStop' OR @PriceType1 <> 'HiddenStop')
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data - bad price type';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employee` (
  `Id` int(11) NOT NULL,
  `SSN` int(11) DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `HourlyRate` decimal(4,2) DEFAULT NULL,
  `Position` varchar(45) DEFAULT 'Representative',
  PRIMARY KEY (`Id`),
  KEY `SSN` (`SSN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (111223333,111223333,'2019-03-15',10.00,'Representative'),(111666666,111666666,'2019-03-05',12.00,'Representative'),(123456789,123456789,'2005-11-01',60.00,'Representative'),(789123456,789123456,'2006-02-02',50.00,'Manager'),(987654321,987654321,'2019-04-29',90.00,'Manager'),(999999999,999999999,'2019-04-20',99.00,'Manager');
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`snisonoff`@`%`*/ /*!50003 TRIGGER `snisonoff`.`Employee_CHECKIDRANGE` BEFORE INSERT ON `Employee` FOR EACH ROW
BEGIN
	IF (@Id > 0 AND @Id < 1000000000)
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `Zipcode` int(11) NOT NULL,
  `City` char(20) NOT NULL,
  `State` char(20) NOT NULL,
  PRIMARY KEY (`Zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES (11111,'Customer City','NY'),(11734,'Stony Brook','NY'),(11790,'Stony Brook','NY'),(11794,'Stony Brook','NY'),(12312,'One City','NY'),(12345,'BBB City','BB'),(93536,'Los Angeles','CA');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Login`
--

DROP TABLE IF EXISTS `Login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Login` (
  `Email` char(32) NOT NULL,
  `Password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Email`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Login`
--

LOCK TABLES `Login` WRITE;
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT INTO `Login` VALUES ('ajay.sarjoo@cs.sunysb.edu','password123210'),('asarjoo@cs.sunysb.edu','password12321'),('b@b.com','password'),('cus@customer.org','password'),('dsmith@cs.sunysb.edu','pass'),('dwarren@cs.sunysb.edu','password1232'),('jsmith@ic.sunysb.edu','password1'),('one@twothree.org','password'),('pml@cs.sunysb.edu','password12'),('snisonoff@cs.sunysb.edu','password123'),('snisonoff@gmail.com','1234'),('vicdu@cs.sunysb.edu','password');
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Order` (
  `NumShares` int(11) DEFAULT NULL,
  `StockName` char(20) NOT NULL,
  `PricePerShare` decimal(4,2) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `DateTime` datetime DEFAULT NULL,
  `Percentage` decimal(4,2) DEFAULT NULL,
  `PriceType` char(20) DEFAULT NULL,
  `OrderType` char(5) DEFAULT NULL,
  `Client` int(11) NOT NULL,
  `Employee` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `StockName` (`StockName`),
  KEY `Client_idx` (`Client`),
  KEY `Employee_idx` (`Employee`),
  CONSTRAINT `Employee` FOREIGN KEY (`Employee`) REFERENCES `Employee` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `StockName` FOREIGN KEY (`StockName`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (75,'GM',34.23,1,'2010-10-10 00:00:00',0.00,'Market','Buy',444444444,123456789),(10,'IBM',91.41,2,'2010-11-10 00:00:00',0.10,'TrailingStop','Sell',222222222,123456789),(30,'IBM',50.00,3,'2010-12-10 00:00:00',0.20,'HiddenStop','Sell',333333333,123456789),(20,'F',12.00,4,'2010-01-01 00:00:00',0.60,'MarketOnClose','Sell',777777777,123456789),(5,'IBM',10.00,5,'2010-10-10 00:00:00',0.30,'Market','Buy',987654321,123456789),(20,'GM',38.00,245,'2019-05-06 00:00:00',0.00,'MarketOnClose','Sell',777777777,123456789),(11,'IBM',91.41,2169,'2019-05-06 00:00:00',3.00,'TrailingStop','Buy',0,123456789),(11,'IBM',91.41,3163,'2019-05-06 00:00:00',0.00,'Market','Buy',777777777,123456789),(3,'F',12.30,8917,'2019-05-06 00:00:00',0.30,'TrailingStop','Sell',777777777,123456789);
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`snisonoff`@`%`*/ /*!50003 TRIGGER `snisonoff`.`Order_CHECKTYPES` BEFORE INSERT ON `Order` FOR EACH ROW
BEGIN
	IF(@OrderType <> 'Buy' OR @OrderType <> 'Sell')
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data - bad order type';
	END IF;
    
	IF(@PriceType <> 'Market' OR @PriceType <> 'MarketOnClose' OR @PriceType <> 'TrailingStop' OR @PriceType <> 'HiddenStop')
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data - bad price type';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `SSN` int(11) NOT NULL,
  `LastName` char(20) NOT NULL,
  `FirstName` char(20) NOT NULL,
  `Address` char(20) DEFAULT NULL,
  `Zipcode` int(11) DEFAULT NULL,
  `Telephone` varchar(10) DEFAULT NULL,
  `Email` char(32) DEFAULT NULL,
  PRIMARY KEY (`SSN`),
  KEY `Zipcode` (`Zipcode`),
  CONSTRAINT `Zipcode` FOREIGN KEY (`Zipcode`) REFERENCES `Location` (`Zipcode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;
INSERT INTO `Person` VALUES (101111111,'Yang','Shang','123 Success Street',11790,'5166328959','syang@cs.sunysb.edu'),(110010001,'Customer','Cus','1 Customer Pl',11111,'5551234567','cus@customer.org'),(111223333,'TwoThree','One','1 23 Pl',12312,'1112223333','one@twothree.org'),(111666666,'Nisonoff','Spencer','100 Nicolls Road',11794,'111111111','snisonoff@gmail.com'),(122337666,'Nisonoff','Spencer','100 Nicolls Road',11794,'1111111','snisonoff@gmail.com'),(123456789,'Smith','David','123 College road',11790,'5162152345','dsmith@cs.sunysb.edu'),(222222222,'Du','Victor','456 Fortune Road',11790,'5166324360','vicdu@cs.sunysb.edu'),(333333333,'Smith','John','789 Peace Blvd.',93536,'3154434321','jsmith@ic.sunysb.edu'),(444444444,'Philip','Lewis','135 Knowledge Lane',11794,'5166668888','pml@cs.sunysb.edu'),(555555555,'Nisonoff','Spencer','New CS',11790,'1234567890','snisonoff@cs.sunysb.edu'),(777777777,'BB','B','BB 2nd Pl',12345,'0000000000','b@b.com'),(789123456,'Warren','David','456 Sunken Street',11794,'6316329987','dwarren@cs.sunysb.edu'),(987654321,'Sarjoo','Ajay','New CS 120',11790,'6666666666','asarjoo@cs.sunysb.edu'),(999999999,'Sarjoo','Ajay','100 Nicolls Road',11794,'1234567890','ajay.sarjoo@cs.sunysb.edu');
/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Portfolio`
--

DROP TABLE IF EXISTS `Portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Portfolio` (
  `AcctNum` int(11) NOT NULL,
  `StockSym` char(20) NOT NULL,
  `NumShares` varchar(45) DEFAULT NULL,
  `StopPrice` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`AcctNum`),
  KEY `StockSym_idx` (`StockSym`),
  CONSTRAINT `AcctNum` FOREIGN KEY (`AcctNum`) REFERENCES `Account` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `StockSym` FOREIGN KEY (`StockSym`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Portfolio`
--

LOCK TABLES `Portfolio` WRITE;
/*!40000 ALTER TABLE `Portfolio` DISABLE KEYS */;
INSERT INTO `Portfolio` VALUES (222222222,'IBM','10',7.00);
/*!40000 ALTER TABLE `Portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stock`
--

DROP TABLE IF EXISTS `Stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stock` (
  `StockSymbol` char(20) NOT NULL,
  `CompanyName` char(20) NOT NULL,
  `Type` char(20) NOT NULL,
  `PricePerShare` decimal(4,2) DEFAULT NULL,
  `NumShares` int(11) DEFAULT '0',
  PRIMARY KEY (`StockSymbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stock`
--

LOCK TABLES `Stock` WRITE;
/*!40000 ALTER TABLE `Stock` DISABLE KEYS */;
INSERT INTO `Stock` VALUES ('F','Ford','automotive',2.00,750),('GM','General Motors','automotive',33.33,1000),('IBM','IBM','automobile',30.00,500);
/*!40000 ALTER TABLE `Stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StockPriceHistory`
--

DROP TABLE IF EXISTS `StockPriceHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StockPriceHistory` (
  `StockSymbol1` char(20) NOT NULL,
  `SharePrice` decimal(4,2) DEFAULT NULL,
  `Timestamp` datetime NOT NULL,
  PRIMARY KEY (`StockSymbol1`,`Timestamp`),
  CONSTRAINT `StockSymbol1` FOREIGN KEY (`StockSymbol1`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StockPriceHistory`
--

LOCK TABLES `StockPriceHistory` WRITE;
/*!40000 ALTER TABLE `StockPriceHistory` DISABLE KEYS */;
INSERT INTO `StockPriceHistory` VALUES ('F',7.00,'2019-05-05 02:57:10'),('F',6.00,'2019-05-05 02:57:58'),('F',3.33,'2019-05-05 02:59:26'),('F',4.00,'2019-05-05 03:00:57'),('F',2.98,'2019-05-05 03:02:35'),('F',2.22,'2019-05-05 03:03:41'),('F',1.00,'2019-05-05 03:04:27'),('F',9.00,'2019-05-05 03:05:20'),('F',3.33,'2019-05-05 03:07:03'),('F',50.00,'2019-05-05 03:08:38'),('F',7.00,'2019-05-05 03:16:09'),('F',3.33,'2019-05-05 03:36:50'),('F',2.00,'2019-05-05 11:36:12'),('GM',34.23,'2008-00-00 00:00:00'),('GM',39.00,'2019-05-04 09:44:05'),('GM',32.00,'2019-05-04 15:32:47'),('GM',33.33,'2019-05-04 15:38:35'),('IBM',29.89,'2019-05-05 02:33:13'),('IBM',28.89,'2019-05-05 02:34:42'),('IBM',29.40,'2019-05-05 02:35:35'),('IBM',28.00,'2019-05-05 02:37:01'),('IBM',33.33,'2019-05-05 02:39:02'),('IBM',29.89,'2019-05-05 02:46:25'),('IBM',29.40,'2019-05-05 02:49:00'),('IBM',29.89,'2019-05-05 02:50:18'),('IBM',29.89,'2019-05-05 02:51:52'),('IBM',29.89,'2019-05-05 02:53:30'),('IBM',3.33,'2019-05-05 03:10:52'),('IBM',3.33,'2019-05-05 03:11:44'),('IBM',80.00,'2019-05-05 03:12:08'),('IBM',29.40,'2019-05-05 03:13:40'),('IBM',1.00,'2019-05-05 03:16:25'),('IBM',90.00,'2019-05-05 03:37:30'),('IBM',3.00,'2019-05-05 03:37:37'),('IBM',2.00,'2019-05-05 03:41:30'),('IBM',70.00,'2019-05-05 11:34:23'),('IBM',30.00,'2019-05-05 11:35:23');
/*!40000 ALTER TABLE `StockPriceHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trade`
--

DROP TABLE IF EXISTS `Trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Trade` (
  `AccountId` int(11) NOT NULL,
  `BrokerId` int(11) NOT NULL,
  `TransactionId` int(11) NOT NULL,
  `OrderId` int(11) NOT NULL,
  `StockId` char(20) NOT NULL,
  PRIMARY KEY (`BrokerId`,`AccountId`,`TransactionId`,`OrderId`,`StockId`),
  KEY `BrokerId_idx` (`BrokerId`),
  KEY `TransactionId_idx` (`TransactionId`),
  KEY `OrderId_idx` (`OrderId`),
  KEY `StockId_idx` (`StockId`),
  KEY `AccountId_idx` (`AccountId`),
  CONSTRAINT `AccountId` FOREIGN KEY (`AccountId`) REFERENCES `Client` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `BrokerId` FOREIGN KEY (`BrokerId`) REFERENCES `Employee` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `OrderId` FOREIGN KEY (`OrderId`) REFERENCES `Order` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `StockId` FOREIGN KEY (`StockId`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `TransactionId` FOREIGN KEY (`TransactionId`) REFERENCES `Transaction` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trade`
--

LOCK TABLES `Trade` WRITE;
/*!40000 ALTER TABLE `Trade` DISABLE KEYS */;
INSERT INTO `Trade` VALUES (110010001,123456789,5963,3163,'IBM'),(122337666,123456789,8099,245,'GM'),(222222222,123456789,626,2,'IBM'),(222222222,123456789,2601,2,'IBM'),(222222222,123456789,9417,2,'IBM'),(222222222,123456789,211111,1,'GM'),(333333333,123456789,1196,3,'IBM'),(333333333,123456789,1283,3,'IBM'),(333333333,123456789,1413,3,'IBM'),(333333333,123456789,1545,3,'IBM'),(333333333,123456789,1607,3,'IBM'),(333333333,123456789,1635,3,'IBM'),(333333333,123456789,1990,3,'IBM'),(333333333,123456789,3275,3,'IBM'),(333333333,123456789,4444,5,'IBM'),(333333333,123456789,9557,3,'IBM'),(777777777,123456789,1645,8917,'F');
/*!40000 ALTER TABLE `Trade` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`snisonoff`@`%`*/ /*!50003 TRIGGER `snisonoff`.`Trade_CHECKBROKERID` BEFORE INSERT ON `Trade` FOR EACH ROW
BEGIN
	IF (@BrokerId > 0 AND @BrokerId < 1000000000)
    THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'invalid data - bad broker id';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction` (
  `Id` int(11) NOT NULL,
  `Fee` decimal(4,2) DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `PricePerShare` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
INSERT INTO `Transaction` VALUES (0,0.00,'2019-05-05 02:57:10',0.00),(626,4.57,'2019-05-05 11:35:23',91.41),(1196,2.50,'2019-05-05 03:11:44',50.00),(1283,2.50,'2019-05-05 11:35:23',50.00),(1413,2.50,'2019-05-05 03:37:30',50.00),(1545,2.50,'2019-05-05 03:37:37',50.00),(1607,2.50,'2019-05-05 03:10:52',50.00),(1635,2.50,'2019-05-05 02:53:30',50.00),(1645,0.62,'2019-05-05 11:36:12',12.30),(1990,2.50,'2019-05-05 03:16:25',50.00),(2580,2.50,'2019-05-05 02:51:52',50.00),(2601,4.57,'2019-05-05 11:34:23',91.41),(3275,2.50,'2019-05-05 03:12:08',50.00),(4444,4.00,'2010-10-10 00:00:00',NULL),(5963,4.57,'2019-05-06 00:00:00',91.41),(6414,2.50,'2019-05-05 02:39:02',50.00),(6727,4.57,'2019-05-05 03:41:30',91.41),(7441,2.50,'2019-05-05 02:49:00',50.00),(8099,1.90,'2019-05-06 00:00:00',38.00),(9417,4.57,'2019-05-05 03:41:30',91.41),(9557,2.50,'2019-05-05 03:13:40',50.00),(9568,2.50,'2019-05-05 02:50:18',50.00),(211111,10.00,'2010-10-10 00:00:00',34.23);
/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `corder`
--

DROP TABLE IF EXISTS `corder`;
/*!50001 DROP VIEW IF EXISTS `corder`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `corder` AS SELECT 
 1 AS `StockSymbol`,
 1 AS `StockType`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Fee`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `crevenue`
--

DROP TABLE IF EXISTS `crevenue`;
/*!50001 DROP VIEW IF EXISTS `crevenue`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `crevenue` AS SELECT 
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Revenue`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `crorder`
--

DROP TABLE IF EXISTS `crorder`;
/*!50001 DROP VIEW IF EXISTS `crorder`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `crorder` AS SELECT 
 1 AS `StockSymbol`,
 1 AS `StockType`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `EmployeeId`,
 1 AS `Fee`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `crrevenue`
--

DROP TABLE IF EXISTS `crrevenue`;
/*!50001 DROP VIEW IF EXISTS `crrevenue`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `crrevenue` AS SELECT 
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Revenue`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `employeename`
--

DROP TABLE IF EXISTS `employeename`;
/*!50001 DROP VIEW IF EXISTS `employeename`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `employeename` AS SELECT 
 1 AS `Id`,
 1 AS `SSN`,
 1 AS `LastName`,
 1 AS `FirstName`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `highestrevenue`
--

DROP TABLE IF EXISTS `highestrevenue`;
/*!50001 DROP VIEW IF EXISTS `highestrevenue`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `highestrevenue` AS SELECT 
 1 AS `MaxRevenue`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `mostactivestock`
--

DROP TABLE IF EXISTS `mostactivestock`;
/*!50001 DROP VIEW IF EXISTS `mostactivestock`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `mostactivestock` AS SELECT 
 1 AS `StockSymbol`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `name`
--

DROP TABLE IF EXISTS `name`;
/*!50001 DROP VIEW IF EXISTS `name`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `name` AS SELECT 
 1 AS `Id`,
 1 AS `AccountId`,
 1 AS `SSN`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `NumShares`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `selectedorders`
--

DROP TABLE IF EXISTS `selectedorders`;
/*!50001 DROP VIEW IF EXISTS `selectedorders`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `selectedorders` AS SELECT 
 1 AS `SSN`,
 1 AS `LastName`,
 1 AS `FirstName`,
 1 AS `Id`,
 1 AS `AccountId`,
 1 AS `StockName`,
 1 AS `DateTime`,
 1 AS `PricePerShare`,
 1 AS `Fee`,
 1 AS `OrderType`,
 1 AS `CompanyName`,
 1 AS `StockType`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `stocktraded`
--

DROP TABLE IF EXISTS `stocktraded`;
/*!50001 DROP VIEW IF EXISTS `stocktraded`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `stocktraded` AS SELECT 
 1 AS `StockSymbol`,
 1 AS `StockName`,
 1 AS `StockType`,
 1 AS `TransactionId`,
 1 AS `PricePerShare`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `stocktradedd`
--

DROP TABLE IF EXISTS `stocktradedd`;
/*!50001 DROP VIEW IF EXISTS `stocktradedd`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `stocktradedd` AS SELECT 
 1 AS `StockSymbol`,
 1 AS `TransactionId`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `tradedtimes`
--

DROP TABLE IF EXISTS `tradedtimes`;
/*!50001 DROP VIEW IF EXISTS `tradedtimes`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `tradedtimes` AS SELECT 
 1 AS `StockSymbol`,
 1 AS `StockName`,
 1 AS `StockType`,
 1 AS `Times`,
 1 AS `PricePerShare`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'snisonoff'
--

--
-- Dumping routines for database 'snisonoff'
--

--
-- Final view structure for view `corder`
--

/*!50001 DROP VIEW IF EXISTS `corder`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `corder` AS select `S`.`StockSymbol` AS `StockSymbol`,`S`.`Type` AS `StockType`,`P`.`LastName` AS `LastName`,`P`.`FirstName` AS `FirstName`,`T`.`Fee` AS `Fee` from ((((`trade` `Tr` join `stock` `S`) join `transaction` `T`) join `client` `N`) join `person` `P`) where ((`Tr`.`StockId` = `S`.`StockSymbol`) and (`T`.`Id` = `Tr`.`TransactionId`) and (`N`.`Id` = `Tr`.`AccountId`) and (`N`.`Id` = `P`.`SSN`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `crevenue`
--

/*!50001 DROP VIEW IF EXISTS `crevenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `crevenue` AS select `corder`.`LastName` AS `LastName`,`corder`.`FirstName` AS `FirstName`,sum(`corder`.`Fee`) AS `Revenue` from `corder` group by `corder`.`LastName`,`corder`.`FirstName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `crorder`
--

/*!50001 DROP VIEW IF EXISTS `crorder`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `crorder` AS select `S`.`StockSymbol` AS `StockSymbol`,`S`.`Type` AS `StockType`,`n`.`LastName` AS `LastName`,`n`.`FirstName` AS `FirstName`,`n`.`Id` AS `EmployeeId`,`T`.`Fee` AS `Fee` from (((`trade` `Tr` join `stock` `S`) join `transaction` `T`) join `employeename` `N`) where ((`Tr`.`StockId` = `S`.`StockSymbol`) and (`T`.`Id` = `Tr`.`TransactionId`) and (`n`.`Id` = `Tr`.`BrokerId`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `crrevenue`
--

/*!50001 DROP VIEW IF EXISTS `crrevenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `crrevenue` AS select `crorder`.`LastName` AS `LastName`,`crorder`.`FirstName` AS `FirstName`,sum(`crorder`.`Fee`) AS `Revenue` from `crorder` group by `crorder`.`LastName`,`crorder`.`FirstName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employeename`
--

/*!50001 DROP VIEW IF EXISTS `employeename`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `employeename` AS select `E`.`Id` AS `Id`,`P`.`SSN` AS `SSN`,`P`.`LastName` AS `LastName`,`P`.`FirstName` AS `FirstName` from (`employee` `E` join `person` `P`) where (`E`.`SSN` = `P`.`SSN`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `highestrevenue`
--

/*!50001 DROP VIEW IF EXISTS `highestrevenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `highestrevenue` AS select max(`crevenue`.`Revenue`) AS `MaxRevenue` from `crevenue` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `mostactivestock`
--

/*!50001 DROP VIEW IF EXISTS `mostactivestock`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `mostactivestock` AS select `tradedtimes`.`StockSymbol` AS `StockSymbol` from `tradedtimes` where (`tradedtimes`.`Times` >= (select max(`tradedtimes`.`Times`) from `tradedtimes`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `name`
--

/*!50001 DROP VIEW IF EXISTS `name`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `name` AS select `C`.`Id` AS `Id`,`A`.`Id` AS `AccountId`,`P`.`SSN` AS `SSN`,`P`.`LastName` AS `LastName`,`P`.`FirstName` AS `FirstName`,`A`.`NumShares` AS `NumShares` from ((`account` `A` join `client` `C`) join `person` `P`) where ((`A`.`Client` = `C`.`Id`) and (`C`.`Id` = `P`.`SSN`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `selectedorders`
--

/*!50001 DROP VIEW IF EXISTS `selectedorders`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `selectedorders` AS select `P`.`SSN` AS `SSN`,`P`.`LastName` AS `LastName`,`P`.`FirstName` AS `FirstName`,`C`.`Id` AS `Id`,`A`.`Id` AS `AccountId`,`O`.`StockName` AS `StockName`,`T`.`DateTime` AS `DateTime`,`T`.`PricePerShare` AS `PricePerShare`,`T`.`Fee` AS `Fee`,`O`.`OrderType` AS `OrderType`,`S`.`CompanyName` AS `CompanyName`,`S`.`Type` AS `StockType` from ((((((`person` `P` join `client` `C`) join `account` `A`) join `order` `O`) join `transaction` `T`) join `stock` `S`) join `trade` `Tr`) where ((`P`.`SSN` = 222222222) and (`P`.`SSN` = `C`.`Id`) and (`C`.`Id` = `A`.`Client`) and (`Tr`.`AccountId` = `A`.`Id`) and (`Tr`.`TransactionId` = `T`.`Id`) and (`Tr`.`OrderId` = `O`.`Id`) and (`O`.`StockName` = `S`.`StockSymbol`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stocktraded`
--

/*!50001 DROP VIEW IF EXISTS `stocktraded`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `stocktraded` AS select `S`.`StockSymbol` AS `StockSymbol`,`S`.`CompanyName` AS `StockName`,`S`.`Type` AS `StockType`,`Tr`.`TransactionId` AS `TransactionId`,`S`.`PricePerShare` AS `PricePerShare` from ((`order` `O` join `trade` `Tr`) join `stock` `S`) where ((`O`.`StockName` = `S`.`StockSymbol`) and (`O`.`Id` = `Tr`.`OrderId`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stocktradedd`
--

/*!50001 DROP VIEW IF EXISTS `stocktradedd`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `stocktradedd` AS select `S`.`StockSymbol` AS `StockSymbol`,`Tr`.`TransactionId` AS `TransactionId` from ((`order` `O` join `trade` `Tr`) join `stock` `S`) where ((`O`.`StockName` = `S`.`StockSymbol`) and (`O`.`Id` = `Tr`.`OrderId`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `tradedtimes`
--

/*!50001 DROP VIEW IF EXISTS `tradedtimes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`snisonoff`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `tradedtimes` AS select `stocktraded`.`StockSymbol` AS `StockSymbol`,`stocktraded`.`StockName` AS `StockName`,`stocktraded`.`StockType` AS `StockType`,count(`stocktraded`.`StockSymbol`) AS `Times`,`stocktraded`.`PricePerShare` AS `PricePerShare` from `stocktraded` group by `stocktraded`.`StockSymbol` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-05 23:54:20
