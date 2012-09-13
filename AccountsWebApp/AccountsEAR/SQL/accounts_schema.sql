-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.24-0ubuntu0.12.04.1 - (Ubuntu)
-- Server OS:                    debian-linux-gnu
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2012-09-13 16:58:23
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for home_accounts
DROP DATABASE IF EXISTS `home_accounts`;
CREATE DATABASE IF NOT EXISTS `home_accounts` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `home_accounts`;


-- Dumping structure for table home_accounts.accounts_user
DROP TABLE IF EXISTS `accounts_user`;
CREATE TABLE IF NOT EXISTS `accounts_user` (
  `idaccounts_user` int(11) NOT NULL AUTO_INCREMENT,
  `user_is_active` tinyint(1) NOT NULL,
  `username` varchar(45) NOT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(32) NOT NULL,
  PRIMARY KEY (`idaccounts_user`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `idaccounts_user_UNIQUE` (`idaccounts_user`),
  KEY `FK_BALANCE_SHEET` (`idaccounts_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.app_setting
DROP TABLE IF EXISTS `app_setting`;
CREATE TABLE IF NOT EXISTS `app_setting` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(50) NOT NULL DEFAULT 'key_name',
  `value` varchar(10000) NOT NULL DEFAULT 'value_details',
  `inserted` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_setting_key_name` (`key_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.balance_sheet
DROP TABLE IF EXISTS `balance_sheet`;
CREATE TABLE IF NOT EXISTS `balance_sheet` (
  `idbalance_sheet` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `sheet_name` varchar(45) NOT NULL,
  `description` longtext,
  `sheet_is_active` tinyint(4) NOT NULL DEFAULT '1',
  `inactive_reason` longtext,
  PRIMARY KEY (`idbalance_sheet`),
  UNIQUE KEY `idbalance_sheet_UNIQUE` (`idbalance_sheet`),
  UNIQUE KEY `sheet_name_UNIQUE` (`sheet_name`),
  KEY `IDX_FK_OWNER` (`owner_id`),
  CONSTRAINT `FK_OWNER` FOREIGN KEY (`owner_id`) REFERENCES `accounts_user` (`idaccounts_user`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.ccy_conversion
DROP TABLE IF EXISTS `ccy_conversion`;
CREATE TABLE IF NOT EXISTS `ccy_conversion` (
  `idccy_conversion` int(11) NOT NULL,
  `ccy1` int(11) NOT NULL,
  `ccy2` int(11) NOT NULL,
  `rate` decimal(9,4) NOT NULL,
  PRIMARY KEY (`idccy_conversion`),
  UNIQUE KEY `UNQ_CONVERSION_IDX` (`ccy1`,`ccy2`),
  KEY `IDX_FK_CCY1` (`ccy1`),
  KEY `IDX_FK_CCY2` (`ccy2`),
  CONSTRAINT `FK_CCY1` FOREIGN KEY (`ccy1`) REFERENCES `currency` (`idcurrency`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_CCY2` FOREIGN KEY (`ccy2`) REFERENCES `currency` (`idcurrency`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.credit_master
DROP TABLE IF EXISTS `credit_master`;
CREATE TABLE IF NOT EXISTS `credit_master` (
  `idcredit_master` int(11) NOT NULL AUTO_INCREMENT,
  `payment_type` int(11) NOT NULL,
  `payment_ammount` decimal(10,4) NOT NULL,
  `currency` int(11) NOT NULL,
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `balance_sheet` int(11) NOT NULL,
  PRIMARY KEY (`idcredit_master`),
  UNIQUE KEY `idcredit_master_UNIQUE` (`idcredit_master`),
  KEY `IDX_FK_CRED_BALANCE_SHEET` (`balance_sheet`),
  KEY `IDX_FK_PAYMENT_TYPE_CRED` (`payment_type`),
  KEY `IDX_FK_CCY` (`currency`),
  CONSTRAINT `FK_CCY_CRED` FOREIGN KEY (`currency`) REFERENCES `currency` (`idcurrency`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CRED_BALANCE_SHEET` FOREIGN KEY (`balance_sheet`) REFERENCES `balance_sheet` (`idbalance_sheet`) ON UPDATE NO ACTION,
  CONSTRAINT `FK_PAYMENT_TYPE_CRED` FOREIGN KEY (`payment_type`) REFERENCES `payment_type` (`idpayment_type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.currency
DROP TABLE IF EXISTS `currency`;
CREATE TABLE IF NOT EXISTS `currency` (
  `idcurrency` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(3) NOT NULL,
  PRIMARY KEY (`idcurrency`),
  UNIQUE KEY `idcurrency_UNIQUE` (`idcurrency`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.debit_master
DROP TABLE IF EXISTS `debit_master`;
CREATE TABLE IF NOT EXISTS `debit_master` (
  `iddebit_master` int(11) NOT NULL AUTO_INCREMENT,
  `payment_type` int(11) NOT NULL,
  `payment_ammount` decimal(10,4) NOT NULL,
  `currency` int(11) NOT NULL,
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `balance_sheet` int(11) NOT NULL,
  PRIMARY KEY (`iddebit_master`),
  UNIQUE KEY `iddebit_master_UNIQUE` (`iddebit_master`),
  KEY `IDX_FK_DEB_BALANCE_SHEET` (`balance_sheet`),
  KEY `IDX_FK_PAYMENT_TYPE` (`payment_type`),
  KEY `IDX_FK_CCY` (`currency`),
  CONSTRAINT `FK_CCY` FOREIGN KEY (`currency`) REFERENCES `currency` (`idcurrency`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DEB_BALANCE_SHEET` FOREIGN KEY (`balance_sheet`) REFERENCES `balance_sheet` (`idbalance_sheet`) ON UPDATE NO ACTION,
  CONSTRAINT `FK_PAYMENT_TYPE` FOREIGN KEY (`payment_type`) REFERENCES `payment_type` (`idpayment_type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.payment_category
DROP TABLE IF EXISTS `payment_category`;
CREATE TABLE IF NOT EXISTS `payment_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  `bal_sheet` int(10) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`),
  KEY `FK_Bal_Sheet` (`bal_sheet`),
  CONSTRAINT `FK_Bal_Sheet` FOREIGN KEY (`bal_sheet`) REFERENCES `balance_sheet` (`idbalance_sheet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.payment_type
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE IF NOT EXISTS `payment_type` (
  `idpayment_type` int(11) NOT NULL AUTO_INCREMENT,
  `payment_name` varchar(50) NOT NULL,
  `payment_category` int(11) NOT NULL,
  PRIMARY KEY (`idpayment_type`),
  UNIQUE KEY `name_UNIQUE` (`payment_category`,`payment_name`),
  CONSTRAINT `FK_Payment_Category` FOREIGN KEY (`payment_category`) REFERENCES `payment_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table home_accounts.user_activation
DROP TABLE IF EXISTS `user_activation`;
CREATE TABLE IF NOT EXISTS `user_activation` (
  `p_key` int(10) NOT NULL AUTO_INCREMENT,
  `userId` int(10) NOT NULL,
  `activation_string` varchar(255) NOT NULL,
  PRIMARY KEY (`p_key`),
  KEY `FK_user` (`userId`),
  CONSTRAINT `FK_user` FOREIGN KEY (`userId`) REFERENCES `accounts_user` (`idaccounts_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
