SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `home_accounts` ;
CREATE SCHEMA IF NOT EXISTS `home_accounts` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `home_accounts` ;

-- -----------------------------------------------------
-- Table `home_accounts`.`balance_sheet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`balance_sheet` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`balance_sheet` (
  `idbalance_sheet` INT NOT NULL AUTO_INCREMENT ,
  `owner_id` INT NOT NULL ,
  `sheet_name` VARCHAR(45) NOT NULL ,
  `description` LONGTEXT NULL ,
  PRIMARY KEY (`idbalance_sheet`) ,
  CONSTRAINT `FK_OWNER`
    FOREIGN KEY (`owner_id` )
    REFERENCES `home_accounts`.`accounts_user` (`idaccounts_user` )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `idbalance_sheet_UNIQUE` ON `home_accounts`.`balance_sheet` (`idbalance_sheet` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_OWNER` ON `home_accounts`.`balance_sheet` (`owner_id` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `sheet_name_UNIQUE` ON `home_accounts`.`balance_sheet` (`sheet_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`accounts_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`accounts_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`accounts_user` (
  `idaccounts_user` INT NOT NULL AUTO_INCREMENT ,
  `active` TINYINT(1) NOT NULL,
  `username` VARCHAR(45) NOT NULL ,
  `firstname` VARCHAR(45) CHARACTER SET 'utf8' NULL ,
  `lastname` VARCHAR(45) NULL ,
  `password` VARCHAR(32) NOT NULL ,
  `salt` VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`idaccounts_user`) ,
  CONSTRAINT `FK_BALANCE_SHEET`
    FOREIGN KEY (`idaccounts_user` )
    REFERENCES `home_accounts`.`balance_sheet` (`idbalance_sheet` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `username_UNIQUE` ON `home_accounts`.`accounts_user` (`username` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `idaccounts_user_UNIQUE` ON `home_accounts`.`accounts_user` (`idaccounts_user` ASC) ;

SHOW WARNINGS;
CREATE INDEX `FK_BALANCE_SHEET` ON `home_accounts`.`accounts_user` (`idaccounts_user` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`payment_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`payment_type` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`payment_type` (
  `idpayment_type` INT NOT NULL ,
  `name` VARCHAR(15) NULL ,
  PRIMARY KEY (`idpayment_type`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `name_UNIQUE` ON `home_accounts`.`payment_type` (`name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`currency` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`currency` (
  `idcurrency` INT NOT NULL AUTO_INCREMENT ,
  `name` CHAR(3) NOT NULL ,
  PRIMARY KEY (`idcurrency`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `idcurrency_UNIQUE` ON `home_accounts`.`currency` (`idcurrency` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `name_UNIQUE` ON `home_accounts`.`currency` (`name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`debit_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`debit_master` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`debit_master` (
  `iddebit_master` INT NOT NULL AUTO_INCREMENT ,
  `payment_type` INT NOT NULL ,
  `payment_ammount` DECIMAL(10,4) NOT NULL ,
  `currency` INT NOT NULL ,
  `insert_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `balance_sheet` INT NOT NULL ,
  PRIMARY KEY (`iddebit_master`) ,
  CONSTRAINT `FK_DEB_BALANCE_SHEET`
    FOREIGN KEY (`balance_sheet` )
    REFERENCES `home_accounts`.`balance_sheet` (`idbalance_sheet` )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PAYMENT_TYPE`
    FOREIGN KEY (`payment_type` )
    REFERENCES `home_accounts`.`payment_type` (`idpayment_type` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CCY`
    FOREIGN KEY (`currency` )
    REFERENCES `home_accounts`.`currency` (`idcurrency` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `iddebit_master_UNIQUE` ON `home_accounts`.`debit_master` (`iddebit_master` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_DEB_BALANCE_SHEET` ON `home_accounts`.`debit_master` (`balance_sheet` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_PAYMENT_TYPE` ON `home_accounts`.`debit_master` (`payment_type` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_CCY` ON `home_accounts`.`debit_master` (`currency` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`credit_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`credit_master` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`credit_master` (
  `idcredit_master` INT NOT NULL AUTO_INCREMENT ,
  `payment_type` INT NOT NULL ,
  `payment_ammount` DECIMAL(10,4) NOT NULL ,
  `currency` INT NOT NULL ,
  `insert_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `balance_sheet` INT NOT NULL ,
  PRIMARY KEY (`idcredit_master`) ,
  CONSTRAINT `FK_CRED_BALANCE_SHEET`
    FOREIGN KEY (`balance_sheet` )
    REFERENCES `home_accounts`.`balance_sheet` (`idbalance_sheet` )
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PAYMENT_TYPE_CRED`
    FOREIGN KEY (`payment_type` )
    REFERENCES `home_accounts`.`payment_type` (`idpayment_type` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CCY_CRED`
    FOREIGN KEY (`currency` )
    REFERENCES `home_accounts`.`currency` (`idcurrency` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `idcredit_master_UNIQUE` ON `home_accounts`.`credit_master` (`idcredit_master` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_CRED_BALANCE_SHEET` ON `home_accounts`.`credit_master` (`balance_sheet` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_PAYMENT_TYPE_CRED` ON `home_accounts`.`credit_master` (`payment_type` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_CCY` ON `home_accounts`.`credit_master` (`currency` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`ccy_conversion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `home_accounts`.`ccy_conversion` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `home_accounts`.`ccy_conversion` (
  `idccy_conversion` INT NOT NULL ,
  `ccy1` INT NOT NULL ,
  `ccy2` INT NOT NULL ,
  `rate` DECIMAL(9,4) NOT NULL ,
  PRIMARY KEY (`idccy_conversion`) ,
  CONSTRAINT `FK_CCY1`
    FOREIGN KEY (`ccy1` )
    REFERENCES `home_accounts`.`currency` (`idcurrency` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CCY2`
    FOREIGN KEY (`ccy2` )
    REFERENCES `home_accounts`.`currency` (`idcurrency` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `UNQ_CONVERSION_IDX` ON `home_accounts`.`ccy_conversion` (`ccy1` ASC, `ccy2` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_CCY1` ON `home_accounts`.`ccy_conversion` (`ccy1` ASC) ;

SHOW WARNINGS;
CREATE INDEX `IDX_FK_CCY2` ON `home_accounts`.`ccy_conversion` (`ccy2` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `home_accounts`.`app_setting`
-- -----------------------------------------------------
CREATE TABLE IF EXISTS `home_accounts`.`app_setting` (
	`id` INT(10) NULL AUTO_INCREMENT,
	`key_name` VARCHAR(50) NOT NULL DEFAULT 'key_name',
	`value` VARCHAR(250) NOT NULL DEFAULT 'value_details',
	`inserted` DATETIME NOT NULL,
	`updated` DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `unique_setting_key_name` (`key_name`)
)
ENGINE=InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
