# example insert statements

# insert user : name Molly, pw FloPup, acheivements a1 through a5
insert into Users values ("Molly","4181eecbd7a755d19fdf73887c54837cbecf63fd","a1,a2,a3,a4,a5");
insert into Users values ("Patrick","b0d81bdef8abef9245c9aebd82b7818b838791df","a1");
insert into Friends values("Molly","Patrick");
insert into Questions values(1,"add","multipleChoice"," (encoded) 2+3 = (a)1 (b)5 (c) 7 (d) -1");
insert into Questions values(2,"add blank","fillInTheBlank","(encoded) 2+3 = ?");
	# note datetime format below
insert into Quizzes values(1,"adding","xyz.com","Molly",FALSE,FALSE,FALSE,FALSE,"2003:12:30 02:03:04");
insert into Messages values("Molly","Patrick","Message","sup",null);
insert into Messages values("Molly","Patrick","Challenge","sup","xyz.com");
insert into QuizzesTaken values ("Molly",1,250.0,"2003:12:30 02:03:04","1200.0");
insert into QuizzesTaken values ("Patrick",1,200.0,"2003:12:30 02:03:04","1000.0");

insert into QuizzesTaken values ("Molly",1,200.0,"2003:12:30 02:03:04","800.0");



# example queries: 

# get all Patrick's friends. He's foreveralone :(
select name2 from Friends where name1 = "Patrick";

# get all info for quiz with key 1
select * from Quizzes where pKey=1;

# get all info for question with key 1 
select * from Quizzes where pKey = 1; 

# get all of the challenge messages that Patrick has received 
select * from Messages where toUser = "Patrick" and type="Challenge";

# get the best 3 scores for quiz 1, where "best" means first the highest score and then the lowest duration: 
select * from QuizzesTaken where quizKey = 1 order by score desc, duration limit 3;
