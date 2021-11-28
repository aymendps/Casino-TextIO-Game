create database Casino_App;

create table Casino_App.Account (
    username VARCHAR(45) not null,
    password VARCHAR(45) not null,
    balance INT not null,
	credit_card_number INT unique not null,
	first_name VARCHAR(45) not null,
    last_name VARCHAR(45) not null,
    phone_number INT unique not null,
	age INT not null check(age>=18),
    PRIMARY KEY (username)
);

create table Casino_App.Game (
    game_name VARCHAR(45) not null check(game_name="blackjack" OR game_name="slot machine"),
    username VARCHAR(45) not null,
    time_played INT not null,
    games_won INT not null,
    games_lost INT not null,
    largest_win INT not null,
    longest_streak INT not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    PRIMARY KEY (game_name, username)
);

create table Casino_App.Withdraw (
    id INT auto_increment,
    date DATE not null,
    amount INT not null,
    username VARCHAR(45) not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    PRIMARY KEY (id)
);

create table Casino_App.Deposit (
	id INT auto_increment,
    date DATE not null,
    amount INT not null,
    username VARCHAR(45) not null,
    FOREIGN KEY (username) REFERENCES Account(username),
    PRIMARY KEY (id)
);

#########################################################################################################

insert into purchase(date,amount,username,game_name) values("2021-11-25",100,"aziz","blackjack");
SELECT * from account;
SELECT * from game;
SELECT * from deposit;
SELECT * from withdraw;
DELETE FROM game;
DELETE FROM account;
DELETE FROM deposit;
DELETE FROM withdraw;
ALTER TABLE withdraw AUTO_INCREMENT = 1;
ALTER TABLE deposit AUTO_INCREMENT = 1;
Insert into account(username,password,balance,credit_card_number,first_name ,last_name ,phone_number ,age)
values("aziz", "lolaazz", 500, 151510,"aziz","maazouz",1541545,23);
Insert into game(game_name,username,time_played,games_won,games_lost,largest_win,longest_streak)
values("slot machine", "aziz", 4, 2, 3, 300, 7);
UPDATE account SET balance=balance-50 WHERE username = "aziz";
SELECT * from account WHERE username = "admin" AND password = "admin";
INSERT INTO deposit(date,amount,username) VALUES("2021-11-25",100,"dragunov")