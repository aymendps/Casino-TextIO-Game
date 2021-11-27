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

#ADD DEFAULT ROWS TO AVOID FOREIGN KEY CHECK ERRORS, ALTERNATIVELY CAN SET FOREIGN_KEYS_CHECKS TO 0:

#########################################################################################################

insert into purchase(date,amount,username,game_name) values("2021-11-25",100,"aziz","blackjack");
SELECT * from account;
DELETE FROM account;
Insert into account(username,password,balance,credit_card_number,first_name ,last_name ,phone_number ,age)
values("aziz", "lolaazz", 500, 151510,"aziz","maazouz",1541545,23);
Insert into game(game_name,username,time_played,games_won,games_lost,largest_win,longest_streak)
values("slot machine", "aziz", 4, 2, 3, 300, 7);