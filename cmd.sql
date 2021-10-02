CREATE TABLE `ltw`.`tblProfile` (
  `id` INT NOT NULL,
  `fullname` VARCHAR(100) NOT NULL,
  `address` VARCHAR(100) NULL,
  `dob` DATE NULL,
  `phone` VARBINARY(12) NULL,
  `createdate` DATE NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `ltw`.`tblUser` (
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(45) NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `profileid` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`profileid` ASC) VISIBLE,
  CONSTRAINT `id`
    FOREIGN KEY (`profileid`)
    REFERENCES `ltw`.`tblProfile` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);
    

CREATE TABLE `ltw`.`tblFllower` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fanid` INT NULL,
  `idolid` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblFllower_1_idx` (`fanid` ASC) VISIBLE,
  INDEX `fk_tblFllower_1_idx1` (`idolid` ASC) VISIBLE,
  CONSTRAINT `fanfk`
    FOREIGN KEY (`fanid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `idolfk`
    FOREIGN KEY (`idolid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);
    

CREATE TABLE `ltw`.`tblAlbum` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `create` DATE NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `ltw`.`tblPicture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `size` FLOAT NOT NULL,
  `tag` VARCHAR(255) NULL,
  `description` VARCHAR(255) NULL,
  `title` VARCHAR(255) NULL,
  `filetype` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `ltw`.`tblPiclnAlbum` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pictureabid` INT NULL,
  `abid` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblPiclnAlbum_1_idx` (`pictureabid` ASC) VISIBLE,
  INDEX `fk_tblPiclnAlbum_2_idx` (`abid` ASC) VISIBLE,
  CONSTRAINT `fk_tblPiclnAlbum_1`
    FOREIGN KEY (`pictureabid`)
    REFERENCES `ltw`.`tblPicture` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tblPiclnAlbum_2`
    FOREIGN KEY (`abid`)
    REFERENCES `ltw`.`tblAlbum` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);


CREATE TABLE `ltw`.`tblUpload` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `authoruploadid` INT NULL,
  `picuploadid` INT NULL,
  `createat` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblUpload_2_idx` (`authoruploadid` ASC) VISIBLE,
  INDEX `fk_tblUpload_1_idx` (`picuploadid` ASC) VISIBLE,
  CONSTRAINT `fk_tblUpload_1`
    FOREIGN KEY (`picuploadid`)
    REFERENCES `ltw`.`tblPicture` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tblUpload_2`
    FOREIGN KEY (`authoruploadid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);


CREATE TABLE `ltw`.`tblReaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `emotion` VARCHAR(255) NULL,
  `content` VARCHAR(255) NULL,
  `createat` DATE NULL,
  `userid` INT NULL,
  `picid` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblReaction_1_idx` (`userid` ASC) VISIBLE,
  INDEX `fk_tblReaction_2_idx` (`picid` ASC) VISIBLE,
  CONSTRAINT `fk_tblReaction_1`
    FOREIGN KEY (`userid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tblReaction_2`
    FOREIGN KEY (`picid`)
    REFERENCES `ltw`.`tblPicture` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);


CREATE TABLE `ltw`.`tblBookedPicture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `discount` FLOAT NULL,
  `price` FLOAT NOT NULL,
  `createat` DATE NULL,
  `userbookedid` INT NULL,
  `picbookedid` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblBookedPicture_1_idx` (`userbookedid` ASC) VISIBLE,
  INDEX `fk_tblBookedPicture_2_idx` (`picbookedid` ASC) VISIBLE,
  CONSTRAINT `fk_tblBookedPicture_1`
    FOREIGN KEY (`userbookedid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tblBookedPicture_2`
    FOREIGN KEY (`picbookedid`)
    REFERENCES `ltw`.`tblPicture` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    
CREATE TABLE `ltw`.`tblBookingPicture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bookedpicid` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblBookingPicture_1_idx` (`bookedpicid` ASC) VISIBLE,
  CONSTRAINT `fk_tblBookingPicture_1`
    FOREIGN KEY (`bookedpicid`)
    REFERENCES `ltw`.`tblBookedPicture` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `ltw`.`tblBill` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bookingid` INT NULL,
  `buyerid` INT NULL,
  `createat` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tblBill_1_idx` (`bookingid` ASC) VISIBLE,
  INDEX `fk_tblBill_2_idx` (`buyerid` ASC) VISIBLE,
  CONSTRAINT `fk_tblBill_1`
    FOREIGN KEY (`bookingid`)
    REFERENCES `ltw`.`tblBookingPicture` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblBill_2`
    FOREIGN KEY (`buyerid`)
    REFERENCES `ltw`.`tblUser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


    


