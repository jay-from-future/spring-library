-- Book with one author and genre

INSERT INTO AUTHOR (ID, FIRST_NAME, LAST_NAME)
VALUES (AUTHOR_SEQUENCE.NEXTVAL, 'Stephen', 'King');

INSERT INTO GENRE (ID, GENRE)
VALUES (GENRE_SEQUENCE.NEXTVAL, 'Dark Fantasy');

INSERT INTO BOOK (ID, TITLE)
VALUES (BOOK_SEQUENCE.NEXTVAL, 'The Green Mile');

INSERT INTO MAP_BOOK_AUTHOR (BOOK_ID, AUTHOR_ID)
VALUES (1, 1);

INSERT INTO MAP_BOOK_GENRE (BOOK_ID, GENRE_ID)
VALUES (1, 1);

INSERT INTO REVIEW (ID, REVIEW_TEXT, BOOK_ID)
VALUES (REVIEW_SEQUENCE.NEXTVAL,
        'The Green Mile is one of my favorite Stephen King books and I have read it several times.', 1);

INSERT INTO REVIEW (ID, REVIEW_TEXT, BOOK_ID)
VALUES (REVIEW_SEQUENCE.NEXTVAL,
        'The Green Mile should bring you joy, laughter, and if you are like most in the theater this night, tears.', 1);

INSERT INTO REVIEW (ID, REVIEW_TEXT, BOOK_ID)
VALUES (REVIEW_SEQUENCE.NEXTVAL,
        'Without using all the old cliches, I would recommend this book to anyone interested in a creative, intriguing,' ||
        ' and life enhancing experience.', 1);

-- Book with many authors and genres

INSERT INTO AUTHOR (ID, FIRST_NAME, LAST_NAME)
VALUES (AUTHOR_SEQUENCE.NEXTVAL, 'Jeanne', 'Boyarsky');

INSERT INTO AUTHOR (ID, FIRST_NAME, LAST_NAME)
VALUES (AUTHOR_SEQUENCE.NEXTVAL, 'Scott', 'Selikoff');

INSERT INTO GENRE (ID, GENRE)
VALUES (GENRE_SEQUENCE.NEXTVAL, 'Programming');

INSERT INTO GENRE (ID, GENRE)
VALUES (GENRE_SEQUENCE.NEXTVAL, 'Java');

INSERT INTO BOOK (ID, TITLE)
VALUES (BOOK_SEQUENCE.NEXTVAL, 'OCA: Oracle Certified Associate Java SE 8 Programmer I Study Guide');

INSERT INTO MAP_BOOK_AUTHOR (BOOK_ID, AUTHOR_ID)
VALUES (2, 2);

INSERT INTO MAP_BOOK_AUTHOR (BOOK_ID, AUTHOR_ID)
VALUES (2, 3);

INSERT INTO MAP_BOOK_GENRE (BOOK_ID, GENRE_ID)
VALUES (2, 2);

INSERT INTO MAP_BOOK_GENRE (BOOK_ID, GENRE_ID)
VALUES (2, 3);

INSERT INTO REVIEW (ID, REVIEW_TEXT, BOOK_ID)
VALUES (REVIEW_SEQUENCE.NEXTVAL, 'This book is really useful for exam preparation.', 2);