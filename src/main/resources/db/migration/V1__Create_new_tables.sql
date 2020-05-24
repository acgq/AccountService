CREATE TABLE `as_userinfo`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`    varchar(64)         NOT NULL COMMENT 'user name',
    `password`    varchar(64)         NOT NULL,
    `salt`        varchar(50)         NOT NULL,
    `create_time` datetime            NOT NULL,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `as_tag`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `description` varchar(50)         NOT NULL,
    `user_id`     bigint(20) unsigned NOT NULL,
    `status`      tinyint(1) unsigned NOT NULL COMMENT '0-> disabled, 1-> enabled',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`),
    KEY `idx_uid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `as_record`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `amount`      decimal(18,2)       NOT NULL DEFAULT '0.00',
    `note`        varchar(200)        DEFAULT NULL,
    `category`    tinyint(1) unsigned NOT NULL COMMENT '0-> outcome, 1-> income',
    `status`      tinyint(1) unsigned NOT NULL COMMENT '0-> disabled, 1-> enabled',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `user_id`     bigint(20) unsigned NOT NULL,
    PRIMARY KEY `pk_id` (`id`),
    KEY `user_key` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `as_record_tag_mapping`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `record_id`   bigint(20) unsigned NOT NULL,
    `tag_id`  bigint(20) unsigned NOT NULL,
    `status`      tinyint(1) unsigned NOT NULL COMMENT '0-> disabled, 1-> enabled',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
