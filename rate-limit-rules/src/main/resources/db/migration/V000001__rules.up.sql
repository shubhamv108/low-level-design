CREATE TABLE `rules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `created_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` int NOT NULL,
  `version` int DEFAULT NULL,
  `allowed` int NOT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  `duration` bigint NOT NULL,
  `plan` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `time_unit` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdfmt3ohi32dqkckifq9ht3tfq` (`plan`,`api_name`,`role`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci