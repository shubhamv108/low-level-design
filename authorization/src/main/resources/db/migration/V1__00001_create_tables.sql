CREATE TABLE `permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_public` bit(1) DEFAULT b'0',
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pnvtwliis6p05pn6i3ndjrqt2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `permissions`(`name`) VALUES('ALL');


CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` int NOT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles`(`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(NOW(), 0, NOW(), 0, 1, 'ADMIN');
INSERT INTO `roles`(`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(NOW(), 0, NOW(), 0, 1, 'EMPLOYEE');
INSERT INTO `roles`(`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(NOW(), 0, NOW(), 0, 1, 'ORGANIZATION_ADMIN');
INSERT INTO `roles`(`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(NOW(), 0, NOW(), 0, 1, 'ORGANIZATION_USER');
INSERT INTO `roles`(`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(NOW(), 0, NOW(), 0, 1, 'INDIVIDUAL_USER');


CREATE TABLE `roles__permissions__mapping` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKpjokekin6wo2sqp0q1jtamq0q` (`permission_id`),
  CONSTRAINT `FKfpd6ciknd1hki2oodqu3ko49h` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKpjokekin6wo2sqp0q1jtamq0q` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles__permissions__mapping` (`role_id`, `permission_id`)
VALUES((SELECT `id` FROM `roles` WHERE `name` = 'ADMIN'),
       (SELECT `id` FROM `permissions` WHERE `name` = 'ALL'));


CREATE TABLE `users_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES(0, (SELECT `id` FROM `roles` WHERE `name` = 'ADMIN'));
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES(0, (SELECT `id` FROM `roles` WHERE `name` = 'EMPLOYEE'));
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES(0, (SELECT `id` FROM `roles` WHERE `name` = 'ORGANIZATION_USER'));
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES(0, (SELECT `id` FROM `roles` WHERE `name` = 'ORGANIZATION_ADMIN'));
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES(0, (SELECT `id` FROM `roles` WHERE `name` = 'INDIVIDUAL_USER'));