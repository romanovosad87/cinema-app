--liquibase formatted sql
--changeset YOUR_NAME:create-all-tables

-- --------------------------
-- Table structure for users
-- --------------------------
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_users_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- --------------------------
-- Table structure for roles
-- --------------------------
CREATE TABLE `roles` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `roleName` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_roles_roleName` (`roleName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- -------------------------------
-- Table structure for users_roles
-- -------------------------------
CREATE TABLE `users_roles` (
                               `user_id` bigint NOT NULL,
                               `role_id` bigint NOT NULL,
                               PRIMARY KEY (`user_id`,`role_id`),
                               KEY `FK_users_roles_roles` (`role_id`),
                               CONSTRAINT `FK_users_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `FK_users_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ---------------------------
-- Table structure for movies
-- ---------------------------
CREATE TABLE `movies` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `title` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- --------------------------------
-- Table structure for cinema_halls
-- --------------------------------
CREATE TABLE `cinema_halls` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `capacity` int NOT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------------
-- Table structure for movie_sessions
-- ----------------------------------
CREATE TABLE `movie_sessions` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `show_time` datetime(6) DEFAULT NULL,
                                  `cinema_hall_id` bigint NOT NULL,
                                  `movie_id` bigint NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FK_movie_sessions_cinema_hall` (`cinema_hall_id`),
                                  KEY `FK_movie_sessions_movie` (`movie_id`),
                                  CONSTRAINT `FK_movie_sessions_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
                                  CONSTRAINT `FK_movie_sessions_cinema_halls` FOREIGN KEY (`cinema_hall_id`) REFERENCES `cinema_halls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for tickets
-- ----------------------------
CREATE TABLE `tickets` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `movie_session_id` bigint DEFAULT NULL,
                           `user_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK_tickets_movie_sessions` (`movie_session_id`),
                           KEY `FK_tickets_users` (`user_id`),
                           CONSTRAINT `FK_tickets_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                           CONSTRAINT `FK_tickets_movie_sessions` FOREIGN KEY (`movie_session_id`) REFERENCES `movie_sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- -----------------------------------
-- Table structure for shopping_carts
-- -----------------------------------
CREATE TABLE `shopping_carts` (
                                  `id` bigint NOT NULL,
                                  PRIMARY KEY (`id`),
                                  CONSTRAINT `FK_PK_shopping_carts_users` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ------------------------------------------
-- Table structure for shopping_carts_tickets
-- ------------------------------------------
CREATE TABLE `shopping_carts_tickets` (
                                          `shopping_cart_id` bigint NOT NULL,
                                          `ticket_id` bigint NOT NULL,
                                          UNIQUE KEY `UK_shopping_carts_tickets_tickets` (`ticket_id`),
                                          KEY `FK_shopping_carts_tickets_shopping_carts` (`shopping_cart_id`),
                                          CONSTRAINT `FK_shopping_carts_tickets_tickets` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`),
                                          CONSTRAINT `FK_shopping_carts_tickets_shopping_carts` FOREIGN KEY (`shopping_cart_id`) REFERENCES `shopping_carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ---------------------------
-- Table structure for orders
-- ---------------------------
CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `order_time` datetime(6) DEFAULT NULL,
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FK_orders_users` (`user_id`),
                          CONSTRAINT `FK_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- -----------------------------------
-- Table structure for orders_tickets
-- -----------------------------------
CREATE TABLE `orders_tickets` (
                                  `order_id` bigint NOT NULL,
                                  `ticket_id` bigint NOT NULL,
                                  UNIQUE KEY `UK_orders_tickets_tickets` (`ticket_id`),
                                  KEY `FK_orders_tickets_orders` (`order_id`),
                                  CONSTRAINT `FK_orders_tickets_tickets` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`),
                                  CONSTRAINT `FK_orders_tickets_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3