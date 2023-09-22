CREATE TABLE `web-tin-tuc`.`role`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `web-tin-tuc`.`user`
(
    `id`       BIGINT       NOT NULL AUTO_INCREMENT,
    `fullname` VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `avatar`   TEXT NULL,
    `token`    VARCHAR(255) NULL,
    `roleid`   BIGINT NULL DEFAULT 2,
    PRIMARY KEY (`id`),
    INDEX      `fk_user_role_idx` (`roleid` ASC) VISIBLE,
    CONSTRAINT `fk_user_role`
        FOREIGN KEY (`roleid`)
            REFERENCES `web-tin-tuc`.`role` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE `web-tin-tuc`.`category`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(45) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `web-tin-tuc`.`article`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `title`        VARCHAR(255) NOT NULL,
    `description`  TEXT         NOT NULL,
    `thumbnail`    VARCHAR(255) NULL,
    `content`      TEXT         NOT NULL,
    `categoryid`   BIGINT       NOT NULL,
    `views`        INT          NOT NULL DEFAULT 0,
    `createddate`  TIMESTAMP    NOT NULL,
    `createdby`    VARCHAR(45)  NOT NULL,
    `modifieddate` TIMESTAMP    NOT NULL,
    `modifiedby`   VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`id`),
    INDEX          `fk_article_category_idx` (`categoryid` ASC) VISIBLE,
    CONSTRAINT `fk_article_category`
        FOREIGN KEY (`categoryid`)
            REFERENCES `web-tin-tuc`.`category` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE `web-tin-tuc`.`comments`
(
    `id`           BIGINT    NOT NULL AUTO_INCREMENT,
    `content`      TEXT      NOT NULL,
    `createddate`  TIMESTAMP NOT NULL,
    `modifieddate` TIMESTAMP NOT NULL,
    `userid`       BIGINT    NOT NULL,
    `articleid`    BIGINT    NOT NULL,
    `parentid`     BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX          `fk_comments_user_idx` (`userid` ASC) VISIBLE,
    INDEX          `fk_comments_article_idx` (`articleid` ASC) VISIBLE,
    CONSTRAINT `fk_comments_user`
        FOREIGN KEY (`userid`)
            REFERENCES `web-tin-tuc`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_comments_article`
        FOREIGN KEY (`articleid`)
            REFERENCES `web-tin-tuc`.`article` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


INSERT INTO `web-tin-tuc`.`role` (`code`, `name`)
VALUES ('admin', 'Quản trị viên');
INSERT INTO `web-tin-tuc`.`role` (`code`, `name`)
VALUES ('user', 'Người dùng');

INSERT INTO `web-tin-tuc`.`user` (`fullname`, `email`, `username`, `password`, `roleid`)
VALUES ('Nguyễn Văn Toàn', 'admin@admin.com', 'admin', 'admin', '1');
INSERT INTO `web-tin-tuc`.`user` (`fullname`, `email`, `username`, `password`, `roleid`)
VALUES ('Họ Tên Người Dùng', 'user@user.com', 'user', '1', '2');
