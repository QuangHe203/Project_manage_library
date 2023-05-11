-- MySQL Script generated by MySQL Workbench
-- Thu May 11 15:54:04 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`books` (
  `id` VARCHAR(10) NOT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `author` VARCHAR(50) NULL DEFAULT NULL,
  `publisher` VARCHAR(50) NULL DEFAULT NULL,
  `publicationYear` INT NULL DEFAULT NULL,
  `quantity` INT NULL DEFAULT NULL,
  `genre` VARCHAR(50) NULL DEFAULT NULL,
  `status` VARCHAR(50) NULL DEFAULT NULL,
  `location` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library`.`borrowers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`borrowers` (
  `id` VARCHAR(10) NOT NULL,
  `fullName` VARCHAR(50) NULL DEFAULT NULL,
  `phoneNumber` VARCHAR(15) NULL DEFAULT NULL,
  `email` VARCHAR(50) NULL DEFAULT NULL,
  `dateOfBirth` DATE NULL DEFAULT NULL,
  `type` VARCHAR(20) NULL DEFAULT NULL,
  `last` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library`.`cards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`cards` (
  `cardId` VARCHAR(10) NOT NULL,
  `borrowerId` VARCHAR(10) NULL DEFAULT NULL,
  `borrowDate` DATE NULL DEFAULT NULL,
  `returnDate` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`cardId`),
  INDEX `borrowerId` (`borrowerId` ASC) VISIBLE,
  INDEX `idx_cards_cardId` (`cardId` ASC) VISIBLE,
  CONSTRAINT `cards_ibfk_1`
    FOREIGN KEY (`borrowerId`)
    REFERENCES `library`.`borrowers` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
