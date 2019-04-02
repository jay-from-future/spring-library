INSERT INTO AUTHOR (ID, FIRSTNAME, LASTNAME)
VALUES (AUTHOR_SEQUENCE.NEXTVAL, 'Stephen', 'King');

INSERT INTO GENRE (ID, GENRE)
VALUES (GENRE_SEQUENCE.NEXTVAL, 'Dark Fantasy');

INSERT INTO BOOK (ID, TITLE, AUTHOR_ID, GENRE_ID)
VALUES (BOOK_SEQUENCE.NEXTVAL, 'The Green Mile', 1, 1);

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