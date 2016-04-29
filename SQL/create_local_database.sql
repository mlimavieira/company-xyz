SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `company_xyz` ;
CREATE SCHEMA IF NOT EXISTS `company_xyz` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `company_xyz` ;

-- -----------------------------------------------------
-- Table `company_xyz`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company_xyz`.`customer` ;

CREATE TABLE IF NOT EXISTS `company_xyz`.`customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `credit_limit` DOUBLE NOT NULL,
  `current_credit_limit` DOUBLE NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  `address` VARCHAR(255) NULL,
  `phone1` VARCHAR(20) NULL,
  `phone2` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `company_xyz`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company_xyz`.`product` ;

CREATE TABLE IF NOT EXISTS `company_xyz`.`product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `company_xyz`.`sale_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company_xyz`.`sale_order` ;

CREATE TABLE IF NOT EXISTS `company_xyz`.`sale_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `total_price` DOUBLE NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customer_idx` (`customer_id` ASC),
  CONSTRAINT `fk_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `company_xyz`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `company_xyz`.`order_line`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company_xyz`.`order_line` ;

CREATE TABLE IF NOT EXISTS `company_xyz`.`order_line` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sale_order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `product_price` DOUBLE NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  INDEX `fk_product1_idx` (`product_id` ASC),
  INDEX `fk_sale_order1_idx` (`sale_order_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sale_order1`
    FOREIGN KEY (`sale_order_id`)
    REFERENCES `company_xyz`.`sale_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `company_xyz`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
