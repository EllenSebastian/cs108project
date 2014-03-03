# because this is mysql, these tables do not enforce any integrity constraints,
# such as "Friends must also be in the Users table".
# however, type matching is enforced. 
# Datetime format is entered in format "YYYY-MM-DD HH:MM:SS".
# another note: Friends should be symmetric, so if you insert ("Patrick","Molly"), you should also insert ("Molly","Patrick").

drop table if exists Users; 
drop table if exists Friends; 
drop table if exists Quizzes;
drop table if exists Questions; 
drop table if exists Messages; 
drop table if exists QuizzesTaken; 


CREATE TABLE Users (
	user_id int AUTO_INCREMENT primary key,
    name CHAR(64),
    passwordHash CHAR(64), 
    isAdmin BOOLEAN
);

CREATE TABLE Friends (
	name1 CHAR(64) ,
	name2 CHAR(64) , 
	primary key (name1,name2)
);

CREATE TABLE Quizzes (
	pKey INT primary key,
	name text,
	url text,
	creator text,
	immediateFeedback BOOLEAN, 
	multiplePages BOOLEAN, 
	practiceMode BOOLEAN,
	randomOrder BOOLEAN, 
	whenCreated DATETIME
	);

CREATE TABLE Questions(
	pKey INT primary key,
	quizKey INT,
	type text, 
	questionData text # encrypted and interpreted by question subclass
);

CREATE TABLE Messages (
	fromUser text,
	toUser text,
	type text,
	msgText text,
	challengeURL text
);

CREATE TABLE QuizzesTaken(
	username text, 
	quizKey INT, # pKey for quiz
	score FLOAT,
	whenTaken DATETIME,
	duration FLOAT
);

CREATE TABLE Activity(
	user_id int,
	time DATETIME,
	type int,
	score float,
    pKey INT
);

Create TABLE Achievment (
	user_id int,
	type int,
	time DATETIME
)

Create TABLE Announcement (
	announcement_id int  primary key,
	user_id int,
	time DATETIME,
	subject text,
	body text
)

Create TABLE Message (
	message_id int primary key,
	type int,
	read Boolean,
	body text,
	pKey int,
	fromUser int,
	toUser int,
	time DATETIME;
)

Create TABLE Friend (
    user1 int,
    user2 int
)

Create TABLE Friend_request(
    requestor_id int,
    requestee_id int
)
