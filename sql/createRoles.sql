DROP TABLE UserRoles;
DROP TABLE account;
CREATE TABLE IF NOT EXISTS account (
  id int(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(32) UNIQUE DEFAULT "",
  sha_pass_hash VARCHAR(40) NOT NULL DEFAULT "",
  sessionkey VARCHAR(80) NOT NULL DEFAULT "",
  v VARCHAR(64) NOT NULL DEFAULT "",
  s VARCHAR(64) NOT NULL DEFAULT "",
  token_key VARCHAR(100) NOT NULL DEFAULT "",
  email VARCHAR(255) NOT NULL DEFAULT "",
  reg_mail VARCHAR(255) NOT NULL DEFAULT "",
  joindate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_ip VARCHAR(15) NOT NULL DEFAULT "127.0.0.1",
  failed_logins int(10) UNSIGNED NOT NULL DEFAULT 0,
  locked TINYINT(3) UNSIGNED NOT NULL DEFAULT 0,
  last_login TIMESTAMP NOT NULL DEFAULT "0000-00-00 00:00:00",
  online TINYINT(3) NOT NULL DEFAULT 0,
  expansion TINYINT(3) UNSIGNED NOT NULL DEFAULT 2,
  mutetime BIGINT(20) NOT NULL DEFAULT 0,
  mutereason VARCHAR(255) NOT NULL DEFAULT "",
  muteby VARCHAR(50) NOT NULL DEFAULT "",
  locale TINYINT(3) UNSIGNED NOT NULL DEFAULT 0,
  os VARCHAR(3) NOT NULL DEFAULT "",
  recruiter INT(10) UNSIGNED NOT NULL DEFAULT 0
);

create table if not exists UserRoles (
  Id               BIGINT PRIMARY KEY AUTO_INCREMENT,
  UserId           INT(10) UNSIGNED NOT NULL,
  username         varchar(15) not null,
  Rolename         varchar(15) not null,
  CONSTRAINT User_UserRoles FOREIGN KEY (UserId) REFERENCES account(id) ON DELETE CASCADE
);

INSERT INTO account(username) VALUES ("auto_user");
INSERT INTO UserRoles(Username, UserId, Rolename) VALUES ("auto_user", 1, "auto");