CREATE TABLE users(
    id INT PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(32),
    birthday DATE
);

INSERT INTO users (id, name, password, birthday)
VALUES  (1, 'bato0n4ik.47@gmail.com', '12345', 1998-09-01),
        (2, 'nikita.47@gmail.com', '1234', 2003-01-24),
        (3, 'wargunoff@gmail.com', '123', 1998-04-05);

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));
