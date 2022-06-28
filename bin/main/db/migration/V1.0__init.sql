SET NAMES utf8;
SET
    time_zone = '+00:00';
SET
    sql_mode = 'NO_AUTO_VALUE_ON_ZERO';


CREATE
    DATABASE IF NOT EXISTS `${schema}` DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE
`${schema}`;

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `name`         varchar(50) NULL,
    `description`          varchar(500) NOT NULL,
    `domain`          varchar(50) NOT NULL,
    `disabled`            boolean     NOT NULL default false,
    `created_date_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `org_name_unique` (`name`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name`         varchar(50) NULL,
    `last_name`          varchar(50) NOT NULL,
    `email`              varchar(50) NOT NULL,
    `password`           varchar(500),
    `role_name`          varchar(500) NOT NULL DEFAULT 'ROLE_USER',
    `disabled`            boolean     NOT NULL default false,
    `created_date_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL DEFAULT NULL,
    `organization_id`   INT  not null references organization(`id`),
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_email_unique` (`email`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `user_password_reset_request`;

CREATE TABLE `user_password_reset_request`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id`                INT     NOT NULL,
    `expire_date_time`       timestamp not null,
    `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `forecast_dash_board`;

CREATE TABLE `forecast_dash_board`
(
    `id` INT NOT NULL AUTO_INCREMENT ,
    `organization_id`   INT  not null references organization(`id`),
    `dash_board_id`          char(36)   NOT NULL,
    `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `projects`;

CREATE TABLE `projects`
(
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name`          varchar(50)   NOT NULL,
    `description`          varchar(500)   NULL,
    `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;    
    


DROP TABLE IF EXISTS `releases`;
CREATE TABLE `releases`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
    
DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL ,
      `contact` VARCHAR(200) NOT NULL ,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
	
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
    
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`
(
	  `id` INT NOT NULL AUTO_INCREMENT ,
	  `name` VARCHAR(200) NOT NULL ,
      `user_id` INT NULL,
      `client_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`client_id`) REFERENCES clients(`id`)
	
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `features`;
CREATE TABLE `features`
(
	  `id` INT NOT NULL AUTO_INCREMENT ,
	  `name` VARCHAR(200) NOT NULL ,
       `product_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES products(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`
(
	  `id` INT NOT NULL AUTO_INCREMENT ,
	  `name` VARCHAR(10) NOT NULL ,
      `type` ENUM('epic', 'story'),
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
    
DROP TABLE IF EXISTS `teams`;
CREATE TABLE `teams`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	 
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
    
DROP TABLE IF EXISTS `epics`;

CREATE TABLE `epics`
(
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name`          varchar(50)   NOT NULL,
    `description`          varchar(500)   NULL,
    `accptance_criteria` VARCHAR(200) NOT NULL,
    `points` INT NULL,
	`original_estimate` INT NULL,
    `remainng_estimate` INT NULL,
    `start_date` DATE NULL,
    `end_date` DATE NULL,
    `due_date` DATE NULL,
    `hours_worked` INT NULL,
    `risk_level` INT NULL,
    `priority` ENUM('low', 'medium', 'high', 'critical'),
    `release_id` INT NULL,
    `feature_id` INT NULL,
    `user_id` INT NULL,
    `team_id` INT NULL,
	`created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`team_id`) REFERENCES teams(`id`),
    FOREIGN KEY (`release_id`) REFERENCES releases(`id`),
    FOREIGN KEY (`feature_id`) REFERENCES features(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `stories`;
CREATE TABLE `stories`
(
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name`          varchar(50)   NOT NULL,
    `description`          varchar(500)   NULL,
    `accptance_criteria` VARCHAR(200) NOT NULL,
    `points` INT NULL,
	`original_estimate` INT NULL,
    `remainng_estimate` INT NULL,
    `start_date` DATE NULL,
    `end_date` DATE NULL,
    `due_date` DATE NULL,
    `hours_worked` INT NULL,
    `risk_level` INT NULL,
    `priority` ENUM('low', 'medium', 'high', 'critical'),
    `epic_id` INT NULL,
    `release_id` INT NULL,
    `feature_id` INT NULL,
    `user_id` INT NULL,
    `team_id` INT NULL,
	`created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`epic_id`) REFERENCES epics(`id`),
    FOREIGN KEY (`team_id`) REFERENCES teams(`id`),
    FOREIGN KEY (`release_id`) REFERENCES releases(`id`),
    FOREIGN KEY (`feature_id`) REFERENCES features(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;


    
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	  `epic_id` INT NULL,
	  `story_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`epic_id`) REFERENCES epics(`id`),
     FOREIGN KEY (`story_id`) REFERENCES epics(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `summary` VARCHAR(500) NOT NULL,
	  `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
    
DROP TABLE IF EXISTS `testcases_modules`;
CREATE TABLE `testcases_modules`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(100) NOT NULL,
	  `description` VARCHAR(500) NOT NULL,
	  `project_id` INT NULL,
	  `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
     FOREIGN KEY (`project_id`) REFERENCES projects(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `testcases`;
CREATE TABLE `testcases`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `test_id` VARCHAR(10) NULL,
	  `name` VARCHAR(50) NULL,
	  `description` VARCHAR(500)  NULL,
	  `expected_results` VARCHAR(500)  NULL,
	  `actual_results` VARCHAR(500)  NULL,
	  `order` INT NULL,
	  `status` ENUM('pass', 'fail'),
	  `user_id` INT NULL,
	  `testcase_module_id` INT NULL,
	  `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`testcase_module_id`) REFERENCES testcases_modules(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `testcases_steps`;
CREATE TABLE `testcases_steps`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `description` VARCHAR(500) NOT NULL,
	  `expected_results` VARCHAR(500) NOT NULL,
	  `actual_results` VARCHAR(500) NOT NULL,
	  `order` INT NULL,
	  `testcase_id` INT NULL,
	  `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`testcase_id`) REFERENCES testcases(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

    
DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	  `type` VARCHAR(50) NOT NULL,
	  `status` ENUM('open', 'close'),
	  `priority` ENUM('low', 'medium', 'high', 'critical'),
	  `user_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `timesheet_projects`;
CREATE TABLE `timesheet_projects`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	  `client_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
     FOREIGN KEY (`client_id`) REFERENCES clients(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
DROP TABLE IF EXISTS `timesheet_sub_projects`;
CREATE TABLE `timesheet_sub_projects`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	  `timesheet_projects_id` INT NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
     FOREIGN KEY (`timesheet_projects_id`) REFERENCES timesheet_projects(`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

    DROP TABLE IF EXISTS `timesheet_tasks`;
CREATE TABLE `timesheet_tasks`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `name` VARCHAR(200) NOT NULL,
	  `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `timesheet_hours`;
CREATE TABLE `timesheet_hours`
(
	  `id` INT NOT NULL AUTO_INCREMENT,
	  `timesheet_projects_id` INT NULL,
	  `timesheet_sub_projects_id` INT NULL,
	  `timesheet_tasks_id` INT NULL,
	  `date`  date NULL,
	  `user_id` INT NULL,
	  `start_at` TIME NULL,
	  `end_at` TIME  NULL,
	  `comment` VARCHAR(500)  NULL,
      `created_date_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `last_updated_date_time` timestamp NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
      FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`timesheet_projects_id`) REFERENCES timesheet_projects(`id`),
    FOREIGN KEY (`timesheet_sub_projects_id`) REFERENCES timesheet_sub_projects(`id`)
    ) ENGINE = InnoDB