CREATE TABLE user(id int NOT NULL PRIMARY KEY, username varchar, password varchar, token varchar, session_active varchar);

CREATE TABLE ACCOUNT
(
    id int NOT NULL PRIMARY KEY,
    account_type varchar(255),
    account_number varchar(255)
);

CREATE TABLE statement
(
    id int NOT NULL PRIMARY KEY,
    account_id int,
    datefield varchar(255),
    amount varchar(255)
);

