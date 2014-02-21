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
    name CHAR(64) primary key, 
    passwordHash CHAR(64), 
    achievements text  
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
	name text,
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

