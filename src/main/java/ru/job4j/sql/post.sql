CREATE TABLE post(
	id serial PRIMARY KEY,
	name varchar(255),
	link text UNIQUE not null,
	description text,
	created date
);