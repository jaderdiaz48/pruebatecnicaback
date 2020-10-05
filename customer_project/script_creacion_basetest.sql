CREATE SCHEMA `customers_credit_info` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `customers_credit_info`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`));
  
INSERT INTO `customers_credit_info`.`roles` (`id`, `name`) VALUES ('1', 'admin');
INSERT INTO `customers_credit_info`.`roles` (`id`, `name`) VALUES ('2', 'customer');


CREATE TABLE `customers_credit_info`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `rol` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_username` USING BTREE (`user_name`) VISIBLE,
  INDEX `FK_users_roles_idx` (`rol` ASC) VISIBLE,
  CONSTRAINT `FK_users_roles`
    FOREIGN KEY (`rol`)
    REFERENCES `customers_credit_info`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

INSERT INTO `customers_credit_info`.`users` (`id`, `user_name`, `password`, `rol`) VALUES ('1', 'admin', '$2a$12$9H1KCF2agbFRi0/AqJvD.uvU90f42pfmmQmsDfdSzPqHkRc.V8p6u', '2');

CREATE TABLE `customers_credit_info`.`customers` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `names` VARCHAR(45) NOT NULL,
  `last_names` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK__idx` (`user` ASC) VISIBLE,
  UNIQUE INDEX `UK_dni` USING BTREE (`dni`) VISIBLE,
  UNIQUE INDEX `UK_user` USING BTREE (`user`) VISIBLE,
  CONSTRAINT `FK_customers_users`
  FOREIGN KEY (`user`)
  REFERENCES `customers_credit_info`.`users` (`id`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE);


CREATE TABLE `customers_credit_info`.`card_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `customers_credit_info`.`card_types` (`id`, `name`, `created_at`) VALUES ('1', 'American Express', '2020-10-05');
INSERT INTO `customers_credit_info`.`card_types` (`id`, `name`, `created_at`) VALUES ('2', 'Master Card','2020-10-05');
INSERT INTO `customers_credit_info`.`card_types` (`id`, `name`, `created_at`) VALUES ('3', 'Visa','2020-10-05');

CREATE TABLE `customers_credit_info`.`credit_cards` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `card_types_id` INT NOT NULL,
  `number` BIGINT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  INDEX `FK_credit_cards_customers_idx` (`customer_id` ASC) VISIBLE,
  INDEX `FK_credit_cards_types_idx` (`card_types_id` ASC) VISIBLE,
  UNIQUE INDEX `UK_dni_customer_numberq` USING BTREE (`customer_id`, `card_types_id`, `number`) VISIBLE,
  CONSTRAINT `FK_credit_cards_customers`
    FOREIGN KEY (`customer_id`)
    REFERENCES `customers_credit_info`.`customers` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `FK_credit_cards_types`
    FOREIGN KEY (`card_types_id`)
    REFERENCES `customers_credit_info`.`card_types` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);