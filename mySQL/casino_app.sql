CREATE DATABASE Casino_App;

CREATE TABLE Casino_App.Account (
    username VARCHAR(45) not null,
    password VARCHAR(45) not null,
    balance INT not null,
	credit_card_number INT unique not null,
	first_name VARCHAR(45) not null,
    last_name VARCHAR(45) not null,
    phone_number INT unique not null,
	age INT not null, 
    PRIMARY KEY (username)
);

create table Casino_App.Game (
    game_name VARCHAR(45) not null,
    username VARCHAR(45) not null,
    time_played DATETIME not null,
    games_won INT not null,
    games_lost INT not null,
    winrate DECIMAL(5,2) not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    PRIMARY KEY (game_name, username)
);

create table Casino_App.Withdraw (
    date DATE,
    amount INT,
    username VARCHAR(45) not null,
    game_name VARCHAR(45) not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    FOREIGN KEY (game_name) REFERENCES Game(game_name),
    PRIMARY KEY (username, game_name)
);

create table Casino_App.Purchase (
    date DATE,
    amount INT,
    username VARCHAR(45) not null,
    game_name VARCHAR(45) not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    FOREIGN KEY (game_name) REFERENCES Game(game_name),
    PRIMARY KEY (username,game_name)
);
