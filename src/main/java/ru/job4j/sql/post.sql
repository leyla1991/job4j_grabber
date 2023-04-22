CREATE TABLE post(
	id serial PRIMARY KEY,
	name varchar(255),
	text text,
	link text,
	created date
);