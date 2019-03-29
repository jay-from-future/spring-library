INSERT INTO AUTHOR (ID, FIRSTNAME, LASTNAME)
VALUES (1, 'test db first name', 'test db last name');

INSERT INTO GENRE (ID, GENRE)
VALUES (1, 'test db genre');

INSERT INTO BOOK (TITLE, AUTHOR_FK, GENRE_FK)
VALUES ('test db title', 1, 1);