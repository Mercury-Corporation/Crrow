-- MySQL Script generated by MySQL Workbench
-- Mon Dec  5 16:12:51 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Crrow
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Crrow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Crrow` DEFAULT CHARACTER SET utf8 ;
USE `Crrow` ;

-- -----------------------------------------------------
-- Table `Crrow`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  `birthday` DATE NULL,
  `sex` INT NOT NULL DEFAULT 0 COMMENT 'ISO 5218によると、\n0 -> 不明(未入力)\n1 -> 男\n2 -> 女\n9 -> 適用不能(その他)',
  `introduction` VARCHAR(200) NULL,
  `ban_level` INT NOT NULL DEFAULT 0,
  `type` INT NOT NULL COMMENT '0 - ゲスト\n1 - 通常ユーザー\n2 - 教師\n8 - デバッグ\n9 - 運営',
  `coin` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Content` (
  `id` INT NOT NULL,
  `User_id` INT NOT NULL,
  `title` VARCHAR(20) NOT NULL,
  `public` TINYINT NOT NULL,
  `views` INT UNSIGNED NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`id`, `User_id`),
  UNIQUE INDEX `User_id_UNIQUE` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_Content_User`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Content_Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Content_Category` (
  `Content_id` INT NOT NULL,
  `category` VARCHAR(10) NOT NULL,
  INDEX `fk_Content_has_Category_Content1_idx` (`Content_id` ASC) VISIBLE,
  CONSTRAINT `fk_Content_has_Category_Content1`
    FOREIGN KEY (`Content_id`)
    REFERENCES `Crrow`.`Content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Following_Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Following_Category` (
  `User_id` INT NOT NULL,
  `category` VARCHAR(10) NOT NULL,
  INDEX `fk_User_has_Category_User1_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_Category_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Content_Like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Content_Like` (
  `User_id` INT NOT NULL,
  `Content_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `Content_id`),
  INDEX `fk_User_has_Content_Content1_idx` (`Content_id` ASC) VISIBLE,
  INDEX `fk_User_has_Content_User1_idx` (`User_id` ASC) VISIBLE,
  UNIQUE INDEX `User_id_UNIQUE` (`User_id` ASC) VISIBLE,
  UNIQUE INDEX `Content_id_UNIQUE` (`Content_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_Content_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Content_Content1`
    FOREIGN KEY (`Content_id`)
    REFERENCES `Crrow`.`Content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Favorite` (
  `User_id` INT NOT NULL,
  `follower_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `follower_id`),
  INDEX `fk_User_has_User_User2_idx` (`follower_id` ASC) VISIBLE,
  INDEX `fk_User_has_User_User1_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_User_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_User_User2`
    FOREIGN KEY (`follower_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Question` (
  `question_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  `content` VARCHAR(50) NOT NULL,
  `time` DATETIME NOT NULL,
  `solve` TINYINT NOT NULL,
  PRIMARY KEY (`question_id`, `User_id`),
  INDEX `fk_Question_User1_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_Question_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Room` (
  `id` INT NOT NULL,
  `public` TINYINT NOT NULL DEFAULT 0,
  `Question_question_id` INT NULL,
  `best_answer` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Room_Question1_idx` (`Question_question_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_Room_Question1`
    FOREIGN KEY (`Question_question_id`)
    REFERENCES `Crrow`.`Question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`DM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`DM` (
  `Room_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  `message` VARCHAR(1000) NOT NULL,
  `time` DATETIME NOT NULL,
  PRIMARY KEY (`Room_id`, `User_id`),
  INDEX `fk_User_has_User_User3_idx` (`User_id` ASC) VISIBLE,
  INDEX `fk_DM_Room1_idx` (`Room_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_User_User3`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DM_Room1`
    FOREIGN KEY (`Room_id`)
    REFERENCES `Crrow`.`Room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Question_Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Question_Category` (
  `Question_question_id` INT NOT NULL,
  `category` VARCHAR(10) NOT NULL,
  INDEX `fk_Question_Category_Question1_idx` (`Question_question_id` ASC) VISIBLE,
  CONSTRAINT `fk_Question_Category_Question1`
    FOREIGN KEY (`Question_question_id`)
    REFERENCES `Crrow`.`Question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Setting` (
  `User_id` INT NOT NULL,
  `nickname` VARCHAR(20) NOT NULL,
  `icon` VARCHAR(32) NOT NULL,
  `email` VARCHAR(20) NULL,
  `school` VARCHAR(13) NOT NULL,
  `range` INT UNSIGNED NOT NULL DEFAULT 10,
  PRIMARY KEY (`User_id`),
  UNIQUE INDEX `User_id_UNIQUE` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_Setting_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Room_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Room_member` (
  `Room_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  PRIMARY KEY (`Room_id`, `User_id`),
  INDEX `fk_Room_has_User_User1_idx` (`User_id` ASC) VISIBLE,
  INDEX `fk_Room_has_User_Room1_idx` (`Room_id` ASC) VISIBLE,
  UNIQUE INDEX `Room_id_UNIQUE` (`Room_id` ASC) VISIBLE,
  UNIQUE INDEX `User_id_UNIQUE` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fk_Room_has_User_Room1`
    FOREIGN KEY (`Room_id`)
    REFERENCES `Crrow`.`Room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Room_has_User_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Crrow`.`Question_Like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Crrow`.`Question_Like` (
  `User_id` INT NOT NULL,
  `Room_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `Room_id`),
  INDEX `fk_User_has_Room_Room1_idx` (`Room_id` ASC) VISIBLE,
  INDEX `fk_User_has_Room_User1_idx` (`User_id` ASC) VISIBLE,
  UNIQUE INDEX `User_id_UNIQUE` (`User_id` ASC) VISIBLE,
  UNIQUE INDEX `Room_id_UNIQUE` (`Room_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_has_Room_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `Crrow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Room_Room1`
    FOREIGN KEY (`Room_id`)
    REFERENCES `Crrow`.`Room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;