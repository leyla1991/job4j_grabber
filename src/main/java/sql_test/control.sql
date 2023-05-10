CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company(id, name) VALUES(1, 'Google');
INSERT INTO company(id, name) VALUES(2, 'MSL');
INSERT INTO company(id, name) VALUES(3, 'Value');
INSERT INTO company(id, name) VALUES(4, 'KeyPAsss');
INSERT INTO company(id, name) VALUES(5, 'RosAtom');
INSERT INTO company(id, name) VALUES (6, 'Letual');
INSERT INTO company(id, name) VALUES (7, 'Kotlin');

INSERT INTO person(id, name, company_id) VALUES (1, 'Sasha', 1);
INSERT INTO person(id, name, company_id) VALUES (2, 'Natasha', 1);
INSERT INTO person(id, name, company_id) VALUES (3, 'Masha', 3);
INSERT INTO person(id, name, company_id) VALUES (4, 'Glasha', 2);
INSERT INTO person(id, name, company_id) VALUES (5, 'Fasha', 4);
INSERT INTO person(id, name, company_id) VALUES (6, 'Pasha', 2);
INSERT INTO person(id, name, company_id) VALUES (7, 'Rasha', 3);
INSERT INTO person(id, name, company_id) VALUES (8, 'Kasha', 5);
INSERT INTO person(id, name, company_id) VALUES (9, 'Tasha', 5);
INSERT INTO person(id, name, company_id) VALUES (10, 'Reasha', 5);


SELECT company.name, COUNT(person.name) AS Количество
FROM
	company INNER JOIN person ON person.company_id = company.id
GROUP BY company.name;

SELECT company.name, COUNT(person.name) AS Количество
FROM
	company INNER JOIN person ON person.company_id = company.id
GROUP BY company.name
HAVING COUNT(person.name) >=
		(SELECT COUNT(company_id)
		 FROM person GROUP BY company_id
		 ORDER BY COUNT(company_id) desc limit 1
		);